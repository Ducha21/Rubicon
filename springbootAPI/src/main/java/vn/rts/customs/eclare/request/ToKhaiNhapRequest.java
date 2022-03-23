package vn.rts.customs.eclare.request;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ToKhaiNhapRequest extends ToKhaiRequest {

	@ApiModelProperty(notes = "Ngay den")
	private String ngayDen;

	@ApiModelProperty(notes = "Chi tiet khai tri gia")
	@Size(max = 300)
	@NotNull(message = "Chi tiết tờ khai trị giá không được để trống")
	@NotEmpty(message = "Chi tiết tờ khai trị giá không được để trống")
	private String chiTietTkTriGia;

	@ApiModelProperty(notes = "Tên người ủy thác xuất khẩu")
	@Size(max = 200)
	private String tenNguoiUyThacXk;

	@ApiModelProperty(notes = "Dia chi nguoi xuat khau")
	@Size(max = 200)
	private String diaChiXk1;

	@ApiModelProperty(notes = "Dia chi nguoi xuat khau")
	@Size(max = 200)
	private String diaChiXk2;

	@ApiModelProperty(notes = "Dia chi nguoi xuat khau")
	@Size(max = 200)
	private String diaChiXk3;

	@ApiModelProperty(notes = "Dia chi nguoi xuat khau")
	@Size(max = 200)
	private String diaChiXk4;

	@ApiModelProperty(notes = "Phi van chuyen")
	@Digits(integer = 20, fraction = 4)
	private BigDecimal vcPhi;

	@ApiModelProperty(notes = "Ma tien phi van chuyen")
	@Size(max = 3)
	private String vcMaTienTePhi;

	@ApiModelProperty(notes = "Ma loai phi van chuyen")
	@Size(max = 1)
	private String vcMaLoaiPhi;

	@ApiModelProperty(notes = "Ma nguoi uy thac nhap khau")
	@Size(max = 13)
	private String maNguoiUyThacNk;

	@ApiModelProperty(notes = "Ten nguoi uy thac nhap khau")
	@Size(max = 200)
	private String tenNguoiUyThacNk;

	@ApiModelProperty(notes = "Dien thoai nguoi nhap khau")
	@Size(max = 20)
	private String sdtNguoiNk;

	@ApiModelProperty(notes = "Ngay duoc phep nhap kho dau tien")
	private String ngayNhapKhoDauTien;

	@ApiModelProperty(notes = "Phi bao hiem")
	@Digits(integer = 20, fraction = 4)
	private BigDecimal bhPhi;

	@ApiModelProperty(notes = "Ma tien phi bao hiem")
	@Size(max = 3)
	private String bhMaTienTe;

	@ApiModelProperty(notes = "Ma loai phi bao hiem")
	@Size(max = 1)
	private String bhMaLoaiPhi;

	@ApiModelProperty(notes = "Số tờ khai xuất khẩu tại chỗ tương ứng")
	private String soTKXKTaiChoTuongUng;

	@ApiModelProperty(notes = "Số đăng ký bảo hiểm tổng hợp")
	@Size(max = 6)
	private String soDangKyBaoHiemTongHop;

	@ApiModelProperty(notes = "Mã lý do đề nghị BP")
	@Size(max = 1)
	private String maLyDoDnbp;

	@ApiModelProperty(notes = "Ma ket qua kiem tra noi dung")
	private String ketQuaXuLy;

	@ApiModelProperty(notes = "Ma Dia diem do hang")
	@Size(max = 6)
	@NotNull(message = "Mã địa điểm dỡ hàng không được để trống")
	@NotEmpty(message = "Mã địa điểm dỡ hàng không được để trống")
	private String maDiaDiemDoHang;

	@ApiModelProperty(notes = "Ten Dia diem do hang")
	@Size(max = 200)
	@NotNull(message = "Tên địa điểm dỡ hàng không được để trống")
	@NotEmpty(message = "Tên địa điểm dỡ hàng không được để trống")
	private String tenDiaDiemDoHang;

	@ApiParam(value = "Danh sach khoan dieu chinh")
	private List<KhoanDieuChinhTkRequest> dsKhoanDieuChinh;

	@ApiModelProperty(notes = "So tiep nhan to khai gia tri tong hop")
	@Size(max = 9)
	private String tgSoTiepNhanTkth;

	@ApiModelProperty(notes = "Ma phan loai khai tri gia")
	@Size(max = 1)
	private String tgMaPlTk;

	@ApiModelProperty(notes = "Dia chi nguoi nhau khau")
	@Size(max = 200)
	@NotNull(message = "Địa chỉ người nhập khẩu không được để trống")
	private String diaChiNguoiNk;
	
	@ApiModelProperty(notes = "Thoi han tai xuat")
	private String thoiHanTaiXuat;
	
	@ApiModelProperty(notes = "1.36. Mã kết quả kiểm tra nội dung")
	private String maKetQuaKiemTraNd;

	@ApiModelProperty(notes = "Ma nuoc nguoi xuat khau")
	@Size(max = 2)
	@NotNull(message = "Mã nước xuất khẩu không được để trống")
	private String maNuocXk;
	
	private int kbSoDinhDanhCangBien;
	
	private int kbSoDinhDanhCangHK;
}
