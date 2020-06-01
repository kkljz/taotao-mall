package com.qtu.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ItemDesc {
    private Long itemId;

    private Date created;

    private Date updated;

    private String itemDesc;
}