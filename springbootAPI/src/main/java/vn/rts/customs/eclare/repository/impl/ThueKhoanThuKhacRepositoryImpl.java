
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.ThueKhoanThuKhacEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author VNETC
 */

@Repository
public class ThueKhoanThuKhacRepositoryImpl extends BaseRepositoryImplEx<ThueKhoanThuKhacEntity, Long>{
    
    @Override
    public ThueKhoanThuKhacEntity insert(ThueKhoanThuKhacEntity objInput) {
    	return excuteObjectUsingSp( //
			ThueKhoanThuKhacEntity.class //
			, "PKG_THUE_KHOAN_THU_KHAC.insertItem" //   
    		, objInput.getId() //   
    		, objInput.getDmHangHoaTkId() //   
    		, objInput.getStt() //   
    		, objInput.getThueMst() //   
    		, objInput.getMaMienThue() //   
    		, objInput.getSoTienGiamThue() //   
    		, objInput.getNguoiTao() //   
    		, objInput.getNgayTao() //
    		, objInput.getNguoiSua() //   
    		, objInput.getNgaySua() //
			, objInput.getMaDoanhNghiep()
		);
	}
    
    @Override
    public ThueKhoanThuKhacEntity update(ThueKhoanThuKhacEntity objInput) {
    	return excuteObjectUsingSp( //
			ThueKhoanThuKhacEntity.class //
			, "PKG_THUE_KHOAN_THU_KHAC.updateItem" //   
    		, objInput.getId() //   
    		, objInput.getDmHangHoaTkId() //   
    		, objInput.getStt() //   
    		, objInput.getThueMst() //   
    		, objInput.getMaMienThue() //   
    		, objInput.getSoTienGiamThue() //   
    		, objInput.getNguoiTao() //   
    		, objInput.getNgayTao() //
    		, objInput.getNguoiSua() //   
    		, objInput.getNgaySua() //
		);
	}
    
    @Override
    public void delete(ThueKhoanThuKhacEntity objInput) {
        excuteObjectUsingSp(ThueKhoanThuKhacEntity.class //
			, "PKG_THUE_KHOAN_THU_KHAC.deleteItem" //
    		, objInput.getId() //
		);
	}
    
    @Override
    public Optional<ThueKhoanThuKhacEntity> findById(Long id) {
        return Optional.ofNullable(excuteObjectUsingSp(ThueKhoanThuKhacEntity.class //
			, "PKG_THUE_KHOAN_THU_KHAC.findById" //
            , id //
		));
	}
    
    public Page<ThueKhoanThuKhacEntity> search(KeySearchListObj objInput) {
    	return convertListObject2PageOfObject(excuteListObjectUsingSp( //
			ThueKhoanThuKhacEntity.class //
			, "PKG_THUE_KHOAN_THU_KHAC.search" //
			, objInput.getPage() //
			, objInput.getSize() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

    public List<ThueKhoanThuKhacEntity> findByHangHoaTkId(long toKhaiId) {
    	return excuteListObjectUsingSp( //
    		ThueKhoanThuKhacEntity.class //
			, "PKG_THUE_KHOAN_THU_KHAC.findbyhanghoatkid" //
			, toKhaiId
		);
	}
}
    