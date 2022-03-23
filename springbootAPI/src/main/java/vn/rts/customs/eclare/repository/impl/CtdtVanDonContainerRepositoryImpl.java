package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.CtdtVanDonContainerEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class CtdtVanDonContainerRepositoryImpl extends BaseRepositoryImplEx<CtdtVanDonContainerEntity, Long> {

	@Override
	public CtdtVanDonContainerEntity insert(CtdtVanDonContainerEntity objInput) {
		return excuteObjectUsingSp( //
				CtdtVanDonContainerEntity.class //
				, "PKG_CTDT_VAN_DON_CONTAINER.insertItem" //
				, objInput.getId() //
				, objInput.getCtdtVanDonId() //
				, objInput.getSoContainer() //
				, objInput.getSoSeal() //
				, objInput.getNguoiTao() //
				, objInput.getNgayTao() //
				, objInput.getNguoiSua() //
				, objInput.getNgaySua() //
				, objInput.getMaDoanhNghiep()
		);
	}

	@Override
	public CtdtVanDonContainerEntity update(CtdtVanDonContainerEntity objInput) {
		return excuteObjectUsingSp( //
				CtdtVanDonContainerEntity.class //
				, "PKG_CTDT_VAN_DON_CONTAINER.updateItem" //
				, objInput.getId() //
				, objInput.getCtdtVanDonId() //
				, objInput.getSoContainer() //
				, objInput.getSoSeal() //
		);
	}

	@Override
	public void delete(CtdtVanDonContainerEntity objInput) {
		excuteInsertUpdateDeleteUsingSp( //
				"PKG_CTDT_VAN_DON_CONTAINER.deleteItem" //
				, objInput.getId() //
		);
	}

	@Override
	public Optional<CtdtVanDonContainerEntity> findById(Long id) {
		return Optional.ofNullable(excuteObjectUsingSp(CtdtVanDonContainerEntity.class //
				, "PKG_CTDT_VAN_DON_CONTAINER.findById" //
				, id //
		));
	}

	public List<CtdtVanDonContainerEntity> findByVanDonId(Long vanDonId) {
		return excuteListObjectUsingSp(CtdtVanDonContainerEntity.class //
				, "PKG_CTDT_VAN_DON_CONTAINER.findByVanDonId" //
				, vanDonId //
		);
	}

	public Page<CtdtVanDonContainerEntity> search(KeySearchListObj objInput) {
		return convertListObject2PageOfObject(excuteListObjectUsingSp( //
				CtdtVanDonContainerEntity.class //
				, "PKG_CTDT_VAN_DON_CONTAINER.search" //
				, objInput.getPageBegin() //
				, objInput.getPageEnd() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

}
