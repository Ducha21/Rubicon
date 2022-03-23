package vn.rts.customs.eclare.dto.body.electronic_doc;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InvoiceContentDto extends ElectronicDocumentContentDto {
	private static final long serialVersionUID = 1L;
	private List<InvoiceContentDto.Invoice> invoices;

	@Data
	public static class Invoice {
		@JsonProperty("invoiceNo")
		private String invoiceNo;

		@JsonProperty("invoiceDate")
		private String invoiceDate;

		@JsonProperty("typeCode")
		private String typeCode;

		@JsonProperty("totalInvoice")
		private String totalInvoice;

		@JsonProperty("invoiceCurrency")
		private String invoiceCurrency;

		@JsonProperty("content")
		private String content;

		@JsonProperty("fileName")
		private String fileName;

		@JsonProperty("fileContent")
		private String fileContent;
	}
}
