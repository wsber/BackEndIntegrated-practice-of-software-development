package com.ws.springboot.service.impl;

import com.ws.springboot.entity.Location;
import com.ws.springboot.mapper.LocationMapper;
import com.ws.springboot.service.ILocationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-01-13
 */
@Service
public class LocationServiceImpl extends ServiceImpl<LocationMapper, Location> implements ILocationService {

}
