package com.ws.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ws.springboot.entity.Bookinfor;
import com.ws.springboot.entity.Finance;
import com.ws.springboot.entity.Storeorder;
import com.ws.springboot.mapper.StoreorderMapper;
import com.ws.springboot.service.IStoreorderService;
import com.ws.springboot.utils.TimeNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sirius
 * @since 2022-06-04
 */
@Service
public class StoreorderServiceImpl extends ServiceImpl<StoreorderMapper, Storeorder> implements IStoreorderService {

    @Autowired
    private BookinforServiceImpl bookinforService;

    @Autowired
    private FinanceServiceImpl financeService;

    public boolean updatestorage(Storeorder storeorder) {
        String bookname = storeorder.getBookname();
        QueryWrapper<Bookinfor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bookname",bookname);
        Bookinfor bookinfo = bookinforService.getOne(queryWrapper);


        System.out.println("----------------------更新支出------------------------");
        QueryWrapper<Finance> financeQueryWrapper = new QueryWrapper<>();
        String now = LocalDateTime.now().toString();
        now=now.substring(0,7);
        financeQueryWrapper.like("time", now);
        Finance finance = financeService.getOne(financeQueryWrapper);
        finance.setExpenditure(finance.getExpenditure()+storeorder.getTotalprice());
        financeService.saveOrUpdate(finance);


        if(bookinfo!=null){//进过这种书
            bookinfo.setStockNum(bookinfo.getStockNum()+storeorder.getBooknum());//库存量增加
            bookinfo.setBuyTime(storeorder.getOrdertime());
            return bookinforService.saveOrUpdate(bookinfo);
        }
        else{
            bookinfo = new Bookinfor();
            bookinfo.setStockNum(storeorder.getBooknum());
            bookinfo.setSales(0);
            bookinfo.setAuthorName(storeorder.getAuthor());
            bookinfo.setBookname(storeorder.getBookname());
            bookinfo.setOriginalPrice(storeorder.getSingleprice());
            bookinfo.setBuyTime(storeorder.getOrdertime());
            return bookinforService.saveOrUpdate(bookinfo);
        }
    }
}
