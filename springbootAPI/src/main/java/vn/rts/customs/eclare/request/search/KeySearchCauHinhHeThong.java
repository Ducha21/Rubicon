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
public class KeySearchCauHinhHeThong extends SearchListBase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ApiParam(value = "Nhóm")
	private String nhom;

	@ApiParam(value = "Key")
	private String key;
	
}
