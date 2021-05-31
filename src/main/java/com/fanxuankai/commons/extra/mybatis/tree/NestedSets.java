package com.fanxuankai.commons.extra.mybatis.tree;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fanxuankai
 */
public class NestedSets {
    public interface Entity extends DefaultEntity {
        /**
         * 左编码
         *
         * @return /
         */
        Long getLft();

        /**
         * (non-Javadoc)
         *
         * @param lft 左编码
         */
        void setLft(Long lft);

        /**
         * 右编码
         *
         * @return /
         */
        Long getRgt();

        /**
         * (non-Javadoc)
         *
         * @param rgt 右编码
         */
        void setRgt(Long rgt);
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
            T node = getById(id);
            return list(Wrappers.lambdaQuery(entityClass())
                    .lt(T::getLft, node.getLft())
                    .gt(T::getRgt, node.getRgt())
                    .orderByAsc(T::getLft));
        }

        /**
         * 插入新节点
         *
         * @param node 节点
         * @param pid  父节点
         */
        @Override
        @Transactional(rollbackFor = Exception.class)
        default void insertNode(T node, Long pid) {
            if (pid == null) {
                node.setLft(1L);
                node.setRgt(2L);
                node.setPid(null);
            } else {
                Class<T> entityClass = entityClass();
                ColumnCache leftColumnCache = TreeUtils.getColumnCache(entityClass, T::getLft);
                ColumnCache rightColumnCache = TreeUtils.getColumnCache(entityClass, T::getRgt);
                String leftColumn = leftColumnCache.getColumnSelect();
                String rightColumn = rightColumnCache.getColumnSelect();
                T parent = getById(pid);
                Long left = parent.getLft();
                String updateLeftSql = String.format("%s = %s + 2", leftColumn, leftColumn);
                update(Wrappers.lambdaUpdate(entityClass).setSql(updateLeftSql).gt(T::getLft, left));
                String updateRightSql = String.format("%s = %s + 2", rightColumn, rightColumn);
                update(Wrappers.lambdaUpdate(entityClass).setSql(updateRightSql).gt(T::getRgt, left));
                node.setLft(left + 1);
                node.setRgt(left + 2);
                node.setPid(pid);
            }
            save(node);
        }

        /**
         * 删除节点
         *
         * @param id               节点 id
         * @param removeDescendant 是否删除子孙节点
         */
        @Override
        default void removeNode(Long id, boolean removeDescendant) {
            // 暂不支持
        }

        /**
         * 移动节点
         *
         * @param id        节点 id
         * @param targetPid 目标父节点 id
         */
        @Override
        default void moveNode(Long id, Long targetPid) {
            // 暂不支持
        }
    }
}