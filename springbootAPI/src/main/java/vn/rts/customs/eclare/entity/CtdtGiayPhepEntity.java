package vn.rts.customs.eclare.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import vn.rts.customs.eclare.request.ctdt.CtdtGiayPhepRequests;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare.EntityTableEtcCustomsEClare;
import vn.rts.customs.lib.dto.ConditionalBaseEoEx;

@Entity
@Table(name = EntityTableEtcCustomsEClare.CTDT_GIAY_PHEP)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CtdtGiayPhepEntity extends ConditionalBaseEoEx<CtdtGiayPhepEntity, Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "ID")
	@Id
	@SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_CTDT_GIAY_PHEP", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
	@Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
	@NotNull
	@Min(0L)
	@Max(Long.MAX_VALUE)
	private Long id;

	@ApiModelProperty(notes = "Mã phân loại giấy phép")
	@Basic
	@Column(name = "MA_PL_GIAY_PHEP", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 4)
	private String maPlGiayPhep;

	@ApiModelProperty(notes = "CHUNG TU DINH KEM TK ID")
	@Basic
	@Column(name = "CHUNG_TU_DINH_KEM_TK_ID", nullable = true, columnDefinition = "VARCHAR2(36)")
	private String chungTuDinhKemTkId;

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

	@ApiModelProperty(notes = "Số giấy phép")
	@Basic
	@Column(name = "SO_GIAY_PHEP", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 35)
	private String soGiayPhep;

	@ApiModelProperty(notes = "Loại giấy phép")
	@Basic
	@Column(name = "LOAI_GIAY_PHEP", nullable = true, columnDefinition = "NUMBER")
	private String loaiGiayPhep;

	@ApiModelProperty(notes = "Ngày cấp giấy phép")
	@Basic
	@Column(name = "NGAY_CAP_GIAY_PHEP", nullable = true, columnDefinition = "DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngayCapGiayPhep;

	@ApiModelProperty(notes = "Ngày hết hạn giấy phép")
	@Basic
	@Column(name = "NGAY_HET_HAN_GIAY_PHEP", nullable = true, columnDefinition = "DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngayHetHanGiayPhep;

	@ApiModelProperty(notes = "Nơi cấp giấy phép")
	@Basic
	@Column(name = "NOI_CAP_GIAY_PHEP", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 255)
	private String noiCapGiayPhep;

	@ApiModelProperty(notes = "Người cấp giấy phép")
	@Basic
	@Column(name = "NGUOI_CAP_GIAY_PHEP", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 255)
	private String nguoiCapGiayPhep;

	@ApiModelProperty(notes = "Ghi chú khác")
	@Basic
	@Column(name = "GHI_CHU_KHAC", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 2000)
	private String ghiChuKhac;

	@ApiModelProperty(notes = "HANG_HOA_ID")
	@Basic
	@Column(name = "HANG_HOA_ID", nullable = true, columnDefinition = "VARCHAR2(200)")
	@Size(max = 2000)
	private String hangHoaId;

	@ApiModelProperty(notes = "mã doanh nghiệp")
	@Basic
	@Column(name = "MA_DOANH_NGHIEP", nullable = true, columnDefinition = "VARCHAR(14)")
	private String maDoanhNghiep;

	@Transient
	@ApiParam(value = "Danh sach hang")
	private List<CtdtGiayPhepRequests.CtdtThongTinHangHoaRequest> dsHang;

	@Override
	public Long getRepositoryId() {
		return this.id;
	}

	@Override
	public String getNotifyWithKey() {
		return String.format("ID : [%s]", id);
	}

	@Override
	public CtdtGiayPhepEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}