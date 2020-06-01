package com.qtu.order.mapper;

import com.qtu.order.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemMapper {
    int insert(OrderItem record);

    int insertSelective(OrderItem record);
}