package com.linfengda.sb.support.cache.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lhdf.eserve.model.entity.GantryPassData;
import com.lhdf.eserve.service.FixCsvDataService;
import com.lhdf.eserve.support.converter.core.impl.GenericDataConverter;
import com.lhdf.eserve.support.converter.entity.dto.MapperDataDTO;
import com.lhdf.eserve.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 上传ETC门架计费扣费交易记录 handler
 * @author: linfengda
 * @date: 2020-06-17 14:08
 */
@Slf4j
public class GantryPassDataMapperHandler extends AbstractDataMapperHandler {

    public GantryPassDataMapperHandler(MapperDataDTO mapperDataDTO) {
        super(mapperDataDTO);
    }

    @Override
    public List<?> doMapper() throws Exception {
        log.debug("上传ETC门架计费扣费交易记录，映射文件={}，开始映射", getMapperDataDTO().getMappingId(), getMapperDataDTO().getJsonArray().toJSONString());
        GenericDataConverter genericDataConverter = new GenericDataConverter();
        List<GantryPassData> resultList = new ArrayList<>();
        JSONArray jsonArray = getMapperDataDTO().getJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONObject resultJson = genericDataConverter.convert2Json(jsonObject, getMapperDataDTO().getMappingId());
            GantryPassData gantryPassData = genericDataConverter.convert2Obj(resultJson, GantryPassData.class);
            FixCsvDataService fixCsvDataService = SpringUtil.getBean(FixCsvDataService.class);
            fixCsvDataService.fixGantryPassData(gantryPassData);
            resultList.add(gantryPassData);
        }
        log.debug("上传ETC门架计费扣费交易记录，映射文件={}，映射完成", getMapperDataDTO().getMappingId(), JSON.toJSONString(resultList));
        return resultList;
    }
}
