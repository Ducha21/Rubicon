package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.ChuKySoEntity;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

@Repository
public class ChuKySoRepositoryImpl  extends BaseRepositoryImplEx<ChuKySoEntity, Long> {
	@Override
    public ChuKySoEntity insert(ChuKySoEntity objInput) {
    	return excuteObjectUsingSp( //
    			ChuKySoEntity.class //
			, "PKG_CHU_KY_SO.insertItem" //   
    		, objInput.getId() //   
    		, objInput.getDonViXuatNhapKhau()
    		, objInput.getMaDoanhNghiep()
    		, objInput.getNgayHetHieuLuc() //
    		, objInput.getNgayHetHieuLucDangKy() //
    		, objInput.getNgayHieuLucDangKy() //
    		, objInput.getNgayCoHieuLuc() // 
    		, objInput.getNguoiCap() //
    		, objInput.getSerialNumber() //
    		, objInput.getSoCMT() //
    		, objInput.getTenChungThuSo() //
    		, objInput.getNguoiDuocCap() //
		);
	}
	
	 @Override
	    public ChuKySoEntity update(ChuKySoEntity objInput) {
	    	return excuteObjectUsingSp( //
	    			ChuKySoEntity.class //
				, "PKG_CHU_KY_SO.updateItem" //   
				, objInput.getId() //   
	    		, objInput.getDonViXuatNhapKhau()
	    		, objInput.getMaDoanhNghiep() // 
	    		, objInput.getNgayHetHieuLuc() //
	    		, objInput.getNgayHetHieuLucDangKy() //
	    		, objInput.getNgayHieuLucDangKy() //
	    		, objInput.getNgayCoHieuLuc() // 
	    		, objInput.getNguoiCap() //
	    		, objInput.getSerialNumber() //
	    		, objInput.getSoCMT() //
	    		, objInput.getTenChungThuSo() //
	    		, objInput.getNguoiDuocCap() // 
			);
	}
	
	 public List<ChuKySoEntity> search(String maDoanhNghiep) {
			return excuteListObjectUsingSp( //
					ChuKySoEntity.class //
					, "PKG_CHU_KY_SO.search"
					, maDoanhNghiep //
			);
	}
	 
	 @Override
	    public Optional<ChuKySoEntity> findById(Long id) {
	        return Optional.ofNullable(excuteObjectUsingSp(ChuKySoEntity.class //
				, "PKG_CHU_KY_SO.findById" //
	            , id //
			));
		}
}
