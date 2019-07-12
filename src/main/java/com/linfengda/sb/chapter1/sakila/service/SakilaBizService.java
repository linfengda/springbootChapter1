package com.linfengda.sb.chapter1.sakila.service;

import com.github.pagehelper.Page;
import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.chapter1.sakila.entity.vo.FilmPlacardInfo;
import com.linfengda.sb.support.api.entity.RequestParam;

public interface SakilaBizService {

    /**
     * 测试mysql
     * @param params
     * @return
     * @throws BusinessException
     */
    Page<FilmPlacardInfo> queryFilmPlacardInfo(RequestParam params) throws BusinessException;

    /**
     * 上架电影
     * @param params
     * @throws Exception
     */
    void addFilm(RequestParam params) throws Exception;

    /**
     * 下架电影
     * @param params
     * @throws Exception
     */
    void delFilm(RequestParam params) throws Exception;
}
