
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import vn.rts.customs.eclare.entity.ContainerInfoEntity;
import vn.rts.customs.eclare.request.search.KeySearchContainers;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author CCS4EO_TEST
 */

@Repository
public class ContainerInfoRepositoryImpl extends BaseRepositoryImplEx<ContainerInfoEntity, String> {

    @Override
    public ContainerInfoEntity insert(ContainerInfoEntity objInput) {
        UUID uuid = UUID.randomUUID();
        objInput.setId(uuid.toString());
        return excuteObjectUsingSp( //
                ContainerInfoEntity.class //
                , "PKG_CONTAINER_INFO.insertItem" //
                , objInput.getId() //
                , objInput.getVanDonSo() //
                , objInput.getSoContainer() //
                , objInput.getSoSeal() //
                , objInput.getNguoiTao() //
                , objInput.getNgayTao() //
                , objInput.getNguoiSua() //
                , objInput.getNgaySua() //
                , objInput.getGhiChu() //
                , objInput.getSoSealHq() //
                , objInput.getTkContainersId() //
                , objInput.getContainer() //
                , objInput.getSoLuongHang() //
                , objInput.getSoLuongHangDvt() //
                , objInput.getTrongLuongHang() //
                , objInput.getTongTrongLuongDvt() //
                , objInput.getMaDoanhNghiep()
        );
    }

    @Override
    public ContainerInfoEntity update(ContainerInfoEntity objInput) {
        return excuteObjectUsingSp( //
                ContainerInfoEntity.class //
                , "PKG_CONTAINER_INFO.updateItem" //
                , objInput.getId() //
                , objInput.getVanDonSo() //
                , objInput.getSoContainer() //
                , objInput.getSoSeal() //
                , objInput.getNguoiTao() //
                , objInput.getNgayTao() //
                , objInput.getNguoiSua() //
                , objInput.getNgaySua() //
                , objInput.getGhiChu() //
                , objInput.getSoSealHq() //
                , objInput.getTkContainersId() //
                , objInput.getContainer() //
                , objInput.getSoLuongHang() //
                , objInput.getSoLuongHangDvt() //
                , objInput.getTrongLuongHang() //
                , objInput.getTongTrongLuongDvt() //
        );
    }

    public void delete(String tkContainerId) {
        excuteInsertUpdateDeleteUsingSp( //
                "PKG_CONTAINER_INFO.deleteItem" //
                , tkContainerId
        );
    }

    @Override
    public Optional<ContainerInfoEntity> findById(String id) {
        return Optional.ofNullable(excuteObjectUsingSp(ContainerInfoEntity.class //
                , "PKG_CONTAINER_INFO.findById" //
                , id //
        ));
    }


    public List<ContainerInfoEntity> findByTkContainerId(String tkContaiterId) {
        return excuteListObjectUsingSp(
                ContainerInfoEntity.class
                , "PKG_CONTAINER_INFO.findByTkContainerId"
                , tkContaiterId
        );
    }

    public Page<ContainerInfoEntity> search(KeySearchContainers objInput) {
        return convertListObject2PageOfObject(excuteListObjectUsingSp( //
                ContainerInfoEntity.class //
                , "PKG_CONTAINER_INFO.search" //
                , objInput.getTkContainerId() //
                , objInput.getPageBegin() //
                , objInput.getPageEnd() //
        ), PageRequest.of(objInput.getPage(), objInput.getSize()));
    }

}
    