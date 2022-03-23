package vn.rts.customs.eclare.mapper.ctdt.giayphep;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import vn.rts.customs.eclare.dto.body.electronic_doc.CertificateOfOriginContentDto;
import vn.rts.customs.eclare.entity.ChungTuDinhKemTkEntity;
import vn.rts.customs.eclare.entity.CtdtCoEntity;
import vn.rts.customs.eclare.util.VnaccsConvert;

import java.util.Date;

@Mapper
public interface CtdtCoMapper {
	CtdtCoMapper INSTANCE = Mappers.getMapper(CtdtCoMapper.class);

	@Mappings({ @Mapping(source = "soCo", target = "certNo"), @Mapping(source = "loaiCo", target = "certType"),
			@Mapping(source = "ngayCapCo", target = "certDate", qualifiedByName = "formatToDate"),
			@Mapping(source = "toChucCo", target = "certOrganization"),
			@Mapping(source = "nguoiCapCo", target = "issuer"),
			@Mapping(source = "nuocCapCo", target = "issueLocation"),
			@Mapping(source = "thoiDiemNopCo", target = "certTime"),
			@Mapping(source = "ghiChuKhac", target = "content") })
	CertificateOfOriginContentDto.CertificateOfOrigin ctdtCoToCertificateOfOriginContentDto(CtdtCoEntity ctdtCoEntity);

	@Mappings({ @Mapping(source = "id", target = "refId"), @Mapping(source = "loaiChungTu", target = "documentType"),
			@Mapping(source = "kenhKhaiBao", target = "declarantChannel"),
			@Mapping(source = "ngayTao", target = "issueDate", qualifiedByName = "formatDateToStringDateTime"),
			@Mapping(source = "chucNangChungTu", target = "function"),
			@Mapping(source = "noiKhaiBao", target = "issueLocation"),
			@Mapping(source = "ngayTao", target = "acceptanceDate", qualifiedByName = "formatDateToStringDateTime"),
			@Mapping(source = "hqTiepNhanChungTu", target = "acceptanceOffice") })
	CertificateOfOriginContentDto chungTuDinhKemToKhaiToCertificateOfOriginContentDto(
			ChungTuDinhKemTkEntity chungTuDinhKemTkEntity);

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
