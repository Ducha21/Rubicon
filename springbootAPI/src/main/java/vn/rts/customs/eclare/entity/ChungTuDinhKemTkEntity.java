
package vn.rts.customs.eclare.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

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

/**
 * @author CCS4EO_DEV
 */

@Entity
@Table(name = EntityTableEtcCustomsEClare.CHUNG_TU_DINH_KEM_TK)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ChungTuDinhKemTkEntity extends ConditionalBaseEoEx<ChungTuDinhKemTkEntity, String> implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "ID")
	@Id
	@Size(max = 36)
	private String id;   //Id là số chứng từ tham chiếu

	@ApiModelProperty(notes = "so to khai")
	@Basic
	@Column(name = "MAU_TK_HQ_ID", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 36)
	private String mauTkHqId;

	@ApiModelProperty(notes = "Loại chứng từ")
	@Basic
	@Column(name = "LOAI_CHUNG_TU", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 10)
	private String loaiChungTu;

	@ApiModelProperty(notes = "Kênh khai báo (DN tự khai hoặc qua đại lý)")
	@Basic
	@Column(name = "KENH_KHAI_BAO", nullable = true, columnDefinition = "CHAR")
	@Size(max = 1)
	private String kenhKhaiBao;

	@ApiModelProperty(notes = "Chức năng của chứng từ")
	@Basic
	@Column(name = "CHUC_NANG_CHUNG_TU", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 2)
	private String chucNangChungTu;

	@ApiModelProperty(notes = "Nơi khai báo")
	@Basic
	@Column(name = "NOI_KHAI_BAO", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 60)
	private String noiKhaiBao;

	@ApiModelProperty(notes = "Ngay hq phan hoi")
	@Basic
	@Column(name = "NGAY_HQ_PHAN_HOI", nullable = true, columnDefinition = "DATE")
	private String ngayHqPhanHoi;

	@ApiModelProperty(notes = "NGUOI_TAO")
	@Basic
	@Column(name = "NGUOI_TAO", nullable = true, columnDefinition = "DATE")
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
	private String nguoiSua;

	@ApiModelProperty(notes = "NGAY_DANG_KY_CT")
	@Basic
	@Column(name = "NGAY_DANG_KY_CT", nullable = true, columnDefinition = "DATE")
	private String ngayDangKyCt;

	@ApiModelProperty(notes = "HQ_TIEP_NHAN_CHUNG_TU")
	@Basic
	@Column(name = "HQ_TIEP_NHAN_CHUNG_TU", nullable = true, columnDefinition = "VARCHAR2")
	@Size(max = 6)
	private String hqTiepNhanChungTu;

	@ApiModelProperty(notes = "HQ_TRA_LOI_CT")
	@Basic
	@Column(name = "HQ_TRA_LOI_CT", nullable = true, columnDefinition = "VARCHAR2(30)")
	private String hqTraLoiCt;

	@ApiModelProperty(notes = "SO_TN")
	@Basic
	@Column(name = "SO_TN", nullable = true, columnDefinition = "VARCHAR2(50)")
	@Size(max = 50)
	private String soTn;

	@ApiModelProperty(notes = "NOI_DUNG_HQ_PHAN_HOI")
	@Basic
	@Column(name = "NOI_DUNG_HQ_PHAN_HOI", nullable = true, columnDefinition = "VARCHAR2")
	private String noiDungHqPhanHoi;

	@ApiModelProperty(notes = "TRANG_THAI")
	@Basic
	@Column(name = "TRANG_THAI", nullable = true, columnDefinition = "VARCHAR2")
	private String trangThai;

	@ApiModelProperty(notes = "mã doanh nghiệp")
	@Basic
	@Column(name = "MA_DOANH_NGHIEP", nullable = true, columnDefinition = "VARCHAR(14)")
	private String maDoanhNghiep;

	@Transient
	private CtdtFileDinhKemEntity ctdtFileDinhKem;

	@Override
	public String getRepositoryId() {
		return this.id;
	}

	@Override
	public String getNotifyWithKey() {
		return String.format("ID : [%s]", id);
	}

	@Override
	public ChungTuDinhKemTkEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
