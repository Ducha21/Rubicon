
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.CtdtCmtcEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class CtdtCmtcRepositoryImpl extends BaseRepositoryImplEx<CtdtCmtcEntity, Long> {

	@Override
	public CtdtCmtcEntity insert(CtdtCmtcEntity objInput) {
		return excuteObjectUsingSp( //
				CtdtCmtcEntity.class //
				, "PKG_CTDT_CMTC.insertItem" //
				, objInput.getId() //
				, objInput.getChungTuDinhKemTkId() //
				, objInput.getMaPlHinhThucChungTu() //
				, objInput.getSoChungTu() //
				, objInput.getNgayPhatHanhChungTu() //
				, objInput.getNganhNgheKinhDoanh() //
				, objInput.getCoSoPhapLy() //
				, objInput.getGhiChuKhac() //
				, objInput.getNguoiTao() //
				, objInput.getMaDoanhNghiep()
		);
	}

	@Override
	public CtdtCmtcEntity update(CtdtCmtcEntity objInput) {
		return excuteObjectUsingSp( //
				CtdtCmtcEntity.class //
				, "PKG_CTDT_CMTC.updateItem" //
				, objInput.getId() //
				, objInput.getChungTuDinhKemTkId() //
				, objInput.getMaPlHinhThucChungTu() //
				, objInput.getSoChungTu() //
				, objInput.getNgayPhatHanhChungTu() //
				, objInput.getNganhNgheKinhDoanh() //
				, objInput.getCoSoPhapLy() //
				, objInput.getGhiChuKhac() //
				, objInput.getNguoiTao() //
				, objInput.getNgayTao() //
				, objInput.getNguoiSua() //
				, objInput.getNgaySua() //
		);
	}

	@Override
	public void delete(CtdtCmtcEntity objInput) {
		excuteInsertUpdateDeleteUsingSp( //
				"PKG_CTDT_CMTC.deleteItem" //
				, objInput.getId() //
		);
	}

	@Override
	public Optional<CtdtCmtcEntity> findById(Long id) {
		return Optional.ofNullable(excuteObjectUsingSp(CtdtCmtcEntity.class //
				, "PKG_CTDT_CMTC.findById" //
				, id //
		));
	}

	public Page<CtdtCmtcEntity> search(KeySearchListObj objInput) {
		return convertListObject2PageOfObject(excuteListObjectUsingSp( //
				CtdtCmtcEntity.class //
				, "PKG_CTDT_CMTC.search" //
				, objInput.getPageBegin() //
				, objInput.getPageEnd() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

	public List<CtdtCmtcEntity> searchByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
		return excuteListObjectUsingSp(
				CtdtCmtcEntity.class
				, "PKG_CTDT_CMTC.searchByChungTuDinhKemTkId"
				, chungTuDinhKemTkId
		);
	}

    public Optional<CtdtCmtcEntity> searchOptionalByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
		return Optional.ofNullable(excuteObjectUsingSp(
				CtdtCmtcEntity.class
				,"PKG_CTDT_CMTC.searchOptionalByChungTuDinhKemTkId"
				,chungTuDinhKemTkId
		));
    }
}
