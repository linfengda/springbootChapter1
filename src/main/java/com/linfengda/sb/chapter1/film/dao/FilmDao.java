package com.linfengda.sb.chapter1.film.dao;

import com.linfengda.sb.chapter1.film.entity.vo.FilmPlacardVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FilmDao {

    List<FilmPlacardVO> queryFilmPlacardInfo(@Param("language") String language);
}

