/**
 * 
 */
package vn.rts.customs.eclare.dto.header;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare;
import vn.rts.customs.eclare.util.VnaccsConvert;
import vn.rts.customs.eclare.util.VnaccsConvert.EFormatDateTime;

/**
 * @author ThanhNC - 2021-04-23
 *
 */
@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class SendApplicationDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("name")
	@Size(max = 255)
	private String name;

	@JsonProperty("version")
	private String version;

	@JsonProperty("companyName")
	@NotNull
	@Size(max = 255)
	private String companyName;

	@JsonProperty("companyIdentity")
	@NotNull
	@Size(max = 50)
	private String companyIdentity;

	@JsonProperty("createMessageIssue")
	@Size(max = 19)
	private String createMessageIssue;

	public SendApplicationDto cloneNotRef() {
		return SerializationUtils.clone(this);
	}

	public SendApplicationDto() {
		this.name = ConstantEtcCustomsEClare.APPLICATION_NAME;
		this.version = ConstantEtcCustomsEClare.APPLICATION_VERSION;
		this.createMessageIssue = VnaccsConvert.date2String(new Date(), EFormatDateTime.YYYY_MM_DD_H.getValueEnum());
	}
	
	

}