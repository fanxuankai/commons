package com.fanxuankai.commons.extra.mybatis.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fanxuankai.commons.domain.PageRequest;
import com.fanxuankai.commons.domain.PageResult;
import com.fanxuankai.commons.extra.mybatis.util.MybatisPlusPageUtils;
import com.fanxuankai.commons.extra.mybatis.util.QueryHelper;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * DAO 接口
 *
 * @param <T> 实体类
 * @author fanxuankai
 */
public interface BaseDao<T, Criteria> extends IService<T> {
    /**
     * 删除记录
     *
     * @param criteria 条件参数
     * @return 是否删除成功
     * @see IService#remove(com.baomidou.mybatisplus.core.conditions.Wrapper)
     */
    default boolean remove(Criteria criteria) {
        return remove(QueryHelper.getQueryWrapper(EntityClassCache.entityClass(getClass()), criteria));
    }

    /**
     * 更新记录 需要设置 sql set
     *
     * @param criteria 条件参数
     * @return 是否更新成功
     * @see IService#update(com.baomidou.mybatisplus.core.conditions.Wrapper)
     */
    default boolean update(Criteria criteria) {
        return update(QueryHelper.getUpdateWrapper(EntityClassCache.entityClass(getClass()), criteria));
    }

    /**
     * 更新记录
     *
     * @param t        实体类
     * @param criteria 条件参数
     * @return 是否更新成功
     * @see IService#update(java.lang.Object, com.baomidou.mybatisplus.core.conditions.Wrapper)
     */
    default boolean update(T t, Criteria criteria) {
        return update(t, QueryHelper.getUpdateWrapper(EntityClassCache.entityClass(getClass()), criteria));
    }

    /**
     * 查询一条记录
     *
     * @param criteria 条件参数
     * @return T
     * @see IService#getOne(com.baomidou.mybatisplus.core.conditions.Wrapper)
     */
    default T getOne(Criteria criteria) {
        return getOne(QueryHelper.getQueryWrapper(EntityClassCache.entityClass(getClass()), criteria));
    }

    /**
     * 查询一条记录
     *
     * @param criteria 条件参数
     * @param throwEx  有多个 result 是否抛出异常
     * @return T
     * @see IService#getOne(com.baomidou.mybatisplus.core.conditions.Wrapper, boolean)
     */
    default T getOne(Criteria criteria, boolean throwEx) {
        return getOne(QueryHelper.getQueryWrapper(EntityClassCache.entityClass(getClass()), criteria), throwEx);
    }

    /**
     * 查询一条记录
     *
     * @param criteria 条件参数
     * @return Map
     * @see IService#getMap(com.baomidou.mybatisplus.core.conditions.Wrapper)
     */
    default Map<String, Object> getMap(Criteria criteria) {
        return getMap(QueryHelper.getQueryWrapper(EntityClassCache.entityClass(getClass()), criteria));
    }

    /**
     * 查询一条记录
     *
     * @param criteria 条件参数
     * @param mapper   转换函数
     * @return V
     * @see IService#getObj(com.baomidou.mybatisplus.core.conditions.Wrapper, java.util.function.Function)
     */
    default <V> V getObj(Criteria criteria, Function<? super Object, V> mapper) {
        return getObj(QueryHelper.getQueryWrapper(EntityClassCache.entityClass(getClass()), criteria), mapper);
    }

    /**
     * 查询总记录数
     *
     * @param criteria 条件参数
     * @return int
     * @see IService#count(com.baomidou.mybatisplus.core.conditions.Wrapper)
     */
    default int count(Criteria criteria) {
        return count(QueryHelper.getQueryWrapper(EntityClassCache.entityClass(getClass()), criteria));
    }

    /**
     * 查询列表
     *
     * @param criteria 条件参数
     * @return List
     * @see IService#list(com.baomidou.mybatisplus.core.conditions.Wrapper)
     */
    default List<T> list(Criteria criteria) {
        return list(QueryHelper.getQueryWrapper(EntityClassCache.entityClass(getClass()), criteria));
    }

    /**
     * 翻页查询
     *
     * @param pageRequest 分页参数
     * @param criteria    条件
     * @return PageResult
     * @see IService#page(com.baomidou.mybatisplus.core.metadata.IPage, com.baomidou.mybatisplus.core.conditions.Wrapper)
     */
    default PageResult<T> page(PageRequest pageRequest, Criteria criteria) {
        return MybatisPlusPageUtils.convert(page(MybatisPlusPageUtils.convert(pageRequest),
                QueryHelper.getQueryWrapper(EntityClassCache.entityClass(getClass()), criteria)));
    }

    /**
     * 翻页查询
     *
     * @param pageRequest 分页参数
     * @return PageResult
     * @see IService#page(com.baomidou.mybatisplus.core.metadata.IPage)
     */
    default PageResult<T> page(PageRequest pageRequest) {
        return MybatisPlusPageUtils.convert(page(MybatisPlusPageUtils.convert(pageRequest)));
    }

    /**
     * 查询列表
     *
     * @param criteria 条件
     * @return List
     * @see IService#listMaps(com.baomidou.mybatisplus.core.conditions.Wrapper)
     */
    default List<Map<String, Object>> listMaps(Criteria criteria) {
        return listMaps(QueryHelper.getQueryWrapper(EntityClassCache.entityClass(getClass()), criteria));
    }

    /**
     * 查询全部记录
     *
     * @param criteria 条件
     * @return List
     * @see IService#listObjs(com.baomidou.mybatisplus.core.conditions.Wrapper)
     */
    default List<Object> listObjs(Criteria criteria) {
        return listObjs(QueryHelper.getQueryWrapper(EntityClassCache.entityClass(getClass()), criteria));
    }

    /**
     * 查询全部记录
     *
     * @param criteria 条件
     * @param mapper   转换函数
     * @return List
     * @see IService#listObjs(com.baomidou.mybatisplus.core.conditions.Wrapper)
     */
    default <V> List<V> listObjs(Criteria criteria, Function<? super Object, V> mapper) {
        return listObjs(QueryHelper.getQueryWrapper(EntityClassCache.entityClass(getClass()), criteria), mapper);
    }

    /**
     * 翻页查询
     *
     * @param pageRequest 分页参数
     * @param criteria    条件
     * @return PageResult
     * @see IService#pageMaps(com.baomidou.mybatisplus.core.metadata.IPage, com.baomidou.mybatisplus.core.conditions.Wrapper)
     */
    default PageResult<Map<String, Object>> pageMaps(PageRequest pageRequest, Criteria criteria) {
        return MybatisPlusPageUtils.convert(pageMaps(MybatisPlusPageUtils.convert(pageRequest),
                QueryHelper.getQueryWrapper(EntityClassCache.entityClass(getClass()), criteria)));
    }

    /**
     * 无条件翻页查询
     *
     * @param pageRequest 分页参数
     * @return PageResult
     * @see IService#pageMaps(com.baomidou.mybatisplus.core.metadata.IPage)
     */
    default PageResult<Map<String, Object>> pageMaps(PageRequest pageRequest) {
        return MybatisPlusPageUtils.convert(pageMaps(MybatisPlusPageUtils.convert(pageRequest)));
    }

    /**
     * 是否存在
     *
     * @param criteria 条件参数
     * @return boolean
     */
    default boolean exists(Criteria criteria) {
        return count(criteria) > 0;
    }

    /**
     * 修改插入
     *
     * @param entity   实体对象
     * @param criteria 条件
     * @return boolean
     * @see IService#saveOrUpdate(java.lang.Object, com.baomidou.mybatisplus.core.conditions.Wrapper)
     */
    default boolean saveOrUpdate(T entity, Criteria criteria) {
        return saveOrUpdate(entity, QueryHelper.getUpdateWrapper(EntityClassCache.entityClass(getClass()), criteria));
    }
}
