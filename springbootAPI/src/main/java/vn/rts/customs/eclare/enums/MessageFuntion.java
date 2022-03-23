package vn.rts.customs.eclare.enums;

public enum MessageFuntion {
	HUY("1"), //
	SUA("5"), //
	KHAI_BAO("8"), //
	PHAN_LUONG("10"), //
	CHUA_XY_LY("12"), //
	HOI_THEO_REF_ID("13"), //
	HOI_THEO_MA_DN("15"), //
	DE_NGHI("16"), //
	TU_CHOI("271"), //
	LOI_NGHIEP_VU("272"), //
	TU_CHOI_PHE_DUYET("273"), //
	CAP_SO_TIEP_NHAN("29"), //
	CAP_SO_TO_KHAI("30"), //
	CHO_PHE_DUYET("31"), //
	CHAP_NHAN_TT("32"), //
	TB_THUE("33"), //
	THU_TUC_HQ("34"), //
	THONG_QUAN("35"), //
	THUC_XUAT("36");//

	private String value;

	public String getValue() {
		return value;
	}

	private MessageFuntion(String value) {
		this.value = value;
	}
}
