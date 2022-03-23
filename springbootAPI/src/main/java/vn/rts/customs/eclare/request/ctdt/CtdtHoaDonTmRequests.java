package vn.rts.customs.eclare.request.ctdt;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class CtdtHoaDonTmRequests {
	@ApiParam(value = "Danh sách hóa đơn thương mại")
	@Valid
	private List<CtdtHoaDonTmRequest> ctdt;

	private String dataSigned;

	@Data
	public static class CtdtHoaDonTmRequest {
		@ApiModelProperty(notes = "số chứng từ đính kèm tờ khai id")
		@Basic
		@Column(name = "CHUNG_TU_DINH_KEM_TK_ID", nullable = true, columnDefinition = "VARCHAR2(36)")
		@Size(max = 36)
		private String chungTuDinhKemTkId;

		@ApiModelProperty(notes = "Số hóa đơn thương mại")
		@NotNull
		@Size(max = 50)
		private String soHoaDonTm;

		@ApiModelProperty(notes = "Ngày phát hành hóa đơn thương mại")
		private Date ngayPhatHanhHoaDonTm;

		@ApiModelProperty(notes = "Mã phân loại hình thức hóa đơn")
		@Size(max = 2)
		private String maPlHinhThucHoaDon;

		@ApiModelProperty(notes = "Tổng giá trị hóa đơn")
		@DecimalMin("0")
		@DecimalMax("9999999999999.9999999")
		@Digits(integer = 21, fraction = 4)
		private BigDecimal tongGiaTriHoaDon;

		@ApiModelProperty(notes = "Đồng tiền thanh toán")
		@Size(max = 3)
		private String donViTienThanhToan;

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
