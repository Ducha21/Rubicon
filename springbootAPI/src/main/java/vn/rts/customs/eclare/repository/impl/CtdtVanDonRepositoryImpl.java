package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.CtdtVanDonEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

@Repository
public class CtdtVanDonRepositoryImpl extends BaseRepositoryImplEx<CtdtVanDonEntity, Long> {

	@Override
	public CtdtVanDonEntity insert(CtdtVanDonEntity objInput) {
		return excuteObjectUsingSp( //
				CtdtVanDonEntity.class //
				, "PKG_CTDT_VAN_DON.insertItem" //
				, objInput.getId() //
				, objInput.getChungTuDinhKemTkId() //
				, objInput.getNgaySua() //
				, objInput.getNgayTao() //
				, objInput.getNguoiSua() //
				, objInput.getNguoiTao() //
				, objInput.getSoVanDon() //
				, objInput.getNgayPhatHanh() //
				, objInput.getNuocPhatHanh() //
				, objInput.getMaNguoiVanChuyen() //
				, objInput.getTenNguoiVanChuyen() //
				, objInput.getSoLuongContainer() //
				, objInput.getSoLuongKien() //
				, objInput.getMaDvtSoLuongKien() //
				, objInput.getTongTrongLuong() //
				, objInput.getMaDvtTongTrongLuong() //
				, objInput.getPhuongThucGiaoHang() //
				, objInput.getSoLuongVanDonNhanh() //
				, objInput.getDiaDiemQuaCanh() //
				, objInput.getLoaiVanDon() //
				, objInput.getGhiChuKhac() //
				, objInput.getMaDoanhNghiep()
		);
	}

	@Override
	public CtdtVanDonEntity update(CtdtVanDonEntity objInput) {
		return excuteObjectUsingSp( //
				CtdtVanDonEntity.class //
				, "PKG_CTDT_VAN_DON.updateItem" //
				, objInput.getId() //
				, objInput.getChungTuDinhKemTkId() //
				, objInput.getSoVanDon() //
				, objInput.getNgayPhatHanh() //
				, objInput.getNuocPhatHanh() //
				, objInput.getMaNguoiVanChuyen() //
				, objInput.getTenNguoiVanChuyen() //
				, objInput.getSoLuongContainer() //
				, objInput.getSoLuongKien() //
				, objInput.getMaDvtSoLuongKien() //
				, objInput.getTongTrongLuong() //
				, objInput.getMaDvtTongTrongLuong() //
				, objInput.getPhuongThucGiaoHang() //
				, objInput.getSoLuongVanDonNhanh() //
				, objInput.getDiaDiemQuaCanh() //
				, objInput.getLoaiVanDon() //
				, objInput.getGhiChuKhac() //
		);
	}

	@Override
	public void delete(CtdtVanDonEntity objInput) {
		excuteInsertUpdateDeleteUsingSp( //
				"PKG_CTDT_VAN_DON.deleteItem" //
				, objInput.getId() //
		);
	}

	@Override
	public Optional<CtdtVanDonEntity> findById(Long id) {
		return Optional.ofNullable(excuteObjectUsingSp(CtdtVanDonEntity.class //
				, "PKG_CTDT_VAN_DON.findById" //
				, id //
		));
	}

	public Page<CtdtVanDonEntity> search(KeySearchListObj objInput) {
		return convertListObject2PageOfObject(excuteListObjectUsingSp( //
				CtdtVanDonEntity.class //
				, "PKG_CTDT_VAN_DON.search" //
				, objInput.getPageBegin() //
				, objInput.getPageEnd() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

	public List<CtdtVanDonEntity> searchByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
		return excuteListObjectUsingSp(
				CtdtVanDonEntity.class
				, "PKG_CTDT_VAN_DON.searchByChungTuDinhKemTkId"
				, chungTuDinhKemTkId
		);
	}

    public Optional<CtdtVanDonEntity> searchOptionalByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
		return Optional.ofNullable(excuteObjectUsingSp(
				CtdtVanDonEntity.class
				,"PKG_CTDT_VAN_DON.searchOptionalByChungTuDinhKemTkId"
				,chungTuDinhKemTkId
		));
    }
}
