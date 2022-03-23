package vn.rts.customs.eclare.request.ctdt;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CtdtBkhhRequests {
	@ApiModelProperty(notes = "Danh sách bảng kê chi tiết hàng hóa")
	@Valid
	private List<CtdtBkhhRequest> ctdt;

	private String dataSigned;

	@Data
	public static class CtdtBkhhRequest {

		@ApiModelProperty(notes = "chứng từ đính kèm id")
		private String chungTuDinhKemTkId;

		@ApiModelProperty(notes = "Số bảng kê chi tiết")
		@Size(max = 50)
		private String soBangKeChiTiet;

		@ApiModelProperty(notes = "Ngày phát hành")
		private Date ngayPhatHanh;

		@ApiModelProperty(notes = "Tổng số lượng mặt hàng")
		@Min(0)
		@Max(99999)
		private Integer tongSoLuongMatHang;

		@ApiModelProperty(notes = "Tổng số lượng kiện hàng")
		@Min(0)
		@Max(99999)
		private Integer tongSoLuongKienHang;

		@ApiModelProperty(notes = "Mã đơn vị tính số lượng kiện")
		@Size(max = 4)
		private String maDvtSoLuongKien;

		@ApiModelProperty(notes = "ghi chú khác")
		@Size(max = 2000)
		private String ghiChuKhac;

		@ApiModelProperty(notes = "NGUOI_TAO")
		private String nguoiTao;

		@ApiModelProperty(notes = "NGAY_TAO")
		private Date ngayTao;

		@ApiModelProperty(notes = "NGUOI_SUA")
		private String nguoiSua;

		@ApiModelProperty(notes = "NGAY_SUA")
		private Date ngaySua;

		@ApiModelProperty(notes = "File đính kèm")
		private CtdtFileDinhKemRequest fileDinhKem;
	}
}
