package vn.rts.customs.eclare.request;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class GiayPhepTkRequest {
	private Long id;
	
	@ApiModelProperty(notes = "fk")
	private String mauKbHqId;

	@ApiModelProperty(notes = "Mã phân loại giấy phép nhập khẩu")
	@Size(max = 4)
	private String maPlGiayPhepNk;

	@ApiModelProperty(notes = "Số của giấy phép")
	@Size(max = 20)
	private String soPkGiayPhepNk;

	@ApiModelProperty(notes = "NGUOI_TAO")
	private String nguoiTao;

	@ApiModelProperty(notes = "NGAY_TAO")
	private Date ngayTao;

	@ApiModelProperty(notes = "NGUOI_SUA")
	private String nguoiSua;

	@ApiModelProperty(notes = "NGAY_SUA")
	private Date ngaySua;
}
