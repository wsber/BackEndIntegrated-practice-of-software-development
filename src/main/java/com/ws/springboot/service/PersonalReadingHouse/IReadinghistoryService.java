package com.ws.springboot.service.PersonalReadingHouse;

import com.ws.springboot.entity.PersonalReadingHouse.Readinghistory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-06-13
 */
public interface IReadinghistoryService extends IService<Readinghistory> {

    boolean setReadingHistory(Integer id, String userid);

    boolean selectRHByBookidAndUserid(Integer bookid,String userid);
}
