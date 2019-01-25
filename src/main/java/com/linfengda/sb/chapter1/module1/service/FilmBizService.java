package com.linfengda.sb.chapter1.module1.service;

import com.github.pagehelper.Page;
import com.linfengda.sb.chapter1.module1.entity.vo.FilmPlacardInfo;
import com.linfengda.sb.common.exception.BusinessException;
import com.linfengda.sb.support.api.entity.RequestParam;

public interface FilmBizService {

    Page<FilmPlacardInfo> queryFilmPlacardInfo(RequestParam params) throws BusinessException;

    void testRedisConnection() throws BusinessException;
}
