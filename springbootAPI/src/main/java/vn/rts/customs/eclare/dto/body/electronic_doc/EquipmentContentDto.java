package vn.rts.customs.eclare.dto.body.electronic_doc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class EquipmentContentDto extends ElectronicDocumentContentDto{

    private List<Equipment> equipments;

    @Data
    public static class Equipment{
        @JsonProperty("content")
        private String content;

        @Size(max = 255)
        private String fileName;

        private String fileContent;
    }
}
