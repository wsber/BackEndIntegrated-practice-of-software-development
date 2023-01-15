package com.ws.springboot.service.impl;

import com.ws.springboot.controller.dto.RecreationDto;
import com.ws.springboot.entity.Ruralrecreation;
import com.ws.springboot.mapper.RuralrecreationMapper;
import com.ws.springboot.service.IRuralrecreationService;
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
 * @since 2023-01-13
 */
@Service
public class RuralrecreationServiceImpl extends ServiceImpl<RuralrecreationMapper, Ruralrecreation> implements IRuralrecreationService {

    @Resource
    RuralrecreationMapper ruralrecreationMapper;
    @Override
    public List<RecreationDto> getDetailData() {
        return ruralrecreationMapper.getDetailData();
    }
}
