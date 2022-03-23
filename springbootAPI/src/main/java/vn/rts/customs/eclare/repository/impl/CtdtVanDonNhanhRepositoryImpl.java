package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.CtdtVanDonNhanhEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class CtdtVanDonNhanhRepositoryImpl extends BaseRepositoryImplEx<CtdtVanDonNhanhEntity, Long> {

	@Override
	public CtdtVanDonNhanhEntity insert(CtdtVanDonNhanhEntity objInput) {
		return excuteObjectUsingSp( //
				CtdtVanDonNhanhEntity.class //
				, "PKG_CTDT_VAN_DON_NHANH.insertItem" //
				, objInput.getId() //
				, objInput.getCtdtVanDonId() //
				, objInput.getSttVanDonNhanh() //
				, objInput.getSoVanDonNhanh() //
				, objInput.getNguoiTao() //
				, objInput.getNgayTao() //
				, objInput.getNguoiSua() //
				, objInput.getNgaySua() //
				, objInput.getMaDoanhNghiep()
		);
	}

	@Override
	public CtdtVanDonNhanhEntity update(CtdtVanDonNhanhEntity objInput) {
		return excuteObjectUsingSp( //
				CtdtVanDonNhanhEntity.class //
				, "PKG_CTDT_VAN_DON_NHANH.updateItem" //
				, objInput.getId() //
				, objInput.getCtdtVanDonId() //
				, objInput.getSttVanDonNhanh() //
				, objInput.getSoVanDonNhanh() //
		);
	}

	@Override
	public void delete(CtdtVanDonNhanhEntity objInput) {
		excuteInsertUpdateDeleteUsingSp( //
				"PKG_CTDT_VAN_DON_NHANH.deleteItem" //
				, objInput.getId() //
		);
	}

	@Override
	public Optional<CtdtVanDonNhanhEntity> findById(Long id) {
		return Optional.ofNullable(excuteObjectUsingSp(CtdtVanDonNhanhEntity.class //
				, "PKG_CTDT_VAN_DON_NHANH.findById" //
				, id //
		));
	}

	public List<CtdtVanDonNhanhEntity> findByVanDonId(Long vanDonId) {
		return excuteListObjectUsingSp(CtdtVanDonNhanhEntity.class //
				, "PKG_CTDT_VAN_DON_NHANH.findByVanDonId" //
				, vanDonId //
		);
	}

	public Page<CtdtVanDonNhanhEntity> search(KeySearchListObj objInput) {
		return convertListObject2PageOfObject(excuteListObjectUsingSp( //
				CtdtVanDonNhanhEntity.class //
				, "PKG_CTDT_VAN_DON_NHANH.search" //
				, objInput.getPageBegin() //
				, objInput.getPageEnd() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

}
