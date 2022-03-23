package vn.rts.customs.eclare.request.ctdt;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.sun.istack.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CtdtGiayPhepRequests {
	@ApiModelProperty(notes = "Danh sách giấy phép")
	@Valid
	private List<CtdtGiayPhepRequest> ctdt;

	private String dataSigned;

	@Data
	public static class CtdtGiayPhepRequest {
		@ApiModelProperty(notes = "Mã phân loại giấy phép")
		@Size(max = 4)
		private String maPlGiayPhep;

		@ApiModelProperty(notes = "NGAY_SUA")
		private Date ngaySua;

		@ApiModelProperty(notes = "NGAY_TAO")
		private Date ngayTao;

		@ApiModelProperty(notes = "NGUOI_SUA")
		private String nguoiSua;

		@ApiModelProperty(notes = "NGUOI_TAO")
		private String nguoiTao;

		@ApiModelProperty(notes = "Số giấy phép")
		@Size(max = 35)
		private String soGiayPhep;

		@ApiModelProperty(notes = "Loại giấy phép")
		@NotNull
		private String loaiGiayPhep;

		@ApiModelProperty(notes = "Ngày cấp giấy phép")
		private Date ngayCapGiayPhep;

		@ApiModelProperty(notes = "Ngày hết hạn giấy phép")
		private Date ngayHetHanGiayPhep;

		@ApiModelProperty(notes = "Nơi cấp giấy phép")
		@Size(max = 255)
		private String noiCapGiayPhep;

		@ApiModelProperty(notes = "Người cấp giấy phép")
		@Size(max = 255)
		private String nguoiCapGiayPhep;

		@ApiModelProperty(notes = "Ghi chú khác")
		@Size(max = 2000)
		private String ghiChuKhac;
		
		@Valid
		private List<CtdtThongTinHangHoaRequest> dsHang;
		
		@ApiModelProperty(notes = "File đính kèm")
		private CtdtFileDinhKemRequest fileDinhKem;
	}
	
	@Data
	public static class CtdtThongTinHangHoaRequest {

		@ApiModelProperty(notes = "Hang hoa id")
		private Long id;
		
		@ApiModelProperty(notes = "Số thứ tự hàng")
		@Range(min=0, max=99999)
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
		@Size(max = 2000)
		private String ghiChuKhac;
	}

}
