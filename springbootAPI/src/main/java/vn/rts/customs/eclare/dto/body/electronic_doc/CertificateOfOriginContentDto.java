package vn.rts.customs.eclare.dto.body.electronic_doc;

import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CertificateOfOriginContentDto extends ElectronicDocumentContentDto {
	private static final long serialVersionUID = 1L;

	private List<CertificateOfOrigin> certificateOfOrigins;

	@Data
	public static class CertificateOfOrigin {
		@JsonProperty("certNo")
		private String certNo;

		@JsonProperty("certType")
		private String certType;

		@JsonProperty("certDate")
		private String certDate;

		@JsonProperty("certOrganization")
		private String certOrganization;

		@JsonProperty("issuer")
		private String issuer;

		@JsonProperty("issueLocation")
		private String issueLocation;

		@JsonProperty("certTime")
		private String certTime;

		@JsonProperty("content")
		private String content;

		@Size(max = 255)
		private String fileName;

		private String fileContent;
	}
}
