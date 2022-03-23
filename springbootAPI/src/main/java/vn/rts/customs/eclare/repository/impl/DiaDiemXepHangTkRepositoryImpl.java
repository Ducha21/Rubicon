
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.DiaDiemXepHangTkEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class DiaDiemXepHangTkRepositoryImpl extends BaseRepositoryImplEx<DiaDiemXepHangTkEntity, Long>{
    
    @Override
    public DiaDiemXepHangTkEntity insert(DiaDiemXepHangTkEntity objInput) {
    	return excuteObjectUsingSp( //
			DiaDiemXepHangTkEntity.class //
			, "PKG_DIA_DIEM_XEP_HANG_TK.insertItem" //   
    		, objInput.getDiaChiDiaDiemXepHang() //   
    		, objInput.getMaDiaDiemXepHang() //   
    		, objInput.getMauTkHqId() //   
    		, objInput.getTenDiaDiemXepHang() //
			, objInput.getMaDoanhNghiep()
		);
	}
    
    @Override
    public DiaDiemXepHangTkEntity update(DiaDiemXepHangTkEntity objInput) {
    	return excuteObjectUsingSp( //
			DiaDiemXepHangTkEntity.class //
			, "PKG_DIA_DIEM_XEP_HANG_TK.updateItem" //   
    		, objInput.getId() //   
    		, objInput.getDiaChiDiaDiemXepHang() //   
    		, objInput.getMaDiaDiemXepHang() //   
    		, objInput.getMauTkHqId() //   
    		, objInput.getTenDiaDiemXepHang() //
		);
	}
    
    @Override
    public void delete(DiaDiemXepHangTkEntity objInput) {
        excuteInsertUpdateDeleteUsingSp( //
			"PKG_DIA_DIEM_XEP_HANG_TK.deleteItem" //
    		, objInput.getId() //
		);
	}
    
    @Override
    public Optional<DiaDiemXepHangTkEntity> findById(Long id) {
        return Optional.ofNullable(excuteObjectUsingSp(DiaDiemXepHangTkEntity.class //
			, "PKG_DIA_DIEM_XEP_HANG_TK.findById" //
            , id //
		));
	}
    
    
    public List<DiaDiemXepHangTkEntity> findByToKhaiId(String toKhaiId) {
        return excuteListObjectUsingSp(DiaDiemXepHangTkEntity.class //
			, "PKG_DIA_DIEM_XEP_HANG_TK.findByToKhaiId" //
            , toKhaiId //
		);
	}
    
    public Page<DiaDiemXepHangTkEntity> search(KeySearchListObj objInput) {
    	return convertListObject2PageOfObject(excuteListObjectUsingSp( //
			DiaDiemXepHangTkEntity.class //
			, "PKG_DIA_DIEM_XEP_HANG_TK.search" //
    		, objInput.getPageBegin() //
			, objInput.getPageEnd() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

}
    