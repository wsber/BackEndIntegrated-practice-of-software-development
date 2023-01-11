package com.ws.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


import com.ws.springboot.service.ISeatService;
import com.ws.springboot.entity.Seat;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-06-15
 */
@RestController
@RequestMapping("/seat")
        public class SeatController {

        @Resource
        private ISeatService seatService;

        @PostMapping
        public boolean save(@RequestBody Seat seat) {
                seat.setOrdertime(LocalDateTime.now());
                return seatService.saveOrUpdate(seat);
        }

        @GetMapping
        public List<Seat> findAll() {
                return seatService.list();
        }

        @GetMapping("/{id}")
        public Seat findOne(@PathVariable String id) {
                return seatService.getById(id);
        }

}
