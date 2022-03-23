package vn.rts.customs.eclare.dto.body;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.SerializationUtils;

@NoArgsConstructor
@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class CtdtBodyDto<E> implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonProperty("content")
	private E content;

	public CtdtBodyDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
