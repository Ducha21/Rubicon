
package vn.rts.customs.eclare.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = EntityTableEtcCustomsEClare.THUE_KHOAN_THU_KHAC)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ThueKhoanThuKhacEntity extends ConditionalBaseEoEx<ThueKhoanThuKhacEntity, Long> implements Serializable {
	private static final long serialVersionUID = 1L;
	  
    @ApiModelProperty(notes = "ID")
    @Id
    @SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_THUE_KHOAN_THU_KHAC", allocationSize = 1)
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
	  
    @ApiModelProperty(notes = "Số thứ tự")
    @Basic
    @Column(name = "STT", nullable = true, columnDefinition = "NUMBER")
    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Integer stt;
	  
    @ApiModelProperty(notes = "Mã áp dụng thuế suất / Mức thuế và thu khác")
    @Basic
    @Column(name = "THUE_MST", nullable = true, columnDefinition = "VARCHAR2(10)")
    @Size(max = 10)
    private String thueMst;
	  
    @ApiModelProperty(notes = "Mã miễn / Giảm / Không chịu thuế và thu khác")
    @Basic
    @Column(name = "MA_MIEN_THUE", nullable = true, columnDefinition = "VARCHAR2(5)")
    @Size(max = 5)
    private String maMienThue;
	  
    @ApiModelProperty(notes = "Số tiền giảm thuế và thu khác")
    @Basic
    @Column(name = "SO_TIEN_GIAM_THUE", nullable = true, columnDefinition = "NUMBER")
    private BigDecimal soTienGiamThue;

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
	public ThueKhoanThuKhacEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
	