package vn.rts.customs.eclare.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;

import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare;
import vn.rts.customs.lib.dto.ConditionalBaseEoEx;
import org.apache.commons.lang3.SerializationUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author CCS4EO_DEV
 */

@Entity
@Table(name = ConstantEtcCustomsEClare.EntityTableEtcCustomsEClare.CTDT_VAN_DON)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CtdtVanDonEntity extends ConditionalBaseEoEx<CtdtVanDonEntity, Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "ID")
	@Id
	@SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_CTDT_VAN_DON", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
	@Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
	@NotNull
	@Min(0L)
	@Max(Long.MAX_VALUE)
	private Long id;

	@ApiModelProperty(notes = "số chứng từ")
	@Basic
	@Column(name = "CHUNG_TU_DINH_KEM_TK_ID", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 36)
	private String chungTuDinhKemTkId;

	@ApiModelProperty(notes = "NGAY_SUA")
	@Basic
	@Column(name = "NGAY_SUA", nullable = true, columnDefinition = "DATE")
	private Date ngaySua;

	@ApiModelProperty(notes = "NGAY_TAO")
	@Basic
	@Column(name = "NGAY_TAO", nullable = true, columnDefinition = "DATE")
	private Date ngayTao;

	@ApiModelProperty(notes = "NGUOI_SUA")
	@Basic
	@Column(name = "NGUOI_SUA", nullable = true, columnDefinition = "VARCHAR2(50)")
	private String nguoiSua;

	@ApiModelProperty(notes = "NGUOI_TAO")
	@Basic
	@Column(name = "NGUOI_TAO", nullable = true, columnDefinition = "VARCHAR2(50)")
	private String nguoiTao;

	@ApiModelProperty(notes = "Số vận đơn")
	@Basic
	@Column(name = "SO_VAN_DON", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 35)
	private String soVanDon;

	@ApiModelProperty(notes = "Ngày phát hành")
	@Basic
	@Column(name = "NGAY_PHAT_HANH", nullable = true, columnDefinition = "DATE")
	@Size(max = 10)
	private Date ngayPhatHanh;

	@ApiModelProperty(notes = "Nước phát hành")
	@Basic
	@Column(name = "NUOC_PHAT_HANH", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 2)
	private String nuocPhatHanh;

	@ApiModelProperty(notes = "Mã người vận chuyển")
	@Basic
	@Column(name = "MA_NGUOI_VAN_CHUYEN", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 17)
	private String maNguoiVanChuyen;

	@ApiModelProperty(notes = "Tên người vận chuyển")
	@Basic
	@Column(name = "TEN_NGUOI_VAN_CHUYEN", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 255)
	private String tenNguoiVanChuyen;

	@ApiModelProperty(notes = "Số lượng container")
	@Basic
	@Column(name = "SO_LUONG_CONTAINER", nullable = true, columnDefinition = "NUMBER")
	@Min(0)
	@Max(Integer.MAX_VALUE)
	private Integer soLuongContainer;

	@ApiModelProperty(notes = "Số lượng kiện")
	@Basic
	@Column(name = "SO_LUONG_KIEN", nullable = true, columnDefinition = "NUMBER")
	@Min(0)
	@Max(Integer.MAX_VALUE)
	private Integer soLuongKien;

	@ApiModelProperty(notes = "Mã đơn vị tính số lượng kiện")
	@Basic
	@Column(name = "MA_DVT_SO_LUONG_KIEN", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 4)
	private String maDvtSoLuongKien;

	@ApiModelProperty(notes = "Tổng trọng lượng (Gross Weight)")
	@Basic
	@Column(name = "TONG_TRONG_LUONG", nullable = true, columnDefinition = "NUMBER")
	@DecimalMin("0")
	@DecimalMax("9999999999999.9999999")
	private BigDecimal tongTrongLuong;

	@ApiModelProperty(notes = "Mã đơn vị tính của tổng trọng lượng")
	@Basic
	@Column(name = "MA_DVT_TONG_TRONG_LUONG", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 4)
	private String maDvtTongTrongLuong;

	@ApiModelProperty(notes = "Phương thức giao hàng")
	@Basic
	@Column(name = "PHUONG_THUC_GIAO_HANG", nullable = true, columnDefinition = "NUMBER")
	@Min(0)
	@Max(Integer.MAX_VALUE)
	private Integer phuongThucGiaoHang;

	@ApiModelProperty(notes = "Số lượng vận đơn nhánh")
	@Basic
	@Column(name = "SO_LUONG_VAN_DON_NHANH", nullable = true, columnDefinition = "NUMBER")
	@Min(0)
	@Max(Integer.MAX_VALUE)
	private Integer soLuongVanDonNhanh;

	@ApiModelProperty(notes = "Địa điểm chuyển tải/quá cảnh")
	@Basic
	@Column(name = "DIA_DIEM_QUA_CANH", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 255)
	private String diaDiemQuaCanh;

	@ApiModelProperty(notes = "Loại vận đơn")
	@Basic
	@Column(name = "LOAI_VAN_DON", nullable = true, columnDefinition = "NUMBER")
	@Min(0)
	@Max(Integer.MAX_VALUE)
	private String loaiVanDon;

	@ApiModelProperty(notes = "Ghi chú khác")
	@Basic
	@Column(name = "GHI_CHU_KHAC", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 2000)
	private String ghiChuKhac;

	@ApiModelProperty(notes = "mã doanh nghiệp")
	@Basic
	@Column(name = "MA_DOANH_NGHIEP", nullable = true, columnDefinition = "VARCHAR(14)")
	private String maDoanhNghiep;

	@Transient
	private List<CtdtVanDonContainerEntity> dsContainer;

	@Transient
	private List<CtdtVanDonNhanhEntity> dsVanDonNhanh;


	@Override
	public Long getRepositoryId() {
		return this.id;
	}

	@Override
	public String getNotifyWithKey() {
		return String.format("ID : [%s]", id);
	}

	@Override
	public CtdtVanDonEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
