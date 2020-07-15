package com.linfengda.sb.chapter1.common.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 描述: 获取服务器硬件（网卡、CPU...）、ip等信息
 *
 * @author linfengda
 * @create 2018-09-12 13:40
 */
public class ServerRunTimeUtil {

    /**
     * 获取jvm实例名称
     * @return
     */
    public static String getIp() {
        try {
            InetAddress ip4 = Inet4Address.getLocalHost();
            return ip4.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取jvm实例名称
     * @return
     */
    public static String getJvmName() {
        RuntimeMXBean mxb = ManagementFactory.getRuntimeMXBean();
        return mxb.getName();
    }

    /**
     * 获取mac地址
     * @return
     */
    public static String getMac(){
        List<String> macs = getMacIds();
        if (macs != null && macs.size() > 0){
            return macs.get(0);
        }
        return "";
    }

    private static List<String> getMacIds() {
        InetAddress ip = null;
        NetworkInterface ni = null;
        List<String> macList = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    ip = ips.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().matches("(\\d{1,3}\\.){3}\\d{1,3}")) {
                        macList.add(getMacFromBytes(ni.getHardwareAddress()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return macList;
    }

    private static String getMacFromBytes(byte[] bytes) {
        StringBuffer mac = new StringBuffer();
        byte currentByte;
        boolean first = false;
        for (byte b : bytes) {
            if (first) {
                mac.append("-");
            }
            currentByte = (byte) ((b & 240) >> 4);
            mac.append(Integer.toHexString(currentByte));
            currentByte = (byte) (b & 15);
            mac.append(Integer.toHexString(currentByte));
            first = true;
        }
        return mac.toString().toUpperCase();
    }
}
