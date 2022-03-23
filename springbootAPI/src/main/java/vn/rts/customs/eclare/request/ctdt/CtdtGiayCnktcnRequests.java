package vn.rts.customs.eclare.request.ctdt;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CtdtGiayCnktcnRequests {
	@Valid
	List<CtdtGiayCnktcnRequests.CtdtGiayCnktcnRequest> ctdt;

	private String dataSigned;

	@Data
	public static class CtdtGiayCnktcnRequest {
		@ApiModelProperty(notes = "Mã phân loại giấy chứng nhận kiểm tra chuyên ngành")
		@Size(max = 4)
		private String maPlGiayCnktcn;

		@ApiModelProperty(notes = "Loại giấy chứng nhận kiểm tra chuyên ngành")
		@Min(0)
		@Max(99)
		private Integer loaiGiayCnktcn;

		@ApiModelProperty(notes = "Tên giấy chứng nhận kiểm tra chuyên ngành")
		@Min(0)
		@Max(99)
		private Integer tenGiayCnktcn;

		@ApiModelProperty(notes = "Số giấy chứng nhận kiểm tra chuyên ngành")
		@Size(max = 35)
		private String soGiayCnktcn;

		@ApiModelProperty(notes = "Ngày giấy chứng nhận kiểm tra chuyên ngành")
		private Date ngayGiayCnktcn;

		@ApiModelProperty(notes = "Ngày hết hạn giấy chứng nhận kiểm tra chuyên ngành")
		private Date ngayHetHanGiayCnktcn;

		@ApiModelProperty(notes = "Nơi cấp giấy chứng nhận kiểm tra chuyên ngành")
		@Size(max = 255)
		private String noiCapGiayCnktcn;

		@ApiModelProperty(notes = "Người cấp giấy chứng nhận kiểm tra chuyên ngành")
		@Size(max = 255)
		private String nguoiCapGiayCnktcn;

		@ApiModelProperty(notes = "ghi chú khác")
		@Size(max = 2000)
		private String ghiChuKhac;
		
		@Valid
		private List<CtdtThongTinHangHoaRequest> dsHang;

		private CtdtFileDinhKemRequest fileDinhKem;
	}
	
	@Data
	public static class CtdtThongTinHangHoaRequest {

		@ApiModelProperty(notes = "Id hàng")
		private Long id;

		@ApiModelProperty(notes = "Số thứ tự hàng")
		@Min(0)
		@Max(99999)
		private Integer soThuTuHang;

		@ApiModelProperty(notes = "Mã số hàng hóa")
		@Size(max = 12)
		private String maSoHangHoa;
		
		@ApiModelProperty(notes = "Tên hàng hóa")
		@Size(max = 256)
		private String tenHangHoa;
		
		@ApiModelProperty(notes = "Số lượng")
		@Digits(integer = 18, fraction = 4)
		private BigDecimal soLuong;
		
		@ApiModelProperty(notes = "Đơn vị tính")
		@Size(max = 4)
		private String donViTinh;
		
		@ApiModelProperty(notes = "Trị Giá")
		private BigDecimal triGia;
		
		@ApiModelProperty(notes = "Nguyên tệ")
		@Size(max = 3)
		private String nguyenTe;
		
		@ApiModelProperty(notes = "Các ghi chú khác về hàng")
		@Size(max = 255)
		private String ghiChuKhac;
	}

}
