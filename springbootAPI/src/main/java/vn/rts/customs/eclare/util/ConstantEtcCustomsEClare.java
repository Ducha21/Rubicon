package vn.rts.customs.eclare.util;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class ConstantEtcCustomsEClare {

	private ConstantEtcCustomsEClare() {
		// not called
	}
	
	public static final String APPLICATION_VERSION = "1.0";
	public static final String APPLICATION_NAME = "HQDT";
	
	public static class ExceptionMessage {
		
		public static final String INPUT_NULL_OR_EMPTY = "Request null or empty";
		public static final String NOT_FOUND = "Không tìm thấy dữ liệu";
		public static final String ERROR_STN = "Không thể xóa tờ khai có số tiếp nhận ";
	}

	public static class EntityTableEtcCustomsEClare {
		public static final String DINH_DANH_HANG_HOA_TK = "DINH_DANH_HANG_HOA_TK";
		public static final String GOODS_ITEMS = "GOODS_ITEMS";
		public static final String DM_HANG_HOA_TK = "DM_HANG_HOA_TK";
		public static final String MAU_TO_KHAI_HQ = "MAU_TO_KHAI_HQ";
		public static final String MAU_TO_KHAI_HQ_TEMPLATE = "MAU_TO_KHAI_HQ_TEMPLATE";
		public static final String THUE_KHOAN_THU_KHAC = "THUE_KHOAN_THU_KHAC";
		public static final String TK_VAN_DON = "TK_VAN_DON";
		public static final String TT_TRUNG_CHUYEN = "TT_TRUNG_CHUYEN";
		public static final String CHI_THI_HQ_TK = "CHI_THI_HQ_TK";
		public static final String DOANH_NGHIEP = "DOANH_NGHIEP";
		public static final String THONG_TIN_XU_LY_TK = "THONG_TIN_XU_LY_TK";
		
		public static final String GIAY_PHEP_TK = "GIAY_PHEP_TK";
		public static final String HUY_TO_KHAI = "HUY_TO_KHAI";
		public static final String KHOAN_DIEU_CHINH_TK = "KHOAN_DIEU_CHINH_TK";
		public static final String MAU_TT_TK_HQ_YEU_CAU = "MAU_TT_TK_HQ_YEU_CAU";
		public static final String MST_DATA = "MST_DATA";
		public static final String NGUOI_DUNG = "NGUOI_DUNG";
		public static final String SO_DINH_KEM_KBDT = "SO_DINH_KEM_KBDT";
		public static final String CO_HANG_HOA_TK = "CO_HANG_HOA_TK";
		public static final String DIA_DIEM_XEP_HANG_TK = "DIA_DIEM_XEP_HANG_TK";
		public static final String CAU_HINH_HE_THONG = "CAU_HINH_HE_THONG";
		public static final String CHU_KY_SO = "CHU_KY_SO";
		public static final String TK_CONTAINER = "TK_CONTAINER";
		// Thông tin dùng chung
		public static final String DM_LOAI_HINH = "DM_LOAI_HINH";
		public static final String DM_CHUC_NANG = "DM_CHUC_NANG";
		public static final String DM_DIA_DIEM_LUU_KHO_HANG = "DM_DIA_DIEM_LUU_KHO_HANG";
		public static final String DM_DIEM_NEO_DAU = "DM_DIEM_NEO_DAU";
		public static final String DM_DIEM_XEP_DO = "DM_DIEM_XEP_DO";
		public static final String DM_DON_VI_TINH = "DM_DON_VI_TINH";
		public static final String DM_LOAI_KIEN = "DM_LOAI_KIEN";
		public static final String DM_MA_AP_DUNG_THUE_KHOAN_THU_KHAC = "DM_MA_AP_DUNG_THUE_KHOAN_THU_KHAC";
		public static final String DM_MA_GIAM_THUE_KHOAN_THU_KHAC = "DM_MA_GIAM_THUE_KHOAN_THU_KHAC";
		public static final String DM_MA_GIAM_THUE_XUAT_NHAP_KHAU = "DM_MA_GIAM_THUE_XUAT_NHAP_KHAU";
		public static final String DM_NUOC = "DM_NUOC";
		public static final String DM_TIEN_TE = "DM_TIEN_TE";
		public static final String DM_VAN_BAN_PHAP_QUY_KHAC = "DM_VAN_BAN_PHAP_QUY_KHAC";
		public static final String DM_HANG_HOA = "DM_HANG_HOA";
		public static final String CHUNG_TU_DINH_KEM_TK = "CHUNG_TU_DINH_KEM_TK";
		public static final String CTDT_FILE_DINH_KEM = "CTDT_FILE_DINH_KEM";
		public static final String CTDT_GIAY_PHEP = "CTDT_GIAY_PHEP";
		public static final String CTDT_HOA_DON_TM = "CTDT_HOA_DON_TM";
		public static final String CTDT_CTAT_5_PHAN_TRAM = "CTDT_CTAT_5_PHAN_TRAM";
		public static final String DM_NGAN_HANG = "DM_NGAN_HANG";
		public static final String CTDT_CO = "CTDT_CO";
		public static final String CTDT_VAN_DON = "CTDT_VAN_DON";
		public static final String CTDT_VAN_DON_CONTAINER = "CTDT_VAN_DON_CONTAINER";
		public static final String CTDT_VAN_DON_NHANH = "CTDT_VAN_DON_NHANH";
		public static final String CTDT_BKHH = "CTDT_BKHH";
		public static final String CTDT_GIAY_CNKTCN = "CTDT_GIAY_CNKTCN";
		public static final String CTDT_MMTB = "CTDT_MMTB";
		public static final String CTDT_HOP_DONG_UT = "CTDT_HOP_DONG_UT";
		public static final String CTDT_CMTC = "CTDT_CMTC";
		public static final String CTDT_TKTG = "CTDT_TKTG";
		public static final String THIET_LAP_THONG_SO = "THIET_LAP_THONG_SO";
		

		public static final Map<String, String> mapEntitytoTableFullName = ImmutableMap.<String, String>builder()
				.put(DM_HANG_HOA_TK, "")
				.put(MAU_TO_KHAI_HQ, "")
				.put(THUE_KHOAN_THU_KHAC, "")
				.put(TK_VAN_DON, "")
				.put(TT_TRUNG_CHUYEN, "")
				.put(CHI_THI_HQ_TK, "")
				.put(DOANH_NGHIEP, "")
				.put(GIAY_PHEP_TK, "")
				.put(HUY_TO_KHAI, "")
				.put(KHOAN_DIEU_CHINH_TK, "")
				.put(MAU_TT_TK_HQ_YEU_CAU, "")
				.put(MST_DATA, "")
				.put(NGUOI_DUNG, "")
				.put(SO_DINH_KEM_KBDT, "")
				.build();

		private EntityTableEtcCustomsEClare() {
			// not called
		}

		public static String getEntityTableFullName(String sInput) {
			String strReturn = sInput;
			for (Map.Entry<String, String> entry : mapEntitytoTableFullName.entrySet()) {
				if (entry.getKey().equalsIgnoreCase(sInput)) {
					strReturn = entry.getValue();
				}
			}
			return strReturn;
		}
	}

	public class RestEndpointEtcCustomsEClare {

		public static final String ELECTRONIC_DOCUMENT = "ctdt/";
		public static final String DECLARATIONS = "declarations/";
		public static final String TO_KHAI = "to-khai/";
		public static final String SO_TIEP_NHAN = "so-tiep-nhan";
		public static final String SO_TK = "so-to-khai";
		public static final String DANH_MUC = "danh-muc/";
		public static final String TKN = "tkn";
		public static final String KHAI_TRUOC_TKN = "khai-truoc-tkn";
		public static final String TKX = "tkx";
		public static final String KHAI_TRUOC_TKX = "khai-truoc-tkx";
		public static final String TKS = "tks/";
		public static final String NK = "nk";
		public static final String XK = "xk";
		public static final String MAU_TK = "mau-tk";
		public static final String SUA_TKN = "sua-tkn";
		public static final String SUA_TKX = "sua-tkx";
		public static final String TKH = "tkh";
		public static final String HOI_TTTK = "hoi-tttk";
		public static final String TK = "tk";
		public static final String MA_LOAI_HINH = "loai-hinh";
		public static final String HAI_QUAN = "hai-quan";
		public static final String DOI = "doi";
		public static final String CHI_THI_HQ = "chi-thi-hq";
		public static final String CHUC_NANG = "chuc-nang";
		public static final String DIA_DIEM_LUU_KHO_HANG = "dia-diem-luu-kho-hang";
		public static final String DIEM_NEO_DAU = "diem-neo-dau";
		public static final String DON_VI_TINH = "don-vi-tinh";
		public static final String LOAI_KIEN = "loai-kien";
		public static final String MA_AP_DUNG_THUE_KHOAN_THU_KHAC = "ma-ap-dung-thue-khoan-thu-khac";
		public static final String MA_GIAM_THUE_KHOAN_THU_KHAC = "ma-giam-thue-khoan-thu-khac";
		public static final String MA_GIAM_THUE_XUAT_NHAP_KHAU = "ma-giam-thue-xuat-nhap-khau";
		public static final String NUOC = "nuoc";
		public static final String TIEN_TE = "tien-te";
		public static final String CAU_HINH_HE_THONG = "cau-hinh-he-thong";
		public static final String CHU_KY_SO = "chu-ky-so";
		public static final String VAN_BAN_PHAP_QUY_KHAC = "van-ban-phap-quy-khac";
		public static final String HANG_HOA = "hang-hoa";
		public static final String CTDT_GIAY_PHEP = "giay-phep";
		public static final String HOA_DON_THUONG_MAI = "hoa-don-thuong-mai";
		public static final String NGAN_HANG = "ngan-hang";
		public static final String HANG_HOA_TK = "hang-hoa-tk";
		public static final String CTDT_CO = "co";
		public static final String HHNK_THUE_GTGT_5_PT = "hang-nhap-khau-thue-gtgt";
		public static final String THONG_TIN_XU_LY_TK = "thong-tin-xu-ly-tk";
		public static final String CTDT_BKHH = "bkhh";
		public static final String CTDK_TK = "chung-tu-kinh-kem-to-khai";
		public static final String GIAY_PHEP = "giay-phep";
		public static final String CTDT_VAN_DON = "van-don";
		public static final String CTDT_GIAY_CNKTCN = "giay-cnktcn";
		public static final String CTDT_DM_MAY_MOC = "may-moc";
		public static final String CTDT_HOP_DONG_UT = "hop-dong-ut";
		public static final String CTDT_CMTC = "cmtc";
		public static final String DIEM_XEP_DO = "diem-xep-do";
		public static final String CTDT_TKTG = "tktg";
		public static final String DL_FILE = "download-file";
		public static final String DETAIL_CTDK_TK = "detail-ctdk-tk";
		public static final String DM_THIET_LAP_THONG_SO = "dm-thiet-lap-thong-so";
		public static final String THIET_LAP_THONG_SO = "thiet-lap-thong-so";
		public static final String NGUOI_DUNG = "nguoi-dung";
		public static final String DOANH_NGHIEP = "doanh-nghiep";
		public static final String DOANH_NGHIEP_SU_DUNG_HE_THONG = "doanh-nghiep-su-dung-he-thong";
		public static final String HEALTH_CHECK = "health-check";
		public static final String DS_NGUOI_DUNG = "ds-nguoi-dung";
		public static final String SYSTEM = "he-thong/";
		public static final String SO_DINH_DOANH = "so-dinh-danh";
		public static final String CONTAINERS = "containers";
		public static final String DS_CONTAINERS = "ds-containers";
		public static final String EXPORT_TKN = "export-tkn";
		public static final String EXPORT_TKX = "export-tkx";

		private RestEndpointEtcCustomsEClare() {
			// not called
		}
	}
}
