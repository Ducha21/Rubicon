package vn.rts.customs.eclare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import vn.rts.customs.eclare.dto.body.ContainerNoContentDto;
import vn.rts.customs.eclare.dto.body.ContainerNosDto;
import vn.rts.customs.eclare.entity.ContainerInfoEntity;
import vn.rts.customs.eclare.entity.TkContainerEntity;
import vn.rts.customs.eclare.util.VnaccsConvert;
import vn.rts.customs.eclare.util.VnaccsConvert.EFormatDateTime;

import java.util.Date;

@Mapper
public interface ContainerTkMapper {
    ContainerTkMapper INSTANCE = Mappers.getMapper(ContainerTkMapper.class);

    @Mappings({
            @Mapping(source = "kenhKhaiBao", target = "declarantChannel"),
            @Mapping(source = "id", target = "refId"),
            @Mapping(source = "ngayTao", target = "issueDate", qualifiedByName = "formatDateToStringDateTime"),
            @Mapping(source = "maChucNang", target = "function"),
            @Mapping(source = "soTiepNhan", target = "refCustomsId"),
            @Mapping(source = "ngayTiepNhan", target = "acceptanceDate", qualifiedByName = "formatDateToStringDateTime"),
            @Mapping(source = "haiQuanTiepNhan", target = "acceptanceOffice"),
            @Mapping(source = "maNguoiKhaiHq", target = "declarantCode"),
            @Mapping(source = "tenNguoiKhaiHq", target = "declarantName"),
            @Mapping(source = "tenDoanhNghiep", target = "importerName"),
            @Mapping(source = "maDoanhNghiep", target = "importerCode"),
            @Mapping(source = "soTk", target = "declarationNo"),
            @Mapping(source = "ngayDangKy", target = "registeredDate", qualifiedByName = "formatDateToStringDate"),
            @Mapping(source = "maLoaiHinh", target = "declarationKindCode"),
            @Mapping(source = "haiQuanTiepNhan", target = "declarationOffice"),
            @Mapping(source = "diaChiDoanhNghiep", target = "importerAddress"),
            @Mapping(source = "noiKhaiBao", target = "issueLocation")
    })
    ContainerNoContentDto thongTinContainerToContainerNoContent(TkContainerEntity tkContainerEntity);

    @Mappings({
            @Mapping(source = "vanDonSo", target = "billNo"),
            @Mapping(source = "soContainer", target = "containerNo"),
            @Mapping(source = "soSeal", target = "sealNo"),
            @Mapping(source = "ghiChu", target = "content")
    })
    ContainerNosDto tkContainerToContainerNos(ContainerInfoEntity containersEntity);

    @Named("formatDateToStringDateTime")
    public static String formatDateToStringDateTime(Date source) {
        return VnaccsConvert.date2String(source, EFormatDateTime.YYYY_MM_DD_H.getValueEnum());
    }

    @Named("formatDateToStringDate")
    public static String formatDateToStringDate(Date source) {
        return VnaccsConvert.date2String(source, EFormatDateTime.YYYY_MM_DD.getValueEnum());
    }
}
