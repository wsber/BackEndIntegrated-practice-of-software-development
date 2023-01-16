package com.ws.springboot.service.impl;

import com.ws.springboot.entity.Bookinfor;
import com.ws.springboot.mapper.BookinforMapper;
import com.ws.springboot.service.IBookinforService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-05-29
 */
@Service
public class BookinforServiceImpl extends ServiceImpl<BookinforMapper, Bookinfor> implements IBookinforService {

    @Resource
    private BookinforMapper bookinforMapper;

    @Override
    public List<Bookinfor> getVillageBooks() {
        return bookinforMapper.getVillageBooks();
    }

    @Override
    public List<Bookinfor> getYouthBooks() {
        return bookinforMapper.getYouthBooks();
    }

}
