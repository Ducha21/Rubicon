/**
 * 
 */
package vn.rts.customs.eclare.dto.header;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author ThanhNC - 2021-04-23
 *
 */

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class SubjectDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("documentType")
	@Size(min = 3, max = 3)
	private String documentType;

	@JsonProperty("function")
	@Size(max = 3)
	private String function;

	@JsonProperty("refId")
	private String refId;

	public SubjectDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}

	public SubjectDto(String documentType, String function) {
		this.documentType = documentType;
		this.function = function;
	}
	
	public SubjectDto(String documentType) {
		this.documentType = documentType;
	}
}