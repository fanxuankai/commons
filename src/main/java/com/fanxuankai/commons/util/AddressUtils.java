package com.fanxuankai.commons.util;

import cn.hutool.core.net.NetUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author fanxuankai
 * @deprecated {@link NetUtil}
 */
public class AddressUtils {

    /**
     * 获取本机网卡IP地址
     *
     * @return /
     * @see NetUtil#getLocalhostStr()
     */
    public static String getHostAddress() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return null;
        }
        return address.getHostAddress();
    }
}