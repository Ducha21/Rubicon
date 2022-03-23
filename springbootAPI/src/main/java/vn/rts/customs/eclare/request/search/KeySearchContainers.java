package vn.rts.customs.eclare.request.search;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class KeySearchContainers extends SearchListBase implements Serializable {
	private String toKhaiId;

	private String tkContainerId;

	private String documentType;

	private String soTiepNhan;

	private Date ngayTiepNhan;

	private Date ngayKhaiBao;

	private String ghiChu;
}
