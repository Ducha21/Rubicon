
package vn.rts.customs.eclare.repository.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import vn.rts.customs.eclare.entity.TkContainerEntity;
import vn.rts.customs.eclare.request.search.KeySearchContainers;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_TEST
 */

@Repository
public class TkContainerRepositoryImpl extends BaseRepositoryImplEx<TkContainerEntity, String> {

    @Override
    public TkContainerEntity insert(TkContainerEntity objInput) {
        UUID uuid = UUID.randomUUID();
        objInput.setId(uuid.toString());
        return excuteObjectUsingSp( //
                TkContainerEntity.class //
                , "PKG_TK_CONTAINER.insertItem" //
                , objInput.getId() //
                , objInput.getLoaiChungTu() //
                , objInput.getKenhKhaiBao() //
                , objInput.getNgayTraLoi() //
                , objInput.getNgayTao() //
                , objInput.getNguoiSua() //
                , objInput.getNgaySua() //
                , objInput.getNoiKhaiBao() //
                , objInput.getMaChucNang() //
                , objInput.getSoTiepNhan() //
                , objInput.getNgayDangKy() //
                , objInput.getMauKbHqId() //
                , objInput.getHaiQuanTiepNhan() //
                , objInput.getMaNguoiKhaiHq() //
                , objInput.getTenNguoiKhaiHq() //
                , objInput.getTenDoanhNghiep() //
                , objInput.getMaDoanhNghiep() //
                , objInput.getDiaChiDoanhNghiep() //
                , objInput.getSoTk() //
                , objInput.getMaLoaiHinh() //
                , objInput.getMaHaiQuanGiamSat() //
                , objInput.getThoiGianKxDuLieu() //
                , objInput.getLuongToKhai() //
                , objInput.getTrangThaiToKhai() //
                , objInput.getTtNiemPhongHang() //
                , objInput.getSoDinhDanhHh() //
                , objInput.getMaPtVanChuyen() //
                , objInput.getNgayTauDuKien() //
                , objInput.getNgayTiepNhan() //
                , objInput.getGhiChu()
        );
    }

    @Override
    public TkContainerEntity update(TkContainerEntity objInput) {
        return excuteObjectUsingSp( //
                TkContainerEntity.class //
                , "PKG_TK_CONTAINER.updateItem" //
                , objInput.getId() //
                , objInput.getLoaiChungTu() //
                , objInput.getKenhKhaiBao() //
                , objInput.getNgayTraLoi() //
                , objInput.getNgayTao() //
                , objInput.getNguoiSua() //
                , objInput.getNgaySua() //
                , objInput.getNoiKhaiBao() //
                , objInput.getMaChucNang() //
                , objInput.getSoTiepNhan() //
                , objInput.getNgayDangKy() //
                , objInput.getMauKbHqId() //
                , objInput.getHaiQuanTiepNhan() //
                , objInput.getMaNguoiKhaiHq() //
                , objInput.getTenNguoiKhaiHq() //
                , objInput.getTenDoanhNghiep() //
                , objInput.getMaDoanhNghiep() //
                , objInput.getDiaChiDoanhNghiep() //
                , objInput.getSoTk() //
                , objInput.getMaLoaiHinh() //
                , objInput.getMaHaiQuanGiamSat() //
                , objInput.getThoiGianKxDuLieu() //
                , objInput.getLuongToKhai() //
                , objInput.getTrangThaiToKhai() //
                , objInput.getTtNiemPhongHang() //
                , objInput.getSoDinhDanhHh() //
                , objInput.getMaPtVanChuyen() //
                , objInput.getNgayTauDuKien() //
                , objInput.getNgayTiepNhan() //
                , objInput.getGhiChu()
                , objInput.getNoiDungPhanHoi()
        );
    }

    @Override
    public void delete(TkContainerEntity objInput) {
        excuteInsertUpdateDeleteUsingSp( //
                "PKG_TK_CONTAINER.deleteItem" //
        );
    }

    @Override
    public Optional<TkContainerEntity> findById(String id) {
        return Optional.ofNullable(excuteObjectUsingSp(TkContainerEntity.class //
                , "PKG_TK_CONTAINER.findById" //
                , id //
        ));
    }

    public Optional<TkContainerEntity> findByToKhaiId(String toKhaiId, String documentType) {
        return Optional.ofNullable(excuteObjectUsingSp(TkContainerEntity.class //
                , "PKG_TK_CONTAINER.findByToKhaiId" //
                , toKhaiId //
                , documentType
        ));
    }

    public void updateInactive(String toKhaiId, String documentType) {
        excuteObjectUsingSp( //
                TkContainerEntity.class //
                , "PKG_TK_CONTAINER.updateInactive" //
                , toKhaiId
                , documentType
        );
    }

    public Page<TkContainerEntity> search(KeySearchContainers objInput) {
        return convertListObject2PageOfObject(excuteListObjectUsingSp( //
                TkContainerEntity.class //
                , "PKG_TK_CONTAINER.search" //
                , objInput.getToKhaiId() //
                , objInput.getPageBegin() //
                , objInput.getPageEnd() //
        ), PageRequest.of(objInput.getPage(), objInput.getSize()));
    }

}
    