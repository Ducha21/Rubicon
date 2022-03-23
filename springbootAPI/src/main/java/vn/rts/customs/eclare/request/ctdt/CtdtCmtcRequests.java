package vn.rts.customs.eclare.request.ctdt;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CtdtCmtcRequests {

	@ApiModelProperty(notes = "Danh sách Chứng từ chứng minh tổ chức, cá nhân đủ điều kiện xuất khẩu, nhập khẩu hàng hóa theo quy định của pháp luật về đầu tư")
	@Valid
	private List<CtdtCmtcRequest> ctdt;

	private String dataSigned;

	@Data
	public static class CtdtCmtcRequest {

		@ApiModelProperty(notes = "CHUNG_TU_DINH_KEM_TK_ID")
		private String chungTuDinhKemTkId;

		@ApiModelProperty(notes = "Mã phân loại hình thức chứng từ")
		@Size(max = 2)
		private String maPlHinhThucChungTu;

		@ApiModelProperty(notes = "Số chứng từ")
		@Size(max = 35)
		private String soChungTu;

		@ApiModelProperty(notes = "Ngày phát hành chứng từ")
		private Date ngayPhatHanhChungTu;

		@ApiModelProperty(notes = "Lĩnh vực/ngành nghề kinh doanh")
		@Size(max = 255)
		private String nganhNgheKinhDoanh;

		@ApiModelProperty(notes = "Cơ sở pháp lý")
		@Size(max = 255)
		private String coSoPhapLy;

		@ApiModelProperty(notes = "Ghi chú khác")
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
