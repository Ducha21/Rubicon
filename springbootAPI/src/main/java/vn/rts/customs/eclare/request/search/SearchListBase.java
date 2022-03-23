package vn.rts.customs.eclare.request.search;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class SearchListBase {
	@ApiParam(value = "Page")
	private int page = 0;
	@ApiParam(value = "Size")
	private int size = 10;
	
	public int getPage() {
		return page;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getPageBegin() {
		return page * size + 1;
	}
	
	public int getPageEnd() {
		return page * size + size ;
	}
}
