package com.qtu.rest.service;

import com.qtu.util.TaotaoResult;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hu Shengkai
 * @create 2019-12-04 22:00
 */
public interface ContentService {
    TaotaoResult listContentByCategoryId(Long categoryId);
}
