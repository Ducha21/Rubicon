package vn.rts.customs.eclare.request.ctdt;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class CtdtHhnkThueGtgt5PtRequests {
	@ApiParam(value = "Danh sách hóa đơn thương mại")
	@Valid
	private List<CtdtHhnkThueGtgt5PtRequest> ctdt;

	private String dataSigned;
	
	@Data
	public static class CtdtHhnkThueGtgt5PtRequest {
		
		@ApiModelProperty(notes = "chứng từ đính kèm id")
		@Size(max = 36)
		private String chungTuDinhKemTkId;
		
		@ApiModelProperty(notes = "Mã phân loại hình thức chứng từ")
		@Size(max = 2)
		private String maPlHinhThucChungTu;
		
		@ApiModelProperty(notes = "Ghi chú khác")
		@Size(max = 2000)
		private String ghiChuKhac;
		
		@ApiModelProperty(notes = "NGUOI_TAO")
		private String nguoiTao;

		@ApiModelProperty(notes = "NGAY_TAO")
		private Date ngayTao;

		@ApiModelProperty(notes = "NGUOI_SUA")
		private String nguoiSua;

		@ApiModelProperty(notes = "NGAY_SUA")
		private Date ngaySua;
		
		@ApiModelProperty(notes = "File đính kèm")
		private CtdtFileDinhKemRequest fileDinhKem;
	}
}
