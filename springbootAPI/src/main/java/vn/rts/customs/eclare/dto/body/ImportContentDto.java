/**
 * 
 */
package vn.rts.customs.eclare.dto.body;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

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
public class ImportContentDto extends ContentBaseDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("detailsOfValuation")
	@Size(max = 300)
	private String detailsOfValuation;
	
	@JsonProperty("exportConsignerName")
	@Size(max = 200)
	private String exportConsignerName;
	
	@JsonProperty("exporterAddress1")
	@Size(max = 200)
	private String exporterAddress1;

	@JsonProperty("exporterAddress2")
	@Size(max = 200)
	private String exporterAddress2;

	@JsonProperty("exporterAddress3")
	@Size(max = 200)
	private String exporterAddress3;

	@JsonProperty("exporterAddress4")
	@Size(max = 200)
	private String exporterAddress4;
	
	@JsonProperty("freightAmount")
	private String freightAmount;
	
	@JsonProperty("freightCurrencyCode")
	@Size(max = 3)
	private String freightCurrencyCode;
	
	@JsonProperty("freightDemarcationCode")
	@Size(max = 1)
	private String freightDemarcationCode;
	
	@JsonProperty("importContractorCode")
	@Size(max = 13)
	private String importContractorCode;
	
	@JsonProperty("importContractorName")
	@Size(max = 200)
	private String importContractorName;
	
	@JsonProperty("importerAddress")
	@Size(max = 200)
	private String importerAddress;
	
	@JsonProperty("importerTelephone")
	@Size(max = 20)
	private String importerTelephone;
	
	@JsonProperty("insuranceAmount")
	private String insuranceAmount;
	
	@JsonProperty("insuranceCurrencyCode")
	@Size(max = 3)
	private String insuranceCurrencyCode;
	
	@JsonProperty("insuranceDemarcationCode")
	@Size(max = 1)
	private String insuranceDemarcationCode;
	
	@JsonProperty("onSpotExportdeclarationNo")
	private String[] onSpotExportdeclarationNo;
	
	@JsonProperty("registrationNoOfComprehensiveInsurance")
	@Size(max = 6)
	private String registrationNoOfComprehensiveInsurance;
	
	@JsonProperty("requestReasonCode")
	@Size(max = 1)
	private String requestReasonCode;
	
	@JsonProperty("resultInspectionCode")
	@Size(max = 1)
	private String resultInspectionCode;
	
	@JsonProperty("unloadingPortCode")
	@Size(max = 6)
	private String unloadingPortCode;

	@JsonProperty("unloadingPortName")
	@Size(max = 200)
	private String unloadingPortName;
	
	@JsonProperty("taxValueCurrencyCode")
	@Size(max = 3)
	private String taxValueCurrencyCode;
	
	@JsonProperty("taxValue")
	private String taxValue;
	
	private List<ValuationAdjustmentsDto> valuationAdjustments;
	
	@JsonProperty("valuationComprehensiveRefNo")
	@Size(max = 9)
	private String valuationComprehensiveRefNo;
	
	@JsonProperty("valuationDemarcationCode")
	@Size(max = 1)
	private String valuationDemarcationCode;

	private List<ImportGoodsItemDto> goodsItems;
}