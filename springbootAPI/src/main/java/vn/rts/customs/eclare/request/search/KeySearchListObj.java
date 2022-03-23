package vn.rts.customs.eclare.request.search;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vn.rts.customs.lib.dto.KeySearchListImpl;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class KeySearchListObj extends KeySearchListImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	// ========= BEGIN String ==========

	private String content;

	private String description;

	// ========= END String ==========

	// ========= BEGIN Integer/int ==========
	private Integer status;
	// ========= END Integer/int ==========

	// ========= BEGIN Long/long ==========
	// ========= END Long/long ==========

	// ========= BEGIN Date ==========
	// ========= END Date ==========

	public String getContent() {
		return StringUtils.trim(this.content);
	}

	public String getDescription() {
		this.description = StringUtils.trim(this.description);
		return this.description;
	}
}
