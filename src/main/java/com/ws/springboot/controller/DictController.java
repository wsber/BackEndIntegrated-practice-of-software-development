package com.ws.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


import com.ws.springboot.service.IDictService;
import com.ws.springboot.entity.Dict;


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
        @RequestMapping("//dict")
            public class DictController {
    
    @Resource
    private IDictService dictService;

    @PostMapping
    public Boolean save(@RequestBody Dict dict) {
            return dictService.saveOrUpdate(dict);
            }


    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
            return dictService.removeById(id);
            }


    @GetMapping
    public List<Dict> findAll() {
            return dictService.list();
            }

    @GetMapping("/{id}")
    public Dict findOne(@PathVariable Integer id) {
            return dictService.getById(id);
            }

    @DeleteMapping("del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
            return  dictService.removeBatchByIds(ids);
            }

    @GetMapping("/page")
    public Page<Dict> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String username,
                                    @RequestParam(defaultValue = "") String email,
                                    @RequestParam(defaultValue = "") String address){
            QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
            if(!"".equals(username)){
            queryWrapper.like("username" ,username);
            }
            if(!"".equals(email)){
            queryWrapper.like("nickname",email);
            }
            if(!"".equals(address)){
            queryWrapper.like("address",address);
            }

                queryWrapper.orderByDesc("id");
            return dictService.page(new Page<>(pageNum, pageSize), queryWrapper);
            }





    }

