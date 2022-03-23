
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.SoDinhKemKbdtEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author VNETC
 */

@Repository
public class SoDinhKemKbdtRepositoryImpl extends BaseRepositoryImplEx<SoDinhKemKbdtEntity, Long>{
    
    @Override
    public SoDinhKemKbdtEntity insert(SoDinhKemKbdtEntity objInput) {
    	return excuteObjectUsingSp( //
			SoDinhKemKbdtEntity.class //
			, "PKG_SO_DINH_KEM_KBDT.insertItem" //   
    		, objInput.getId() //   
    		, objInput.getMauTkHqId() //   
    		, objInput.getPlDinhKemDt() //   
    		, objInput.getSoDinhKemDt() //   
    		, objInput.getNguoiTao() //   
    		, objInput.getNgayTao() //
    		, objInput.getNguoiSua() //   
    		, objInput.getNgaySua() //
    		, objInput.getSoChungTu() //   
    		, objInput.getTenChungTu() //   
    		, objInput.getNgayChungTu() //   
    		, objInput.getGhiChu() //   
    		, objInput.getTenFileChungTu() //   
    		, objInput.getUrlFile() //   
    		, objInput.getSizeFile() //
			, objInput.getMaDoanhNghiep()
		);
	}
    
    @Override
    public SoDinhKemKbdtEntity update(SoDinhKemKbdtEntity objInput) {
    	return excuteObjectUsingSp( //
			SoDinhKemKbdtEntity.class //
			, "PKG_SO_DINH_KEM_KBDT.updateItem" //   
    		, objInput.getId() //   
    		, objInput.getMauTkHqId() //   
    		, objInput.getPlDinhKemDt() //   
    		, objInput.getSoDinhKemDt() //   
    		, objInput.getNguoiTao() //   
    		, objInput.getNgayTao() //
    		, objInput.getNguoiSua() //   
    		, objInput.getNgaySua() //
    		, objInput.getSoChungTu() //   
    		, objInput.getTenChungTu() //   
    		, objInput.getNgayChungTu() //   
    		, objInput.getGhiChu() //   
    		, objInput.getTenFileChungTu() //   
    		, objInput.getUrlFile() //   
    		, objInput.getSizeFile() //
		);
	}
    
    @Override
    public void delete(SoDinhKemKbdtEntity objInput) {
    	excuteInsertUpdateDeleteUsingSp(
			"PKG_SO_DINH_KEM_KBDT.deleteItem" //
    		, objInput.getId() //
		);
	}
    
    public void deleteByToKhaiId(List<SoDinhKemKbdtEntity> objInputs) {
    	if(objInputs == null) return;
        for(int i=0; i<objInputs.size(); i++) {
        	delete(objInputs.get(i));
        }
	} 
    
    @Override
    public Optional<SoDinhKemKbdtEntity> findById(Long id) {
        return Optional.ofNullable(excuteObjectUsingSp(SoDinhKemKbdtEntity.class //
			, "PKG_SO_DINH_KEM_KBDT.findById" //
            , id //
		));
	}
    
    public Page<SoDinhKemKbdtEntity> search(KeySearchListObj objInput) {
    	return convertListObject2PageOfObject(excuteListObjectUsingSp( //
			SoDinhKemKbdtEntity.class //
			, "PKG_SO_DINH_KEM_KBDT.search" //
			, objInput.getPage() //
			, objInput.getSize() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

    public List<SoDinhKemKbdtEntity> findByToKhaiId(String toKhaiId) {
    	return excuteListObjectUsingSp( //
    		SoDinhKemKbdtEntity.class //
			, "PKG_SO_DINH_KEM_KBDT.findbytokhaiid" //
			, toKhaiId
		);
	}

}
    