package com.fanxuankai.commons.extra.mybatis.util;

import cn.hutool.core.io.resource.NoResourceException;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @author fanxuankai
 */
public class MetadataUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataUtils.class);

    /**
     * 如果有必要则修改元数据,包括库名、表名、字段名等信息
     * 需要在 resources 目录下创建 table-info.json 文件
     * 格式为 com.fanxuankai.commons.extra.mybatis.util.MetadataUtils.MetaData 数组
     */
    public static void modifyMetadataIfNecessary() {
        try {
            String json = ResourceUtil.readStr("table-info.json", Charset.defaultCharset());
            List<Metadata> metadataList = JSONUtil.toList(json, Metadata.class);
            for (Metadata metadata : metadataList) {
                Class<?> entityClass = Class.forName(metadata.getClassName());
                AnnotationUtils.modifyTableInfo(entityClass, metadata.getSchemaName(),
                        metadata.getTableName());
                AnnotationUtils.modifyFieldInfo(entityClass, metadata.getFieldMap());
            }
        } catch (NoResourceException e) {
            LOGGER.info("not found table-info.json");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Metadata {
        private String className;
        private String schemaName;
        private String tableName;
        private Map<String, String> fieldMap;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getSchemaName() {
            return schemaName;
        }

        public void setSchemaName(String schemaName) {
            this.schemaName = schemaName;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public Map<String, String> getFieldMap() {
            return fieldMap;
        }

        public void setFieldMap(Map<String, String> fieldMap) {
            this.fieldMap = fieldMap;
        }
    }
}
