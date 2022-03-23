package vn.rts.customs.eclare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import vn.rts.customs.eclare.dto.body.CargoCtrlNoContentDto;
import vn.rts.customs.eclare.entity.DinhDanhHangHoaTkEntity;
import vn.rts.customs.eclare.util.VnaccsConvert;
import vn.rts.customs.eclare.util.VnaccsConvert.EFormatDateTime;

import java.util.Date;

@Mapper
public interface SoDinhDanhMapper {
    SoDinhDanhMapper INSTANCE = Mappers.getMapper(SoDinhDanhMapper.class);

    @Mappings({
            @Mapping(source = "kenhKhaiBao", target = "declarantChannel"),
            @Mapping(source = "id", target = "refId"),
            @Mapping(source = "ngayTao", target = "issueDate", qualifiedByName = "formatDateToStringDateTime"),
            @Mapping(source = "trangThai", target = "function"),
            @Mapping(source = "noiKhaiBao", target = "issueLocation"),
            @Mapping(source = "soTiepNhan", target = "refCustomsId"),
            @Mapping(source = "ngayTiepNhan", target = "acceptanceDate", qualifiedByName = "formatDateToStringDateTime"),
            @Mapping(source = "maNguoiKhaiHq", target = "declarantCode"),
            @Mapping(source = "tenNguoiKhaiHq", target = "declarantName"),
            @Mapping(source = "tenDoanhNghiep", target = "importerName"),
            @Mapping(source = "maDoanhNghiep", target = "importerCode"),
            @Mapping(source = "diaChiDoanhNghiep", target = "importerAddress"),
            @Mapping(source = "loaiDoiTuong", target = "requestType"),
            @Mapping(source = "loaiHangHoa", target = "transportEquipmentType"),
            @Mapping(source = "doanhNghiepXnk", target = "customsImporter"),
            @Mapping(source = "hqTiepNhan", target = "acceptanceOffice")
    })
    CargoCtrlNoContentDto soDinhDanhEntityToCargoCtrlNoContent(DinhDanhHangHoaTkEntity dinhDanhHangHoaTkEntity);

    @Named("formatDateToStringDateTime")
    public static String formatDateToStringDateTime(Date source) {
        return VnaccsConvert.date2String(source, EFormatDateTime.YYYY_MM_DD_H.getValueEnum());
    }
}
