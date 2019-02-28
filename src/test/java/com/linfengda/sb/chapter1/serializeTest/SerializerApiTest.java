package com.linfengda.sb.chapter1.serializeTest;

import com.linfengda.sb.chapter1.serializeTest.entity.MySecondSon;
import com.linfengda.sb.chapter1.serializeTest.entity.MySon;
import com.linfengda.sb.chapter1.serializeTest.entity.Pig;
import com.linfengda.sb.support.middleware.redis.ProtoStuffUtil;
import com.linfengda.sb.support.middleware.redis.SimpleRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * 描述: 序列化器api支持度测试
 *
 * @author linfengda
 * @create 2019-01-25 9:41
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SerializerApiTest {

    @Resource
    private SimpleRedisTemplate simpleRedisTemplate;

    @Test
    public void runTest() {
        try {
            //JavaBeanSerializeTest();
            //ListSerializeTest();
            //SetSerializeTest();
            //HashSerializeTest();
            //ProtoStuffSerializeTest();

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
        simpleRedisTemplate.setObject("mySon", mySon);

        log.info("===================================redis反序列化");
        MySon son = simpleRedisTemplate.getObject("mySon", MySon.class);
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
        simpleRedisTemplate.listAdd("pigFamily", peggy, george);
        simpleRedisTemplate.listAddAll("pigFamily", pigFamily);

        log.info("===================================redis list反序列化");
        // 返回列表中指定位置的元素（不会移除列表中元素）
        List<Pig> pigs = simpleRedisTemplate.listGet("pigFamily", Pig.class);
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

        log.info("===================================redis list序列化");
        // 向集合中添加元素
        simpleRedisTemplate.setAdd("pigFamily", peggy, george);
        simpleRedisTemplate.setAddAll("pigFamily", pigFamily);
        // 移除集合中一个或多个元素
        simpleRedisTemplate.setDelete("pigFamily", wilson);

        log.info("===================================redis list反序列化");
        // 返回集合中的所有元素
        Set<Pig> pigs = simpleRedisTemplate.setGet("pigFamily", Pig.class);
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

        log.info("===================================redis list序列化");
        // 向哈希表中添加元素
        simpleRedisTemplate.mapPut("pigFamily", "peggy", peggy);
        simpleRedisTemplate.mapPut("pigFamily", "george", george);
        simpleRedisTemplate.mapPut("pigFamily", "tom", tom);
        simpleRedisTemplate.mapPut("pigFamily", "wilson", wilson);
        // 移除哈希表中一个或多个元素
        simpleRedisTemplate.mapDelete("pigFamily", "wilson");

        log.info("===================================redis list反序列化");
        // 获取哈希表中的元素
        Pig peggyPig = simpleRedisTemplate.mapGet("pigFamily", "peggy", Pig.class);
        log.info(peggyPig.toString());
    }

    private void ProtoStuffSerializeTest() {
        try {
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
            Pig pig = new Pig();
            pig.setId(1);
            pig.setCode("Z001");
            pig.setMaster("Jack");
            pig.setWeight(100.5);
            pig.setGrowDays(100);
            mySon.setPig(pig);
            mySon.setChildPigs(null);

            Map<String, Pig> kindPigs = new HashMap<>();
            kindPigs.put("stupidPig", pig);
            kindPigs.put("moreStupidPig", pig);
            mySon.setKindPigs(kindPigs);
            mySon.setPapaName("papa");

            log.info("===================================protoStuff redis序列化");
            byte[] bytes = ProtoStuffUtil.serialize(mySon);

            log.info("===================================protoStuff redis反序列化");
            MySecondSon mySecondSon = ProtoStuffUtil.deserialize(bytes, MySecondSon.class);
            log.info("内容[" + mySecondSon.toString() + "]");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
