package vn.rts.customs.eclare.webapi;

import java.io.FileNotFoundException;
import java.util.List;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import io.swagger.annotations.ApiOperation;
import vn.rts.customs.eclare.dto.TkdnMsgDto;
import vn.rts.customs.eclare.entity.*;
import vn.rts.customs.eclare.mapper.IDeclarationMapper;
import vn.rts.customs.eclare.request.*;
import vn.rts.customs.eclare.request.search.*;
import vn.rts.customs.eclare.service.DeclarationDetailService;
import vn.rts.customs.eclare.service.DeclarationsService;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare.RestEndpointEtcCustomsEClare;
import vn.rts.customs.lib.consts.ConstantEx.RestControllerDefault;
import vn.rts.customs.lib.dto.PageableResponse;
import vn.rts.customs.lib.exception.ResourceNotFoundException;

/**
 * @author ThanhNC - 28/09/2020
 * @see <a href=
 * "http://www.baeldung.com/spring-boot-start">http://www.baeldung.com/spring-boot-start</a>
 */
@Api(tags = { "ECLARE. Khai báo tờ khai xuất nhập khẩu" })
@RestController
@RequestMapping(value = RestControllerDefault.URL_V1 + RestEndpointEtcCustomsEClare.TO_KHAI)
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DeclarationsRestController {

    @Autowired
    private DeclarationsService declarationsService;

    @Autowired
    private DeclarationDetailService declarationDetailService;

    @Autowired
    private IDeclarationMapper iDeclarationMapper;

    @ApiOperation(value = "Xem chi tiết tờ khai")
    @GetMapping(RestEndpointEtcCustomsEClare.TK + "/{toKhaiId}")
    public MauToKhaiHqEntity selectItemTK(@Valid @PathVariable String toKhaiId)
            throws ResourceNotFoundException {
        return declarationDetailService.selectItemTK(toKhaiId);
    }

    @ApiOperation(value = "Khai chính thức tờ khai")
    @PostMapping(RestEndpointEtcCustomsEClare.TK)
    public String khaiChinhThuc(@RequestParam(required = true) String toKhaiId,
                                @RequestParam(required = false) Integer isSigned,
                                @RequestBody ToKhaiRequest tkRequest)
            throws ResourceNotFoundException {
        return declarationsService.guiToKhaiChinhThuc(toKhaiId, isSigned, tkRequest.getDataSigned());
    }

    @ApiOperation(value = "Xóa tờ khai")
    @DeleteMapping(RestEndpointEtcCustomsEClare.TK)
    public String deleteTkn(@RequestParam(required = true) String toKhaiId) throws ResourceNotFoundException {
        declarationDetailService.deleteTk(toKhaiId);
        return "OK";
    }

    @ApiOperation(value = "search thông tin tờ khai sau khi được cấp số")
    @GetMapping(RestEndpointEtcCustomsEClare.TK)
    public PageableResponse<TkdnMsgDto> searchTK(@Valid @ModelAttribute KeySearchMauToKhaiHq objInput) {
        return iDeclarationMapper
                .convertPageableResponseEntity2PageableResponseDto(declarationDetailService.searchTK(objInput));
    }

    @ApiOperation(value = "Sửa tờ khai")
    @PatchMapping(RestEndpointEtcCustomsEClare.TKN)
    public MauToKhaiHqEntity updateTkn(@RequestParam(required = true) String toKhaiId,
                                       @RequestBody ToKhaiNhapRequest tknRequest) throws ResourceNotFoundException {
        return declarationsService.suaToKhaiNhap(toKhaiId, tknRequest);
    }

    @ApiOperation(value = "Tiếp nhận message TKN")
    @PostMapping(RestEndpointEtcCustomsEClare.TKN)
    public String receiveMessageTKN(@RequestBody ToKhaiNhapRequest tknRequest,
                                    @RequestParam int type,
                                    @RequestParam(required = false) Integer isSigned) {
        return declarationsService.khaiToKhaiNhapKhau(tknRequest, type, isSigned);

    }

    @ApiOperation(value = "Sửa tờ khai")
    @PatchMapping(RestEndpointEtcCustomsEClare.TKX)
    public MauToKhaiHqEntity updateTkx(@RequestParam(required = true) String toKhaiId,
                                       @RequestBody ToKhaiXuatRequest tkxRequest) throws ResourceNotFoundException {
        return declarationsService.suaToKhaiXuat(toKhaiId, tkxRequest);
    }

    @ApiOperation(value = "Tiếp nhận message TKX")
    @PostMapping(RestEndpointEtcCustomsEClare.TKX)
    public String receiveMessageTKX(@RequestBody ToKhaiXuatRequest tkxRequest,
                                    @RequestParam(required = true) int type,
                                    @RequestParam(required = false) Integer isSigned) {
        return declarationsService.khaiToKhaiXuatKhau(tkxRequest, type, isSigned);

    }

    @ApiOperation(value = "Tiếp nhận message tờ khai sửa nhập khẩu")
    @PostMapping(RestEndpointEtcCustomsEClare.TKS + RestEndpointEtcCustomsEClare.NK)
    public String receiveMessageUpdateTkn(@RequestBody ToKhaiNhapRequest tkRequest,
                                          @RequestParam(required = true) Integer type,
                                          @RequestParam(required = false) Integer isSigned) {
        return declarationsService.khaiSuaToKhaiNhapKhau(tkRequest, type, isSigned);
    }

    @ApiOperation(value = "Tiếp nhận message tờ khai sửa xuất khẩu")
    @PostMapping(RestEndpointEtcCustomsEClare.TKS + RestEndpointEtcCustomsEClare.XK)
    public String receiveMessageUpdateTKX(@RequestBody ToKhaiXuatRequest tkRequest,
                                          @RequestParam(required = true) Integer type,
                                          @RequestParam(required = false) Integer isSigned) {
        return declarationsService.khaiSuaToKhaiXuatKhau(tkRequest, type, isSigned);

    }

    @ApiOperation(value = "Tiếp nhận message TK huỷ")
    @PostMapping(RestEndpointEtcCustomsEClare.TKH)
    public String huyToKhai(@RequestParam(required = true) String toKhaiId,
                            @RequestBody ToKhaiHuyRequest tkRequest,
                            @RequestParam(required = false) Integer isSigned) throws ResourceNotFoundException {
        return declarationsService.huyToKhai(tkRequest, toKhaiId, isSigned);

    }

    @ApiOperation(value = "Xóa template tờ khai xuất")
    @DeleteMapping(RestEndpointEtcCustomsEClare.MAU_TK)
    public String deleteTemplate(@RequestParam(required = true) Long templateId) throws ResourceNotFoundException {
        declarationDetailService.deleteTemplate(templateId);
        return "OK";
    }

    @ApiOperation(value = "Tạo template tờ khai")
    @PostMapping(RestEndpointEtcCustomsEClare.MAU_TK)
    public MauToKhaiHqTemplateEntity createTemplate(@RequestBody String templateContent,
                                                    @RequestParam(required = true) String doanhNghiepId,
                                                    @RequestParam(required = true) String templateName,
                                                    @RequestParam(required = true) String loaiToKhai) {
        return declarationDetailService.createTemplate(templateContent, doanhNghiepId, templateName, loaiToKhai);
    }

    @ApiOperation(value = "Update template tờ khai")
    @PatchMapping(RestEndpointEtcCustomsEClare.MAU_TK)
    public MauToKhaiHqTemplateEntity updateTemplate(@RequestBody String templateContent,
                                                    @RequestParam(required = true) String templateName,
                                                    @RequestParam(required = true) Long templateId)
            throws ResourceNotFoundException {
        return declarationDetailService.updateTemplate(templateId, templateName, templateContent);
    }

    @ApiOperation(value = "Lấy danh sách template tờ khai xuất")
    @GetMapping(RestEndpointEtcCustomsEClare.MAU_TK)
    public List<MauToKhaiHqTemplateEntity> getTemplate(@RequestParam(required = true) String doanhNghiepId,
                                                       @RequestParam(required = true) String loaiToKhai, String templateName) {
        return declarationDetailService.searchTemplate(doanhNghiepId, loaiToKhai, templateName);
    }

    @ApiOperation(value = "search chỉ thị hải quan theo loại")
    @GetMapping(RestEndpointEtcCustomsEClare.CHI_THI_HQ)
    public PageableResponse<ChiThiHqTkEntity> searchChiThiHqTk(@RequestParam(required = true) String toKhaiId,
                                                               @Valid @ModelAttribute KeySearchChiThiHqTk objInput) {
        return declarationDetailService.searchChiThiHqTk(toKhaiId, objInput);
    }

    @ApiOperation(value = "Message hỏi thông tin tờ khai")
    @PostMapping(RestEndpointEtcCustomsEClare.HOI_TTTK)
    public String pushMessageHoiTTTK(@Valid @RequestParam String toKhaiId,
                                     @RequestParam(required = false) String type,
                                     @RequestParam(required = false) Integer isSigned,
                                     @RequestBody ToKhaiRequest tkRequest) throws ResourceNotFoundException {
        return declarationsService.hoiThongTinToKhai(toKhaiId, type, isSigned, tkRequest.getDataSigned());
    }

    @ApiOperation(value = "lấy thông tin xử lí tờ khai")
    @GetMapping(RestEndpointEtcCustomsEClare.THONG_TIN_XU_LY_TK)
    public List<ThongTinXuLyTkEntity> selectThongTinXyLyTK(@Valid @ModelAttribute @RequestParam String toKhaiId)
            throws ResourceNotFoundException {
        return declarationDetailService.selectThongTinXyLyTK(toKhaiId);
    }

    @ApiOperation(value = "lấy hàng hóa tk")
    @GetMapping(RestEndpointEtcCustomsEClare.HANG_HOA_TK)
    public List<DmHangHoaTkEntity> selectHangHoaTK(@Valid @RequestParam(required = true) String toKhaiId)
            throws ResourceNotFoundException {
        return declarationDetailService.selectHangHoaTK(toKhaiId);
    }

    @ApiOperation(value = "lấy danh sách số tiếp nhận")
    @GetMapping(RestEndpointEtcCustomsEClare.SO_TIEP_NHAN)
    public List<KeyValueDto> selectSoTiepNhan(@Valid @RequestParam Integer soTiepNhan, @RequestParam String loaiTkHq) throws ResourceNotFoundException {
        return declarationsService.selectSoTiepNhan(soTiepNhan, loaiTkHq);
    }

    @ApiOperation(value = "lấy danh sách số tờ khai")
    @GetMapping(RestEndpointEtcCustomsEClare.SO_TK)
    public List<KeyValueDto> selectSoTk(@Valid @RequestParam Integer soTk, @RequestParam String loaiTkHq) throws ResourceNotFoundException {
        return declarationsService.selectSoTk(soTk, loaiTkHq);
    }

    @ApiOperation(value = "Tạo dm hàng hóa")
    @PostMapping(RestEndpointEtcCustomsEClare.HANG_HOA)
    public DmHangHoaEntity createHangHoa(@RequestBody @Valid DmHangHoaEntity dmHangHoaEntity) {
        return declarationDetailService.createDmHangHoa(dmHangHoaEntity);
    }

    @ApiOperation(value = "Cập nhật dm hàng hóa")
    @PatchMapping(RestEndpointEtcCustomsEClare.HANG_HOA)
    public DmHangHoaEntity updateHangHoa(@RequestParam Long id, @RequestBody @Valid DmHangHoaEntity dmHangHoaEntity)
            throws ResourceNotFoundException {
        return declarationDetailService.updateDmHangHoa(id, dmHangHoaEntity);
    }

    @ApiOperation(value = "Tìm kiếm dm hàng hóa")
    @GetMapping(RestEndpointEtcCustomsEClare.HANG_HOA)
    public DanhMucChungResponse<KeyValueDto> searchHangHoa(@Valid @ModelAttribute KeySearchKeyValue objInput)
            throws ResourceNotFoundException {
        return new DanhMucChungResponse<KeyValueDto>(declarationDetailService.searchDmHangHoa(objInput));
    }

    @ApiOperation(value = "Xóa dm hàng hóa")
    @DeleteMapping(RestEndpointEtcCustomsEClare.HANG_HOA)
    public ResponseEntity<String> deleteHangHoa(@RequestParam Long id) throws ResourceNotFoundException {
        declarationDetailService.deleteDmHangHoa(id);
        return ResponseEntity.ok("OK");
    }

    @ApiOperation(value = "Xin số định danh hàng hóa")
    @PostMapping(RestEndpointEtcCustomsEClare.SO_DINH_DOANH)
    public String pushMessageXinSoDinhDoanh(@RequestParam(required = false) Integer isSigned,
                                            @RequestBody SoDinhDanhRequest request) {
        return declarationsService.soDinhDanh(isSigned, request);
    }

    @ApiOperation(value = "Danh sach so dinh danh hang hoa")
    @GetMapping(RestEndpointEtcCustomsEClare.SO_DINH_DOANH)
    public PageableResponse<DinhDanhHangHoaTkEntity> dsSoDinhDanh(@ModelAttribute KeySearchSoDinhDanhTk objInput) {
        return declarationsService.dsSoDinhDanh(objInput);
    }

    @ApiOperation(value = "Khai báo danh sách container cho tờ khai")
    @PostMapping(RestEndpointEtcCustomsEClare.CONTAINERS)
    public String kbContainerTk(@RequestParam(required = false) Integer isSigned,
                                @RequestParam(required = true) String toKhaiId,
                                @RequestParam(required = true) String documentType,
                                @RequestBody ContainerTkRequest request) throws ResourceNotFoundException {
        return declarationsService.khaiContainerTk(documentType, toKhaiId, isSigned, request);
    }

    @ApiOperation(value = "Hỏi danh sách container cho tờ khai")
    @PostMapping(RestEndpointEtcCustomsEClare.DS_CONTAINERS)
    public String hoiDsContainers(@RequestParam(required = false) Integer isSigned,
                                  @RequestParam(required = true) String toKhaiId,
                                  @RequestParam(required = true) String tkContainerId,
                                  @RequestBody ContainerTkRequest request) throws ResourceNotFoundException {
        return declarationsService.hoiDsContainers(toKhaiId, tkContainerId, isSigned, request.getDataSigned());
    }

    @ApiOperation(value = "Danh sach container cho tờ khai")
    @GetMapping(RestEndpointEtcCustomsEClare.CONTAINERS)
    public ContainerTkResponse dsContainers(@ModelAttribute KeySearchContainers objInput) throws ResourceNotFoundException {
        return declarationsService.dsContainers(objInput);
    }

    @ApiOperation(value = "jasper report tờ khai nhập")
    @GetMapping(RestEndpointEtcCustomsEClare.EXPORT_TKN)
    public ResponseEntity<byte[]> exportTKN(@RequestParam String id) throws FileNotFoundException, JRException {
        return declarationsService.exportTKN(id);
    }

    @ApiOperation(value = "jasper report tờ khai xuất")
    @GetMapping(RestEndpointEtcCustomsEClare.EXPORT_TKX)
    public ResponseEntity<byte[]> exportTKX(@RequestParam String id) throws FileNotFoundException, JRException {
        return declarationsService.exportTKX(id);
    }
}
