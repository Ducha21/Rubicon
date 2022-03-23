/**
 * 
 */
package vn.rts.customs.eclare.dto.header;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ThanhNC - 2021-04-23
 *
 */

@NoArgsConstructor
@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class FromDto  implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("name")
	@NotNull
	@Size(max = 255)
	private String name;

	@JsonProperty("identity")
	@NotNull
	@Size(max = 50)
	private String identity;
	
	public FromDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}

}