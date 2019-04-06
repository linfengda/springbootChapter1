package com.linfengda.sb.support.lettuce.helper;

import java.nio.charset.Charset;

/**
 * 描述: 获取指定大小字节包
 *
 * @author linfengda
 * @create 2019-02-28 16:58
 */
public class BytePackageHelper {
    private static final StringBuilder STR100 = new StringBuilder("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");

    public static byte[] getBytePackage(long len) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len/100; i++) {
            stringBuilder.append(STR100);
        }
        return stringBuilder.toString().getBytes(Charset.defaultCharset());
    }

    public static void main(String[] args) {
        System.out.println(getBytePackage(10000).length);
    }

}
