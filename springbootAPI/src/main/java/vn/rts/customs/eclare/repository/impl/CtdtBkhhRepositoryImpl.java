
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.CtdtBkhhEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class CtdtBkhhRepositoryImpl extends BaseRepositoryImplEx<CtdtBkhhEntity, Long> {

	@Override
	public CtdtBkhhEntity insert(CtdtBkhhEntity objInput) {
		return excuteObjectUsingSp( //
				CtdtBkhhEntity.class //
				, "PKG_CTDT_BKHH.insertItem" //
				, objInput.getId() //
				, objInput.getChungTuDinhKemTkId() //
				, objInput.getSoBangKeChiTiet() //
				, objInput.getNgayPhatHanh() //
				, objInput.getTongSoLuongMatHang() //
				, objInput.getTongSoLuongKienHang() //
				, objInput.getMaDvtSoLuongKien() //
				, objInput.getGhiChuKhac() //
				, objInput.getNguoiTao() //
				, objInput.getMaDoanhNghiep()
		);
	}

	@Override
	public CtdtBkhhEntity update(CtdtBkhhEntity objInput) {
		return excuteObjectUsingSp( //
				CtdtBkhhEntity.class //
				, "PKG_CTDT_BKHH.updateItem" //
				, objInput.getId() //
				, objInput.getChungTuDinhKemTkId() //
				, objInput.getSoBangKeChiTiet() //
				, objInput.getNgayPhatHanh() //
				, objInput.getTongSoLuongMatHang() //
				, objInput.getTongSoLuongKienHang() //
				, objInput.getMaDvtSoLuongKien() //
				, objInput.getGhiChuKhac() //
				, objInput.getNguoiTao() //
				, objInput.getNgayTao() //
				, objInput.getNguoiSua() //
				, objInput.getNgaySua() //
		);
	}

	@Override
	public void delete(CtdtBkhhEntity objInput) {
		excuteInsertUpdateDeleteUsingSp( //
				"PKG_CTDT_BKHH.deleteItem" //
				, objInput.getId() //
		);
	}

	@Override
	public Optional<CtdtBkhhEntity> findById(Long id) {
		return Optional.ofNullable(excuteObjectUsingSp(CtdtBkhhEntity.class //
				, "PKG_CTDT_BKHH.findById" //
				, id //
		));
	}

	public Page<CtdtBkhhEntity> search(KeySearchListObj objInput) {
		return convertListObject2PageOfObject(excuteListObjectUsingSp( //
				CtdtBkhhEntity.class //
				, "PKG_CTDT_BKHH.search" //
				, objInput.getPageBegin() //
				, objInput.getPageEnd() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

	public List<CtdtBkhhEntity> searchByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
		return excuteListObjectUsingSp(
				CtdtBkhhEntity.class
				, "PKG_CTDT_BKHH.searchByChungTuDinhKemTkId"
				, chungTuDinhKemTkId
		);
	}

    public Optional<CtdtBkhhEntity> searchOptionalByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
		return Optional.ofNullable(excuteObjectUsingSp(
				CtdtBkhhEntity.class
				,"PKG_CTDT_BKHH.searchOptionalByChungTuDinhKemTkId"
				,chungTuDinhKemTkId
		));
    }
}
