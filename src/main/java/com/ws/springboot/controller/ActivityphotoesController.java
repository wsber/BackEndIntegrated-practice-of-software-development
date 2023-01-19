package com.ws.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import com.ws.springboot.service.IActivityphotoesService;
import com.ws.springboot.entity.Activityphotoes;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-01-15
 */
@RestController
@RequestMapping("/activityphotoes")
public class ActivityphotoesController {
    private final int  photoNum = 6;
    
    @Resource
    private IActivityphotoesService activityphotoesService;

    @PostMapping
    public Boolean save(@RequestBody Activityphotoes activityphotoes) {
            return activityphotoesService.saveOrUpdate(activityphotoes);
            }


    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
            return activityphotoesService.removeById(id);
            }

    @GetMapping("/random")
    public List<Activityphotoes> findPhotosWithRandom(){
        //
        List<Activityphotoes> rawList = findAll();
        List<Activityphotoes> results = new ArrayList<>();
        Random r = new Random(System.currentTimeMillis());
        int len = 0;
        int dbPhotoNum = rawList.size();
        Activityphotoes tmp = null;
        while(len < photoNum ){
            int rn = r.nextInt(dbPhotoNum);
            tmp = rawList.get(rn);
            if (!results.contains(tmp))
            {
                results.add(tmp);
                len++;
            }
        }
        return results;
    }

    @GetMapping
    public List<Activityphotoes> findAll() {
            return activityphotoesService.list();
            }

    @GetMapping("/{id}")
    public Activityphotoes findOne(@PathVariable Integer id) {
            return activityphotoesService.getById(id);
            }

    @DeleteMapping("del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
            return  activityphotoesService.removeBatchByIds(ids);
            }

    @GetMapping("/page")
    public Page<Activityphotoes> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String username,
                                    @RequestParam(defaultValue = "") String email,
                                    @RequestParam(defaultValue = "") String address){
            QueryWrapper<Activityphotoes> queryWrapper = new QueryWrapper<>();
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
            return activityphotoesService.page(new Page<>(pageNum, pageSize), queryWrapper);
            }





    }

