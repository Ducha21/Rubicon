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
public class ExportGoodsItemDto extends GoodsItemDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("exemptionExportTaxCode")
	@Size(max = 5)
	private String exemptionExportTaxCode;

	@JsonProperty("reductionExportTaxAmount")
	private String reductionExportTaxAmount;

	public ExportGoodsItemDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
