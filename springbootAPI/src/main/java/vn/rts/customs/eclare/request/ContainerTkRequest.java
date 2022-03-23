package vn.rts.customs.eclare.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ContainerTkRequest {
	@ApiModelProperty(notes = "Ngày khai báo")
	private Date ngayKb;

	@ApiModelProperty(notes = "Ghi chú")
	private String ghiChu;

	@ApiModelProperty(notes = "Danh sách container")
	private List<ContainerRequest> containers;

	private String dataSigned;
}
