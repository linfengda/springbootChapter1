package com.linfengda.sb.chapter1.redis.service;

import com.alibaba.fastjson.JSONObject;
import com.linfengda.sb.chapter1.redis.helper.LettuceTemplateHelper;
import com.linfengda.sb.chapter1.redis.lettuce.LettuceTemplate;
import com.linfengda.sb.chapter1.system.entity.vo.UserListVO;
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


        UserListVO userListVO = new UserListVO();
        userListVO.setUserId(1L);
        userListVO.setUserName("流浪地球");
        row = lettuceTemplate.leftPush("myList", userListVO);
        log.info("row length after leftPush: {}", row);
        userListVO = (UserListVO) lettuceTemplate.rightPop("myList");
        log.info("rightPop object: ", JSONObject.toJSONString(userListVO));
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
