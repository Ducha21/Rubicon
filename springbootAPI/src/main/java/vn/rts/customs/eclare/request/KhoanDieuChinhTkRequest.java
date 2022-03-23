package vn.rts.customs.eclare.request;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class KhoanDieuChinhTkRequest {
	private String id;
	
	@ApiModelProperty(notes = "So tt khoan dieu chinh")
	private Integer dcStt;
	
	@ApiModelProperty(notes = "MAU_KB_HQ_ID")
	private String mauKbHqId;

	@ApiModelProperty(notes = "Mã tương ứng khoản điều chỉnh")
	@Size(max = 1)
	private String dcTriGiaMa;

	@ApiModelProperty(notes = "Mã phân loại điều chỉnh trị giá")
	@Size(max = 3)
	private String dcTriGiaMaPl;

	@ApiModelProperty(notes = "Mã đơn vị tiền tệ của khoản điều chỉnh")
	@Size(max = 3)
	private String dcTriGiaMaNt;

	@ApiModelProperty(notes = "Trị giá khoản điều chỉnh")
	@Digits(integer = 20, fraction = 4)
	private BigDecimal dcTriGia;

	@ApiModelProperty(notes = "Tổng hệ số phân bổ trị giá khoản điều chỉnh")
	@Digits(integer = 20, fraction = 4)
	private BigDecimal dcTriGiaTongHsPhanBo;

	@ApiModelProperty(notes = "NGUOI_TAO")
	private String nguoiTao;

	@ApiModelProperty(notes = "NGAY_TAO")
	private Date ngayTao;

	@ApiModelProperty(notes = "NGUOI_SUA")
	private String nguoiSua;

	@ApiModelProperty(notes = "NGAY_SUA")
	private Date ngaySua;
}
