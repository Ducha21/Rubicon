
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
@Table(name = EntityTableEtcCustomsEClare.DM_HANG_HOA)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class DmHangHoaEntity extends ConditionalBaseEoEx<DmHangHoaEntity, Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "ID")
	@Id
	@SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_DM_HANG_HOA", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
	@Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
	@NotNull
	@Min(0L)
	@Max(Long.MAX_VALUE)
	private Long id;

	@ApiModelProperty(notes = "Mã hàng")
	@Basic
	@Column(name = "MA_HANG", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 50)
	private String maHang;

	@ApiModelProperty(notes = "Mã HS")
	@Basic
	@Column(name = "MA_HS", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 12)
	private String maHs;

	@ApiModelProperty(notes = "Tên hàng")
	@Basic
	@Column(name = "TEN_HANG", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 500)
	private String tenHang;

	@ApiModelProperty(notes = "Danh nghiệp khai báo")
	@Basic
	@Column(name = "DANH_NGHIEP_ID", nullable = true, columnDefinition = "NUMBER")
	@Min(0L)
	@Max(Long.MAX_VALUE)
	private Long danhNghiepId;

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
	public DmHangHoaEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}