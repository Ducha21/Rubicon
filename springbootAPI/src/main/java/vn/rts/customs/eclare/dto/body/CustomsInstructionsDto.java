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
public class CustomsInstructionsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("dateOfCustoms")
	private String dateOfCustoms;

	@JsonProperty("titleOfCustoms")
	@Size(max = 200)
	private String titleOfCustoms;

	@JsonProperty("contentOfCustoms")
	@Size(max = 1000)
	private String contentOfCustoms;

	public CustomsInstructionsDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}

}
