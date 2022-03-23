
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import vn.rts.customs.eclare.entity.CtdtCtat5PhanTramEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class CtdtCtat5PhanTramRepositoryImpl extends BaseRepositoryImplEx<CtdtCtat5PhanTramEntity, Long>{
    
    @Override
    public CtdtCtat5PhanTramEntity insert(CtdtCtat5PhanTramEntity objInput) {
    	return excuteObjectUsingSp( //
			CtdtCtat5PhanTramEntity.class //
			, "PKG_CTDT_CTAT_5_PHAN_TRAM.insertItem" //   
    		, objInput.getId() //   
    		, objInput.getMaPlHinhThucChungTu() //   
    		, objInput.getGhiChuKhac() //   
    		, objInput.getChungTuDinhKemTkId() //   
    		, objInput.getNguoiTao() //
			, objInput.getMaDoanhNghiep()
		);
	}
    
    @Override
    public CtdtCtat5PhanTramEntity update(CtdtCtat5PhanTramEntity objInput) {
    	return excuteObjectUsingSp( //
			CtdtCtat5PhanTramEntity.class //
			, "PKG_CTDT_CTAT_5_PHAN_TRAM.updateItem" //   
    		, objInput.getId() //   
    		, objInput.getMaPlHinhThucChungTu() //   
    		, objInput.getGhiChuKhac() //   
    		, objInput.getChungTuDinhKemTkId() //   
    		, objInput.getNguoiTao() //   
    		, objInput.getNgayTao() //   
    		, objInput.getNguoiSua() //   
    		, objInput.getNgaySua() //
		);
	}
    
    @Override
    public void delete(CtdtCtat5PhanTramEntity objInput) {
        excuteInsertUpdateDeleteUsingSp( //
			"PKG_CTDT_CTAT_5_PHAN_TRAM.deleteItem" //
    		, objInput.getId() //
		);
	}
    
    @Override
    public Optional<CtdtCtat5PhanTramEntity> findById(Long id) {
        return Optional.ofNullable(excuteObjectUsingSp(CtdtCtat5PhanTramEntity.class //
			, "PKG_CTDT_CTAT_5_PHAN_TRAM.findById" //
            , id //
		));
	}
    
    public Page<CtdtCtat5PhanTramEntity> search(KeySearchListObj objInput) {
    	return convertListObject2PageOfObject(excuteListObjectUsingSp( //
			CtdtCtat5PhanTramEntity.class //
			, "PKG_CTDT_CTAT_5_PHAN_TRAM.search" //
    		, objInput.getPageBegin() //
			, objInput.getPageEnd() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

	public List<CtdtCtat5PhanTramEntity> searchByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
		return excuteListObjectUsingSp(
				CtdtCtat5PhanTramEntity.class
				, "PKG_CTDT_CTAT_5_PHAN_TRAM.searchByChungTuDinhKemTkId"
				, chungTuDinhKemTkId
		);
	}

    public Optional<CtdtCtat5PhanTramEntity> searchOptionalByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
    	return Optional.ofNullable(excuteObjectUsingSp(
    			CtdtCtat5PhanTramEntity.class
				,"PKG_CTDT_CTAT_5_PHAN_TRAM.searchOptionalByChungTuDinhKemTkId"
				,chungTuDinhKemTkId
		));
    }
}
    