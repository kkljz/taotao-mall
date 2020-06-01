package com.qtu.bean;

import lombok.Data;

import java.util.List;

/**
 * @author Hu Shengkai
 * @create 2019-12-07 10:18
 */
@Data
public class SearchResult {
    private List<?> itemList;
    private int totalPages;
    private long total;
}
