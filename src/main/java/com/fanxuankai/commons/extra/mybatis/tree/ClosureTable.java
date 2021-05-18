package com.fanxuankai.commons.extra.mybatis.tree;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanxuankai.commons.util.IdUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * 闭包表
 *
 * @author fanxuankai
 */
public class ClosureTable {
    public interface Entity extends BaseEntity {
        /**
         * id
         *
         * @param id /
         */
        void setId(Long id);

        /**
         * 祖先
         *
         * @return /
         */
        Long getAncestor();

        /**
         * 祖先
         *
         * @param ancestor /
         */
        void setAncestor(Long ancestor);

        /**
         * 后代
         *
         * @return /
         */
        Long getDescendant();

        /**
         * 后代
         *
         * @param descendant /
         */
        void setDescendant(Long descendant);

        /**
         * 深度
         *
         * @return /
         */
        Integer getDepth();

        /**
         * 深度
         *
         * @param depth /
         */
        void setDepth(Integer depth);
    }

    public interface Dao<T extends Entity> extends TreeDao<T> {
        /**
         * 祖先(ancestor)节点：A是所有节点的祖先，F是K与L的祖先。
         *
         * @param id 节点 id
         * @return /
         */
        @Override
        default List<T> ancestors(Long id) {
            List<T> list = list(Wrappers.lambdaQuery(entityClass())
                    .ne(T::getAncestor, id)
                    .eq(T::getDescendant, id));
            list.sort(Comparator.comparing(Entity::getDepth));
            return list;
        }

        /**
         * 子孙(descendant)节点：所有节点是A的子孙，K与L是F的子孙。
         *
         * @param id 节点 id
         * @return /
         */
        @Override
        default List<Descendant<T>> descendants(Long id) {
            List<T> list = list(Wrappers.lambdaQuery(entityClass())
                    .eq(T::getAncestor, id)
                    .ne(T::getDescendant, id));
            return ClosureTableUtils.buildDescendants(id, list);
        }

        /**
         * 父节点(parent node)：B直接连到E与F且只差一个阶度，则B为E与F的父节点
         *
         * @param id 节点 id
         * @return T
         */
        @Override
        default T parent(Long id) {
            return getOne(Wrappers.lambdaQuery(entityClass())
                    .eq(T::getDescendant, id)
                    .eq(T::getDepth, 1), false);
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
                    .eq(T::getAncestor, id)
                    .eq(T::getDepth, 1));
        }

        /**
         * 根节点(root node)：没有父节点的节点，为树的源头。 如：A。
         *
         * @param wrapper /
         * @return /
         */
        @Override
        default List<T> roots(LambdaQueryWrapper<T> wrapper) {
            return list(wrapper.eq(T::getDepth, 0));
        }

        /**
         * 插入新节点
         *
         * @param node 节点
         */
        @Override
        default void insertNode(T node) {
            if (node.getDescendant() == null) {
                throw new NullPointerException("descendant 不能为空");
            }
            if (node.getAncestor() == null
                    || Objects.equals(node.getAncestor(), node.getDescendant())) {
                node.setAncestor(node.getDescendant());
                node.setDepth(0);
                save(node);
                return;
            }
            List<T> ancestors = ancestors(node.getAncestor());
            ancestors.forEach(o -> {
                o.setId(IdUtils.nextId());
                o.setDescendant(node.getDescendant());
                o.setDepth(o.getDepth() + 1);
            });
            node.setDepth(1);
            ancestors.add(node);
            saveBatch(ancestors);
        }

        /**
         * 移动节点
         *
         * @param id        节点 id
         * @param targetPid 目标父节点 id
         */
        @Override
        @Transactional(rollbackFor = Exception.class)
        default void moveNode(Long id, Long targetPid) {
            // 移除与旧祖先的关联关系
            remove(Wrappers.lambdaQuery(entityClass())
                    .eq(T::getDescendant, id));

            // 创建与新祖先的关联关系
            List<T> ancestors = ancestors(targetPid);
            ancestors.forEach(o -> {
                o.setId(IdUtils.nextId());
                o.setDescendant(id);
                o.setDepth(o.getDepth() + 1);
            });
            T node = ReflectUtil.newInstance(entityClass());
            node.setId(IdUtils.nextId());
            node.setAncestor(targetPid);
            node.setDescendant(id);
            node.setDepth(1);
            ancestors.add(node);
            saveBatch(ancestors);

            // 子节点跟随移动触发更新与祖先的关联关系
            children(id).forEach(o -> moveNode(o.getDescendant(), o.getAncestor()));
        }

        /**
         * 删除节点
         *
         * @param id               节点 id
         * @param removeDescendant 是否删除子孙节点
         */
        @Override
        default void removeNode(Long id, boolean removeDescendant) {
            // 移除与祖先的关联关系
            remove(Wrappers.lambdaQuery(entityClass())
                    .eq(T::getDescendant, id));
            // 移除与后代的关联关系
            remove(Wrappers.lambdaQuery(entityClass())
                    .eq(T::getAncestor, id));
            if (removeDescendant) {
                // 删除子节点
                children(id).forEach(o -> removeNode(o.getDescendant(), true));
            }
        }
    }
}