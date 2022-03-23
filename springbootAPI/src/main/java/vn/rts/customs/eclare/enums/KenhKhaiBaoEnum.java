package vn.rts.customs.eclare.enums;

public enum KenhKhaiBaoEnum {
	DN_TU_KHAI("1"), //
	DAI_LY("2");//

	private String value;

	public String getValue() {
		return value;
	}

	KenhKhaiBaoEnum(String value) {
		this.value = value;
	}
}
