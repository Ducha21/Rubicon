package vn.rts.customs.eclare.request;

import java.util.Date;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SoDinhKemKbdtRequest {
	private Long id;
	
    @ApiModelProperty(notes = "MAU_TK_HQ_ID")
    private String mauTkHqId;
	  
    @ApiModelProperty(notes = "Phân loại đính kém khai báo điện tử")
    @Size(max = 3)
    private String plDinhKemDt;
	  
    @ApiModelProperty(notes = "Số đính kém khai báo điện tử")
    @Size(max = 12)
    private String soDinhKemDt;
	  
    @ApiModelProperty(notes = "NGUOI_TAO")
    private String nguoiTao;
	  
    @ApiModelProperty(notes = "NGAY_TAO")
    private Date ngayTao;
	  
    @ApiModelProperty(notes = "NGUOI_SUA")
    private String nguoiSua;
	  
    @ApiModelProperty(notes = "NGAY_SUA")
    private Date ngaySua;
	  
    @ApiModelProperty(notes = "Số chứng từ")
    @Size(max = 20)
    private String soChungTu;
	  
    @ApiModelProperty(notes = "Tên chứng từ")
    @Size(max = 200)
    private String tenChungTu;
	  
    @ApiModelProperty(notes = "Ngày chứng từ")
    private Date ngayChungTu;
	  
    @ApiModelProperty(notes = "Ghi chú")
    @Size(max = 500)
    private String ghiChu;
	  
    @ApiModelProperty(notes = "Tên file chứng từ")
    @Size(max = 512)
    private String tenFileChungTu;
	  
    @ApiModelProperty(notes = "url")
    @Size(max = 512)
    private String urlFile;
	  
    @ApiModelProperty(notes = "kích thước file")
    @Size(max = 20)
    private String sizeFile;
}
