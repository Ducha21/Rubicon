
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
@Table(name = EntityTableEtcCustomsEClare.DIA_DIEM_XEP_HANG_TK) //vanning
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class DiaDiemXepHangTkEntity extends ConditionalBaseEoEx<DiaDiemXepHangTkEntity, Long> implements Serializable {
	private static final long serialVersionUID = 1L;
	  
    @ApiModelProperty(notes = "ID")
    @Id
    @SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_THONG_TIN_XU_LY_TK", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
    @Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
    @NotNull
    @Min(0L)
    @Max(Long.MAX_VALUE)
    private Long id;

	@ApiModelProperty(notes = "NGAY_TAO")
	@Basic
	@Column(name = "NGAY_TAO", nullable = true, columnDefinition = "DATE")
	private Date ngayTao;
    
    @ApiModelProperty(notes = "FK")
    @Basic
    @Column(name = "MAU_TK_HQ_ID", nullable = true, columnDefinition = "VARCHAR(36)")
    private String mauTkHqId;
    
    @ApiModelProperty(notes = "2.59. Đia điểm xếp hàng lên xe chở hàng (Vanning), mã")
	@Basic
	@Column(name = "MA_DIA_DIEM_XEP_HANG", nullable = true, columnDefinition = "VARCHAR(7)")
	private String maDiaDiemXepHang;

	@ApiModelProperty(notes = "2.59. Đia điểm xếp hàng lên xe chở hàng (Vanning), tên")
	@Basic
	@Column(name = "TEN_DIA_DIEM_XEP_HANG", nullable = true, columnDefinition = "VARCHAR(70)")
	private String tenDiaDiemXepHang;

	@ApiModelProperty(notes = "2.59. Đia điểm xếp hàng lên xe chở hàng (Vanning), địa chỉ")
	@Basic
	@Column(name = "DIA_CHI_DIA_DIEM_XEP_HANG", nullable = true, columnDefinition = "VARCHAR(100)")
	private String diaChiDiaDiemXepHang;

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
	public DiaDiemXepHangTkEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
	