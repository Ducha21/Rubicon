package vn.rts.customs.eclare.mapper.ctdt.giayphep;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import vn.rts.customs.eclare.dto.body.electronic_doc.TrustContractContentDto;
import vn.rts.customs.eclare.entity.ChungTuDinhKemTkEntity;
import vn.rts.customs.eclare.entity.CtdtHopDongUtEntity;
import vn.rts.customs.eclare.util.VnaccsConvert;

import java.util.Date;

@Mapper
public interface CtdtHopDongUtMapper {
	CtdtHopDongUtMapper INSTANCE = Mappers.getMapper(CtdtHopDongUtMapper.class);

	@Mappings({ @Mapping(source = "id", target = "refId"), @Mapping(source = "loaiChungTu", target = "documentType"),
			@Mapping(source = "kenhKhaiBao", target = "declarantChannel"),
			@Mapping(source = "ngayTao", target = "issueDate", qualifiedByName = "formatDateToStringDateTime"),
			@Mapping(source = "chucNangChungTu", target = "function"),
			@Mapping(source = "noiKhaiBao", target = "issueLocation"),
			@Mapping(source = "ngayTao", target = "acceptanceDate", qualifiedByName = "formatDateToStringDateTime"),
			@Mapping(source = "hqTiepNhanChungTu", target = "acceptanceOffice") })
	TrustContractContentDto chungTuDinhKemToKhaiToTrustContractContentDto(
			ChungTuDinhKemTkEntity chungTuDinhKemTkEntity);

	@Mappings({ @Mapping(source = "tenNguoiUyThac", target = "entrusteeName"),
			@Mapping(source = "maNguoiUyThac", target = "entrusteeCode"),
			@Mapping(source = "diaChiNguoiUyThac", target = "entrusteeAddress"),
			@Mapping(source = "tenNguoiNhanUyThac", target = "entrustorName"),
			@Mapping(source = "maNguoiNhanUyThac", target = "entrustorCode"),
			@Mapping(source = "diaChiNguoiNhanUyThac", target = "entrustorAddress"),
			@Mapping(source = "ghiChuKhac", target = "content"), })
	TrustContractContentDto.TrustContract ctdtHopDongUtToTrustContractContentDto(
			CtdtHopDongUtEntity ctdtHopDongUtEntity);

	@Named("formatToDate")
	public static String formatToDate(Date source) {
		return VnaccsConvert.date2String(source, VnaccsConvert.EFormatDateTime.YYYY_MM_DD.getValueEnum());
	}

	@Named("formatToDateTime")
	public static String formatToDateTime(String source) {
		return VnaccsConvert.stringDate2String(source, VnaccsConvert.EFormatDateTime.YYYY_MM_DD_H.getValueEnum());
	}
	@Named("formatDateToStringDateTime")
	public static String formatDateToStringDateTime(Date source) {
		return VnaccsConvert.date2String(source, VnaccsConvert.EFormatDateTime.YYYY_MM_DD_H.getValueEnum());
	}
}
