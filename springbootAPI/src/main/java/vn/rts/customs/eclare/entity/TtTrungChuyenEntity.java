
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
@Table(name = EntityTableEtcCustomsEClare.TT_TRUNG_CHUYEN)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
public class TtTrungChuyenEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	  
    @ApiModelProperty(notes = "ID")
    @Id
    @SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_TT_TRUNG_CHUYEN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
    @Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
    @NotNull
    @Min(0L)
    @Max(Long.MAX_VALUE)
    private Long id;
	  
    @ApiModelProperty(notes = "MAU_KB_HQ_ID")
    @Basic
    @Column(name = "MAU_KB_HQ_ID", nullable = true, columnDefinition = "VARCHAR(36)")
    private String mauKbHqId;
	  
    @ApiModelProperty(notes = "Địa điểm trung chuyển cho vận chuyển bảo thuế (khai báo gộp)")
    @Basic
    @Column(name = "DIA_DIEM_TRUNG_CHUYEN_BCBT", nullable = true, columnDefinition = "VARCHAR2(100)")
    @Size(max = 3)
    private String diaDiemTrungChuyenBcbt;
	  
    @ApiModelProperty(notes = "Ngày đến địa điểm trung chuyển")
    @Basic
    @Column(name = "NGAY_DEN", nullable = true, columnDefinition = "DATE")
    private Date ngayDen;
	  
    @ApiModelProperty(notes = "Ngày rời địa điểm trung chuyển")
    @Basic
    @Column(name = "NGAY_KHOI_HANH", nullable = true, columnDefinition = "DATE")
    private Date ngayKhoiHanh;

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
	