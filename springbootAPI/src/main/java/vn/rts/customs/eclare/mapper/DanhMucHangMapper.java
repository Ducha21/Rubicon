package vn.rts.customs.eclare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import vn.rts.customs.eclare.dto.body.CertificateOfOriginsDto;
import vn.rts.customs.eclare.dto.body.ExportGoodsItemDto;
import vn.rts.customs.eclare.dto.body.ImportGoodsItemDto;
import vn.rts.customs.eclare.entity.CoHangHoaTkEntity;
import vn.rts.customs.eclare.entity.DmHangHoaTkEntity;
import vn.rts.customs.eclare.util.VnaccsConvert;

@Mapper
public interface DanhMucHangMapper {
    DanhMucHangMapper INSTANCE = Mappers.getMapper(DanhMucHangMapper.class);

    @Mappings({
            @Mapping(source = "sttHang", target = "sequence"),
            @Mapping(source = "maNgoaiHanNgach", target = "tariffQuotaClassification"),
            @Mapping(source = "maHs", target = "hsCode"), @Mapping(source = "maQlr", target = "materialCode"),
            @Mapping(source = "thueSuat", target = "dutyRate"),
            @Mapping(source = "thueSuatTuyetDoi", target = "absoluteTariffRate"),
            @Mapping(source = "thueSuatTuyetDoiDvt", target = "unitAbsoluteDutyRate"),
            @Mapping(source = "thueSuatTuyetDoiMaNt", target = "currencyAbsoluteDutyRate"),
            @Mapping(source = "nuocXxMa", target = "originCode"), @Mapping(source = "maHang", target = "commodityCode"),
            @Mapping(source = "tenHang", target = "itemName"),
            @Mapping(source = "thueMienGiamMa", target = "exemptionImportTaxCode"),
            @Mapping(source = "thueGiam", target = "reductionImportTaxAmount"),
            @Mapping(source = "luong", target = "quantity1"),
            @Mapping(source = "maDvt", target = "quantityUnitCode1"),
            @Mapping(source = "luong2", target = "quantity2"),
            @Mapping(source = "maDvt2", target = "quantityUnitCode2"),
            @Mapping(source = "triGiaKb", target = "invoiceValue"),
            @Mapping(source = "dgiaKb", target = "invoiceUnitPrice"),
            @Mapping(source = "dgiaKbMaNt", target = "unitPriceCurrencyCode"),
            @Mapping(source = "dgiaKbDvt", target = "unitPriceQuantity"),
            @Mapping(source = "triGiaTinhThue", target = "taxValue"),
            @Mapping(source = "triGiaTinhThueDvt", target = "taxValueCurrencyCode"),
            @Mapping(source = "sttHangTntx", target = "lineNumberTentative"),
            @Mapping(source = "soDkMienThue", target = "taxExemptionNo"),
            @Mapping(source = "sttHangDmMienThue", target = "lineNumberTaxExemption"),
            @Mapping(source = "cdUdThue", target = "certificateType"),
            @Mapping(source = "soDanhMucKhoanDieuChinh", target = "valuationNos", qualifiedByName = "formatToArray"),
            @Mapping(source = "maMucThueTuyetDoi", target = "perUnitTaxCode"),
            @Mapping(source = "maBieuThue", target = "importTaxClassificationCode"),
            @Mapping(source = "loaiHang", target = "commodityType")
    })
    ImportGoodsItemDto danhMucHangToImportGoodsItemDto(DmHangHoaTkEntity danhMucHang);

    @Mappings({
            @Mapping(source = "sttHang", target = "sequence"),
            @Mapping(source = "maNgoaiHanNgach", target = "tariffQuotaClassification"),
            @Mapping(source = "maHs", target = "hsCode"), @Mapping(source = "maQlr", target = "materialCode"),
            @Mapping(source = "thueSuat", target = "dutyRate"),
            @Mapping(source = "thueSuatTuyetDoi", target = "absoluteTariffRate"),
            @Mapping(source = "thueSuatTuyetDoiDvt", target = "unitAbsoluteDutyRate"),
            @Mapping(source = "thueSuatTuyetDoiMaNt", target = "currencyAbsoluteDutyRate"),
            @Mapping(source = "nuocXxMa", target = "originCode"), @Mapping(source = "maHang", target = "commodityCode"),
            @Mapping(source = "tenHang", target = "itemName"),
            @Mapping(source = "thueMienGiamMa", target = "exemptionExportTaxCode"),
            @Mapping(source = "thueGiam", target = "reductionExportTaxAmount"),
            @Mapping(source = "luong", target = "quantity1"),
            @Mapping(source = "maDvt", target = "quantityUnitCode1"),
            @Mapping(source = "luong2", target = "quantity2"),
            @Mapping(source = "maDvt2", target = "quantityUnitCode2"),
            @Mapping(source = "triGiaKb", target = "invoiceValue"),
            @Mapping(source = "dgiaKb", target = "invoiceUnitPrice"),
            @Mapping(source = "dgiaKbMaNt", target = "unitPriceCurrencyCode"),
            @Mapping(source = "dgiaKbDvt", target = "unitPriceQuantity"),
            @Mapping(source = "triGiaTinhThue", target = "taxValue"),
            @Mapping(source = "triGiaTinhThueDvt", target = "taxValueCurrencyCode"),
            @Mapping(source = "sttHangTntx", target = "lineNumberTentative"),
            @Mapping(source = "soDkMienThue", target = "taxExemptionNo"),
            @Mapping(source = "sttHangDmMienThue", target = "lineNumberTaxExemption"),
            @Mapping(source = "cdUdThue", target = "certificateType"),
            @Mapping(source = "soDanhMucKhoanDieuChinh", target = "valuationNos", qualifiedByName = "formatToArray"),
            @Mapping(source = "maMucThueTuyetDoi", target = "perUnitTaxCode"),
            @Mapping(source = "maBieuThue", target = "importTaxClassificationCode"),
//            @Mapping(source = "loaiHang", target = "commodityType")
    })
    ExportGoodsItemDto danhMucHangToExportGoodsItemDto(DmHangHoaTkEntity danhMucHang);

    @Named("formatToArray")
    public static String[] formatToArray(String source) {
        if (source == null || source.isEmpty()) return new String[0];
        return source.split(",");
    }

    @Mappings({
            @Mapping(source = "coNgay", target = "certDate", qualifiedByName = "formatToDate"),
            @Mapping(source = "coNguoiCap", target = "certIssuer"),
            @Mapping(source = "coNoicap", target = "certLocation"),
            @Mapping(source = "coSo", target = "certNo"),
            @Mapping(source = "coNgayHetHan", target = "certExpire", qualifiedByName = "formatToDate")
    })
    CertificateOfOriginsDto dsCoToCertificateOfOrigins(CoHangHoaTkEntity co);


    @Named("formatToDate")
    public static String formatToDate(String source) {
        return VnaccsConvert.stringDate2String(source, VnaccsConvert.EFormatDateTime.YYYY_MM_DD.getValueEnum());
    }
}
