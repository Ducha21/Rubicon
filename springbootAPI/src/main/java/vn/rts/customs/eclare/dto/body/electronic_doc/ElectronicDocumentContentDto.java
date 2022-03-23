package vn.rts.customs.eclare.dto.body.electronic_doc;

import java.io.Serializable;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ElectronicDocumentContentDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("documentType")
	@Size(max = 3)
	private String documentType;

	@JsonProperty("declarantChannel")
	@Size(max = 1)
	private String declarantChannel;

	@JsonProperty("refId")
	@Size(max = 36)
	private String refId;

	@JsonProperty("issueDate")
	@Size(max = 19)
	private String issueDate;

	@JsonProperty("function")
	@Size(max = 2)
	private String function;

	@JsonProperty("issueLocation")
	@Size(max = 60)
	private String issueLocation;

	@JsonProperty("refCustomsId")
	@Size(max = 38)
	private String refCustomsId;
	
	@JsonProperty("acceptanceDate")
	@Size(max = 19)
	private String acceptanceDate;
	
	@JsonProperty("acceptanceOffice")
	@Size(max = 6)
	private String acceptanceOffice;
	
	@JsonProperty("declarantCode")
	@Size(max = 17)
	private String declarantCode;
	
	@JsonProperty("declarantName")
	@Size(max = 200)
	private String declarantName;
	
	@JsonProperty("importerName")
	@Size(max = 200)
	private String importerName;
	
	@JsonProperty("importerCode")
	@Size(max = 13)
	private String importerCode;
	
	@JsonProperty("importerAddress")
	@Size(max = 255)
	private String importerAddress;
	
	@JsonProperty("declarationNo")
	@Size(max = 12)
	private String declarationNo;
	
	@JsonProperty("registeredDate")
	@Size(max = 10)
	private String registeredDate;
	
	@JsonProperty("declarationKindCode")
	@Size(max = 10)
	private String declarationKindCode;
	
	@JsonProperty("declarationOffice")
	@Size(max = 6)
	private String declarationOffice;
}
