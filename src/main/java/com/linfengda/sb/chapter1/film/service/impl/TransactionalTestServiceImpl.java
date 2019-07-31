package com.linfengda.sb.chapter1.film.service.impl;

import com.linfengda.sb.chapter1.film.entity.dto.UploadFilmDTO;
import com.linfengda.sb.chapter1.film.service.FilmService;
import com.linfengda.sb.chapter1.film.service.TransactionalTestService;
import com.linfengda.sb.support.dao.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 描述: 电影信息业务相关
 *
 * @author linfengda
 * @create 2018-08-14 15:59
 */
@Service
@Slf4j
public class TransactionalTestServiceImpl extends BaseService implements TransactionalTestService {
    @Resource
    private FilmService filmService;

    //@Transactional(rollbackFor=Exception.class)
    @Override
    public void testTransaction1(UploadFilmDTO uploadFilmDTO) throws Exception {
        Long filmId = uploadFilmDTO.getFilmId();
        filmService.updateDescription(filmId, uploadFilmDTO.getDescription());
    }
}
