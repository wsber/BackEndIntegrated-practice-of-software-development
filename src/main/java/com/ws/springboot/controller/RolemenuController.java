package com.ws.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


import com.ws.springboot.service.IRolemenuService;
import com.ws.springboot.entity.Rolemenu;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-06-11
 */
        @RestController
        @RequestMapping("//rolemenu")
            public class RolemenuController {
    
    @Resource
    private IRolemenuService rolemenuService;

    @PostMapping
    public Boolean save(@RequestBody Rolemenu rolemenu) {
            return rolemenuService.saveOrUpdate(rolemenu);
            }


    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
            return rolemenuService.removeById(id);
            }


    @GetMapping
    public List<Rolemenu> findAll() {
            return rolemenuService.list();
            }

    @GetMapping("/{id}")
    public Rolemenu findOne(@PathVariable Integer id) {
            return rolemenuService.getById(id);
            }

    @DeleteMapping("del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
            return  rolemenuService.removeBatchByIds(ids);
            }



    }

