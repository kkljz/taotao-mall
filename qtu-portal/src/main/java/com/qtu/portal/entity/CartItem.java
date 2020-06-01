package com.qtu.portal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author Hu Shengkai
 * @create 2019-12-11 11:39
 */
@Data
public class CartItem {
    private Long id;

    private String title;

        private Long price;

    private Integer num;

    private String image;

    private String[] images;

    @JsonIgnore
    public String[] getImages(){
        if (image != null){
            String[] images = image.split(",");
            return images;
        }
        return null;
    }
}
