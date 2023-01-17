package com.ws.springboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ws.springboot.entity.Recreationloacation;
import com.ws.springboot.entity.Ruralrecreation;
import com.ws.springboot.mapper.RecreationloacationMapper;
import com.ws.springboot.service.IRecreationloacationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class RecreationloacationServiceImpl extends ServiceImpl<RecreationloacationMapper, Recreationloacation> implements IRecreationloacationService {

    @Resource
    private RecreationloacationMapper recreationloacationMapper;
    @Override
    public Page<Recreationloacation> getpage(Integer pageNum,Integer pageSize) {

        return getPages(pageNum,pageSize,recreationloacationMapper.getAll());
    }
    /**
     * 分页函数
     * @author pochettino
     * @param currentPage   当前页数
     * @param pageSize  每一页的数据条数
     * @param list  要进行分页的数据列表
     * @return  当前页要展示的数据
     */
    private Page getPages(Integer currentPage, Integer pageSize, List list) {
        Page page = new Page();
        if(list==null){
            return  null;
        }
        int size = list.size();

        if(pageSize > size) {
            pageSize = size;
        }
        if (pageSize!=0){
            // 求出最大页数，防止currentPage越界
            int maxPage = size % pageSize == 0 ? size / pageSize : size / pageSize + 1;

            if(currentPage > maxPage) {
                currentPage = maxPage;
            }
        }
        // 当前页第一条数据的下标
        int curIdx = currentPage > 1 ? (currentPage - 1) * pageSize : 0;

        List pageList = new ArrayList();

        // 将当前页的数据放进pageList
        for(int i = 0; i < pageSize && curIdx + i < size; i++) {
            pageList.add(list.get(curIdx + i));
        }

        page.setCurrent(currentPage).setSize(pageSize).setTotal(list.size()).setRecords(pageList);
        return page;
    }

}
