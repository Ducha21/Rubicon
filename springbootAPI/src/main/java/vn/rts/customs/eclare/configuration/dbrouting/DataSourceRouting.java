package vn.rts.customs.eclare.configuration.dbrouting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DataSourceRouting extends AbstractRoutingDataSource {

	@Value("${spring.datasource.username}")
	private String userName;

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getOrgCodeContext();
	}

	public void initDatasource(DataSource empDataSource,
							   DataSource emp2DataSource) {
		String orgCode = DataSourceContextHolder.getOrgCodeContext();
		Map<Object, Object> dataSourceMap = new HashMap<>();
		dataSourceMap.put(userName, empDataSource);
		if (orgCode != null && !orgCode.isEmpty()) {
			dataSourceMap.put(orgCode, emp2DataSource);
		}
		this.setTargetDataSources(dataSourceMap);
		this.setDefaultTargetDataSource(empDataSource);
	}
}
