package vn.rts.customs.eclare.request.ctdt;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CtdtVanDonRequests {
	
	@Valid
	List<CtdtVanDonRequest> ctdt;

	private String dataSigned;

	@Data
	public static class CtdtVanDonRequest {
		@ApiModelProperty(notes = "số chứng từ")
		@Basic
		@Column(name = "CHUNG_TU_DINH_KEM_TK_ID", nullable = false, columnDefinition = "VARCHAR2")
		@NotNull
		@Size(max = 20)
		private String chungTuDinhKemTkId;

		@ApiModelProperty(notes = "Số vận đơn")
		@Size(max = 35)
		@NotNull(message = "Số vận đơn không được để trống")
		private String soVanDon;

		@ApiModelProperty(notes = "Ngày phát hành(YYYY-MM-DD)")
		@NotNull(message = "Ngày phát hành không được để trống")
		private Date ngayPhatHanh;

		@ApiModelProperty(notes = "Nước phát hành")
		@Size(max = 2)
		@NotNull(message = "Nước phát hành không được để trống")
		private String nuocPhatHanh;

		@ApiModelProperty(notes = "Mã người vận chuyển")
		@Size(max = 17)
		@NotNull(message = "Mã người vận chuyển không được để trống")
		private String maNguoiVanChuyen;

		@ApiModelProperty(notes = "Tên người vận chuyển")
		@Size(max = 255)
		@NotNull(message = "Tên người vận chuyển không được để trống")
		private String tenNguoiVanChuyen;

		@ApiModelProperty(notes = "Số lượng container")
		@Min(0)
		@Max(999)
		@NotNull(message = "Số lượng container không được để trống")
		private Integer soLuongContainer;

		@ApiModelProperty(notes = "Số lượng kiện")
		@Min(0)
		@Max(99999999)
		@NotNull(message = "Số lượng kiện không được để trống")
		private Integer soLuongKien;

		@ApiModelProperty(notes = "Mã đơn vị tính số lượng kiện")
		@Size(max = 4)
		@NotNull(message = "Mã đơn vị tính số lượng kiện không được để trống")
		private String maDvtSoLuongKien;

		@ApiModelProperty(notes = "Tổng trọng lượng (Gross Weight)")
		@Digits(integer = 10, fraction = 3)
		@NotNull(message = "Tổng trọng lượng không được để trống")
		private BigDecimal tongTrongLuong;

		@ApiModelProperty(notes = "Mã đơn vị tính của tổng trọng lượng")
		@Size(max = 4)
		@NotNull(message = "Mã đơn vị tính tổng trọng lượng không được để trống")
		private String maDvtTongTrongLuong;

		@ApiModelProperty(notes = "Phương thức giao hàng")
		@Min(0)
		@Max(99)
		@NotNull(message = "Phương thức giao hàng không được để trống")
		private Integer phuongThucGiaoHang;

		@ApiModelProperty(notes = "Số lượng vận đơn nhánh")
		@Min(0)
		@Max(99)
		@NotNull(message = "Số lượng vận đơn nhánh không được để trống")
		private Integer soLuongVanDonNhanh;

		@ApiModelProperty(notes = "Địa điểm chuyển tải/quá cảnh")
		@Size(max = 255)
		private String diaDiemQuaCanh;

		@ApiModelProperty(notes = "Loại vận đơn")
		@NotNull(message = "Loại vận đơn không được để trống")
		private String loaiVanDon;

		@ApiModelProperty(notes = "Ghi chú khác")
		@Size(max = 2000)
		private String ghiChuKhac;

		private List<CtdtVanDonDRequest> dsContainer;

		private List<CtdtVanDonNhanhRequest> dsVanDonNhanh;

		private CtdtFileDinhKemRequest fileDinhKem;
	}

	@Data
	public static class CtdtVanDonDRequest {
		@ApiModelProperty(notes = "Số container")
		@Size(max = 35)
		private String soContainer;

		@ApiModelProperty(notes = "Số seal")
		@Size(max = 100)
		private String soSeal;
	}

	@Data
	public static class CtdtVanDonNhanhRequest {
		@ApiModelProperty(notes = "Số thứ tự vận đơn nhánh")
		@Min(0)
		@Max(99)
		private Integer sttVanDonNhanh;

		@ApiModelProperty(notes = "Số vận đơn nhánh")
		@Size(max = 35)
		private String soVanDonNhanh;
	}

}
