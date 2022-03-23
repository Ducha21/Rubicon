package vn.rts.customs.eclare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import vn.rts.customs.eclare.dto.body.CancelContentDto;
import vn.rts.customs.eclare.entity.HuyToKhaiEntity;
import vn.rts.customs.eclare.util.VnaccsConvert;
import vn.rts.customs.eclare.util.VnaccsConvert.EFormatDateTime;

@Mapper
public interface TkHuyMapper {
	TkHuyMapper INSTANCE = Mappers.getMapper(TkHuyMapper.class);
	
	@Mappings({
		@Mapping(source = "noiKbHuy",target = "issueLocation"),
		@Mapping(source = "kenhKhaiBao",target = "declarantChannel"),
		@Mapping(source = "maNguoiKhaiHq",target = "declarantCode"),
		@Mapping(source = "tenNguoiKhaiHq",target = "declarantName"),
		@Mapping(source = "sdtNguoiKhaiHq",target = "declarantTel"),
		@Mapping(source = "diaChiNguoiKhaiHq",target = "declarantAddress"),
		@Mapping(source = "soTk",target = "declarationNo"),
		@Mapping(source = "maLyDoHuy",target = "requestStatement"),
		@Mapping(source = "lyDoHuy",target = "requestContent")
	})
	CancelContentDto huyToKhaiEntityToCancelContentDto(HuyToKhaiEntity huyToKhaiEntity);
	
	@Named("formatToDate")
	public static String formatToDate(String source) {
		return VnaccsConvert.stringDate2String(source, EFormatDateTime.YYYY_MM_DD.getValueEnum());
	}
	
	@Named("formatToDateTime")
	public static String formatToDateTime(String source) {
		return VnaccsConvert.stringDate2String(source, EFormatDateTime.YYYY_MM_DD_H.getValueEnum());
	}
}
