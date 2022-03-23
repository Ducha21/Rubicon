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
import javax.validation.constraints.Pattern;
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

@Entity
@Table(name = EntityTableEtcCustomsEClare.CHU_KY_SO)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ChuKySoEntity extends ConditionalBaseEoEx<ChuKySoEntity, Long> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(notes = "ID")
    @Id
    @SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_CHU_KY_SO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
    @Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
    @Min(0L)
    @Max(Long.MAX_VALUE)
    private Long id;

	@ApiModelProperty(notes = "mã doanh nghiệp")
	@Basic
	@Column(name = "MA_DOANH_NGHIEP", nullable = true, columnDefinition = "VARCHAR(14)")
	private String maDoanhNghiep;
	
	@ApiModelProperty(notes = "SO_CMT")
	@Basic
	@Column(name = "SO_CMT", nullable = true, columnDefinition = "VARCHAR2(12)")
	@Size(max = 12)
	private String soCMT;	
	
	@ApiModelProperty(notes = "TEN_CHUNG_THU_SO")
	@Basic
	@Column(name = "TEN_CHUNG_THU_SO", nullable = true, columnDefinition = "VARCHAR2(225)")
	@Size(max = 225)
	private String tenChungThuSo;
	
	@ApiModelProperty(notes = "DON_VI_XUAT_NHAP_KHAU")
	@Basic
	@Column(name = "DON_VI_XUAT_NHAP_KHAU", nullable = true, columnDefinition = "NUMBER")
	@Size(max = 14)
	@Pattern(regexp = "[0-9]*")
	private String donViXuatNhapKhau;
	
	@ApiModelProperty(notes = "NGUOI_CAP")
	@Basic
	@Column(name = "NGUOI_CAP", nullable = true, columnDefinition = "VARCHAR2(225)")
	@Size(max = 225)
	private String nguoiCap;
	
	@ApiModelProperty(notes = "NGUOI_DUOC_CAP")
	@Basic
	@Column(name = "NGUOI_DUOC_CAP", nullable = true, columnDefinition = "VARCHAR2(225)")
	@Size(max = 225)
	private String nguoiDuocCap;
	
	@ApiModelProperty(notes = "SERIAL_NUMBER")
	@Basic
	@Column(name = "SERIAL_NUMBER", nullable = true, columnDefinition = "VARCHAR2(300)")
	private String serialNumber;
	
	@ApiModelProperty(notes = "NGAY_BAT_DAU_HIEU_LUC")
	@Basic
	@Column(name = "NGAY_BAT_DAU_HIEU_LUC", nullable = true, columnDefinition = "DATE")
	private Date ngayCoHieuLuc;
	
	@ApiModelProperty(notes = "NGAY_HET_HIEU_LUC")
	@Basic
	@Column(name = "NGAY_HET_HIEU_LUC", nullable = true, columnDefinition = "DATE")
	private Date ngayHetHieuLuc;
	
	@ApiModelProperty(notes = "NGAY_HIEU_LUC_DANG_KY")
	@Basic
	@Column(name = "NGAY_HIEU_LUC_DANG_KY", nullable = true, columnDefinition = "DATE")
	private Date ngayHieuLucDangKy;
	
	@ApiModelProperty(notes = "NGAY_HET_HIEU_LUC_DANG_KY")
	@Basic
	@Column(name = "NGAY_HET_HIEU_LUC_DANG_KY", nullable = true, columnDefinition = "DATE")
	private Date ngayHetHieuLucDangKy;

	@ApiModelProperty(notes = "NGUOI_TAO")
	@Basic
	@Column(name = "NGUOI_TAO", nullable = true, columnDefinition = "VARCHAR2(50)")
	@Size(max = 100)
	private String nguoiTao;

	@ApiModelProperty(notes = "NGAY_TAO")
	@Basic
	@Column(name = "NGAY_TAO", nullable = true, columnDefinition = "DATE")
	private Date ngayTao;

	@ApiModelProperty(notes = "NGAY_SUA")
	@Basic
	@Column(name = "NGAY_SUA", nullable = true, columnDefinition = "DATE")
	private Date ngaySua;

	@ApiModelProperty(notes = "NGUOI_SUA")
	@Basic
	@Column(name = "NGUOI_SUA", nullable = true, columnDefinition = "VARCHAR2(50)")
	@Size(max = 100)
	private String nguoiSua;
	
	@Override
    public Long getRepositoryId() {
		return this.id;
	}

	@Override
	public String getNotifyWithKey() {
		return String.format("ID : [%s]", id);
	}

	@Override
	public ChuKySoEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
