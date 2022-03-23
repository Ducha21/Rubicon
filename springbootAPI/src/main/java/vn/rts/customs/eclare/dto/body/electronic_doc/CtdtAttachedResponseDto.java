package vn.rts.customs.eclare.dto.body.electronic_doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.rts.customs.eclare.entity.CtdtFileDinhKemEntity;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CtdtAttachedResponseDto {
    private String id;
    private String soTn;
    private String ngayTao;
    private String loaiChungTu;
    private String ghiChu;
    private String trangThai;
    private String noiDungHqPhanHoi;
    private String ngayHqPhanHoi;
    private String hqTiepNhan;
    private String ngayDangKyCt;

    private CtdtFileDinhKemEntity ctdtFileDinhKem;
}
