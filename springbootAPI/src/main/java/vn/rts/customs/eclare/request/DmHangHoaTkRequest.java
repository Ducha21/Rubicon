package vn.rts.customs.eclare.request;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DmHangHoaTkRequest {
	private Long id;

	@ApiModelProperty(notes = "Mã  ngoài hạn ngạch")
	@Size(max = 1)
	private String maNgoaiHanNgach;

	@ApiModelProperty(notes = "Mã số hàng hóa (HS)")
	@Size(max = 12)
	@NotNull(message = "Mã số hàng hóa không được để trống")
	@NotEmpty(message = "Mã số hàng hóa không được để trống")
	private String maHs;

	@ApiModelProperty(notes = "Mã quản lý riêng")
	@Size(max = 7)
	private String maQlr;

	@ApiModelProperty(notes = "Thuế suất")
	@Digits(integer = 20, fraction = 4)
	private BigDecimal thueSuat;

	@ApiModelProperty(notes = "Mức thuế tuyệt đối")
	@Digits(integer = 20, fraction = 4)
	private BigDecimal thueSuatTuyetDoi;

	@ApiModelProperty(notes = "Mã đơn vị tính của mức thuế tuyệt đối")
	@Size(max = 4)
	private String thueSuatTuyetDoiDvt;

	@ApiModelProperty(notes = "Mã nguyên tệ của mức thuế tuyệt đối")
	@Size(max = 3)
	private String thueSuatTuyetDoiMaNt;

	@ApiModelProperty(notes = "Mã nước xuất xứ")
	@Size(max = 2)
	@NotNull(message = "Mã nước xuất xứ không được để trống")
	@NotEmpty(message = "Mã nước xuất xứ không được để trống")
	private String nuocXxMa;

	@ApiModelProperty(notes = "Mã hàng hóa (Commodity codes)")
	@Size(max = 50)
	private String maHang;

	@ApiModelProperty(notes = "Mô tả hàng hóa")
	@Size(max = 500)
	@NotNull(message = "Mô tả hàng hóa không được để trống")
	@NotEmpty(message = "Mô tả hàng hóa không được để trống")
	private String tenHang;

	@ApiModelProperty(notes = "Mã miễn / Giảm / Không chịu thuế xuất khẩu")
	@Size(max = 5)
	private String thueMienGiamMa;

	@ApiModelProperty(notes = "Số tiền giảm thuế xuất khẩu")
	@Digits(integer = 20, fraction = 4)
	private BigDecimal thueGiam;

	@ApiModelProperty(notes = "Số lượng (1)")
	@NotNull(message = "Số lượng(1) không được để trống")
	@NotEmpty(message = "Số lượng(1) không được để trống")
	@Digits(integer = 12, fraction = 2)
	private BigDecimal luong;

	@ApiModelProperty(notes = "Mã đơn vị tính của Số lượng (1)")
	@Size(max = 4)
	@NotNull(message = "Mã đơn vị tính của số lượng(1) không được để trống")
	@NotEmpty(message = "Mã đơn vị tính của số lượng(1) không được để trống")
	private String maDvt;

	@ApiModelProperty(notes = "Số lượng (2)")
	@Digits(integer = 12, fraction = 2)
	private BigDecimal luong2;

	@ApiModelProperty(notes = "Mã đơn vị tính của Số lượng (2)")
	@Size(max = 4)
	private String maDvt2;

	@ApiModelProperty(notes = "Trị giá hóa đơn")
	@Digits(integer = 20, fraction = 4)
	private BigDecimal triGiaKb;

	@ApiModelProperty(notes = "Đơn giá hóa đơn")
	@Digits(integer = 20, fraction = 4)
	private BigDecimal dgiaKb;

	@ApiModelProperty(notes = "Mã nguyên tệ (của đơn giá hóa đơn)")
	@Size(max = 3)
	private String dgiaKbMaNt;

	@ApiModelProperty(notes = "Mã đơn vị tính ( của đơn giá hóa đơn)")
	@Size(max = 4)
	private String dgiaKbDvt;

	@ApiModelProperty(notes = "Trị giá tính thuế")
	@Digits(integer = 20, fraction = 4)
	private BigDecimal triGiaTinhThue;

	@ApiModelProperty(notes = "Trị giá tính thuế đơn vị ")
	@Size(max = 3)
	private String triGiaTinhThueDvt;

	@ApiModelProperty(notes = "Số thứ tự của dòng hàng trên tờ khai tạm nhập tái xuất tương ứng")
	@Size(max = 2)
	private String sttHangTntx;

	@ApiModelProperty(notes = "Danh mục miễn thuế xuất khẩu")
	@Size(max = 12)
	private String soDkMienThue;

	@ApiModelProperty(notes = "Số dòng tương ứng trong Danh mục miễn thuế xuất khẩu")
	@Size(max = 3)
	private String sttHangDmMienThue;

	@ApiModelProperty(notes = "Mã văn bản pháp luật khác")
	@Size(max = 14)
	private String maVb;

	@ApiModelProperty(notes = "Chế độ ưu đãi thuế( Loại C/O(Form A, D, E, AK))")
	@Size(max = 50)
	private String cdUdThue;

	@ApiModelProperty(notes = "Danh sach Co")
	private List<CoRequest> dsCo;

	@ApiModelProperty(notes = "FK")
	private String mauKbHqId;

	@ApiModelProperty(notes = "NGUOI_TAO")
	private String nguoiTao;

	@ApiModelProperty(notes = "NGAY_TAO")
	private Date ngayTao;

	@ApiModelProperty(notes = "NGUOI_SUA")
	private String nguoiSua;

	@ApiModelProperty(notes = "NGAY_SUA")
	private Date ngaySua;

	@ApiModelProperty(notes = "1.82. Mã áp dụng mức thuế tuyệt đối")
	@Size(max = 10)
	private String maMucThueTuyetDoi;

	@ApiModelProperty(notes = "MA_BIEU_THUE")
	@Size(max = 3)
	@NotNull(message = "Mã biểu thuế không được để trống")
	@NotEmpty(message = "Mã biểu thuế không được để trống")
	private String maBieuThue;

	@ApiModelProperty(notes = "DS_THUE_KHOAN_THU_KHAC")
	private List<ThueKhoanThuKhacRequest> dsThueKhoanThuKhac;

	@ApiModelProperty(notes = "KHOAN_DIEU_CHINH")
	private String soDanhMucKhoanDieuChinh;

   	private int sttHang;

	private String loaiHang;
}
