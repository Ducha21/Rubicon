/**
 * 
 */
package vn.rts.customs.eclare.dto.body;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author ThanhNC - 2021-04-23
 *
 */

@NoArgsConstructor
@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class ContainerNoContentDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("documentType")
	@Size(max = 3)
	private String documentType;
	
	@JsonProperty("declarantChannel")
	@Size(max = 1)
	private String declarantChannel;

	@JsonProperty("refId")
	@Size(max = 36)
	private String refId;

	@NotNull
	@JsonProperty("issueDate")
	private String issueDate;

	@JsonProperty("function")
	private String function;

	@JsonProperty("issueLocation")
	@Size(max = 60)
	private String issueLocation;

	@JsonProperty("refCustomsId")
	private String refCustomsId;

	@NotNull
	@Size(max = 10)
	private String acceptanceDate;

	@NotNull
	@Size(max = 6)
	private String acceptanceOffice;
	
	@JsonProperty("declarantCode")
	@Size(max = 13)
	private String declarantCode;
	
	@JsonProperty("declarantName")
	@Size(max = 200)
	private String declarantName;
	
	@JsonProperty("importerCode")
	@Size(max = 255)
	private String importerCode;
	
	@JsonProperty("importerName")
	@Size(max = 17)
	private String importerName;

	@JsonProperty("importerAddress")
	@Size(max = 255)
	private String importerAddress;

	@JsonProperty("declarationNo")
	@Size(max = 12)
	private String declarationNo;

	@JsonProperty("registeredDate")
	@Size(max = 19)
	private String registeredDate;

	@JsonProperty("declarationKindCode")
	@Size(max = 10)
	private String declarationKindCode;

	@JsonProperty("declarationOffice")
	@Size(max = 6)
	private String declarationOffice;

	@JsonProperty("containerNos")
	private List<ContainerNosDto> containerNos;

	public ContainerNoContentDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}

}