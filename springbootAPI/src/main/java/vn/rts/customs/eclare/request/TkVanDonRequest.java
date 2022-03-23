package vn.rts.customs.eclare.request;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TkVanDonRequest {
	private Long id;

	@ApiModelProperty(notes = "So van don")
	@Size(max = 35)
	@NotNull(message = "Số vận đơn không được để trống")
	private String vanDonSo;

	@ApiModelProperty(notes = "Ngay van don")
	@NotNull(message = "Ngày vận đơn không được để trống")
	private String ngayVanDon;

	@ApiModelProperty(notes = "Số lượng kiện")
	@Max(99999999)
	@NotNull(message = "Số lượng kiện không được để trống")
	private int soLuongKien;

	@ApiModelProperty(notes = "Số lượng kiện - đơn vị tính")
	@Size(max = 4)
	@NotNull(message = "Đơn vị tính số lượng kiện không được để trống")
	private String soLuongKienDvt;

	@ApiModelProperty(notes = "So thu tu")
	@Size(max = 20)
	private String stt;

	@ApiModelProperty(notes = "Nguoi tao")
	private String nguoiTao;

	@ApiModelProperty(notes = "NGAY_TAO")
	private String ngayTao;

	@ApiModelProperty(notes = "Nguoi cap nhat")
	private String nguoiSua;

	@ApiModelProperty(notes = "Ngay cap nhat")
	private String ngaySua;

	@ApiModelProperty(notes = "FK")
	private String mauKbHqId;

	@ApiModelProperty(notes = "Số định danh")
	@Size(max = 50)
	@NotNull(message = "Số định danh không được để trống")
	private String soDinhDanh;

	@ApiModelProperty(notes = "Tổng trọng lượng (Gross Weight)")
	@Digits(integer = 20, fraction = 4)
	private BigDecimal trongLuong;

	@ApiModelProperty(notes = "Mã đơn vị tính của tổng trọng lượng")
	@Size(max = 4)
	@NotNull(message = "Đơn vị tính tổng trọng lượng không được để trống")
	private String trongLuongDvt;
}
