
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import vn.rts.customs.eclare.entity.CtdtTktgEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class CtdtTktgRepositoryImpl extends BaseRepositoryImplEx<CtdtTktgEntity, Long>{
    
    @Override
    public CtdtTktgEntity insert(CtdtTktgEntity objInput) {
    	return excuteObjectUsingSp( //
			CtdtTktgEntity.class //
			, "PKG_CTDT_TKTG.insertItem" //   
    		, objInput.getId() //   
    		, objInput.getChungTuDinhKemTkId() //   
    		, objInput.getGhiChuKhac() //   
    		, objInput.getNguoiTao() //   
    		, objInput.getNgayTao() //   
    		, objInput.getNguoiSua() //   
    		, objInput.getNgaySua() //
			, objInput.getMaDoanhNghiep()
		);
	}
    
    @Override
    public CtdtTktgEntity update(CtdtTktgEntity objInput) {
    	return excuteObjectUsingSp( //
			CtdtTktgEntity.class //
			, "PKG_CTDT_TKTG.updateItem" //   
    		, objInput.getId() //   
    		, objInput.getChungTuDinhKemTkId() //   
    		, objInput.getGhiChuKhac() //   
    		, objInput.getNguoiTao() //   
    		, objInput.getNgayTao() //   
    		, objInput.getNguoiSua() //   
    		, objInput.getNgaySua() //
		);
	}
    
    @Override
    public void delete(CtdtTktgEntity objInput) {
        excuteInsertUpdateDeleteUsingSp( //
			"PKG_CTDT_TKTG.deleteItem" //
    		, objInput.getId() //
		);
	}
    
    @Override
    public Optional<CtdtTktgEntity> findById(Long id) {
        return Optional.ofNullable(excuteObjectUsingSp(CtdtTktgEntity.class //
			, "PKG_CTDT_TKTG.findById" //
            , id //
		));
	}
    
    public Page<CtdtTktgEntity> search(KeySearchListObj objInput) {
    	return convertListObject2PageOfObject(excuteListObjectUsingSp( //
			CtdtTktgEntity.class //
			, "PKG_CTDT_TKTG.search" //
    		, objInput.getPageBegin() //
			, objInput.getPageEnd() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

	public List<CtdtTktgEntity> searchByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
		return excuteListObjectUsingSp(
				CtdtTktgEntity.class
				, "PKG_CTDT_TKTG.searchByChungTuDinhKemTkId"
				, chungTuDinhKemTkId
		);
	}

    public Optional<CtdtTktgEntity> searchOptionalByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
    	return Optional.ofNullable(excuteObjectUsingSp(
    			CtdtTktgEntity.class
				,"PKG_CTDT_TKTG.searchOptionalByChungTuDinhKemTkId"
				,chungTuDinhKemTkId
		));
    }
}
    