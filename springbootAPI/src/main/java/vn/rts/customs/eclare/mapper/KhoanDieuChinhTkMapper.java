package vn.rts.customs.eclare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import vn.rts.customs.eclare.dto.body.ValuationAdjustmentsDto;
import vn.rts.customs.eclare.entity.KhoanDieuChinhTkEntity;

@Mapper
public interface KhoanDieuChinhTkMapper {
    KhoanDieuChinhTkMapper INSTANCE = Mappers.getMapper(KhoanDieuChinhTkMapper.class);

    @Mappings({
            @Mapping(source = "dcTriGiaMa", target = "codeValuationTitle"),
            @Mapping(source = "dcTriGiaMaPl", target = "valuationAdjustmentCode"),
            @Mapping(source = "dcTriGiaMaNt", target = "currencyCodeEvaluated"),
            @Mapping(source = "dcTriGia", target = "evaluatedAmount"),
            @Mapping(source = "dcTriGiaTongHsPhanBo", target = "totalDistributionEevaluatedAmount"),
            @Mapping(source = "dcStt", target = "sequence")
    })
    ValuationAdjustmentsDto khoanDieuChinhTkToValuationAdjustmentsDto(KhoanDieuChinhTkEntity khoanDieuChinhTkEntity);

}
