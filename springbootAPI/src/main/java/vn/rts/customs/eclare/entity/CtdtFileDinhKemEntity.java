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
import lombok.experimental.Accessors;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare.EntityTableEtcCustomsEClare;
import vn.rts.customs.lib.dto.ConditionalBaseEoEx;

/**
 * @author CCS4EO_DEV
 */

@Entity
@Table(name = EntityTableEtcCustomsEClare.CTDT_FILE_DINH_KEM)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CtdtFileDinhKemEntity extends ConditionalBaseEoEx<CtdtFileDinhKemEntity, Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "ID")
	@Id
	@SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_CTDT_FILE_DINH_KEM", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
	@Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
	@NotNull
	@Min(0L)
	@Max(Long.MAX_VALUE)
	private Long id;

	@ApiModelProperty(notes = "CHUNG_TU_DINH_KEM_TK_ID")
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

	@ApiModelProperty(notes = "PHAN_LOAI_DINH_KEM_DT")
	@Basic
	@Column(name = "PHAN_LOAI_DINH_KEM_DT", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 4)
	private String phanLoaiDinhKemDt;

	@ApiModelProperty(notes = "SIZE_FILE")
	@Basic
	@Column(name = "SIZE_FILE", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 20)
	private String sizeFile;

	@ApiModelProperty(notes = "TEN_FILE_CHUNG_TU")
	@Basic
	@Column(name = "TEN_FILE_CHUNG_TU", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 512)
	private String tenFileChungTu;

	@ApiModelProperty(notes = "LOAI_CHUNG_TU_ID")
	@Basic
	@Column(name = "LOAI_CHUNG_TU_ID", nullable = true, columnDefinition = "NUMBER")
	@Min(0L)
	@Max(Long.MAX_VALUE)
	private Long loaiChungTuId;

	@ApiModelProperty(notes = "FILE_ID")
	@Basic
	@Column(name = "FILE_ID", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 256)
	private String fileId;

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
	public CtdtFileDinhKemEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
