package com.qtu.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author Hu Shengkai
 * @create 2019-11-27 15:35
 */
@Data
public class ItemParamAndItemCatBean {
    private Long id;

    private Long itemCatId;

    private Date created;

    private Date updated;

    private String paramData;

    private String itemCatName;
}
