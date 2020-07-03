package com.linfengda.sb.support.cache.handler.impl;

import com.lhdf.eserve.support.converter.entity.dto.MapperDataDTO;
import com.lhdf.eserve.support.converter.handler.DataMapperHandler;
import lombok.Data;

/**
 * 描述: Abstract Data Mapper Handler
 *
 * @author linfengda
 * @create 2020-06-15 14:10
 */
@Data
public abstract class AbstractDataMapperHandler implements DataMapperHandler {
    /**
     * 映射结果
     */
    private MapperDataDTO mapperDataDTO;

    public AbstractDataMapperHandler(MapperDataDTO mapperDataDTO) {
        this.mapperDataDTO = mapperDataDTO;
    }
}
