/**
 * 
 */
package vn.rts.customs.eclare.dto.body;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class OtherTaxsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("typeCode")
	@Size(max = 10)
	private String typeCode;

	@JsonProperty("exemptionOtherCode")
	@Size(max = 5)
	private String exemptionOtherCode;

	@JsonProperty("reductionOtherTaxAmount")
	@Size(max = 16)
	private String reductionOtherTaxAmount;

	public OtherTaxsDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
