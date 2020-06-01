package com.qtu.portal.bean;
import com.qtu.portal.entity.Order;
import com.qtu.portal.entity.OrderItem;
import com.qtu.portal.entity.OrderShipping;
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
