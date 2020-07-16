package com.fanxuankai.commons.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author fanxuankai
 */
public class AddressUtils {
    public static String getHostAddress() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return null;
        }
        return address.getHostAddress();
    }

    public static void main(String[] args) {
        System.out.println(getHostAddress());
    }
}
