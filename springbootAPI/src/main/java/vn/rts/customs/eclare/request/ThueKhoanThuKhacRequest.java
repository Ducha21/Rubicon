package vn.rts.customs.eclare.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ThueKhoanThuKhacRequest {
	private Long id;

	@ApiModelProperty(notes = "DM_HANG_HOA_TK_ID")
	@NotNull
	@Min(0L)
	@Max(Long.MAX_VALUE)
	private Long dmHangHoaTkId;

	@ApiModelProperty(notes = "Số thứ tự")
	@Min(0)
	@Max(Integer.MAX_VALUE)
	private Integer stt;

	@ApiModelProperty(notes = "Mã áp dụng thuế suất / Mức thuế và thu khác")
	@Size(max = 10)
	private String thueMst;

	@ApiModelProperty(notes = "Mã miễn / Giảm / Không chịu thuế và thu khác")
	@Size(max = 5)
	private String maMienThue;

	@ApiModelProperty(notes = "Số tiền giảm thuế và thu khác")
	private BigDecimal soTienGiamThue;
}
