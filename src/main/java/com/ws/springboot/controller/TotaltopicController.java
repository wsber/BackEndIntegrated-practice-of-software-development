package com.ws.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


import com.ws.springboot.service.ITotaltopicService;
import com.ws.springboot.entity.Totaltopic;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-06-14
 */
@RestController
@RequestMapping("/totaltopic")
public class TotaltopicController {

    @Autowired
    private ITotaltopicService totaltopicService;

    @PostMapping
    public Boolean save(@RequestBody Totaltopic totaltopic) {
        return totaltopicService.saveOrUpdate(totaltopic);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return totaltopicService.removeById(id);
    }

    @GetMapping
    public List<Totaltopic> findAll() {
        return totaltopicService.list();
    }

    @GetMapping("/{id}")
    public Totaltopic findOne(@PathVariable Integer id) {
        System.out.println(id);
        return totaltopicService.getById(id);
    }

    @GetMapping("/getarticle/{id}")
    public Totaltopic findOne2(@PathVariable Integer id) {
        System.out.println("----------------------");
        return totaltopicService.getById(id);
    }

    @GetMapping("/getBlogsByUser/{author}")
    public List<Totaltopic> findOneByUser(@PathVariable String author) {
        System.out.println(author);
        QueryWrapper<Totaltopic> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("author",author);
        return totaltopicService.list(queryWrapper);
    }

    @DeleteMapping("del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return  totaltopicService.removeBatchByIds(ids);
    }

    @GetMapping("/page")
    public IPage<Totaltopic> findPage(@RequestParam Integer pageNum,
                                      @RequestParam Integer pageSize,
                                      @RequestParam(defaultValue = "") String title){
        QueryWrapper<Totaltopic> queryWrapper = new QueryWrapper<>();
        IPage<Totaltopic> page = new Page<>(pageNum,pageSize);
        if(!"".equals(title)){
            queryWrapper.like("title",title);
        }
        //queryWrapper.orderByDesc("id");
        return totaltopicService.page(page,queryWrapper);
    }
}