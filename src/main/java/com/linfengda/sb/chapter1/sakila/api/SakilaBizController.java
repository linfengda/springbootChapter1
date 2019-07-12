package com.linfengda.sb.chapter1.sakila.api;

import com.github.pagehelper.Page;
import com.linfengda.sb.chapter1.sakila.entity.vo.FilmPlacardInfo;
import com.linfengda.sb.chapter1.sakila.service.SakilaBizService;
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
public class SakilaBizController extends BaseController {

    @Resource
    private SakilaBizService sakilaBizService;

    @PostMapping("/film/queryFilmList")
    public Result queryFilmList() throws Exception {
        RequestParam params = getParams();
        Assert.notNull(params.getInteger("pageNo"), "分页页码必须为数字且不能为空");
        Assert.notNull(params.getInteger("pageSize"), "分页大小必须为数字且不能为空");

        Page<FilmPlacardInfo> filmPlacardInfoPage = sakilaBizService.queryFilmPlacardInfo(params);
        return new Result(filmPlacardInfoPage);
    }

    @PostMapping("/film/addFilm")
    public Result addFilm() throws Exception {
        RequestParam params = getParams();
        sakilaBizService.addFilm(params);
        return SUCCESS_RESULT;
    }
}
