package vn.rts.customs.eclare.dto.body.electronic_doc;

import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LicenseContentDto extends ElectronicDocumentContentDto {
	private static final long serialVersionUID = 1L;

	private List<LicenseContentDto.License> licenses;

	@Data
	public static class License {
		@JsonProperty("typeCode")
		private String typeCode;

		@JsonProperty("licenseType")
		private String licenseType;

		@JsonProperty("licenseNo")
		private String licenseNo;

		@JsonProperty("licenseDate")
		private String licenseDate;

		@JsonProperty("expireDate")
		private String expireDate;

		@JsonProperty("issueLocation")
		private String issueLocation;

		@JsonProperty("issuer")
		private String issuer;

		@Size(max = 2000)
		private String content;

		@Size(max = 255)
		private String fileName;

		private String fileContent;

		private List<LicenseContentDto.GoodItem> goodsItems;
	}

	@Data
	public static class GoodItem {
		private String sequenceNo;

		@Size(max = 12)
		private String hsCode;

		@Size(max = 256)
		private String itemName;

		private String quantity;

		@Size(max = 4)
		private String quantityUnit;

		private String statisticalValue;

		@Size(max = 3)
		private String currencyCode;

		@Size(max = 2000)
		private String content;
	}

}
