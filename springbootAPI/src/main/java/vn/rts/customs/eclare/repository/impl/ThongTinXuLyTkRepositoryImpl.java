
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.ThongTinXuLyTkEntity;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_DEV
 */

@Repository
public class ThongTinXuLyTkRepositoryImpl extends BaseRepositoryImplEx<ThongTinXuLyTkEntity, Long> {

    @Override
    public ThongTinXuLyTkEntity insert(ThongTinXuLyTkEntity objInput) {
        return excuteObjectUsingSp( //
                ThongTinXuLyTkEntity.class //
                , "PKG_THONG_TIN_XU_LY_TK.insertItem" //
                , objInput.getMaChucNang() //
                , objInput.getMauTkHqId() //
                , objInput.getNgayTao() //
                , objInput.getMaDoanhNghiep()
        );
    }

    @Override
    public ThongTinXuLyTkEntity update(ThongTinXuLyTkEntity objInput) {
        return excuteObjectUsingSp( //
                ThongTinXuLyTkEntity.class //
                , "PKG_THONG_TIN_XU_LY_TK.updateItem" //
                , objInput.getId() //
                , objInput.getKetQuaXuLy() //
                , objInput.getNgayPhanHoi() //
                , objInput.getNoiDungPhanHoi() //
        );
    }

    @Override
    public void delete(ThongTinXuLyTkEntity objInput) {
        excuteInsertUpdateDeleteUsingSp( //
                "PKG_THONG_TIN_XU_LY_TK.deleteItem" //
                , objInput.getId() //
        );
    }

    @Override
    public Optional<ThongTinXuLyTkEntity> findById(Long id) {
        return Optional.ofNullable(excuteObjectUsingSp(ThongTinXuLyTkEntity.class //
                , "PKG_THONG_TIN_XU_LY_TK.findById" //
                , id //
        ));
    }

    public List<ThongTinXuLyTkEntity> findByMauTkHqId(String mauTkHqId) {
        return excuteListObjectUsingSp(
                ThongTinXuLyTkEntity.class,
                "PKG_THONG_TIN_XU_LY_TK.findByMauTkHqId",
                mauTkHqId);
    }


}
    