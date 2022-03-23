package vn.rts.customs.eclare.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.*;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare.EntityTableEtcCustomsEClare;
import vn.rts.customs.lib.dto.ConditionalBaseEoEx;

@Entity
@Table(name = EntityTableEtcCustomsEClare.CAU_HINH_HE_THONG)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CauHinhHeThongEntity extends ConditionalBaseEoEx<CauHinhHeThongEntity, Long> implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(notes = "ID")
    @Id
    @SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_CAU_HINH_HE_THONG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
    @Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
    @Min(0L)
    @Max(Long.MAX_VALUE)
    private Long id;
	
	@ApiModelProperty(notes = "Nhóm cấu hình hệ thông")
	@Basic
	@Column(name = "NHOM", nullable = true, columnDefinition = "VARCHAR2(50)")
	@Size(max = 50)
	@NotNull
	@NotEmpty
	private String nhom;
	 
	@ApiModelProperty(notes = "Key cấu hình hệ thống")
	@Basic
	@Column(name = "KEY", nullable = true, columnDefinition = "VARCHAR2(50)")
	@Size(max = 50)
	@NotNull
	@NotEmpty
	private String key;
	 
	@ApiModelProperty(notes = "Value cấu hình hệ thống")
	@Basic
	@Column(name = "VALUE", nullable = true, columnDefinition = "VARCHAR2(225)")
	@Size(max = 225)
	@NotNull
	@NotEmpty
	private String value;

	@ApiModelProperty(notes = "Được xóa")
	@Basic
	@Column(name = "IS_DELEABLE", columnDefinition = "NUMBER")
	private Integer isDeletable;

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
	public CauHinhHeThongEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
