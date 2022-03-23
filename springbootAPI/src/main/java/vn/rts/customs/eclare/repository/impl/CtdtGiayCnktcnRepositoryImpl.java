package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.CtdtGiayCnktcnEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class CtdtGiayCnktcnRepositoryImpl extends BaseRepositoryImplEx<CtdtGiayCnktcnEntity, String> {

	@Override
	public CtdtGiayCnktcnEntity insert(CtdtGiayCnktcnEntity objInput) {
		return excuteObjectUsingSp( //
				CtdtGiayCnktcnEntity.class //
				, "PKG_CTDT_GIAY_CNKTCN.insertItem" //
				, objInput.getId() //
				, objInput.getChungTuDinhKemTkId() //
				, objInput.getMaPlGiayCnktcn() //
				, objInput.getLoaiGiayCnktcn() //
				, objInput.getTenGiayCnktcn() //
				, objInput.getSoGiayCnktcn() //
				, objInput.getNgayGiayCnktcn() //
				, objInput.getNgayHetHanGiayCnktcn() //
				, objInput.getNoiCapGiayCnktcn() //
				, objInput.getNguoiCapGiayCnktcn() //
				, objInput.getGhiChuKhac() //
				, objInput.getNguoiTao() //
				, objInput.getHangHoaId()
				, objInput.getMaDoanhNghiep()
		);
	}

	@Override
	public CtdtGiayCnktcnEntity update(CtdtGiayCnktcnEntity objInput) {
		return excuteObjectUsingSp( //
				CtdtGiayCnktcnEntity.class //
				, "PKG_CTDT_GIAY_CNKTCN.updateItem" //
				, objInput.getId() //
				, objInput.getChungTuDinhKemTkId() //
				, objInput.getMaPlGiayCnktcn() //
				, objInput.getLoaiGiayCnktcn() //
				, objInput.getTenGiayCnktcn() //
				, objInput.getSoGiayCnktcn() //
				, objInput.getNgayGiayCnktcn() //
				, objInput.getNgayHetHanGiayCnktcn() //
				, objInput.getNoiCapGiayCnktcn() //
				, objInput.getNguoiCapGiayCnktcn() //
				, objInput.getGhiChuKhac() //
		);
	}

	@Override
	public void delete(CtdtGiayCnktcnEntity objInput) {
		excuteInsertUpdateDeleteUsingSp( //
				"PKG_CTDT_GIAY_CNKTCN.deleteItem" //
				, objInput.getId() //
		);
	}

	@Override
	public Optional<CtdtGiayCnktcnEntity> findById(String id) {
		return Optional.ofNullable(excuteObjectUsingSp(CtdtGiayCnktcnEntity.class //
				, "PKG_CTDT_GIAY_CNKTCN.findById" //
				, id //
		));
	}

	public Page<CtdtGiayCnktcnEntity> search(KeySearchListObj objInput) {
		return convertListObject2PageOfObject(excuteListObjectUsingSp( //
				CtdtGiayCnktcnEntity.class //
				, "PKG_CTDT_GIAY_CNKTCN.search" //
				, objInput.getPageBegin() //
				, objInput.getPageEnd() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

	public List<CtdtGiayCnktcnEntity> searchByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
		return excuteListObjectUsingSp(
				CtdtGiayCnktcnEntity.class
				, "PKG_CTDT_GIAY_CNKTCN.searchByChungTuDinhKemTkId"
				, chungTuDinhKemTkId
		);
	}

    public Optional<CtdtGiayCnktcnEntity> searchOptionalByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
		return Optional.ofNullable(excuteObjectUsingSp(
				CtdtGiayCnktcnEntity.class
				,"PKG_CTDT_GIAY_CNKTCN.searchOptionalByChungTuDinhKemTkId"
				,chungTuDinhKemTkId
		));
    }
}
