CREATE DATABASE IF NOT EXISTS item_service_db;
CREATE USER IF NOT EXISTS 'item_service_user'@'%' IDENTIFIED BY '0d000721';
GRANT ALL PRIVILEGES ON item_service_db.* TO 'item_service_user'@'%';
FLUSH PRIVILEGES;

USE item_service_db;
CREATE TABLE `item`
(
    `id`                   BIGINT       NOT NULL AUTO_INCREMENT,
    `user_id`              BIGINT       NOT NULL,
    `item_state`                INTEGER      NOT NULL,
    `delivery_method_code` INTEGER      NOT NULL,
    `price`                DOUBLE       NOT NULL,
    `postage`              DOUBLE      NOT NULL,
    `name`                 VARCHAR(255) NOT NULL,
    `description`          TEXT         NOT NULL,
    `favorites`            BIGINT       NOT NULL,
    `created_at`           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`           TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`id`, `user_id`)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE `image`
(
    `id`          BIGINT  NOT NULL AUTO_INCREMENT,
    `item_id`     BIGINT  NOT NULL,
    `file_id`     BIGINT  NOT NULL,
    `order_index` INTEGER NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`item_id`, `order_index`)
) DEFAULT CHARSET = utf8mb4;