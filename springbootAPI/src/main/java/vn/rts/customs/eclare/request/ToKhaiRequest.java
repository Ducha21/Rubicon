package vn.rts.customs.eclare.request;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class ToKhaiRequest {

	private String id;

	@ApiModelProperty(notes = "Nhom loai hinh(0 . Tất cả;1. Chính thức;2. Sửa; 3. Hủy ;- Gía trị mặc định : Tất cả)")
	@Min(0)
	@Max(Integer.MAX_VALUE)
	private Integer nhomLoaiHinh;

	@ApiModelProperty(notes = "So tiep nhan khai bao")
	private Integer soTiepNhan;
	
	@ApiModelProperty(notes = "So tk")
	private String soToKhai;

	@ApiModelProperty(notes = "Kênh khai báo(1. DN tự khai; 2. Qua đại lý)")
	@Size(max = 1)
	@NotNull(message = "Kênh khai báo không được để trống")
	private String kenhKhaiBao;

	@ApiModelProperty(notes = "Mã người khai hải quan")
	@NotNull(message = "Mã người khai hải quan không được để trống")
	@Size(max = 17)
	private String maNguoiKhaiHq;

	@ApiModelProperty(notes = "Tên người khai hải quan")
	@NotNull(message = "Tên người khai hải quan không được để trống")
	@Size(max = 255)
	private String tenNguoiKhaiHq;

	@ApiModelProperty(notes = "Mã chức năng (8-khai mới, 5-sửa)")
	@Size(max = 1)
	private String maChucNang;

	@ApiModelProperty(notes = "Hải quan tiếp nhận khai báo")
	@Size(max = 6)
	@NotNull(message = "Mã hải quan không được để trống")
	private String haiQuanTiepNhanKb;

	@ApiModelProperty(notes = "Tên hải quan không được để trống")
	@NotNull(message = "Tên hải quan không được để trống")
	private String tenHaiQuanTiepNhanKb;

	@ApiModelProperty(notes = "Số tham chiếu tờ khai")
	@Size(max = 36)
	private String soThamChieuTk;

	@ApiModelProperty(notes = "Ngày khai báo")
	private String ngayTraLoi;

	@ApiModelProperty(notes = "Số tờ khai đầu tiên")
	@Size(max = 12)
	private String soTkDauTien;

	@ApiModelProperty(notes = "Số nhánh của tờ khai chia nhỏ")
	@Min(0)
	@Max(99)
	private Integer soNhanhTk;

	@ApiModelProperty(notes = "Tổng số tờ khai chia nhỏ")
	@Min(0)
	@Max(99)
	private Integer tongSoTk;

	@ApiModelProperty(notes = "So to khai (Không phải nhập dữ liệu, hệ thống tự trả về)")
	@Size(max = 12)
	private String soTk;

	@ApiModelProperty(notes = "So to khai tam nhap tai xuat tuong ung")
	@Size(max = 12)
	private String soTkXktc;

	@ApiModelProperty(notes = "Ma loai hinh")
	@NotNull(message = "Mã loại hình không được để trống")
	@NotEmpty(message = "Mã loại hình không được để trống")
	@Size(max = 3)
	private String maLoaiHinh;

	@ApiModelProperty(notes = "Ma phan loai hang hoa")
	@Size(max = 1)
	private String plHangHoa;

	@ApiModelProperty(notes = "Ngay khai du kien")
	private String ngayKhaiDuKien;

	@ApiModelProperty(notes = "Phan loai ca nhan/ to chuc")
	@Size(max = 1)
	private String plCaNhanToChuc;

	@ApiModelProperty(notes = "Ma bo phan xu ly to khai")
	@Size(max = 2)
	@NotNull(message = "Mã bộ phận xử lý tờ khai không được để trống")
	private String plKths;

	@ApiModelProperty(notes = "Ma hieu phuong thuc van chuyen")
	@Size(max = 1)
	@NotNull(message = "Mã hiệu phương thức vận chuyển không được để trống")
	private String maPtVanChuyen;

	@ApiModelProperty(notes = "Ma nguoi nhap khau")
	@Size(max = 13)
	@NotNull(message = "Mã người nhập khẩu không được để trống")
	private String maNguoiNk;

	@ApiModelProperty(notes = "Tên người nhập khẩu")
	@Size(max = 200)
	@NotNull(message = "Tên người nhập khẩu không được để trống")
	private String tenNguoiNk;

	@ApiModelProperty(notes = "Ma buu chinh")
	@Size(max = 9)
	private String maBuuChinhNguoiNk;

	@ApiModelProperty(notes = "Ma nguoi xuat khau")
	@Size(max = 13)
	@NotNull(message = "Mã người xuất khẩu không được để trống")
	private String maNguoiXk;

	@ApiModelProperty(notes = "Ten nguoi xuat khau")
	@Size(max = 200)
	@NotNull(message = "Tên người xuất khẩu không được để trống")
	private String tenNguoiXk;

	@ApiModelProperty(notes = "Ma buu dien nguoi xuat khau")
	@Size(max = 9)
	private String maBuuChinhNguoiXk;

	@ApiModelProperty(notes = "Mã đại lý hải quan")
	@Size(max = 5)
	private String maDaiLyHq;

	@ApiModelProperty(notes = "Số Vận đơn (Số B/L số AWB v.v...) (1: Khai báo số định danh theo chuẩn quản lý giám sát Hải quan tự động tại Cảng biển; 2: Khai báo số định danh theo chuẩn quản lý giám sát Hải quan tự động tại Cảng hàng không)")
	@Size(max = 1)
	private String soVanDon;

	@ApiModelProperty(notes = "So luong kien hang")
	@Min(0)
	@Max(Integer.MAX_VALUE)
	private Integer tongSoLuongKien;

	@ApiModelProperty(notes = "Kien hang combobox")
	@Size(max = 4)
	private String tongSoLuongKienDvt;

	@ApiModelProperty(notes = "Tong trong luong hang hoa (gross)")
	@Digits(integer = 20, fraction = 2)
	@NotNull(message = "Tổng trọng lượng không được để trống")
	private BigDecimal tongTrongLuong;

	@ApiModelProperty(notes = "Tong trong luong hang hoa (gross)-combobox")
	@Size(max = 4)
	@NotNull(message = "Mã đơn vị tính tổng trọng lượng không được để trống")
	private String tongTrongLuongDvt;

	@ApiModelProperty(notes = "Ma dia diem luu kho hang cho thong quan du kien")
	@Size(max = 7)
	@NotNull(message = "Mã địa điểm lưu kho hàng chờ thông quan dự kiến không được để trống")
	private String diaDiemLuuKhoHangCtqdk;

	@ApiModelProperty(notes = "Ký hiệu và số hiệu")
	@Size(max = 140)
	@NotNull(message = "Ký hiệu và số hiệu không được để trống")
	private String kyHieuSoHieu;

	@ApiModelProperty(notes = "Ma Phuong tien van chuyen")
	@Size(max = 9)
	private String maPhuongTienVanChuyen;

	@ApiModelProperty(notes = "Ten Phuong tien van chuyen")
	@Size(max = 200)
	private String tenPhuongTienVanChuyen;

	@ApiModelProperty(notes = "So luong container")
	@Min(0)
	@Max(999)
	private Integer soContainer;

	@ApiModelProperty(notes = "1: Nhap khau; 2: xuat khua")
	@Size(max = 3)
	private String loaiTkHq;

	@ApiModelProperty(notes = "NGUOI_TAO")
	private String nguoiTao;

	@ApiModelProperty(notes = "NGAY_TAO")
	private String ngayTao;

	@ApiModelProperty(notes = "NGUOI_SUA")
	private String nguoiSua;

	@ApiModelProperty(notes = "NGAY_SUA")
	private String ngaySua;

	@ApiModelProperty(notes = "DOANH_NGHIEP_ID")
	@NotNull
	private String doanhNghiepId;

	@ApiModelProperty(notes = "LUONG_TO_KHAI")
	private String luongToKhai;

	@ApiModelProperty(notes = "LY_DO_TU_CHOI")
	private String lyDotuChoi;

	@ApiModelProperty(notes = "PHAN_LOAI_CHI_THI_HQ")
	private Integer phanLoaiChiThiHq;

	@ApiModelProperty(notes = "So hop dong GC")
	@Size(max = 50)
	private String gcSoHopDong;

	@ApiModelProperty(notes = "Ngay hop dong GC")
	private String gcNgayHopDong;

	@ApiModelProperty(notes = "Phân loại hình thức hóa đơn")
	@NotNull(message = "Phân loại hình thức hóa đơn không được để trống")
	@NotEmpty(message = "Phân loại hình thức hóa đơn không được để trống")
	@Size(max = 1)
	private String hoaDonPlHinhThuc;

	@ApiModelProperty(notes = "So tiep nhan hoa don dien tu")
	@Size(max = 12)
	private String hoaDonSoDienTu;

	@ApiModelProperty(notes = "So hoa don")
	@Size(max = 35)
	private String hoaDonSo;

	@ApiModelProperty(notes = "Ngay phat hanh")
	private String hoaDonNgayPhatHanh;

	@ApiModelProperty(notes = "Phuong thuc thanh toan")
	@Size(max = 7)
	@NotNull(message = "Phương thức thanh toán không được để trống")
	private String hoaDonPtThanhToan;

	@ApiModelProperty(notes = "Ma phan loai gia hoa don")
	@Size(max = 1)
	private String hoaDonPlGia;

	@ApiModelProperty(notes = "Điều kiện giá hoá đơn")
	@Size(max = 3)
	@NotNull(message = "Điều kiện giá hóa đơn không được để trống")
	private String hoaDonDieuKienGia;

	@ApiModelProperty(notes = "Tong tri gia hoa don")
	@Digits(integer = 20, fraction = 4)
	@NotNull(message = "Tổng giá trị hóa đơn không được để trống")
	private BigDecimal hoaDonTongTriGiaKb;
	
	@ApiModelProperty(notes = "1.44. Mã đồng tiền của hóa đơn")
	@Size(max = 3)
	private String hoaDonMaTienTe;

	@ApiModelProperty(notes = "Ma tien te")
	@Size(max = 3)
	private String tgMaTienTe;

	@ApiModelProperty(notes = "Gia co so de hieu chinh gia")
	@Min(0L)
	@Max(Long.MAX_VALUE)
	private Long tgGiaCoSoHc;

	@ApiModelProperty(notes = "Tong he so phan bo tri gia")
	@Digits(integer = 20, fraction = 4)
	private BigDecimal dcTriGiaTongHsPhanBo;

	@ApiModelProperty(notes = "Nguoi nop thue")
	@Size(max = 1)
	@NotNull(message = "Người nộp thuế không được để trống")
	private String nguoiNopThue;

	@ApiModelProperty(notes = "Ma ngan hang tra thue thay")
	@Size(max = 11)
	private String maNganHangTraThueThay;

	@ApiModelProperty(notes = "Nam phat hanh han muc")
	private String hanMucNamPhatHanh;

	@ApiModelProperty(notes = "Ky hieu chung tu han muc")
	@Size(max = 10)
	private String hanMucKiHieuChungTu;

	@ApiModelProperty(notes = "So chung tu han muc")
	@Size(max = 10)
	private String hanMucSoChungTu;

	@ApiModelProperty(notes = "Mã xác định thời hạn nộp thuế")
	private String ngayHoanThanhNvThue;

	@ApiModelProperty(notes = "Mã ngân hàng bảo lãnh")
	@Size(max = 11)
	private String baoLanhMaNganHang;

	@ApiModelProperty(notes = "Nam phat hanh bao lanh")
	private String baoLanhNamPhatHanh;

	@ApiModelProperty(notes = "Ky hieu chung tu bao lanh")
	@Size(max = 10)
	private String baoLanhKiHieuChungTu;

	@ApiModelProperty(notes = "So chung tu bao lanh")
	@Size(max = 10)
	private String baoLanhSoChungTu;

	@ApiModelProperty(notes = "Ngay khoi hanh van chuyen")
	private String ngayKhoiHanh;

	@ApiModelProperty(notes = "Ngày dự kiến đến(Đến điểm đích)")
	private String ngayDenDuKien;

	@ApiModelProperty(notes = "Địa điểm đích cho vận chuyển bảo thuế")
	@Size(max = 7)
	private String diaDiemDichVcbt;

	@ApiModelProperty(notes = "Phan ghi chu")
	@Size(max = 500)
	private String ghiChu;

	@ApiModelProperty(notes = "So quan ly cua noi bo doanh nghiep")
	@Size(max = 20)
	private String soQuanLyNbdn;
	
	@ApiModelProperty(notes = "2.80 Mã văn bản pháp luật khác")
	@Size(max = 200)
	private String maVb;
	
	@Size(max = 1)
	private String maXacDinhThoiHanNopThue;
	
	@ApiModelProperty(notes = "Ma Dia diem xep hang")
	@Size(max = 6)
	private String maDiaDiemXepHang;

	@ApiModelProperty(notes = "Ten Dia diem xep hang")
	@Size(max = 200)
	private String tenDiaDiemXepHang;

	@ApiModelProperty(notes = "Mã doanh nghiệp")
	private String maDoanhNghiep;

	@ApiModelProperty(notes = "Tên doanh nghiệp")
	private String tenDoanhNghiep;

	@ApiParam(value = "Danh sach van don")
	private List<TkVanDonRequest> dsVanDon;

	@ApiParam(value = "Danh sach giay phep")
	private List<GiayPhepTkRequest> dsGiayPhep;

	@ApiParam(value = "Danh sach dinh kem")
	private List<SoDinhKemKbdtRequest> dsDinhKemKbdt;

	@ApiParam(value = "Danh sach thong tin trung chuyen")
	private List<TtTrungChuyenRequest> dsThongTinTc;

	@ApiParam(value = "Danh sach hang")
	private List<DmHangHoaTkRequest> dsHang;

	private String dataSigned;
}
