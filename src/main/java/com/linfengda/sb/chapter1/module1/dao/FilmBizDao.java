package com.linfengda.sb.chapter1.module1.dao;

import com.linfengda.sb.chapter1.module1.entity.vo.FilmPlacardInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FilmBizDao {

    List<FilmPlacardInfo> queryFilmPlacardInfo(@Param("actorName") String actorName,
                                               @Param("category") String category,
                                               @Param("language") String language);
}

