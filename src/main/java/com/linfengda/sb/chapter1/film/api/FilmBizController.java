package com.linfengda.sb.chapter1.film.api;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.linfengda.sb.chapter1.film.entity.dto.UploadFilmDTO;
import com.linfengda.sb.chapter1.film.entity.vo.FilmPlacardVO;
import com.linfengda.sb.chapter1.film.service.FilmService;
import com.linfengda.sb.chapter1.film.service.TransactionalTestService;
import com.linfengda.sb.support.api.BaseController;
import com.linfengda.sb.support.api.entity.RequestParam;
import com.linfengda.sb.support.api.entity.Result;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 描述: 测试MVC
 *
 * @author linfengda
 * @create 2018-08-16 10:29
 */
@RestController
public class FilmBizController extends BaseController {
    @Resource
    private TransactionalTestService transactionalTestService;
    @Resource
    private FilmService filmService;

    @PostMapping("/film/queryFilmList")
    public Result queryFilmList() throws Exception {
        RequestParam params = getParams();
        Assert.notNull(params.getInteger("pageNo"), "分页页码必须为数字且不能为空");
        Assert.notNull(params.getInteger("pageSize"), "分页大小必须为数字且不能为空");
        Page<FilmPlacardVO> filmPlacardVOPage = filmService.queryFilmPlacardInfo(params);
        return new Result(filmPlacardVOPage);
    }

    @PostMapping("/film/addFilm")
    public Result addFilm() throws Exception {
        RequestParam params = getParams();
        UploadFilmDTO uploadFilmDTO = JSON.toJavaObject(params, UploadFilmDTO.class);
        transactionalTestService.testTransaction1(uploadFilmDTO);
        return SUCCESS_RESULT;
    }
}
