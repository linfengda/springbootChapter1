package com.linfengda.sb.chapter1.film.service;

import com.linfengda.sb.chapter1.film.entity.dto.UploadFilmDTO;

public interface TransactionalTestService {

    void testTransaction1(UploadFilmDTO uploadFilmDTO) throws Exception;
}
