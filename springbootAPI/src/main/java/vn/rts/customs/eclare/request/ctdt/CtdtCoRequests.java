package vn.rts.customs.eclare.request.ctdt;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CtdtCoRequests {
	@ApiModelProperty(notes = "Danh sách thông tin về C/O ")
	@Valid
	private List<CtdtCoRequest> ctdt;

	private String dataSigned;

	@Data
	public static class CtdtCoRequest {

		@ApiModelProperty(notes = "Số C/O")
		@Size(max = 35)
		private String soCo;

		@ApiModelProperty(notes = "Loại C/O (Form A, D, E, AK...)")
		@Size(max = 35)
		private String loaiCo;

		@ApiModelProperty(notes = "Ngày cấp C/O")
		@JsonFormat(pattern="yyyy-MM-dd")
		private Date ngayCapCo;

		@ApiModelProperty(notes = "Tổ chức cấp C/O")
		@Size(max = 255)
		private String toChucCo;

		@ApiModelProperty(notes = "Người cấp C/O")
		@Size(max = 255)
		private String nguoiCapCo;

		@ApiModelProperty(notes = "Nước cấp C/O")
		@Size(max = 2)
		private String nuocCapCo;

		@ApiModelProperty(notes = "Thời điểm nộp C/O")
		@Range(max = 99)
		private Integer thoiDiemNopCo;

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

		@ApiModelProperty(notes = "CHUNG_TU_DINH_KEM_TK_ID")
		private Long chungTuDinhKemTkId;

		@ApiModelProperty(notes = "File đính kèm")
		private CtdtFileDinhKemRequest fileDinhKem;
	}
}
