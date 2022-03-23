
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.CtdtHoaDonTmEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class CtdtHoaDonTmRepositoryImpl extends BaseRepositoryImplEx<CtdtHoaDonTmEntity, Long> {

	@Override
	public CtdtHoaDonTmEntity insert(CtdtHoaDonTmEntity objInput) {
		return excuteObjectUsingSp( //
				CtdtHoaDonTmEntity.class //
				, "PKG_CTDT_HOA_DON_TM.insertItem" //
				, objInput.getId() //
				, objInput.getChungTuDinhKemTkId() //
				, objInput.getSoHoaDonTm() //
				, objInput.getNgayPhatHanhHoaDonTm() //
				, objInput.getMaPlHinhThucHoaDon() //
				, objInput.getTongGiaTriHoaDon() //
				, objInput.getDonViTienThanhToan() //
				, objInput.getGhiChuKhac() //
				, objInput.getNguoiTao() //
				, objInput.getMaDoanhNghiep()
		);
	}

	@Override
	public CtdtHoaDonTmEntity update(CtdtHoaDonTmEntity objInput) {
		return excuteObjectUsingSp( //
				CtdtHoaDonTmEntity.class //
				, "PKG_CTDT_HOA_DON_TM.updateItem" //
				, objInput.getId() //
				, objInput.getChungTuDinhKemTkId() //
				, objInput.getSoHoaDonTm() //
				, objInput.getNgayPhatHanhHoaDonTm() //
				, objInput.getMaPlHinhThucHoaDon() //
				, objInput.getTongGiaTriHoaDon() //
				, objInput.getDonViTienThanhToan() //
				, objInput.getGhiChuKhac() //
				, objInput.getNguoiTao() //
				, objInput.getNgayTao() //
				, objInput.getNguoiSua() //
				, objInput.getNgaySua() //
		);
	}

	@Override
	public void delete(CtdtHoaDonTmEntity objInput) {
		excuteInsertUpdateDeleteUsingSp( //
				"PKG_CTDT_HOA_DON_TM.deleteItem" //
				, objInput.getId() //
		);
	}

	@Override
	public Optional<CtdtHoaDonTmEntity> findById(Long id) {
		return Optional.ofNullable(excuteObjectUsingSp(CtdtHoaDonTmEntity.class //
				, "PKG_CTDT_HOA_DON_TM.findById" //
				, id //
		));
	}

	public Page<CtdtHoaDonTmEntity> search(KeySearchListObj objInput) {
		return convertListObject2PageOfObject(excuteListObjectUsingSp( //
				CtdtHoaDonTmEntity.class //
				, "PKG_CTDT_HOA_DON_TM.search" //
				, objInput.getPageBegin() //
				, objInput.getPageEnd() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

	public List<CtdtHoaDonTmEntity> searchByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
		return excuteListObjectUsingSp(
				CtdtHoaDonTmEntity.class
				, "PKG_CTDT_HOA_DON_TM.searchByChungTuDinhKemTkId"
				, chungTuDinhKemTkId
		);
	}


    public Optional<CtdtHoaDonTmEntity> searchOptionalByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
		return Optional.ofNullable(excuteObjectUsingSp(
				CtdtHoaDonTmEntity.class
				,"PKG_CTDT_HOA_DON_TM.searchOptionalByChungTuDinhKemTkId"
				,chungTuDinhKemTkId
		));
    }
}
