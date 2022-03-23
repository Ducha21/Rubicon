
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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare.EntityTableEtcCustomsEClare;

/**
 * @author VNETC
 */

@Entity
@Table(name = EntityTableEtcCustomsEClare.SO_DINH_KEM_KBDT)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
public class SoDinhKemKbdtEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	  
    @ApiModelProperty(notes = "ID")
    @Id
    @SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_SO_DINH_KEM_KBDT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
    @Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
    @NotNull
    @Min(0L)
    @Max(Long.MAX_VALUE)
    private Long id;
	  
    @ApiModelProperty(notes = "MAU_TK_HQ_ID")
    @Basic
    @Column(name = "MAU_TK_HQ_ID", nullable = true, columnDefinition = "VARCHAR(36)")
    private String mauTkHqId;
	  
    @ApiModelProperty(notes = "Phân loại đính kém khai báo điện tử")
    @Basic
    @Column(name = "PL_DINH_KEM_DT", nullable = true, columnDefinition = "VARCHAR2(3)")
    @Size(max = 3)
    private String plDinhKemDt;
	  
    @ApiModelProperty(notes = "Số đính kém khai báo điện tử")
    @Basic
    @Column(name = "SO_DINH_KEM_DT", nullable = true, columnDefinition = "VARCHAR2(12)")
    @Size(max = 12)
    private String soDinhKemDt;

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
	  
    @ApiModelProperty(notes = "Số chứng từ")
    @Basic
    @Column(name = "SO_CHUNG_TU", nullable = true, columnDefinition = "VARCHAR2(20)")
    @Size(max = 20)
    private String soChungTu;
	  
    @ApiModelProperty(notes = "Tên chứng từ")
    @Basic
    @Column(name = "TEN_CHUNG_TU", nullable = true, columnDefinition = "VARCHAR2(200)")
    @Size(max = 200)
    private String tenChungTu;
	  
    @ApiModelProperty(notes = "Ngày chứng từ")
    @Basic
    @Column(name = "NGAY_CHUNG_TU", nullable = true, columnDefinition = "DATE")
    private Date ngayChungTu;
	  
    @ApiModelProperty(notes = "Ghi chú")
    @Basic
    @Column(name = "GHI_CHU", nullable = true, columnDefinition = "VARCHAR2(500)")
    @Size(max = 500)
    private String ghiChu;
	  
    @ApiModelProperty(notes = "Tên file chứng từ")
    @Basic
    @Column(name = "TEN_FILE_CHUNG_TU", nullable = true, columnDefinition = "VARCHAR2(512)")
    @Size(max = 512)
    private String tenFileChungTu;
	  
    @ApiModelProperty(notes = "url")
    @Basic
    @Column(name = "URL_FILE", nullable = true, columnDefinition = "VARCHAR2(512)")
    @Size(max = 512)
    private String urlFile;
	  
    @ApiModelProperty(notes = "kích thước file")
    @Basic
    @Column(name = "SIZE_FILE", nullable = true, columnDefinition = "VARCHAR2(20)")
    @Size(max = 20)
    private String sizeFile;

    @ApiModelProperty(notes = "mã doanh nghiệp")
    @Basic
    @Column(name = "MA_DOANH_NGHIEP", nullable = true, columnDefinition = "VARCHAR(14)")
    private String maDoanhNghiep;
}
	