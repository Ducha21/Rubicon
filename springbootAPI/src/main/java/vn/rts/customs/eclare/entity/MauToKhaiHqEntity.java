
package vn.rts.customs.eclare.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare.EntityTableEtcCustomsEClare;
import vn.rts.customs.lib.dto.ConditionalBaseEoEx;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author VNETC
 */

@Entity
@Table(name = EntityTableEtcCustomsEClare.MAU_TO_KHAI_HQ)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class MauToKhaiHqEntity extends ConditionalBaseEoEx<MauToKhaiHqEntity, String> implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "ID")
	@Basic
	@Column(name = "ID", nullable = false, columnDefinition = "VARCHAR(36)")
	@NotNull
	@Id
	private String id;

	@ApiModelProperty(notes = "Nhom loai hinh(0 . Tất cả;1. Chính thức;2. Sửa; 3. Hủy ;- Gía trị mặc định : Tất cả)")
	@Basic
	@Column(name = "NHOM_LOAI_HINH", nullable = true, columnDefinition = "NUMBER")
	@Min(0)
	@Max(Integer.MAX_VALUE)
	private Integer nhomLoaiHinh;

	@ApiModelProperty(notes = "So tiep nhan khai bao")
	@Basic
	@Column(name = "SO_TIEP_NHAN", nullable = true, columnDefinition = "VARCHAR(100)")
	private String soTiepNhan;

	@ApiModelProperty(notes = "Kênh khai báo(1. DN tự khai; 2. Qua đại lý)")
	@Basic
	@Column(name = "KENH_KHAI_BAO", nullable = true, columnDefinition = "CHAR(1)")
	@Size(max = 1)
	@NotBlank(message = "Kênh khai báo không được để trống")
	private String kenhKhaiBao;

	@ApiModelProperty(notes = "Mã người khai hải quan")
	@Basic
	@Column(name = "MA_NGUOI_KHAI_HQ", nullable = true, columnDefinition = "VARCHAR(100)")
	private String maNguoiKhaiHq;

	@ApiModelProperty(notes = "Tên người khai hải quan")
	@Basic
	@Column(name = "TEN_NGUOI_KHAI_HQ", nullable = true, columnDefinition = "VARCHAR2(255)")
	private String tenNguoiKhaiHq;

	@ApiModelProperty(notes = "Mã chức năng (8-khai mới, 5-sửa)")
	@Basic
	@Column(name = "MA_CHUC_NANG", nullable = true, columnDefinition = "CHAR(1)")
	@Size(max = 1)
	private String maChucNang;

	@ApiModelProperty(notes = "Số tham chiếu tờ khai")
	@Basic
	@Column(name = "SO_THAM_CHIEU_TK", nullable = true, columnDefinition = "VARCHAR2(36)")
	@Size(max = 36)
	private String soThamChieuTk;

	@ApiModelProperty(notes = "Ngày trả lời")
	@Basic
	@Column(name = "NGAY_TRA_LOI", nullable = true, columnDefinition = "DATE")
	private Date ngayTraLoi;

	@ApiModelProperty(notes = "Số tờ khai đầu tiên")
	@Basic
	@Column(name = "SO_TK_DAU_TIEN", nullable = true, columnDefinition = "VARCHAR2(12)")
	@Size(max = 12)
	private String soTkDauTien;

	@ApiModelProperty(notes = "Số nhánh của tờ khai chia nhỏ")
	@Basic
	@Column(name = "SO_NHANH_TK", nullable = true, columnDefinition = "NUMBER")
	@Min(0)
	@Max(99)
	private Integer soNhanhTk;

	@ApiModelProperty(notes = "Tổng số tờ khai chia nhỏ")
	@Basic
	@Column(name = "TONG_SO_TK", nullable = true, columnDefinition = "NUMBER")
	@Min(0)
	@Max(99)
	private Integer tongSoTk;

	@ApiModelProperty(notes = "So to khai (Không phải nhập dữ liệu, hệ thống tự trả về)")
	@Basic
	@Column(name = "SO_TK", nullable = true, columnDefinition = "VARCHAR2(12)")
	private String soTk;

	@ApiModelProperty(notes = "So to khai tam nhap tai xuat tuong ung")
	@Basic
	@Column(name = "SO_TK_XKTC", nullable = true, columnDefinition = "VARCHAR2(20)")
	@Size(max = 20)
	private String soTkXktc;

	@ApiModelProperty(notes = "Ma loai hinh")
	@Basic
	@Column(name = "MA_LOAI_HINH", nullable = false, columnDefinition = "VARCHAR2(3)")
	@NotNull
	@Size(max = 3)
	private String maLoaiHinh;

	@ApiModelProperty(notes = "Ma phan loai hang hoa")
	@Basic
	@Column(name = "PL_HANG_HOA", nullable = true, columnDefinition = "VARCHAR2(1)")
	@Size(max = 1)
	private String plHangHoa;

	@ApiModelProperty(notes = "Co quan hai quan")
	@Basic
	@Column(name = "CO_QUAN_HQ", nullable = true, columnDefinition = "VARCHAR2(6)")
	@NotNull
	@Size(max = 6)
	private String coQuanHq;

	@ApiModelProperty(notes = "Ngay khai du kien")
	@Basic
	@Column(name = "NGAY_KHAI_DU_KIEN", nullable = true, columnDefinition = "DATE")
	private String ngayKhaiDuKien;

	@ApiModelProperty(notes = "Thoi han tai xuat")
	@Basic
	@Column(name = "THOI_HAN_TAI_XUAT", nullable = true, columnDefinition = "DATE")
	private String thoiHanTaiXuat;
	
	@ApiModelProperty(notes = "Thoi han tai nhap")
	@Basic
	@Column(name = "THOI_HAN_TAI_NHAP", nullable = true, columnDefinition = "DATE")
	private String thoiHanTaiNhap;

	@ApiModelProperty(notes = "Phan loai ca nhan/ to chuc")
	@Basic
	@Column(name = "PL_CA_NHAN_TO_CHUC", nullable = true, columnDefinition = "VARCHAR2(1)")
	@Size(max = 1)
	private String plCaNhanToChuc;

	@ApiModelProperty(notes = "Ma bo phan xu ly to khai")
	@Basic
	@Column(name = "PL_KTHS", nullable = true, columnDefinition = "VARCHAR2(2)")
	@Size(max = 2)
	private String plKths;

	@ApiModelProperty(notes = "Ma hieu phuong thuc van chuyen")
	@Basic
	@Column(name = "MA_PT_VAN_CHUYEN", nullable = true, columnDefinition = "CHAR(1)")
	@Size(max = 1)
	private String maPtVanChuyen;

	@ApiModelProperty(notes = "ma nguoi nhap khau")
	@Basic
	@Column(name = "MA_NGUOI_NK", nullable = true, columnDefinition = "VARCHAR2(13)")
	@Size(max = 13)
	private String maNguoiNk;

	@ApiModelProperty(notes = "Tên người nhập khẩu")
	@Basic
	@Column(name = "TEN_NGUOI_NK", nullable = true, columnDefinition = "VARCHAR2(200)")
	@Size(max = 200)
	private String tenNguoiNk;

	@ApiModelProperty(notes = "Ma buu chinh")
	@Basic
	@Column(name = "MA_BUU_CHINH_NGUOI_NK", nullable = true, columnDefinition = "VARCHAR2(9)")
	@Size(max = 9)
	private String maBuuChinhNguoiNk;

	@ApiModelProperty(notes = "Dia chi nguoi nhau khau")
	@Basic
	@Column(name = "DIA_CHI_NGUOI_NK", nullable = true, columnDefinition = "VARCHAR2(200)")
	@Size(max = 200)
	private String diaChiNguoiNk;

	@ApiModelProperty(notes = "Dien thoai nguoi nhap khau")
	@Basic
	@Column(name = "SDT_NGUOI_NK", nullable = true, columnDefinition = "VARCHAR2(20)")
	@Size(max = 20)
	private String sdtNguoiNk;

	@ApiModelProperty(notes = "Ma nguoi uy thac nhap khau")
	@Basic
	@Column(name = "MA_NGUOI_UY_THAC_NK", nullable = true, columnDefinition = "VARCHAR2(20)")
	@Size(max = 20)
	private String maNguoiUyThacNk;

	@ApiModelProperty(notes = "Ten nguoi uy thac nhap khau")
	@Basic
	@Column(name = "TEN_NGUOI_UY_THAC_NK", nullable = true, columnDefinition = "VARCHAR2(256)")
	@Size(max = 256)
	private String tenNguoiUyThacNk;

	@ApiModelProperty(notes = "Ma nguoi xuat khau")
	@Basic
	@Column(name = "MA_NGUOI_XK", nullable = true, columnDefinition = "VARCHAR2(13)")
	@Size(max = 13)
	private String maNguoiXk;

	@ApiModelProperty(notes = "Ten nguoi xuat khau")
	@Basic
	@Column(name = "TEN_NGUOI_XK", nullable = true, columnDefinition = "VARCHAR2(200)")
	@Size(max = 200)
	private String tenNguoiXk;

	@ApiModelProperty(notes = "Ma buu dien nguoi xuat khau")
	@Basic
	@Column(name = "MA_BUU_CHINH_NGUOI_XK", nullable = true, columnDefinition = "VARCHAR2(9)")
	@Size(max = 9)
	private String maBuuChinhNguoiXk;

	@ApiModelProperty(notes = "Dia chi nguoi xuat khau")
	@Basic
	@Column(name = "DIA_CHI_XK_1", nullable = true, columnDefinition = "VARCHAR2(200)")
	@Size(max = 200)
	private String diaChiXk1;

	@ApiModelProperty(notes = "Dia chi nguoi xuat khau")
	@Basic
	@Column(name = "DIA_CHI_XK_2", nullable = true, columnDefinition = "VARCHAR2(200)")
	@Size(max = 200)
	private String diaChiXk2;

	@ApiModelProperty(notes = "Dia chi nguoi xuat khau")
	@Basic
	@Column(name = "DIA_CHI_XK_3", nullable = true, columnDefinition = "VARCHAR2(200)")
	@Size(max = 200)
	private String diaChiXk3;

	@ApiModelProperty(notes = "Dia chi nguoi xuat khau")
	@Basic
	@Column(name = "DIA_CHI_XK_4", nullable = true, columnDefinition = "VARCHAR2(200)")
	@Size(max = 200)
	private String diaChiXk4;

	@ApiModelProperty(notes = "Ma nuoc nguoi xuat khau")
	@Basic
	@Column(name = "MA_NUOC_XK", nullable = true, columnDefinition = "VARCHAR2(2)")
	@Size(max = 2)
	private String maNuocXk;
	
	@ApiModelProperty(notes = "Ma nuoc nguoi nhap khau")
	@Basic
	@Column(name = "MA_NUOC_NK", nullable = true, columnDefinition = "VARCHAR2(2)")
	@Size(max = 2)
	private String maNuocNk;

	@ApiModelProperty(notes = "Mã người ủy thác xuất khẩu")
	@Basic
	@Column(name = "MA_NGUOI_UY_THAC_XK", nullable = true, columnDefinition = "VARCHAR2(20)")
	@Size(max = 20)
	private String maNguoiUyThacXk;

	@ApiModelProperty(notes = "Tên người ủy thác xuất khẩu")
	@Basic
	@Column(name = "TEN_NGUOI_UY_THAC_XK", nullable = true, columnDefinition = "VARCHAR2(200)")
	@Size(max = 200)
	private String tenNguoiUyThacXk;

	@ApiModelProperty(notes = "Mã đại lý hải quan")
	@Basic
	@Column(name = "MA_DAI_LY_HQ", nullable = true, columnDefinition = "VARCHAR2(5)")
	@Size(max = 5)
	private String maDaiLyHq;

	@ApiModelProperty(notes = "Số Vận đơn (Số B/L số AWB v.v...) (1: Khai báo số định danh theo chuẩn quản lý giám sát Hải quan tự động tại Cảng biển; 2: Khai báo số định danh theo chuẩn quản lý giám sát Hải quan tự động tại Cảng hàng không)")
	@Basic
	@Column(name = "SO_VAN_DON", nullable = true, columnDefinition = "CHAR(1)")
	@Size(max = 1)
	private String soVanDon;

	@ApiModelProperty(notes = "So luong kien hang")
	@Basic
	@Column(name = "SO_LUONG_KIEN", nullable = true, columnDefinition = "NUMBER")
	@Min(0)
	@Max(Integer.MAX_VALUE)
	private Integer soLuongKien;

	@ApiModelProperty(notes = "Kien hang combobox")
	@Basic
	@Column(name = "SO_LUONG_KIEN_DVT", nullable = true, columnDefinition = "VARCHAR2(4)")
	@Size(max = 4)
	private String soLuongKienDvt;

	@ApiModelProperty(notes = "Tong trong luong hang hoa (gross)")
	@Basic
	@Column(name = "TONG_TRONG_LUONG", nullable = true, columnDefinition = "NUMBER")
	private BigDecimal tongTrongLuong;

	@ApiModelProperty(notes = "Tong trong luong hang hoa (gross)-combobox")
	@Basic
	@Column(name = "TONG_TRONG_LUONG_DVT", nullable = true, columnDefinition = "VARCHAR2(4)")
	@Size(max = 4)
	private String tongTrongLuongDvt;

	@ApiModelProperty(notes = "Ma dia diem luu kho hang cho thong quan du kien")
	@Basic
	@Column(name = "DIA_DIEM_LUU_KHO_HANG_CTQDK", nullable = true, columnDefinition = "VARCHAR2(7)")
	@Size(max = 7)
	private String diaDiemLuuKhoHangCtqdk;

	@ApiModelProperty(notes = "Ký hiệu và số hiệu")
	@Basic
	@Column(name = "KY_HIEU_SO_HIEU", nullable = true, columnDefinition = "VARCHAR2(140)")
	@Size(max = 140)
	private String kyHieuSoHieu;

	@ApiModelProperty(notes = "Ma Phuong tien van chuyen")
	@Basic
	@Column(name = "MA_PHUONG_TIEN_VAN_CHUYEN", nullable = true, columnDefinition = "VARCHAR2(9)")
	@Size(max = 9)
	private String maPhuongTienVanChuyen;

	@ApiModelProperty(notes = "Ten Phuong tien van chuyen")
	@Basic
	@Column(name = "TEN_PHUONG_TIEN_VAN_CHUYEN", nullable = true, columnDefinition = "VARCHAR2(200)")
	@Size(max = 200)
	private String tenPhuongTienVanChuyen;

	@ApiModelProperty(notes = "Ngay den")
	@Basic
	@Column(name = "NGAY_DEN", nullable = true, columnDefinition = "DATE")
	private String ngayDen;

	@ApiModelProperty(notes = "Ma Dia diem do hang")
	@Basic
	@Column(name = "MA_DIA_DIEM_DO_HANG", nullable = true, columnDefinition = "VARCHAR2(6)")
	@Size(max = 6)
	private String maDiaDiemDoHang;

	@ApiModelProperty(notes = "Ten Dia diem do hang")
	@Basic
	@Column(name = "TEN_DIA_DIEM_DO_HANG", nullable = true, columnDefinition = "VARCHAR2(200)")
	@Size(max = 200)
	private String tenDiaDiemDoHang;

	@ApiModelProperty(notes = "So luong container")
	@Basic
	@Column(name = "SO_CONTAINER", nullable = true, columnDefinition = "NUMBER")
	@Min(0)
	@Max(Integer.MAX_VALUE)
	private Integer soContainer;

	@ApiModelProperty(notes = "Ma ket qua kiem tra noi dung")
	@Basic
	@Column(name = "KET_QUA_XU_LY", nullable = true, columnDefinition = "VARCHAR(3)")
	private String ketQuaXuLy;

	@ApiModelProperty(notes = "1: Nhap khau; 2: xuat khua")
	@Basic
	@Column(name = "LOAI_TK_HQ", nullable = true, columnDefinition = "CHAR(3)")
	@Size(max = 3)
	private String loaiTkHq;

	@ApiModelProperty(notes = "NGUOI_TAO")
	@Basic
	@Column(name = "NGUOI_TAO", nullable = true, columnDefinition = "VARCHAR2(50)")
	private String nguoiTao;

	@ApiModelProperty(notes = "NGAY_TAO")
	@Basic
	@Column(name = "NGAY_TAO", nullable = true, columnDefinition = "DATE")
	private Date ngayTao;

	@ApiModelProperty(notes = "NGUOI_SUA")
	@Basic
	@Column(name = "NGUOI_SUA", nullable = true, columnDefinition = "VARCHAR2(50)")
	private String nguoiSua;

	@ApiModelProperty(notes = "NGAY_SUA")
	@Basic
	@Column(name = "NGAY_SUA", nullable = true, columnDefinition = "DATE")
	private Date ngaySua;

	@ApiModelProperty(notes = "DOANH_NGHIEP_ID")
	@Column(name = "DOANH_NGHIEP_ID", nullable = false, columnDefinition = "VARCHAR2(200)")
	private String doanhNghiepId;

	@ApiModelProperty(notes = "LUONG_TO_KHAI")
	@Basic
	@Column(name = "LUONG_TO_KHAI", nullable = true, columnDefinition = "VARCHAR2(3)")
	private String luongToKhai;

	@ApiModelProperty(notes = "LY_DO_TU_CHOI")
	@Basic
	@Column(name = "LY_DO_TU_CHOI", nullable = true, columnDefinition = "VARCHAR2(200)")
	private String lyDoTuChoi;

	@ApiModelProperty(notes = "PHAN_LOAI_CHI_THI_HQ")
	@Basic
	@Column(name = "PHAN_LOAI_CHI_THI_HQ", nullable = true, columnDefinition = "NUMBER")
	private Integer phanLoaiChiThiHq;

	@ApiModelProperty(notes = "Tổng số lượng kiên")
	@Basic
	@Column(name = "TONG_SO_LUONG_KIEN", nullable = true, columnDefinition = "NUMBER")
	@Min(0)
	@Max(Integer.MAX_VALUE)
	private Integer tongSoLuongKien;

	@ApiModelProperty(notes = "Mã đơn vị tính Ví dụ: CS: thùng BX: hộp….(Tham khảo bảng “Mã loại kiện” trên website Hải quan: www.customs.gov.vn)")
	@Basic
	@Column(name = "TONG_SO_LUONG_KIEN_DVT", nullable = true, columnDefinition = "VARCHAR2(4)")
	private String tongSoLuongKienDvt;

	@ApiModelProperty(notes = "Mã địa điểm nhận hàng cuối cùng ")
	@Basic
	@Column(name = "MA_DIA_DIEM_NHAN_HANG_CUOI", nullable = true, columnDefinition = "VARCHAR2(200)")
	private String maDiaDiemNhanHangCuoi;

	@ApiModelProperty(notes = "Tên địa điểm nhận hàng cuối cùng ")
	@Basic
	@Column(name = "TEN_DIA_DIEM_NHAN_HANG_CUOI", nullable = true, columnDefinition = "VARCHAR2(200)")
	private String tenDiaDiemNhanHangCuoi;

	@ApiModelProperty(notes = "Ngày hàng đi dự kiến")
	@Basic
	@Column(name = "NGAY_HANG_DI_DU_KIEN", nullable = true, columnDefinition = "DATE")
	private String ngayHangDiDuKien;

	@ApiModelProperty(notes = "So hop dong GC")
	@Basic
	@Column(name = "GC_SO_HOP_DONG", nullable = true, columnDefinition = "VARCHAR2(50)")
	@Size(max = 50)
	private String gcSoHopDong;

	@ApiModelProperty(notes = "Ngay hop dong GC")
	@Basic
	@Column(name = "GC_NGAY_HOP_DONG", nullable = true, columnDefinition = "DATE")
	private String gcNgayHopDong;

	@ApiModelProperty(notes = "Ngay het han")
	@Basic
	@Column(name = "GC_NGAY_HET_HAN", nullable = true, columnDefinition = "DATE")
	private String gcNgayHetHan;

	@ApiModelProperty(notes = "2.80 Mã văn bản pháp luật khác")
	@Basic
	@Column(name = "MA_VB", nullable = true, columnDefinition = "VARCHAR2(200)")
	@Size(max = 200)
	private String maVb;

	@ApiModelProperty(notes = "Phân loại hình thức hóa đơn")
	@Basic
	@Column(name = "HOA_DON_PL_HINH_THUC", nullable = false, columnDefinition = "VARCHAR2(1)")
	@NotNull
	@Size(max = 1)
	private String hoaDonPlHinhThuc;

	@ApiModelProperty(notes = "So tiep nhan hoa don dien tu")
	@Basic
	@Column(name = "HOA_DON_SO_DIEN_TU", nullable = true, columnDefinition = "VARCHAR2(12)")
	@Size(max = 12)
	private String hoaDonSoDienTu;

	@ApiModelProperty(notes = "So hoa don")
	@Basic
	@Column(name = "HOA_DON_SO", nullable = true, columnDefinition = "VARCHAR2(35)")
	@Size(max = 35)
	private String hoaDonSo;

	@ApiModelProperty(notes = "Ngay phat hanh")
	@Basic
	@Column(name = "HOA_DON_NGAY_PHAT_HANH", nullable = true, columnDefinition = "VARCHAR2(10)")
	@Size(max = 10)
	private String hoaDonNgayPhatHanh;

	@ApiModelProperty(notes = "Phuong thuc thanh toan")
	@Basic
	@Column(name = "HOA_DON_PT_THANH_TOAN", nullable = true, columnDefinition = "VARCHAR2(7)")
	@Size(max = 7)
	private String hoaDonPtThanhToan;

	@ApiModelProperty(notes = "Ma phan loai gia hoa don")
	@Basic
	@Column(name = "HOA_DON_PL_GIA", nullable = true, columnDefinition = "VARCHAR2(1)")
	@Size(max = 1)
	private String hoaDonPlGia;

	@ApiModelProperty(notes = "Điều kiện giá hoá đơn")
	@Basic
	@Column(name = "HOA_DON_DIEU_KIEN_GIA", nullable = true, columnDefinition = "VARCHAR2(3)")
	@Size(max = 3)
	private String hoaDonDieuKienGia;

	@ApiModelProperty(notes = "Tong tri gia hoa don")
	@Basic
	@Column(name = "HOA_DON_TONG_TRI_GIA_KB", nullable = true, columnDefinition = "NUMBER")
	private BigDecimal hoaDonTongTriGiaKb;

	@ApiModelProperty(notes = "Ma dong tien cua hoa don")
	@Basic
	@Column(name = "HOA_DON_MA_TIEN_TE", nullable = true, columnDefinition = "VARCHAR2(3)")
	@Size(max = 3)
	private String hoaDonMaTienTe;

	@ApiModelProperty(notes = "Ma phan loai khai tri gia")
	@Basic
	@Column(name = "TG_MA_PL_TK", nullable = true, columnDefinition = "VARCHAR2(1)")
	@Size(max = 1)
	private String tgMaPlTk;

	@ApiModelProperty(notes = "So tiep nhan to khai gia tri tong hop")
	@Basic
	@Column(name = "TG_SO_TIEP_NHAN_TKTH", nullable = true, columnDefinition = "VARCHAR2(9)")
	@Size(max = 9)
	private String tgSoTiepNhanTkth;

	@ApiModelProperty(notes = "Ma tien te")
	@Basic
	@Column(name = "TG_MA_TIEN_TE", nullable = true, columnDefinition = "VARCHAR2(3)")
	@Size(max = 3)
	private String tgMaTienTe;

	@ApiModelProperty(notes = "Gia co so de hieu chinh gia")
	@Basic
	@Column(name = "TG_GIA_CO_SO_HC", nullable = true, columnDefinition = "NUMBER")
	@Min(0L)
	@Max(Long.MAX_VALUE)
	private Long tgGiaCoSoHc;

	@ApiModelProperty(notes = "Ma loai phi van chuyen")
	@Basic
	@Column(name = "VC_MA_LOAI_PHI", nullable = true, columnDefinition = "VARCHAR2(1)")
	@Size(max = 1)
	private String vcMaLoaiPhi;

	@ApiModelProperty(notes = "Ma tien phi van chuyen")
	@Basic
	@Column(name = "VC_MA_TIEN_TE_PHI", nullable = true, columnDefinition = "VARCHAR2(3)")
	@Size(max = 3)
	private String vcMaTienTePhi;

	@ApiModelProperty(notes = "Phi van chuyen")
	@Basic
	@Column(name = "VC_PHI", nullable = true, columnDefinition = "NUMBER")
	private BigDecimal vcPhi;

	@ApiModelProperty(notes = "Ma loai phi bao hiem")
	@Basic
	@Column(name = "BH_MA_LOAI_PHI", nullable = true, columnDefinition = "VARCHAR2(1)")
	@Size(max = 1)
	private String bhMaLoaiPhi;

	@ApiModelProperty(notes = "Ma tien phi bao hiem")
	@Basic
	@Column(name = "BH_MA_TIEN_TE", nullable = true, columnDefinition = "VARCHAR2(3)")
	@Size(max = 3)
	private String bhMaTienTe;

	@ApiModelProperty(notes = "Phi bao hiem")
	@Basic
	@Column(name = "BH_PHI", nullable = true, columnDefinition = "NUMBER")
	private BigDecimal bhPhi;

	@ApiModelProperty(notes = "Chi tiet khai tri gia")
	@Basic
	@Column(name = "CHI_TIET_TK_TRI_GIA", nullable = true, columnDefinition = "VARCHAR2(300)")
	@Size(max = 300)
	private String chiTietTkTriGia;

	@ApiModelProperty(notes = "Tong he so phan bo tri gia")
	@Basic
	@Column(name = "DC_TRI_GIA_TONG_HS_PHAN_BO", nullable = true, columnDefinition = "NUMBER")
	private BigDecimal dcTriGiaTongHsPhanBo;

	@ApiModelProperty(notes = "Nguoi nop thue")
	@Basic
	@Column(name = "NGUOI_NOP_THUE", nullable = true, columnDefinition = "CHAR(1)")
	@Size(max = 1)
	private String nguoiNopThue;

	@ApiModelProperty(notes = "Mã lý do đề nghị BP")
	@Basic
	@Column(name = "MA_LY_DO_DNBP", nullable = true, columnDefinition = "CHAR(1)")
	@Size(max = 1)
	private String maLyDoDnbp;

	@ApiModelProperty(notes = "Ma ngan hang tra thue thay")
	@Basic
	@Column(name = "MA_NGAN_HANG_TRA_THUE_THAY", nullable = true, columnDefinition = "VARCHAR2(11)")
	@Size(max = 11)
	private String maNganHangTraThueThay;

	@ApiModelProperty(notes = "Nam phat hanh han muc")
	@Basic
	@Column(name = "HAN_MUC_NAM_PHAT_HANH", nullable = true, columnDefinition = "NUMBER")
	private String hanMucNamPhatHanh;

	@ApiModelProperty(notes = "Ky hieu chung tu han muc")
	@Basic
	@Column(name = "HAN_MUC_KI_HIEU_CHUNG_TU", nullable = true, columnDefinition = "VARCHAR2(10)")
	@Size(max = 10)
	private String hanMucKiHieuChungTu;

	@ApiModelProperty(notes = "So chung tu han muc")
	@Basic
	@Column(name = "HAN_MUC_SO_CHUNG_TU", nullable = true, columnDefinition = "VARCHAR2(10)")
	@Size(max = 10)
	private String hanMucSoChungTu;

	@ApiModelProperty(notes = "Ngày hoàn thành nv thuế")
	@Basic
	@Column(name = "NGAY_HOAN_THANH_NV_THUE", nullable = true, columnDefinition = "DATE")
	private String ngayHoanThanhNvThue;
	
	@ApiModelProperty(notes = "Mã xác định thời hạn nộp thuế")
	@Basic
	@Column(name = "MA_XAC_DINH_THOI_HAN_NOP_THUE", nullable = true, columnDefinition = "CHAR(1)")
	@Size(max = 1)
	private String maXacDinhThoiHanNopThue;

	@ApiModelProperty(notes = "Mã ngân hàng bảo lãnh")
	@Basic
	@Column(name = "BAO_LANH_MA_NGAN_HANG", nullable = true, columnDefinition = "VARCHAR2(11)")
	@Size(max = 11)
	private String baoLanhMaNganHang;

	@ApiModelProperty(notes = "Nam phat hanh bao lanh")
	@Basic
	@Column(name = "BAO_LANH_NAM_PHAT_HANH", nullable = true, columnDefinition = "VARCHAR2(4)")
	private String baoLanhNamPhatHanh;

	@ApiModelProperty(notes = "Ky hieu chung tu bao lanh")
	@Basic
	@Column(name = "BAO_LANH_KI_HIEU_CHUNG_TU", nullable = true, columnDefinition = "VARCHAR2(10)")
	@Size(max = 10)
	private String baoLanhKiHieuChungTu;

	@ApiModelProperty(notes = "So chung tu bao lanh")
	@Basic
	@Column(name = "BAO_LANH_SO_CHUNG_TU", nullable = true, columnDefinition = "VARCHAR2(10)")
	@Size(max = 10)
	private String baoLanhSoChungTu;

	@ApiModelProperty(notes = "Ngay duoc phep nhap kho dau tien")
	@Basic
	@Column(name = "NGAY_NHAP_KHO_DAU_TIEN", nullable = true, columnDefinition = "DATE")
	private String ngayNhapKhoDauTien;

	@ApiModelProperty(notes = "Ngay khoi hanh van chuyen")
	@Basic
	@Column(name = "NGAY_KHOI_HANH", nullable = true, columnDefinition = "DATE")
	private String ngayKhoiHanh;

	@ApiModelProperty(notes = "Ngày dự kiến đến(Đến điểm đích)")
	@Basic
	@Column(name = "NGAY_DEN_DU_KIEN", nullable = true, columnDefinition = "DATE")
	private String ngayDenDuKien;

	@ApiModelProperty(notes = "Địa điểm đích cho vận chuyển bảo thuế")
	@Basic
	@Column(name = "DIA_DIEM_DICH_VCBT", nullable = true, columnDefinition = "VARCHAR2(7)")
	@Size(max = 7)
	private String diaDiemDichVcbt;

	@ApiModelProperty(notes = "Phan ghi chu")
	@Basic
	@Column(name = "GHI_CHU", nullable = true, columnDefinition = "VARCHAR2(500)")
	@Size(max = 500)
	private String ghiChu;

	@ApiModelProperty(notes = "So quan ly cua noi bo doanh nghiep")
	@Basic
	@Column(name = "SO_QUAN_LY_NBDN", nullable = true, columnDefinition = "VARCHAR2(20)")
	@Size(max = 20)
	private String soQuanLyNbdn;

	@ApiModelProperty(notes = "Số tờ khai xuất khẩu tại chỗ tương ứng")
	@Basic
	@Column(name = "SO_TK_XK_TAI_CHO_TUONG_UNG", nullable = true, columnDefinition = "VARCHAR(200)")
	private String soTKXKTaiChoTuongUng;

	@Column(name = "SO_DANG_KY_BAO_HIEM_TONG_HOP", nullable = true, columnDefinition = "VARCHAR(6)")
	@Size(max = 6)
	private String soDangKyBaoHiemTongHop;

	@Column(name = "PL_KHONG_CAN_QUY_DOI_VND", nullable = true, columnDefinition = "VARCHAR(1)")
	@Size(max = 1)
	private String plKhongCanQuyDoiVND;

	@Column(name = "DIA_CHI_NGUOI_XK", nullable = true, columnDefinition = "VARCHAR(200)")
	@Size(max = 200)
	private String diaChiNguoiXk;

	@Column(name = "SDT_NGUOI_XK", nullable = true, columnDefinition = "VARCHAR(20)")
	@Size(max = 20)
	private String sdtNguoiXk;

	@Column(name = "DIA_CHI_NK_1", nullable = true, columnDefinition = "VARCHAR(200)")
	@Size(max = 200)
	private String diaChiNk1;
	
	@Column(name = "DIA_CHI_NK_2", nullable = true, columnDefinition = "VARCHAR(200)")
	@Size(max = 200)
	private String diaChiNk2;
	
	@Column(name = "DIA_CHI_NK_3", nullable = true, columnDefinition = "VARCHAR(200)")
	@Size(max = 200)
	private String diaChiNk3;
	
	@Column(name = "DIA_CHI_NK_4", nullable = true, columnDefinition = "VARCHAR(200)")
	@Size(max = 200)
	private String diaChiNk4;
	
	@Column(name = "TONG_TRI_GIA_TINH_THUE",nullable = true,columnDefinition = "NUMBER")
	@Digits(integer = 20, fraction = 4)
	private BigDecimal tongTriGiaTinhThue;
	
	@Column(name = "TONG_TRI_GIA_TINH_THUE_MA_TIEN_TE",nullable = true,columnDefinition = "VARCHAR(3)")
	@Size(max = 3)
	private String tongTriGiaTinhThueMaTienTe;
	
    @ApiModelProperty(notes = "MA_KET_QUA_KIEM_TRA_ND")
    @Basic
    @Column(name = "MA_KET_QUA_KIEM_TRA_ND", nullable = true, columnDefinition = "VARCHAR(1)")
    private String maKetQuaKiemTraNd;
    
    @ApiModelProperty(notes = "Ngày cấp số tiếp nhận")
    @Basic
    @Column(name = "NGAY_CAP_SO_TIEP_NHAN", nullable = true, columnDefinition = "DATE")
	private Date ngayCapSoTiepNhan;
    
    @ApiModelProperty(notes = "Ngày cấp số tờ khai")
    @Basic
    @Column(name = "NGAY_DANG_KY", nullable = true, columnDefinition = "DATE")
    private Date ngayDangKy;
    
    @ApiModelProperty(notes = "Hải quan tiếp nhận khai báo")
    @Basic
    @Column(name = "HAI_QUAN_TIEP_NHAN_KB", nullable = true, columnDefinition = "VARCHAR(6)")
    private String haiQuanTiepNhanKb;

	@ApiModelProperty(notes = "Ten Hải quan tiếp nhận khai báo")
	@Basic
	@Column(name = "TEN_HAI_QUAN_TIEP_NHAN_KB", nullable = true, columnDefinition = "VARCHAR(2000)")
	private String tenHaiQuanTiepNhanKb;
    
    @ApiModelProperty(notes = "Mẫu tờ khai hải quan id tham chiếu")
    @Basic
    @Column(name = "MAU_TO_KHAI_HQ_ID_THAM_CHIEU", nullable = true, columnDefinition = "VARCHAR(36)")
    private String mauToKhaiHqIdThamChieu;

    @ApiModelProperty(notes = "mã doanh nghiệp")
	@Basic
	@Column(name = "MA_DOANH_NGHIEP", nullable = true, columnDefinition = "VARCHAR(14)")
	private String maDoanhNghiep;

	@ApiModelProperty(notes = "Tên doanh nghiệp")
	@Basic
	@Column(name = "TEN_DOANH_NGHIEP", nullable = true, columnDefinition = "VARCHAR2(2000)")
	private String tenDoanhNghiep;
    
    @ApiModelProperty(notes = "kbSoDinhDanhCangBien")
	@Basic
	@Column(name = "KB_SO_DINH_DANH_CANG_BIEN", nullable = true, columnDefinition = "NUMBER")
	private int kbSoDinhDanhCangBien;

    @ApiModelProperty(notes = "kbSoDinhDanhCangHK")
	@Basic
	@Column(name = "KB_SO_DINH_DANH_CANG_HK", nullable = true, columnDefinition = "NUMBER")
	private int kbSoDinhDanhCangHK;
    
    @ApiModelProperty(notes = "1.34. Địa điểm xếp hàng")
	@Basic
	@Column(name = "MA_DIA_DIEM_XEP_HANG", nullable = true, columnDefinition = "VARCHAR(7)")
	private String maDiaDiemXepHang;

	@ApiModelProperty(notes = "1.34. Tên Địa điểm xếp hàng")
	@Basic
	@Column(name = "TEN_DIA_DIEM_XEP_HANG", nullable = true, columnDefinition = "VARCHAR2(70)")
	private String tenDiaDiemXepHang;

	@Transient
	@ApiParam(value = "Danh sach van don")
	private List<TkVanDonEntity> dsVanDon;

	@Transient
	@ApiParam(value = "Danh sach giay phep")
	private List<GiayPhepTkEntity> dsGiayPhep;

	@Transient
	@ApiParam(value = "Danh sach khoan dieu chinh")
	private List<KhoanDieuChinhTkEntity> dsKhoanDieuChinh;

	@Transient
	@ApiParam(value = "Danh sach dinh kem")
	private List<SoDinhKemKbdtEntity> dsDinhKemKbdt;

	@Transient
	@ApiParam(value = "Danh sach thong tin trung chuyen")
	private List<TtTrungChuyenEntity> dsThongTinTc;

	@Transient
	@ApiParam(value = "Danh sach hang")
	private List<DmHangHoaTkEntity> dsHang;
	
	@Transient
	@ApiParam(value = "Danh sach dia diem xep hang")
	private List<DiaDiemXepHangTkEntity> dsDiaDiemXepHang;

}
