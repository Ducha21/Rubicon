package vn.rts.customs.eclare.request;

import java.util.Date;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TtTrungChuyenRequest {
	private Long id;

	@ApiModelProperty(notes = "MAU_KB_HQ_ID")
	private String mauKbHqId;

	@ApiModelProperty(notes = "Địa điểm trung chuyển cho vận chuyển bảo thuế (khai báo gộp)")
	@Size(max = 3)
	private String diaDiemTrungChuyenBcbt;

	@ApiModelProperty(notes = "Ngày đến địa điểm trung chuyển")
	private Date ngayDen;

	@ApiModelProperty(notes = "Ngày rời địa điểm trung chuyển")
	private Date ngayKhoiHanh;

	@ApiModelProperty(notes = "NGUOI_TAO")
	private String nguoiTao;

	@ApiModelProperty(notes = "NGAY_TAO")
	private Date ngayTao;

	@ApiModelProperty(notes = "NGUOI_SUA")
	private String nguoiSua;

	@ApiModelProperty(notes = "NGAY_SUA")
	private Date ngaySua;
}
