package com.changgou.user.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.changgou.user.dao.AreasMapper;
import com.changgou.user.pojo.Areas;
import com.changgou.user.service.AreasService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/26 10:55
 * @Description: 地区服务实现
 **/
@Service
public class AreasServiceImpl implements AreasService {
    @Autowired
    private AreasMapper areasMapper;

    @Override
    public List<Areas> findAll() {
        return areasMapper.selectAll();
    }

    @Override
    public Areas findById(String id) {
        return areasMapper.selectByPrimaryKey( id );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAreas(Areas areas) {
        areasMapper.insertSelective( areas );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAreas(Areas areas) {
        areasMapper.updateByPrimaryKeySelective( areas );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        areasMapper.deleteByPrimaryKey( id );
    }

    @Override
    public List<Areas> findList(@NotNull Map<String, Object> searchMap) {
        return areasMapper.selectByExample( getExample( searchMap ) );
    }

    @Override
    public Page<Areas> findPage(@NotNull Map<String, Object> searchMap, Integer pageNum, Integer pageSize) {
        return PageHelper
                .startPage( pageNum, pageSize )
                .doSelectPage( () -> areasMapper.selectByExample( getExample( searchMap ) ) );
    }

    /**
     * 条件拼接
     *
     * @param searchMap 查询条件
     * @return example 条件对象
     */
    private Example getExample(@NotNull Map<String, Object> searchMap) {
        Example example = new Example( Areas.class );
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 区域ID
            String areaId = Convert.toStr( searchMap.get( "areaid" ) );
            if (StrUtil.isNotEmpty( areaId )) {
                criteria.andLike( "areaid", "%" + areaId + "%" );
            }
            // 区域名称
            String area = Convert.toStr( searchMap.get( "area" ) );
            if (StrUtil.isNotEmpty( area )) {
                criteria.andLike( "area", "%" + area + "%" );
            }
            // 城市ID
            String cityId = Convert.toStr( searchMap.get( "cityid" ) );
            if (StrUtil.isNotEmpty( cityId )) {
                criteria.andLike( "cityid", "%" + cityId + "%" );
            }
        }
        return example;
    }
}