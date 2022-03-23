package vn.rts.customs.eclare.repository.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.CauHinhHeThongEntity;
import vn.rts.customs.eclare.request.search.KeySearchCauHinhHeThong;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

@Repository
public class CauHinhHeThongRepositoryImpl extends BaseRepositoryImplEx<CauHinhHeThongEntity, Long> {

	@Override
	public CauHinhHeThongEntity insert(CauHinhHeThongEntity objInput) {
		return excuteObjectUsingSp( //
				CauHinhHeThongEntity.class //
				, "PKG_CAU_HINH_HE_THONG.insertItem" //
				, objInput.getId() //
				, objInput.getNhom() //
				, objInput.getKey() //
				, objInput.getValue()
				, objInput.getIsDeletable()
				, objInput.getMaDoanhNghiep()
		);
	}
	
	public Page<CauHinhHeThongEntity> search(KeySearchCauHinhHeThong objInput) {
    	return convertListObject2PageOfObject(excuteListObjectUsingSp( //
			CauHinhHeThongEntity.class //
			, "PKG_CAU_HINH_HE_THONG.search" //
            , objInput.getKey() //
            , objInput.getNhom() //
    		, objInput.getPageBegin() //
			, objInput.getPageEnd()), 
	    	PageRequest.of(objInput.getPage(), objInput.getSize())
    	);
	}
	
	@Override
	public Optional<CauHinhHeThongEntity> findById(Long id) {
		return Optional.ofNullable(excuteObjectUsingSp(CauHinhHeThongEntity.class //
				, "PKG_CAU_HINH_HE_THONG.findById" //
				, id //
		));
	}

	@Override
	public void delete(CauHinhHeThongEntity objInput) {
		excuteInsertUpdateDeleteUsingSp( //
				"PKG_CAU_HINH_HE_THONG.deleteItem" //
				, objInput.getId() //
		);
	}
	
	@Override
	public CauHinhHeThongEntity update(CauHinhHeThongEntity objInput) {
		return excuteObjectUsingSp( //
				CauHinhHeThongEntity.class //
				, "PKG_CAU_HINH_HE_THONG.updateItem" //
				, objInput.getId() //
				, objInput.getKey() //
		        , objInput.getNhom() //
		        , objInput.getValue() //
				, objInput.getIsDeletable()
		);
	}
}
