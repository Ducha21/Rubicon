/**
 * 
 */
package vn.rts.customs.eclare.dto.body;

import java.io.Serializable;

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
public class SignatureDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("data")
	private String data;

	@JsonProperty("fileCert")
	private String fileCert;

	public SignatureDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
