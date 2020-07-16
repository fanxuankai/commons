package com.fanxuankai.commons.util;

import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import com.dangdang.ddframe.rdb.sharding.id.generator.self.CommonSelfIdGenerator;

/**
 * @author fanxuankai
 */
public class DangDangIdGenerator {

    private static final IdGenerator ID_GENERATOR = new CommonSelfIdGenerator();

    public static IdGenerator getInstance() {
        return ID_GENERATOR;
    }

    public static Number generateId() {
        return ID_GENERATOR.generateId();
    }

}
