
package vn.rts.customs.eclare.repository.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.HuyToKhaiEntity;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.eclare.util.VnaccsConvert;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author VNETC
 */

@Repository
public class HuyToKhaiRepositoryImpl extends BaseRepositoryImplEx<HuyToKhaiEntity, Long> {

	@Override
	public HuyToKhaiEntity insert(HuyToKhaiEntity objInput) {
		return excuteObjectUsingSp( //
				HuyToKhaiEntity.class //
				, "PKG_HUY_TO_KHAI.insertItem" //
				, objInput.getId() //
				, objInput.getDiaChiNguoiKhaiHq() //
				, objInput.getKenhKhaiBao() //
				, objInput.getLyDoHuy() //
				, objInput.getDonViHqTiepNhan() //
				, objInput.getMaLyDoHuy() //
				, objInput.getMaNguoiKhaiHq() //
				, VnaccsConvert.string2Date(objInput.getNgayKbHuy(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, objInput.getNgaySua() //
				, objInput.getNgayTao() //
				, objInput.getNguoiSua() //
				, objInput.getNguoiTao() //
				, objInput.getNoiKbHuy() //
				, objInput.getSdtNguoiKhaiHq() //
				, objInput.getSoTk() //
				, objInput.getTenNguoiKhaiHq() //
				, objInput.getMauTkHqId() //
				, objInput.getMaDoanhNghiep()
		);
	}

	@Override
	public HuyToKhaiEntity update(HuyToKhaiEntity objInput) {
		return excuteObjectUsingSp( //
				HuyToKhaiEntity.class //
				, "PKG_HUY_TO_KHAI.updateItem" //
				, objInput.getId() //
				, objInput.getDiaChiNguoiKhaiHq() //
				, objInput.getKenhKhaiBao() //
				, objInput.getLyDoHuy() //
				, objInput.getDonViHqTiepNhan() //
				, objInput.getMaLyDoHuy() //
				, objInput.getMaNguoiKhaiHq() //
				, objInput.getNgayKbHuy() //
				, objInput.getNgaySua() //
				, objInput.getNgayTao() //
				, objInput.getNguoiSua() //
				, objInput.getNguoiTao() //
				, objInput.getNoiKbHuy() //
				, objInput.getSdtNguoiKhaiHq() //
				, objInput.getSoTk() //
				, objInput.getTenNguoiKhaiHq() //
				, objInput.getMauTkHqId() //
		);
	}

	@Override
	public void delete(HuyToKhaiEntity objInput) {
		excuteInsertUpdateDeleteUsingSp( //
				"PKG_HUY_TO_KHAI.deleteItem" //
				, objInput.getId() //
		);
	}

	@Override
	public Optional<HuyToKhaiEntity> findById(Long id) {
		return Optional.ofNullable(excuteObjectUsingSp(HuyToKhaiEntity.class //
				, "PKG_HUY_TO_KHAI.findById" //
				, id //
		));
	}

	public Page<HuyToKhaiEntity> search(KeySearchListObj objInput) {
		return convertListObject2PageOfObject(excuteListObjectUsingSp( //
				HuyToKhaiEntity.class //
				, "PKG_HUY_TO_KHAI.search" //
				, objInput.getPage() //
				, objInput.getSize() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

}
