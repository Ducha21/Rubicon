/**
 * 
 */
package vn.rts.customs.eclare.dto.header;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotNull;
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
public class ReferenceDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("version")
	@NotNull
	@Size(max = 50)
	private String version;

	@JsonProperty("messageId")
	@NotNull
	@Size(min = 36, max = 36)
	private String messageId;
	
	public ReferenceDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}

	public ReferenceDto(String version, String replyTo) {
		this.version = version;
		this.messageId = UUID.randomUUID().toString().toUpperCase();
	}

}
