package com.linfengda.sb.chapter1.order;

import com.linfengda.sb.chapter1.Chapter1Application;
import com.linfengda.sb.chapter1.entity.dto.BigBomMaterialQueryDTO;
import com.linfengda.sb.chapter1.remote.PlmRemote;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @description
 * @author linfengda
 * @date 2020-09-22 15:11
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Chapter1Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlmRemoteTest {
    @Resource
    private PlmRemote plmRemote;

    /**
     * 测试feign post请求
     */
    @Test
    public void testGetOrderBigBomMaterials() {
        BigBomMaterialQueryDTO bigBomMaterialQueryDTO = new BigBomMaterialQueryDTO();
        bigBomMaterialQueryDTO.setSku("DK1234");
        bigBomMaterialQueryDTO.setVersion(1);
        log.info("请求plm订单大货物料信息，返回：{}", plmRemote.getOrderBigBomMaterials(bigBomMaterialQueryDTO));
    }

    /**
     * 测试feign get请求
     */
    @Test
    public void getMaterialPrice() {
        log.info("请求plm物料单价，返回：{}", plmRemote.getMaterialPrice("DK1234"));
    }
}
