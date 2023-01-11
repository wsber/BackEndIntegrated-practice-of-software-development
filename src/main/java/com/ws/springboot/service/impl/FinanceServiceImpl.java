package com.ws.springboot.service.impl;

import com.ws.springboot.entity.Finance;
import com.ws.springboot.mapper.FinanceMapper;
import com.ws.springboot.service.IFinanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-06-03
 */
@Service
public class FinanceServiceImpl extends ServiceImpl<FinanceMapper, Finance> implements IFinanceService {

}
