package com.qtu.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ItemParam {

    private Long id;

    private Long itemCatId;

    private Date created;

    private Date updated;

    private String paramData;

}