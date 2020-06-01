package com.qtu.entity;

import lombok.Data;

import java.util.Date;

/**
 * 商品规格和商品的关系表
 */
@Data
public class ItemParamItem {
    private Long id;

    private Long itemId;

    private Date created;

    private Date updated;

    private String paramData;

}