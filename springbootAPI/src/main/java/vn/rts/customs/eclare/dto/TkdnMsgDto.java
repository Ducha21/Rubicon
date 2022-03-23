package vn.rts.customs.eclare.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class TkdnMsgDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "ID")
	private String id;

	@ApiModelProperty(notes = "So tiep nhan khai bao")
	private String soTiepNhan;

	@ApiModelProperty(notes = "So to khai (Không phải nhập dữ liệu, hệ thống tự trả về)")
	private String soTk;

	@ApiModelProperty(notes = "NGAY_TAO")
	private String ngayTao;

	@ApiModelProperty(notes = "Ngày khai báo")
	private String ngayTraLoi;

	@ApiModelProperty(notes = "Hải quan tiếp nhận khai báo")
	private String haiQuanTiepNhanKb;

	@ApiModelProperty(notes = "Tên hải quan tiếp nhận khai báo")
	private String tenHaiQuanTiepNhanKb;

	@ApiModelProperty(notes = "Ma loai hinh")
	private String maLoaiHinh;

	@ApiModelProperty(notes = "Nhom loai hinh(0 . Tất cả;1. Chính thức;2. Sửa; 3. Hủy ;- Gía trị mặc định : Tất cả)")
	private Integer nhomLoaiHinh;

	@ApiModelProperty(notes = "Ma chuc nang")
	private String maChucNang;

	@ApiModelProperty(notes = "Ma ket qua kiem tra noi dung")
	private String ketQuaXuLy;
}
