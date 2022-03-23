package vn.rts.customs.eclare.request.ctdt;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CtdtHopDongUtRequests {
	@Valid
	List<CtdtHopDongUtRequests.HopDongUtRequest> ctdt;

	private String dataSigned;

	@Data
	public static class HopDongUtRequest {
		@ApiModelProperty(notes = "Tên người ủy thác")
		@Size(max = 255)
		private String tenNguoiUyThac;

		@ApiModelProperty(notes = "Mã người ủy thác")
		@Size(max = 17)
		private String maNguoiUyThac;

		@ApiModelProperty(notes = "Địa chỉ người ủy thác")
		@Size(max = 255)
		private String diaChiNguoiUyThac;

		@ApiModelProperty(notes = "Tên người nhận nhận ủy thác")
		@Size(max = 255)
		private String tenNguoiNhanUyThac;

		@ApiModelProperty(notes = "Mã người nhận nhận ủy thác")
		@Size(max = 17)
		private String maNguoiNhanUyThac;

		@ApiModelProperty(notes = "Địa chỉ người nhận ủy thác")
		@Size(max = 255)
		private String diaChiNguoiNhanUyThac;

		@ApiModelProperty(notes = "ghi chú khac")
		@Size(max = 2000)
		private String ghiChuKhac;

		private CtdtFileDinhKemRequest fileDinhKem;
	}
}
