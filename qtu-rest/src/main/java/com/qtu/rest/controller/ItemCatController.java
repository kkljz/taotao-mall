package com.qtu.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtu.rest.bean.ItemCatResult;
import com.qtu.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Hu Shengkai
 * @create 2019-12-04 14:54
 */
@Controller
@RequestMapping("/api/item/cat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;


//    /**
//     * 得到所有的itemCat，并封装成树形结构
//     * @return
//     */
//    @GetMapping(value = "/all")
//    public ResponseEntity<String> getAllItemCatToTree(@RequestParam(value = "callback",required = false)String callback){
//        try {
//            ItemCatResult result = itemCatService.getAllItemCat();
//            ObjectMapper mapper = new ObjectMapper();
//            String resultJson = mapper.writeValueAsString(result);
//            if("".equals(callback)){
//                return ResponseEntity.status(HttpStatus.OK).body(resultJson);
//            }else{
//                //包裹callback函数，给js解析。
//                return ResponseEntity.ok(callback+"("+resultJson+");");
//            }
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//    }

    /**
     * 服务接口：得到所有的商品类型（itemcat）
     * @param callback
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/all")
    public Object getAll(@RequestParam(value = "callback",required = false)String callback){
        try {
            ItemCatResult result = itemCatService.getAllItemCat();
            ObjectMapper mapper = new ObjectMapper();
            String resultJson = mapper.writeValueAsString(result);
            if("".equals(callback)){
                return resultJson;
            }else{
                //包裹callback函数，给js解析。
                return callback+"("+resultJson+");";
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }




}
