
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.DmHangHoaTkEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author VNETC
 */

@Repository
public class DmHangHoaTkRepositoryImpl extends BaseRepositoryImplEx<DmHangHoaTkEntity, Long> {
    private static final String S_FORMAT_DATE = "yyyy-MM-dd";

    @Override
    public DmHangHoaTkEntity insert(DmHangHoaTkEntity objInput) {
        return excuteObjectUsingSp( //
                DmHangHoaTkEntity.class //
                , "PKG_DM_HANG_HOA_TK.insertItem" //
                , objInput.getId() //
                , objInput.getMaNgoaiHanNgach() //
                , objInput.getMaHs() //
                , objInput.getMaQlr() //
                , objInput.getThueSuat() //
                , objInput.getThueSuatTuyetDoi() //
                , objInput.getThueSuatTuyetDoiDvt() //
                , objInput.getThueSuatTuyetDoiMaNt() //
                , objInput.getNuocXxMa() //
                , objInput.getMaHang() //
                , objInput.getTenHang() //
                , objInput.getThueMienGiamMa() //
                , objInput.getThueGiam() //
                , objInput.getLuong() //
                , objInput.getMaDvt() //
                , objInput.getLuong2() //
                , objInput.getMaDvt2() //
                , objInput.getTriGiaKb() //
                , objInput.getDgiaKb() //
                , objInput.getDgiaKbMaNt() //
                , objInput.getDgiaKbDvt() //
                , objInput.getTriGiaTinhThue() //
                , objInput.getTriGiaTinhThueDvt() //
                , objInput.getSttHangTntx() //
                , objInput.getSoDkMienThue() //
                , objInput.getSttHangDmMienThue() //
                , objInput.getCdUdThue() //
                , objInput.getMauKbHqId() //
                , objInput.getNguoiTao() //
                , objInput.getNgayTao() //
                , objInput.getNguoiSua() //
                , objInput.getNgaySua() //
                , objInput.getMaBieuThue()
                , objInput.getSoDanhMucKhoanDieuChinh()
                , objInput.getMaMucThueTuyetDoi()
                , objInput.getSttHang()
                , objInput.getMaDoanhNghiep()
                , objInput.getLoaiHang()
        );
    }

    @Override
    public DmHangHoaTkEntity update(DmHangHoaTkEntity objInput) {
        return excuteObjectUsingSp( //
                DmHangHoaTkEntity.class //
                , "PKG_DM_HANG_HOA_TK.updateItem" //
                , objInput.getId() //
                , objInput.getMaNgoaiHanNgach() //
                , objInput.getMaHs() //
                , objInput.getMaQlr() //
                , objInput.getThueSuat() //
                , objInput.getThueSuatTuyetDoi() //
                , objInput.getThueSuatTuyetDoiDvt() //
                , objInput.getThueSuatTuyetDoiMaNt() //
                , objInput.getNuocXxMa() //
                , objInput.getMaHang() //
                , objInput.getTenHang() //
                , objInput.getThueMienGiamMa() //
                , objInput.getThueGiam() //
                , objInput.getLuong() //
                , objInput.getMaDvt() //
                , objInput.getLuong2() //
                , objInput.getMaDvt2() //
                , objInput.getTriGiaKb() //
                , objInput.getDgiaKb() //
                , objInput.getDgiaKbMaNt() //
                , objInput.getDgiaKbDvt() //
                , objInput.getTriGiaTinhThue() //
                , objInput.getTriGiaTinhThueDvt() //
                , objInput.getSttHangTntx() //
                , objInput.getSoDkMienThue() //
                , objInput.getSttHangDmMienThue() //
                , objInput.getCdUdThue() //
                , objInput.getMauKbHqId() //
                , objInput.getNguoiTao() //
                , objInput.getNgayTao() //
                , objInput.getNguoiSua() //
                , objInput.getNgaySua() //
                , objInput.getMaBieuThue()
                , objInput.getSoDanhMucKhoanDieuChinh()
                , objInput.getMaMucThueTuyetDoi()
                , objInput.getSttHang()
                , objInput.getGhiChuKhac()
                , objInput.getLoaiHang()
        );
    }

    @Override
    public void delete(DmHangHoaTkEntity objInput) {
        excuteInsertUpdateDeleteUsingSp( //
                "PKG_DM_HANG_HOA_TK.deleteItem" //
                , objInput.getId() //
        );
    }

    public void deleteByToKhaiId(List<DmHangHoaTkEntity> objInputs) {
        if (objInputs == null) return;
        for (DmHangHoaTkEntity dmHangHoaTkEntity : objInputs) {
            delete(dmHangHoaTkEntity);
        }
    }

    @Override
    public Optional<DmHangHoaTkEntity> findById(Long id) {
        return Optional.ofNullable(excuteObjectUsingSp(DmHangHoaTkEntity.class //
                , "PKG_DM_HANG_HOA_TK.findById" //
                , id //
        ));
    }

    public List<DmHangHoaTkEntity> findByToKhaiId(String toKhaiId) {
        return excuteListObjectUsingSp( //
                DmHangHoaTkEntity.class //
                , "PKG_DM_HANG_HOA_TK.findbytokhaiid" //
                , toKhaiId
        );
    }

    public List<DmHangHoaTkEntity> findByToKhaiIdAndSttHang(String toKhaiId, Integer sttHang) {
        return excuteListObjectUsingSp( //
                DmHangHoaTkEntity.class //
                , "PKG_DM_HANG_HOA_TK.findbytokhaiidandSttHang" //
                , toKhaiId
                , sttHang
        );
    }

    public Page<DmHangHoaTkEntity> search(KeySearchListObj objInput) {
        return convertListObject2PageOfObject(excuteListObjectUsingSp( //
                DmHangHoaTkEntity.class //
                , "PKG_DM_HANG_HOA_TK.search" //
                , objInput.getPage() //
                , objInput.getSize() //
        ), PageRequest.of(objInput.getPage(), objInput.getSize()));
    }

}
    