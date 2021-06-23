package com.fanxuankai.commons.extra.mybatis.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fanxuankai.commons.domain.PageRequest;
import com.fanxuankai.commons.domain.PageResult;
import com.fanxuankai.commons.extra.mybatis.util.MybatisPlusPageUtils;
import com.fanxuankai.commons.extra.mybatis.util.QueryHelper;

import java.util.List;

/**
 * DAO 接口
 *
 * @param <T> 实体类
 * @author fanxuankai
 */
public interface BaseDao<T> extends IService<T> {
    /**
     * 获取实体类类型
     *
     * @return /
     */
    default Class<T> entityClass() {
        return EntityClassCache.entityClass(getClass());
    }

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageRequest     分页参数
     * @return PageResult
     */
    default PageResult<T> page(Object criteria, PageRequest pageRequest) {
        return MybatisPlusPageUtils.convert(page(MybatisPlusPageUtils.convert(pageRequest),
                QueryHelper.getQueryWrapper(entityClass(), criteria)));
    }

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     */
    default List<T> list(Object criteria) {
        return list(QueryHelper.getQueryWrapper(entityClass(), criteria));
    }

    /**
     * 查询数量
     *
     * @param criteria 条件参数
     * @return int
     */
    default int count(Object criteria) {
        return count(QueryHelper.getQueryWrapper(entityClass(), criteria));
    }

    /**
     * 是否存在
     *
     * @param criteria 条件参数
     * @return boolean
     */
    default boolean exists(Object criteria) {
        return count(criteria) > 0;
    }

    /**
     * 更新
     *
     * @param t        实体类
     * @param criteria 条件参数
     * @return 是否更新成功
     */
    default boolean update(T t, Object criteria) {
        return update(t, QueryHelper.getUpdateWrapper(entityClass(), criteria));
    }

    /**
     * 删除
     *
     * @param criteria 条件参数
     * @return 是否删除成功
     */
    default boolean remove(Object criteria) {
        return remove(QueryHelper.getQueryWrapper(entityClass(), criteria));
    }
}
