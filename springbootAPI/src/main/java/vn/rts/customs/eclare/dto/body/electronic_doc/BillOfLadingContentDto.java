package vn.rts.customs.eclare.dto.body.electronic_doc;

import java.util.List;

import lombok.Data;

@Data
public class BillOfLadingContentDto extends ElectronicDocumentContentDto {

	private static final long serialVersionUID = 1L;

	List<BillOfLading> billOfLadings;

	@Data
	public static class BillOfLading {
		private String billNo;
		private String billDate;
		private String issueLocation;
		private String carrierCode;
		private String carrierName;
		private String numberContainers;
		private String cargoPiece;
		private String pieceUnitCode;
		private String cargoWeight;
		private String weightUnitCode;
		private String deliveryMethod;
		private String numberBranch;
		private String transitLocation;
		private String typeCode;
		private String content;
		private String fileName;
		private String fileContent;
		private List<BranchDetail> branchDetails;
		private List<ContainerNo> containerNos;
	}

	@Data
	public static class BranchDetail {
		private String sequenceNo;
		private String billNoBranch;
	}

	@Data
	public static class ContainerNo {
		private String containerNo;
		private String sealNo;
	}
}
