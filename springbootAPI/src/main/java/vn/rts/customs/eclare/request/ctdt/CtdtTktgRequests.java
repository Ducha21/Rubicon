package vn.rts.customs.eclare.request.ctdt;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CtdtTktgRequests {
	@Valid
	private List<CtdtTktgRequests.CtdtTktgRequest> ctdt;

	private String dataSigned;
	
	@Data
	public static class CtdtTktgRequest {
		@ApiModelProperty(notes = "Ghi chú khác")
		@Size(max = 2000)
		private String ghiChuKhac;

		@ApiModelProperty(notes = "File đính kèm")
		private CtdtFileDinhKemRequest fileDinhKem;
	}
}
