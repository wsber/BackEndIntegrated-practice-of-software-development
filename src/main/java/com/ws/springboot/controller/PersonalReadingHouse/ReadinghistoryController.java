package com.ws.springboot.controller.PersonalReadingHouse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ws.springboot.common.Result;
import com.ws.springboot.entity.Bookinfor;
import com.ws.springboot.mapper.PersonalReadingHouse.ReadinghistoryMapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


import com.ws.springboot.service.PersonalReadingHouse.IReadinghistoryService;
import com.ws.springboot.entity.PersonalReadingHouse.Readinghistory;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-06-13
 */
        @RestController
        @RequestMapping("//readinghistory")
            public class ReadinghistoryController {
    
    @Resource
    private IReadinghistoryService readinghistoryService;

    @Resource
    private ReadinghistoryMapper readinghistoryMapper;

    @PostMapping
    public Boolean save(@RequestBody Readinghistory readinghistory) {
            return readinghistoryService.saveOrUpdate(readinghistory);
            }


    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
            return readinghistoryService.removeById(id);
            }


    @GetMapping
    public List<Readinghistory> findAll() {
            return readinghistoryService.list();
            }

    @GetMapping("/{id}")
    public Readinghistory findOne(@PathVariable Integer id) {
            return readinghistoryService.getById(id);
            }

    @DeleteMapping("del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
            return  readinghistoryService.removeBatchByIds(ids);
            }

//    @GetMapping("/page")
//    public Page<Readinghistory> findPage(@RequestParam Integer pageNum,
//                                    @RequestParam Integer pageSize,
//                                    @RequestParam(defaultValue = "") String username,
//                                    @RequestParam(defaultValue = "") String email,
//                                    @RequestParam(defaultValue = "") String address){
//            QueryWrapper<Readinghistory> queryWrapper = new QueryWrapper<>();
//            if(!"".equals(username)){
//            queryWrapper.like("username" ,username);
//            }
//            if(!"".equals(email)){
//            queryWrapper.like("nickname",email);
//            }
//            if(!"".equals(address)){
//            queryWrapper.like("address",address);
//            }
//
//                queryWrapper.orderByDesc("id");
//            return readinghistoryService.page(new Page<>(pageNum, pageSize), queryWrapper);
//            }
//
    @GetMapping("/getReadingHistoryInfor/{userId}")
    public List<Bookinfor> findOneUserDownloadHistory(@PathVariable Integer userId) {
        // System.out.println("下面是查出来的多表联合数据" + downloadhistoryMapper.getDownloadHistoryInfor(id) );
        return readinghistoryMapper.getReadingHistoryInfor(userId);
    }

    @PostMapping("/reading/{bookid}/{userid}")
    public Result saveHistory(@PathVariable String bookid, @PathVariable String userid) {
        Integer id = Integer.valueOf(bookid);
        if (readinghistoryService.setReadingHistory(id,userid))
            return Result.success();
        else {
            return  Result.error("CODE_600","收藏失败：因为重复收藏或则会员等级不够");
        }
    }



    }

