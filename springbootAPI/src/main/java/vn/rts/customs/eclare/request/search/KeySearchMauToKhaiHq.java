package vn.rts.customs.eclare.request.search;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class KeySearchMauToKhaiHq extends SearchListBase implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiParam(value = "8. Chính thức 5. Sửa 1. Hủy")
	private String loaiTkHq;

	@ApiParam(value = "1. Kinh doanh 2. Sản xuất, xuất khẩu 3. Gia công 4. Chế xuất")
	private Integer nhomLoaiHinh;

	@ApiParam(value = "Ngày khai báo - từ ngày")
	private String ngayTaoFrom;

	@ApiParam(value = "Ngày khai báo - đến ngày")
	private String ngayTaoTo;

	@ApiParam(value = "So tiep nhan khai bao")
	private String soTiepNhan;

	@ApiParam(value = "Hải quan tiếp nhận khai báo")
	private String maHq;

	@ApiParam(value = "Ma loai hinh")
	private String maLoaiHinh;

	@ApiParam(value = "Phân luồng tờ khai")
	private int phanLuong;

	@ApiParam(value = "So to khai")
	private Long soTk;

	@ApiParam(value = "ma doanh nghiep")
	private String code;

	@ApiParam(value = "Loại tờ khai (Nhập/Xuất)")
	private String toKhaiNhapXuat;
}
