package com.linfengda.sb.support.lettuce.service;

import com.alibaba.fastjson.JSONObject;
import com.linfengda.sb.chapter1.film.entity.vo.FilmPlacardVO;
import com.linfengda.sb.support.lettuce.LettuceTemplate;
import com.linfengda.sb.support.lettuce.helper.LettuceTemplateHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述:
 *
 * @author linfengda
 * @create 2019-02-19 23:31
 */
@Slf4j
public class LettuceTestServiceImpl implements TestService {
    private static LettuceTemplate<String, Object> lettuceTemplate = LettuceTemplateHelper.getTemplate();

    @Override
    public void simpleStringOperation(int count) throws Exception {
        long setTime = 0;
        long getTime = 0;
        long delTime = 0;
        for (int i = 0; i < count; i++) {
            long t0 = System.currentTimeMillis();
            lettuceTemplate.set("key", "value");
            long t1 = System.currentTimeMillis();
            lettuceTemplate.get("key");
            long t2 = System.currentTimeMillis();
            lettuceTemplate.del("key");
            long t3 = System.currentTimeMillis();
            setTime += t1-t0;
            getTime += t2-t1;
            delTime += t3-t2;
        }
        log.info("------------------------------------------------<string command> set service average time={}ms", setTime/count);
        log.info("------------------------------------------------<string command> get service average time={}ms", getTime/count);
        log.info("------------------------------------------------<string command> del service average time={}ms", delTime/count);
    }

    @Override
    public void stringSetOperation() throws Exception {
        lettuceTemplate.set("key:" + Thread.currentThread().getId(), "value");
    }

    @Override
    public void stringSetGetOperation() throws Exception {
        lettuceTemplate.set("key:" + Thread.currentThread().getId(), "value");
        lettuceTemplate.get("key:" + Thread.currentThread().getId());
    }

    @Override
    public void simpleListOperation() throws Exception {
        lettuceTemplate.set("key", "i am the boss");
        String str = (String) lettuceTemplate.get("key");
        log.info(str);
        Long row = lettuceTemplate.del("key");
        log.info("delete affect row: {}", row);


        FilmPlacardVO filmPlacardVO = new FilmPlacardVO();
        filmPlacardVO.setFilmId(1);
        filmPlacardVO.setTitle("流浪地球");
        filmPlacardVO.setDescription("这是一部吴京导演的科幻片");
        filmPlacardVO.setReleaseYear("2019");
        filmPlacardVO.setLanguage("chinese");
        row = lettuceTemplate.leftPush("myList", filmPlacardVO);
        log.info("row length after leftPush: {}", row);
        filmPlacardVO = (FilmPlacardVO) lettuceTemplate.rightPop("myList");
        log.info("rightPop object: ", JSONObject.toJSONString(filmPlacardVO));
    }

    public static void main(String[] args) {
        try {
            LettuceTestServiceImpl testService = new LettuceTestServiceImpl();
            testService.simpleListOperation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
