package com.qtu.bean;

import lombok.Data;

import java.util.List;

/**
 * @author Hu Shengkai
 * @create 2019-11-27 10:40
 */
@Data
public class EUDataGridResult {
    private long total;
    private List<?> rows;
}
