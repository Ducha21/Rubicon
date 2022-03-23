package vn.rts.customs.eclare.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import vn.rts.customs.eclare.dto.body.SignatureDto;
import vn.rts.customs.eclare.dto.header.HeaderSendDto;

/**
 * @author ThanhNC - 2021-04-23
 *
 */

@NoArgsConstructor
@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public abstract class MsgSendDto<B> implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("header")
	private HeaderSendDto header;

	@JsonProperty("body")
	private transient B body;
	
	@JsonProperty("signature")
	private SignatureDto signature;

}
