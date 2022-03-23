
package vn.rts.customs.eclare.repository.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.CtdtFileDinhKemEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class CtdtFileDinhKemRepositoryImpl extends BaseRepositoryImplEx<CtdtFileDinhKemEntity, Long> {

	@Override
	public CtdtFileDinhKemEntity insert(CtdtFileDinhKemEntity objInput) {
		return excuteObjectUsingSp( //
				CtdtFileDinhKemEntity.class //
				, "PKG_CTDT_FILE_DINH_KEM.insertItem" //
				, objInput.getId() //
				, objInput.getChungTuDinhKemTkId() //
				, objInput.getNgaySua() //
				, objInput.getNgayTao() //
				, objInput.getNguoiSua() //
				, objInput.getNguoiTao() //
				, objInput.getPhanLoaiDinhKemDt() //
				, objInput.getSizeFile() //
				, objInput.getTenFileChungTu() //
				, objInput.getLoaiChungTuId() //
				, objInput.getFileId() //
				, objInput.getMaDoanhNghiep()
		);
	}


	@Override
	public CtdtFileDinhKemEntity update(CtdtFileDinhKemEntity objInput) {
		return excuteObjectUsingSp( //
				CtdtFileDinhKemEntity.class //
				, "PKG_CTDT_FILE_DINH_KEM.updateItem" //
				, objInput.getId() //
				, objInput.getChungTuDinhKemTkId() //
				, objInput.getNgaySua() //
				, objInput.getNgayTao() //
				, objInput.getNguoiSua() //
				, objInput.getNguoiTao() //
				, objInput.getPhanLoaiDinhKemDt() //
				, objInput.getSizeFile() //
				, objInput.getTenFileChungTu() //
				, objInput.getLoaiChungTuId() //
				, objInput.getFileId() //
		);
	}

	@Override
	public void delete(CtdtFileDinhKemEntity objInput) {
		excuteInsertUpdateDeleteUsingSp( //
				"PKG_CTDT_FILE_DINH_KEM.deleteItem" //
				, objInput.getId() //
		);
	}

	@Override
	public Optional<CtdtFileDinhKemEntity> findById(Long id) {
		return Optional.ofNullable(excuteObjectUsingSp(CtdtFileDinhKemEntity.class //
				, "PKG_CTDT_FILE_DINH_KEM.findById" //
				, id //
		));
	}

	public Page<CtdtFileDinhKemEntity> search(KeySearchListObj objInput) {
		return convertListObject2PageOfObject(excuteListObjectUsingSp( //
				CtdtFileDinhKemEntity.class //
				, "PKG_CTDT_FILE_DINH_KEM.search" //
				, objInput.getPageBegin() //
				, objInput.getPageEnd() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

	public CtdtFileDinhKemEntity searchByChungTuDinhKemTkIdAndFileId(Long fileId, String chungTuDinhKemTkId ){
		return excuteObjectUsingSp(
				CtdtFileDinhKemEntity.class
				, "PKG_CTDT_FILE_DINH_KEM.searchByChungTuDinhKemTkId"
				, fileId
				, chungTuDinhKemTkId
		);
	}
}
