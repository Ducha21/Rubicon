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
public class KeySearchSystemSettingDto extends SearchListBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4668803620090857238L;

	@ApiParam(value = "id")
	private Long id;
	

	@ApiParam(value = "Domain")
	private String domain;
	
	
	@ApiParam(value = "Proxy")
	private String username;

	@ApiParam(value = "Doanh nghiep")
	private String doanhNghiepId;
	
	
}
