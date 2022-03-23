
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import vn.rts.customs.eclare.entity.CtdtHopDongUtEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class CtdtHopDongUtRepositoryImpl extends BaseRepositoryImplEx<CtdtHopDongUtEntity, Long>{
    
    @Override
    public CtdtHopDongUtEntity insert(CtdtHopDongUtEntity objInput) {
    	return excuteObjectUsingSp( //
			CtdtHopDongUtEntity.class //
			, "PKG_CTDT_HOP_DONG_UT.insertItem" //   
    		, objInput.getId() //   
    		, objInput.getChungTuDinhKemTkId() //   
    		, objInput.getTenNguoiUyThac() //   
    		, objInput.getMaNguoiUyThac() //   
    		, objInput.getDiaChiNguoiUyThac() //   
    		, objInput.getTenNguoiNhanUyThac() //   
    		, objInput.getMaNguoiNhanUyThac() //   
    		, objInput.getDiaChiNguoiNhanUyThac() //   
    		, objInput.getGhiChuKhac() //   
    		, objInput.getNguoiTao() //   
    		, objInput.getNgayTao() //   
    		, objInput.getNguoiSua() //   
    		, objInput.getNgaySua() //
			, objInput.getMaDoanhNghiep()
		);
	}
    
    @Override
    public CtdtHopDongUtEntity update(CtdtHopDongUtEntity objInput) {
    	return excuteObjectUsingSp( //
			CtdtHopDongUtEntity.class //
			, "PKG_CTDT_HOP_DONG_UT.updateItem" //   
    		, objInput.getId() //   
    		, objInput.getChungTuDinhKemTkId() //   
    		, objInput.getTenNguoiUyThac() //   
    		, objInput.getMaNguoiUyThac() //   
    		, objInput.getDiaChiNguoiUyThac() //   
    		, objInput.getTenNguoiNhanUyThac() //   
    		, objInput.getMaNguoiNhanUyThac() //   
    		, objInput.getDiaChiNguoiNhanUyThac() //   
    		, objInput.getGhiChuKhac() //   
    		, objInput.getNguoiTao() //   
    		, objInput.getNgayTao() //   
    		, objInput.getNguoiSua() //   
    		, objInput.getNgaySua() //
		);
	}
    
    @Override
    public void delete(CtdtHopDongUtEntity objInput) {
        excuteInsertUpdateDeleteUsingSp( //
			"PKG_CTDT_HOP_DONG_UT.deleteItem" //
    		, objInput.getId() //
		);
	}
    
    @Override
    public Optional<CtdtHopDongUtEntity> findById(Long id) {
        return Optional.ofNullable(excuteObjectUsingSp(CtdtHopDongUtEntity.class //
			, "PKG_CTDT_HOP_DONG_UT.findById" //
            , id //
		));
	}
    
    public Page<CtdtHopDongUtEntity> search(KeySearchListObj objInput) {
    	return convertListObject2PageOfObject(excuteListObjectUsingSp( //
			CtdtHopDongUtEntity.class //
			, "PKG_CTDT_HOP_DONG_UT.search" //
    		, objInput.getPageBegin() //
			, objInput.getPageEnd() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

	public List<CtdtHopDongUtEntity> searchByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
		return excuteListObjectUsingSp(
				CtdtHopDongUtEntity.class
				, "PKG_CTDT_HOP_DONG_UT.searchByChungTuDinhKemTkId"
				, chungTuDinhKemTkId
		);
	}

    public Optional<CtdtHopDongUtEntity> searchOptionalByChungTuDinhKemTkId(String chungTuDinhKemTkId) {
    	return Optional.ofNullable(excuteObjectUsingSp(
    			CtdtHopDongUtEntity.class
				,"PKG_CTDT_HOP_DONG_UT.searchOptionalByChungTuDinhKemTkId"
				,chungTuDinhKemTkId
		));
    }
}
    