package com.qtu.search;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qtu.bean.SearchBean;
import com.qtu.bean.SearchResult;
import com.qtu.search.entity.Item;
import com.qtu.search.mapper.ItemMapper;
import com.qtu.search.service.SolrService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

/**
 * @author Hu Shengkai
 * @create 2019-12-06 15:28
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class SolerTest {
//    @Autowired
//    private SolrService solrService;
//    @Autowired
//    private ItemMapper itemMapper;

//    @Test
//    public void testSave() {
//        Item i = new Item();
//        i.setId(10002L);
//        i.setTitle("三星 Galaxy Note10+ 5G手机 骁龙855 智能S Pen 12GB+256GB 莫奈彩 双卡双待 游戏手机");
//        i.setSellPoint("【三星12.12嗨购】白条24期免息！购机赠原装无线充电器+一年原厂碎屏保");
//        i.setPrice(7999L);
//        i.setNum(99999);
//        i.setImage("http://images.qtu.com/2019/12/06/12312312312.jpg");
//        solrService.add(i);
//    }
//
//    @Test
//    public void saveAll(){
//        //分页插入
//        int pageNo = 1;
//        int pages = 0;
//        PageHelper.startPage(pageNo,500);
//        List<Item> list = itemMapper.selectAll();
//        PageInfo<Item> pageInfo = new PageInfo<>(list);
//        pages = pageInfo.getPages();
//        solrService.adds(list);
//        pageNo++;
//        while ( pageNo <= pages ) {
//            PageHelper.startPage(pageNo,500);
//            list = itemMapper.selectAll();
//            solrService.adds(list);
//            pageNo++;
//        }
//    }
//
//    @Test
//    public void searchByTitle(){
//        SearchBean searchBean = new SearchBean();
//        searchBean.setKeywords("三星");
//        SearchResult search = solrService.search(searchBean);
//        System.out.println(search);
//    }
//
//    @Test
//    public void testSearchById(){
//        Item item = solrService.searchById(562379);
//        System.out.println(item);
//    }

}
