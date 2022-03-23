package vn.rts.customs.eclare.dto.body;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CertificateOfOriginsDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("certDate")
	private String certDate;
	
	@JsonProperty("certLocation")
	private String certLocation;
	
	@JsonProperty("certIssuer")
	private String certIssuer;
	
	@JsonProperty("certExpire")
	private String certExpire;
	
	@JsonProperty("certNo")
	private String certNo;
}
