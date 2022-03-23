
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
@Table(name = EntityTableEtcCustomsEClare.CTDT_CO)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CtdtCoEntity extends ConditionalBaseEoEx<CtdtCoEntity, Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "ID")
	@Id
	@SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_CTDT_CO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
	@Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
	@NotNull
	@Min(0L)
	@Max(Long.MAX_VALUE)
	private Long id;

	@ApiModelProperty(notes = "Số C/O")
	@Basic
	@Column(name = "SO_CO", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 35)
	private String soCo;

	@ApiModelProperty(notes = "Loại C/O (Form A, D, E, AK...)")
	@Basic
	@Column(name = "LOAI_CO", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 35)
	private String loaiCo;

	@ApiModelProperty(notes = "Ngày cấp C/O")
	@Basic
	@Column(name = "NGAY_CAP_CO", nullable = true, columnDefinition = "DATE")
	private Date ngayCapCo;

	@ApiModelProperty(notes = "Tổ chức cấp C/O")
	@Basic
	@Column(name = "TO_CHUC_CO", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 255)
	private String toChucCo;

	@ApiModelProperty(notes = "Người cấp C/O")
	@Basic
	@Column(name = "NGUOI_CAP_CO", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 255)
	private String nguoiCapCo;

	@ApiModelProperty(notes = "Nước cấp C/O")
	@Basic
	@Column(name = "NUOC_CAP_CO", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 2)
	private String nuocCapCo;

	@ApiModelProperty(notes = "Thời điểm nộp C/O")
	@Basic
	@Column(name = "THOI_DIEM_NOP_CO", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 2)
	private Integer thoiDiemNopCo;

	@ApiModelProperty(notes = "Ghi chú khác")
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

	@ApiModelProperty(notes = "CHUNG TU DINH KEM TK ID")
	@Basic
	@Column(name = "CHUNG_TU_DINH_KEM_TK_ID", nullable = true, columnDefinition = "VARCHAR2(36)")
	private String chungTuDinhKemTkId;

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
	public CtdtCoEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}