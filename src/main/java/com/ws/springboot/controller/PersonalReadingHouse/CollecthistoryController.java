package com.ws.springboot.controller.PersonalReadingHouse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ws.springboot.common.Result;
import com.ws.springboot.entity.Bookinfor;
import com.ws.springboot.mapper.PersonalReadingHouse.CollecthistoryMapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


import com.ws.springboot.service.PersonalReadingHouse.ICollecthistoryService;
import com.ws.springboot.entity.PersonalReadingHouse.Collecthistory;


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
        @RequestMapping("//collecthistory")
            public class CollecthistoryController {
    
    @Resource
    private ICollecthistoryService collecthistoryService;

    @Resource
    private CollecthistoryMapper collecthistoryMapper;

    @PostMapping
    public Boolean save(@RequestBody Collecthistory collecthistory) {
            return collecthistoryService.saveOrUpdate(collecthistory);
            }


    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
            return collecthistoryService.removeById(id);
            }


    @GetMapping
    public List<Collecthistory> findAll() {
            return collecthistoryService.list();
            }

    @GetMapping("/{id}")
    public Collecthistory findOne(@PathVariable Integer id) {
            return collecthistoryService.getById(id);
            }

     @GetMapping("/findUserInfor/{userid}")
     public Result findAllByUserid(@PathVariable String userid){
         return Result.success(collecthistoryMapper.getAllByUserid(userid));
     }
    @DeleteMapping("del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
            return  collecthistoryService.removeBatchByIds(ids);
            }


    @GetMapping("/getCollectHistoryInfor/{userId}")
    public List<Bookinfor> getUserAllCollectedHistory(@PathVariable Integer userId) {
        // System.out.println("下面是查出来的多表联合数据" + downloadhistoryMapper.getDownloadHistoryInfor(id) );
        return collecthistoryMapper.getCollectHistoryInfor(userId);
    }

    @PostMapping("/collect/{bookid}/{userid}")
    public Result saveHistory(@PathVariable String bookid, @PathVariable String userid) {
        Integer id = Integer.valueOf(bookid);
        if (collecthistoryService.setCollectHistory(id,userid))
        return Result.success();
        else {
            return  Result.error("CODE_600","收藏失败：因为重复收藏或则会员等级不够");
        }
    }

    @PostMapping("/delete/{bookid}/{userid}")
    public Result deleteHistory(@PathVariable Integer bookid, @PathVariable String userid) {
        if (collecthistoryService.deleteCollectHistory(bookid,userid))
            return Result.success();
        else {
            return  Result.error("CODE_600","删除失败");
        }
    }

    }

