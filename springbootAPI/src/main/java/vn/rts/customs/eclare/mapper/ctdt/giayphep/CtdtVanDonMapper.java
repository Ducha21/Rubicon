package vn.rts.customs.eclare.mapper.ctdt.giayphep;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import vn.rts.customs.eclare.dto.body.electronic_doc.BillOfLadingContentDto;
import vn.rts.customs.eclare.entity.ChungTuDinhKemTkEntity;
import vn.rts.customs.eclare.entity.CtdtVanDonContainerEntity;
import vn.rts.customs.eclare.entity.CtdtVanDonEntity;
import vn.rts.customs.eclare.entity.CtdtVanDonNhanhEntity;
import vn.rts.customs.eclare.util.VnaccsConvert;

import java.util.Date;

@Mapper
public interface CtdtVanDonMapper {
    CtdtVanDonMapper INSTANCE = Mappers.getMapper(CtdtVanDonMapper.class);

    @Mappings({
            @Mapping(source = "soContainer", target = "containerNo"),
            @Mapping(source = "soSeal", target = "sealNo")
    })
    BillOfLadingContentDto.ContainerNo ctdtVanDonDEntityToContainerNo(CtdtVanDonContainerEntity ctdtVanDonContainerEntity);

    @Mappings({
            @Mapping(source = "sttVanDonNhanh", target = "sequenceNo"),
            @Mapping(source = "soVanDonNhanh", target = "billNoBranch")
    })
    BillOfLadingContentDto.BranchDetail ctdtVanDonNhanhEntityToBranchDetail(CtdtVanDonNhanhEntity ctdtVanDonNhanhEntity);

    @Mappings({
            @Mapping(source = "id", target = "refId"),
            @Mapping(source = "loaiChungTu", target = "documentType"),
            @Mapping(source = "kenhKhaiBao", target = "declarantChannel"),
            @Mapping(source = "ngayTao", target = "issueDate", qualifiedByName = "formatDateToStringDateTime"),
            @Mapping(source = "chucNangChungTu", target = "function"),
            @Mapping(source = "noiKhaiBao", target = "issueLocation"),
            @Mapping(source = "ngayTao", target = "acceptanceDate", qualifiedByName = "formatDateToStringDateTime"),
            @Mapping(source = "hqTiepNhanChungTu", target = "acceptanceOffice")
    })
    BillOfLadingContentDto chungTuDinhKemToKhaiToBillOfLadingContentDto(ChungTuDinhKemTkEntity chungTuDinhKemTkEntity);


    @Mappings({
            @Mapping(source = "soVanDon", target = "billNo"),
            @Mapping(source = "ngayPhatHanh", target = "billDate", qualifiedByName = "formatToDate"),
            @Mapping(source = "nuocPhatHanh", target = "issueLocation"),
            @Mapping(source = "maNguoiVanChuyen", target = "carrierCode"),
            @Mapping(source = "tenNguoiVanChuyen", target = "carrierName"),
            @Mapping(source = "soLuongContainer", target = "numberContainers"),
            @Mapping(source = "soLuongKien", target = "cargoPiece"),
            @Mapping(source = "maDvtSoLuongKien", target = "pieceUnitCode"),
            @Mapping(source = "tongTrongLuong", target = "cargoWeight"),
            @Mapping(source = "maDvtTongTrongLuong", target = "weightUnitCode"),
            @Mapping(source = "phuongThucGiaoHang", target = "deliveryMethod"),
            @Mapping(source = "soLuongVanDonNhanh", target = "numberBranch"),
            @Mapping(source = "diaDiemQuaCanh", target = "transitLocation"),
            @Mapping(source = "loaiVanDon", target = "typeCode"),
            @Mapping(source = "ghiChuKhac", target = "content")
    })
    BillOfLadingContentDto.BillOfLading ctdtVanDonEntityToctdtVanDonEntityToBillOfLading(CtdtVanDonEntity ctdtVanDonEntity);

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
