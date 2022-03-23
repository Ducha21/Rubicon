package vn.rts.customs.eclare.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContainerRequest {
	@ApiModelProperty(notes = "Số vận đơn")
	private String soVanDon;

	@ApiModelProperty(notes = "Số container")
	private String soContainer;

	@ApiModelProperty(notes = "Hải quan tiếp nhận")
	private String soSeal;

	@ApiModelProperty(notes = "Ghi chú")
	private String ghiChu;
}
