package com.ws.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ws.springboot.entity.Userorder;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Sirius
 * @since 2022-06-05
 */
public interface IUserorderService extends IService<Userorder> {

    Integer updatesales(Userorder userorder);
}
