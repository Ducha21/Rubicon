
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.TtTrungChuyenEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author VNETC
 */

@Repository
public class TtTrungChuyenRepositoryImpl extends BaseRepositoryImplEx<TtTrungChuyenEntity, Long>{
    
    @Override
    public TtTrungChuyenEntity insert(TtTrungChuyenEntity objInput) {
    	return excuteObjectUsingSp( //
			TtTrungChuyenEntity.class //
			, "PKG_TT_TRUNG_CHUYEN.insertItem" //   
    		, objInput.getId() //   
    		, objInput.getMauKbHqId() //   
    		, objInput.getDiaDiemTrungChuyenBcbt() //   
    		, objInput.getNgayDen() //
    		, objInput.getNgayKhoiHanh() //
    		, objInput.getNguoiTao() //   
    		, objInput.getNgayTao() //
    		, objInput.getNguoiSua() //   
    		, objInput.getNgaySua() //
			, objInput.getMaDoanhNghiep()
		);
	}
    
    @Override
    public TtTrungChuyenEntity update(TtTrungChuyenEntity objInput) {
    	return excuteObjectUsingSp( //
			TtTrungChuyenEntity.class //
			, "PKG_TT_TRUNG_CHUYEN.updateItem" //   
    		, objInput.getId() //   
    		, objInput.getMauKbHqId() //   
    		, objInput.getDiaDiemTrungChuyenBcbt() //   
    		, objInput.getNgayDen() //
    		, objInput.getNgayKhoiHanh() // 
    		, objInput.getNguoiTao() //   
    		, objInput.getNgayTao() //
    		, objInput.getNguoiSua() //   
    		, objInput.getNgaySua() //
		);
	}
    
    @Override
    public void delete(TtTrungChuyenEntity objInput) {
    	excuteInsertUpdateDeleteUsingSp(//
			"PKG_TT_TRUNG_CHUYEN.deleteItem" //
    		, objInput.getId() //
		);
	}
    
    public void deleteByToKhaiId(List<TtTrungChuyenEntity> objInputs) {
    	if(objInputs == null) return;
        for(int i=0; i<objInputs.size(); i++) {
        	delete(objInputs.get(i));
        }
	} 
    
    @Override
    public Optional<TtTrungChuyenEntity> findById(Long id) {
        return Optional.ofNullable(excuteObjectUsingSp(TtTrungChuyenEntity.class //
			, "PKG_TT_TRUNG_CHUYEN.findById" //
            , id //
		));
	}
    
    public Page<TtTrungChuyenEntity> search(KeySearchListObj objInput) {
    	return convertListObject2PageOfObject(excuteListObjectUsingSp( //
			TtTrungChuyenEntity.class //
			, "PKG_TT_TRUNG_CHUYEN.search" //
			, objInput.getPage() //
			, objInput.getSize() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

    public List<TtTrungChuyenEntity> findByToKhaiId(String toKhaiId) {
    	return excuteListObjectUsingSp( //
    		TtTrungChuyenEntity.class //
			, "PKG_TT_TRUNG_CHUYEN.findbytokhaiid" //
			, toKhaiId
		);
	}

}
    