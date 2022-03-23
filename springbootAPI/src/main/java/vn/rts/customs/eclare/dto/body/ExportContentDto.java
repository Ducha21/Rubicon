/**
 * 
 */
package vn.rts.customs.eclare.dto.body;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author ThanhNC - 2021-04-23
 *
 */

@NoArgsConstructor
@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@EqualsAndHashCode(callSuper = true)
public class ExportContentDto extends ContentBaseDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("classificationNonConvertingVND")
	@Size(max = 1)
	private String classificationNonConvertingVND;
	
	@JsonProperty("departurePlannedDate")
	@Size(max = 200)
	private String departurePlannedDate;
	
	@JsonProperty("exportContractorCode")
	@Size(max = 13)
	private String exportContractorCode;
	
	@JsonProperty("exportContractorName")
	@Size(max = 200)
	private String exportContractorName;
	
	@JsonProperty("exporterAddress")
	@Size(max = 200)
	private String exporterAddress;
	
	@JsonProperty("exporterTelephone")
	@Size(max = 20)
	private String exporterTelephone;
	
	@JsonProperty("finalDestinationCode")
	@Size(max = 5)
	private String finalDestinationCode;
	
	@JsonProperty("finalDestinationName")
	@Size(max = 200)
	private String finalDestinationName;
	
	@JsonProperty("importerAddress1")
	@Size(max = 200)
	private String importerAddress1;

	@JsonProperty("importerAddress2")
	@Size(max = 200)
	private String importerAddress2;

	@JsonProperty("importerAddress3")
	@Size(max = 200)
	private String importerAddress3;

	@JsonProperty("importerAddress4")
	@Size(max = 200)
	private String importerAddress4;
	
	@JsonProperty("totalTaxValue")
	private String totalTaxValue;

	@JsonProperty("totalTaxValueCurrencyCode")
	@Size(max = 3)
	private String totalTaxValueCurrencyCode;
	
	@JsonProperty("vanningPlaceCode")
	@Size(max = 7)
	private String vanningPlaceCode;

	@JsonProperty("vanningPlaceName")
	@Size(max = 70)
	private String vanningPlaceName;

	@JsonProperty("vanningPlaceAddress")
	@Size(max = 70)
	private String vanningPlaceAddress;
	
	@JsonProperty("vanningPlaces")
	private List<VanningPlacesDto> vanningPlaces;

	private List<ExportGoodsItemDto> goodsItems;

	public ExportContentDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}

}