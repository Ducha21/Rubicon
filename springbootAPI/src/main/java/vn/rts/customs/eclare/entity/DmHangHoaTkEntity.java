
package vn.rts.customs.eclare.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare.EntityTableEtcCustomsEClare;

/**
 * @author VNETC
 */

@Entity
@Table(name = EntityTableEtcCustomsEClare.DM_HANG_HOA_TK)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
public class DmHangHoaTkEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "ID")
	@Id
	@SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_DM_HANG_HOA_TK", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
	@Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
	@NotNull
	@Min(0L)
	@Max(Long.MAX_VALUE)
	private Long id;

	@ApiModelProperty(notes = "Mã  ngoài hạn ngạch")
	@Basic
	@Column(name = "MA_NGOAI_HAN_NGACH", nullable = true, columnDefinition = "CHAR(1)")
	@Size(max = 1)
	private String maNgoaiHanNgach;

	@ApiModelProperty(notes = "Mã số hàng hóa (HS)")
	@Basic
	@Column(name = "MA_HS", nullable = true, columnDefinition = "VARCHAR2(12)")
	@Size(max = 12)
	private String maHs;

	@ApiModelProperty(notes = "Mã quản lý riêng")
	@Basic
	@Column(name = "MA_QLR", nullable = true, columnDefinition = "VARCHAR2(20)")
	@Size(max = 20)
	private String maQlr;

	@ApiModelProperty(notes = "Thuế suất")
	@Basic
	@Column(name = "THUE_SUAT", nullable = true, columnDefinition = "NUMBER")
	private BigDecimal thueSuat;

	@ApiModelProperty(notes = "Mức thuế tuyệt đối")
	@Basic
	@Column(name = "THUE_SUAT_TUYET_DOI", nullable = true, columnDefinition = "NUMBER")
	private BigDecimal thueSuatTuyetDoi;

	@ApiModelProperty(notes = "Mã đơn vị tính của mức thuế tuyệt đối")
	@Basic
	@Column(name = "THUE_SUAT_TUYET_DOI_DVT", nullable = true, columnDefinition = "VARCHAR2(4)")
	@Size(max = 4)
	private String thueSuatTuyetDoiDvt;

	@ApiModelProperty(notes = "Mã nguyên tệ của mức thuế tuyệt đối")
	@Basic
	@Column(name = "THUE_SUAT_TUYET_DOI_MA_NT", nullable = true, columnDefinition = "VARCHAR2(3)")
	@Size(max = 3)
	private String thueSuatTuyetDoiMaNt;

	@ApiModelProperty(notes = "Mã nước xuất xứ")
	@Basic
	@Column(name = "NUOC_XX_MA", nullable = true, columnDefinition = "VARCHAR2(20)")
	@Size(max = 20)
	private String nuocXxMa;

	@ApiModelProperty(notes = "Mã hàng hóa (Commodity codes)")
	@Basic
	@Column(name = "MA_HANG", nullable = true, columnDefinition = "VARCHAR2(50)")
	@Size(max = 50)
	private String maHang;

	@ApiModelProperty(notes = "Mô tả hàng hóa")
	@Basic
	@Column(name = "TEN_HANG", nullable = true, columnDefinition = "VARCHAR2(500)")
	@Size(max = 500)
	private String tenHang;

	@ApiModelProperty(notes = "Mã miễn / Giảm / Không chịu thuế xuất khẩu")
	@Basic
	@Column(name = "THUE_MIEN_GIAM_MA", nullable = true, columnDefinition = "VARCHAR2(5)")
	@Size(max = 5)
	private String thueMienGiamMa;

	@ApiModelProperty(notes = "Số tiền giảm thuế xuất khẩu")
	@Basic
	@Column(name = "THUE_GIAM", nullable = true, columnDefinition = "NUMBER")
	@Digits(integer = 20, fraction = 4)
	private BigDecimal thueGiam;

	@ApiModelProperty(notes = "Số lượng (1)")
	@Basic
	@Column(name = "LUONG", nullable = false, columnDefinition = "NUMBER")
	private BigDecimal luong;

	@ApiModelProperty(notes = "Mã đơn vị tính của Số lượng (1)")
	@Basic
	@Column(name = "MA_DVT", nullable = false, columnDefinition = "VARCHAR2(4)")
	@Size(max = 4)
	private String maDvt;

	@ApiModelProperty(notes = "Số lượng (2)")
	@Basic
	@Column(name = "LUONG_2", nullable = true, columnDefinition = "NUMBER")
	private BigDecimal luong2;

	@ApiModelProperty(notes = "Mã đơn vị tính của Số lượng (2)")
	@Basic
	@Column(name = "MA_DVT_2", nullable = true, columnDefinition = "VARCHAR2(20)")
	@Size(max = 20)
	private String maDvt2;

	@ApiModelProperty(notes = "Trị giá hóa đơn")
	@Basic
	@Column(name = "TRI_GIA_KB", nullable = true, columnDefinition = "NUMBER")
	private BigDecimal triGiaKb;

	@ApiModelProperty(notes = "Đơn giá hóa đơn")
	@Basic
	@Column(name = "DGIA_KB", nullable = true, columnDefinition = "NUMBER")
	private BigDecimal dgiaKb;

	@ApiModelProperty(notes = "Mã nguyên tệ (của đơn giá hóa đơn)")
	@Basic
	@Column(name = "DGIA_KB_MA_NT", nullable = true, columnDefinition = "VARCHAR2(3)")
	@Size(max = 3)
	private String dgiaKbMaNt;

	@ApiModelProperty(notes = "Mã đơn vị tính ( của đơn giá hóa đơn)")
	@Basic
	@Column(name = "DGIA_KB_DVT", nullable = true, columnDefinition = "VARCHAR2(4)")
	@Size(max = 4)
	private String dgiaKbDvt;

	@ApiModelProperty(notes = "Trị giá tính thuế")
	@Basic
	@Column(name = "TRI_GIA_TINH_THUE", nullable = true, columnDefinition = "NUMBER")
	private BigDecimal triGiaTinhThue;

	@ApiModelProperty(notes = "Trị giá tính thuế đơn vị ")
	@Basic
	@Column(name = "TRI_GIA_TINH_THUE_DVT", nullable = true, columnDefinition = "VARCHAR2(20)")
	@Size(max = 20)
	private String triGiaTinhThueDvt;

	@ApiModelProperty(notes = "Số thứ tự của dòng hàng trên tờ khai tạm nhập tái xuất tương ứng")
	@Basic
	@Column(name = "STT_HANG_TNTX", nullable = true, columnDefinition = "VARCHAR2(20)")
	@Size(max = 20)
	private String sttHangTntx;

	@ApiModelProperty(notes = "Danh mục miễn thuế xuất khẩu")
	@Basic
	@Column(name = "SO_DK_MIEN_THUE", nullable = true, columnDefinition = "VARCHAR2(12)")
	@Size(max = 12)
	private String soDkMienThue;

	@ApiModelProperty(notes = "Số dòng tương ứng trong Danh mục miễn thuế xuất khẩu")
	@Basic
	@Column(name = "STT_HANG_DM_MIEN_THUE", nullable = true, columnDefinition = "NUMBER")
	@Size(max = 3)
	private String sttHangDmMienThue;

	@ApiModelProperty(notes = "Chế độ ưu đãi thuế( Loại C/O(Form A, D, E, AK))")
	@Basic
	@Column(name = "CD_UD_THUE", nullable = true, columnDefinition = "VARCHAR2(50)")
	@Size(max = 50)
	private String cdUdThue;

	@ApiModelProperty(notes = "FK")
	@Basic
	@Column(name = "MAU_KB_HQ_ID", nullable = true, columnDefinition = "VARCHAR(36)")
	private String mauKbHqId;

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

	@ApiModelProperty(notes = "1.82. Mã áp dụng mức thuế tuyệt đối")
	@Basic
	@Column(name = "MA_MUC_THUE_TUYET_DOI", nullable = true, columnDefinition = "VARCHAR(10)")
	private String maMucThueTuyetDoi;

	@ApiModelProperty(notes = "MA_BIEU_THUE")
	@Basic
	@Column(name = "MA_BIEU_THUE", nullable = true, columnDefinition = "VARCHAR(20)")
	private String maBieuThue;

	@ApiModelProperty(notes = "TRI_GIA_KB_VND")
	@Basic
	@Column(name = "TRI_GIA_KB_VND", nullable = true, columnDefinition = "VARCHAR(3)")
	private String triGiaKbVnd;

	@Transient
	private List<ThueKhoanThuKhacEntity> dsThueKhoanThuKhac;
	
	@Transient
	private List<CoHangHoaTkEntity> dsCo;

	@ApiModelProperty(notes = "SO_DANH_MUC_KHOAN_DIEU_CHINH")
	@Basic
	@Column(name = "SO_DANH_MUC_KHOAN_DIEU_CHINH", nullable = true, columnDefinition = "VARCHAR(300)")
	private String soDanhMucKhoanDieuChinh;

	@ApiModelProperty(notes = "STT_HANG")
	@Basic
	@Column(name = "STT_HANG", nullable = true, columnDefinition = "NUMBER")
	private Integer sttHang;

	@ApiModelProperty(notes = "GHI_CHU_KHAC")
	@Basic
	@Column(name = "GHI_CHU_KHAC", nullable = true, columnDefinition = "VARCHAR2(2000)")
	private String ghiChuKhac;

	@ApiModelProperty(notes = "mã doanh nghiệp")
	@Basic
	@Column(name = "MA_DOANH_NGHIEP", nullable = true, columnDefinition = "VARCHAR(14)")
	private String maDoanhNghiep;

	@ApiModelProperty(notes = "Loại hàng")
	@Basic
	@Column(name = "LOAI_HANG", nullable = true, columnDefinition = "VARCHAR(1)")
	private String loaiHang;

}
