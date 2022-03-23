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
public class ValuationAdjustmentsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("sequence")
	private String sequence;

	@JsonProperty("codeValuationTitle")
	@Size(max = 1)
	private String codeValuationTitle;

	@JsonProperty("valuationAdjustmentCode")
	@Size(max = 3)
	private String valuationAdjustmentCode;

	@JsonProperty("currencyCodeEvaluated")
	@Size(max = 3)
	private String currencyCodeEvaluated;

	@JsonProperty("evaluatedAmount")
	private String evaluatedAmount;

	@JsonProperty("totalDistributionEevaluatedAmount")
	private String totalDistributionEevaluatedAmount;

	public ValuationAdjustmentsDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}

}
