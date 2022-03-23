/**
 * 
 */
package vn.rts.customs.eclare.dto.body;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

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
public class GoodsItemDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("sequence")
	private String sequence;
	
	@JsonProperty("hsCode")
	@Size(max = 12)
	private String hsCode;

	@JsonProperty("materialCode")
	@Size(max = 7)
	private String materialCode;

	@JsonProperty("dutyRate")
	private String dutyRate;

	@JsonProperty("absoluteTariffRate")
	private String absoluteTariffRate;

	@JsonProperty("unitAbsoluteDutyRate")
	@Size(max = 4)
	private String unitAbsoluteDutyRate;

	@JsonProperty("currencyAbsoluteDutyRate")
	@Size(max = 3)
	private String currencyAbsoluteDutyRate;

	@JsonProperty("commodityCode")
	@Size(max = 50)
	private String commodityCode;

	@JsonProperty("itemName")
	@Size(max = 500)
	private String itemName;

	@JsonProperty("originCode")
	@Size(max = 2)
	private String originCode;

	@JsonProperty("importTaxClassificationCode")
	@Size(max = 3)
	private String importTaxClassificationCode;

	@JsonProperty("tariffQuotaClassification")
	@Size(max = 1)
	private String tariffQuotaClassification;

	@JsonProperty("perUnitTaxCode")
	@Size(max = 10)
	private String perUnitTaxCode;

	@JsonProperty("quantity_1")
	@Size(max = 12)
	private String quantity1;

	@JsonProperty("quantityUnitCode_1")
	@Size(max = 4)
	private String quantityUnitCode1;

	@JsonProperty("quantity_2")
	private String quantity2;

	@JsonProperty("quantityUnitCode_2")
	@Size(max = 4)
	private String quantityUnitCode2;

	@JsonProperty("invoiceValue")
	@Size(max = 20)
	private String invoiceValue;

	@JsonProperty("invoiceUnitPrice")
	private String invoiceUnitPrice;

	@JsonProperty("unitPriceCurrencyCode")
	@Size(max = 3)
	private String unitPriceCurrencyCode;

	@JsonProperty("unitPriceQuantity")
	@Size(max = 4)
	private String unitPriceQuantity;

	@JsonProperty("taxValue")
	private String taxValue;

	@JsonProperty("taxValueCurrencyCode")
	@Size(max = 3)
	private String taxValueCurrencyCode;

	@JsonProperty("valuationNos")
	private String[] valuationNos;

	@JsonProperty("lineNumberTentative")
	@Size(max = 2)
	private String lineNumberTentative;

	@JsonProperty("taxExemptionNo")
	@Size(max = 12)
	private String taxExemptionNo;

	@JsonProperty("lineNumberTaxExemption")
	@Size(max = 3)
	private String lineNumberTaxExemption;

	private ArrayList<OtherTaxsDto> otherTaxs;
	
	@JsonProperty("certificateType")
	private String certificateType;
	
	@JsonProperty("certificateOfOrigins")
	private List<CertificateOfOriginsDto> certificateOfOrigins;

	public GoodsItemDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
