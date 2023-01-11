package com.ws.springboot.service.PersonalReadingHouse.impl;

import com.ws.springboot.entity.PersonalReadingHouse.Collecthistory;
import com.ws.springboot.mapper.PersonalReadingHouse.CollecthistoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ws.springboot.service.PersonalReadingHouse.ICollecthistoryService;
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
public class CollecthistoryServiceImpl extends ServiceImpl<CollecthistoryMapper, Collecthistory> implements ICollecthistoryService {

    @Resource CollecthistoryMapper collecthistoryMapper;
    @Override
    public Boolean setCollectHistory(Integer bookid, String userid) {

        Collecthistory collecthistory = new Collecthistory();
        collecthistory.setBookid(bookid);
        collecthistory.setUserid(userid);
        if(selectCHByBookidAndUserid(bookid,userid)){
            System.out.println("这里是插入实验"+ collecthistoryMapper.insert(collecthistory));
            return  true;
        }
        return false;
    }

    @Override
    public Boolean selectCHByBookidAndUserid(Integer bookid, String userid) {
        System.out.println("这里是搜索结果"+collecthistoryMapper.selectCHByBookidAndUserid(bookid,userid));
        if(collecthistoryMapper.selectCHByBookidAndUserid(bookid,userid).size()!=0){
            return false;
        }
        System.out.println("错误的走到了这里");
        return true;
    }

    @Override
    public Boolean deleteCollectHistory(Integer bookid, String userid) {
        return  collecthistoryMapper.deleteByBookidAndUserid(bookid,userid);

    }
}
