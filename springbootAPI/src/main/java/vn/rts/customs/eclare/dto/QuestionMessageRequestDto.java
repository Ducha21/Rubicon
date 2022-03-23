/**
 * 
 */
package vn.rts.customs.eclare.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vn.rts.customs.eclare.dto.body.BodyDto;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class QuestionMessageRequestDto extends MsgSendDto<BodyDto> implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("clientCustomField")
	private Long clientCustomField;
}
