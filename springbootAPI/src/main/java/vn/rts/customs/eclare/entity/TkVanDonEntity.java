
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
@Table(name = EntityTableEtcCustomsEClare.TK_VAN_DON)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
public class TkVanDonEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	  
    @ApiModelProperty(notes = "ID")
    @Id
    @SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_TK_VAN_DON", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
    @Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
    @NotNull
    @Min(0L)
    @Max(Long.MAX_VALUE)
    private Long id;
	  
    @ApiModelProperty(notes = "So van don")
    @Basic
    @Column(name = "VAN_DON_SO", nullable = true, columnDefinition = "VARCHAR2(20)")
    @Size(max = 20)
    private String vanDonSo;
	  
    @ApiModelProperty(notes = "Ngay van don")
    @Basic
    @Column(name = "NGAY_VAN_DON", nullable = true, columnDefinition = "DATE")
    private String ngayVanDon;
	  
    @ApiModelProperty(notes = "Số lượng kiện")
    @Basic
    @Column(name = "SO_LUONG_KIEN", nullable = true, columnDefinition = "NUMBER")
    private int soLuongKien;
	  
    @ApiModelProperty(notes = "Số lượng kiện - đơn vị tính")
    @Basic
    @Column(name = "SO_LUONG_KIEN_DVT", nullable = true, columnDefinition = "VARCHAR2(4)")
    @Size(max = 4)
    private String soLuongKienDvt;
	  
    @ApiModelProperty(notes = "So thu tu")
    @Basic
    @Column(name = "STT", nullable = true, columnDefinition = "VARCHAR2(20)")
    @Size(max = 20)
    private String stt;
	  
    @ApiModelProperty(notes = "Nguoi tao")
    @Basic
    @Column(name = "NGUOI_TAO", nullable = true, columnDefinition = "VARCHAR2(50)")
    private String nguoiTao;
	  
    @ApiModelProperty(notes = "NGAY_TAO")
    @Basic
    @Column(name = "NGAY_TAO", nullable = true, columnDefinition = "DATE")
    private Date ngayTao;
	  
    @ApiModelProperty(notes = "Nguoi cap nhat")
    @Basic
    @Column(name = "NGUOI_SUA", nullable = true, columnDefinition = "VARCHAR2(50)")
    private String nguoiSua;
	  
    @ApiModelProperty(notes = "Ngay cap nhat")
    @Basic
    @Column(name = "NGAY_SUA", nullable = true, columnDefinition = "DATE")
    private Date ngaySua;
	  
    @ApiModelProperty(notes = "FK")
    @Basic
    @Column(name = "MAU_KB_HQ_ID", nullable = true, columnDefinition = "VARCHAR(36)")
    private String mauKbHqId;
	  
    @ApiModelProperty(notes = "Số định danh")
    @Basic
    @Column(name = "SO_DINH_DANH", nullable = true, columnDefinition = "VARCHAR2(20)")
    @Size(max = 20)
    private String soDinhDanh;
	  
    @ApiModelProperty(notes = "Tổng trọng lượng (Gross Weight)")
    @Basic
    @Column(name = "TRONG_LUONG", nullable = true, columnDefinition = "NUMBER")
    private BigDecimal trongLuong;
	  
    @ApiModelProperty(notes = "Mã đơn vị tính của tổng trọng lượng")
    @Basic
    @Column(name = "TRONG_LUONG_DVT", nullable = true, columnDefinition = "VARCHAR2(20)")
    @Size(max = 20)
    private String trongLuongDvt;

    @ApiModelProperty(notes = "mã doanh nghiệp")
    @Basic
    @Column(name = "MA_DOANH_NGHIEP", nullable = true, columnDefinition = "VARCHAR(14)")
    private String maDoanhNghiep;
}
	