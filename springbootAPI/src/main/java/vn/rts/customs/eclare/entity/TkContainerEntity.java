
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
 * @author CCS4EO_TEST
 */

@Entity
@Table(name = ConstantEtcCustomsEClare.EntityTableEtcCustomsEClare.TK_CONTAINER)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class TkContainerEntity extends ConditionalBaseEoEx<TkContainerEntity, String> implements Serializable {
	private static final long serialVersionUID = 1L;
	  
    @ApiModelProperty(notes = "ID")
    @Basic
    @Column(name = "ID", nullable = false, columnDefinition = "VARCHAR2")
    @NotNull
    @Id
    @Size(max = 36)
    private String id;
	  
    @ApiModelProperty(notes = "LOAI_CHUNG_TU")
    @Basic
    @Column(name = "LOAI_CHUNG_TU", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 4)
    private String loaiChungTu;
	  
    @ApiModelProperty(notes = "KENH_KHAI_BAO")
    @Basic
    @Column(name = "KENH_KHAI_BAO", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 1)
    private String kenhKhaiBao;
	  
    @ApiModelProperty(notes = "NGAY_TRA_LOI")
    @Basic
    @Column(name = "NGAY_TRA_LOI", nullable = true, columnDefinition = "DATE")
    private Date ngayTraLoi;
	  
    @ApiModelProperty(notes = "NGAY_TAO")
    @Basic
    @Column(name = "NGAY_TAO", nullable = true, columnDefinition = "DATE")
    private Date ngayTao;
	  
    @ApiModelProperty(notes = "NGUOI_SUA")
    @Basic
    @Column(name = "NGUOI_SUA", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 50)
    private String nguoiSua;

    @ApiModelProperty(notes = "NGUOI_TAO")
    @Basic
    @Column(name = "NGUOI_TAO", nullable = true, columnDefinition = "VARCHAR2(50)")
    @Size(max = 50)
    private String nguoiTao;
	  
    @ApiModelProperty(notes = "NGAY_SUA")
    @Basic
    @Column(name = "NGAY_SUA", nullable = true, columnDefinition = "DATE")
    private Date ngaySua;
	  
    @ApiModelProperty(notes = "NOI_KHAI_BAO")
    @Basic
    @Column(name = "NOI_KHAI_BAO", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 60)
    private String noiKhaiBao;
	  
    @ApiModelProperty(notes = "MA_CHUC_NANG")
    @Basic
    @Column(name = "MA_CHUC_NANG", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 3)
    private String maChucNang;
	  
    @ApiModelProperty(notes = "SO_TIEP_NHAN")
    @Basic
    @Column(name = "SO_TIEP_NHAN", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 38)
    private String soTiepNhan;
	  
    @ApiModelProperty(notes = "NGAY_DANG_KY")
    @Basic
    @Column(name = "NGAY_DANG_KY", nullable = true, columnDefinition = "DATE")
    private Date ngayDangKy;
	  
    @ApiModelProperty(notes = "MAU_KB_HQ_ID")
    @Basic
    @Column(name = "MAU_KB_HQ_ID", nullable = false, columnDefinition = "VARCHAR2")
    @NotNull
    @Size(max = 36)
    private String mauKbHqId;
	  
    @ApiModelProperty(notes = "HAI_QUAN_TIEP_NHAN")
    @Basic
    @Column(name = "HAI_QUAN_TIEP_NHAN", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 6)
    private String haiQuanTiepNhan;
	  
    @ApiModelProperty(notes = "MA_NGUOI_KHAI_HQ")
    @Basic
    @Column(name = "MA_NGUOI_KHAI_HQ", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 17)
    private String maNguoiKhaiHq;
	  
    @ApiModelProperty(notes = "TEN_NGUOI_KHAI_HQ")
    @Basic
    @Column(name = "TEN_NGUOI_KHAI_HQ", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 255)
    private String tenNguoiKhaiHq;
	  
    @ApiModelProperty(notes = "TEN_DOANH_NGHIEP")
    @Basic
    @Column(name = "TEN_DOANH_NGHIEP", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 255)
    private String tenDoanhNghiep;

    @ApiModelProperty(notes = "mã doanh nghiệp")
    @Basic
    @Column(name = "MA_DOANH_NGHIEP", nullable = true, columnDefinition = "VARCHAR(14)")
    private String maDoanhNghiep;
	  
    @ApiModelProperty(notes = "DIA_CHI_DOANH_NGHIEP")
    @Basic
    @Column(name = "DIA_CHI_DOANH_NGHIEP", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 255)
    private String diaChiDoanhNghiep;
	  
    @ApiModelProperty(notes = "SO_TK")
    @Basic
    @Column(name = "SO_TK", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 12)
    private String soTk;
	  
    @ApiModelProperty(notes = "MA_LOAI_HINH")
    @Basic
    @Column(name = "MA_LOAI_HINH", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 10)
    private String maLoaiHinh;
	  
    @ApiModelProperty(notes = "MA_HAI_QUAN_GIAM_SAT")
    @Basic
    @Column(name = "MA_HAI_QUAN_GIAM_SAT", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 6)
    private String maHaiQuanGiamSat;
	  
    @ApiModelProperty(notes = "THOI_GIAN_KX_DU_LIEU")
    @Basic
    @Column(name = "THOI_GIAN_KX_DU_LIEU", nullable = true, columnDefinition = "DATE")
    private Date thoiGianKxDuLieu;
	  
    @ApiModelProperty(notes = "LUONG_TO_KHAI")
    @Basic
    @Column(name = "LUONG_TO_KHAI", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 1)
    private String luongToKhai;
	  
    @ApiModelProperty(notes = "TRANG_THAI_TO_KHAI")
    @Basic
    @Column(name = "TRANG_THAI_TO_KHAI", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 4)
    private String trangThaiToKhai;
	  
    @ApiModelProperty(notes = "TT_NIEM_PHONG_HANG")
    @Basic
    @Column(name = "TT_NIEM_PHONG_HANG", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 255)
    private String ttNiemPhongHang;
	  
    @ApiModelProperty(notes = "SO_DINH_DANH_HH")
    @Basic
    @Column(name = "SO_DINH_DANH_HH", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 255)
    private String soDinhDanhHh;
	  
    @ApiModelProperty(notes = "MA_PT_VAN_CHUYEN")
    @Basic
    @Column(name = "MA_PT_VAN_CHUYEN", nullable = true, columnDefinition = "VARCHAR2")
    @Size(max = 1)
    private String maPtVanChuyen;
	  
    @ApiModelProperty(notes = "NGAY_TAU_DU_KIEN")
    @Basic
    @Column(name = "NGAY_TAU_DU_KIEN", nullable = true, columnDefinition = "DATE")
    private Date ngayTauDuKien;
	  
    @ApiModelProperty(notes = "NGAY_TIEP_NHAN")
    @Basic
    @Column(name = "NGAY_TIEP_NHAN", nullable = true, columnDefinition = "DATE")
    private Date ngayTiepNhan;

    @ApiModelProperty(notes = "ACTIVE")
    @Basic
    @Column(name = "ACTIVE", nullable = true, columnDefinition = "NUMBER")
    private Integer active;

    @ApiModelProperty(notes = "NOI_DUNG_PHAN_HOI")
    @Basic
    @Column(name = "NOI_DUNG_PHAN_HOI", nullable = true, columnDefinition = "VARCHAR2(2000)")
    private String noiDungPhanHoi;

    @ApiModelProperty(notes = "GHI_CHU")
    @Basic
    @Column(name = "GHI_CHU", nullable = true, columnDefinition = "VARCHAR2(2000)")
    private String ghiChu;
    
    @Override
    public String getRepositoryId() {
		return this.id;
	}

	@Override
	public String getNotifyWithKey() {
		return String.format(": []");
	}

	@Override
	public TkContainerEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
	