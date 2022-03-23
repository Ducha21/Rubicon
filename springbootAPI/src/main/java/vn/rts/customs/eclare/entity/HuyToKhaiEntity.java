
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

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare.EntityTableEtcCustomsEClare;
import vn.rts.customs.lib.dto.ConditionalBaseEoEx;

/**
 * @author VNETC
 */

@Entity
@Table(name = EntityTableEtcCustomsEClare.HUY_TO_KHAI)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class HuyToKhaiEntity extends ConditionalBaseEoEx<HuyToKhaiEntity, Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "ID")
	@Id
    @SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "SEQ_HUY_TO_KHAI", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")
	@Column(name = "ID", nullable = false, columnDefinition = "NUMBER")
	@NotNull
	@Min(0L)
	@Max(Long.MAX_VALUE)
	private Long id;

	@ApiModelProperty(notes = "MAU_TK_HQ_ID")
	@Basic
	@Column(name = "MAU_TK_HQ_ID", nullable = false, columnDefinition = "VARCHAR(36)")
	@NotNull
	private String mauTkHqId;

	@ApiModelProperty(notes = "Ngày khai báo xin hủy")
	@Basic
	@Column(name = "NGAY_KB_HUY", nullable = false, columnDefinition = "DATE")
	private String ngayKbHuy;

	@ApiModelProperty(notes = "Nơi khai báo xin huỷ")
	@Basic
	@Column(name = "NOI_KB_HUY", nullable = false, columnDefinition = "VARCHAR2(60)")
	@NotNull
	@Size(max = 60)
	private String noiKbHuy;

	@ApiModelProperty(notes = "Mã đơn vị hải quan tiếp nhận chứng từ")
	@Basic
	@Column(name = "DON_VI_HQ_TIEP_NHAN", nullable = true, columnDefinition = "VARCHAR2(6)")
	@Size(max = 6)
	private String donViHqTiepNhan;

	@ApiModelProperty(notes = "Kênh khai báo")
	@Basic
	@Column(name = "KENH_KHAI_BAO", nullable = true, columnDefinition = "CHAR(1)")
	@Size(max = 1)
	private String kenhKhaiBao;

	@ApiModelProperty(notes = "Mã người khai Hải quan")
	@Basic
	@Column(name = "MA_NGUOI_KHAI_HQ", nullable = true, columnDefinition = "VARCHAR2(17)")
	@Size(max = 17)
	private String maNguoiKhaiHq;

	@ApiModelProperty(notes = "Tên người khai Hải quan")
	@Basic
	@Column(name = "TEN_NGUOI_KHAI_HQ", nullable = true, columnDefinition = "VARCHAR2(200)")
	@Size(max = 200)
	private String tenNguoiKhaiHq;

	@ApiModelProperty(notes = "Số điện thoại (người khai Hải quan; )")
	@Basic
	@Column(name = "SDT_NGUOI_KHAI_HQ", nullable = true, columnDefinition = "VARCHAR2(20)")
	@Size(max = 20)
	private String sdtNguoiKhaiHq;

	@ApiModelProperty(notes = "Địa chỉ người khai")
	@Basic
	@Column(name = "DIA_CHI_NGUOI_KHAI_HQ", nullable = true, columnDefinition = "VARCHAR2(200)")
	@Size(max = 200)
	private String diaChiNguoiKhaiHq;

	@ApiModelProperty(notes = "Số tờ khai huỷ")
	@Basic
	@Column(name = "SO_TK", nullable = true, columnDefinition = "VARCHAR2(20)")
	@Size(max = 20)
	private String soTk;

	@ApiModelProperty(notes = "Mã lý do huỷ")
	@Basic
	@Column(name = "MA_LY_DO_HUY", nullable = true, columnDefinition = "VARCHAR2(2)")
	@Size(max = 2)
	private String maLyDoHuy;

	@ApiModelProperty(notes = "Lý do huỷ")
	@Basic
	@Column(name = "LY_DO_HUY", nullable = true, columnDefinition = "VARCHAR2(2000)")
	@Size(max = 2000)
	private String lyDoHuy;

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

	@Override
	public Long getRepositoryId() {
		return this.id;
	}

	@Override
	public String getNotifyWithKey() {
		return String.format("ID : [%s]", id);
	}

	@Override
	public HuyToKhaiEntity cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
