package com.qtu.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ItemCat {
    private Long id;

    private Long parentId;

    private String name;

    private Integer status;

    private Integer sortOrder;

    private Boolean isParent;

    private Date created;

    private Date updated;


}