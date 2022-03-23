package vn.rts.customs.eclare.dto.body.electronic_doc;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ImportedGoodsWith5PercentTaxContentDto extends ElectronicDocumentContentDto {
	private static final long serialVersionUID = 1L;
	private List<ImportedGoodsWith5PercentTaxContentDto.ImportedGoodsWith5PercentTax> certTaxs;
	@Data
	
	public static class ImportedGoodsWith5PercentTax {
		@JsonProperty("typeCode")
		private String typeCode;
		
		@JsonProperty("content")
		private String content;

		@JsonProperty("fileName")
		private String fileName;

		@JsonProperty("fileContent")
		private String fileContent;
	}
}
