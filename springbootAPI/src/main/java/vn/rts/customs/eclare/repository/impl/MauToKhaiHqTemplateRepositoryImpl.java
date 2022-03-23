
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.MauToKhaiHqTemplateEntity;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class MauToKhaiHqTemplateRepositoryImpl extends BaseRepositoryImplEx<MauToKhaiHqTemplateEntity, Long> {

	@Override
	public MauToKhaiHqTemplateEntity insert(MauToKhaiHqTemplateEntity objInput) {
		return excuteObjectUsingSp( //
				MauToKhaiHqTemplateEntity.class //
				, "PKG_MAU_TO_KHAI_HQ_TEMPLATE.insertItem" //
				, objInput.getId() //
				, objInput.getName() //
				, objInput.getDoanhNghiepId() //
				, objInput.getContent() //
				, objInput.getLoaiTkHq() //
				, objInput.getNgayTao() //
				, objInput.getMaDoanhNghiep()
		);
	}

	@Override
	public MauToKhaiHqTemplateEntity update(MauToKhaiHqTemplateEntity objInput) {
		return excuteObjectUsingSp( //
				MauToKhaiHqTemplateEntity.class //
				, "PKG_MAU_TO_KHAI_HQ_TEMPLATE.updateItem" //
				, objInput.getId() //
				, objInput.getName() //
				, objInput.getDoanhNghiepId() //
				, objInput.getContent() //
				, objInput.getLoaiTkHq() //
		);
	}

	@Override
	public void delete(MauToKhaiHqTemplateEntity objInput) {
		excuteInsertUpdateDeleteUsingSp( //
				"PKG_MAU_TO_KHAI_HQ_TEMPLATE.deleteItem" //
				, objInput.getId() //
		);
	}

	@Override
	public Optional<MauToKhaiHqTemplateEntity> findById(Long id) {
		return Optional.ofNullable(excuteObjectUsingSp(MauToKhaiHqTemplateEntity.class //
				, "PKG_MAU_TO_KHAI_HQ_TEMPLATE.findById" //
				, id //
		));
	}

	public List<MauToKhaiHqTemplateEntity> search(String doanhNghiepId, String loaiToKhai, String templateName) {
		return excuteListObjectUsingSp( //
				MauToKhaiHqTemplateEntity.class //
				, "PKG_MAU_TO_KHAI_HQ_TEMPLATE.search"
				, doanhNghiepId //
				, templateName //
				, loaiToKhai //
		);
	}

}
