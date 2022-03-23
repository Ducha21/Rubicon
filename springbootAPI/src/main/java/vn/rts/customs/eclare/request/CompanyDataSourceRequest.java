package vn.rts.customs.eclare.request;

import lombok.Data;


@Data
public class CompanyDataSourceRequest {
	private Integer isInit;
	private String[] orgCodes;
}
