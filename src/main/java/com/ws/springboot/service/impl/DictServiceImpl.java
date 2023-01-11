package com.ws.springboot.service.impl;

import com.ws.springboot.entity.Dict;
import com.ws.springboot.mapper.DictMapper;
import com.ws.springboot.service.IDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-06-11
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

}
