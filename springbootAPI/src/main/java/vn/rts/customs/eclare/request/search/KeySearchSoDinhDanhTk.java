package vn.rts.customs.eclare.request.search;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class KeySearchSoDinhDanhTk extends SearchListBase implements Serializable {
	private String soDinhDanh;

	private String soTiepNhan;

	private Date ngayTiepNhan;

	private Date ngayCap;

	private String maDoanhNghiep;

	private String trangThai;

	private String kenhKhaiBao;

	private String noiKhaiBao;

	private String hqTiepNhan;

	private String tenHqTiepNhan;

	private String maNguoiKhaiHq;

	private String tenNguoiKhaiHq;

	private String tenDoanhNghiep;

	private String diaChiDoanhNghiep;

	private String loaiDoiTuong;

	private String loaiHangHoa;

	private String doanhNghiepXnk;
}
