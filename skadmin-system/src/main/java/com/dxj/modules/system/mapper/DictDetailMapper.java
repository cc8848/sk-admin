package com.dxj.modules.system.mapper;

import com.dxj.mapper.EntityMapper;
import com.dxj.modules.system.domain.DictDetail;
import com.dxj.modules.system.dto.DictDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;

/**
* @author dxj
* @date 2019-04-10
*/
@Service
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictDetailMapper extends EntityMapper<DictDetailDTO, DictDetail> {

}
