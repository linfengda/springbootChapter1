package com.linfengda.sb.chapter1.sakila.dao;

import com.linfengda.sb.chapter1.sakila.entity.vo.FilmPlacardInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SakilaBizDao {

    List<FilmPlacardInfo> queryFilmPlacardInfo(@Param("actorName") String actorName,
                                               @Param("category") String category,
                                               @Param("language") String language);
}

