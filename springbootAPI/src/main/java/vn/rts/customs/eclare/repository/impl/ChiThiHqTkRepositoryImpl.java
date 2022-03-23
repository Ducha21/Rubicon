
package vn.rts.customs.eclare.repository.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.ChiThiHqTkEntity;
import vn.rts.customs.eclare.util.VnaccsConvert;
import vn.rts.customs.eclare.request.search.KeySearchChiThiHqTk;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author VNETC
 */

@Repository
public class ChiThiHqTkRepositoryImpl extends BaseRepositoryImplEx<ChiThiHqTkEntity, Long>{
	@Override
    public ChiThiHqTkEntity insert(ChiThiHqTkEntity objInput) {
    	return excuteObjectUsingSp( //
			ChiThiHqTkEntity.class //
			, "PKG_CHI_THI_HQ_TK.insertItem" //   
    		, objInput.getId() //   
    		, objInput.getMauTkHqId() //   
    		, objInput.getNgayChiThiHq() //   
    		, objInput.getNgaySua() //     
    		, objInput.getNguoiSua() //   
    		, objInput.getNguoiTao() //   
    		, objInput.getNoiDungChiThiHq() //   
    		, objInput.getPlChiThiHq() //   
    		, objInput.getTenChiThiHq() //   
    		, objInput.getMaChucNang() //
			, objInput.getMaDoanhNghiep()
		);
	}
    
    @Override
    public ChiThiHqTkEntity update(ChiThiHqTkEntity objInput) {
    	return excuteObjectUsingSp( //
			ChiThiHqTkEntity.class //
			, "PKG_CHI_THI_HQ_TK.updateItem" //   
    		, objInput.getId() //   
    		, objInput.getMauTkHqId() //   
    		, objInput.getNgayChiThiHq() //   
    		, objInput.getNgaySua() //   
    		, objInput.getNguoiSua() //   
    		, objInput.getNguoiTao() //   
    		, objInput.getNoiDungChiThiHq() //   
    		, objInput.getPlChiThiHq() //   
    		, objInput.getTenChiThiHq() //   
    		, objInput.getMaChucNang() //
		);
	}

    
    @Override
    public Optional<ChiThiHqTkEntity> findById(Long id) {
        return Optional.ofNullable(excuteObjectUsingSp(ChiThiHqTkEntity.class //
			, "PKG_CHI_THI_HQ_TK.findById" //
            , id //
		));
	}

    public Page<ChiThiHqTkEntity> search(String toKhaiId, KeySearchChiThiHqTk objInput) {
    	return convertListObject2PageOfObject(excuteListObjectUsingSp( //
			ChiThiHqTkEntity.class //
			, "PKG_CHI_THI_HQ_TK.search" //
			, toKhaiId
            , objInput.getPlChiThiHq() //
            , VnaccsConvert.string2Date(objInput.getNgayTaoTuNgay(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
            , VnaccsConvert.string2Date(objInput.getNgayTaoDenNgay(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
            , objInput.getSoTiepNhan() //
            , objInput.getSoTk() //
            , objInput.getLuongToKhai() //
            , objInput.getMaThongDiep() //
    		, objInput.getPage() //
			, objInput.getSize() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

}
    