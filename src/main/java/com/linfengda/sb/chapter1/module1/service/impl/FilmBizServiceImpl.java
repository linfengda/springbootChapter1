package com.linfengda.sb.chapter1.module1.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.linfengda.sb.chapter1.module1.dao.FilmBizDao;
import com.linfengda.sb.chapter1.module1.entity.vo.FilmPlacardInfo;
import com.linfengda.sb.chapter1.module1.service.FilmBizService;
import com.linfengda.sb.support.api.entity.RequestParam;
import com.linfengda.sb.support.middleware.redis.SimpleRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述: 电影信息业务相关
 *
 * @author linfengda
 * @create 2018-08-14 15:59
 */
@Service
public class FilmBizServiceImpl implements FilmBizService {
    @Resource
    private FilmBizDao filmBizDao;
    @Resource
    private SimpleRedisTemplate simpleRedisTemplate;

    @Override
    public Page<FilmPlacardInfo> queryFilmPlacardInfo(RequestParam params) {

        /*simpleRedisTemplate.mapPut("a", "b", "123");
        System.out.println("-----------------------" + simpleRedisTemplate.mapGet("a", "b", String.class))*/;

        PageHelper.startPage(params.getInteger("pageNo"), params.getInteger("pageSize"));
        Page<FilmPlacardInfo> page = (Page) filmBizDao.queryFilmPlacardInfo(params.getString("actorName"), params.getString("category"), params.getString("language"));
        return page;
    }
}
