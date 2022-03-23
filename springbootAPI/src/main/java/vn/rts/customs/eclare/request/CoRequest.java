package vn.rts.customs.eclare.request;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CoRequest {
	private Long hangHoaId;

	@ApiModelProperty(notes = "Ngày C/O")
	@Size(max = 10)
	private String coNgay;

	@ApiModelProperty(notes = "Người cấp C/O")
	@Size(max = 50)
	private String coNguoiCap;

	@ApiModelProperty(notes = "Nơi cấp C/O")
	@Size(max = 255)
	private String coNoicap;

	@ApiModelProperty(notes = "Số C/O")
	@Size(max = 50)
	private String coSo;

	@ApiModelProperty(notes = "Ngày hết hạn C/O")
	@Size(max = 20)
	private String coNgayHetHan;
}
