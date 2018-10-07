package com.linfengda.sb.chapter1.module1.service;

import com.linfengda.sb.chapter1.module1.entity.vo.FilmPlacardInfo;

import com.github.pagehelper.Page;
import com.linfengda.sb.support.api.entity.RequestParam;

public interface FilmBizService {

    Page<FilmPlacardInfo> queryFilmPlacardInfo(RequestParam params);
}
