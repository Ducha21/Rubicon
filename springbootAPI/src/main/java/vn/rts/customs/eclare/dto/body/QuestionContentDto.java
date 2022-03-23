/**
 * 
 */
package vn.rts.customs.eclare.dto.body;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.SerializationUtils;

/**
 * @author ThanhNC - 2021-04-23
 *
 */

@NoArgsConstructor
@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class QuestionContentDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("documentType")
	@Size(max = 3)
	private String documentType;
	
	@JsonProperty("declarantChannel")
	@Size(max = 1)
	private String declarantChannel;

	@JsonProperty("function")
	private String function;

	@JsonProperty("refId")
	@Size(max = 36)
	private String refId;

	@JsonProperty("refCustomsId")
	private String refCustomsId;
	
	@JsonProperty("declarantCode")
	@Size(max = 13)
	private String declarantCode;
	
	@JsonProperty("declarantName")
	@Size(max = 200)
	private String declarantName;
	
	@JsonProperty("importerCode")
	@Size(max = 255)
	private String importerCode;
	
	@JsonProperty("importerName")
	@Size(max = 17)
	private String importerName;

	public QuestionContentDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}

}