
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.KhoanDieuChinhTkEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author VNETC
 */

@Repository
public class KhoanDieuChinhTkRepositoryImpl extends BaseRepositoryImplEx<KhoanDieuChinhTkEntity, String>{
    
    @Override
    public KhoanDieuChinhTkEntity insert(KhoanDieuChinhTkEntity objInput) {
    	return excuteObjectUsingSp( //
			KhoanDieuChinhTkEntity.class //
			, "PKG_KHOAN_DIEU_CHINH_TK.insertItem" //   
    		, objInput.getId() //   
    		, objInput.getMauKbHqId() //   
    		, objInput.getDcTriGiaMa() //   
    		, objInput.getDcTriGiaMaPl() //   
    		, objInput.getDcTriGiaMaNt() //   
    		, objInput.getDcTriGia() //   
    		, objInput.getDcTriGiaTongHsPhanBo() //   
    		, objInput.getNguoiTao() //   
    		, objInput.getNgayTao() //
    		, objInput.getNguoiSua() //   
    		, objInput.getNgaySua() //
    		, objInput.getDcStt()
			, objInput.getMaDoanhNghiep()
		);
	}
    
    @Override
    public KhoanDieuChinhTkEntity update(KhoanDieuChinhTkEntity objInput) {
    	return excuteObjectUsingSp( //
			KhoanDieuChinhTkEntity.class //
			, "PKG_KHOAN_DIEU_CHINH_TK.updateItem" //   
    		, objInput.getId() //   
    		, objInput.getMauKbHqId() //   
    		, objInput.getDcTriGiaMa() //   
    		, objInput.getDcTriGiaMaPl() //   
    		, objInput.getDcTriGiaMaNt() //   
    		, objInput.getDcTriGia() //   
    		, objInput.getDcTriGiaTongHsPhanBo() //   
    		, objInput.getNguoiTao() //   
    		, objInput.getNgayTao() //
    		, objInput.getNguoiSua() //   
    		, objInput.getNgaySua() //
    		, objInput.getDcStt()
		);
	}
    
    @Override
    public void delete(KhoanDieuChinhTkEntity objInput) {
        excuteObjectUsingSp(KhoanDieuChinhTkEntity.class //
			, "PKG_KHOAN_DIEU_CHINH_TK.deleteItem" //
    		, objInput.getId() //
		);
	}
    
    @Override
    public Optional<KhoanDieuChinhTkEntity> findById(String id) {
        return Optional.ofNullable(excuteObjectUsingSp(KhoanDieuChinhTkEntity.class //
			, "PKG_KHOAN_DIEU_CHINH_TK.findById" //
            , id //
		));
	}
    
    public Page<KhoanDieuChinhTkEntity> search(KeySearchListObj objInput) {
    	return convertListObject2PageOfObject(excuteListObjectUsingSp( //
			KhoanDieuChinhTkEntity.class //
			, "PKG_KHOAN_DIEU_CHINH_TK.search" //
			, objInput.getPage() //
			, objInput.getSize() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

    public List<KhoanDieuChinhTkEntity> findByToKhaiId(String toKhaiId) {
    	return excuteListObjectUsingSp( //
    		KhoanDieuChinhTkEntity.class //
			, "PKG_KHOAN_DIEU_CHINH_TK.findbytokhaiid" //
			, toKhaiId
		);
	}

}
    