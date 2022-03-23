
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.TkVanDonEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.eclare.util.VnaccsConvert;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author VNETC
 */

@Repository
public class TkVanDonRepositoryImpl extends BaseRepositoryImplEx<TkVanDonEntity, Long> {

	@Override
    public TkVanDonEntity insert(TkVanDonEntity objInput) {
    	return excuteObjectUsingSp( //
			TkVanDonEntity.class //
			, "PKG_TK_VAN_DON.insertItem" //   
    		, objInput.getMauKbHqId() //
			, objInput.getNgaySua() //
			, objInput.getNgayTao()
    		, VnaccsConvert.string2Date(objInput.getNgayVanDon(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
    		, objInput.getNguoiSua() //   
    		, objInput.getNguoiTao() //   
    		, objInput.getSoDinhDanh() //   
    		, objInput.getSoLuongKien() //   
    		, objInput.getSoLuongKienDvt() //   
    		, objInput.getStt() //   
    		, objInput.getTrongLuong() //   
    		, objInput.getTrongLuongDvt() //   
    		, objInput.getVanDonSo() //
			, objInput.getMaDoanhNghiep()
		);
	}
    
    @Override
    public TkVanDonEntity update(TkVanDonEntity objInput) {
    	return excuteObjectUsingSp( //
			TkVanDonEntity.class //
			, "PKG_TK_VAN_DON.updateItem" //   
    		, objInput.getId() //   
    		, objInput.getMauKbHqId() //
			, objInput.getNgaySua() //
			, objInput.getNgayTao()
    		, VnaccsConvert.string2Date(objInput.getNgayVanDon(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
    		, objInput.getNguoiSua() //   
    		, objInput.getNguoiTao() //   
    		, objInput.getSoDinhDanh() //   
    		, objInput.getSoLuongKien() //   
    		, objInput.getSoLuongKienDvt() //   
    		, objInput.getStt() //   
    		, objInput.getTrongLuong() //   
    		, objInput.getTrongLuongDvt() //   
    		, objInput.getVanDonSo() //
		);
	}
    
    @Override
    public void delete(TkVanDonEntity objInput) {
        excuteInsertUpdateDeleteUsingSp( //
			"PKG_TK_VAN_DON.deleteItem" //
    		, objInput.getId() //
		);
	}
    
    public void deleteByToKhaiId(List<TkVanDonEntity> objInputs) {
    	if(objInputs == null) return;
        for(int i=0; i<objInputs.size(); i++) {
        	delete(objInputs.get(i));
        }
	}
    
    @Override
    public Optional<TkVanDonEntity> findById(Long id) {
        return Optional.ofNullable(excuteObjectUsingSp(TkVanDonEntity.class //
			, "PKG_TK_VAN_DON.findById" //
            , id //
		));
	}
    
    public Page<TkVanDonEntity> search(KeySearchListObj objInput) {
    	return convertListObject2PageOfObject(excuteListObjectUsingSp( //
			TkVanDonEntity.class //
			, "PKG_TK_VAN_DON.search" //
    		, objInput.getPage() //
			, objInput.getSize() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

    public List<TkVanDonEntity> findByToKhaiId(String toKhaiId) {
    	return excuteListObjectUsingSp( //
    		TkVanDonEntity.class //
			, "PKG_TK_VAN_DON.findbytokhaiid" //
			, toKhaiId
		);
	}
}
