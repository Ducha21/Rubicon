package vn.rts.customs.eclare.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import vn.rts.customs.lib.enums.IEnumEx;
import vn.rts.customs.lib.exception.EtcCustomsRuntimeException;

public class VnaccsConvert {

	private VnaccsConvert() {
		// not called
	}

	public static boolean stringIsEmpty(String sInput) {
		sInput = StringUtils.trimToEmpty(sInput);
		return StringUtils.isEmpty(sInput);
	}
	
	public static boolean isDateWithFormat(String dateStr, String dateFormat) {
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

	public enum EFormatDateTime implements IEnumEx {
		YYYYMMDD("yyyyMMdd"), YYYY_MM_DD("yyyy-MM-dd"), DDMMYYYY("ddMMyyyy"), DD_MM_YYYY("dd-MM-yyyy"), YYYY_MM_DD_H("yyyy-MM-dd HH:mm:ss");

		private String sValueEnum;

		private EFormatDateTime(String sValueEnum) {
			this.sValueEnum = sValueEnum;
		}

		@Override
		public String getValueEnum() {
			return this.sValueEnum;
		}
	}

	public static Date string2Date(String sInput, EFormatDateTime eFormatDateTime) {
		return string2Date(sInput, eFormatDateTime.getValueEnum());
	}

	public static Date string2Date(String sInput, String sFormat) {
		try {
			if (stringIsEmpty(sInput))
				return null;
			return new SimpleDateFormat(sFormat).parse(StringUtils.trimToEmpty(sInput));
		} catch (ParseException e) {
			throw new EtcCustomsRuntimeException(e);
		}
	}

	public static Integer string2Integer(String sInput) {
		if (stringIsEmpty(sInput))
			return null;
		return Integer.parseInt(StringUtils.trimToEmpty(sInput));
	}

	public static Long string2Long(String sInput) {
		if (stringIsEmpty(sInput))
			return null;
		return Long.parseLong(StringUtils.trimToEmpty(sInput));
	}

	public static BigDecimal string2BigDecimal(String sInput) {
		if (stringIsEmpty(sInput))
			return null;
		return new BigDecimal(StringUtils.trimToEmpty(sInput));
	}
	
	public static String date2String(Date dInput, String sFormat) {
		if(StringUtils.isEmpty(sFormat) || dInput == null)
			return null;
		DateFormat dateFormat = new SimpleDateFormat(sFormat);  
		return dateFormat.format(dInput);  
	}
	
	public static String stringDate2String(String sInput, String sFormat) {
		if(StringUtils.isEmpty(sFormat) || sInput == null)
			return null;
		DateFormat dateInFormat = new SimpleDateFormat(EFormatDateTime.YYYY_MM_DD_H.getValueEnum()); 
		Date date;
		try {
			date = dateInFormat.parse(sInput);
			DateFormat dateOutFormat = new SimpleDateFormat(sFormat);  
			return dateOutFormat.format(date); 
		} catch (ParseException e) {
			return sInput;
		}
	}

	public static Date getCurrentDateTime() {
		Date currentDate = new Date();
		return currentDate;
	}

}
