
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
@Table(name = EntityTableEtcCustomsEClare.CTDT_CMTC)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CtdtCmtcEntity extends ConditionalBaseEoEx<CtdtCmtcEntity, Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "ID")
	@Id
	@SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_CTDT_CMTC", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
	@Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
	@NotNull
	@Min(0L)
	@Max(Long.MAX_VALUE)
	private Long id;

	@ApiModelProperty(notes = "CHUNG_TU_DINH_KEM_TK_ID")
	@Basic
	@Column(name = "CHUNG_TU_DINH_KEM_TK_ID", nullable = true, columnDefinition = "VARCHAR2")
	@Min(0L)
	@Max(Long.MAX_VALUE)
	private String chungTuDinhKemTkId;

	@ApiModelProperty(notes = "Mã phân loại hình thức chứng từ")
	@Basic
	@Column(name = "MA_PL_HINH_THUC_CHUNG_TU", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 2)
	private String maPlHinhThucChungTu;

	@ApiModelProperty(notes = "Số chứng từ")
	@Basic
	@Column(name = "SO_CHUNG_TU", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 35)
	private String soChungTu;

	@ApiModelProperty(notes = "Ngày phát hành chứng từ")
	@Basic
	@Column(name = "NGAY_PHAT_HANH_CHUNG_TU", nullable = true, columnDefinition = "DATE")
	private Date ngayPhatHanhChungTu;

	@ApiModelProperty(notes = "Lĩnh vực/ngành nghề kinh doanh")
	@Basic
	@Column(name = "NGANH_NGHE_KINH_DOANH", nullable = true, columnDefinition = "VARCHAR2(255)")
	@Size(max = 255)
	private String nganhNgheKinhDoanh;

	@ApiModelProperty(notes = "Cơ sở pháp lý")
	@Basic
	@Column(name = "CO_SO_PHAP_LY", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 255)
	private String coSoPhapLy;

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
	public CtdtCmtcEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
