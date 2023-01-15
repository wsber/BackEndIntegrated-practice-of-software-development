package com.ws.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


import com.ws.springboot.service.ILocationService;
import com.ws.springboot.entity.Location;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-01-13
 */
        @RestController
        @RequestMapping("/location")
            public class LocationController {
    
    @Resource
    private ILocationService locationService;

    @PostMapping
    public Boolean save(@RequestBody Location location) {
            return locationService.saveOrUpdate(location);
            }


    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
            return locationService.removeById(id);
            }


    @GetMapping
    public List<Location> findAll() {
            return locationService.list();
            }

    @GetMapping("/{id}")
    public Location findOne(@PathVariable Integer id) {
            return locationService.getById(id);
            }

    @DeleteMapping("del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
            return  locationService.removeBatchByIds(ids);
            }

    @GetMapping("/page")
    public Page<Location> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String username,
                                    @RequestParam(defaultValue = "") String email,
                                    @RequestParam(defaultValue = "") String address){
            QueryWrapper<Location> queryWrapper = new QueryWrapper<>();
            if(!"".equals(username)){
            queryWrapper.like("username" ,username);
            }
            if(!"".equals(email)){
            queryWrapper.like("nickname",email);
            }
            if(!"".equals(address)){
            queryWrapper.like("address",address);
            }

            return locationService.page(new Page<>(pageNum, pageSize), queryWrapper);
            }





    }

