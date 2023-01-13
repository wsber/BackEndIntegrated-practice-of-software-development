package com.ws.springboot.service;

import com.ws.springboot.controller.dto.RecreationDto;
import com.ws.springboot.entity.Ruralrecreation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2023-01-13
 */
public interface IRuralrecreationService extends IService<Ruralrecreation> {

    List<RecreationDto> getDetailData();
}
