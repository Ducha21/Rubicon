
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.CoHangHoaTkEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.eclare.util.VnaccsConvert;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class CoHangHoaTkRepositoryImpl extends BaseRepositoryImplEx<CoHangHoaTkEntity, Long>{
    @Override
    public CoHangHoaTkEntity insert(CoHangHoaTkEntity objInput) {
    	return excuteObjectUsingSp( //
			CoHangHoaTkEntity.class //
			, "PKG_CO_HANG_HOA_TK.insertItem" //   
    		, objInput.getId() //   
    		, VnaccsConvert.string2Date(objInput.getCoNgay(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
    		, objInput.getCoNguoiCap() //   
    		, objInput.getCoNoicap() //   
    		, objInput.getCoSo() //   
    		, VnaccsConvert.string2Date(objInput.getCoNgayHetHan(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
    		, objInput.getDmHangHoaTkId()
			, objInput.getMaDoanhNghiep()
		);
	}
    
    @Override
    public CoHangHoaTkEntity update(CoHangHoaTkEntity objInput) {
    	return excuteObjectUsingSp( //
			CoHangHoaTkEntity.class //
			, "PKG_CO_HANG_HOA_TK.updateItem" //   
    		, objInput.getId() //   
    		, VnaccsConvert.string2Date(objInput.getCoNgay(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
    		, objInput.getCoNguoiCap() //   
    		, objInput.getCoNoicap() //   
    		, objInput.getCoSo() //   
    		, VnaccsConvert.string2Date(objInput.getCoNgayHetHan(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
		);
	}
    
    @Override
    public void delete(CoHangHoaTkEntity objInput) {
        excuteInsertUpdateDeleteUsingSp( //
			"PKG_CO_HANG_HOA_TK.deleteItem" //
    		, objInput.getId() //
		);
	}
    
    @Override
    public Optional<CoHangHoaTkEntity> findById(Long id) {
        return Optional.ofNullable(excuteObjectUsingSp(CoHangHoaTkEntity.class //
			, "PKG_CO_HANG_HOA_TK.findById" //
            , id //
		));
	}
    
    public List<CoHangHoaTkEntity> findByDmHangHoaTkId(Long dmHangHoaTkId) {
        return excuteListObjectUsingSp(CoHangHoaTkEntity.class //
    			, "PKG_CO_HANG_HOA_TK.findByDmHangHoaTkId" //
                , dmHangHoaTkId //
    		);
	}
    
    public Page<CoHangHoaTkEntity> search(KeySearchListObj objInput) {
    	return convertListObject2PageOfObject(excuteListObjectUsingSp( //
			CoHangHoaTkEntity.class //
			, "PKG_CO_HANG_HOA_TK.search" //
    		, objInput.getPageBegin() //
			, objInput.getPageEnd() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

}
    