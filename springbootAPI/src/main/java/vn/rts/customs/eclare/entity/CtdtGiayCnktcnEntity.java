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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vn.rts.customs.eclare.request.ctdt.CtdtGiayCnktcnRequests;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare.EntityTableEtcCustomsEClare;
import vn.rts.customs.lib.dto.ConditionalBaseEoEx;

/**
 * @author CCS4EO_DEV
 */

@Entity
@Table(name = EntityTableEtcCustomsEClare.CTDT_GIAY_CNKTCN)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CtdtGiayCnktcnEntity extends ConditionalBaseEoEx<CtdtGiayCnktcnEntity, Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "ID")
	@Id
	@SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_CTDT_GIAY_CNKTCN", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
	@Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
	@NotNull
	@Min(0L)
	@Max(Long.MAX_VALUE)
	private Long id;

	@ApiModelProperty(notes = "so chứng từ")
	@Basic
	@Column(name = "CHUNG_TU_DINH_KEM_TK_ID", nullable = true, columnDefinition = "VARCHAR2")
	private String chungTuDinhKemTkId;

	@ApiModelProperty(notes = "Mã phân loại giấy chứng nhận kiểm tra chuyên ngành")
	@Basic
	@Column(name = "MA_PL_GIAY_CNKTCN", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 4)
	private String maPlGiayCnktcn;

	@ApiModelProperty(notes = "Loại giấy chứng nhận kiểm tra chuyên ngành")
	@Basic
	@Column(name = "LOAI_GIAY_CNKTCN", nullable = true, columnDefinition = "NUMBER")
	@Min(0)
	@Max(Integer.MAX_VALUE)
	private Integer loaiGiayCnktcn;

	@ApiModelProperty(notes = "Tên giấy chứng nhận kiểm tra chuyên ngành")
	@Basic
	@Column(name = "TEN_GIAY_CNKTCN", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 2)
	private Integer tenGiayCnktcn;

	@ApiModelProperty(notes = "Số giấy chứng nhận kiểm tra chuyên ngành")
	@Basic
	@Column(name = "SO_GIAY_CNKTCN", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 35)
	private String soGiayCnktcn;

	@ApiModelProperty(notes = "Ngày giấy chứng nhận kiểm tra chuyên ngành")
	@Basic
	@Column(name = "NGAY_GIAY_CNKTCN", nullable = true, columnDefinition = "DATE")
	private Date ngayGiayCnktcn;

	@ApiModelProperty(notes = "Ngày hết hạn giấy chứng nhận kiểm tra chuyên ngành")
	@Basic
	@Column(name = "NGAY_HET_HAN_GIAY_CNKTCN", nullable = true, columnDefinition = "DATE")
	private Date ngayHetHanGiayCnktcn;

	@ApiModelProperty(notes = "Nơi cấp giấy chứng nhận kiểm tra chuyên ngành")
	@Basic
	@Column(name = "NOI_CAP_GIAY_CNKTCN", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 255)
	private String noiCapGiayCnktcn;

	@ApiModelProperty(notes = "Người cấp giấy chứng nhận kiểm tra chuyên ngành")
	@Basic
	@Column(name = "NGUOI_CAP_GIAY_CNKTCN", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 255)
	private String nguoiCapGiayCnktcn;

	@ApiModelProperty(notes = "ghi chú khác")
	@Basic
	@Column(name = "GHI_CHU_KHAC", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 2000)
	private String ghiChuKhac;

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
	private List<CtdtGiayCnktcnRequests.CtdtThongTinHangHoaRequest> dsHang;

	@Override
	public Long getRepositoryId() {
		return this.id;
	}

	@Override
	public String getNotifyWithKey() {
		return String.format("ID : [%s]", id);
	}

	@Override
	public CtdtGiayCnktcnEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
