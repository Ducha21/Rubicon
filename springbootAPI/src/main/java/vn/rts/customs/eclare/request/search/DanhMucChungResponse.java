package vn.rts.customs.eclare.request.search;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class DanhMucChungResponse<T> {
	private List<T> data;

	public DanhMucChungResponse(List<T> data) {
		this.data = data;
	}
}
