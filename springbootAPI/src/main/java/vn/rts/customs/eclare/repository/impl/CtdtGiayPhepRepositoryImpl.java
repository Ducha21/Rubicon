package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.CtdtGiayPhepEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

@Repository
public class CtdtGiayPhepRepositoryImpl extends BaseRepositoryImplEx<CtdtGiayPhepEntity, Long> {

	@Override
	public CtdtGiayPhepEntity insert(CtdtGiayPhepEntity objInput) {
		return excuteObjectUsingSp( //
				CtdtGiayPhepEntity.class //
				, "PKG_CTDT_GIAY_PHEP.insertItem" //
				, objInput.getId() //
				, objInput.getMaPlGiayPhep() //
				, objInput.getChungTuDinhKemTkId() //
				, objInput.getNguoiTao() //
				, objInput.getSoGiayPhep() //
				, objInput.getLoaiGiayPhep() //
				, objInput.getNgayCapGiayPhep() //
				, objInput.getNgayHetHanGiayPhep() //
				, objInput.getNoiCapGiayPhep() //
				, objInput.getNguoiCapGiayPhep() //
				, objInput.getGhiChuKhac() //
				, objInput.getHangHoaId() //
				, objInput.getMaDoanhNghiep()
		);
	}

	@Override
    public CtdtGiayPhepEntity update(CtdtGiayPhepEntity objInput) {
    	return excuteObjectUsingSp( //
			CtdtGiayPhepEntity.class //
			, "PKG_CTDT_GIAY_PHEP.updateItem" //   
    		, objInput.getId() //   
    		, objInput.getMaPlGiayPhep() //   
    		, objInput.getChungTuDinhKemTkId() //   
    		, objInput.getNgaySua() //   
    		, objInput.getNguoiSua() //   
    		, objInput.getSoGiayPhep() //   
    		, objInput.getLoaiGiayPhep() //   
    		, objInput.getNgayCapGiayPhep() //   
    		, objInput.getNgayHetHanGiayPhep() //   
    		, objInput.getNoiCapGiayPhep() //   
    		, objInput.getNguoiCapGiayPhep() //   
    		, objInput.getGhiChuKhac() //
		);
	}

	@Override
	public void delete(CtdtGiayPhepEntity objInput) {
		excuteInsertUpdateDeleteUsingSp( //
				"PKG_CTDT_GIAY_PHEP.deleteItem" //
				, objInput.getId() //
		);
	}

	@Override
	public Optional<CtdtGiayPhepEntity> findById(Long id) {
		return Optional.ofNullable(excuteObjectUsingSp(CtdtGiayPhepEntity.class //
				, "PKG_CTDT_GIAY_PHEP.findById" //
				, id //
		));
	}

	public Page<CtdtGiayPhepEntity> search(KeySearchListObj objInput) {
		return convertListObject2PageOfObject(excuteListObjectUsingSp( //
				CtdtGiayPhepEntity.class //
				, "PKG_CTDT_GIAY_PHEP.search" //
				, objInput.getPageBegin() //
				, objInput.getPageEnd() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

	public List<CtdtGiayPhepEntity> searchByChungTuDinhKemTkId(String chungTuDinhKemTkId){
		return excuteListObjectUsingSp(
				CtdtGiayPhepEntity.class
				, "PKG_CTDT_GIAY_PHEP.searchByChungTuDinhKemTkId"
				, chungTuDinhKemTkId
		);
	}

	public Optional<CtdtGiayPhepEntity> searchOptionalByChungTuDinhKemTkId(String chungTuDinhKemTkId){
		return Optional.ofNullable(excuteObjectUsingSp(CtdtGiayPhepEntity.class //
				, "PKG_CTDT_GIAY_PHEP.searchOptionalByChungTuDinhKemTkId" //
				, chungTuDinhKemTkId //
		));
	}

}
