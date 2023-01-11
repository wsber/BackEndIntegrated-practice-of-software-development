package com.ws.springboot.service.PersonalReadingHouse.impl;

import com.ws.springboot.entity.PersonalReadingHouse.Collecthistory;
import com.ws.springboot.entity.PersonalReadingHouse.Readinghistory;
import com.ws.springboot.mapper.PersonalReadingHouse.ReadinghistoryMapper;
import com.ws.springboot.service.PersonalReadingHouse.IReadinghistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-06-13
 */
@Service
public class ReadinghistoryServiceImpl extends ServiceImpl<ReadinghistoryMapper, Readinghistory> implements IReadinghistoryService {

    @Resource ReadinghistoryMapper readinghistoryMapper;

    @Override
    public boolean setReadingHistory(Integer bookid, String userid) {

        Readinghistory readinghistory = new Readinghistory();
        readinghistory.setBookid(bookid);
        readinghistory.setUserid(userid);
        if(selectRHByBookidAndUserid(bookid,userid)){
            System.out.println("这里是插入实验"+ readinghistoryMapper.insert(readinghistory));
            return  true;
        }
        return false;
    }

    @Override
    public boolean selectRHByBookidAndUserid(Integer bookid, String userid) {
        System.out.println("这里是搜索结果"+readinghistoryMapper.selectRHByBookidAndUserid(bookid,userid));
        if(readinghistoryMapper.selectRHByBookidAndUserid(bookid,userid).size()!=0){
            return false;
        }
        System.out.println("错误的走到了这里");
        return true;
    }
}
