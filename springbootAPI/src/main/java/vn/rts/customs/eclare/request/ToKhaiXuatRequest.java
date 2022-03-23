package vn.rts.customs.eclare.request;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import vn.rts.customs.eclare.entity.DiaDiemXepHangTkEntity;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ToKhaiXuatRequest extends ToKhaiRequest {
	@ApiModelProperty(notes = "Phân loại không cần quy đổi VND")
	@Size(max = 1)
	private String plKhongCanQuyDoiVND;

	@ApiModelProperty(notes = "Ngày hàng đi dự kiến")
	@NotNull(message = "Ngày hàng đi dự kiến không được để trống")
	private String ngayHangDiDuKien;

	@ApiModelProperty(notes = "Mã người ủy thác xuất khẩu")
	@Size(max = 20)
	private String maNguoiUyThacXk;

	@ApiModelProperty(notes = "Tên người ủy thác xuất khẩu")
	@Size(max = 200)
	private String tenNguoiUyThacXk;

	@ApiModelProperty(notes = "Địa chỉ người ủy thác xuất khẩu")
	@Size(max = 200)
	@NotNull(message = "Địa chỉ người ủy thác xuất khẩu không được để trống")
	private String diaChiNguoiXk;

	@NotNull(message = "Số điện thoại người xuất khẩu không được để trống")
	@Size(max = 20)
	private String sdtNguoiXk;

	@ApiModelProperty(notes = "Mã địa điểm nhận hàng cuối cùng ")
	@Size(max = 5)
	@NotNull(message = "Mã địa điểm nhận hàng cuối cùng không được để trống")
	private String maDiaDiemNhanHangCuoi;

	@ApiModelProperty(notes = "Tên địa điểm nhận hàng cuối cùng ")
	@Size(max = 200)
	@NotNull(message = "Tên địa điểm nhận hàng cuối cùng không được để trống")
	private String tenDiaDiemNhanHangCuoi;

	@NotNull(message = "Địa chỉ nhập khẩu 1 không được để trống")
	@Size(max = 200)
	private String diaChiNk1;

	@Size(max = 200)
	private String diaChiNk2;

	@Size(max = 200)
	private String diaChiNk3;

	@Size(max = 200)
	private String diaChiNk4;
	
	@NotNull(message = "Tổng trị giá tính thuế không được để trống")
	@Digits(integer = 20, fraction = 4)
	private BigDecimal tongTriGiaTinhThue;
	
	@NotNull(message = "Mã tiền tệ tổng trị giá tính thuế không được để trống")
	private String tongTriGiaTinhThueMaTienTe;
	
	@ApiModelProperty(notes = "Thoi han tai nhap")
	private String thoiHanTaiNhap;
	
	@ApiModelProperty(notes = "Ma nuoc nguoi nhap khau")
	private String maNuocNk;
	
	@ApiParam(value = "vanning")
	private List<DiaDiemXepHangTkEntity> dsDiaDiemXepHang;
}
