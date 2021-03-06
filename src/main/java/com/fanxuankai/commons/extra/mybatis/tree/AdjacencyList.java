package com.fanxuankai.commons.extra.mybatis.tree;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author fanxuankai
 */
public class AdjacencyList {
    public interface Entity extends DefaultEntity {
    }

    public interface Dao<T extends Entity> extends DefaultTreeDao<T> {
        /**
         * 祖先(ancestor)节点：A是所有节点的祖先，F是K与L的祖先。
         *
         * @param id 节点 id
         * @return /
         */
        @Override
        default List<T> ancestors(Long id) {
            Long pid = getById(id).getPid();
            if (pid == null) {
                return Collections.emptyList();
            }
            List<T> list = new ArrayList<>();
            while (pid != null) {
                T parent = getById(pid);
                if (parent == null) {
                    break;
                }
                list.add(parent);
                pid = parent.getPid();
            }
            return list;
        }

        /**
         * 父节点(parent node)：B直接连到E与F且只差一个阶度，则B为E与F的父节点
         *
         * @param id 节点 id
         * @return /
         */
        @Override
        default T parent(Long id) {
            Long pid = getById(id).getPid();
            if (pid == null) {
                return null;
            }
            return getById(pid);
        }

        /**
         * 子节点(children node)：B直接连到E与F且只差一个阶度，则E与F为B的子节点。
         *
         * @param id 节点 id
         * @return /
         */
        @Override
        default List<T> children(Long id) {
            return list(Wrappers.lambdaQuery(entityClass())
                    .eq(T::getPid, id));
        }

        /**
         * 根节点(root node)：没有父节点的节点，为树的源头。 如：A。
         *
         * @param wrapper /
         * @return /
         */
        @Override
        default List<T> roots(LambdaQueryWrapper<T> wrapper) {
            return list(wrapper.isNull(T::getPid));
        }

        /**
         * 插入新节点
         *
         * @param node 节点
         * @param pid  父节点
         */
        @Override
        default void insertNode(T node, Long pid) {
            node.setPid(pid);
            save(node);
        }

        /**
         * 删除节点
         *
         * @param id               节点 id
         * @param removeDescendant 是否删除子孙节点
         */
        @Override
        @Transactional(rollbackFor = Exception.class)
        default void removeNode(Long id, boolean removeDescendant) {
            removeById(id);
            if (removeDescendant) {
                children(id).forEach(o -> removeNode(o.getId(), true));
            } else {
                Class<T> entityClass = entityClass();
                ColumnCache pidColumnCache = TreeUtils.getColumnCache(entityClass, T::getPid);
                update(Wrappers.lambdaUpdate(entityClass).eq(T::getPid, id)
                        .setSql(pidColumnCache.getColumnSelect() + " = null"));
            }
        }

        /**
         * 移动节点
         *
         * @param id        节点 id
         * @param targetPid 目标父节点 id
         */
        @Override
        default void moveNode(Long id, Long targetPid) {
            Class<T> entityClass = entityClass();
            T entity = ReflectUtil.newInstance(entityClass);
            entity.setPid(targetPid);
            update(entity, Wrappers.lambdaUpdate(entityClass).eq(T::getId, id));
        }
    }
}