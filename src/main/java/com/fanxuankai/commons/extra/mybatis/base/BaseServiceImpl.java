package com.fanxuankai.commons.extra.mybatis.base;

import com.fanxuankai.commons.domain.Page;
import com.fanxuankai.commons.domain.PageResult;
import com.fanxuankai.commons.util.Converter;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Service 接口实现类
 *
 * @param <T>   实体类
 * @param <D>   DTO
 * @param <V>   VO
 * @param <C>   对象转换器
 * @param <DAO> DAO
 * @author fanxuankai
 */
public class BaseServiceImpl<T extends BaseModel, D, V, C extends Converter<T, D, V>, DAO extends BaseDao<T>>
        implements BaseService<D, V> {
    @Resource
    private C converter;
    @Resource
    private DAO dao;

    @Override
    public PageResult<V> page(Object criteria, Page page) {
        return dao.page(criteria, page).map(converter::toVo);
    }

    @Override
    public List<V> list(Object criteria) {
        return converter.toVo(dao.list(criteria));
    }

    @Override
    public V get(Long id) {
        return converter.toVo(dao.getById(id));
    }

    @Override
    public void create(D dto) {
        dao.save(converter.toEntity(dto));
    }

    @Override
    public void update(Long id, D dto) {
        T existsUser = dao.getById(id);
        T t = converter.toEntity(dto);
        t.setId(existsUser.getId());
        dao.updateById(t);
    }

    @Override
    public void deleteAll(Long[] ids) {
        dao.removeByIds(Arrays.asList(ids));
    }
}