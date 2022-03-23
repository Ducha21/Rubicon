package vn.rts.customs.eclare.request.search;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class KeySearchChungTuDinhKemTk extends SearchListBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiParam(value = "id tờ khai hải quan")
    private String toKhaiId;
    
    @ApiParam(value = "Số tham chiếu")
    private String id;

    @ApiParam(value = "Loại chứng từ")
    private String loaiChungTu;

    @ApiParam(value = "Hải quan tiếp nhận chứng từ")
    private String hqTiepNhanChungTu;

    @ApiParam(value = "Ngày tao")
    private String ngayTao;

    @ApiParam(value = "Ngay hq phan hoi")
    private String ngayHqPhanHoi;

}
