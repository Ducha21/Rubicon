package vn.rts.customs.eclare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import vn.rts.customs.eclare.dto.body.OtherTaxsDto;
import vn.rts.customs.eclare.entity.ThueKhoanThuKhacEntity;

@Mapper
public interface ThueKhoanThuKhacMapper {
	ThueKhoanThuKhacMapper INSTANCE = Mappers.getMapper(ThueKhoanThuKhacMapper.class);
	
	@Mappings({
		@Mapping(source="thueMst",target="typeCode"),
		@Mapping(source="maMienThue",target="exemptionOtherCode"),
		@Mapping(source="soTienGiamThue",target="reductionOtherTaxAmount")
	})
	OtherTaxsDto thueKhoanThuKhacToOtherTaxsDto(ThueKhoanThuKhacEntity thueKhoanThuKhacEntity);
}
