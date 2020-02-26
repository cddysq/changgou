package com.changgou.user.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.changgou.user.dao.CitiesMapper;
import com.changgou.user.pojo.Cities;
import com.changgou.user.service.CitiesService;
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
 * @Description: 城市服务实现
 **/
@Service
public class CitiesServiceImpl implements CitiesService {
    @Autowired
    private CitiesMapper citiesMapper;

    @Override
    public List<Cities> findAll() {
        return citiesMapper.selectAll();
    }

    @Override
    public Cities findById(String id) {
        return citiesMapper.selectByPrimaryKey( id );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCities(Cities cities) {
        citiesMapper.insertSelective( cities );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCities(Cities cities) {
        citiesMapper.updateByPrimaryKeySelective( cities );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        citiesMapper.deleteByPrimaryKey( id );
    }

    @Override
    public List<Cities> findList(@NotNull Map<String, Object> searchMap) {
        return citiesMapper.selectByExample( getExample( searchMap ) );
    }

    @Override
    public Page<Cities> findPage(@NotNull Map<String, Object> searchMap, Integer pageNum, Integer pageSize) {
        return PageHelper
                .startPage( pageNum, pageSize )
                .doSelectPage( () -> citiesMapper.selectByExample( getExample( searchMap ) ) );
    }

    /**
     * 条件拼接
     *
     * @param searchMap 查询条件
     * @return example 条件对象
     */
    private Example getExample(@NotNull Map<String, Object> searchMap) {
        Example example = new Example( Cities.class );
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 城市ID
            String cityId = Convert.toStr( searchMap.get( "cityid" ) );
            if (StrUtil.isNotEmpty( cityId )) {
                criteria.andLike( "cityid", "%" + cityId + "%" );
            }
            // 城市名称
            String city = Convert.toStr( searchMap.get( "city" ) );
            if (StrUtil.isNotEmpty( city )) {
                criteria.andLike( "city", "%" + city + "%" );
            }
            // 省份ID
            String provinceId = Convert.toStr( searchMap.get( "provinceid" ) );
            if (StrUtil.isNotEmpty( provinceId )) {
                criteria.andLike( "provinceid", "%" + provinceId + "%" );
            }
        }
        return example;
    }
}