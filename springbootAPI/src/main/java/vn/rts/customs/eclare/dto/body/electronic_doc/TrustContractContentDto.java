package vn.rts.customs.eclare.dto.body.electronic_doc;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class TrustContractContentDto extends ElectronicDocumentContentDto {
	List<TrustContractContentDto.TrustContract> trustContracts;

	@Data
	public static class TrustContract {
		@NotNull
		@Size(max = 255)
		private String entrusteeName;

		@NotNull
		@Size(max = 17)
		private String entrusteeCode;

		@NotNull
		@Size(max = 255)
		private String entrusteeAddress;

		@NotNull
		@Size(max = 255)
		private String entrustorName;

		@NotNull
		@Size(max = 17)
		private String entrustorCode;

		@NotNull
		@Size(max = 255)
		private String entrustorAddress;

		@Size(max = 2000)
		private String content;

		@NotNull
		private String fileName;

		@NotNull
		private String fileContent;
	}
}
