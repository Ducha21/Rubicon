
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
 * @author VNETC
 */

@Entity
@Table(name = EntityTableEtcCustomsEClare.CHI_THI_HQ_TK)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ChiThiHqTkEntity extends ConditionalBaseEoEx<ChiThiHqTkEntity, Long> implements Serializable {
	private static final long serialVersionUID = 1L;
	  
    @ApiModelProperty(notes = "ID")
    @Id
    @SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_CHI_THI_HQ_TK", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
    @Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
    @NotNull
    @Min(0L)
    @Max(Long.MAX_VALUE)
    private Long id;
	  
    @ApiModelProperty(notes = "Phân loại chỉ thị của Hải quan")
    @Basic
    @Column(name = "PL_CHI_THI_HQ", nullable = false, columnDefinition = "CHAR(2)")
    @Size(max = 2)
    private String plChiThiHq;
	  
    @ApiModelProperty(notes = "Ngày chỉ thị của Hải quan")
    @Basic
    @Column(name = "NGAY_CHI_THI_HQ", nullable = true, columnDefinition = "DATE")
    private Date ngayChiThiHq;
    
    @ApiModelProperty(notes = "Mã chức năng")
    @Basic
    @Column(name = "MA_CHUC_NANG", nullable = false, columnDefinition = "CHAR(2)")
    private Integer maChucNang;
	  
    @ApiModelProperty(notes = "Tên chỉ thị của Hải quan")
    @Basic
    @Column(name = "TEN_CHI_THI_HQ", nullable = true, columnDefinition = "VARCHAR2(200)")
    @Size(max = 200)
    private String tenChiThiHq;
	  
    @ApiModelProperty(notes = "Nội dung chỉ thị của Hải quan")
    @Basic
    @Column(name = "NOI_DUNG_CHI_THI_HQ", nullable = true, columnDefinition = "VARCHAR2(1000)")
    @Size(max = 1000)
    private String noiDungChiThiHq;
	  
    @ApiModelProperty(notes = "NGUOI_TAO")
    @Basic
    @Column(name = "NGUOI_TAO", nullable = true, columnDefinition = "VARCHAR(50)")
    private String nguoiTao;
	  
    @ApiModelProperty(notes = "NGAY_TAO")
    @Basic
    @Column(name = "NGAY_TAO", nullable = true, columnDefinition = "DATE")
    private Date ngayTao;
	  
    @ApiModelProperty(notes = "NGUOI_SUA")
    @Basic
    @Column(name = "NGUOI_SUA", nullable = true, columnDefinition = "VARCHAR(50)")
    private String nguoiSua;
	  
    @ApiModelProperty(notes = "NGAY_SUA")
    @Basic
    @Column(name = "NGAY_SUA", nullable = true, columnDefinition = "DATE")
    private Date ngaySua;
	  
    @ApiModelProperty(notes = "FK")
    @Basic
    @Column(name = "MAU_TK_HQ_ID", nullable = true, columnDefinition = "VARCHAR(36)")
    private String mauTkHqId;

    @ApiModelProperty(notes = "SO_TIEP_NHAN")
    @Basic
    @Column(name = "SO_TIEP_NHAN", nullable = true, columnDefinition = "VARCHAR2(12)")
    private String soTiepNhan;

    @ApiModelProperty(notes = "SO_TK")
    @Basic
    @Column(name = "SO_TK", nullable = true, columnDefinition = "VARCHAR2(12)")
    private String soTk;

    @ApiModelProperty(notes = "LUONG_TO_KHAI")
    @Basic
    @Column(name = "LUONG_TO_KHAI", nullable = true, columnDefinition = "VARCHAR2(1)")
    private String luongToKhai;

    @ApiModelProperty(notes = "MA_THONG_DIEP")
    @Basic
    @Column(name = "MA_THONG_DIEP", nullable = true, columnDefinition = "VARCHAR2(255)")
    private String maThongDiep;

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
	public ChiThiHqTkEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
	