/**
 * 
 */
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
 * @author ThanhNC - 22/04/2021
 * @see <a href=
 *      "https://www.objectdb.com/java/jpa/entity/id">https://www.objectdb.com/java/jpa/entity/id</a>
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@Embeddable
public class DeclarationsKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "ID")
	@NotNull
	private Long id;

	public DeclarationsKey cloneNotRef() {
		return SerializationUtils.clone(this);
	}
}
