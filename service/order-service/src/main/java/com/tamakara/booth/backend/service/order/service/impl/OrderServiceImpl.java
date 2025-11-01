package com.tamakara.booth.backend.service.order.service.impl;

import com.tamakara.booth.backend.common.client.ItemClient;
import com.tamakara.booth.backend.common.client.UserClient;
import com.tamakara.booth.backend.common.domain.vo.ItemVO;
import com.tamakara.booth.backend.service.order.domain.entity.Order;
import com.tamakara.booth.backend.service.order.domain.pojo.OrderState;
import com.tamakara.booth.backend.service.order.domain.vo.OrderVO;
import com.tamakara.booth.backend.service.order.mapper.OrderMapper;
import com.tamakara.booth.backend.service.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    private final OrderMapper orderMapper;
    private final UserClient userClient;
    private final ItemClient itemClient;

    @Override
    @Transactional
    public Long createOrder(Long userId, Long itemId) {
        boolean sold = exists(new LambdaQueryWrapper<Order>()
                .eq(Order::getItemId, itemId)
                .ne(Order::getOrderState, OrderState.CANCELLED)
        );
        if (sold) {
            throw new RuntimeException("商品已被购买");
        }

        ItemVO itemVO = itemClient.getItemVO(itemId).getBody();
        if (itemVO == null) {
            throw new RuntimeException("商品不存在");
        }

        Order order = new Order();
        order.setItemId(itemId);
        order.setSellerId(userId);

        order.setOrderState(OrderState.CREATED);
        order.setPayAmount(itemVO.getPrice() + (itemVO.getDeliveryMethodCode() == 2 ? itemVO.getPostage() : 0.0));
        orderMapper.insert(order);

        itemClient.lockItem(itemId);
        return order.getId();
    }

    @Override
    public Boolean payOrder(Long userId, Long orderId) {
        Order order = orderMapper.selectById(orderId);
        Boolean result = userClient.pay(userId, orderId, order.getPayAmount()).getBody();
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderVO getOrderVO(Long userId, Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        OrderVO vo = new OrderVO();
        BeanUtils.copyProperties(order, vo);
        return vo;
    }

}