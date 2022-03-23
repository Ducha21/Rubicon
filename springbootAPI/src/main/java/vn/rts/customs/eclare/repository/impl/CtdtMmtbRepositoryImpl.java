
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import vn.rts.customs.eclare.entity.CtdtMmtbEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class CtdtMmtbRepositoryImpl extends BaseRepositoryImplEx<CtdtMmtbEntity, Long>{
    
    @Override
    public CtdtMmtbEntity insert(CtdtMmtbEntity objInput) {
    	return excuteObjectUsingSp( //
			CtdtMmtbEntity.class //
			, "PKG_CTDT_MMTB.insertItem" //   
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
    public CtdtMmtbEntity update(CtdtMmtbEntity objInput) {
    	return excuteObjectUsingSp( //
			CtdtMmtbEntity.class //
			, "PKG_CTDT_MMTB.updateItem" //   
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
    public void delete(CtdtMmtbEntity objInput) {
        excuteInsertUpdateDeleteUsingSp( //
			"PKG_CTDT_MMTB.deleteItem" //
    		, objInput.getId() //
		);
	}
    
    @Override
    public Optional<CtdtMmtbEntity> findById(Long id) {
        return Optional.ofNullable(excuteObjectUsingSp(CtdtMmtbEntity.class //
			, "PKG_CTDT_MMTB.findById" //
            , id //
		));
	}
    
    public Page<CtdtMmtbEntity> search(KeySearchListObj objInput) {
    	return convertListObject2PageOfObject(excuteListObjectUsingSp( //
			CtdtMmtbEntity.class //
			, "PKG_CTDT_MMTB.search" //
    		, objInput.getPageBegin() //
			, objInput.getPageEnd() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

	public List<CtdtMmtbEntity> searchByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
		return excuteListObjectUsingSp(
				CtdtMmtbEntity.class
				, "PKG_CTDT_MMTB.searchByChungTuDinhKemTkId"
				, chungTuDinhKemTkId
		);
	}

    public Optional<CtdtMmtbEntity> searchOptionalByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
    	return Optional.ofNullable(excuteObjectUsingSp(
				CtdtMmtbEntity.class
				,"PKG_CTDT_MMTB.searchOptionalByChungTuDinhKemTkId"
				,chungTuDinhKemTkId
		));
    }
}
    