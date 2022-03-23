package vn.rts.customs.eclare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import vn.rts.customs.eclare.dto.body.ImportContentDto;
import vn.rts.customs.eclare.entity.MauToKhaiHqEntity;
import vn.rts.customs.eclare.util.VnaccsConvert;
import vn.rts.customs.eclare.util.VnaccsConvert.EFormatDateTime;

import java.util.Date;

@Mapper
public interface TknToContentDtoMapper {
	TknToContentDtoMapper INSTANCE = Mappers.getMapper(TknToContentDtoMapper.class);

	@Mappings({
			/* Thong tin chung mapping */
			@Mapping(source = "id", target = "refId"), @Mapping(source = "soTiepNhan", target = "refCustomsId"),
			@Mapping(source = "kenhKhaiBao", target = "declarantChannel"),
			@Mapping(source = "maNguoiKhaiHq", target = "declarantCode"),
			@Mapping(source = "tenNguoiKhaiHq", target = "declarantName"),
			@Mapping(source = "haiQuanTiepNhanKb", target = "acceptanceOffice"),
			@Mapping(source = "haiQuanTiepNhanKb", target = "declarationOffice"),
			@Mapping(source = "ngayTao", target = "issueDate", qualifiedByName = "formatDateToStringDateTime"),
			@Mapping(source = "ngayTraLoi", target = "acceptanceDate", qualifiedByName = "formatDateToStringDateTime"),
			@Mapping(source = "ngayTao", target = "registeredDate", qualifiedByName = "formatDateToStringDateTime"),
			@Mapping(source = "soTkDauTien", target = "firstDeclarationNo"),
			@Mapping(source = "soNhanhTk", target = "branchNoOfDividedDeclaration"),
			@Mapping(source = "tongSoTk", target = "noOfDividedDeclaration"),
			@Mapping(source = "soTkXktc", target = "declarationNoTentative"),
			@Mapping(source = "maLoaiHinh", target = "declarationKindCode"),
			@Mapping(source = "plHangHoa", target = "cargoClassificationCode"),
			@Mapping(source = "haiQuanTiepNhanKb", target = "customsOffice"),
			@Mapping(source = "ngayKhaiDuKien", target = "declarationPlannedDate", qualifiedByName = "formatToDate"),
			@Mapping(source = "plCaNhanToChuc", target = "classificationIndividualOrganization"),
			@Mapping(source = "plKths", target = "customsSubSection"),
			@Mapping(source = "maPtVanChuyen", target = "meansOfTransportationCode"),
			@Mapping(source = "maNguoiNk", target = "importerCode"),
			@Mapping(source = "tenNguoiNk", target = "importerName"),
			@Mapping(source = "maBuuChinhNguoiNk", target = "importerPostCode"),
			@Mapping(source = "maNguoiXk", target = "exporterCode"),
			@Mapping(source = "tenNguoiXk", target = "exporterName"),
			@Mapping(source = "maBuuChinhNguoiXk", target = "exporterPostCode"),
			 @Mapping(source = "maDaiLyHq", target = "agentCode"),
			@Mapping(source = "tongSoLuongKien", target = "totalCargoPiece"),
			@Mapping(source = "tongTrongLuongDvt", target = "totalWeightUnitCode"),
			@Mapping(source = "diaDiemLuuKhoHangCtqdk", target = "customsWarehouseCode"),
			@Mapping(source = "kyHieuSoHieu", target = "marksNumbers"),
			@Mapping(source = "maPhuongTienVanChuyen", target = "loadingVesselCode"),
			@Mapping(source = "tenPhuongTienVanChuyen", target = "loadingVesselName"),
			@Mapping(source = "maDiaDiemXepHang", target = "loadingPortCode"),
			@Mapping(source = "tenDiaDiemXepHang", target = "loadingPortName"),
			@Mapping(source = "soContainer", target = "numberContainers"),
			@Mapping(source = "tongSoLuongKienDvt", target = "totalPieceUnitCode"),
			@Mapping(source = "tongTrongLuong", target = "totalWeightGross"),
			@Mapping(source = "hoaDonTongTriGiaKb", target = "totalInvoicePrice"),
			@Mapping(source = "hoaDonMaTienTe", target = "invoiceCurrencyCode"),
			@Mapping(source = "phanLoaiChiThiHq", target = "customsInstructionClassification"),
			@Mapping(source = "gcSoHopDong", target = "processContractNo"),
			@Mapping(source = "gcNgayHopDong", target = "processContractDate", qualifiedByName = "formatToDate"),
			@Mapping(source = "hoaDonPlHinhThuc", target = "invoiceClassificationCode"),
			@Mapping(source = "hoaDonSoDienTu", target = "electronicInvoiceRefNo"),
			@Mapping(source = "hoaDonSo", target = "invoiceNo"),
			@Mapping(source = "hoaDonNgayPhatHanh", target = "invoiceDate", qualifiedByName = "formatToDate"),
			@Mapping(source = "hoaDonPtThanhToan", target = "termOfPayment"),
			@Mapping(source = "hoaDonPlGia", target = "invoicePriceKindCode"),
			@Mapping(source = "hoaDonDieuKienGia", target = "invoicePriceConditionCode"),
			@Mapping(source = "dcTriGiaTongHsPhanBo", target = "totalDistributionOnTaxValue"),
			@Mapping(source = "nguoiNopThue", target = "taxPayer"),
			@Mapping(source = "maNganHangTraThueThay", target = "bankPaymentCode"),
			@Mapping(source = "hanMucNamPhatHanh", target = "bankPaymentIssuedYear"),
			@Mapping(source = "hanMucKiHieuChungTu", target = "bankPaymentSign"),
			@Mapping(source = "hanMucSoChungTu", target = "bankPaymentNo"),
			@Mapping(source = "baoLanhMaNganHang", target = "securityBankCode"),
			@Mapping(source = "baoLanhNamPhatHanh", target = "securityBankIssuedYear"),
			@Mapping(source = "baoLanhKiHieuChungTu", target = "securityBankSign"),
			@Mapping(source = "baoLanhSoChungTu", target = "securityNo"),
			@Mapping(source = "ngayKhoiHanh", target = "startDateOfTransport", qualifiedByName = "formatToDate"),
			@Mapping(source = "ngayDenDuKien", target = "arrivalDateOfTransport", qualifiedByName = "formatToDate"),
			@Mapping(source = "diaDiemDichVcbt", target = "destinationBondedTransport"),
			@Mapping(source = "ghiChu", target = "notes"),
			@Mapping(source = "soQuanLyNbdn", target = "enterpriseInternalNumber"),
			@Mapping(source = "maXacDinhThoiHanNopThue", target = "paymentDueDateCode"),
			@Mapping(source = "maVb", target = "otherLawCodes", qualifiedByName = "formatToArray"),
			/* Tờ khai nhập mapping */
			@Mapping(source = "thoiHanTaiXuat", target = "timeLimitReExport", qualifiedByName = "formatToDate"),
			@Mapping(source = "ngayDen", target = "arrivalDate", qualifiedByName = "formatToDate"),
			@Mapping(source = "chiTietTkTriGia", target = "detailsOfValuation"),
			@Mapping(source = "tenNguoiUyThacXk", target = "exportConsignerName"),
			@Mapping(source = "diaChiXk1", target = "exporterAddress1"),
			@Mapping(source = "diaChiXk2", target = "exporterAddress2"),
			@Mapping(source = "diaChiXk3", target = "exporterAddress3"),
			@Mapping(source = "diaChiXk4", target = "exporterAddress4"),
			@Mapping(source = "vcPhi", target = "freightAmount"),
			@Mapping(source = "vcMaTienTePhi", target = "freightCurrencyCode"),
			@Mapping(source = "vcMaLoaiPhi", target = "freightDemarcationCode"),
			@Mapping(source = "maNguoiUyThacNk", target = "importContractorCode"),
			@Mapping(source = "tenNguoiUyThacNk", target = "importContractorName"),
			@Mapping(source = "diaChiNguoiNk", target = "importerAddress"),
			@Mapping(source = "sdtNguoiNk", target = "importerTelephone"),
			@Mapping(source = "ngayNhapKhoDauTien", target = "initialPermitCarryWarehouseDate", qualifiedByName = "formatToDate"),
			@Mapping(source = "bhPhi", target = "insuranceAmount"),
			@Mapping(source = "bhMaTienTe", target = "insuranceCurrencyCode"),
			@Mapping(source = "bhMaLoaiPhi", target = "insuranceDemarcationCode"),
			@Mapping(source = "soTKXKTaiChoTuongUng", target = "onSpotExportdeclarationNo", qualifiedByName = "formatToArray"),
			@Mapping(source = "soDangKyBaoHiemTongHop", target = "registrationNoOfComprehensiveInsurance"),
			@Mapping(source = "maLyDoDnbp", target = "requestReasonCode"),
			@Mapping(source = "maKetQuaKiemTraNd", target = "resultInspectionCode"),
			@Mapping(source = "maDiaDiemDoHang", target = "unloadingPortCode"),
			@Mapping(source = "tenDiaDiemDoHang", target = "unloadingPortName"),
			@Mapping(source = "tgSoTiepNhanTkth", target = "valuationComprehensiveRefNo"),
			@Mapping(source = "tgMaPlTk", target = "valuationDemarcationCode"),
			@Mapping(source = "tongTriGiaTinhThue", target = "taxValue"),
			@Mapping(source = "tongTriGiaTinhThueMaTienTe", target = "taxValueCurrencyCode"),
			@Mapping(source = "maNuocXk", target = "countryCode"),
			@Mapping(source = "soTk", target = "declarationNo")
	})
	ImportContentDto tknToContentDto(MauToKhaiHqEntity mauToKhaiHqEntity);

	@Named("formatToDate")
	public static String formatToDate(String source) {
		return VnaccsConvert.stringDate2String(source, EFormatDateTime.YYYY_MM_DD.getValueEnum());
	}

	@Named("formatToDateTime")
	public static String formatToDateTime(String source) {
		return VnaccsConvert.stringDate2String(source, EFormatDateTime.YYYY_MM_DD_H.getValueEnum());
	}

	@Named("formatDateToStringDateTime")
	public static String formatDateToStringDateTime(Date source) {
		return VnaccsConvert.date2String(source, EFormatDateTime.YYYY_MM_DD_H.getValueEnum());
	}
	
	@Named("formatToArray")
	public static String[] formatToArray(String source) {
		if (source == null || source.isEmpty()) return new String[0];
		return source.split(",");
	}
}
