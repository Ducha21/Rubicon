package vn.rts.customs.eclare.dto.body.electronic_doc;

import java.util.List;

import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class SpecializedCersContentDto extends ElectronicDocumentContentDto {

	private static final long serialVersionUID = 1L;

	private List<SpecializedCersContentDto.SpecializedCers> specializedCers;

	@Data
	public static class SpecializedCers {
		@NotNull
		@Size(max = 4)
		private String typeCode;

		@NotNull
		private String type;

		@NotNull
		private String name;

		@NotNull
		@Size(max = 35)
		private String refNo;

		@NotNull
		@Size(max = 10)
		private String issueDate;

		@NotNull
		@Size(max = 10)
		private String expireDate;

		@NotNull
		@Size(max = 255)
		private String issueLocation;

		@NotNull
		@Size(max = 255)
		private String issuer;

		@Size(max = 2000)
		private String content;

		private String fileName;

		private String fileContent;
		
		private List<GoodItem> goodsItems;
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
