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
public class KeySearchChiThiHqTk extends SearchListBase implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiParam(value = "Phân loại chỉ thị của Hải quan")
	private String plChiThiHq;

	@ApiParam(value = "Ngày khai báo từ ngày")
	private String ngayTaoTuNgay;

	@ApiParam(value = "Ngày khai báo đến ngày")
	private String ngayTaoDenNgay;

	@ApiParam(value = "Số tiếp nhận")
	private String soTiepNhan;

	@ApiParam(value = "Số tờ khai")
	private String soTk;

	@ApiParam(value = "Luồng tờ khai")
	private String luongToKhai;

	@ApiParam(value = "Mã thông điệp")
	private String maThongDiep;
}
