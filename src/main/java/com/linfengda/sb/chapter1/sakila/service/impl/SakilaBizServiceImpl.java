package com.linfengda.sb.chapter1.sakila.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.linfengda.sb.chapter1.sakila.dao.SakilaBizDao;
import com.linfengda.sb.chapter1.sakila.entity.vo.FilmPlacardInfo;
import com.linfengda.sb.chapter1.sakila.service.SakilaBizService;
import com.linfengda.sb.support.api.entity.RequestParam;
import com.linfengda.sb.support.dao.BaseService;
import com.linfengda.sb.support.middleware.redis.SimpleRedisTemplate;
import lombok.extern.slf4j.Slf4j;
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
public class SakilaBizServiceImpl extends BaseService implements SakilaBizService {
    @Resource
    private SakilaBizDao sakilaBizDao;
    @Resource
    private SimpleRedisTemplate simpleRedisTemplate;

    @Override
    public Page<FilmPlacardInfo> queryFilmPlacardInfo(RequestParam params) {
        PageHelper.startPage(params.getInteger("pageNo"), params.getInteger("pageSize"));
        Page<FilmPlacardInfo> page = (Page) sakilaBizDao.queryFilmPlacardInfo(params.getString("actorName"), params.getString("category"), params.getString("language"));
        return page;
    }
}
