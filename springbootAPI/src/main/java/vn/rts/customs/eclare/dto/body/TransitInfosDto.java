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
public class TransitInfosDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("transitLocationBondedTransport")
	@Size(max = 3)
	private String transitLocationBondedTransport;

	@JsonProperty("transitArrivalDateOfTransport")
	private String transitArrivalDateOfTransport;

	@JsonProperty("transitStartDateOfTransport")
	private String transitStartDateOfTransport;

	public TransitInfosDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}

}
