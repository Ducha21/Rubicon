
package vn.rts.customs.eclare.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;
import vn.rts.customs.lib.dto.ConditionalBaseEoEx;
import org.apache.commons.lang3.SerializationUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * @author CCS4EO_TEST
 */

@Entity
@Table(name = "CONTAINER_INFO")
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ContainerInfoEntity extends ConditionalBaseEoEx<ContainerInfoEntity, String> implements Serializable {
	private static final long serialVersionUID = 1L;
	  
    @ApiModelProperty(notes = "ID")
    @Basic
    @Column(name = "ID", nullable = false, columnDefinition = "VARCHAR2")
    @NotNull
    @Size(max = 36)
    @Id
    private String id;
	  
    @ApiModelProperty(notes = "VAN_DON_SO")
    @Basic
    @Column(name = "VAN_DON_SO", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 35)
    private String vanDonSo;
	  
    @ApiModelProperty(notes = "SO_CONTAINER")
    @Basic
    @Column(name = "SO_CONTAINER", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 35)
    private String soContainer;
	  
    @ApiModelProperty(notes = "SO_SEAL")
    @Basic
    @Column(name = "SO_SEAL", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 35)
    private String soSeal;
	  
    @ApiModelProperty(notes = "NGUOI_TAO")
    @Basic
    @Column(name = "NGUOI_TAO", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 50)
    private String nguoiTao;
	  
    @ApiModelProperty(notes = "NGAY_TAO")
    @Basic
    @Column(name = "NGAY_TAO", nullable = true, columnDefinition = "DATE")
    private Date ngayTao;
	  
    @ApiModelProperty(notes = "NGUOI_SUA")
    @Basic
    @Column(name = "NGUOI_SUA", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 50)
    private String nguoiSua;
	  
    @ApiModelProperty(notes = "NGAY_SUA")
    @Basic
    @Column(name = "NGAY_SUA", nullable = true, columnDefinition = "DATE")
    private Date ngaySua;
	  
    @ApiModelProperty(notes = "GHI_CHU")
    @Basic
    @Column(name = "GHI_CHU", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 2000)
    private String ghiChu;

    @ApiModelProperty(notes = "ND_MA_VACH")
    @Basic
    @Column(name = "ND_MA_VACH", nullable = true, columnDefinition = "VARCHAR2(4000)")
    private String ndMaVach;
	  
    @ApiModelProperty(notes = "SO_SEAL_HQ")
    @Basic
    @Column(name = "SO_SEAL_HQ", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 35)
    private String soSealHq;
	  
    @ApiModelProperty(notes = "TK_CONTAINERS_ID")
    @Basic
    @Column(name = "TK_CONTAINERS_ID", nullable = false, columnDefinition = "VARCHAR2")
    @NotNull
    @Size(max = 36)
    private String tkContainersId;

    @ApiModelProperty(notes = "CONTAINER")
    @Basic
    @Column(name = "CONTAINER", nullable = true, columnDefinition = "VARCHAR2(1)")
    @Size(max = 1)
    private String container;

    @ApiModelProperty(notes = "SO_LUONG_HANG")
    @Basic
    @Column(name = "SO_LUONG_HANG", nullable = true, columnDefinition = "VARCHAR2(8)")
    @Size(max = 8)
    private String soLuongHang;

    @ApiModelProperty(notes = "SO_LUONG_HANG_DVT")
    @Basic
    @Column(name = "SO_LUONG_HANG_DVT", nullable = true, columnDefinition = "VARCHAR2(4)")
    @Size(max = 4)
    private String soLuongHangDvt;

    @ApiModelProperty(notes = "TRONG_LUONG_HANG")
    @Basic
    @Column(name = "TRONG_LUONG_HANG", nullable = true, columnDefinition = "VARCHAR2(10)")
    @Size(max = 10)
    private String trongLuongHang;

    @ApiModelProperty(notes = "TONG_TRONG_LUONG_DVT")
    @Basic
    @Column(name = "TONG_TRONG_LUONG_DVT", nullable = true, columnDefinition = "VARCHAR2(4)")
    @Size(max = 4)
    private String tongTrongLuongDvt;

    @ApiModelProperty(notes = "mã doanh nghiệp")
    @Basic
    @Column(name = "MA_DOANH_NGHIEP", nullable = true, columnDefinition = "VARCHAR(14)")
    private String maDoanhNghiep;
	
    
    @Override
    public String getRepositoryId() {
		return this.id;
	}

	@Override
	public String getNotifyWithKey() {
		return String.format(": []");
	}

	@Override
	public ContainerInfoEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
	