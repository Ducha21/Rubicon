package vn.rts.customs.eclare.mapper.ctdt.giayphep;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import vn.rts.customs.eclare.dto.body.electronic_doc.SpecializedCersContentDto;
import vn.rts.customs.eclare.entity.ChungTuDinhKemTkEntity;
import vn.rts.customs.eclare.entity.CtdtGiayCnktcnEntity;
import vn.rts.customs.eclare.request.ctdt.CtdtGiayCnktcnRequests;
import vn.rts.customs.eclare.util.VnaccsConvert;

import java.util.Date;

@Mapper
public interface CtdtGiayCnktcnMapper {
	CtdtGiayCnktcnMapper INSTANCE = Mappers.getMapper(CtdtGiayCnktcnMapper.class);

	@Mappings({ @Mapping(source = "id", target = "refId"), @Mapping(source = "loaiChungTu", target = "documentType"),
			@Mapping(source = "kenhKhaiBao", target = "declarantChannel"),
			@Mapping(source = "ngayTao", target = "issueDate", qualifiedByName = "formatDateToStringDateTime"),
			@Mapping(source = "chucNangChungTu", target = "function"),
			@Mapping(source = "noiKhaiBao", target = "issueLocation"),
			@Mapping(source = "ngayTao", target = "acceptanceDate", qualifiedByName = "formatDateToStringDateTime"),
			@Mapping(source = "hqTiepNhanChungTu", target = "acceptanceOffice") })
	SpecializedCersContentDto chungTuDinhKemTkToSpecializedCersContentDto(
			ChungTuDinhKemTkEntity chungTuDinhKemTkEntity);

	@Mappings({ @Mapping(source = "maPlGiayCnktcn", target = "typeCode"),
			@Mapping(source = "loaiGiayCnktcn", target = "type"),
			@Mapping(source = "tenGiayCnktcn", target = "name"),
			@Mapping(source = "soGiayCnktcn", target = "refNo"),
			@Mapping(source = "ngayGiayCnktcn", target = "issueDate", qualifiedByName = "formatToDate"),
			@Mapping(source = "ngayHetHanGiayCnktcn", target = "expireDate", qualifiedByName = "formatToDate"),
			@Mapping(source = "noiCapGiayCnktcn", target = "issueLocation"),
			@Mapping(source = "nguoiCapGiayCnktcn", target = "issuer"),
			@Mapping(source = "ghiChuKhac", target = "content") })
	SpecializedCersContentDto.SpecializedCers ctdtGiayCnktcnEntityToSpecializedCers(
			CtdtGiayCnktcnEntity ctdtGiayCnktcnEntity);

	@Mappings({ @Mapping(source = "soThuTuHang", target = "sequenceNo"), 
		@Mapping(source = "maSoHangHoa", target = "hsCode"), 
		@Mapping(source = "tenHangHoa", target = "itemName"),
		@Mapping(source = "soLuong", target = "quantity"),
		@Mapping(source = "donViTinh", target = "quantityUnit"), 
		@Mapping(source = "triGia", target = "statisticalValue"),
		@Mapping(source = "nguyenTe", target = "currencyCode"),
		@Mapping(source = "ghiChuKhac", target = "content") })
	SpecializedCersContentDto.GoodItem ctdtThongTinHangHoaRequestToGoodsItems(CtdtGiayCnktcnRequests.CtdtThongTinHangHoaRequest ctdtThongTinHangHoaRequest);

	
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
