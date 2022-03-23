/**
 * 
 */
package vn.rts.customs.eclare.dto.body;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.util.SerializationUtils;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author ThanhNC - 2021-04-23
 *
 */

@NoArgsConstructor
@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class ContainerNosDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("billNo")
	@Size(max = 35)
	private String billNo;

	@JsonProperty("containerNo")
	@Size(max = 35)
	private String containerNo;

	@JsonProperty("sealNo")
	@Size(max = 35)
	private String sealNo;

	@JsonProperty("content")
	@Size(max = 2000)
	private String content;

	public ContainerNosDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}

}
