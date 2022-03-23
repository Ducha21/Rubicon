package vn.rts.customs.eclare.key;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ThangNM11 - 28/09/2020
 * @see <a href=
 *      "https://www.objectdb.com/java/jpa/entity/id">https://www.objectdb.com/java/jpa/entity/id</a>
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@Embeddable
public class DemoDecKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "PK; Reference")
	@NotNull
	private String ref;

	public DemoDecKey cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}