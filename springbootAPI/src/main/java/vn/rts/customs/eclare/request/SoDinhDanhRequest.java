package vn.rts.customs.eclare.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SoDinhDanhRequest {
	@ApiModelProperty(notes = "Kênh khai báo")
	private String kenhKhaiBao;

	@ApiModelProperty(notes = "Nơi khai báo")
	private String noiKhaiBao;

	@ApiModelProperty(notes = "Hải quan tiếp nhận")
	private String hqTiepNhan;

	@ApiModelProperty(notes = "Ten Hải quan tiếp nhận")
	private String tenHqTiepNhan;

	@ApiModelProperty(notes = "Ma người khai hq")
	private String maNguoiKhaiHq;

	@ApiModelProperty(notes = "Tên người khai hq")
	private String tenNguoiKhaiHq;

	@ApiModelProperty(notes = "Mã doanh nghiệp")
	private String maDoanhNghiep;

	@ApiModelProperty(notes = "Tên doanh nghiệp")
	private String tenDoanhNghiep;

	@ApiModelProperty(notes = "Địa chỉ doanh nghiệp")
	private String diaChiDoanhNghiep;

	@ApiModelProperty(notes = "Loại đối tượng xin cấp số")
	private String loaiDoiTuong;

	@ApiModelProperty(notes = "Loại hàng hóa")
	private String loaiHangHoa;

	@ApiModelProperty(notes = "Doanh nghiệp XNK mở tờ khai")
	private String doanhNghiepXnk;

	private String dataSigned;
}
