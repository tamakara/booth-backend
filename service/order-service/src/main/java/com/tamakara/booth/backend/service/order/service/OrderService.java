package com.tamakara.booth.backend.service.order.service;

import com.tamakara.booth.backend.service.order.domain.entity.Order;
import com.tamakara.booth.backend.service.order.domain.vo.OrderVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OrderService extends IService<Order> {
    Long createOrder(Long userId, Long itemId);

    Boolean payOrder(Long userId, Long orderId);

    OrderVO getOrderVO(Long userId, Long orderId);
}
