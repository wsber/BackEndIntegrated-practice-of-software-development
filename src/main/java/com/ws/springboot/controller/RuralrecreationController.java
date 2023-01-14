package com.ws.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
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

    @GetMapping("/{id}")
    public Ruralrecreation findOne(@PathVariable Integer id) {
            return ruralrecreationService.getById(id);
            }

    @DeleteMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
            return  ruralrecreationService.removeBatchByIds(ids);
            }

    @GetMapping("/page")
    public Page<Ruralrecreation> findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(defaultValue = "") String activity_name,
                                        @RequestParam(defaultValue = "") String id,
                                        @RequestParam(defaultValue = "") String type){
            QueryWrapper<Ruralrecreation> queryWrapper = new QueryWrapper<>();
            if(!"".equals(activity_name)){
            queryWrapper.like("activity_name" ,activity_name);
            }
            if(!"".equals(id)){
            queryWrapper.like("id",id);
            }
            if(!"".equals(type)){
            queryWrapper.like("type",type);
            }
            System.out.println(1);
            return ruralrecreationService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }
}

