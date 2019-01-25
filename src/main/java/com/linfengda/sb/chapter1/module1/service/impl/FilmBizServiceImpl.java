package com.linfengda.sb.chapter1.module1.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.linfengda.sb.chapter1.module1.dao.FilmBizDao;
import com.linfengda.sb.chapter1.module1.entity.vo.FilmPlacardInfo;
import com.linfengda.sb.chapter1.module1.service.FilmBizService;
import com.linfengda.sb.common.exception.BusinessException;
import com.linfengda.sb.support.api.entity.RequestParam;
import com.linfengda.sb.support.middleware.redis.SimpleRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述: 电影信息业务相关
 *
 * @author linfengda
 * @create 2018-08-14 15:59
 */
@Service
@Slf4j
public class FilmBizServiceImpl implements FilmBizService {
    @Resource
    private FilmBizDao filmBizDao;
    @Resource
    private SimpleRedisTemplate simpleRedisTemplate;

    @Override
    public Page<FilmPlacardInfo> queryFilmPlacardInfo(RequestParam params) {
        PageHelper.startPage(params.getInteger("pageNo"), params.getInteger("pageSize"));
        Page<FilmPlacardInfo> page = (Page) filmBizDao.queryFilmPlacardInfo(params.getString("actorName"), params.getString("category"), params.getString("language"));
        return page;
    }

    @Override
    public void testRedisConnection() throws BusinessException {
        int count = 100;
        for (int i=0; i<count; i++) {
            doTestRedis();
        }
        log.info("{}个测试线程启动完毕", count);
    }

    @Async("myExecutorPool")
    public void doTestRedis() {
        long t1 = System.currentTimeMillis();
        simpleRedisTemplate.mapPut("a", "b", "123");
        long t2 = System.currentTimeMillis();
        log.info("------------------------------------------------setTime={}", t2-t1);
        System.out.println("------------------------------------------------" + simpleRedisTemplate.mapGet("a", "b", String.class));
        long t3 = System.currentTimeMillis();
        log.info("------------------------------------------------getTime={}", t3-t2);
    }
}
