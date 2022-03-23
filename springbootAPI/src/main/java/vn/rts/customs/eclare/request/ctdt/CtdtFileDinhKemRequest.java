package vn.rts.customs.eclare.request.ctdt;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CtdtFileDinhKemRequest {
	@ApiModelProperty(notes = "PHAN_LOAI_DINH_KEM_DT")
	@Size(max = 4)
	private String phanLoaiDinhKemDt;
	
	private String file;

	@ApiModelProperty(notes = "SIZE_FILE")
	@Size(max = 20)
	private String sizeFile;

	@ApiModelProperty(notes = "TEN_FILE_CHUNG_TU")
	@Size(max = 512)
	private String tenFileChungTu;

	@ApiModelProperty(notes = "LOAI_CHUNG_TU_ID")
	@Min(0L)
	@Max(Long.MAX_VALUE)
	private Long loaiChungTuId;
}
