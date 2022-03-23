
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
@Table(name = EntityTableEtcCustomsEClare.MAU_TO_KHAI_HQ_TEMPLATE)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class MauToKhaiHqTemplateEntity extends ConditionalBaseEoEx<MauToKhaiHqTemplateEntity, Long> implements Serializable {
	private static final long serialVersionUID = 1L;
	  
    @ApiModelProperty(notes = "ID")
    @Id
    @SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_MAU_TO_KHAI_HQ_TEMPLATE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
    @Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
    @NotNull
    @Min(0L)
    @Max(Long.MAX_VALUE)
    private Long id;
	  
    @ApiModelProperty(notes = "NAME")
    @Basic
    @Column(name = "NAME", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 100)
    private String name;
	  
    @ApiModelProperty(notes = "DOANH_NGHIEP_ID")
    @Basic
    @Column(name = "DOANH_NGHIEP_ID", nullable = true, columnDefinition = "VARCHAR2(200)")
    private String doanhNghiepId;
	  
    @ApiModelProperty(notes = "CONTENT")
    @Basic
    @Column(name = "CONTENT", nullable = true, columnDefinition = "CLOB")
    private String content;
	  
    @ApiModelProperty(notes = "LOAI_TK_HQ")
    @Basic
    @Column(name = "LOAI_TK_HQ", nullable = true, columnDefinition = "CHAR")
    @Size(max = 3)
    private String loaiTkHq;
    
    @ApiModelProperty(notes = "NGAY_TAO")
	@Basic
	@Column(name = "NGAY_TAO", nullable = true, columnDefinition = "DATE")
	private Date ngayTao;

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
	public MauToKhaiHqTemplateEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
	