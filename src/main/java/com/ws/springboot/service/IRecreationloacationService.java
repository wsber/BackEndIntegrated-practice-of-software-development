package com.ws.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ws.springboot.entity.Recreationloacation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2023-01-13
 */
public interface IRecreationloacationService extends IService<Recreationloacation> {
    Page<Recreationloacation> getpage(Integer pageNum,Integer pageSize);
}
