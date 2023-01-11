package com.ws.springboot.service.impl;

import com.ws.springboot.entity.Seat;
import com.ws.springboot.mapper.SeatMapper;
import com.ws.springboot.service.ISeatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-06-15
 */
@Service
public class SeatServiceImpl extends ServiceImpl<SeatMapper, Seat> implements ISeatService {

}
