package vn.rts.customs.eclare.configuration.dbrouting;

import org.castor.core.util.Assert;

public class DataSourceContextHolder {

	private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

	public static void setOrgCodeContext(String orgCode) {
		threadLocal.set(orgCode);
	}

	public static String getOrgCodeContext() {
		return threadLocal.get();
	}

	public static void clearBranchContext() {
		threadLocal.remove();
	}
}
