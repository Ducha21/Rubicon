
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
@Table(name = EntityTableEtcCustomsEClare.KHOAN_DIEU_CHINH_TK)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
public class KhoanDieuChinhTkEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	  
    @ApiModelProperty(notes = "ID")
    @Id
    @SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_KHOAN_DIEU_CHINH_TK", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
    @Column(name = "ID", nullable = false, columnDefinition = "VARCHAR2(20)")
    @NotNull
    @Size(max = 20)
    private String id;
    
    @ApiModelProperty(notes = "DC_STT")
    @Basic
    @Column(name = "DC_STT", nullable = true, columnDefinition = "NUMBER")
	private Integer dcStt;
	  
    @ApiModelProperty(notes = "MAU_KB_HQ_ID")
    @Basic
    @Column(name = "MAU_KB_HQ_ID", nullable = true, columnDefinition = "VARCHAR(36)")
    private String mauKbHqId;
	  
    @ApiModelProperty(notes = "Mã tương ứng khoản điều chỉnh")
    @Basic
    @Column(name = "DC_TRI_GIA_MA", nullable = true, columnDefinition = "VARCHAR2(1)")
    @Size(max = 1)
    private String dcTriGiaMa;
	  
    @ApiModelProperty(notes = "Mã phân loại điều chỉnh trị giá")
    @Basic
    @Column(name = "DC_TRI_GIA_MA_PL", nullable = true, columnDefinition = "VARCHAR2(3)")
    @Size(max = 3)
    private String dcTriGiaMaPl;
	  
    @ApiModelProperty(notes = "Mã đơn vị tiền tệ của khoản điều chỉnh")
    @Basic
    @Column(name = "DC_TRI_GIA_MA_NT", nullable = true, columnDefinition = "VARCHAR2(3)")
    @Size(max = 3)
    private String dcTriGiaMaNt;
	  
    @ApiModelProperty(notes = "Trị giá khoản điều chỉnh")
    @Basic
    @Column(name = "DC_TRI_GIA", nullable = true, columnDefinition = "NUMBER")
    private BigDecimal dcTriGia;
	  
    @ApiModelProperty(notes = "Tổng hệ số phân bổ trị giá khoản điều chỉnh")
    @Basic
    @Column(name = "DC_TRI_GIA_TONG_HS_PHAN_BO", nullable = true, columnDefinition = "NUMBER")
    private BigDecimal dcTriGiaTongHsPhanBo;
	  
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
}
	