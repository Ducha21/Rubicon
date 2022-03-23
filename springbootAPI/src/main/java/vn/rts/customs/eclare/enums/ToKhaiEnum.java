package vn.rts.customs.eclare.enums;

public enum ToKhaiEnum {
	KHAI_TRUOC(0), //
	KHAI_CHINH_THUC(1); //
	
	private int value;
	
	public int getValue() {
		return value;
	}

	private ToKhaiEnum(int value) {
		this.value = value;
	}
}
