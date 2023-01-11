package com.ws.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ws.springboot.common.Result;
import com.ws.springboot.entity.Bookinfor;
import com.ws.springboot.entity.Finance;
import com.ws.springboot.entity.User;
import com.ws.springboot.entity.Userorder;
import com.ws.springboot.mapper.BookOrderMapper;
import com.ws.springboot.mapper.BookinforMapper;
import com.ws.springboot.mapper.UserMapper;
import com.ws.springboot.mapper.UserorderMapper;
import com.ws.springboot.service.IUserorderService;
import com.ws.springboot.utils.TimeNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sirius
 * @since 2022-06-05
 */
@Service
public class UserorderServiceImpl extends ServiceImpl<UserorderMapper, Userorder> implements IUserorderService {

    @Autowired
    private BookinforServiceImpl bookinforService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private FinanceServiceImpl financeService;

    @Override
    public Integer updatesales(Userorder userorder) {
       boolean flagbook = false;
       boolean flaguser = false;
        String bookname = userorder.getBookname();
        Integer bookid = userorder.getBookid();
        String username = userorder.getUsername();
        String userid = userorder.getUserid();
        QueryWrapper<Bookinfor> bookqueryWrapper = new QueryWrapper<>();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        bookqueryWrapper.eq("bookid",bookid);
        bookqueryWrapper.eq("bookname",bookname);
        userQueryWrapper.eq("userid",userid);
        userQueryWrapper.eq("username",username);
        Bookinfor bookinfo = bookinforService.getOne(bookqueryWrapper);
        User user = userService.getOne(userQueryWrapper);


        if(bookinfo==null) return -1;
        if(bookinfo!=null){
            Integer sales = bookinfo.getSales();
            sales = sales + userorder.getBooknum();
            bookinfo.setSales(sales);
            Integer storage = bookinfo.getStockNum();
            storage = storage - userorder.getBooknum();
            if(storage>=0){
                bookinfo.setStockNum(storage);
                if(bookinforService.saveOrUpdate(bookinfo)){
                    System.out.println("更新啦");
                    flagbook=true;
                }else {
                    return -1;
                }
            }
            else{
                return -2;
            }
        }
        if(user!=null){
            double account = user.getAccount();
            account = account - userorder.getTotalprice();
            if(account>=0){
                user.setAccount(account);
                userService.saveOrUpdate(user);
                flaguser=true;
            }
            else {
                return -4;
            }
        }else {
            System.out.println("----------------------更新收入------------------------");
            QueryWrapper<Finance> financeQueryWrapper = new QueryWrapper<>();
            String now = LocalDateTime.now().toString();
            now=now.substring(0,7);
            financeQueryWrapper.like("time", now);
            Finance finance = financeService.getOne(financeQueryWrapper);
            finance.setIncome(finance.getIncome()+userorder.getTotalprice());
            financeService.saveOrUpdate(finance);
            return -5;//账户不存在，是游客
        }
        if(flagbook&&flaguser){
            System.out.println("----------------------更新收入------------------------");
            QueryWrapper<Finance> financeQueryWrapper = new QueryWrapper<>();
            String now = LocalDateTime.now().toString();
            now=now.substring(0,7);
            financeQueryWrapper.like("time", now);
            Finance finance = financeService.getOne(financeQueryWrapper);
            finance.setIncome(finance.getIncome()+userorder.getTotalprice());
            financeService.saveOrUpdate(finance);
            return 1;
        }else
            return 0;
    }


    public Result getAllOrder() {
        return null;
    }


    public Result saveOrder(Userorder userorder) {
        QueryWrapper<Bookinfor> bookqueryWrapper = new QueryWrapper<>();
        bookqueryWrapper.eq("bookid",userorder.getBookid());
        bookqueryWrapper.eq("bookname",userorder.getBookname());
        Bookinfor bookinfor = bookinforService.getOne(bookqueryWrapper);

        QueryWrapper<Userorder> userorderQueryWrapper = new QueryWrapper<>();
        userorderQueryWrapper.eq("ordernumber",userorder.getOrdernumber());
        Userorder res = getOne(userorderQueryWrapper);
        if(res!=null){
            return Result.error("-1","请重新操作");
        }

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",userorder.getUsername());
        User user = userService.getOne(userQueryWrapper);
        if(user.getAccount()<userorder.getTotalprice()){
            return Result.error("-1","余额不足，请充值后重新购买");
        }

        if(bookinfor.getStockNum()<userorder.getBooknum()){
            return Result.error("-1","库存不足");
        }

        userorder.setUserid(user.getUserid());

        bookinfor.setStockNum(bookinfor.getStockNum()-userorder.getBooknum());
        bookinfor.setSales(bookinfor.getSales()+userorder.getBooknum());
        bookinforService.saveOrUpdate(bookinfor);
        user.setAccount(user.getAccount()-userorder.getTotalprice());
        userService.saveOrUpdate(user);
        saveOrUpdate(userorder);

        System.out.println("----------------------更新收入------------------------");
        QueryWrapper<Finance> financeQueryWrapper = new QueryWrapper<>();
        String now = LocalDateTime.now().toString();
        now=now.substring(0,7);
        financeQueryWrapper.like("time", now);
        Finance finance = financeService.getOne(financeQueryWrapper);
        finance.setIncome(finance.getIncome()+userorder.getTotalprice());
        financeService.saveOrUpdate(finance);

        return Result.success();
    }

    public String getOrderNumber() {
        return TimeNumberUtils.getLocalTrmSeqNum();
    }
}
