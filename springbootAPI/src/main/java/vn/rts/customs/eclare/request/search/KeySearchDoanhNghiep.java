package vn.rts.customs.eclare.request.search;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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
public class KeySearchDoanhNghiep extends SearchListBase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ApiParam(value = "Trạng thái doanh nghiệp")
	@Min(0)
	@Max(1)
	private Integer status;
}
