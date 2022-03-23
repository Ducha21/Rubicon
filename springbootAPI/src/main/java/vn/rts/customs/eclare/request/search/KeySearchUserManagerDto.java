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
public class KeySearchUserManagerDto extends SearchListBase implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -6228193575034514518L;


	@ApiParam(value = "Tên Đăng nhập")
	private String tenDangNhap;
	

	@ApiParam(value = "Họ và tên")
	 private String hoTen;
	
	
	@ApiParam(value = "Trạng thái")
	private String trangThai;
}
