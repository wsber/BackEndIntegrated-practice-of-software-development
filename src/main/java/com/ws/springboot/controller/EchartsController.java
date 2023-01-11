package com.ws.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.ws.springboot.common.Result;
import com.ws.springboot.entity.Finance;
import com.ws.springboot.entity.User;
import com.ws.springboot.service.IFinanceService;
import com.ws.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IFinanceService financeService;

    @GetMapping("/ex")
    public Result get() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y", CollUtil.newArrayList(150, 230, 224, 210));
        return Result.success(map);
    }

    @GetMapping("/members")
    public Result members() {
        List<User> list = userService.list();
        int q1 = 0; // 第一季度
        int q2 = 0; // 第二季度
        int q3 = 0; // 第三季度
        int q4 = 0; // 第四季度
        for (User user : list) {
            LocalDateTime createTime = user.getRegdate();
            System.out.println(createTime);
            Quarter quarter = DateUtil.quarterEnum(Date.from( createTime.atZone( ZoneId.systemDefault()).toInstant()));
            switch (quarter) {
                case Q1: q1 += 1; break;
                case Q2: q2 += 1; break;
                case Q3: q3 += 1; break;
                case Q4: q4 += 1; break;
                default: break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }

    @GetMapping("/income")
    public Result getIncmome() {
        Map<String, Object> map = new HashMap<>();
        List<Finance> list = financeService.list();
        List<Double> fina =  CollUtil.newArrayList();
        List<String> time =  CollUtil.newArrayList();
        for (Finance finance : list) {
            System.out.println(finance.getIncome());
             fina.add(finance.getIncome());
             time.add(finance.getTime());
        }

        map.put("x", time);
        map.put("y", fina);
        return Result.success(map);
    }

}
