package com.lfd.soa.srv.demo.support.redis;

import com.lfd.soa.srv.demo.support.redis.bean.MySon;
import com.lfd.soa.srv.demo.support.redis.bean.Pig;
import com.lfd.soa.srv.demo.Chapter1Application;
import com.lfd.soa.srv.demo.support.redis.bean.LruExpireResultBO;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
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
public class GenericRedisTemplateTest {
    @Resource
    private GenericRedisTemplate genericRedisTemplate;


    @Test
    public void testBean() {
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
        genericRedisTemplate.setObject("mySon", mySon);

        log.info("===================================redis反序列化");
        MySon son = genericRedisTemplate.getObject("mySon");
        log.info("内容[" + son.toString() + "]");
    }

    @Test
    public void testList() {
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
        genericRedisTemplate.listAdd("pigFamily", peggy);
        genericRedisTemplate.listAdd("pigFamily", george);
        genericRedisTemplate.listAddAll("pigFamily", pigFamily);

        log.info("===================================redis list反序列化");
        // 返回列表中指定位置的元素（不会移除列表中元素）
        List<Pig> pigs = genericRedisTemplate.listGetAll("pigFamily");
        for (Pig pig : pigs) {
            log.info(pig.toString());
        }
    }

    @Test
    public void testSet() {
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
        genericRedisTemplate.setAdd("pigFamily", peggy);
        genericRedisTemplate.setAdd("pigFamily", george);
        genericRedisTemplate.setAddAll("pigFamily", pigFamily);
        // 移除集合中一个或多个元素
        genericRedisTemplate.setDelete("pigFamily", wilson);

        log.info("===================================redis set反序列化");
        // 返回集合中的所有元素
        Set<Pig> pigs = genericRedisTemplate.setGetAll("pigFamily");
        for (Pig pig : pigs) {
            log.info(pig.toString());
        }
    }

    @Test
    public void testHash() {
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
        genericRedisTemplate.hashPut("pigFamily", "peggy", peggy);
        genericRedisTemplate.hashPut("pigFamily", "george", george);
        genericRedisTemplate.hashPut("pigFamily", "tom", tom);
        genericRedisTemplate.hashPut("pigFamily", "wilson", wilson);
        // 移除哈希表中一个或多个元素
        genericRedisTemplate.hashDel("pigFamily", "wilson");

        log.info("===================================redis hash反序列化");
        // 获取哈希表中的元素
        Pig peggyPig = genericRedisTemplate.hashGet("pigFamily", "peggy");
        log.info(peggyPig.toString());
    }

    /**
     * 测试zset scan
     */
    @Test
    public void testZsetScan() {
        genericRedisTemplate.opsForZSet().add(Constant.DEFAULT_LRU_RECORD_PREFIX + Constant.COLON + "key1", "aaa", 1);
        genericRedisTemplate.opsForZSet().add(Constant.DEFAULT_LRU_RECORD_PREFIX + Constant.COLON + "key1", "bbb", 2);
        genericRedisTemplate.opsForZSet().add(Constant.DEFAULT_LRU_RECORD_PREFIX + Constant.COLON + "key2", "aaa", 1);
        genericRedisTemplate.opsForZSet().add(Constant.DEFAULT_LRU_RECORD_PREFIX + Constant.COLON + "key2", "bbb", 2);

        // 使用scan渐进删除
        LruExpireResultBO lruExpireResultBO = genericRedisTemplate.execute(new RedisCallback<LruExpireResultBO>() {
            LruExpireResultBO lruExpireResultBO = new LruExpireResultBO();
            long startTime = System.currentTimeMillis();

            @Override
            public LruExpireResultBO doInRedis(RedisConnection connection) throws DataAccessException {

                Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(Constant.DEFAULT_LRU_RECORD_PREFIX + Constant.ASTERISK).count(10).build());
                while(cursor.hasNext()) {
                    String lruKey = new String(cursor.next());
                    genericRedisTemplate.opsForZSet().removeRangeByScore(lruKey, 0, (double) System.currentTimeMillis());
                    log.info("批量清理LRU缓存记录，position={}，lruKey={}", cursor.getPosition(), lruKey);

                    /*long offset = 0L;
                    while(true) {
                        Set<Object> expireKeys = simpleRedisTemplate.opsForZSet().rangeByScore(lruKey, 0, CacheUtil.getKeyLruScore(), offset, 1000);
                        if (CollectionUtils.isEmpty(expireKeys)) {
                            simpleRedisTemplate.delete(lruKey);
                            break;
                        }
                        simpleRedisTemplate.opsForZSet().remove(lruKey, expireKeys.toArray());
                        log.info("批量清除LRU缓存记录，lruKey={}，expireKeys={}", lruKey, JSON.toJSON(expireKeys));
                        offset += expireKeys.size()
                    }*/
                    lruExpireResultBO.addLruKeyNum();
                }

                lruExpireResultBO.setCostTime(System.currentTimeMillis()-startTime);
                return lruExpireResultBO;
            }
        });
        log.info(lruExpireResultBO.getExpireMsg());
    }
}
