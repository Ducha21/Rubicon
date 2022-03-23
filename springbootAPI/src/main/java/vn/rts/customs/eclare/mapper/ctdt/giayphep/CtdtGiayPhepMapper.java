package vn.rts.customs.eclare.mapper.ctdt.giayphep;

import java.util.Date;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import org.mapstruct.factory.Mappers;

import vn.rts.customs.eclare.dto.body.electronic_doc.LicenseContentDto;
import vn.rts.customs.eclare.entity.ChungTuDinhKemTkEntity;
import vn.rts.customs.eclare.entity.CtdtGiayPhepEntity;
import vn.rts.customs.eclare.request.ctdt.CtdtGiayPhepRequests;
import vn.rts.customs.eclare.util.VnaccsConvert;
import vn.rts.customs.eclare.util.VnaccsConvert.EFormatDateTime;

@Mapper
public interface CtdtGiayPhepMapper {
	CtdtGiayPhepMapper INSTANCE = Mappers.getMapper(CtdtGiayPhepMapper.class);

	@Mappings({ @Mapping(source = "id", target = "refId"), @Mapping(source = "loaiChungTu", target = "documentType"),
			@Mapping(source = "kenhKhaiBao", target = "declarantChannel"),
			@Mapping(source = "ngayTao", target = "issueDate", qualifiedByName = "formatDateToStringDateTime"),
			@Mapping(source = "chucNangChungTu", target = "function"),
			@Mapping(source = "noiKhaiBao", target = "issueLocation"),
			@Mapping(source = "ngayTao", target = "acceptanceDate", qualifiedByName = "formatDateToStringDateTime"),
			@Mapping(source = "hqTiepNhanChungTu", target = "acceptanceOffice") })
	LicenseContentDto chungTuDinhKemTkToLicenseContentDto(ChungTuDinhKemTkEntity chungTuDinhKemTkEntity);

	@Mappings({ @Mapping(source = "maPlGiayPhep", target = "typeCode"),
			@Mapping(source = "loaiGiayPhep", target = "licenseType"),
			@Mapping(source = "soGiayPhep", target = "licenseNo"),
			@Mapping(source = "ngayCapGiayPhep", target = "licenseDate", qualifiedByName = "formatToDate"),
			@Mapping(source = "ngayHetHanGiayPhep", target = "expireDate", qualifiedByName = "formatToDate"),
			@Mapping(source = "noiCapGiayPhep", target = "issueLocation"),
			@Mapping(source = "nguoiCapGiayPhep", target = "issuer"),
			@Mapping(source = "ghiChuKhac", target = "content") })
	LicenseContentDto.License ctdtGiayPhepToLicenseContentDto(CtdtGiayPhepEntity ctdtGiayPhepEntity);

	@Mappings({ @Mapping(source = "soThuTuHang", target = "sequenceNo"), //ko thay
			@Mapping(source = "maSoHangHoa", target = "hsCode"), //2 ma hang
			@Mapping(source = "tenHangHoa", target = "itemName"),
			@Mapping(source = "soLuong", target = "quantity"),
			@Mapping(source = "donViTinh", target = "quantityUnit"), //2 dv tính
			@Mapping(source = "triGia", target = "statisticalValue"),
			@Mapping(source = "nguyenTe", target = "currencyCode"),
			@Mapping(source = "ghiChuKhac", target = "content") })
	LicenseContentDto.GoodItem ctdtThongTinHangHoaRequestToGoodsItems(CtdtGiayPhepRequests.CtdtThongTinHangHoaRequest ctdtThongTinHangHoaRequest);
	
	@Named("formatToDate")
	public static String formatToDate(Date source) {
		return VnaccsConvert.date2String(source, EFormatDateTime.YYYY_MM_DD.getValueEnum());
	}

	@Named("formatToDateTime")
	public static String formatToDateTime(String source) {
		return VnaccsConvert.stringDate2String(source, EFormatDateTime.YYYY_MM_DD_H.getValueEnum());
	}
	@Named("formatDateToStringDateTime")
	public static String formatDateToStringDateTime(Date source) {
		return VnaccsConvert.date2String(source, VnaccsConvert.EFormatDateTime.YYYY_MM_DD_H.getValueEnum());
	}
}
