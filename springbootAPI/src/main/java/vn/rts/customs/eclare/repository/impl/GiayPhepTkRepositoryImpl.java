
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.GiayPhepTkEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author VNETC
 */

@Repository
public class GiayPhepTkRepositoryImpl extends BaseRepositoryImplEx<GiayPhepTkEntity, Long>{
    
	@Override
    public GiayPhepTkEntity insert(GiayPhepTkEntity objInput) {
    	return excuteObjectUsingSp( //
			GiayPhepTkEntity.class //
			, "PKG_GIAY_PHEP_TK.insertItem" //   
    		, objInput.getId() //   
    		, objInput.getMaPlGiayPhepNk() //   
    		, objInput.getMauKbHqId() //   
    		, objInput.getNgaySua() //
    		, objInput.getNgayTao() //
    		, objInput.getNguoiSua() //   
    		, objInput.getNguoiTao() //   
    		, objInput.getSoPkGiayPhepNk() //
			, objInput.getMaDoanhNghiep()
		);
	}
    
    @Override
    public GiayPhepTkEntity update(GiayPhepTkEntity objInput) {
    	return excuteObjectUsingSp( //
			GiayPhepTkEntity.class //
			, "PKG_GIAY_PHEP_TK.updateItem" //   
    		, objInput.getId() //   
    		, objInput.getMaPlGiayPhepNk() //   
    		, objInput.getMauKbHqId() //   
    		, objInput.getNgaySua() //
    		, objInput.getNgayTao() //
    		, objInput.getNguoiSua() //   
    		, objInput.getNguoiTao() //   
    		, objInput.getSoPkGiayPhepNk() //
		);
	}
    
    @Override
    public void delete(GiayPhepTkEntity objInput) {
        excuteInsertUpdateDeleteUsingSp( //
			"PKG_GIAY_PHEP_TK.deleteItem" //
    		, objInput.getId() //
		);
	}
    
    public void deleteByToKhaiId(List<GiayPhepTkEntity> objInputs) {
    	if(objInputs == null) return;
        for(int i=0; i<objInputs.size(); i++) {
        	delete(objInputs.get(i));
        }
	} 
    
    @Override
    public Optional<GiayPhepTkEntity> findById(Long id) {
        return Optional.ofNullable(excuteObjectUsingSp(GiayPhepTkEntity.class //
			, "PKG_GIAY_PHEP_TK.findById" //
            , id //
		));
	}
    
    public Page<GiayPhepTkEntity> search(KeySearchListObj objInput) {
    	return convertListObject2PageOfObject(excuteListObjectUsingSp( //
			GiayPhepTkEntity.class //
			, "PKG_GIAY_PHEP_TK.search" //
    		, objInput.getPage() //
			, objInput.getSize() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

    public List<GiayPhepTkEntity> findByToKhaiId(String toKhaiId) {
    	return excuteListObjectUsingSp( //
    		GiayPhepTkEntity.class //
			, "PKG_GIAY_PHEP_TK.findbytokhaiid" //
			, toKhaiId
		);
	}

}
    