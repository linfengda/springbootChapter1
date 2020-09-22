package com.linfengda.sb.chapter1.order.client;

import com.linfengda.sb.chapter1.Chapter1Application;
import com.linfengda.sb.chapter1.order.entity.dto.BigBomMaterialQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-09-22 15:11
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Chapter1Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlmClientTest {
    @Resource
    private PlmClient plmClient;

    /**
     * 测试请求plm订单大货物料信息
     */
    @Test
    public void testGetOrderBigBomMaterials() {
        BigBomMaterialQueryDTO bigBomMaterialQueryDTO = new BigBomMaterialQueryDTO();
        bigBomMaterialQueryDTO.setSku("DK1234");
        bigBomMaterialQueryDTO.setVersion(1);
        log.info("请求plm订单大货物料信息，返回：{}", plmClient.getOrderBigBomMaterials(bigBomMaterialQueryDTO));
    }
}
