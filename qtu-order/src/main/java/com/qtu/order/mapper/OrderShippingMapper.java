package com.qtu.order.mapper;

import com.qtu.order.entity.OrderShipping;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderShippingMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(OrderShipping record);

    int insertSelective(OrderShipping record);

    OrderShipping selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderShipping record);

    int updateByPrimaryKey(OrderShipping record);
}