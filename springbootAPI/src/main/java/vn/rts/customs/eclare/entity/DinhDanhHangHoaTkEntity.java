
package vn.rts.customs.eclare.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;

import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare;
import vn.rts.customs.lib.dto.ConditionalBaseEoEx;
import org.apache.commons.lang3.SerializationUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author CCS4EO_DEV
 */

@Entity
@Table(name = ConstantEtcCustomsEClare.EntityTableEtcCustomsEClare.DINH_DANH_HANG_HOA_TK)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class DinhDanhHangHoaTkEntity extends ConditionalBaseEoEx<DinhDanhHangHoaTkEntity, String> implements Serializable {
	private static final long serialVersionUID = 1L;
	  
    @ApiModelProperty(notes = "Số tham chiếu")
    @Id
    @SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_DINH_DANH_HANG_HOA_TK", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
    @Column(name = "ID", nullable = false, columnDefinition = "VARCHAR2")
    @NotNull
    @Size(max = 36)
    private String id;
	  
    @ApiModelProperty(notes = "NGAY_SUA")
    @Basic
    @Column(name = "NGAY_SUA", nullable = true, columnDefinition = "DATE")
    private Date ngaySua;
	  
    @ApiModelProperty(notes = "NGAY_TAO")
    @Basic
    @Column(name = "NGAY_TAO", nullable = true, columnDefinition = "DATE")
    private Date ngayTao;
	  
    @ApiModelProperty(notes = "NGUOI_SUA")
    @Basic
    @Column(name = "NGUOI_SUA", nullable = true, columnDefinition = "VARCHAR2(50)")
    private String nguoiSua;
	  
    @ApiModelProperty(notes = "NGUOI_TAO")
    @Basic
    @Column(name = "NGUOI_TAO", nullable = true, columnDefinition = "VARCHAR2(50)")
    private String nguoiTao;
	  
    @ApiModelProperty(notes = "SO_DINH_DANH")
    @Basic
    @Column(name = "SO_DINH_DANH", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 20)
    private String soDinhDanh;
	  
    @ApiModelProperty(notes = "SO_TIEP_NHAN")
    @Basic
    @Column(name = "SO_TIEP_NHAN", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 100)
    private String soTiepNhan;
	  
    @ApiModelProperty(notes = "NGAY_TIEP_NHAN")
    @Basic
    @Column(name = "NGAY_TIEP_NHAN", nullable = true, columnDefinition = "DATE")
    private Date ngayTiepNhan;
	  
    @ApiModelProperty(notes = "NGAY_CAP")
    @Basic
    @Column(name = "NGAY_CAP", nullable = true, columnDefinition = "DATE")
    private Date ngayCap;

    @ApiModelProperty(notes = "mã doanh nghiệp")
    @Basic
    @Column(name = "MA_DOANH_NGHIEP", nullable = true, columnDefinition = "VARCHAR(14)")
    private String maDoanhNghiep;
	  
    @ApiModelProperty(notes = "TRANG_THAI")
    @Basic
    @Column(name = "TRANG_THAI", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 3)
    private String trangThai;
	  
    @ApiModelProperty(notes = "KENH_KHAI_BAO")
    @Basic
    @Column(name = "KENH_KHAI_BAO", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 20)
    private String kenhKhaiBao;
	  
    @ApiModelProperty(notes = "NOI_KHAI_BAO")
    @Basic
    @Column(name = "NOI_KHAI_BAO", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 60)
    private String noiKhaiBao;
	  
    @ApiModelProperty(notes = "HQ_TIEP_NHAN")
    @Basic
    @Column(name = "HQ_TIEP_NHAN", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 6)
    private String hqTiepNhan;

    @ApiModelProperty(notes = "HQ_TIEP_NHAN")
    @Basic
    @Column(name = "TEN_HQ_TIEP_NHAN", nullable = true, columnDefinition = "VARCHAR2(255)")
    @Size(max = 255)
    private String tenHqTiepNhan;
	  
    @ApiModelProperty(notes = "MA_NGUOI_KHAI_HQ")
    @Basic
    @Column(name = "MA_NGUOI_KHAI_HQ", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 20)
    private String maNguoiKhaiHq;
	  
    @ApiModelProperty(notes = "TEN_NGUOI_KHAI_HQ")
    @Basic
    @Column(name = "TEN_NGUOI_KHAI_HQ", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 200)
    private String tenNguoiKhaiHq;
	  
    @ApiModelProperty(notes = "TEN_DOANH_NGHIEP")
    @Basic
    @Column(name = "TEN_DOANH_NGHIEP", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 255)
    private String tenDoanhNghiep;
	  
    @ApiModelProperty(notes = "DIA_CHI_DOANH_NGHIEP")
    @Basic
    @Column(name = "DIA_CHI_DOANH_NGHIEP", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 255)
    private String diaChiDoanhNghiep;
	  
    @ApiModelProperty(notes = "LOAI_DOI_TUONG")
    @Basic
    @Column(name = "LOAI_DOI_TUONG", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 3)
    private String loaiDoiTuong;
	  
    @ApiModelProperty(notes = "LOAI_HANG_HOA")
    @Basic
    @Column(name = "LOAI_HANG_HOA", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 2)
    private String loaiHangHoa;
	  
    @ApiModelProperty(notes = "DOANH_NGHIEP_XNK")
    @Basic
    @Column(name = "DOANH_NGHIEP_XNK", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 17)
    private String doanhNghiepXnk;

    @ApiModelProperty(notes = "MA_VACH")
    @Basic
    @Column(name = "MA_VACH", nullable = true, columnDefinition = "VARCHAR2(4000)")
    @Size(max = 4000)
    private String maVach;

    @ApiModelProperty(notes = "NOI_DUNG_PHAN_HOI")
    @Basic
    @Column(name = "NOI_DUNG_PHAN_HOI", nullable = true, columnDefinition = "VARCHAR2(4000)")
    @Size(max = 4000)
    private String noiDungPhanHoi;

    
    @Override
    public String getRepositoryId() {
		return this.id;
	}

	@Override
	public String getNotifyWithKey() {
		return String.format("Số tham chiếu : [%s]", id);
	}

	@Override
	public DinhDanhHangHoaTkEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
	