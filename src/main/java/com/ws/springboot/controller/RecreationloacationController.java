package com.ws.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


import com.ws.springboot.service.IRecreationloacationService;
import com.ws.springboot.entity.Recreationloacation;


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
@RequestMapping("/recreationloacation")
public class RecreationloacationController {
    
    @Resource
    private IRecreationloacationService recreationloacationService;

    @PostMapping
    public Boolean save(@RequestBody Recreationloacation recreationloacation) {
            return recreationloacationService.saveOrUpdate(recreationloacation);
            }


    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
            return recreationloacationService.removeById(id);
            }


    @GetMapping
    public List<Recreationloacation> findAll() {
            return recreationloacationService.list();
            }

    @GetMapping("/{id}")
    public Recreationloacation findOne(@PathVariable Integer id) {
            return recreationloacationService.getById(id);
            }

    @DeleteMapping("del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
            return  recreationloacationService.removeBatchByIds(ids);
            }



    @GetMapping("/page")
    public Page<Recreationloacation> findPage(@RequestParam Integer pageNum,
                                            @RequestParam Integer pageSize,
                                            @RequestParam(defaultValue = "") String username,
                                            @RequestParam(defaultValue = "") String email,
                                            @RequestParam(defaultValue = "") String address){
            QueryWrapper<Recreationloacation> queryWrapper = new QueryWrapper<>();
            if(!"".equals(username)){
            queryWrapper.like("username" ,username);
            }
            if(!"".equals(email)){
            queryWrapper.like("nickname",email);
            }
            if(!"".equals(address)){
            queryWrapper.like("address",address);
            }
            return recreationloacationService.getpage(pageNum,pageSize);
            }
    }

