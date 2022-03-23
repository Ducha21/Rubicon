package vn.rts.customs.eclare.dto.body;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CancelContentDto {
	@JsonProperty("refId")
	private String refId;
	
	@Size(max = 10)
	@NotNull
	@JsonProperty("documentType")
	private String documentType;
	
	@Size(max = 2)
	@NotNull
	@JsonProperty("function")
	private String function;
	
	@NotNull
	@JsonProperty("issueDate")
	private String issueDate;
	
	@NotNull
	@Size(max = 38)
	@JsonProperty("refCustomsId")
	private String refCustomsId;
	
	@JsonProperty("issueLocation")
	@Size(max = 60)
	private String issueLocation;
	
	@NotNull
	@Size(max = 10)
	private String acceptanceDate;
	
	@NotNull
	@Size(max = 6)
	private String acceptanceOffice;
	
	@NotNull
	@Size(max = 1)
	private String declarantChannel;
	
	@NotNull
	@Size(max = 17)
	private String declarantCode;
	
	@NotNull
	@Size(max = 255)
	private String declarantName;
	
	private String declarantTel;
	
	private String declarantAddress;
	
	@NotNull
	@Size(max = 12)
	private String declarationNo;
	
	@NotNull
	@Size(max = 19)
	private String registeredDate;
	
	@NotNull
	@Size(max = 3)
	private String declarationKindCode;
	
	@NotNull
	@Size(max = 6)
	private String declarationOffice;
	
	@NotNull
	@Size(max = 17)
	private String importerCode;
	
	@NotNull
	@Size(max = 255)
	private String importerName;
	
	private String requestStatement;
	
	@Size(max = 2000)
	private String requestContent;
	
	@NotNull
	@Size(max = 10)
	private String clearanceDate;
}
