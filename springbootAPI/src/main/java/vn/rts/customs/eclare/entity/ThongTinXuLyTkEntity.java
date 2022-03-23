
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
@Table(name = EntityTableEtcCustomsEClare.THONG_TIN_XU_LY_TK)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ThongTinXuLyTkEntity extends ConditionalBaseEoEx<ThongTinXuLyTkEntity, Long> implements Serializable {
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
	  
    @ApiModelProperty(notes = "Kết quả xử lý")
    @Basic
    @Column(name = "KET_QUA_XU_LY", nullable = true, columnDefinition = "VARCHAR2(3)")
    @Size(max = 3)
    private String ketQuaXuLy;
	  
    @ApiModelProperty(notes = "Ngày phản hồi thông tin")
    @Basic
    @Column(name = "NGAY_PHAN_HOI", nullable = true, columnDefinition = "DATE")
    private Date ngayPhanHoi;
    
    @ApiModelProperty(notes = "Mã chức năng")
    @Basic
    @Column(name = "MA_CHUC_NANG", nullable = false, columnDefinition = "VARCHAR2(2)")
    private String maChucNang;
	  
    @ApiModelProperty(notes = "Nội dung phản hồi")
    @Basic
    @Column(name = "NOI_DUNG_PHAN_HOI", nullable = true, columnDefinition = "VARCHAR2(1000)")
    @Size(max = 1000)
    private String noiDungPhanHoi;

    @ApiModelProperty(notes = "NGAY_TAO")
    @Basic
    @Column(name = "NGAY_TAO", nullable = true, columnDefinition = "DATE")
    private Date ngayTao;
    
    @ApiModelProperty(notes = "FK")
    @Basic
    @Column(name = "MAU_TK_HQ_ID", nullable = true, columnDefinition = "VARCHAR(36)")
    private String mauTkHqId;

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
	public ThongTinXuLyTkEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
	