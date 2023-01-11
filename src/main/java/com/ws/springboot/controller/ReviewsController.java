package com.ws.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


import com.ws.springboot.service.IReviewsService;
import com.ws.springboot.entity.Reviews;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-06-14
 */
@RestController
@RequestMapping("/reviews")
public class ReviewsController {

    @Resource
    private IReviewsService reviewsService;

    @PostMapping
    public boolean save(@RequestBody Reviews reviews){
        System.out.println(reviews);
        if(reviews.getUsername().equals(""))
            return false;

        return reviewsService.saveOrUpdate(reviews);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable String id) {
        return reviewsService.removeById(id);
    }

    @PostMapping("del/batch")
    public boolean deleteBatch(@RequestBody List<String> userids){
        return reviewsService.removeBatchByIds(userids);
    }

    @GetMapping
    public List<Reviews> findAll() {
        return reviewsService.list();
    }

    @GetMapping("/{id}")
    public List<Reviews> findReviews(@PathVariable Integer id) {
        QueryWrapper<Reviews> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("topicid",id);
        System.out.println("话题id="+id);
        return reviewsService.list(queryWrapper);
    }


    @GetMapping("/page")
    public IPage<Reviews> findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize){
        IPage<Reviews> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Reviews> queryWrapper = new QueryWrapper<>();
        return reviewsService.page(page,queryWrapper);
    }
}
