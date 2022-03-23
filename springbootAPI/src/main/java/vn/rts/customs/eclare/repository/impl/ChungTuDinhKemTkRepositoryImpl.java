
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.ChungTuDinhKemTkEntity;
import vn.rts.customs.eclare.request.search.KeySearchChungTuDinhKemTk;
import vn.rts.customs.eclare.request.search.KeySearchCtdkTkByMore;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class ChungTuDinhKemTkRepositoryImpl extends BaseRepositoryImplEx<ChungTuDinhKemTkEntity, String> {

	@Override
	public ChungTuDinhKemTkEntity insert(ChungTuDinhKemTkEntity objInput) {
		UUID uuid = UUID.randomUUID();
		objInput.setId(uuid.toString());
		return excuteObjectUsingSp( //
				ChungTuDinhKemTkEntity.class //
				, "PKG_CHUNG_TU_DINH_KEM_TK.insertItem" //
				, objInput.getId() //
				, objInput.getMauTkHqId() //
				, objInput.getLoaiChungTu() //
				, objInput.getKenhKhaiBao() //
				, objInput.getChucNangChungTu() //
				, objInput.getNoiKhaiBao() //
				, objInput.getHqTiepNhanChungTu() //
				, objInput.getNguoiTao() //
				, objInput.getNgayTao() //
				, objInput.getMaDoanhNghiep()
		);
	}

	@Override
	public ChungTuDinhKemTkEntity update(ChungTuDinhKemTkEntity objInput) {
		return excuteObjectUsingSp( //
				ChungTuDinhKemTkEntity.class //
				, "PKG_CHUNG_TU_DINH_KEM_TK.updateItem" //
				, objInput.getId() //
				, objInput.getMauTkHqId() //
				, objInput.getLoaiChungTu() //
				, objInput.getKenhKhaiBao() //
				, objInput.getChucNangChungTu() //
				, objInput.getNoiKhaiBao() //
				, objInput.getHqTiepNhanChungTu() //
				, objInput.getNguoiTao() //
		);
	}

	public ChungTuDinhKemTkEntity updateStatus(ChungTuDinhKemTkEntity objInput) {
		return excuteObjectUsingSp( //
				ChungTuDinhKemTkEntity.class //
				, "PKG_CHUNG_TU_DINH_KEM_TK.updateStatus" //
				, objInput.getId() //
				, objInput.getSoTn()
				, objInput.getTrangThai() //
				, objInput.getNoiDungHqPhanHoi()
				, objInput.getNgayHqPhanHoi()
				, objInput.getNgayDangKyCt()
				, objInput.getHqTraLoiCt()
		);
	}

	@Override
	public void delete(ChungTuDinhKemTkEntity objInput) {
		excuteInsertUpdateDeleteUsingSp( //
				"PKG_CHUNG_TU_DINH_KEM_TK.deleteItem" //
				, objInput.getId() //
		);
	}

	@Override
	public Optional<ChungTuDinhKemTkEntity> findById(String id) {
		return Optional.ofNullable(excuteObjectUsingSp(ChungTuDinhKemTkEntity.class //
				, "PKG_CHUNG_TU_DINH_KEM_TK.findById" //
				, id //
		));
	}

	public Page<ChungTuDinhKemTkEntity> search(KeySearchListObj objInput) {
		return convertListObject2PageOfObject(excuteListObjectUsingSp( //
				ChungTuDinhKemTkEntity.class //
				, "PKG_CHUNG_TU_DINH_KEM_TK.search" //
				, objInput.getPageBegin() //
				, objInput.getPageEnd() //
		), PageRequest.of(objInput.getPage(), objInput.getSize()));
	}

	public Page<ChungTuDinhKemTkEntity> selectCtdkTkBymauTkHqId(KeySearchChungTuDinhKemTk params) {
		return convertListObject2PageOfObject(excuteListObjectUsingSp(
				ChungTuDinhKemTkEntity.class
				, "PKG_CHUNG_TU_DINH_KEM_TK.selectCtdkTkBymauTkHqId"
				, params.getToKhaiId()
				, params.getId()
				, params.getLoaiChungTu()
				, params.getHqTiepNhanChungTu()
				, params.getNgayTao()
				, params.getNgayHqPhanHoi()
		), PageRequest.of(params.getPage(), params.getSize()));
	}


    public List<ChungTuDinhKemTkEntity> selectCtdkTkByMore(KeySearchCtdkTkByMore objInput) {
		return excuteListObjectUsingSp(
				ChungTuDinhKemTkEntity.class
				, "PKG_CHUNG_TU_DINH_KEM_TK.selectCtdkTkByMore"
				, objInput.getId()
				, objInput.getLoaiChungTu()
				, objInput.getSoDangKyChungTu()
				, objInput.getHqTiepNhanChungTu()
				, objInput.getNgayKhaiChungTu()
				, objInput.getNgayDangKyChungTu()
		);
    }
}