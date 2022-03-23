
package vn.rts.customs.eclare.dto.body;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.util.SerializationUtils;

/**
 * @author ThanhNC - 2021-04-23
 *
 */

@NoArgsConstructor
@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class ContentBaseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("refId")
	@Size(max = 35)
	private String refId;
	
	@JsonProperty("arrivalDate")
	private String arrivalDate;

	@JsonProperty("registeredDate")
	private String registeredDate;
	
	@JsonProperty("issueDate")
	private String issueDate;

	@JsonProperty("function")
	private String function;

	@JsonProperty("documentType")
	private String documentType;

	@JsonProperty("acceptanceDate")
	private String acceptanceDate;

	@JsonProperty("issueLocation")
	private String issueLocation;
	
	@JsonProperty("acceptanceOffice")
	private String acceptanceOffice;

	@JsonProperty("refCustomsId")
	@Size(max = 35)
	private String refCustomsId;

	@JsonProperty("declarationNo")
	@Size(max = 12)
	private String declarationNo;

	@JsonProperty("declarantCode")
	@Size(max = 17)
	private String declarantCode;

	@JsonProperty("declarantName")
	@Size(max = 200)
	private String declarantName;

	@JsonProperty("declarantChannel")
	@Size(max = 1)
	private String declarantChannel;

	@JsonProperty("firstDeclarationNo")
	@Size(max = 12)
	private String firstDeclarationNo;

	@JsonProperty("branchNoOfDividedDeclaration")
	@Size(max = 2)
	private String branchNoOfDividedDeclaration;

	@JsonProperty("noOfDividedDeclaration")
	@Size(max = 2)
	private String noOfDividedDeclaration;

	@JsonProperty("declarationNoTentative")
	@Size(max = 12)
	private String declarationNoTentative;

	@JsonProperty("declarationKindCode")
	@Size(max = 3)
	private String declarationKindCode;

	@JsonProperty("cargoClassificationCode")
	@Size(max = 1)
	private String cargoClassificationCode;

	@JsonProperty("meansOfTransportationCode")
	@Size(max = 1)
	private String meansOfTransportationCode;

	@JsonProperty("classificationIndividualOrganization")
	@Size(max = 1)
	private String classificationIndividualOrganization;

	@JsonProperty("customsOffice")
	@Size(max = 6)
	private String customsOffice;

	@JsonProperty("customsSubSection")
	@Size(max = 2)
	private String customsSubSection;

	@JsonProperty("timeLimitReExport")
	private String timeLimitReExport;

	@JsonProperty("timeLimitReImport")
	private String timeLimitReImport;

	@JsonProperty("declarationPlannedDate")
	private String declarationPlannedDate;

	@JsonProperty("importerCode")
	@Size(max = 13)
	private String importerCode;

	@JsonProperty("importerName")
	@Size(max = 200)
	private String importerName;

	@JsonProperty("importerPostCode")
	@Size(max = 9)
	private String importerPostCode;

	@JsonProperty("exporterCode")
	@Size(max = 13)
	private String exporterCode;

	@JsonProperty("exporterName")
	@Size(max = 200)
	private String exporterName;

	@JsonProperty("exporterPostCode")
	@Size(max = 9)
	private String exporterPostCode;

	@JsonProperty("countryCode")
	@Size(max = 2)
	private String countryCode;

	@JsonProperty("exportConsignerName")
	@Size(max = 200)
	private String exportConsignerName;

	@JsonProperty("agentCode")
	@Size(max = 5)
	private String agentCode;

	private List<CargoNosDto> cargoNos;

	@JsonProperty("totalCargoPiece")
	@Size(max = 8)
	private String totalCargoPiece;

	@JsonProperty("totalPieceUnitCode")
	@Size(max = 3)
	private String totalPieceUnitCode;

	@JsonProperty("totalWeightGross")
	private String totalWeightGross;

	@JsonProperty("totalWeightUnitCode")
	@Size(max = 3)
	private String totalWeightUnitCode;

	@JsonProperty("customsWarehouseCode")
	@Size(max = 7)
	private String customsWarehouseCode;

	@JsonProperty("marksNumbers")
	@Size(max = 140)
	private String marksNumbers;

	@JsonProperty("loadingVesselCode")
	@Size(max = 9)
	private String loadingVesselCode;

	@JsonProperty("loadingVesselName")
	@Size(max = 200)
	private String loadingVesselName;

	@JsonProperty("loadingPortCode")
	@Size(max = 6)
	private String loadingPortCode;

	@JsonProperty("loadingPortName")
	@Size(max = 200)
	private String loadingPortName;

	@JsonProperty("numberContainers")
	@Size(max = 3)
	private String numberContainers;

	private List<PermitsDto> permits;

	@JsonProperty("processContractNo")
	private String processContractNo;

	@JsonProperty("processContractDate")
	private String processContractDate;

	@JsonProperty("invoiceClassificationCode")
	@Size(max = 1)
	private String invoiceClassificationCode;

	@JsonProperty("electronicInvoiceRefNo")
	@Size(max = 12)
	private String electronicInvoiceRefNo;

	@JsonProperty("invoiceNo")
	@Size(max = 35)
	private String invoiceNo;

	@JsonProperty("invoiceDate")
	private String invoiceDate;

	@JsonProperty("termOfPayment")
	@Size(max = 7)
	private String termOfPayment;

	@JsonProperty("invoicePriceKindCode")
	@Size(max = 1)
	private String invoicePriceKindCode;

	@JsonProperty("invoicePriceConditionCode")
	@Size(max = 3)
	private String invoicePriceConditionCode;

	@JsonProperty("invoiceCurrencyCode")
	@Size(max = 3)
	private String invoiceCurrencyCode;
	
	@JsonProperty("totalInvoicePrice")
	private String totalInvoicePrice;

	@JsonProperty("totalDistributionOnTaxValue")
	private String totalDistributionOnTaxValue;

	@JsonProperty("taxPayer")
	@Size(max = 1)
	private String taxPayer;

	@JsonProperty("bankPaymentCode")
	@Size(max = 11)
	private String bankPaymentCode;

	@JsonProperty("bankPaymentIssuedYear")
	@Size(max = 4)
	private String bankPaymentIssuedYear;

	@JsonProperty("bankPaymentSign")
	@Size(max = 10)
	private String bankPaymentSign;

	@JsonProperty("bankPaymentNo")
	@Size(max = 10)
	private String bankPaymentNo;

	@JsonProperty("paymentDueDateCode")
	@Size(max = 1)
	private String paymentDueDateCode;

	@JsonProperty("securityBankCode")
	@Size(max = 11)
	private String securityBankCode;

	@JsonProperty("securityBankIssuedYear")
	@Size(max = 4)
	private String securityBankIssuedYear;

	@JsonProperty("securityBankSign")
	@Size(max = 10)
	private String securityBankSign;

	@JsonProperty("securityNo")
	@Size(max = 10)
	private String securityNo;

	private List<ElectronicAttachmentDto> electronicAttachment;

	@JsonProperty("startDateOfTransport")
	private String startDateOfTransport;

	private List<TransitInfosDto> transitInfos;

	@JsonProperty("destinationBondedTransport")
	@Size(max = 7)
	private String destinationBondedTransport;

	@JsonProperty("arrivalDateOfTransport")
	private String arrivalDateOfTransport;

	@JsonProperty("notes")
	@Size(max = 500)
	private String notes;

	@JsonProperty("enterpriseInternalNumber")
	@Size(max = 20)
	private String enterpriseInternalNumber;

	@JsonProperty("containerNos")
	private String[] containerNos;

	@JsonProperty("customsInstructionClassification")
	private String customsInstructionClassification;

	private List<CustomsInstructionsDto> customsInstructions;

	@JsonProperty("classificationOfSpecificGoods")
	@Size(max = 1)
	private String classificationOfSpecificGoods;
	
	@JsonProperty("initialPermitCarryWarehouseDate")
	private String initialPermitCarryWarehouseDate;
	
	@JsonProperty("declarationOffice")
	private String declarationOffice;
	
	@JsonProperty("otherLawCodes")
	private String[] otherLawCodes;

	public ContentBaseDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}

}