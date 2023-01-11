package com.ws.springboot.service.impl;

import com.ws.springboot.entity.Reviews;
import com.ws.springboot.mapper.ReviewsMapper;
import com.ws.springboot.service.IReviewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-06-14
 */
@Service
public class ReviewsServiceImpl extends ServiceImpl<ReviewsMapper, Reviews> implements IReviewsService {

}
