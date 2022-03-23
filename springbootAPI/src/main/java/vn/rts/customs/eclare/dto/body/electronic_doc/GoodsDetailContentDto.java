package vn.rts.customs.eclare.dto.body.electronic_doc;

import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GoodsDetailContentDto extends ElectronicDocumentContentDto {
	private static final long serialVersionUID = 1L;

	private List<GoodsDetail> goodsDetails;

	@Data
	public static class GoodsDetail {
		@JsonProperty("refNo")
		private String refNo;

		@JsonProperty("issueDate")
		private String issueDate;

		@JsonProperty("totalItems")
		private String totalItems;

		@JsonProperty("cargoPiece")
		private String cargoPiece;

		@JsonProperty("pieceUnitCode")
		private String pieceUnitCode;

		@JsonProperty("content")
		private String content;

		@Size(max = 255)
		private String fileName;

		private String fileContent;
	}
}
