package vn.rts.customs.eclare.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
@Table(name = EntityTableEtcCustomsEClare.CTDT_HOP_DONG_UT)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CtdtHopDongUtEntity extends ConditionalBaseEoEx<CtdtHopDongUtEntity, Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "ID")
	@Id
	@SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_CTDT_HOP_DONG_UT", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
	@Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
	@NotNull
	@Min(0L)
	@Max(Long.MAX_VALUE)
	private Long id;

	@ApiModelProperty(notes = "số chứng từ đính kèm")
	@Basic
	@Column(name = "CHUNG_TU_DINH_KEM_TK_ID", nullable = true, columnDefinition = "VARCHAR2")
	private String chungTuDinhKemTkId;

	@ApiModelProperty(notes = "Tên người ủy thác")
	@Basic
	@Column(name = "TEN_NGUOI_UY_THAC", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 256)
	private String tenNguoiUyThac;

	@ApiModelProperty(notes = "Mã người ủy thác")
	@Basic
	@Column(name = "MA_NGUOI_UY_THAC", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 17)
	private String maNguoiUyThac;

	@ApiModelProperty(notes = "Địa chỉ người ủy thác")
	@Basic
	@Column(name = "DIA_CHI_NGUOI_UY_THAC", nullable = true, columnDefinition = "VARCHAR2(255)")
	@Size(max = 256)
	private String diaChiNguoiUyThac;

	@ApiModelProperty(notes = "Tên người nhận nhận ủy thác")
	@Basic
	@Column(name = "TEN_NGUOI_NHAN_UY_THAC", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 256)
	private String tenNguoiNhanUyThac;

	@ApiModelProperty(notes = "Mã người nhận nhận ủy thác")
	@Basic
	@Column(name = "MA_NGUOI_NHAN_UY_THAC", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 17)
	private String maNguoiNhanUyThac;

	@ApiModelProperty(notes = "Địa chỉ người nhận ủy thác")
	@Basic
	@Column(name = "DIA_CHI_NGUOI_NHAN_UY_THAC", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 256)
	private String diaChiNguoiNhanUyThac;

	@ApiModelProperty(notes = "ghi chú khac")
	@Basic
	@Column(name = "GHI_CHU_KHAC", nullable = true, columnDefinition = "VARCHAR2")
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
	public CtdtHopDongUtEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
