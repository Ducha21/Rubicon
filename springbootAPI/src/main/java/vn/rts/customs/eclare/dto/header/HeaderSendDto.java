/**
 * 
 */
package vn.rts.customs.eclare.dto.header;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ThanhNC - 2021-04-23
 *
 */

@NoArgsConstructor
@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class HeaderSendDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("declarantChannel")
	private String declarantChannel;

	@JsonProperty("reference")
	private ReferenceDto reference;

	@JsonProperty("sendApplication")
	private SendApplicationDto sendApplication;

	@JsonProperty("from")
	private FromDto from;

	@JsonProperty("to")
	private ToDto to;

	@JsonProperty("subject")
	private SubjectDto subject;

	public HeaderSendDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
