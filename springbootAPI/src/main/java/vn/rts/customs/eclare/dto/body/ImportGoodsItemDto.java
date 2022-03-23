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

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author ThanhNC - 2021-04-23
 *
 */

@NoArgsConstructor
@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class ImportGoodsItemDto extends GoodsItemDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("exemptionImportTaxCode")
	@Size(max = 5)
	private String exemptionImportTaxCode;

	@JsonProperty("reductionImportTaxAmount")
	private String reductionImportTaxAmount;

	@JsonProperty("commodityType")
	private String commodityType;

	public ImportGoodsItemDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
