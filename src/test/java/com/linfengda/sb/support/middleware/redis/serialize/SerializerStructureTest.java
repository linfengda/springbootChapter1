package com.linfengda.sb.support.middleware.redis.serialize;

import com.linfengda.sb.chapter1.Chapter1Application;
import com.linfengda.sb.support.middleware.redis.serialize.entity.MySon;
import com.linfengda.sb.support.middleware.redis.serialize.entity.Pig;
import com.linfengda.sb.support.middleware.redis.template.JacksonRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * 描述: 序列化结构测试
 *
 * @author linfengda
 * @create 2019-01-25 9:41
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Chapter1Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SerializerStructureTest {

    @Resource
    private JacksonRedisTemplate jacksonRedisTemplate;

    @Test
    public void runTest() {
        try {
            //JavaBeanSerializeTest();
            //ListSerializeTest();
            //SetSerializeTest();
            HashSerializeTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void JavaBeanSerializeTest() {
        MySon mySon = new MySon();
        mySon.setF1(11);
        mySon.setF2(111111l);
        mySon.setF3(11.111f);
        mySon.setF4(22.222);
        mySon.setF5('a');
        mySon.setF6(false);
        mySon.setF7("my son raise many pigs.");
        mySon.setF8(new Timestamp(System.currentTimeMillis()));
        mySon.setF9(new int[]{1, 2, 3});
        mySon.setF10(new long[]{11, 22, 33});
        mySon.setF11(new float[]{1.1f, 2.2f, 3.3f});
        mySon.setF12(new double[]{11.1, 22.2, 33.3});
        mySon.setF13(new char[]{'a', 'b', 'c'});
        mySon.setF14(new boolean[]{false, false, true});
        mySon.setF15(new String[]{"Peggy", "Wilson", "George"});
        //mySon.setUpperHeadField("xxx");
        //mySon.setLowerHeadField("yyy");
        Pig pig = new Pig();
        pig.setId(1);
        pig.setCode("Z001");
        pig.setMaster("Jack");
        pig.setWeight(100.5);
        pig.setGrowDays(100);
        mySon.setPig(pig);

        List<Pig> childPigs = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Pig p = new Pig();
            p.setId(i);
            p.setCode("Z00" + i);
            p.setMaster("Jack");
            p.setWeight(100 + i);
            p.setGrowDays(100 + i);
            childPigs.add(p);
        }
        mySon.setChildPigs(childPigs);

        Map<String, Pig> kindPigs = new HashMap<>();
        kindPigs.put("stupidPig", pig);
        kindPigs.put("moreStupidPig", pig);
        mySon.setKindPigs(kindPigs);
        mySon.setPapaName("papa");

        log.info("===================================redis序列化");
        jacksonRedisTemplate.setObject("mySon", mySon);

        log.info("===================================redis反序列化");
        MySon son = jacksonRedisTemplate.getObject("mySon");
        log.info("内容[" + son.toString() + "]");
    }

    private void ListSerializeTest() {
        Pig peggy = new Pig();
        peggy.setId(1);
        peggy.setCode("peggy");
        peggy.setMaster("Jack");
        peggy.setWeight(100);
        peggy.setGrowDays(100);

        Pig george = new Pig();
        george.setId(2);
        george.setCode("george");
        george.setMaster("Jack");
        george.setWeight(150);
        george.setGrowDays(150);

        Pig tom = new Pig();
        tom.setId(3);
        tom.setCode("tom");
        tom.setMaster("Jack");
        tom.setWeight(120);
        tom.setGrowDays(120);

        Pig wilson = new Pig();
        wilson.setId(4);
        wilson.setCode("wilson");
        wilson.setMaster("Jack");
        wilson.setWeight(110);
        wilson.setGrowDays(110);

        List<Pig> pigFamily = new ArrayList<>();
        pigFamily.add(tom);
        pigFamily.add(wilson);

        log.info("===================================redis list序列化");
        jacksonRedisTemplate.listAdd("pigFamily", peggy);
        jacksonRedisTemplate.listAdd("pigFamily", george);
        jacksonRedisTemplate.listAddAll("pigFamily", pigFamily);

        log.info("===================================redis list反序列化");
        // 返回列表中指定位置的元素（不会移除列表中元素）
        List<Pig> pigs = jacksonRedisTemplate.listGet("pigFamily");
        for (Pig pig : pigs) {
            log.info(pig.toString());
        }
    }

    private void SetSerializeTest() {
        Pig peggy = new Pig();
        peggy.setId(1);
        peggy.setCode("peggy");
        peggy.setMaster("Jack");
        peggy.setWeight(100);
        peggy.setGrowDays(100);

        Pig george = new Pig();
        george.setId(2);
        george.setCode("george");
        george.setMaster("Jack");
        george.setWeight(150);
        george.setGrowDays(150);

        Pig tom = new Pig();
        tom.setId(3);
        tom.setCode("tom");
        tom.setMaster("Jack");
        tom.setWeight(120);
        tom.setGrowDays(120);

        Pig wilson = new Pig();
        wilson.setId(4);
        wilson.setCode("wilson");
        wilson.setMaster("Jack");
        wilson.setWeight(110);
        wilson.setGrowDays(110);

        List<Pig> pigFamily = new ArrayList<>();
        pigFamily.add(tom);
        pigFamily.add(wilson);

        log.info("===================================redis set序列化");
        // 向集合中添加元素
        jacksonRedisTemplate.setAdd("pigFamily", peggy);
        jacksonRedisTemplate.setAdd("pigFamily", george);
        jacksonRedisTemplate.setAddAll("pigFamily", pigFamily);
        // 移除集合中一个或多个元素
        jacksonRedisTemplate.setDelete("pigFamily", wilson);

        log.info("===================================redis set反序列化");
        // 返回集合中的所有元素
        Set<Pig> pigs = jacksonRedisTemplate.setGet("pigFamily");
        for (Pig pig : pigs) {
            log.info(pig.toString());
        }
    }

    private void HashSerializeTest() {
        Pig peggy = new Pig();
        peggy.setId(1);
        peggy.setCode("peggy");
        peggy.setMaster("Jack");
        peggy.setWeight(100);
        peggy.setGrowDays(100);

        Pig george = new Pig();
        george.setId(2);
        george.setCode("george");
        george.setMaster("Jack");
        george.setWeight(150);
        george.setGrowDays(150);

        Pig tom = new Pig();
        tom.setId(3);
        tom.setCode("tom");
        tom.setMaster("Jack");
        tom.setWeight(120);
        tom.setGrowDays(120);

        Pig wilson = new Pig();
        wilson.setId(4);
        wilson.setCode("wilson");
        wilson.setMaster("Jack");
        wilson.setWeight(110);
        wilson.setGrowDays(110);

        log.info("===================================redis hash序列化");
        // 向哈希表中添加元素
        jacksonRedisTemplate.hashPut("pigFamily", "peggy", peggy);
        jacksonRedisTemplate.hashPut("pigFamily", "george", george);
        jacksonRedisTemplate.hashPut("pigFamily", "tom", tom);
        jacksonRedisTemplate.hashPut("pigFamily", "wilson", wilson);
        // 移除哈希表中一个或多个元素
        jacksonRedisTemplate.hashDel("pigFamily", "wilson");

        log.info("===================================redis hash反序列化");
        // 获取哈希表中的元素
        Pig peggyPig = jacksonRedisTemplate.hashGet("pigFamily", "peggy");
        log.info(peggyPig.toString());
    }
}
