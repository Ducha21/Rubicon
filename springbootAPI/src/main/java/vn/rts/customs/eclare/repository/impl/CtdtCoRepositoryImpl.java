
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.CtdtCoEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class CtdtCoRepositoryImpl extends BaseRepositoryImplEx<CtdtCoEntity, Long> {

	@Override
	public CtdtCoEntity insert(CtdtCoEntity objInput) {
		return excuteObjectUsingSp( //
				CtdtCoEntity.class //
				, "PKG_CTDT_CO.insertItem" //
				, objInput.getId() //
				, objInput.getSoCo() //
				, objInput.getLoaiCo() //
				, objInput.getNgayCapCo() //
				, objInput.getToChucCo() //
				, objInput.getNguoiCapCo() //
				, objInput.getNuocCapCo() //
				, objInput.getThoiDiemNopCo() //
				, objInput.getGhiChuKhac() //
				, objInput.getNguoiTao() //
				, objInput.getChungTuDinhKemTkId() //
				, objInput.getMaDoanhNghiep()
		);
	}

	@Override
	public CtdtCoEntity update(CtdtCoEntity objInput) {
		return excuteObjectUsingSp( //
				CtdtCoEntity.class //
				, "PKG_CTDT_CO.updateItem" //
				, objInput.getId() //
				, objInput.getSoCo() //
				, objInput.getLoaiCo() //
				, objInput.getNgayCapCo() //
				, objInput.getToChucCo() //
				, objInput.getNguoiCapCo() //
				, objInput.getNuocCapCo() //
				, objInput.getThoiDiemNopCo() //
				, objInput.getGhiChuKhac() //
				, objInput.getNguoiTao() //
				, objInput.getNgayTao() //
				, objInput.getNguoiSua() //
				, objInput.getNgaySua() //
				, objInput.getChungTuDinhKemTkId() //
		);
	}

	@Override
	public void delete(CtdtCoEntity objInput) {
		excuteInsertUpdateDeleteUsingSp( //
				"PKG_CTDT_CO.deleteItem" //
				, objInput.getId() //
		);
	}

	@Override
	public Optional<CtdtCoEntity> findById(Long id) {
		return Optional.ofNullable(excuteObjectUsingSp(CtdtCoEntity.class //
				, "PKG_CTDT_CO.findById" //
				, id //
		));
	}

	public Page<CtdtCoEntity> search(KeySearchListObj objInput) {
		return convertListObject2PageOfObject(excuteListObjectUsingSp( //
				CtdtCoEntity.class //
				, "PKG_CTDT_CO.search" //
				, objInput.getPageBegin() //
				, objInput.getPageEnd() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

	public List<CtdtCoEntity> searchByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
		return excuteListObjectUsingSp(
				CtdtCoEntity.class
				, "PKG_CTDT_CO.searchByChungTuDinhKemTkId"
				, chungTuDinhKemTkId
		);
	}

    public Optional<CtdtCoEntity> searchOptionalByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
		return Optional.ofNullable(excuteObjectUsingSp(
				CtdtCoEntity.class
				,"PKG_CTDT_CO.searchOptionalByChungTuDinhKemTkId"
				,chungTuDinhKemTkId
		));
    }
}