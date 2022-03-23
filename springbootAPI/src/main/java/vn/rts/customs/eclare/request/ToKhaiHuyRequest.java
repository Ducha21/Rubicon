package vn.rts.customs.eclare.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ToKhaiHuyRequest {
	  
    @ApiModelProperty(notes = "Nơi khai báo xin huỷ")
    @NotNull(message = "Nơi khai báo hủy không được để trống")
    @Size(max = 60)
    private String noiKbHuy;
	  
    @ApiModelProperty(notes = "Mã đơn vị hải quan tiếp nhận chứng từ")
    @Size(max = 6)
    private String donViHqTiepNhan;
	  
    @ApiModelProperty(notes = "Kênh khai báo")
    @Size(max = 1)
    @NotNull(message = "Kênh khai báo không được để trống")
    private String kenhKhaiBao;
	  
    @ApiModelProperty(notes = "Mã người khai Hải quan")
    @Size(max = 17)
    @NotNull(message = "Mã người khai hải quan không được để trống")
    private String maNguoiKhaiHq;
	  
    @ApiModelProperty(notes = "Tên người khai Hải quan")
    @Size(max = 200)
    @NotNull(message = "Tên người khai hải quan không được để trống")
    private String tenNguoiKhaiHq;
	  
    @ApiModelProperty(notes = "Số điện thoại (người khai Hải quan; )")
    @Size(max = 20)
    private String sdtNguoiKhaiHq;
	  
    @ApiModelProperty(notes = "Địa chỉ người khai")
    @Size(max = 200)
    private String diaChiNguoiKhaiHq;
	  
    @ApiModelProperty(notes = "Số tờ khai")
    @Size(max = 20)
//    @NotNull(message = "Số tờ khai không được để trống")
    private String soTk;
	  
    @ApiModelProperty(notes = "Mã lý do huỷ")
    @Size(max = 2)
    private String maLyDoHuy;
	  
    @ApiModelProperty(notes = "Lý do huỷ")
    @Size(max = 2000)
    private String lyDoHuy;

    @ApiModelProperty(notes = "mã doanh nghiệp")
    @Size(max = 36)
    private String maDoanhNghiep;

    private String dataSigned;
}
