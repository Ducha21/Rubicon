package vn.rts.customs.eclare.enums;

public enum MessageType {
    TK_NHAP_KHAU("929"), //
    TK_XUAT_KHAU("930"), //
    CTDT_GIAY_PHEP("1308"), //
    CTDT_HOA_DON_TM("1310"), //
    CTDT_CO("1311"), //
    CTDT_VAN_DON("1312"), //
    CTDT_BKHH("1315"), //
    CTDT_GIAY_CNKTCN("1316"), //
    CTDT_CMTC("1317"), //
    CTDT_HOP_DONG_UT("1318"), //
    CTDT_TKTG("1319"), //
    CTDT_MMTB("1320"), //
    CTDT_CTAT_5_PT("1321"), //
    SO_DINH_DANH("219"), //
    CONTAINER_KB("303"), //
    CONTAINER_YC("304");//

    private String value;

    public String getValue() {
        return value;
    }

    private MessageType(String value) {
        this.value = value;
    }
}
