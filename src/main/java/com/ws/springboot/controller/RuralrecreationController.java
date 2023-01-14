package com.ws.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ws.springboot.controller.dto.RecreationDto;
import com.ws.springboot.entity.Bookinfor;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;


import com.ws.springboot.service.IRuralrecreationService;
import com.ws.springboot.entity.Ruralrecreation;


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
        @RequestMapping("/ruralrecreation")
            public class RuralrecreationController {
    
    @Resource
    private IRuralrecreationService ruralrecreationService;

    @PostMapping
    public Boolean save(@RequestBody Ruralrecreation ruralrecreation) {
            return ruralrecreationService.saveOrUpdate(ruralrecreation);
            }


    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
            return ruralrecreationService.removeById(id);
            }


    @GetMapping
    public List<Ruralrecreation> findAll() {
            return ruralrecreationService.list();
            }

    @GetMapping("/detail")
    public List<RecreationDto> getDetailData(){
            return ruralrecreationService.getDetailData();
    }

    @GetMapping("/{id}")
    public Ruralrecreation findOne(@PathVariable Integer id) {
            return ruralrecreationService.getById(id);
            }

    @DeleteMapping("del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
            return  ruralrecreationService.removeBatchByIds(ids);
            }

    @GetMapping("/page")
    public Page<Ruralrecreation> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String username,
                                    @RequestParam(defaultValue = "") String email,
                                    @RequestParam(defaultValue = "") String address){
            QueryWrapper<Ruralrecreation> queryWrapper = new QueryWrapper<>();
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
            return ruralrecreationService.page(new Page<>(pageNum, pageSize), queryWrapper);
            }


    @GetMapping("/likeSearchActivities")
    public List<Ruralrecreation> likeSearchBooks(
            @RequestParam(defaultValue = "") String activityName,
            @RequestParam(defaultValue = "") String activityType,
            @RequestParam(defaultValue = "") String performanceTeam) {

        QueryWrapper<Ruralrecreation> queryWrapper = new QueryWrapper<>();
        int readingPrivilege =  0;

        if (!"".equals(activityName)) {
            queryWrapper.like("activity_name", activityName);
        }
        if (!"".equals(activityType)) {
            queryWrapper.eq("type", activityType);
        }
        if (!"".equals(performanceTeam)) {
            queryWrapper.like("performance_team", performanceTeam);
        }
        List<Ruralrecreation> ruralrecreations = new ArrayList<>();
        ruralrecreations.addAll(ruralrecreationService.list(queryWrapper));
        return ruralrecreations;

    }
    }

