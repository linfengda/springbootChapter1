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
}
