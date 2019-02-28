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
    public void testRedisConnection(RequestParam params) throws BusinessException {
        Long count = params.getLong("count");
        log.info("测试{}次redis请求", count);
        testStringCommand(count);
    }

    //@Async("myExecutorPool")
    public void testStringCommand(Long count) {
        long setTime = 0;
        long getTime = 0;
        long delTime = 0;
        for (int i = 0; i < count; i++) {
            long t0 = System.currentTimeMillis();
            simpleRedisTemplate.setObject("key", "value");
            long t1 = System.currentTimeMillis();
            simpleRedisTemplate.getObject("key", String.class);
            long t2 = System.currentTimeMillis();
            simpleRedisTemplate.deleteObject("key");
            long t3 = System.currentTimeMillis();
            setTime += t1-t0;
            getTime += t2-t1;
            delTime += t3-t2;
        }
        log.info("------------------------------------------------<string command> set operation average time={}ms", setTime/count);
        log.info("------------------------------------------------<string command> get operation average time={}ms", getTime/count);
        log.info("------------------------------------------------<string command> del operation average time={}ms", delTime/count);
    }
}
