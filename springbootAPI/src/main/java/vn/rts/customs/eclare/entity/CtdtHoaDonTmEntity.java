
package vn.rts.customs.eclare.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare.EntityTableEtcCustomsEClare;
import vn.rts.customs.lib.dto.ConditionalBaseEoEx;

/**
 * @author CCS4EO_DEV
 */

@Entity
@Table(name = EntityTableEtcCustomsEClare.CTDT_HOA_DON_TM)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CtdtHoaDonTmEntity extends ConditionalBaseEoEx<CtdtHoaDonTmEntity, Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "ID")
	@Id
	@SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_CTDT_HOA_DON_TM", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
	@Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
	@NotNull
	@Min(0L)
	@Max(Long.MAX_VALUE)
	private Long id;

	@ApiModelProperty(notes = "so chung tu")
	@Basic
	@Column(name = "CHUNG_TU_DINH_KEM_TK_ID", nullable = true, columnDefinition = "VARCHAR2(36)")
	@Size(max = 36)
	private String chungTuDinhKemTkId;

	@ApiModelProperty(notes = "Số hóa đơn thương mại")
	@Basic
	@Column(name = "SO_HOA_DON_TM", nullable = false, columnDefinition = "VARCHAR2(10)")
	@NotNull
	@Size(max = 10)
	private String soHoaDonTm;

	@ApiModelProperty(notes = "Ngày phát hành hóa đơn thương mại")
	@Basic
	@Column(name = "NGAY_PHAT_HANH_HOA_DON_TM", nullable = true, columnDefinition = "DATE")
	private Date ngayPhatHanhHoaDonTm;

	@ApiModelProperty(notes = "Mã phân loại hình thức hóa đơn")
	@Basic
	@Column(name = "MA_PL_HINH_THUC_HOA_DON", nullable = true, columnDefinition = "VARCHAR2(2)")
	@Size(max = 2)
	private String maPlHinhThucHoaDon;

	@ApiModelProperty(notes = "Tổng giá trị hóa đơn")
	@Basic
	@Column(name = "TONG_GIA_TRI_HOA_DON", nullable = true, columnDefinition = "NUMBER")
	@Digits(integer = 21, fraction = 4)
	private BigDecimal tongGiaTriHoaDon;

	@ApiModelProperty(notes = "Đồng tiền thanh toán")
	@Basic
	@Column(name = "DON_VI_TIEN_THANH_TOAN", nullable = true, columnDefinition = "VARCHAR2(3)")
	@Size(max = 3)
	private String donViTienThanhToan;

	@ApiModelProperty(notes = "Ghi chú khác")
	@Basic
	@Column(name = "GHI_CHU_KHAC", nullable = true, columnDefinition = "VARCHAR2(2000)")
	@Size(max = 2000)
	private String ghiChuKhac;

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

	@ApiModelProperty(notes = "mã doanh nghiệp")
	@Basic
	@Column(name = "MA_DOANH_NGHIEP", nullable = true, columnDefinition = "VARCHAR(14)")
	private String maDoanhNghiep;

	@Override
	public Long getRepositoryId() {
		return this.id;
	}

	@Override
	public String getNotifyWithKey() {
		return String.format("ID : [%s]", id);
	}

	@Override
	public CtdtHoaDonTmEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}