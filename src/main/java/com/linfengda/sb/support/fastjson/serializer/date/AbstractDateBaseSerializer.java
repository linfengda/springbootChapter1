package com.linfengda.sb.support.fastjson.serializer.date;

import com.alibaba.fastjson.serializer.SerializeWriter;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-08-22 18:51
 */
public abstract class AbstractDateBaseSerializer {

    /**
     * 时间写入处理
     * @param object
     * @param out
     */
    public void writeDate(Object object, SerializeWriter out){
        if (object instanceof Timestamp){
            Timestamp timestamp = (Timestamp)object;
            doWrite(timestamp.getTime(), out);
        }else if (object instanceof Date){
            Date date = (Date)object;
            doWrite(date.getTime(), out);
        }else if (object instanceof Integer){
            Integer second = (Integer)object;
            doWrite(1000L * second, out);
        }
    }

    /**
     * 执行写操作
     * @param time
     * @param out
     */
    public abstract void doWrite(Long time, SerializeWriter out);
}
