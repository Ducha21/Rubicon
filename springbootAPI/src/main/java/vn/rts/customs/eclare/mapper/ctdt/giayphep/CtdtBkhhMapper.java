package vn.rts.customs.eclare.mapper.ctdt.giayphep;

import java.util.Date;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import vn.rts.customs.eclare.dto.body.electronic_doc.GoodsDetailContentDto;
import vn.rts.customs.eclare.entity.ChungTuDinhKemTkEntity;
import vn.rts.customs.eclare.entity.CtdtBkhhEntity;
import vn.rts.customs.eclare.util.VnaccsConvert;

@Mapper
public interface CtdtBkhhMapper {
	CtdtBkhhMapper INSTANCE = Mappers.getMapper(CtdtBkhhMapper.class);

	@Mappings({ @Mapping(source = "soBangKeChiTiet", target = "refNo"),
			@Mapping(source = "ngayPhatHanh", target = "issueDate" , qualifiedByName = "formatToDate"),
			@Mapping(source = "tongSoLuongMatHang", target = "totalItems"),
			@Mapping(source = "tongSoLuongKienHang", target = "cargoPiece"),
			@Mapping(source = "maDvtSoLuongKien", target = "pieceUnitCode"),
			@Mapping(source = "ghiChuKhac", target = "content") })
	GoodsDetailContentDto.GoodsDetail ctdtCoToCertificateOfOriginContentDto(CtdtBkhhEntity ctdtBkhhEntity);

	@Mappings({ @Mapping(source = "id", target = "refId"), @Mapping(source = "loaiChungTu", target = "documentType"),
			@Mapping(source = "kenhKhaiBao", target = "declarantChannel"),
			@Mapping(source = "ngayTao", target = "issueDate", qualifiedByName = "formatDateToStringDateTime"),
			@Mapping(source = "chucNangChungTu", target = "function"),
			@Mapping(source = "noiKhaiBao", target = "issueLocation"),
			@Mapping(source = "ngayTao", target = "acceptanceDate", qualifiedByName = "formatDateToStringDateTime"),
			@Mapping(source = "hqTiepNhanChungTu", target = "acceptanceOffice") })
	GoodsDetailContentDto chungTuDinhKemToKhaiToGoodsDetailContentDto(ChungTuDinhKemTkEntity chungTuDinhKemTkEntity);

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
