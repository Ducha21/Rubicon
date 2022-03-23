package vn.rts.customs.eclare.request;

import lombok.Data;
import vn.rts.customs.eclare.entity.ContainerInfoEntity;
import vn.rts.customs.lib.dto.PageableResponse;

import java.util.Date;

@Data
public class ContainerTkResponse {

	private String maChucNang;

	private String soTiepNhan;

	private Date ngayTiepNhan;

	private Date ngayDangKy;

	private String ghiChu;

	private String id;

	private String noiDungPhanHoi;

	private PageableResponse<ContainerInfoEntity> containers;
}
