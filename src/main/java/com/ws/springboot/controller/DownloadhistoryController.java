package com.ws.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ws.springboot.entity.Bookinfor;
import com.ws.springboot.mapper.DownloadhistoryMapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


import com.ws.springboot.service.IDownloadhistoryService;
import com.ws.springboot.entity.Downloadhistory;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-06-05
 */
        @RestController
        @RequestMapping("//downloadHistory")
            public class DownloadhistoryController {
    
    @Resource
    private IDownloadhistoryService downloadhistoryService;

    @Resource
    private DownloadhistoryMapper downloadhistoryMapper;

    @PostMapping
    public Boolean save(@RequestBody Downloadhistory downloadhistory) {
            return downloadhistoryService.saveOrUpdate(downloadhistory);
            }


    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
            return downloadhistoryService.removeById(id);
            }


    @GetMapping
    public List<Downloadhistory> findAll() {
            return downloadhistoryService.list();
            }

    @GetMapping("/{id}")
    public Downloadhistory findOne(@PathVariable Integer id) {
            return downloadhistoryService.getById(id);
            }

    @GetMapping("/downloadHistoryInfor/{userId}")
    public List<Bookinfor> findOneUserDownloadHistory(@PathVariable Integer userId) {
       // System.out.println("下面是查出来的多表联合数据" + downloadhistoryMapper.getDownloadHistoryInfor(id) );

        return downloadhistoryMapper.getDownloadHistoryInfor(userId);
    }

    @DeleteMapping("del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
            return  downloadhistoryService.removeBatchByIds(ids);
            }

    @GetMapping("/page")
    public Page<Downloadhistory> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String username,
                                    @RequestParam(defaultValue = "") String email,
                                    @RequestParam(defaultValue = "") String address){
            QueryWrapper<Downloadhistory> queryWrapper = new QueryWrapper<>();
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
            return downloadhistoryService.page(new Page<>(pageNum, pageSize), queryWrapper);
            }





    }

