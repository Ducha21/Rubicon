package vn.rts.customs.eclare.dto.body.electronic_doc;

import lombok.Data;
import lombok.NoArgsConstructor;
import vn.rts.customs.eclare.entity.ChungTuDinhKemTkEntity;
import vn.rts.customs.eclare.entity.CtdtFileDinhKemEntity;

import java.util.Optional;

@Data
@NoArgsConstructor
public class CtdtDetailResponseDto {

    private ctdkTk<?> ctdktk;

    @Data
    @NoArgsConstructor
    public static class ctdkTk<B>{
        private Optional<ChungTuDinhKemTkEntity> chungTuDinhKemTk;

        private ctdt<B> ctdt;
    }

    @Data
    @NoArgsConstructor
    public static class ctdt<B>{

        private B chungTu;

        private CtdtFileDinhKemEntity ctdtFileDinhKem;
    }

}
