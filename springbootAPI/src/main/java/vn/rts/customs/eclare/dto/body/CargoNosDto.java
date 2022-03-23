/**
 * 
 */
package vn.rts.customs.eclare.dto.body;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ThanhNC - 2021-04-23
 *
 */

@NoArgsConstructor
@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class CargoNosDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("billNo")
	@Size(max = 35)
	private String billNo;

	@JsonProperty("billDate")
	private String billDate;

	@JsonProperty("cargoCtrlNo")
	@Size(max = 50)
	private String cargoCtrlNo;

	@JsonProperty("cargoPiece")
	@Size(max = 8)
	private String cargoPiece;

	@JsonProperty("pieceUnitCode")
	@Size(max = 3)
	private String pieceUnitCode;

	@JsonProperty("cargoWeightGross")
	private String cargoWeightGross;

	@JsonProperty("weightUnitCode")
	@Size(max = 3)
	private String weightUnitCode;

	public CargoNosDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}

}
