package vn.rts.customs.eclare.webapi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import vn.rts.customs.eclare.configuration.dbrouting.DataSourceConfig;
import vn.rts.customs.eclare.request.CompanyDataSourceRequest;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare.RestEndpointEtcCustomsEClare;
import vn.rts.customs.lib.exception.ResourceNotFoundException;

import java.sql.SQLException;

/**
 * @author ThanhNC - 28/09/2020
 * @see <a href=
 * "http://www.baeldung.com/spring-boot-start">http://www.baeldung.com/spring-boot-start</a>
 */
@RestController
@RequestMapping(value = "swagger-resources/" + RestEndpointEtcCustomsEClare.TO_KHAI)
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Api(tags = { "" })
public class DeclarationPublicRestController {
    @Autowired
    private DataSourceConfig dataSourceConfig;

    @ApiOperation(value = "Config data source priority org")
    @PostMapping("/config-datasource")
    public void configDataSource(@RequestBody CompanyDataSourceRequest request) throws ResourceNotFoundException, SQLException {
        dataSourceConfig.buildDataSource(request.getOrgCodes());
    }
}
