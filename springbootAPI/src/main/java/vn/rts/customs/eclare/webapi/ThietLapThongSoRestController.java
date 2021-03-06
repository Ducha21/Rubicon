package vn.rts.customs.eclare.webapi;

import javax.validation.Valid;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import io.swagger.annotations.ApiOperation;
import vn.rts.customs.eclare.entity.CauHinhHeThongEntity;
import vn.rts.customs.eclare.entity.ChuKySoEntity;
import vn.rts.customs.eclare.entity.ThietLapThongSoEntity;
import vn.rts.customs.eclare.request.CauHinhHeThongRequest;
import vn.rts.customs.eclare.request.search.KeySearchCauHinhHeThong;
import vn.rts.customs.eclare.request.search.KeySearchDoanhNghiep;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.eclare.request.search.KeySearchSystemSettingDto;
import vn.rts.customs.eclare.service.ThietLapThongSoService;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare.RestEndpointEtcCustomsEClare;
import vn.rts.customs.lib.consts.ConstantEx;
import vn.rts.customs.lib.dto.PageableResponse;
import vn.rts.customs.lib.exception.ResourceNotFoundException;
import vn.rts.customs.lib.key.SequenceKey;
import vn.rts.customs.lib.webapi.WebApiBaseRestControllerImplWithoutMapper;

import java.util.List;
import java.util.Optional;

/**
 * @author CCS4EO_DEV
 */

@CrossOrigin
@RestController
 @RequestMapping(value = ConstantEx.RestControllerDefault.URL_V1 + RestEndpointEtcCustomsEClare.SYSTEM)
//@RequestMapping(value = "swagger-resources/"+ RestEndpointEtcCustomsEClare.SYSTEM)
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ThietLapThongSoRestController extends
        WebApiBaseRestControllerImplWithoutMapper<ThietLapThongSoEntity, Long, SequenceKey, ThietLapThongSoService, KeySearchListObj> {

    @Autowired
    private ThietLapThongSoService thietLapThongSoService;

    @ApiOperation(value = "M?? t??? ?? ngh??a API (danh s??ch c??c tham s??? n???u c??)")
	@GetMapping(RestEndpointEtcCustomsEClare.DM_THIET_LAP_THONG_SO)
	public PageableResponse<ThietLapThongSoEntity> searchThietLapThongSo(@Valid @ModelAttribute KeySearchSystemSettingDto objInput) {
		return this.getObjService().search(objInput);
	}
   
    @ApiOperation(value = "M?? t??? ?? ngh??a API (Chi tiet Thiet lap thong so)")
	@GetMapping(RestEndpointEtcCustomsEClare.THIET_LAP_THONG_SO)
    public ThietLapThongSoEntity selectThietLapThongSoDetail(@Valid @RequestParam String doanhNghiepId) {
    	Optional<ThietLapThongSoEntity> ent = thietLapThongSoService.findByDoanhNghiepId(doanhNghiepId);
		return ent.orElse(null);
	}
    
    @ApiOperation(value = "M?? t??? ?? ngh??a API (Insert Thiet lap thong so)")
	@PostMapping(RestEndpointEtcCustomsEClare.THIET_LAP_THONG_SO)
    public ThietLapThongSoEntity insertThietLapThongSo(@RequestBody ThietLapThongSoEntity thietLapThongSoEntity) {
    	return this.thietLapThongSoService.insertItem(thietLapThongSoEntity);
    }
    
    @ApiOperation(value = "M?? t??? ?? ngh??a API (Update Thiet lap thong so)")
   	@PutMapping(RestEndpointEtcCustomsEClare.THIET_LAP_THONG_SO)
       public ThietLapThongSoEntity updateThietLapThongSo(@RequestBody ThietLapThongSoEntity thietLapThongSoEntity) {
       	return this.thietLapThongSoService.updateItem(thietLapThongSoEntity);
   }

	@ApiOperation(value = "T???o ch???ng th?? s???")
	@PostMapping(RestEndpointEtcCustomsEClare.CHU_KY_SO)
	public ChuKySoEntity createChuKySo(@RequestBody @Valid ChuKySoEntity chuKySoEntity) {
		return thietLapThongSoService.createChuKySo(chuKySoEntity);
	}

	@ApiOperation(value = "S???a ch???ng th?? s???")
	@PatchMapping(RestEndpointEtcCustomsEClare.CHU_KY_SO)
	public ChuKySoEntity updateChuKySo(@RequestParam(required = true) Long chuKySoId,
									   @RequestBody @Valid ChuKySoEntity chuKySoEntity) throws ResourceNotFoundException {
		return thietLapThongSoService.updateChuKySo(chuKySoId, chuKySoEntity);
	}

	@ApiOperation(value = "L???y ch???ng th?? s??? theo m?? doanh nghi???p")
	@GetMapping(RestEndpointEtcCustomsEClare.CHU_KY_SO)
	public List<ChuKySoEntity> getChuKySo(@RequestParam(required = true) String maDoanhNghiep) throws ResourceNotFoundException {
		return thietLapThongSoService.getChuKySo(maDoanhNghiep);
	}

	//-------CAU HINH HE THONG--------//

	@ApiOperation(value = "T???o c???u h??nh h??? th???ng")
	@PostMapping(RestEndpointEtcCustomsEClare.CAU_HINH_HE_THONG)
	public CauHinhHeThongEntity createCauHinhHeThong(@RequestBody @Valid CauHinhHeThongEntity cauHinhHeThongEntity) {
		return thietLapThongSoService.createCauHinhHeThong(cauHinhHeThongEntity);
	}

	@ApiOperation(value = "X??a c???u h??nh h??? th???ng")
	@DeleteMapping(RestEndpointEtcCustomsEClare.CAU_HINH_HE_THONG)
	public void deleteCauHinhHeThong(@RequestBody CauHinhHeThongRequest request) throws ResourceNotFoundException {
    	thietLapThongSoService.xoaCauHinhHeThong(request.getCauHinhId());
	}

	@ApiOperation(value = "S???a c???u h??nh h??? th???ng")
	@PatchMapping(RestEndpointEtcCustomsEClare.CAU_HINH_HE_THONG)
	public CauHinhHeThongEntity updateCauHinhHeThong(@RequestParam(required = true) Long cauHinhId,
													 @RequestBody @Valid CauHinhHeThongEntity cauHinhHeThongEntity) throws ResourceNotFoundException {
		return thietLapThongSoService.updateCauHinhHeThong(cauHinhId, cauHinhHeThongEntity);
	}

	@ApiOperation(value = "search th??ng tin c???u h??nh")
	@GetMapping(RestEndpointEtcCustomsEClare.CAU_HINH_HE_THONG)
	public PageableResponse<CauHinhHeThongEntity> searchCauHinh(@ModelAttribute KeySearchCauHinhHeThong objInput) {
		return thietLapThongSoService.searchCauHinhHeThong(objInput);
	}

	@ApiOperation(value = "Ki???m tra k???t n???i Client-Server")
	@GetMapping(RestEndpointEtcCustomsEClare.HEALTH_CHECK)
	public String healthCheck(){
		return "OK";
	}

	@ApiOperation(value = "Upgrade company")
	@PostMapping("upgrade")
	public ResponseEntity<String> upgradeCompany(@RequestParam String orgCode, @RequestBody String request) throws NotFoundException {
		return ResponseEntity.ok(thietLapThongSoService.upgradeCompany(orgCode, request));
	}
}
    