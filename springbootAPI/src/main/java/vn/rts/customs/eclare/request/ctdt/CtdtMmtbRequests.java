package vn.rts.customs.eclare.request.ctdt;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@Data
public class CtdtMmtbRequests {

    @ApiModelProperty(notes = "Danh sách thông tin về danh mục máy móc")
    @Valid
    private List<CtdtMmtbRequest> ctdt;

    private String dataSigned;

    @Data
    public static class CtdtMmtbRequest {

        @ApiModelProperty(notes = "GHI_CHU_KHAC")
        @Size(max = 2000)
        private String ghiChuKhac;

        @ApiModelProperty(notes = "File đính kèm")
        private CtdtFileDinhKemRequest fileDinhKem;

    }

}
