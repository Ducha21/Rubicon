package vn.rts.customs.eclare.dto.body.electronic_doc;

import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CertificateContentDto extends ElectronicDocumentContentDto {
	private static final long serialVersionUID = 1L;

	private List<Certificate> certificates;

	@Data
	public static class Certificate {

		@JsonProperty("typeCode")
		private String typeCode;

		@JsonProperty("refNo")
		private String refNo;

		@JsonProperty("issueDate")
		private String issueDate;

		@JsonProperty("businessType")
		private String businessType;

		@JsonProperty("legal")
		private String legal;

		@JsonProperty("content")
		private String content;

		@Size(max = 255)
		private String fileName;

		private String fileContent;
	}
}
