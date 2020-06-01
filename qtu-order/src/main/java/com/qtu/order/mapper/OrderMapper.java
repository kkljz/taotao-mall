package com.qtu.order.mapper;

import com.qtu.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    int insert(Order record);

    int insertSelective(Order record);
}