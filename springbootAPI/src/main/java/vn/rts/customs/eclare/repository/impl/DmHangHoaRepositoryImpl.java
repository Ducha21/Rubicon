
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.DmHangHoaEntity;
import vn.rts.customs.eclare.request.search.KeySearchKeyValue;
import vn.rts.customs.eclare.request.search.KeyValueDto;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class DmHangHoaRepositoryImpl extends BaseRepositoryImplEx<DmHangHoaEntity, Long> {
	private static final String S_FORMAT_DATE = "yyyy-MM-dd";

	@Override
	public DmHangHoaEntity insert(DmHangHoaEntity objInput) {
		return excuteObjectUsingSp( //
				DmHangHoaEntity.class //
				, "PKG_DM_HANG_HOA.insertItem" //
				, objInput.getId() //
				, objInput.getMaHang() //
				, objInput.getMaHs() //
				, objInput.getTenHang() //
				, objInput.getDanhNghiepId() //
				, objInput.getNguoiTao() //
				, objInput.getNgayTao()
				, objInput.getNguoiSua() //
				, objInput.getMaDoanhNghiep()
		);
	}

	@Override
	public DmHangHoaEntity update(DmHangHoaEntity objInput) {
		return excuteObjectUsingSp( //
				DmHangHoaEntity.class //
				, "PKG_DM_HANG_HOA.updateItem" //
				, objInput.getId() //
				, objInput.getMaHang() //
				, objInput.getMaHs() //
				, objInput.getTenHang() //
				, objInput.getDanhNghiepId() //
				, objInput.getNguoiTao() //
				, objInput.getNgayTao()
				, objInput.getNguoiSua() //
				, objInput.getNgaySua()
		);
	}

	@Override
	public void delete(DmHangHoaEntity objInput) {
		excuteInsertUpdateDeleteUsingSp( //
				"PKG_DM_HANG_HOA.deleteItem" //
				, objInput.getId() //
		);
	}

	@Override
	public Optional<DmHangHoaEntity> findById(Long id) {
		return Optional.ofNullable(excuteObjectUsingSp(DmHangHoaEntity.class //
				, "PKG_DM_HANG_HOA.findById" //
				, id //
		));
	}

	public List<KeyValueDto> search(KeySearchKeyValue objInput) {
		return excuteListObjectUsingSp( //
				DmHangHoaEntity.class //
				, "PKG_DM_HANG_HOA.search" //
				, objInput.getKey() //
				, objInput.getValue() //
				, objInput.getPage() //
				, objInput.getSize() //
		);
	}

}