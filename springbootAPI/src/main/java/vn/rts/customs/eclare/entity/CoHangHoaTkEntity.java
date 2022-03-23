
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
@Table(name = EntityTableEtcCustomsEClare.CO_HANG_HOA_TK)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CoHangHoaTkEntity extends ConditionalBaseEoEx<CoHangHoaTkEntity, Long> implements Serializable {
	private static final long serialVersionUID = 1L;
	  
    @ApiModelProperty(notes = "ID")
    @Id
    @SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_CO_HANG_HOA_TK", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
    @Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
    @NotNull
    @Min(0L)
    @Max(Long.MAX_VALUE)
    private Long id;
    
    @ApiModelProperty(notes = "DM_HANG_HOA_TK_ID")
    @Basic
    @Column(name = "DM_HANG_HOA_TK_ID", nullable = false, columnDefinition = "NUMBER")
    @NotNull
    @Min(0L)
    @Max(Long.MAX_VALUE)
    private Long dmHangHoaTkId;
	  
    @ApiModelProperty(notes = "CO_NGAY")
    @Basic
    @Column(name = "CO_NGAY", nullable = true, columnDefinition = "DATE")
    private String coNgay;
	  
    @ApiModelProperty(notes = "CO_NGUOI_CAP")
    @Basic
    @Column(name = "CO_NGUOI_CAP", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 50)
    private String coNguoiCap;
	  
    @ApiModelProperty(notes = "CO_NOICAP")
    @Basic
    @Column(name = "CO_NOICAP", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 255)
    private String coNoicap;
	  
    @ApiModelProperty(notes = "CO_SO")
    @Basic
    @Column(name = "CO_SO", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 50)
    private String coSo;
	  
    @ApiModelProperty(notes = "CO_NGAY_HET_HAN")
    @Basic
    @Column(name = "CO_NGAY_HET_HAN", nullable = true, columnDefinition = "DATE")
    private String coNgayHetHan;

    @ApiModelProperty(notes = "NGUOI_TAO")
    @Basic
    @Column(name = "NGUOI_TAO", nullable = true, columnDefinition = "VARCHAR2(50)")
    private String nguoiTao;

    @ApiModelProperty(notes = "NGAY_TAO")
    @Basic
    @Column(name = "NGAY_TAO", nullable = true, columnDefinition = "DATE")
    private Date ngayTao;

    @ApiModelProperty(notes = "NGAY_SUA")
    @Basic
    @Column(name = "NGAY_SUA", nullable = true, columnDefinition = "DATE")
    private Date ngaySua;

    @ApiModelProperty(notes = "NGUOI_SUA")
    @Basic
    @Column(name = "NGUOI_SUA", nullable = true, columnDefinition = "VARCHAR2(50)")
    private String nguoiSua;

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
	public CoHangHoaTkEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
	