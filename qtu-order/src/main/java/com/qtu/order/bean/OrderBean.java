package com.qtu.order.bean;

import com.qtu.order.entity.Order;
import com.qtu.order.entity.OrderItem;
import com.qtu.order.entity.OrderShipping;
import lombok.Data;

import java.util.List;

/**
 * @author Hu Shengkai
 * @create 2019-12-13 10:17
 */
@Data
public class OrderBean extends Order {
    private List<OrderItem> orderItems;
    private OrderShipping orderShipping;
}
