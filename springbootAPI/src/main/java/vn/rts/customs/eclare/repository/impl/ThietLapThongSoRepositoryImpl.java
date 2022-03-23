
package vn.rts.customs.eclare.repository.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.ThietLapThongSoEntity;
import vn.rts.customs.eclare.request.search.KeySearchSystemSettingDto;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class ThietLapThongSoRepositoryImpl extends BaseRepositoryImplEx<ThietLapThongSoEntity, Long>{
    
    @Override
    public ThietLapThongSoEntity insert(ThietLapThongSoEntity objInput) {
    	return excuteObjectUsingSp( //
			ThietLapThongSoEntity.class //
			, "PKG_THIET_LAP_THONG_SO.insertItem" //   
    		, objInput.getId() //   
    		, objInput.getAuthenticationType() //      
    		, objInput.getDoanhNghiepId() //   
    		, objInput.getDomain() //   
    		, new Date()
    		, objInput.getPassword() //   
    		, objInput.getPort()  
    		, objInput.getUsername() //
			, objInput.getMaDoanhNghiep()
		);
	}
    
    @Override
    public ThietLapThongSoEntity update(ThietLapThongSoEntity objInput) {
    	return excuteObjectUsingSp( //
			ThietLapThongSoEntity.class //
			, "PKG_THIET_LAP_THONG_SO.updateItem" //
			, objInput.getId() //   
    		, objInput.getAuthenticationType() //      
    		, objInput.getDoanhNghiepId() //
    		, objInput.getDomain() //   
    		, objInput.getNgayTao() //   
    		, objInput.getPassword() //   
    		, objInput.getPort()  
    		, objInput.getUsername() //  
		);
	}
    
    @Override
    public void delete(ThietLapThongSoEntity objInput) {
        excuteInsertUpdateDeleteUsingSp( //
			"PKG_THIET_LAP_THONG_SO.deleteItem" //
    		, objInput.getId() //
		);
	}
    
    @Override
    public Optional<ThietLapThongSoEntity> findById(Long id) {
        return Optional.ofNullable(excuteObjectUsingSp(ThietLapThongSoEntity.class //
			, "PKG_THIET_LAP_THONG_SO.findById" //
            , id //
		));
	}

	public Optional<ThietLapThongSoEntity> findByDoanhNghiepId(String doanhNghiepId) {
		return Optional.ofNullable(excuteObjectUsingSp(ThietLapThongSoEntity.class //
				, "PKG_THIET_LAP_THONG_SO.findByDoanhNghiepId" //
				, doanhNghiepId //
		));
	}
    
    public Page<ThietLapThongSoEntity> search(KeySearchSystemSettingDto objInput) {
    	return convertListObject2PageOfObject(excuteListObjectUsingSp( //
			ThietLapThongSoEntity.class //
			, "PKG_THIET_LAP_THONG_SO.search" //
            , objInput.getId() //
            , objInput.getDoanhNghiepId() //
            , objInput.getDomain() //
            , objInput.getUsername() //
    		, objInput.getPageBegin() //
			, objInput.getPageEnd() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

}
    