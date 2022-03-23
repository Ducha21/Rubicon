
package vn.rts.customs.eclare.repository.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import vn.rts.customs.eclare.entity.DinhDanhHangHoaTkEntity;
import vn.rts.customs.eclare.request.search.KeySearchSoDinhDanhTk;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class DinhDanhHangHoaTkRepositoryImpl extends BaseRepositoryImplEx<DinhDanhHangHoaTkEntity, String> {

    @Override
    public DinhDanhHangHoaTkEntity insert(DinhDanhHangHoaTkEntity objInput) {
        UUID uuid = UUID.randomUUID();
        objInput.setId(uuid.toString());
        return excuteObjectUsingSp( //
                DinhDanhHangHoaTkEntity.class //
                , "PKG_DINH_DANH_HANG_HOA_TK.insertItem" //
                , objInput.getId() //
                , objInput.getNgaySua() //
                , objInput.getNgayTao() //
                , objInput.getNguoiSua() //
                , objInput.getNguoiTao() //
                , objInput.getSoDinhDanh() //
                , objInput.getSoTiepNhan() //
                , objInput.getNgayTiepNhan() //
                , objInput.getNgayCap() //
                , objInput.getMaDoanhNghiep() //
                , objInput.getTrangThai() //
                , objInput.getKenhKhaiBao() //
                , objInput.getNoiKhaiBao() //
                , objInput.getHqTiepNhan() //
                , objInput.getTenHqTiepNhan()
                , objInput.getMaNguoiKhaiHq() //
                , objInput.getTenNguoiKhaiHq() //
                , objInput.getTenDoanhNghiep() //
                , objInput.getDiaChiDoanhNghiep() //
                , objInput.getLoaiDoiTuong() //
                , objInput.getLoaiHangHoa() //
                , objInput.getDoanhNghiepXnk() //
        );
    }

    @Override
    public DinhDanhHangHoaTkEntity update(DinhDanhHangHoaTkEntity objInput) {
        return excuteObjectUsingSp( //
                DinhDanhHangHoaTkEntity.class //
                , "PKG_DINH_DANH_HANG_HOA_TK.updateItem" //
                , objInput.getId() //
                , objInput.getNgaySua() //
                , objInput.getNgayTao() //
                , objInput.getNguoiSua() //
                , objInput.getNguoiTao() //
                , objInput.getSoDinhDanh() //
                , objInput.getSoTiepNhan() //
                , objInput.getNgayTiepNhan() //
                , objInput.getNgayCap() //
                , objInput.getMaDoanhNghiep() //
                , objInput.getTrangThai() //
                , objInput.getKenhKhaiBao() //
                , objInput.getNoiKhaiBao() //
                , objInput.getHqTiepNhan() //
                , objInput.getMaNguoiKhaiHq() //
                , objInput.getTenNguoiKhaiHq() //
                , objInput.getTenDoanhNghiep() //
                , objInput.getDiaChiDoanhNghiep() //
                , objInput.getLoaiDoiTuong() //
                , objInput.getLoaiHangHoa() //
                , objInput.getDoanhNghiepXnk() //
        );
    }

    @Override
    public void delete(DinhDanhHangHoaTkEntity objInput) {
        excuteInsertUpdateDeleteUsingSp( //
                "PKG_DINH_DANH_HANG_HOA_TK.deleteItem" //
                , objInput.getId() //
        );
    }

    @Override
    public Optional<DinhDanhHangHoaTkEntity> findById(String id) {
        return Optional.ofNullable(excuteObjectUsingSp(DinhDanhHangHoaTkEntity.class //
                , "PKG_DINH_DANH_HANG_HOA_TK.findById" //
                , id //
        ));
    }

    public Page<DinhDanhHangHoaTkEntity> search(KeySearchSoDinhDanhTk objInput) {
        return convertListObject2PageOfObject(excuteListObjectUsingSp( //
                DinhDanhHangHoaTkEntity.class //
                , "PKG_DINH_DANH_HANG_HOA_TK.search" //
                , objInput.getSoDinhDanh() //
                , objInput.getSoTiepNhan() //
                , objInput.getNgayTiepNhan() //
                , objInput.getNgayCap() //
                , objInput.getMaDoanhNghiep() //
                , objInput.getTrangThai() //
                , objInput.getKenhKhaiBao() //
                , objInput.getNoiKhaiBao() //
                , objInput.getHqTiepNhan() //
                , objInput.getDoanhNghiepXnk() //
                , objInput.getPageBegin() //
                , objInput.getPageEnd() //
        ), PageRequest.of(objInput.getPage(), objInput.getSize()));
    }

}
    