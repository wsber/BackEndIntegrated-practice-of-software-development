package com.ws.springboot.service.PersonalReadingHouse;

import com.ws.springboot.entity.PersonalReadingHouse.Collecthistory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-06-13
 */
public interface ICollecthistoryService extends IService<Collecthistory> {

    Boolean setCollectHistory(Integer bookid, String userid);

    Boolean selectCHByBookidAndUserid(Integer bookid,String userid);

    Boolean deleteCollectHistory(Integer bookid, String userid);
}
