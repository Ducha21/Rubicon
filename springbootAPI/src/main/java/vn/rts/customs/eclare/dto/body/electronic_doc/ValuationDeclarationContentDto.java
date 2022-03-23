package vn.rts.customs.eclare.dto.body.electronic_doc;

import java.util.List;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public final class ValuationDeclarationContentDto extends ElectronicDocumentContentDto{
	private static final long serialVersionUID = 1L;
	
	private List<ValuationDeclarationContentDto.ValuationDeclaration> valuationDeclarations;

	@Data
	public static class ValuationDeclaration {
		@Size(max = 2000)
		private String content;

		@Size(max = 255)
		private String fileName;

		private String fileContent;
	}
}
