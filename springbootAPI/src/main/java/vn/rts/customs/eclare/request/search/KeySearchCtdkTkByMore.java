package vn.rts.customs.eclare.request.search;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class KeySearchCtdkTkByMore implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiParam(value = "Số tham chiếu")
    private String id;

    @ApiParam(value = "Loại chứng từ")
    private String loaiChungTu;

    @ApiParam(value = "Số đăng ký chứng từ")
    private Integer soDangKyChungTu;

    @ApiParam(value = "Hải quan tiếp nhận chứng từ")
    private String hqTiepNhanChungTu;

    @ApiParam(value = "Ngày khai chứng từ")
    private String ngayKhaiChungTu;

    @ApiParam(value = "Ngày đăng ký chứng từ")
    private String ngayDangKyChungTu;
}
