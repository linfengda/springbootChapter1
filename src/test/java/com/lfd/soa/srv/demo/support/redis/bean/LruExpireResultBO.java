package com.lfd.soa.srv.demo.support.redis.bean;

import lombok.Data;

/**
 * @description lru key淘汰结果BO
 * @author linfengda
 * @date 2020-07-24 10:14
 */
@Data
public class LruExpireResultBO {
    /**
     * 淘汰的lru key总数
     */
    private Long lruKeyNum;
    /**
     * 淘汰的key总数
     */
    private Long keyNum;
    /**
     * 淘汰耗时
     */
    private Long costTime;

    public LruExpireResultBO() {
        this.lruKeyNum = 0L;
        this.keyNum = 0L;
        this.costTime = 0L;
    }

    /**
     * 淘汰的lru key总数+1
     */
    public void addLruKeyNum() {
        this.lruKeyNum++;
    }

    /**
     * 淘汰的key总数+1
     */
    public void addKeyNum() {
        this.keyNum++;
    }

    public String getExpireMsg() {
        return "共计淘汰lru key：" + this.lruKeyNum + "，key：" + this.keyNum + "，耗时：" + this.costTime + "毫秒";
    }
}
