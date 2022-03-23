package vn.rts.customs.eclare.webapi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import vn.rts.customs.eclare.dto.body.electronic_doc.CtdtAttachedResponseDto;
import vn.rts.customs.eclare.dto.body.electronic_doc.CtdtDetailResponseDto;
import vn.rts.customs.eclare.request.ctdt.*;
import vn.rts.customs.eclare.request.search.KeySearchChungTuDinhKemTk;
import vn.rts.customs.eclare.service.ElectronicDocumentService;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare.RestEndpointEtcCustomsEClare;
import vn.rts.customs.lib.consts.ConstantEx.RestControllerDefault;
import vn.rts.customs.lib.exception.ResourceNotFoundException;

import javax.validation.Valid;

/**
 * @author ThanhNC - 28/09/2020
 * @see <a href=
 * "http://www.baeldung.com/spring-boot-start">http://www.baeldung.com/spring-boot-start</a>
 */

@Api(tags = { "ECLARE. Chứng từ đính kèm tờ khai xuất nhập" })
@RestController
@RequestMapping(value = RestControllerDefault.URL_V1 + RestEndpointEtcCustomsEClare.ELECTRONIC_DOCUMENT)
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ElectronicDocumentRestController {
	@Autowired
	ElectronicDocumentService electronicDocumentService;

    @ApiOperation(value = "Gửi ctdt giấy phép")
    @PostMapping(RestEndpointEtcCustomsEClare.CTDT_GIAY_PHEP)
    public ResponseEntity<String> guiCtdtGiayPhep(@RequestParam String toKhaiId, @RequestParam Integer isSigned, @RequestBody CtdtGiayPhepRequests request) throws ResourceNotFoundException {
        String res = electronicDocumentService.guiCtdtGiayPhep(request, toKhaiId, isSigned);
        return ResponseEntity.ok(res);
    }

    @ApiOperation(value = "Gửi ctdt Hóa đơn thương mại")
    @PostMapping(RestEndpointEtcCustomsEClare.HOA_DON_THUONG_MAI)
    public ResponseEntity<String> guiCtdtHoaDonThuongMai(@RequestParam String toKhaiId, @RequestParam Integer isSigned, @RequestBody CtdtHoaDonTmRequests request) {
        String res = electronicDocumentService.khaiHoaDonThuongMai(request, toKhaiId, isSigned);
        return ResponseEntity.ok(res);
    }

    @ApiOperation(value = "Gửi ctdt Chứng từ xác định hàng hóa nhập khẩu được áp dụng thuế suất thuế GTGT 5%")
    @PostMapping(RestEndpointEtcCustomsEClare.HHNK_THUE_GTGT_5_PT)
    public ResponseEntity<String> guiCtdtHHNKThueGTGT5PT(@RequestParam String toKhaiId, @RequestParam Integer isSigned, @RequestBody CtdtHhnkThueGtgt5PtRequests request) {
        String res = electronicDocumentService.guiCtdtHhnkThueGtgt5Pt(request, toKhaiId, isSigned);
        return ResponseEntity.ok(res);
    }

    @ApiOperation(value = "Gửi ctdt Chứng từ xuất xứ CO")
    @PostMapping(RestEndpointEtcCustomsEClare.CTDT_CO)
    public ResponseEntity<String> guiCtdtCO(@RequestParam String toKhaiId, @RequestParam Integer isSigned, @RequestBody CtdtCoRequests request) {
        String res = electronicDocumentService.guiCtdtCO(request, toKhaiId, isSigned);
        return ResponseEntity.ok(res);

    }

    @ApiOperation(value = "Gửi ctdt Bảng kê chi tiết hàng hóa")
    @PostMapping(RestEndpointEtcCustomsEClare.CTDT_BKHH)
    public ResponseEntity<String> guiCtdtBkhh(@RequestParam String toKhaiId, @RequestParam Integer isSigned, @RequestBody CtdtBkhhRequests request) {
        String res = electronicDocumentService.guiCtdtBkhh(request, toKhaiId, isSigned);
        return ResponseEntity.ok(res);
    }

    @ApiOperation(value = "Gửi vận tải đơn hoặc các chứng từ vận tải khác có giá trị tương đương")
    @PostMapping(RestEndpointEtcCustomsEClare.CTDT_VAN_DON)
    public ResponseEntity<String> guiCtdtVanDon(@RequestParam String toKhaiId, @RequestParam Integer isSigned, @RequestBody CtdtVanDonRequests request) {
        String res = electronicDocumentService.guiCtdtVanDon(request, toKhaiId, isSigned);
        return ResponseEntity.ok(res);
    }

    @ApiOperation(value = "Gửi ctdt giấy chứng nhận kiểm tra chuyên ngành")
    @PostMapping(RestEndpointEtcCustomsEClare.CTDT_GIAY_CNKTCN)
    public ResponseEntity<String> guiCtdtGiayCnktcn(@RequestParam String toKhaiId, @RequestParam Integer isSigned, @RequestBody CtdtGiayCnktcnRequests request) throws ResourceNotFoundException {
        String res = electronicDocumentService.guiCtdtGiayCnktcn(request, toKhaiId, isSigned);
        return ResponseEntity.ok(res);
    }

    @ApiOperation(value = "Gửi ctdt danh mục máy móc")
    @PostMapping(RestEndpointEtcCustomsEClare.CTDT_DM_MAY_MOC)
    public ResponseEntity<String> guiDmMayMoc(@RequestParam String toKhaiId, @RequestParam Integer isSigned, @RequestBody CtdtMmtbRequests request) {
        String res = electronicDocumentService.guiCtdtMmtb(request, toKhaiId, isSigned);
        return ResponseEntity.ok(res);
    }

    @ApiOperation(value = "Gửi ctdt Hợp đồng ủy thác")
    @PostMapping(RestEndpointEtcCustomsEClare.CTDT_HOP_DONG_UT)
    public ResponseEntity<String> guiCtdtHopDongUt(@RequestParam String toKhaiId, @RequestParam Integer isSigned, @RequestBody CtdtHopDongUtRequests request) {
        String res = electronicDocumentService.guiCtdtHopDongUt(request, toKhaiId, isSigned);
        return ResponseEntity.ok(res);
    }

    @ApiOperation(value = "Gửi ctdt Chứng từ chứng minh tổ chức, cá nhân đủ điều kiện xuất khẩu, nhập khẩu hàng hóa theo quy định của pháp luật về đầu tư")
    @PostMapping(RestEndpointEtcCustomsEClare.CTDT_CMTC)
    public ResponseEntity<String> guiCtdtCmtc(@RequestParam String toKhaiId, @RequestParam Integer isSigned, @RequestBody CtdtCmtcRequests request) {
        String res = electronicDocumentService.guiCtdtCmtc(request, toKhaiId, isSigned);
        return ResponseEntity.ok(res);
    }

    @ApiOperation(value = "Gửi ctdt tờ khai trị giá")
    @PostMapping(RestEndpointEtcCustomsEClare.CTDT_TKTG)
    public ResponseEntity<String> guiCtdtTktg(@RequestParam String toKhaiId, @RequestParam Integer isSigned, @RequestBody CtdtTktgRequests request) throws ResourceNotFoundException {
        String res = electronicDocumentService.guiCtdtTktg(request, toKhaiId, isSigned);
        return ResponseEntity.ok(res);
    }

    @ApiOperation(value = "Lấy danh sách chứng từ đính kèm")
    @GetMapping(RestEndpointEtcCustomsEClare.CTDK_TK)
    public Page<CtdtAttachedResponseDto> selectCtdkTkBymauTkHqId(@ModelAttribute @Valid KeySearchChungTuDinhKemTk objInput) {
        return electronicDocumentService.selectCtdkTkBymauTkHqId(objInput);
    }

    @ApiOperation(value = "Download")
    @GetMapping(RestEndpointEtcCustomsEClare.DL_FILE)
    public ResponseEntity<byte[]> downloadFileV6Demo(@Valid @RequestParam String sid) {
        return electronicDocumentService.downloadFileV6Demo(sid);
    }

    @ApiOperation(value = "lấy chi tiết chứng từ đính kèm")
    @GetMapping(RestEndpointEtcCustomsEClare.DETAIL_CTDK_TK)
    public CtdtDetailResponseDto selectDetailCtdkTkById(@Valid @RequestParam String ctdtId) throws ResourceNotFoundException {
        return electronicDocumentService.selectDetailCtdkTkById(ctdtId);
    }
}
