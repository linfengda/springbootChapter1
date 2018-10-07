package com.linfengda.sb.chapter1.module1.api;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.linfengda.sb.chapter1.module1.entity.vo.FilmPlacardInfo;
import com.linfengda.sb.chapter1.module1.service.FilmBizService;
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
    private FilmBizService filmBizService;

    @PostMapping("/film/queryFilmList")
    public Result queryFilmList() throws Exception {

        // 从context中获取封装的请求参数
         RequestParam params = getParams();
        // 使用spring提供的断言
        Assert.notNull(params.getInteger("pageNo"), "分页页码必须为数字且不能为空");
        Assert.notNull(params.getInteger("pageSize"), "分页大小必须为数字且不能为空");

        Page<FilmPlacardInfo> filmPlacardInfoPage = filmBizService.queryFilmPlacardInfo(params);
        return new Result(filmPlacardInfoPage);
    }


}
