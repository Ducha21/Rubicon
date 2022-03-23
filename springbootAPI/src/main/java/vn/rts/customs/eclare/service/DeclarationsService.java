package vn.rts.customs.eclare.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.rts.customs.eclare.dto.CancelRequestDto;
import vn.rts.customs.eclare.dto.DsHangVaThueDto;
import vn.rts.customs.eclare.dto.QuestionMessageRequestDto;
import vn.rts.customs.eclare.dto.RequestDto;
import vn.rts.customs.eclare.dto.body.*;
import vn.rts.customs.eclare.dto.header.*;
import vn.rts.customs.eclare.entity.*;
import vn.rts.customs.eclare.enums.KenhKhaiBaoEnum;
import vn.rts.customs.eclare.enums.MessageFuntion;
import vn.rts.customs.eclare.enums.MessageType;
import vn.rts.customs.eclare.enums.ToKhaiEnum;
import vn.rts.customs.eclare.mapper.*;
import vn.rts.customs.eclare.repository.impl.*;
import vn.rts.customs.eclare.request.*;
import vn.rts.customs.eclare.request.search.KeySearchContainers;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.eclare.request.search.KeySearchSoDinhDanhTk;
import vn.rts.customs.eclare.request.search.KeyValueDto;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare.ExceptionMessage;
import vn.rts.customs.eclare.util.VnaccsConvert;
import vn.rts.customs.eclare.util.VnaccsConvert.EFormatDateTime;
import vn.rts.customs.lib.dto.PageableResponse;
import vn.rts.customs.lib.dto.PageableResponseEx;
import vn.rts.customs.lib.exception.BadRequestException;
import vn.rts.customs.lib.exception.ResourceNotFoundException;
import vn.rts.customs.lib.service.WebApiCommonServiceImpl;
import vn.rts.customs.lib.util.JacksonEx;
import vn.rts.customs.lib.util.JasperReportsEx;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

/**
 * @author sacmv
 */

@Service
@Slf4j
public class DeclarationsService extends WebApiCommonServiceImpl<MauToKhaiHqRepositoryImpl, KeySearchListObj> {
    /* declare topic id */
    @Value("${spring.kafka.producer.topic-tcp-declare-id}")
    private String sTopicId;
    /* question topic id */
    @Value("${spring.kafka.producer.topic-tcp-question-id}")
    private String sTopicQuestion;

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String kafkaServer;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private MauToKhaiHqRepositoryImpl mauToKhaiHqRepositoryImpl;

    @Autowired
    private DmHangHoaTkRepositoryImpl danhDmHangHoaTkRepositoryImpl;

    @Autowired
    private TkVanDonRepositoryImpl tkVanDonNkRepositoryImpl;

    @Autowired
    private GiayPhepTkRepositoryImpl giayPhepNhapKhauTkRepositoryImpl;

    @Autowired
    private KhoanDieuChinhTkRepositoryImpl khoanDieuChinhTkRepositoryImpl;

    @Autowired
    private SoDinhKemKbdtRepositoryImpl soDinhKemKbdtRepositoryImpl;

    @Autowired
    private TtTrungChuyenRepositoryImpl ttTrungChuyenRepositoryImpl;

    @Autowired
    private ThueKhoanThuKhacRepositoryImpl thueKhoanThuKhacRepositoryImpl;

    @Autowired
    private ThongTinXuLyTkRepositoryImpl thongTinXuLyTkRepositoryImpl;

    @Autowired
    private HuyToKhaiRepositoryImpl huyToKhaiRepositoryImpl;

    @Autowired
    private CoHangHoaTkRepositoryImpl coHangHoaTkRepositoryImpl;

    @Autowired
    private DiaDiemXepHangTkRepositoryImpl diaDiemXepHangTkRepositoryImpl;

    @Autowired
    private ChungTuDinhKemTkRepositoryImpl chungTuDinhKemTkRepositoryImpl;

    @Autowired
    private DinhDanhHangHoaTkRepositoryImpl dinhDanhHangHoaTkRepositoryImpl;

    @Autowired
    private TkContainerRepositoryImpl tkContainerRepositoryImpl;

    @Autowired
    private ContainerInfoRepositoryImpl containerInfoRepositoryImpl;

    @Autowired
    private Validator validator;

    /**
     * @param tknRequest tờ khai nhập request
     * @param type       1: khai chính thức - 0: khai tạm
     * @param isSigned   0: Tờ khai chưa ký - 1: Tờ khai đã ký
     * @return data chưa ký
     */
    @Transactional
    public String khaiToKhaiNhapKhau(ToKhaiNhapRequest tknRequest, int type, Integer isSigned) {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            String signedData = tknRequest.getDataSigned();
            log.info("<--------------------------message tờ khai nhập----------------------------->");
            log.info(signedData);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicId, signedData);
            return signedData;
        }
        if (tknRequest == null) {
            throw new BadRequestException(ExceptionMessage.INPUT_NULL_OR_EMPTY);
        }
        String maDoanhNghiep = tknRequest.getMaDoanhNghiep();
        if (type == ToKhaiEnum.KHAI_CHINH_THUC.getValue()) {
            Set<ConstraintViolation<ToKhaiNhapRequest>> constraintViolations = validator.validate(tknRequest);
            if (constraintViolations.size() > 0) {
                throw new BadRequestException(constraintViolations.iterator().next().getMessage());
            }
        }
        MauToKhaiHqEntity tkn = new MauToKhaiHqEntity();
        BeanUtils.copyProperties(tknRequest, tkn);

        /* insert to khai */
        tkn.setLoaiTkHq(MessageType.TK_NHAP_KHAU.getValue());
        tkn.setMaChucNang(MessageFuntion.KHAI_BAO.getValue());
        tkn.setMaDoanhNghiep(tknRequest.getMaDoanhNghiep());
        tkn.setTenDoanhNghiep(tknRequest.getTenDoanhNghiep());
        if (type == ToKhaiEnum.KHAI_CHINH_THUC.getValue()) {
            tkn.setNgayTraLoi(currentDate);
        }
        tkn.setNgayTao(currentDate);
        tkn.setNguoiTao(currentUser);
        tkn.setMaDoanhNghiep(maDoanhNghiep);
        MauToKhaiHqEntity mauToKhaiHqEntity = mauToKhaiHqRepositoryImpl.insert(tkn);

        /* insert danh sach van don */
        ArrayList<CargoNosDto> cargoNosDtos = new ArrayList<>();
        for (TkVanDonRequest req : tknRequest.getDsVanDon()) {
            TkVanDonEntity tkVanDonEntity = new TkVanDonEntity();
            BeanUtils.copyProperties(req, tkVanDonEntity);
            tkVanDonEntity.setMauKbHqId(mauToKhaiHqEntity.getId());
            tkVanDonEntity.setNgayTao(currentDate);
            tkVanDonEntity.setNguoiTao(currentUser);
            tkVanDonEntity.setMaDoanhNghiep(maDoanhNghiep);
            tkVanDonNkRepositoryImpl.insert(tkVanDonEntity);
            cargoNosDtos.add(TkVanDonNkMapper.INSTANCE.TkVanDonNkToCargoNosDto(tkVanDonEntity));
        }

        /* insert danh sach giay phep */
        ArrayList<PermitsDto> permitsDtos = new ArrayList<>();
        for (GiayPhepTkRequest req : tknRequest.getDsGiayPhep()) {
            GiayPhepTkEntity giayPhepTkEntity = new GiayPhepTkEntity();
            BeanUtils.copyProperties(req, giayPhepTkEntity);
            giayPhepTkEntity.setMauKbHqId(mauToKhaiHqEntity.getId());
            giayPhepTkEntity.setNgayTao(currentDate);
            giayPhepTkEntity.setNguoiTao(currentUser);
            giayPhepTkEntity.setMaDoanhNghiep(maDoanhNghiep);
            giayPhepNhapKhauTkRepositoryImpl.insert(giayPhepTkEntity);
            permitsDtos.add(GiayPhepNhapKhauTkMapper.INSTANCE.giayPhepNhapKhauTkToPermitsDto(giayPhepTkEntity));
        }

        /* insert danh sach cac khoan dieu chinh */
        ArrayList<ValuationAdjustmentsDto> valuationAdjustmentsDtos = new ArrayList<>();
        if (tknRequest.getDsKhoanDieuChinh() != null) {
            for (KhoanDieuChinhTkRequest req : tknRequest.getDsKhoanDieuChinh()) {
                KhoanDieuChinhTkEntity khoanDieuChinhTkEntity = new KhoanDieuChinhTkEntity();
                BeanUtils.copyProperties(req, khoanDieuChinhTkEntity);
                khoanDieuChinhTkEntity.setMauKbHqId(mauToKhaiHqEntity.getId());
                khoanDieuChinhTkEntity.setNgayTao(currentDate);
                khoanDieuChinhTkEntity.setNguoiTao(currentUser);
                khoanDieuChinhTkEntity.setMaDoanhNghiep(maDoanhNghiep);
                khoanDieuChinhTkRepositoryImpl.insert(khoanDieuChinhTkEntity);
                valuationAdjustmentsDtos.add(
                        KhoanDieuChinhTkMapper.INSTANCE.khoanDieuChinhTkToValuationAdjustmentsDto(khoanDieuChinhTkEntity));
            }
        }

        /* insert danh sach dinh kem */
        ArrayList<ElectronicAttachmentDto> electronicAttachmentDtos = new ArrayList<>();
        for (SoDinhKemKbdtRequest req : tknRequest.getDsDinhKemKbdt()) {
            SoDinhKemKbdtEntity soDinhKemKbdtEntity = new SoDinhKemKbdtEntity();
            BeanUtils.copyProperties(req, soDinhKemKbdtEntity);
            soDinhKemKbdtEntity.setMauTkHqId(mauToKhaiHqEntity.getId());
            soDinhKemKbdtEntity.setNgayTao(currentDate);
            soDinhKemKbdtEntity.setNguoiTao(currentUser);
            soDinhKemKbdtEntity.setMaDoanhNghiep(maDoanhNghiep);
            soDinhKemKbdtRepositoryImpl.insert(soDinhKemKbdtEntity);
            electronicAttachmentDtos
                    .add(SoDinhKemKbdtMapper.INSTANCE.soDinhKemKbdtToElectronicAttachmentDto(soDinhKemKbdtEntity));
        }

        /* insert danh sach thong tin trung chuyen */
        ArrayList<TransitInfosDto> transitInfosDtos = new ArrayList<>();
        for (TtTrungChuyenRequest req : tknRequest.getDsThongTinTc()) {
            TtTrungChuyenEntity ttTrungChuyenEntity = new TtTrungChuyenEntity();
            BeanUtils.copyProperties(req, ttTrungChuyenEntity);
            ttTrungChuyenEntity.setMauKbHqId(mauToKhaiHqEntity.getId());
            ttTrungChuyenEntity.setNgayTao(currentDate);
            ttTrungChuyenEntity.setNguoiTao(currentUser);
            ttTrungChuyenEntity.setMaDoanhNghiep(maDoanhNghiep);
            ttTrungChuyenRepositoryImpl.insert(ttTrungChuyenEntity);
            transitInfosDtos.add(TtTrungChuyenMapper.INSTANCE.ttTrungChuyenToTransitInfosDto(ttTrungChuyenEntity));
        }

        /* mapping field contentDto */
        ImportContentDto objContent = TknToContentDtoMapper.INSTANCE.tknToContentDto(mauToKhaiHqEntity);
        objContent.setDocumentType(MessageType.TK_NHAP_KHAU.getValue());
        objContent.setFunction(MessageFuntion.KHAI_BAO.getValue());
        objContent.setCargoNos(cargoNosDtos);
        objContent.setPermits(permitsDtos);
        objContent.setValuationAdjustments(valuationAdjustmentsDtos);
        objContent.setElectronicAttachment(electronicAttachmentDtos);
        objContent.setTransitInfos(transitInfosDtos);
        /* init ReferenceDto */
        ReferenceDto objreference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");

        /* init SendApplicationDto */
        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(tknRequest.getTenDoanhNghiep());
        objsendApplication.setCompanyIdentity(tknRequest.getMaDoanhNghiep());
        /* init FromDto */
        FromDto objFrom = new FromDto();
        objFrom.setName(tknRequest.getTenNguoiKhaiHq());
        objFrom.setIdentity(tknRequest.getMaNguoiKhaiHq());
        /* init ToDto */
        ToDto objTo = new ToDto();
        objTo.setName(tknRequest.getTenHaiQuanTiepNhanKb());
        objTo.setIdentity(tknRequest.getHaiQuanTiepNhanKb());
        /* init SubjectDto */

        ThongTinXuLyTkEntity ttXuLyTk = new ThongTinXuLyTkEntity();
        ttXuLyTk.setMaChucNang(MessageFuntion.KHAI_BAO.getValue());
        ttXuLyTk.setMauTkHqId(mauToKhaiHqEntity.getId());
        ttXuLyTk.setNgayTao(currentDate);
        ttXuLyTk.setMaDoanhNghiep(maDoanhNghiep);
        ThongTinXuLyTkEntity ttXuLyTkResponse = thongTinXuLyTkRepositoryImpl.insert(ttXuLyTk);

        SubjectDto objSubject = new SubjectDto(MessageType.TK_NHAP_KHAU.getValue(), MessageFuntion.KHAI_BAO.getValue());
        objSubject.setRefId(objContent.getRefId());
        /* Danh sach hang hoa */
        List<ImportGoodsItemDto> objGoodsItems = new ArrayList<>();
        for (DmHangHoaTkRequest req : tknRequest.getDsHang()) {
            /* insert danh mục hàng hoá */
            DmHangHoaTkEntity dmHangHoaTkEntity = new DmHangHoaTkEntity();
            BeanUtils.copyProperties(req, dmHangHoaTkEntity, "dsThueKhoanThuKhac");
            dmHangHoaTkEntity.setMauKbHqId(mauToKhaiHqEntity.getId());
            dmHangHoaTkEntity.setNgayTao(currentDate);
            dmHangHoaTkEntity.setNguoiTao(currentUser);
            dmHangHoaTkEntity.setMaDoanhNghiep(maDoanhNghiep);
            DmHangHoaTkEntity daDmHangHoaTkEntity = danhDmHangHoaTkRepositoryImpl.insert(dmHangHoaTkEntity);
            ImportGoodsItemDto goodsItemDto = DanhMucHangMapper.INSTANCE.danhMucHangToImportGoodsItemDto(daDmHangHoaTkEntity);

            // thong tin Co
            ArrayList<CertificateOfOriginsDto> certificateOfOrigins = new ArrayList<>();
            for (CoRequest dmHangHoaTkRequest : req.getDsCo()) {
                CoHangHoaTkEntity coHangHoaTkEntity = new CoHangHoaTkEntity();
                BeanUtils.copyProperties(dmHangHoaTkRequest, coHangHoaTkEntity);
                coHangHoaTkEntity.setDmHangHoaTkId(daDmHangHoaTkEntity.getId());
                coHangHoaTkEntity.setMaDoanhNghiep(maDoanhNghiep);
                coHangHoaTkRepositoryImpl.insert(coHangHoaTkEntity);
                certificateOfOrigins.add(DanhMucHangMapper.INSTANCE.dsCoToCertificateOfOrigins(coHangHoaTkEntity));
            }

            /* Thuế và khoản thu khác */
            ArrayList<OtherTaxsDto> otherTaxsDtos = new ArrayList<>();
            for (ThueKhoanThuKhacRequest thueKhoanThuKhacRequest : req.getDsThueKhoanThuKhac()) {
                ThueKhoanThuKhacEntity thueKhoanThuKhacEntity = new ThueKhoanThuKhacEntity();
                BeanUtils.copyProperties(thueKhoanThuKhacRequest, thueKhoanThuKhacEntity);
                thueKhoanThuKhacEntity.setDmHangHoaTkId(daDmHangHoaTkEntity.getId());
                thueKhoanThuKhacEntity.setNgayTao(currentDate);
                thueKhoanThuKhacEntity.setNguoiTao(currentUser);
                thueKhoanThuKhacEntity.setMaDoanhNghiep(maDoanhNghiep);
                thueKhoanThuKhacRepositoryImpl.insert(thueKhoanThuKhacEntity);
                otherTaxsDtos
                        .add(ThueKhoanThuKhacMapper.INSTANCE.thueKhoanThuKhacToOtherTaxsDto(thueKhoanThuKhacEntity));
            }

            goodsItemDto.setOtherTaxs(otherTaxsDtos);
            goodsItemDto.setCertificateOfOrigins(certificateOfOrigins);
            objGoodsItems.add(goodsItemDto);
        }
        objContent.setGoodsItems(objGoodsItems);
        List<CustomsInstructionsDto> lst = new ArrayList<>();
        lst.add(new CustomsInstructionsDto());
        objContent.setCustomsInstructions(lst);
        /* init SignatureDto */
        SignatureDto objSignature = new SignatureDto();
        objSignature.setData("");
        objSignature.setFileCert("");
        /* init BodyTKDto */
        BodyDto<ImportContentDto> objBody = new BodyDto<ImportContentDto>();
        objBody.setContent(objContent);

        /* init HeaderSendDto */
        HeaderSendDto objHeader = new HeaderSendDto();
        objHeader.setDeclarantChannel(objContent.getDeclarantChannel());
        objHeader.setFrom(objFrom);
        objHeader.setReference(objreference);
        objHeader.setSendApplication(objsendApplication);
        objHeader.setSubject(objSubject);
        objHeader.setTo(objTo);
        /* init TKMsgDto */
        RequestDto requestDto = new RequestDto();
        requestDto.setHeader(objHeader);
        requestDto.setBody(objBody);
        requestDto.setSignature(objSignature);
        requestDto.setClientCustomField(ttXuLyTkResponse.getId());
        String jsonTopic = JacksonEx.getInstance().object2String(requestDto);
        return jsonTopic;
    }

    /**
     * @param tkxRequest tờ khai xuất request
     * @param type       1: khai chính thức - 0: khai tạm
     * @param isSigned   0: Tờ khai chưa ký - 1: Tờ khai đã ký
     * @return data chưa ký
     */
    @Transactional
    public String khaiToKhaiXuatKhau(ToKhaiXuatRequest tkxRequest, int type, Integer isSigned) {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            String signedData = tkxRequest.getDataSigned();
            log.info("<--------------------------message tờ khai nhập----------------------------->");
            log.info(signedData);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicId, signedData);
            return signedData;
        }
        if (tkxRequest == null) {
            throw new BadRequestException(ExceptionMessage.INPUT_NULL_OR_EMPTY);
        }
        String maDoanhNghiep = tkxRequest.getMaDoanhNghiep();
        if (type == ToKhaiEnum.KHAI_CHINH_THUC.getValue()) {
            Set<ConstraintViolation<ToKhaiXuatRequest>> constraintViolations = validator.validate(tkxRequest);
            if (constraintViolations.size() > 0) {
                throw new BadRequestException(constraintViolations.iterator().next().getMessage());
            }
        }
        MauToKhaiHqEntity tkx = new MauToKhaiHqEntity();
        BeanUtils.copyProperties(tkxRequest, tkx);

        if (type == 1) {
            tkx.setNgayTraLoi(currentDate);
        }
        tkx.setNgayTao(currentDate);
        tkx.setNguoiTao(currentUser);
        /* insert to khai */
        tkx.setLoaiTkHq(MessageType.TK_XUAT_KHAU.getValue());
        tkx.setMaChucNang(MessageFuntion.KHAI_BAO.getValue());
        tkx.setMaDoanhNghiep(maDoanhNghiep);
        tkx.setTenDoanhNghiep(tkxRequest.getTenDoanhNghiep());
        MauToKhaiHqEntity mauToKhaiHqEntity = mauToKhaiHqRepositoryImpl.insert(tkx);

        /* insert danh sach van don */
        ArrayList<CargoNosDto> cargoNosDtos = new ArrayList<>();
        for (TkVanDonRequest req : tkxRequest.getDsVanDon()) {
            TkVanDonEntity tkVanDonEntity = new TkVanDonEntity();
            BeanUtils.copyProperties(req, tkVanDonEntity);
            tkVanDonEntity.setMauKbHqId(mauToKhaiHqEntity.getId());
            tkVanDonEntity.setNgayTao(currentDate);
            tkVanDonEntity.setNguoiTao(currentUser);
            tkVanDonEntity.setMaDoanhNghiep(maDoanhNghiep);
            tkVanDonNkRepositoryImpl.insert(tkVanDonEntity);
            cargoNosDtos.add(TkVanDonNkMapper.INSTANCE.TkVanDonNkToCargoNosDto(tkVanDonEntity));
        }

        /* insert danh sach giay phep */
        ArrayList<PermitsDto> permitsDtos = new ArrayList<>();
        for (GiayPhepTkRequest req : tkxRequest.getDsGiayPhep()) {
            GiayPhepTkEntity giayPhepTkEntity = new GiayPhepTkEntity();
            BeanUtils.copyProperties(req, giayPhepTkEntity);
            giayPhepTkEntity.setMauKbHqId(mauToKhaiHqEntity.getId());
            giayPhepTkEntity.setNgayTao(currentDate);
            giayPhepTkEntity.setNguoiTao(currentUser);
            giayPhepTkEntity.setMaDoanhNghiep(maDoanhNghiep);
            giayPhepNhapKhauTkRepositoryImpl.insert(giayPhepTkEntity);
            permitsDtos.add(GiayPhepNhapKhauTkMapper.INSTANCE.giayPhepNhapKhauTkToPermitsDto(giayPhepTkEntity));
        }

        /* insert danh sach dinh kem */
        ArrayList<ElectronicAttachmentDto> electronicAttachmentDtos = new ArrayList<>();
        for (SoDinhKemKbdtRequest req : tkxRequest.getDsDinhKemKbdt()) {
            SoDinhKemKbdtEntity soDinhKemKbdtEntity = new SoDinhKemKbdtEntity();
            BeanUtils.copyProperties(req, soDinhKemKbdtEntity);
            soDinhKemKbdtEntity.setMauTkHqId(mauToKhaiHqEntity.getId());
            soDinhKemKbdtEntity.setNgayTao(currentDate);
            soDinhKemKbdtEntity.setNguoiTao(currentUser);
            soDinhKemKbdtEntity.setMaDoanhNghiep(maDoanhNghiep);
            soDinhKemKbdtRepositoryImpl.insert(soDinhKemKbdtEntity);
            electronicAttachmentDtos
                    .add(SoDinhKemKbdtMapper.INSTANCE.soDinhKemKbdtToElectronicAttachmentDto(soDinhKemKbdtEntity));
        }

        /* insert danh sach thong tin trung chuyen */
        ArrayList<TransitInfosDto> transitInfosDtos = new ArrayList<>();
        for (TtTrungChuyenRequest req : tkxRequest.getDsThongTinTc()) {
            TtTrungChuyenEntity ttTrungChuyenEntity = new TtTrungChuyenEntity();
            BeanUtils.copyProperties(req, ttTrungChuyenEntity);
            ttTrungChuyenEntity.setMauKbHqId(mauToKhaiHqEntity.getId());
            ttTrungChuyenEntity.setNgayTao(currentDate);
            ttTrungChuyenEntity.setNguoiTao(currentUser);
            ttTrungChuyenEntity.setMaDoanhNghiep(maDoanhNghiep);
            ttTrungChuyenRepositoryImpl.insert(ttTrungChuyenEntity);
            transitInfosDtos.add(TtTrungChuyenMapper.INSTANCE.ttTrungChuyenToTransitInfosDto(ttTrungChuyenEntity));
        }
        // Vanning
        ArrayList<VanningPlacesDto> vanningPlacesDtos = new ArrayList<>();
        List<DiaDiemXepHangTkEntity> diDiemXepHangs = tkxRequest.getDsDiaDiemXepHang();
        if (diDiemXepHangs != null) {
            for (DiaDiemXepHangTkEntity req : tkxRequest.getDsDiaDiemXepHang()) {
                DiaDiemXepHangTkEntity diaDiemXepHangTkEntity = new DiaDiemXepHangTkEntity();
                BeanUtils.copyProperties(req, diaDiemXepHangTkEntity);
                diaDiemXepHangTkEntity.setMauTkHqId(mauToKhaiHqEntity.getId());
                diaDiemXepHangTkEntity.setNgayTao(currentDate);
                diaDiemXepHangTkEntity.setMaDoanhNghiep(maDoanhNghiep);
                diaDiemXepHangTkRepositoryImpl.insert(diaDiemXepHangTkEntity);
                vanningPlacesDtos
                        .add(DiaDiemXepHangMapper.INSTANCE.diaDiemXepHangToVanningPlacesDto(diaDiemXepHangTkEntity));
            }
        }
        /* mapping field contentDto */
        ExportContentDto objContent = TkxToContentDtoMapper.INSTANCE.tkxToContentDto(mauToKhaiHqEntity);
        objContent.setDocumentType(MessageType.TK_XUAT_KHAU.getValue());
        objContent.setFunction(MessageFuntion.KHAI_BAO.getValue());
        objContent.setCargoNos(cargoNosDtos);
        objContent.setPermits(permitsDtos);
        objContent.setElectronicAttachment(electronicAttachmentDtos);
        objContent.setTransitInfos(transitInfosDtos);
        objContent.setVanningPlaces(vanningPlacesDtos);
        /* init ReferenceDto */
        ReferenceDto objreference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");

        /* init SendApplicationDto */
        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(tkxRequest.getTenDoanhNghiep());
        objsendApplication.setCompanyIdentity(tkxRequest.getMaDoanhNghiep());
        /* init FromDto */
        FromDto objFrom = new FromDto();
        objFrom.setName(tkxRequest.getTenNguoiKhaiHq());
        objFrom.setIdentity(tkxRequest.getMaNguoiKhaiHq());
        /* init ToDto */
        ToDto objTo = new ToDto();
        objTo.setName(tkxRequest.getTenHaiQuanTiepNhanKb());
        objTo.setIdentity(tkxRequest.getHaiQuanTiepNhanKb());
        /* init SubjectDto */
        ThongTinXuLyTkEntity ttXuLyTk = new ThongTinXuLyTkEntity();
        ttXuLyTk.setMaChucNang(MessageFuntion.KHAI_BAO.getValue());
        ttXuLyTk.setMauTkHqId(mauToKhaiHqEntity.getId());
        ttXuLyTk.setNgayTao(currentDate);
        ttXuLyTk.setMaDoanhNghiep(maDoanhNghiep);
        ThongTinXuLyTkEntity ttXuLyTkResponse = thongTinXuLyTkRepositoryImpl.insert(ttXuLyTk);

        SubjectDto objSubject = new SubjectDto(MessageType.TK_XUAT_KHAU.getValue(), MessageFuntion.KHAI_BAO.getValue());
        objSubject.setRefId(objContent.getRefId());
        /* Danh sach hang hoa */
        List<ExportGoodsItemDto> objGoodsItems = new ArrayList<>();
        for (DmHangHoaTkRequest req : tkxRequest.getDsHang()) {
            /* insert danh mục hàng hoá */
            DmHangHoaTkEntity dmHangHoaTkEntity = new DmHangHoaTkEntity();
            BeanUtils.copyProperties(req, dmHangHoaTkEntity, "dsThueKhoanThuKhac");
            dmHangHoaTkEntity.setMauKbHqId(mauToKhaiHqEntity.getId());
            dmHangHoaTkEntity.setNgayTao(currentDate);
            dmHangHoaTkEntity.setNguoiTao(currentUser);
            dmHangHoaTkEntity.setMaDoanhNghiep(maDoanhNghiep);
            DmHangHoaTkEntity daDmHangHoaTkEntity = danhDmHangHoaTkRepositoryImpl.insert(dmHangHoaTkEntity);
            ExportGoodsItemDto goodsItemDto = DanhMucHangMapper.INSTANCE.danhMucHangToExportGoodsItemDto(daDmHangHoaTkEntity);

            // thong tin Co
            ArrayList<CertificateOfOriginsDto> certificateOfOrigins = new ArrayList<>();
            for (CoRequest dmHangHoaTkRequest : req.getDsCo()) {
                CoHangHoaTkEntity coHangHoaTkEntity = new CoHangHoaTkEntity();
                BeanUtils.copyProperties(dmHangHoaTkRequest, coHangHoaTkEntity);
                coHangHoaTkEntity.setDmHangHoaTkId(daDmHangHoaTkEntity.getId());
                coHangHoaTkEntity.setMaDoanhNghiep(maDoanhNghiep);
                coHangHoaTkRepositoryImpl.insert(coHangHoaTkEntity);
                certificateOfOrigins.add(DanhMucHangMapper.INSTANCE.dsCoToCertificateOfOrigins(coHangHoaTkEntity));
            }

            if (req.getDsThueKhoanThuKhac() != null) {
                /* Thuế và khoản thu khác */
                ArrayList<OtherTaxsDto> otherTaxsDtos = new ArrayList<>();
                for (ThueKhoanThuKhacRequest thueKhoanThuKhacRequest : req.getDsThueKhoanThuKhac()) {
                    ThueKhoanThuKhacEntity thueKhoanThuKhacEntity = new ThueKhoanThuKhacEntity();
                    BeanUtils.copyProperties(thueKhoanThuKhacRequest, thueKhoanThuKhacEntity);
                    thueKhoanThuKhacEntity.setDmHangHoaTkId(daDmHangHoaTkEntity.getId());
                    thueKhoanThuKhacEntity.setNgayTao(currentDate);
                    thueKhoanThuKhacEntity.setNguoiTao(currentUser);
                    thueKhoanThuKhacEntity.setMaDoanhNghiep(maDoanhNghiep);
                    thueKhoanThuKhacRepositoryImpl.insert(thueKhoanThuKhacEntity);
                    otherTaxsDtos
                            .add(ThueKhoanThuKhacMapper.INSTANCE.thueKhoanThuKhacToOtherTaxsDto(thueKhoanThuKhacEntity));
                }
                goodsItemDto.setOtherTaxs(otherTaxsDtos);
            }
            goodsItemDto.setCertificateOfOrigins(certificateOfOrigins);
            objGoodsItems.add(goodsItemDto);
        }
        objContent.setGoodsItems(objGoodsItems);
        List<CustomsInstructionsDto> lst = new ArrayList<>();
        lst.add(new CustomsInstructionsDto());
        objContent.setCustomsInstructions(lst);
        /* init SignatureDto */
        SignatureDto objSignature = new SignatureDto();
        objSignature.setData("");
        objSignature.setFileCert("");
        /* init BodyTKDto */
        BodyDto<ExportContentDto> objBody = new BodyDto<ExportContentDto>();
        objBody.setContent(objContent);
        /* init HeaderSendDto */
        HeaderSendDto objHeader = new HeaderSendDto();
        objHeader.setDeclarantChannel(objContent.getDeclarantChannel());
        objHeader.setFrom(objFrom);
        objHeader.setReference(objreference);
        objHeader.setSendApplication(objsendApplication);
        objHeader.setSubject(objSubject);
        objHeader.setTo(objTo);
        /* init TKMsgDto */
        RequestDto requestDto = new RequestDto();
        requestDto.setHeader(objHeader);
        requestDto.setBody(objBody);
        requestDto.setSignature(objSignature);
        requestDto.setClientCustomField(ttXuLyTkResponse.getId());
        /* Push kafka topic */
        String jsonTopic = JacksonEx.getInstance().object2String(requestDto);
        log.info("<--------------------------message tờ khai xuất----------------------------->");
        log.info(jsonTopic);
        log.info("<--------------------------------------------------------------------------->");
        return jsonTopic;
    }

    /**
     * @param tknRequest Tờ khai nhập request
     * @param type       1: khai chính thức - 0: khai tạm
     * @param isSigned   0: Tờ khai chưa ký - 1: Tờ khai đã ký
     * @return data chưa ký
     */
    @Transactional
    public String khaiSuaToKhaiNhapKhau(ToKhaiNhapRequest tknRequest, Integer type, Integer isSigned) {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            String signedData = tknRequest.getDataSigned();
            log.info("<--------------------------message tờ sửa khai nhập----------------------------->");
            log.info(signedData);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicId, signedData);
            return signedData;
        }
        if (tknRequest == null) {
            throw new BadRequestException(ExceptionMessage.INPUT_NULL_OR_EMPTY);
        }
        String maDoanhNghiep = tknRequest.getMaDoanhNghiep();
        if (type == ToKhaiEnum.KHAI_CHINH_THUC.getValue()) {
            Set<ConstraintViolation<ToKhaiNhapRequest>> constraintViolations = validator.validate(tknRequest);
            if (constraintViolations.size() > 0) {
                throw new BadRequestException(constraintViolations.iterator().next().getMessage());
            }
        }
        Optional<MauToKhaiHqEntity> toKhaiNhapCanSua = mauToKhaiHqRepositoryImpl.findById(tknRequest.getId());
        if (!toKhaiNhapCanSua.isPresent()) {
            throw new BadRequestException("Tờ khai nhập có id " + tknRequest.getId() + " không tồn tại");
        }
        MauToKhaiHqEntity tkn = new MauToKhaiHqEntity();
        BeanUtils.copyProperties(tknRequest, tkn);

        /* insert to khai */
        tkn.setLoaiTkHq(MessageType.TK_NHAP_KHAU.getValue());
        tkn.setMaChucNang(MessageFuntion.SUA.getValue());
        tkn.setMauToKhaiHqIdThamChieu(tknRequest.getId());
        tkn.setMaDoanhNghiep(toKhaiNhapCanSua.get().getMaDoanhNghiep());
        tkn.setTenDoanhNghiep(toKhaiNhapCanSua.get().getTenDoanhNghiep());
        tkn.setSoTiepNhan(null);    // reset
        tkn.setKetQuaXuLy("");      // reset
        tkn.setNgayTraLoi(null);   // reset
        if (type == ToKhaiEnum.KHAI_CHINH_THUC.getValue()) {
            tkn.setNgayTraLoi(currentDate);
        }
        tkn.setNgayTao(currentDate);
        tkn.setNguoiTao(currentUser);
        tkn.setMaDoanhNghiep(maDoanhNghiep);
        MauToKhaiHqEntity mauToKhaiHqEntity = mauToKhaiHqRepositoryImpl.insert(tkn);

        /* insert danh sach van don */
        ArrayList<CargoNosDto> cargoNosDtos = new ArrayList<>();
        for (TkVanDonRequest req : tknRequest.getDsVanDon()) {
            TkVanDonEntity tkVanDonEntity = new TkVanDonEntity();
            BeanUtils.copyProperties(req, tkVanDonEntity);
            tkVanDonEntity.setMauKbHqId(mauToKhaiHqEntity.getId());
            tkVanDonEntity.setNgayTao(currentDate);
            tkVanDonEntity.setNguoiTao(currentUser);
            tkVanDonEntity.setMaDoanhNghiep(maDoanhNghiep);
            tkVanDonNkRepositoryImpl.insert(tkVanDonEntity);
            cargoNosDtos.add(TkVanDonNkMapper.INSTANCE.TkVanDonNkToCargoNosDto(tkVanDonEntity));
        }

        /* insert danh sach giay phep */
        ArrayList<PermitsDto> permitsDtos = new ArrayList<>();
        for (GiayPhepTkRequest req : tknRequest.getDsGiayPhep()) {
            GiayPhepTkEntity giayPhepTkEntity = new GiayPhepTkEntity();
            BeanUtils.copyProperties(req, giayPhepTkEntity);
            giayPhepTkEntity.setMauKbHqId(mauToKhaiHqEntity.getId());
            giayPhepTkEntity.setNgayTao(currentDate);
            giayPhepTkEntity.setNguoiTao(currentUser);
            giayPhepTkEntity.setMaDoanhNghiep(maDoanhNghiep);
            giayPhepNhapKhauTkRepositoryImpl.insert(giayPhepTkEntity);
            permitsDtos.add(GiayPhepNhapKhauTkMapper.INSTANCE.giayPhepNhapKhauTkToPermitsDto(giayPhepTkEntity));
        }

        /* insert danh sach cac khoan dieu chinh */
        ArrayList<ValuationAdjustmentsDto> valuationAdjustmentsDtos = new ArrayList<>();
        for (KhoanDieuChinhTkRequest req : tknRequest.getDsKhoanDieuChinh()) {
            KhoanDieuChinhTkEntity khoanDieuChinhTkEntity = new KhoanDieuChinhTkEntity();
            BeanUtils.copyProperties(req, khoanDieuChinhTkEntity);
            khoanDieuChinhTkEntity.setMauKbHqId(mauToKhaiHqEntity.getId());
            khoanDieuChinhTkEntity.setNgayTao(currentDate);
            khoanDieuChinhTkEntity.setNguoiTao(currentUser);
            khoanDieuChinhTkEntity.setMaDoanhNghiep(maDoanhNghiep);
            khoanDieuChinhTkRepositoryImpl.insert(khoanDieuChinhTkEntity);
            valuationAdjustmentsDtos.add(
                    KhoanDieuChinhTkMapper.INSTANCE.khoanDieuChinhTkToValuationAdjustmentsDto(khoanDieuChinhTkEntity));
        }

        /* insert danh sach dinh kem */
        ArrayList<ElectronicAttachmentDto> electronicAttachmentDtos = new ArrayList<>();
        for (SoDinhKemKbdtRequest req : tknRequest.getDsDinhKemKbdt()) {
            SoDinhKemKbdtEntity soDinhKemKbdtEntity = new SoDinhKemKbdtEntity();
            BeanUtils.copyProperties(req, soDinhKemKbdtEntity);
            soDinhKemKbdtEntity.setMauTkHqId(mauToKhaiHqEntity.getId());
            soDinhKemKbdtEntity.setNgayTao(currentDate);
            soDinhKemKbdtEntity.setNguoiTao(currentUser);
            soDinhKemKbdtEntity.setMaDoanhNghiep(maDoanhNghiep);
            soDinhKemKbdtRepositoryImpl.insert(soDinhKemKbdtEntity);
            electronicAttachmentDtos
                    .add(SoDinhKemKbdtMapper.INSTANCE.soDinhKemKbdtToElectronicAttachmentDto(soDinhKemKbdtEntity));
        }

        /* insert danh sach thong tin trung chuyen */
        ArrayList<TransitInfosDto> transitInfosDtos = new ArrayList<>();
        for (TtTrungChuyenRequest req : tknRequest.getDsThongTinTc()) {
            TtTrungChuyenEntity ttTrungChuyenEntity = new TtTrungChuyenEntity();
            BeanUtils.copyProperties(req, ttTrungChuyenEntity);
            ttTrungChuyenEntity.setMauKbHqId(mauToKhaiHqEntity.getId());
            ttTrungChuyenEntity.setNgayTao(currentDate);
            ttTrungChuyenEntity.setNguoiTao(currentUser);
            ttTrungChuyenEntity.setMaDoanhNghiep(maDoanhNghiep);
            ttTrungChuyenRepositoryImpl.insert(ttTrungChuyenEntity);
            transitInfosDtos.add(TtTrungChuyenMapper.INSTANCE.ttTrungChuyenToTransitInfosDto(ttTrungChuyenEntity));
        }

        /* mapping field contentDto */
        ImportContentDto objContent = TknToContentDtoMapper.INSTANCE.tknToContentDto(mauToKhaiHqEntity);
        objContent.setDocumentType(MessageType.TK_NHAP_KHAU.getValue());
        objContent.setFunction(MessageFuntion.SUA.getValue());
        objContent.setCargoNos(cargoNosDtos);
        objContent.setPermits(permitsDtos);
        objContent.setValuationAdjustments(valuationAdjustmentsDtos);
        objContent.setElectronicAttachment(electronicAttachmentDtos);
        objContent.setTransitInfos(transitInfosDtos);
        /* init ReferenceDto */
        ReferenceDto objreference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");
        /* init SendApplicationDto */
        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(toKhaiNhapCanSua.get().getTenDoanhNghiep());
        objsendApplication.setCompanyIdentity(toKhaiNhapCanSua.get().getMaDoanhNghiep());
        /* init FromDto */
        FromDto objFrom = new FromDto();
        objFrom.setName(tknRequest.getTenNguoiKhaiHq());
        objFrom.setIdentity(tknRequest.getMaNguoiKhaiHq());
        /* init ToDto */
        ToDto objTo = new ToDto();
        objTo.setName(tknRequest.getTenHaiQuanTiepNhanKb());
        objTo.setIdentity(tknRequest.getHaiQuanTiepNhanKb());

        ThongTinXuLyTkEntity ttXuLyTk = new ThongTinXuLyTkEntity();
        ttXuLyTk.setMaChucNang(MessageFuntion.SUA.getValue());
        ttXuLyTk.setMauTkHqId(mauToKhaiHqEntity.getId());
        ttXuLyTk.setNgayTao(currentDate);
        ttXuLyTk.setMaDoanhNghiep(maDoanhNghiep);
        ThongTinXuLyTkEntity ttXuLyTkResponse = thongTinXuLyTkRepositoryImpl.insert(ttXuLyTk);
        /* init SubjectDto */
        SubjectDto objSubject = new SubjectDto(MessageType.TK_NHAP_KHAU.getValue(), MessageFuntion.SUA.getValue());
        objSubject.setRefId(objContent.getRefId());
        /* Danh sach hang hoa */
        List<ImportGoodsItemDto> objGoodsItems = new ArrayList<>();
        for (DmHangHoaTkRequest req : tknRequest.getDsHang()) {
            /* insert danh mục hàng hoá */
            DmHangHoaTkEntity dmHangHoaTkEntity = new DmHangHoaTkEntity();
            BeanUtils.copyProperties(req, dmHangHoaTkEntity, "dsThueKhoanThuKhac");
            dmHangHoaTkEntity.setMauKbHqId(mauToKhaiHqEntity.getId());
            dmHangHoaTkEntity.setNgayTao(currentDate);
            dmHangHoaTkEntity.setNguoiTao(currentUser);
            dmHangHoaTkEntity.setMaDoanhNghiep(maDoanhNghiep);
            DmHangHoaTkEntity daDmHangHoaTkEntity = danhDmHangHoaTkRepositoryImpl.insert(dmHangHoaTkEntity);
            ImportGoodsItemDto goodsItemDto = DanhMucHangMapper.INSTANCE.danhMucHangToImportGoodsItemDto(daDmHangHoaTkEntity);

            // thong tin Co
            ArrayList<CertificateOfOriginsDto> certificateOfOrigins = new ArrayList<>();
            for (CoRequest dmHangHoaTkRequest : req.getDsCo()) {
                CoHangHoaTkEntity coHangHoaTkEntity = new CoHangHoaTkEntity();
                BeanUtils.copyProperties(dmHangHoaTkRequest, coHangHoaTkEntity);
                coHangHoaTkEntity.setDmHangHoaTkId(daDmHangHoaTkEntity.getId());
                coHangHoaTkEntity.setMaDoanhNghiep(maDoanhNghiep);
                coHangHoaTkRepositoryImpl.insert(coHangHoaTkEntity);
                certificateOfOrigins.add(DanhMucHangMapper.INSTANCE.dsCoToCertificateOfOrigins(coHangHoaTkEntity));
            }

            /* Thuế và khoản thu khác */
            ArrayList<OtherTaxsDto> otherTaxsDtos = new ArrayList<>();
            for (ThueKhoanThuKhacRequest thueKhoanThuKhacRequest : req.getDsThueKhoanThuKhac()) {
                ThueKhoanThuKhacEntity thueKhoanThuKhacEntity = new ThueKhoanThuKhacEntity();
                BeanUtils.copyProperties(thueKhoanThuKhacRequest, thueKhoanThuKhacEntity);
                thueKhoanThuKhacEntity.setDmHangHoaTkId(daDmHangHoaTkEntity.getId());
                thueKhoanThuKhacEntity.setNgayTao(currentDate);
                thueKhoanThuKhacEntity.setNguoiTao(currentUser);
                thueKhoanThuKhacEntity.setMaDoanhNghiep(maDoanhNghiep);
                thueKhoanThuKhacRepositoryImpl.insert(thueKhoanThuKhacEntity);
                otherTaxsDtos
                        .add(ThueKhoanThuKhacMapper.INSTANCE.thueKhoanThuKhacToOtherTaxsDto(thueKhoanThuKhacEntity));
            }

            goodsItemDto.setOtherTaxs(otherTaxsDtos);
            goodsItemDto.setCertificateOfOrigins(certificateOfOrigins);
            objGoodsItems.add(goodsItemDto);
        }
        objContent.setGoodsItems(objGoodsItems);
        List<CustomsInstructionsDto> lst = new ArrayList<>();
        lst.add(new CustomsInstructionsDto());
        objContent.setCustomsInstructions(lst);
        /* init SignatureDto */
        SignatureDto objSignature = new SignatureDto();
        objSignature.setData("");
        objSignature.setFileCert("");
        /* init BodyTKDto */
        BodyDto<ImportContentDto> objBody = new BodyDto<ImportContentDto>();
        objBody.setContent(objContent);
        /* init HeaderSendDto */
        HeaderSendDto objHeader = new HeaderSendDto();
        objHeader.setDeclarantChannel(objContent.getDeclarantChannel());
        objHeader.setFrom(objFrom);
        objHeader.setReference(objreference);
        objHeader.setSendApplication(objsendApplication);
        objHeader.setSubject(objSubject);
        objHeader.setTo(objTo);
        /* init TKMsgDto */
        RequestDto requestDto = new RequestDto();
        requestDto.setHeader(objHeader);
        requestDto.setBody(objBody);
        requestDto.setSignature(objSignature);
        requestDto.setClientCustomField(ttXuLyTkResponse.getId());
        String jsonTopic = JacksonEx.getInstance().object2String(requestDto);
        log.info("<--------------------------message tờ sửa khai nhập----------------------------->");
        log.info(jsonTopic);
        log.info("<--------------------------------------------------------------------------->");
        return jsonTopic;
    }

    /**
     * @param tkxRequest Tờ khai nhập request
     * @param type       1: khai chính thức - 0: khai tạm
     * @param isSigned   0: Tờ khai chưa ký - 1: Tờ khai đã ký
     * @return data chưa ký
     */
    @Transactional
    public String khaiSuaToKhaiXuatKhau(ToKhaiXuatRequest tkxRequest, Integer type, Integer isSigned) {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            String signedData = tkxRequest.getDataSigned();
            log.info("<--------------------------message tờ khai nhập----------------------------->");
            log.info(signedData);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicId, signedData);
            return signedData;
        }
        if (tkxRequest == null) {
            throw new BadRequestException(ExceptionMessage.INPUT_NULL_OR_EMPTY);
        }
        String maDoanhNghiep = tkxRequest.getMaDoanhNghiep();
        if (type == ToKhaiEnum.KHAI_CHINH_THUC.getValue()) {
            Set<ConstraintViolation<ToKhaiXuatRequest>> constraintViolations = validator.validate(tkxRequest);
            if (constraintViolations.size() > 0) {
                throw new BadRequestException(constraintViolations.iterator().next().getMessage());
            }
        }
        Optional<MauToKhaiHqEntity> toKhaiXuatCanSua = mauToKhaiHqRepositoryImpl.findById(tkxRequest.getId());
        if (!toKhaiXuatCanSua.isPresent()) {
            throw new BadRequestException("Tờ khai nhập có id " + tkxRequest.getId() + " không tồn tại");
        }
        /* insert to khai */
        MauToKhaiHqEntity tkx = new MauToKhaiHqEntity();
        BeanUtils.copyProperties(tkxRequest, tkx);
        tkx.setLoaiTkHq(MessageType.TK_XUAT_KHAU.getValue());
        tkx.setMaChucNang(MessageFuntion.SUA.getValue());
        tkx.setMauToKhaiHqIdThamChieu(tkxRequest.getId());
        tkx.setMaDoanhNghiep(toKhaiXuatCanSua.get().getMaDoanhNghiep());
        tkx.setTenDoanhNghiep(toKhaiXuatCanSua.get().getTenDoanhNghiep());
        tkx.setSoTiepNhan(null);    // reset
        tkx.setKetQuaXuLy("");      // reset
        tkx.setNgayTraLoi(null);   // reset
        if (type == 1) {
            tkx.setNgayTraLoi(currentDate);
        }
        tkx.setNgayTao(currentDate);
        tkx.setNguoiTao(currentUser);
        tkx.setMaDoanhNghiep(maDoanhNghiep);
        MauToKhaiHqEntity mauToKhaiHqEntity = mauToKhaiHqRepositoryImpl.insert(tkx);

        /* insert danh sach van don */
        ArrayList<CargoNosDto> cargoNosDtos = new ArrayList<>();
        for (TkVanDonRequest req : tkxRequest.getDsVanDon()) {
            TkVanDonEntity tkVanDonEntity = new TkVanDonEntity();
            BeanUtils.copyProperties(req, tkVanDonEntity);
            tkVanDonEntity.setMauKbHqId(mauToKhaiHqEntity.getId());
            tkVanDonEntity.setNgayTao(currentDate);
            tkVanDonEntity.setNguoiTao(currentUser);
            tkVanDonEntity.setMaDoanhNghiep(maDoanhNghiep);
            tkVanDonNkRepositoryImpl.insert(tkVanDonEntity);
            cargoNosDtos.add(TkVanDonNkMapper.INSTANCE.TkVanDonNkToCargoNosDto(tkVanDonEntity));
        }

        /* insert danh sach giay phep */
        ArrayList<PermitsDto> permitsDtos = new ArrayList<>();
        for (GiayPhepTkRequest req : tkxRequest.getDsGiayPhep()) {
            GiayPhepTkEntity giayPhepTkEntity = new GiayPhepTkEntity();
            BeanUtils.copyProperties(req, giayPhepTkEntity);
            giayPhepTkEntity.setMauKbHqId(mauToKhaiHqEntity.getId());
            giayPhepTkEntity.setNgayTao(currentDate);
            giayPhepTkEntity.setNguoiTao(currentUser);
            giayPhepTkEntity.setMaDoanhNghiep(maDoanhNghiep);
            giayPhepNhapKhauTkRepositoryImpl.insert(giayPhepTkEntity);
            permitsDtos.add(GiayPhepNhapKhauTkMapper.INSTANCE.giayPhepNhapKhauTkToPermitsDto(giayPhepTkEntity));
        }

        /* insert danh sach dinh kem */
        ArrayList<ElectronicAttachmentDto> electronicAttachmentDtos = new ArrayList<>();
        for (SoDinhKemKbdtRequest req : tkxRequest.getDsDinhKemKbdt()) {
            SoDinhKemKbdtEntity soDinhKemKbdtEntity = new SoDinhKemKbdtEntity();
            BeanUtils.copyProperties(req, soDinhKemKbdtEntity);
            soDinhKemKbdtEntity.setMauTkHqId(mauToKhaiHqEntity.getId());
            soDinhKemKbdtEntity.setNgayTao(currentDate);
            soDinhKemKbdtEntity.setNguoiTao(currentUser);
            soDinhKemKbdtEntity.setMaDoanhNghiep(maDoanhNghiep);
            soDinhKemKbdtRepositoryImpl.insert(soDinhKemKbdtEntity);
            electronicAttachmentDtos
                    .add(SoDinhKemKbdtMapper.INSTANCE.soDinhKemKbdtToElectronicAttachmentDto(soDinhKemKbdtEntity));
        }

        /* insert danh sach thong tin trung chuyen */
        ArrayList<TransitInfosDto> transitInfosDtos = new ArrayList<>();
        for (TtTrungChuyenRequest req : tkxRequest.getDsThongTinTc()) {
            TtTrungChuyenEntity ttTrungChuyenEntity = new TtTrungChuyenEntity();
            BeanUtils.copyProperties(req, ttTrungChuyenEntity);
            ttTrungChuyenEntity.setMauKbHqId(mauToKhaiHqEntity.getId());
            ttTrungChuyenEntity.setNgayTao(currentDate);
            ttTrungChuyenEntity.setNguoiTao(currentUser);
            ttTrungChuyenEntity.setMaDoanhNghiep(maDoanhNghiep);
            ttTrungChuyenRepositoryImpl.insert(ttTrungChuyenEntity);
            transitInfosDtos.add(TtTrungChuyenMapper.INSTANCE.ttTrungChuyenToTransitInfosDto(ttTrungChuyenEntity));
        }

        // Vanning
        ArrayList<VanningPlacesDto> vanningPlacesDtos = new ArrayList<>();
        List<DiaDiemXepHangTkEntity> diDiemXepHangs = tkxRequest.getDsDiaDiemXepHang();
        if (diDiemXepHangs != null) {
            for (DiaDiemXepHangTkEntity req : tkxRequest.getDsDiaDiemXepHang()) {
                DiaDiemXepHangTkEntity diaDiemXepHangTkEntity = new DiaDiemXepHangTkEntity();
                BeanUtils.copyProperties(req, diaDiemXepHangTkEntity);
                diaDiemXepHangTkEntity.setMauTkHqId(mauToKhaiHqEntity.getId());
                diaDiemXepHangTkEntity.setNgayTao(currentDate);
                diaDiemXepHangTkEntity.setMaDoanhNghiep(maDoanhNghiep);
                diaDiemXepHangTkRepositoryImpl.insert(diaDiemXepHangTkEntity);
                vanningPlacesDtos
                        .add(DiaDiemXepHangMapper.INSTANCE.diaDiemXepHangToVanningPlacesDto(diaDiemXepHangTkEntity));
            }
        }

        /* mapping field contentDto */
        ExportContentDto objContent = TkxToContentDtoMapper.INSTANCE.tkxToContentDto(mauToKhaiHqEntity);
        objContent.setDocumentType(MessageType.TK_XUAT_KHAU.getValue());
        objContent.setFunction(MessageFuntion.SUA.getValue());
        objContent.setCargoNos(cargoNosDtos);
        objContent.setPermits(permitsDtos);
        objContent.setElectronicAttachment(electronicAttachmentDtos);
        objContent.setTransitInfos(transitInfosDtos);
        objContent.setVanningPlaces(vanningPlacesDtos);
        /* init ReferenceDto */
        ReferenceDto objreference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");
        /* init SendApplicationDto */
        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(tkxRequest.getTenDoanhNghiep());
        objsendApplication.setCompanyIdentity(tkxRequest.getMaDoanhNghiep());
        /* init FromDto */
        FromDto objFrom = new FromDto();
        objFrom.setName(tkxRequest.getTenNguoiKhaiHq());
        objFrom.setIdentity(tkxRequest.getMaNguoiKhaiHq());
        /* init ToDto */
        ToDto objTo = new ToDto();
        objTo.setName(tkxRequest.getTenHaiQuanTiepNhanKb());
        objTo.setIdentity(tkxRequest.getHaiQuanTiepNhanKb());
        ThongTinXuLyTkEntity ttXuLyTk = new ThongTinXuLyTkEntity();

        ttXuLyTk.setMaChucNang(MessageFuntion.SUA.getValue());
        ttXuLyTk.setMauTkHqId(mauToKhaiHqEntity.getId());
        ttXuLyTk.setNgayTao(currentDate);
        ttXuLyTk.setMaDoanhNghiep(maDoanhNghiep);
        ThongTinXuLyTkEntity ttXuLyTkResponse = thongTinXuLyTkRepositoryImpl.insert(ttXuLyTk);

        /* init SubjectDto */
        SubjectDto objSubject = new SubjectDto(MessageType.TK_XUAT_KHAU.getValue(), MessageFuntion.SUA.getValue());
        objSubject.setRefId(objContent.getRefId());
        /* Danh sach hang hoa */
        List<ExportGoodsItemDto> objGoodsItems = new ArrayList<>();
        for (DmHangHoaTkRequest req : tkxRequest.getDsHang()) {
            /* insert danh mục hàng hoá */
            DmHangHoaTkEntity dmHangHoaTkEntity = new DmHangHoaTkEntity();
            BeanUtils.copyProperties(req, dmHangHoaTkEntity, "dsThueKhoanThuKhac");
            dmHangHoaTkEntity.setMauKbHqId(mauToKhaiHqEntity.getId());
            dmHangHoaTkEntity.setNgayTao(currentDate);
            dmHangHoaTkEntity.setNguoiTao(currentUser);
            dmHangHoaTkEntity.setMaDoanhNghiep(maDoanhNghiep);
            DmHangHoaTkEntity daDmHangHoaTkEntity = danhDmHangHoaTkRepositoryImpl.insert(dmHangHoaTkEntity);
            ExportGoodsItemDto goodsItemDto = DanhMucHangMapper.INSTANCE.danhMucHangToExportGoodsItemDto(daDmHangHoaTkEntity);

            // thong tin Co
            ArrayList<CertificateOfOriginsDto> certificateOfOrigins = new ArrayList<>();
            for (CoRequest dmHangHoaTkRequest : req.getDsCo()) {
                CoHangHoaTkEntity coHangHoaTkEntity = new CoHangHoaTkEntity();
                BeanUtils.copyProperties(dmHangHoaTkRequest, coHangHoaTkEntity);
                coHangHoaTkEntity.setDmHangHoaTkId(daDmHangHoaTkEntity.getId());
                coHangHoaTkEntity.setMaDoanhNghiep(maDoanhNghiep);
                coHangHoaTkRepositoryImpl.insert(coHangHoaTkEntity);
                certificateOfOrigins.add(DanhMucHangMapper.INSTANCE.dsCoToCertificateOfOrigins(coHangHoaTkEntity));
            }

            /* Thuế và khoản thu khác */
            ArrayList<OtherTaxsDto> otherTaxsDtos = new ArrayList<>();
            for (ThueKhoanThuKhacRequest thueKhoanThuKhacRequest : req.getDsThueKhoanThuKhac()) {
                ThueKhoanThuKhacEntity thueKhoanThuKhacEntity = new ThueKhoanThuKhacEntity();
                BeanUtils.copyProperties(thueKhoanThuKhacRequest, thueKhoanThuKhacEntity);
                thueKhoanThuKhacEntity.setDmHangHoaTkId(daDmHangHoaTkEntity.getId());
                thueKhoanThuKhacEntity.setNgayTao(currentDate);
                thueKhoanThuKhacEntity.setNguoiTao(currentUser);
                thueKhoanThuKhacEntity.setMaDoanhNghiep(maDoanhNghiep);
                thueKhoanThuKhacRepositoryImpl.insert(thueKhoanThuKhacEntity);
                otherTaxsDtos
                        .add(ThueKhoanThuKhacMapper.INSTANCE.thueKhoanThuKhacToOtherTaxsDto(thueKhoanThuKhacEntity));
            }

            goodsItemDto.setOtherTaxs(otherTaxsDtos);
            goodsItemDto.setCertificateOfOrigins(certificateOfOrigins);
            objGoodsItems.add(goodsItemDto);
        }
        objContent.setGoodsItems(objGoodsItems);
        List<CustomsInstructionsDto> lst = new ArrayList<>();
        lst.add(new CustomsInstructionsDto());
        objContent.setCustomsInstructions(lst);
        /* init SignatureDto */
        SignatureDto objSignature = new SignatureDto();
        objSignature.setData("");
        objSignature.setFileCert("");
        /* init BodyTKDto */
        BodyDto<ExportContentDto> objBody = new BodyDto<ExportContentDto>();
        objBody.setContent(objContent);
        /* init HeaderSendDto */
        HeaderSendDto objHeader = new HeaderSendDto();
        objHeader.setDeclarantChannel(objContent.getDeclarantChannel());
        objHeader.setFrom(objFrom);
        objHeader.setReference(objreference);
        objHeader.setSendApplication(objsendApplication);
        objHeader.setSubject(objSubject);
        objHeader.setTo(objTo);
        /* init TKMsgDto */
        RequestDto requestDto = new RequestDto();
        requestDto.setHeader(objHeader);
        requestDto.setBody(objBody);
        requestDto.setSignature(objSignature);
        requestDto.setClientCustomField(ttXuLyTkResponse.getId());
        String jsonTopic = JacksonEx.getInstance().object2String(requestDto);

        log.info("<--------------------------message tờ sửa khai xuất----------------------------->");
        log.info(jsonTopic);
        log.info("<--------------------------------------------------------------------------->");
        return jsonTopic;
    }

    /**
     * @param request tờ khai update request
     * @param source  data source
     */
    @Transactional
    private void updateCacDanhSachMauToKhaiHq(ToKhaiRequest request, MauToKhaiHqEntity source) {
        String maDoanhNghiep = request.getMaDoanhNghiep();
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        // Nếu danh sách mới không có -> delete
        List<TkVanDonEntity> dsVanDon = tkVanDonNkRepositoryImpl.findByToKhaiId(source.getId());
        if (dsVanDon != null) {
            for (TkVanDonEntity tkVanDonEntity : dsVanDon) {
                Optional<TkVanDonRequest> vanDoncompare = request.getDsVanDon() != null ? request.getDsVanDon().stream()
                        .filter(o -> tkVanDonEntity.getId().equals(o.getId())).findFirst() : Optional.empty();
                if (!vanDoncompare.isPresent()) {
                    tkVanDonNkRepositoryImpl.delete(tkVanDonEntity);
                }
            }
        }
        // Insert, update danh sách mới
        if (request.getDsVanDon() != null) {
            for (TkVanDonRequest tkVanDonRequest : request.getDsVanDon()) {
                TkVanDonEntity tkVanDonEntity = new TkVanDonEntity();
                BeanUtils.copyProperties(tkVanDonRequest, tkVanDonEntity);
                if (tkVanDonEntity.getId() == null) {
                    tkVanDonEntity.setMauKbHqId(source.getId());
                    tkVanDonEntity.setNgayTao(currentDate);
                    tkVanDonEntity.setNguoiTao(currentUser);
                    tkVanDonEntity.setMaDoanhNghiep(maDoanhNghiep);
                    tkVanDonNkRepositoryImpl.insert(tkVanDonEntity);
                } else {
                    tkVanDonEntity.setNgaySua(currentDate);
                    tkVanDonEntity.setNguoiSua(currentUser);
                    tkVanDonNkRepositoryImpl.update(tkVanDonEntity);
                }
            }
        }

        // Nếu danh sách mới không có -> delete
        List<GiayPhepTkEntity> dsGiayPhepSource = giayPhepNhapKhauTkRepositoryImpl.findByToKhaiId(source.getId());
        if (dsGiayPhepSource != null) {
            for (GiayPhepTkEntity giayPhepTkEntity : dsGiayPhepSource) {
                Optional<GiayPhepTkRequest> giayPhepCompare = request.getDsGiayPhep() != null ? request.getDsGiayPhep()
                        .stream().filter(o -> giayPhepTkEntity.getId().equals(o.getId())).findFirst()
                        : Optional.empty();
                if (!giayPhepCompare.isPresent()) {
                    giayPhepNhapKhauTkRepositoryImpl.delete(giayPhepTkEntity);
                }
            }
        }
        // Insert, update danh sách mới
        if (request.getDsGiayPhep() != null) {
            for (GiayPhepTkRequest giayPhepTkRequest : request.getDsGiayPhep()) {
                GiayPhepTkEntity giayPhepTkEntity = new GiayPhepTkEntity();
                BeanUtils.copyProperties(giayPhepTkRequest, giayPhepTkEntity);
                if (giayPhepTkEntity.getId() == null) {
                    giayPhepTkEntity.setMauKbHqId(source.getId());
                    giayPhepTkEntity.setNgayTao(currentDate);
                    giayPhepTkEntity.setNguoiTao(currentUser);
                    giayPhepTkEntity.setMaDoanhNghiep(maDoanhNghiep);
                    giayPhepNhapKhauTkRepositoryImpl.insert(giayPhepTkEntity);
                } else {
                    giayPhepTkEntity.setNgaySua(currentDate);
                    giayPhepTkEntity.setNguoiSua(currentUser);
                    giayPhepNhapKhauTkRepositoryImpl.update(giayPhepTkEntity);
                }
            }
        }

        // Nếu danh sách mới không có -> delete
        List<SoDinhKemKbdtEntity> dsDinhKemKbdt = soDinhKemKbdtRepositoryImpl.findByToKhaiId(source.getId());
        if (dsDinhKemKbdt != null) {
            for (SoDinhKemKbdtEntity soDinhKemKbdtEntity : dsDinhKemKbdt) {
                Optional<SoDinhKemKbdtRequest> soDinhKemCompare = request.getDsDinhKemKbdt() != null
                        ? request.getDsDinhKemKbdt().stream().filter(o -> soDinhKemKbdtEntity.getId().equals(o.getId()))
                        .findFirst()
                        : Optional.empty();
                if (!soDinhKemCompare.isPresent()) {
                    soDinhKemKbdtRepositoryImpl.delete(soDinhKemKbdtEntity);
                }
            }
        }
        // Insert, update danh sách mới
        if (request.getDsDinhKemKbdt() != null) {
            for (SoDinhKemKbdtRequest soDinhKemKbdtRequest : request.getDsDinhKemKbdt()) {
                SoDinhKemKbdtEntity soDinhKemKbdtEntity = new SoDinhKemKbdtEntity();
                BeanUtils.copyProperties(soDinhKemKbdtRequest, soDinhKemKbdtEntity);
                if (soDinhKemKbdtEntity.getId() == null) {
                    soDinhKemKbdtEntity.setMauTkHqId(source.getId());
                    soDinhKemKbdtEntity.setNgayTao(currentDate);
                    soDinhKemKbdtEntity.setNguoiTao(currentUser);
                    soDinhKemKbdtEntity.setMaDoanhNghiep(maDoanhNghiep);
                    soDinhKemKbdtRepositoryImpl.insert(soDinhKemKbdtEntity);
                } else {
                    soDinhKemKbdtEntity.setNgaySua(currentDate);
                    soDinhKemKbdtEntity.setNguoiSua(currentUser);
                    soDinhKemKbdtRepositoryImpl.update(soDinhKemKbdtEntity);
                }
            }
        }

        // Nếu danh sách mới không có -> delete
        List<TtTrungChuyenEntity> dsThongTinTc = ttTrungChuyenRepositoryImpl.findByToKhaiId(source.getId());
        if (dsThongTinTc != null) {
            for (TtTrungChuyenEntity ttTrungChuyenEntity : dsThongTinTc) {
                Optional<TtTrungChuyenRequest> ttCompare = request.getDsThongTinTc() != null ? request.getDsThongTinTc()
                        .stream().filter(o -> ttTrungChuyenEntity.getId().equals(o.getId())).findFirst()
                        : Optional.empty();
                if (!ttCompare.isPresent()) {
                    ttTrungChuyenRepositoryImpl.delete(ttTrungChuyenEntity);
                }
            }
        }
        // Insert, update danh sách mới
        if (request.getDsThongTinTc() != null) {
            for (TtTrungChuyenRequest ttTrungChuyenRequest : request.getDsThongTinTc()) {
                TtTrungChuyenEntity ttTrungChuyenEntity = new TtTrungChuyenEntity();
                BeanUtils.copyProperties(ttTrungChuyenRequest, ttTrungChuyenEntity);
                if (ttTrungChuyenEntity.getId() == null) {
                    ttTrungChuyenEntity.setMauKbHqId(source.getId());
                    ttTrungChuyenEntity.setNgayTao(currentDate);
                    ttTrungChuyenEntity.setNguoiTao(currentUser);
                    ttTrungChuyenEntity.setMaDoanhNghiep(maDoanhNghiep);
                    ttTrungChuyenRepositoryImpl.insert(ttTrungChuyenEntity);
                } else {
                    ttTrungChuyenEntity.setNgaySua(currentDate);
                    ttTrungChuyenEntity.setNguoiSua(currentUser);
                    ttTrungChuyenRepositoryImpl.update(ttTrungChuyenEntity);
                }
            }
        }

        // Nếu danh sách mới không có -> delete
        List<DmHangHoaTkEntity> dsHang = danhDmHangHoaTkRepositoryImpl.findByToKhaiId(source.getId());
        if (dsHang != null) {
            for (DmHangHoaTkEntity dmHangHoaTkEntity : dsHang) {
                Optional<DmHangHoaTkRequest> dmHangCompare = request.getDsHang() != null ? request.getDsHang().stream()
                        .filter(o -> dmHangHoaTkEntity.getId().equals(o.getId())).findFirst() : Optional.empty();
                if (!dmHangCompare.isPresent()) {
                    danhDmHangHoaTkRepositoryImpl.delete(dmHangHoaTkEntity);

                    // Delete ds thue khoan thu khac cua hang hoa do
                    List<ThueKhoanThuKhacEntity> dsThueKhoanThuKhac = thueKhoanThuKhacRepositoryImpl
                            .findByHangHoaTkId(dmHangHoaTkEntity.getId());
                    if (dsThueKhoanThuKhac != null) {
                        for (ThueKhoanThuKhacEntity thueKhoanThuKhacEntity : dsThueKhoanThuKhac) {
                            thueKhoanThuKhacRepositoryImpl.delete(thueKhoanThuKhacEntity);
                        }
                    }
                }
            }
        }
        // Insert, update danh sách mới
        if (request.getDsHang() != null) {
            for (DmHangHoaTkRequest dmHangHoaTkRequest : request.getDsHang()) {
                DmHangHoaTkEntity dmHangHoaTkEntity = new DmHangHoaTkEntity();
                BeanUtils.copyProperties(dmHangHoaTkRequest, dmHangHoaTkEntity);
                // Neu hang hoa nay da ton tai -> xoa danh sach thue khoan thu khac
                if (dmHangHoaTkEntity.getId() != null) {
                    List<ThueKhoanThuKhacEntity> dsThueKhoanThuKhac = thueKhoanThuKhacRepositoryImpl
                            .findByHangHoaTkId(dmHangHoaTkEntity.getId());
                    if (dsThueKhoanThuKhac != null) {
                        for (ThueKhoanThuKhacEntity thueKhoanThuKhacEntity : dsThueKhoanThuKhac) {
                            Optional<ThueKhoanThuKhacRequest> thueKhoanThuKhacCompare = dmHangHoaTkRequest
                                    .getDsThueKhoanThuKhac() != null
                                    ? dmHangHoaTkRequest.getDsThueKhoanThuKhac().stream()
                                    .filter(o -> thueKhoanThuKhacEntity.getId().equals(o.getId()))
                                    .findFirst()
                                    : Optional.empty();
                            if (!thueKhoanThuKhacCompare.isPresent()) {
                                thueKhoanThuKhacRepositoryImpl.delete(thueKhoanThuKhacEntity);
                            }
                        }
                    }
                }

                // Insert hang hoa
                if (dmHangHoaTkEntity.getId() == null) {
                    dmHangHoaTkEntity.setMauKbHqId(source.getId());
                    dmHangHoaTkEntity.setNgayTao(currentDate);
                    dmHangHoaTkEntity.setNguoiTao(currentUser);
                    dmHangHoaTkEntity.setMaDoanhNghiep(maDoanhNghiep);
                    dmHangHoaTkEntity = danhDmHangHoaTkRepositoryImpl.insert(dmHangHoaTkEntity);
                } else {
                    dmHangHoaTkEntity.setNgaySua(currentDate);
                    dmHangHoaTkEntity.setNguoiSua(currentUser);
                    dmHangHoaTkEntity = danhDmHangHoaTkRepositoryImpl.update(dmHangHoaTkEntity);
                }

                // Insert thue khoan thu khac cua hang hoa
                if (dmHangHoaTkRequest.getDsThueKhoanThuKhac() != null) {
                    for (ThueKhoanThuKhacRequest thueKhoanThuKhacRequest : dmHangHoaTkRequest.getDsThueKhoanThuKhac()) {
                        ThueKhoanThuKhacEntity thueKhoanThuKhacEntity = new ThueKhoanThuKhacEntity();
                        BeanUtils.copyProperties(thueKhoanThuKhacRequest, thueKhoanThuKhacEntity);
                        thueKhoanThuKhacEntity.setDmHangHoaTkId(dmHangHoaTkEntity.getId());
                        if (thueKhoanThuKhacEntity.getId() == null) {
                            thueKhoanThuKhacEntity.setDmHangHoaTkId(dmHangHoaTkEntity.getId());
                            thueKhoanThuKhacEntity.setNgayTao(currentDate);
                            thueKhoanThuKhacEntity.setNguoiTao(currentUser);
                            thueKhoanThuKhacEntity.setMaDoanhNghiep(maDoanhNghiep);
                            thueKhoanThuKhacRepositoryImpl.insert(thueKhoanThuKhacEntity);
                        } else {
                            thueKhoanThuKhacEntity.setNgaySua(currentDate);
                            thueKhoanThuKhacEntity.setNguoiSua(currentUser);
                            thueKhoanThuKhacRepositoryImpl.update(thueKhoanThuKhacEntity);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param id         id tờ khai xuất
     * @param tkxRequest request update tk
     * @return entity updated
     * @throws ResourceNotFoundException exception khi không tìm thấy tk
     */
    @Transactional
    public MauToKhaiHqEntity suaToKhaiXuat(String id, ToKhaiXuatRequest tkxRequest) throws ResourceNotFoundException {
        String maDoanhNghiep = tkxRequest.getMaDoanhNghiep();
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        // Lấy thông tin tờ khai hải quan
        Optional<MauToKhaiHqEntity> mauToKhaiHqEntityOptional = mauToKhaiHqRepositoryImpl.findById(id);
        if (!mauToKhaiHqEntityOptional.isPresent()) {
            throw new ResourceNotFoundException(ExceptionMessage.NOT_FOUND);
        }
        MauToKhaiHqEntity mauToKhaiHqEntity = mauToKhaiHqEntityOptional.get();

        // Update các danh sách trong mẫu tờ khai hq
        updateCacDanhSachMauToKhaiHq(tkxRequest, mauToKhaiHqEntity);

        // Nếu danh sách mới không có -> delete
        List<DiaDiemXepHangTkEntity> diaDiemXepHangSource = diaDiemXepHangTkRepositoryImpl
                .findByToKhaiId(mauToKhaiHqEntity.getId());
        if (diaDiemXepHangSource != null) {
            for (DiaDiemXepHangTkEntity diaDiemXepHangTkEntity : diaDiemXepHangSource) {
                Optional<DiaDiemXepHangTkEntity> diaDiemXepHangCompare = tkxRequest.getDsDiaDiemXepHang() != null
                        ? tkxRequest.getDsDiaDiemXepHang().stream()
                        .filter(o -> diaDiemXepHangTkEntity.getId().equals(o.getId())).findFirst()
                        : Optional.empty();
                if (!diaDiemXepHangCompare.isPresent()) {
                    diaDiemXepHangTkRepositoryImpl.delete(diaDiemXepHangTkEntity);
                }
            }
        }
        // Insert, update danh sách mới
        if (tkxRequest.getDsDiaDiemXepHang() != null) {
            for (DiaDiemXepHangTkEntity diaDiemXepHangReq : tkxRequest.getDsDiaDiemXepHang()) {
                DiaDiemXepHangTkEntity diaDiemXepHangTkEntity = new DiaDiemXepHangTkEntity();
                BeanUtils.copyProperties(diaDiemXepHangReq, diaDiemXepHangTkEntity);
                if (diaDiemXepHangTkEntity.getId() == null) {
                    diaDiemXepHangTkEntity.setMauTkHqId(mauToKhaiHqEntity.getId());
                    diaDiemXepHangTkEntity.setNgayTao(currentDate);
                    diaDiemXepHangTkEntity.setMaDoanhNghiep(maDoanhNghiep);
                    diaDiemXepHangTkRepositoryImpl.insert(diaDiemXepHangTkEntity);
                } else {
                    diaDiemXepHangTkRepositoryImpl.update(diaDiemXepHangTkEntity);
                }
            }
        }

        // Mapper va update
        try {
            BeanUtils.copyProperties(tkxRequest, mauToKhaiHqEntity);
        } catch (Exception e) {
            throw new BadRequestException("Mapping error");
        }
        mauToKhaiHqEntity.setNgaySua(currentDate);
        mauToKhaiHqEntity.setNguoiSua(currentUser);
        mauToKhaiHqEntity = mauToKhaiHqRepositoryImpl.update(mauToKhaiHqEntity);
        return mauToKhaiHqEntity;
    }

    /**
     * @param id         id tờ khai nhập
     * @param tknRequest request update tk
     * @return entity updated
     * @throws ResourceNotFoundException exception khi không tìm thấy tk
     */
    @Transactional
    public MauToKhaiHqEntity suaToKhaiNhap(String id, ToKhaiNhapRequest tknRequest) throws ResourceNotFoundException {
        String maDoanhNghiep = tknRequest.getMaDoanhNghiep();
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        // Lấy thông tin tờ khai hải quan
        Optional<MauToKhaiHqEntity> mauToKhaiHqEntityOptional = mauToKhaiHqRepositoryImpl.findById(id);
        if (!mauToKhaiHqEntityOptional.isPresent()) {
            throw new ResourceNotFoundException(ExceptionMessage.NOT_FOUND);
        }
        MauToKhaiHqEntity mauToKhaiHqEntity = mauToKhaiHqEntityOptional.get();

        // Update các danh sách trong mẫu tờ khai hq
        updateCacDanhSachMauToKhaiHq(tknRequest, mauToKhaiHqEntity);

        // Nếu danh sách mới không có -> delete
        List<KhoanDieuChinhTkEntity> dsKhoanDieuChinhSource = khoanDieuChinhTkRepositoryImpl
                .findByToKhaiId(mauToKhaiHqEntity.getId());
        if (dsKhoanDieuChinhSource != null) {
            for (KhoanDieuChinhTkEntity khoanDieuChinhTkEntity : dsKhoanDieuChinhSource) {
                Optional<KhoanDieuChinhTkRequest> khoanDieuChinhCompare = tknRequest.getDsKhoanDieuChinh() != null
                        ? tknRequest.getDsKhoanDieuChinh().stream()
                        .filter(o -> khoanDieuChinhTkEntity.getId().equals(o.getId())).findFirst()
                        : Optional.empty();
                if (!khoanDieuChinhCompare.isPresent()) {
                    khoanDieuChinhTkRepositoryImpl.delete(khoanDieuChinhTkEntity);
                }
            }
        }
        // Insert, update danh sách mới
        if (tknRequest.getDsKhoanDieuChinh() != null) {
            for (KhoanDieuChinhTkRequest khoanDieuChinhTkRequest : tknRequest.getDsKhoanDieuChinh()) {
                KhoanDieuChinhTkEntity khoanDieuChinhTkEntity = new KhoanDieuChinhTkEntity();
                BeanUtils.copyProperties(khoanDieuChinhTkRequest, khoanDieuChinhTkEntity);
                if (khoanDieuChinhTkEntity.getId() == null) {
                    khoanDieuChinhTkEntity.setMauKbHqId(mauToKhaiHqEntity.getId());
                    khoanDieuChinhTkEntity.setNgayTao(currentDate);
                    khoanDieuChinhTkEntity.setNguoiTao(currentUser);
                    khoanDieuChinhTkEntity.setMaDoanhNghiep(maDoanhNghiep);
                    khoanDieuChinhTkRepositoryImpl.insert(khoanDieuChinhTkEntity);
                } else {
                    khoanDieuChinhTkEntity.setNgaySua(currentDate);
                    khoanDieuChinhTkEntity.setNguoiSua(currentUser);
                    khoanDieuChinhTkRepositoryImpl.update(khoanDieuChinhTkEntity);
                }
            }
        }

        // Mapper va update
        try {
            BeanUtils.copyProperties(tknRequest, mauToKhaiHqEntity);
        } catch (Exception e) {
            throw new BadRequestException("Mapping error");
        }
        mauToKhaiHqEntity.setNgaySua(currentDate);
        mauToKhaiHqEntity.setNguoiSua(currentUser);
        mauToKhaiHqEntity = mauToKhaiHqRepositoryImpl.update(mauToKhaiHqEntity);
        return mauToKhaiHqEntity;
    }

    /**
     *
     * @param id id tờ khai
     * @return thông tin tờ khai
     * @throws ResourceNotFoundException exception khi không tìm thấy tk
     */
    /**
     * @param id         id tờ khai
     * @param isSigned   0: Tờ khai chưa ký - 1: Tờ khai đã ký
     * @param signedData data đã được ký
     * @return thông tin tờ khai
     * @throws ResourceNotFoundException exception khi không tìm thấy tk
     */
    @Transactional
    public String guiToKhaiChinhThuc(String id, Integer isSigned, String signedData) throws ResourceNotFoundException {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            log.info("<--------------------------message khai chính thức tk----------------------------->");
            log.info(signedData);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicId, signedData);
            return signedData;
        }
        // Lấy thông tin tờ khai hải quan
        Optional<MauToKhaiHqEntity> mauToKhaiHqEntityOptional = mauToKhaiHqRepositoryImpl.findById(id);
        if (!mauToKhaiHqEntityOptional.isPresent()) {
            throw new ResourceNotFoundException(ExceptionMessage.NOT_FOUND);
        }
        MauToKhaiHqEntity mauToKhaiHqEntity = mauToKhaiHqEntityOptional.get();
        String maDoanhNghiep = mauToKhaiHqEntity.getMaDoanhNghiep();
        // validate object
        if (mauToKhaiHqEntity.getLoaiTkHq().equals(MessageType.TK_NHAP_KHAU.getValue())) {
            ToKhaiNhapRequest tknRequest = new ToKhaiNhapRequest();
            BeanUtils.copyProperties(mauToKhaiHqEntity, tknRequest);
            Set<ConstraintViolation<ToKhaiNhapRequest>> constraintViolations = validator.validate(tknRequest);
            if (constraintViolations.size() > 0) {
                throw new BadRequestException(constraintViolations.iterator().next().getMessage());
            }
        } else if (mauToKhaiHqEntity.getLoaiTkHq().equals(MessageType.TK_XUAT_KHAU.getValue())) {
            ToKhaiXuatRequest tkxRequest = new ToKhaiXuatRequest();
            BeanUtils.copyProperties(mauToKhaiHqEntity, tkxRequest);
            Set<ConstraintViolation<ToKhaiXuatRequest>> constraintViolations = validator.validate(tkxRequest);
            if (constraintViolations.size() > 0) {
                throw new BadRequestException(constraintViolations.iterator().next().getMessage());
            }
        }
        // update lai ngay khai bao
        mauToKhaiHqEntity.setNgayTraLoi(currentDate);
        mauToKhaiHqEntity.setNgaySua(currentDate);
        mauToKhaiHqEntity.setNguoiSua(currentUser);
        mauToKhaiHqRepositoryImpl.update(mauToKhaiHqEntity);
        /* insert danh sach van don */
        ArrayList<CargoNosDto> cargoNosDtos = new ArrayList<>();
        List<TkVanDonEntity> dsVanDon = tkVanDonNkRepositoryImpl.findByToKhaiId(mauToKhaiHqEntity.getId());
        if (CollectionUtils.isNotEmpty(dsVanDon)) {
            for (TkVanDonEntity tkVanDonNkEntity : dsVanDon) {
                cargoNosDtos.add(TkVanDonNkMapper.INSTANCE.TkVanDonNkToCargoNosDto(tkVanDonNkEntity));
            }
        }
        /* insert danh sach giay phep */
        ArrayList<PermitsDto> permitsDtos = new ArrayList<>();
        List<GiayPhepTkEntity> dsGiayPhep = giayPhepNhapKhauTkRepositoryImpl.findByToKhaiId(mauToKhaiHqEntity.getId());
        if (CollectionUtils.isNotEmpty(dsGiayPhep)) {
            for (GiayPhepTkEntity giayPhepNhapKhauTkEntity : dsGiayPhep) {
                permitsDtos.add(
                        GiayPhepNhapKhauTkMapper.INSTANCE.giayPhepNhapKhauTkToPermitsDto(giayPhepNhapKhauTkEntity));
            }
        }
        /* insert danh sach cac khoan dieu chinh */
        ArrayList<ValuationAdjustmentsDto> valuationAdjustmentsDtos = new ArrayList<>();
        List<KhoanDieuChinhTkEntity> dsKhoanDieuChinh = khoanDieuChinhTkRepositoryImpl
                .findByToKhaiId(mauToKhaiHqEntity.getId());
        if (CollectionUtils.isNotEmpty(dsKhoanDieuChinh)) {
            for (KhoanDieuChinhTkEntity khoanDieuChinhTkEntity : dsKhoanDieuChinh) {
                valuationAdjustmentsDtos.add(KhoanDieuChinhTkMapper.INSTANCE
                        .khoanDieuChinhTkToValuationAdjustmentsDto(khoanDieuChinhTkEntity));
            }
        }
        /* insert danh sach dinh kem */
        ArrayList<ElectronicAttachmentDto> electronicAttachmentDtos = new ArrayList<>();
        List<SoDinhKemKbdtEntity> dsDinhKemKbdt = soDinhKemKbdtRepositoryImpl.findByToKhaiId(mauToKhaiHqEntity.getId());
        if (CollectionUtils.isNotEmpty(dsDinhKemKbdt)) {
            for (SoDinhKemKbdtEntity soDinhKemKbdtEntity : dsDinhKemKbdt) {
                electronicAttachmentDtos
                        .add(SoDinhKemKbdtMapper.INSTANCE.soDinhKemKbdtToElectronicAttachmentDto(soDinhKemKbdtEntity));
            }
        }
        /* insert danh sach thong tin trung chuyen */
        ArrayList<TransitInfosDto> transitInfosDtos = new ArrayList<>();
        List<TtTrungChuyenEntity> dsThongTinTc = ttTrungChuyenRepositoryImpl.findByToKhaiId(mauToKhaiHqEntity.getId());
        if (CollectionUtils.isNotEmpty(dsThongTinTc)) {
            for (TtTrungChuyenEntity ttTrungChuyenEntity : dsThongTinTc) {
                transitInfosDtos.add(TtTrungChuyenMapper.INSTANCE.ttTrungChuyenToTransitInfosDto(ttTrungChuyenEntity));
            }
        }
        /* insert danh sach thong tin trung chuyen */
        ArrayList<VanningPlacesDto> vanningPlacesDtos = new ArrayList<>();
        List<DiaDiemXepHangTkEntity> diDiemXepHangs = diaDiemXepHangTkRepositoryImpl
                .findByToKhaiId(mauToKhaiHqEntity.getId());
        if (CollectionUtils.isNotEmpty(diDiemXepHangs)) {
            for (DiaDiemXepHangTkEntity diaDiemXepHangTkEntity : diDiemXepHangs) {
                vanningPlacesDtos
                        .add(DiaDiemXepHangMapper.INSTANCE.diaDiemXepHangToVanningPlacesDto(diaDiemXepHangTkEntity));
            }
        }
        List<CustomsInstructionsDto> lst = new ArrayList<>();
        lst.add(new CustomsInstructionsDto());

        /* init ReferenceDto */
        ReferenceDto objreference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");
        /* init SendApplicationDto */
        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(mauToKhaiHqEntity.getTenDoanhNghiep());
        objsendApplication.setCompanyIdentity(mauToKhaiHqEntity.getMaDoanhNghiep());
        /* init SignatureDto */
        SignatureDto objSignature = new SignatureDto();
        objSignature.setData("");
        objSignature.setFileCert("");
        /* init FromDto */
        FromDto objFrom = new FromDto();
        objFrom.setName(mauToKhaiHqEntity.getTenNguoiKhaiHq());
        objFrom.setIdentity(mauToKhaiHqEntity.getMaNguoiKhaiHq());
        /* init ToDto */
        ToDto objTo = new ToDto();
        objTo.setName(mauToKhaiHqEntity.getTenHaiQuanTiepNhanKb());
        objTo.setIdentity(mauToKhaiHqEntity.getHaiQuanTiepNhanKb());
        /* init SubjectDto */
        ThongTinXuLyTkEntity ttXuLyTk = new ThongTinXuLyTkEntity();
        ttXuLyTk.setMaChucNang(MessageFuntion.KHAI_BAO.getValue());
        ttXuLyTk.setMauTkHqId(mauToKhaiHqEntity.getId());
        ttXuLyTk.setNgayTao(currentDate);
        ttXuLyTk.setMaDoanhNghiep(maDoanhNghiep);
        ThongTinXuLyTkEntity ttXuLyTkResponse = thongTinXuLyTkRepositoryImpl.insert(ttXuLyTk);

        SubjectDto objSubject = new SubjectDto(mauToKhaiHqEntity.getLoaiTkHq(), mauToKhaiHqEntity.getMaChucNang());
        objSubject.setRefId(mauToKhaiHqEntity.getId());
        /* Danh sach hang hoa */
        List<ImportGoodsItemDto> importContentDtoArrayList = new ArrayList<>();
        List<ExportGoodsItemDto> exportGoodsItemDtoArrayList = new ArrayList<>();
        /* kiểm tra danh sách hàng hoá */
        List<DmHangHoaTkEntity> dsHang = danhDmHangHoaTkRepositoryImpl.findByToKhaiId(mauToKhaiHqEntity.getId());
        for (DmHangHoaTkEntity item : dsHang) {
            ArrayList<OtherTaxsDto> otherTaxsDtos = new ArrayList<>();
            /* Thuế và khoản thu khác */
            if (CollectionUtils.isNotEmpty(item.getDsThueKhoanThuKhac())) {
                List<ThueKhoanThuKhacEntity> dsThueKhoanThuKhac = thueKhoanThuKhacRepositoryImpl
                        .findByHangHoaTkId(item.getId());
                for (ThueKhoanThuKhacEntity thueKhoanThuKhacEntity : dsThueKhoanThuKhac) {
                    otherTaxsDtos.add(
                            ThueKhoanThuKhacMapper.INSTANCE.thueKhoanThuKhacToOtherTaxsDto(thueKhoanThuKhacEntity));
                }
            }
            List<CertificateOfOriginsDto> certificateOfOriginsDtos = new ArrayList<>();
            List<CoHangHoaTkEntity> dsCo = coHangHoaTkRepositoryImpl.findByDmHangHoaTkId(item.getId());
            for (CoHangHoaTkEntity coHangHoaTkEntityItem : dsCo) {
                certificateOfOriginsDtos
                        .add(DanhMucHangMapper.INSTANCE.dsCoToCertificateOfOrigins(coHangHoaTkEntityItem));
            }

            ExportGoodsItemDto exportGoodsItemDto = DanhMucHangMapper.INSTANCE.danhMucHangToExportGoodsItemDto(item);
            ImportGoodsItemDto importGoodsItemDto = DanhMucHangMapper.INSTANCE.danhMucHangToImportGoodsItemDto(item);
            exportGoodsItemDto.setOtherTaxs(otherTaxsDtos);
            exportGoodsItemDto.setCertificateOfOrigins(certificateOfOriginsDtos);
            importGoodsItemDto.setOtherTaxs(otherTaxsDtos);
            importGoodsItemDto.setCertificateOfOrigins(certificateOfOriginsDtos);
            importContentDtoArrayList.add(importGoodsItemDto);
            exportGoodsItemDtoArrayList.add(exportGoodsItemDto);
        }
        /* mapping field contentDto */
        ImportContentDto importContent = TknToContentDtoMapper.INSTANCE.tknToContentDto(mauToKhaiHqEntity);
        ExportContentDto exportContent = TkxToContentDtoMapper.INSTANCE.tkxToContentDto(mauToKhaiHqEntity);
        if (mauToKhaiHqEntity.getLoaiTkHq().equals(MessageType.TK_NHAP_KHAU.getValue())) {
            importContent.setDocumentType(mauToKhaiHqEntity.getLoaiTkHq());
            importContent.setFunction(mauToKhaiHqEntity.getMaChucNang());
            importContent.setCargoNos(cargoNosDtos);
            importContent.setPermits(permitsDtos);
            importContent.setValuationAdjustments(valuationAdjustmentsDtos);
            importContent.setElectronicAttachment(electronicAttachmentDtos);
            importContent.setTransitInfos(transitInfosDtos);
            importContent.setCustomsInstructions(lst);
            importContent.setGoodsItems(importContentDtoArrayList);
        } else if (mauToKhaiHqEntity.getLoaiTkHq().equals(MessageType.TK_XUAT_KHAU.getValue())) {
            exportContent.setDocumentType(mauToKhaiHqEntity.getLoaiTkHq());
            exportContent.setFunction(mauToKhaiHqEntity.getMaChucNang());
            exportContent.setCargoNos(cargoNosDtos);
            exportContent.setPermits(permitsDtos);
            exportContent.setElectronicAttachment(electronicAttachmentDtos);
            exportContent.setTransitInfos(transitInfosDtos);
            exportContent.setVanningPlaces(vanningPlacesDtos);
            exportContent.setCustomsInstructions(lst);
            exportContent.setGoodsItems(exportGoodsItemDtoArrayList);
        }
        /* init BodyTKDto */
        BodyDto<ImportContentDto> objTknBody = new BodyDto<>();
        BodyDto<ExportContentDto> objTkxBody = new BodyDto<>();
        if (mauToKhaiHqEntity.getLoaiTkHq().equals(MessageType.TK_NHAP_KHAU.getValue())) {
            objTknBody.setContent(importContent);
        } else if (mauToKhaiHqEntity.getLoaiTkHq().equals(MessageType.TK_XUAT_KHAU.getValue())) {
            objTkxBody.setContent(exportContent);
        }
        /* init HeaderSendDto */
        HeaderSendDto objHeader = new HeaderSendDto();
        objHeader.setDeclarantChannel(importContent.getDeclarantChannel());
        objHeader.setFrom(objFrom);
        objHeader.setReference(objreference);
        objHeader.setSendApplication(objsendApplication);
        objHeader.setSubject(objSubject);
        objHeader.setTo(objTo);
        /* init TKMsgDto */
        RequestDto requestDto = new RequestDto();
        requestDto.setHeader(objHeader);
        if (mauToKhaiHqEntity.getLoaiTkHq().equals(MessageType.TK_NHAP_KHAU.getValue())) {
            requestDto.setBody(objTknBody);
        } else if (mauToKhaiHqEntity.getLoaiTkHq().equals(MessageType.TK_XUAT_KHAU.getValue())) {
            requestDto.setBody(objTkxBody);
        }
        requestDto.setSignature(objSignature);
        requestDto.setClientCustomField(ttXuLyTkResponse.getId());
        String jsonTopic = JacksonEx.getInstance().object2String(requestDto);
        log.info("<--------------------------message khai chính thức tk----------------------------->");
        log.info(jsonTopic);
        log.info("<--------------------------------------------------------------------------->");
        return jsonTopic;
    }

    /**
     * @param toKhaiId   tờ khai id
     * @param type       13: Hỏi theo refId, 15: Hỏi theo mã DN
     * @param isSigned   0: Tờ khai chưa ký - 1: Tờ khai đã ký
     * @param signedData data đã được ký
     * @return data chưa ký
     * @throws ResourceNotFoundException
     */
    @Transactional
    public String hoiThongTinToKhai(String toKhaiId, String type, Integer isSigned, String signedData) throws ResourceNotFoundException {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            log.info("<--------------------------message hỏi tt tờ khai----------------------------->");
            log.info(signedData);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicQuestion, signedData);
            return signedData;
        }
        String questionType = MessageFuntion.HOI_THEO_REF_ID.getValue();
        if (type != null && !type.isEmpty()) {
            questionType = type;
        }
        /* Query thong tin to khai */
        Optional<MauToKhaiHqEntity> toKhaiHq = mauToKhaiHqRepositoryImpl.findById(toKhaiId);
        if (!toKhaiHq.isPresent()) {
            throw new ResourceNotFoundException(ExceptionMessage.NOT_FOUND);
        }
        MauToKhaiHqEntity toKhai = toKhaiHq.get();
        String maDoanhNghiep = toKhai.getMaDoanhNghiep();

        ReferenceDto objReference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");

        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(toKhai.getTenDoanhNghiep());
        objsendApplication.setCompanyIdentity(toKhai.getMaDoanhNghiep());

        FromDto objFrom = new FromDto();
        objFrom.setName(toKhai.getTenNguoiKhaiHq());
        objFrom.setIdentity(toKhai.getMaNguoiKhaiHq());

        ToDto objTo = new ToDto();
        objTo.setName(toKhai.getTenHaiQuanTiepNhanKb());
        objTo.setIdentity(toKhai.getHaiQuanTiepNhanKb());

        ThongTinXuLyTkEntity ttXuLyTk = new ThongTinXuLyTkEntity();
        ttXuLyTk.setMaChucNang(questionType);
        ttXuLyTk.setMauTkHqId(toKhaiId);
        ttXuLyTk.setNgayTao(currentDate);
        ttXuLyTk.setMaDoanhNghiep(maDoanhNghiep);
        ThongTinXuLyTkEntity ttXuLyTkResponse = thongTinXuLyTkRepositoryImpl.insert(ttXuLyTk);

        SubjectDto objSubject = new SubjectDto(toKhai.getLoaiTkHq());
        objSubject.setRefId(toKhai.getId());
        objSubject.setFunction(questionType);

        QuestionContentDto objContentTKHoiTT = new QuestionContentDto();
        objContentTKHoiTT.setDocumentType(toKhai.getLoaiTkHq());
        objContentTKHoiTT.setFunction(questionType);

        objContentTKHoiTT.setRefId(toKhai.getId());
        objContentTKHoiTT.setDeclarantChannel(toKhai.getKenhKhaiBao());
        objContentTKHoiTT.setRefCustomsId(String.valueOf(toKhai.getSoTiepNhan()));
        if (toKhai.getKenhKhaiBao() == KenhKhaiBaoEnum.DN_TU_KHAI.getValue()) {
            objContentTKHoiTT.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
            objContentTKHoiTT.setDeclarantName(toKhai.getTenNguoiKhaiHq());
        } else {
            objContentTKHoiTT.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
            objContentTKHoiTT.setDeclarantName(toKhai.getTenNguoiKhaiHq());
        }
        if (toKhai.getLoaiTkHq().equals(MessageType.TK_NHAP_KHAU.getValue())) {
            objContentTKHoiTT.setImporterCode(toKhai.getMaNguoiNk());
            objContentTKHoiTT.setImporterName(toKhai.getTenNguoiNk());
        } else if (toKhai.getLoaiTkHq().equals(MessageType.TK_XUAT_KHAU.getValue())) {
            objContentTKHoiTT.setImporterCode(toKhai.getMaNguoiXk());
            objContentTKHoiTT.setImporterName(toKhai.getTenNguoiXk());
        }

        HeaderSendDto objHeaderSendDto = new HeaderSendDto();
        objHeaderSendDto.setFrom(objFrom);
        objHeaderSendDto.setReference(objReference);
        objHeaderSendDto.setDeclarantChannel(toKhai.getKenhKhaiBao());
        objHeaderSendDto.setSendApplication(objsendApplication);
        objHeaderSendDto.setSubject(objSubject);
        objHeaderSendDto.setTo(objTo);

        BodyDto<QuestionContentDto> objBodyTKHoiTT = new BodyDto<>();
        objBodyTKHoiTT.setContent(objContentTKHoiTT);

        /* init SignatureDto */
        SignatureDto objSignature = new SignatureDto();
        objSignature.setData("");
        objSignature.setFileCert("");

        QuestionMessageRequestDto requestDto = new QuestionMessageRequestDto();
        requestDto.setHeader(objHeaderSendDto);
        requestDto.setBody(objBodyTKHoiTT);
        requestDto.setSignature(objSignature);
        requestDto.setClientCustomField(ttXuLyTkResponse.getId());
        String jsonToTopic = JacksonEx.getInstance().object2String(requestDto);
        log.info("<--------------------------message hỏi thông tin tờ khai----------------------------->");
        log.info(jsonToTopic);
        log.info("<------------------------------------------------------------------------------------>");
        return jsonToTopic;
    }

    /**
     * @param tkhuyRequest   tờ khai hủy
     * @param toKhaiCanHuyId id tờ khai cần hủy
     * @param isSigned       0: Tờ khai chưa ký - 1: Tờ khai đã ký
     * @return data chưa ký
     * @throws ResourceNotFoundException
     */
    @Transactional
    public String huyToKhai(ToKhaiHuyRequest tkhuyRequest, String toKhaiCanHuyId, Integer isSigned) throws ResourceNotFoundException {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            String signedData = tkhuyRequest.getDataSigned();
            log.info("<--------------------------message tờ huy----------------------------->");
            log.info(signedData);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicId, signedData);
            return signedData;
        }

        if (tkhuyRequest == null) {
            throw new BadRequestException(ExceptionMessage.INPUT_NULL_OR_EMPTY);
        }
        String maDoanhNghiep = tkhuyRequest.getMaDoanhNghiep();
        /** Lay thong tin to khai can huy */
        Optional<MauToKhaiHqEntity> toKhaiCanHuyOptional = mauToKhaiHqRepositoryImpl.findById(toKhaiCanHuyId);
        if (!toKhaiCanHuyOptional.isPresent()) {
            throw new ResourceNotFoundException(ExceptionMessage.NOT_FOUND);
        }
        MauToKhaiHqEntity toKhaiCanHuy = toKhaiCanHuyOptional.get();
        MauToKhaiHqEntity mauToKhaiHqHuyEntity = new MauToKhaiHqEntity();
        HuyToKhaiEntity huyToKhaiEntity = new HuyToKhaiEntity();
        BeanUtils.copyProperties(toKhaiCanHuy, mauToKhaiHqHuyEntity);
        BeanUtils.copyProperties(tkhuyRequest, huyToKhaiEntity);
        mauToKhaiHqHuyEntity.setMaChucNang(MessageFuntion.HUY.getValue());
        mauToKhaiHqHuyEntity.setMauToKhaiHqIdThamChieu(toKhaiCanHuyId);
        mauToKhaiHqHuyEntity.setMaDoanhNghiep(toKhaiCanHuy.getMaDoanhNghiep());
        mauToKhaiHqHuyEntity.setTenDoanhNghiep(toKhaiCanHuy.getTenDoanhNghiep());
        mauToKhaiHqHuyEntity.setNgayTraLoi(null);
        mauToKhaiHqHuyEntity.setKetQuaXuLy("");
        mauToKhaiHqHuyEntity.setSoTiepNhan("");
        mauToKhaiHqHuyEntity.setNgayTao(currentDate);
        mauToKhaiHqHuyEntity.setNguoiTao(currentUser);
        mauToKhaiHqHuyEntity.setMaDoanhNghiep(maDoanhNghiep);
        MauToKhaiHqEntity mauToKhaiHq = mauToKhaiHqRepositoryImpl.insert(mauToKhaiHqHuyEntity);
        huyToKhaiEntity.setMauTkHqId(mauToKhaiHq.getId());
        huyToKhaiEntity.setSoTk(toKhaiCanHuy.getSoTk());
        huyToKhaiEntity.setNgayTao(currentDate);
        huyToKhaiEntity.setNguoiTao(currentUser);
        huyToKhaiEntity.setMaDoanhNghiep(maDoanhNghiep);
        HuyToKhaiEntity huyToKhai = huyToKhaiRepositoryImpl.insert(huyToKhaiEntity);

        CancelContentDto cancelContentDto = TkHuyMapper.INSTANCE.huyToKhaiEntityToCancelContentDto(huyToKhai);
        cancelContentDto.setRefId(mauToKhaiHq.getId());
        cancelContentDto.setRefCustomsId(String.valueOf(toKhaiCanHuy.getSoTiepNhan()));
        cancelContentDto.setDeclarantChannel(toKhaiCanHuy.getKenhKhaiBao());
        cancelContentDto.setDeclarationNo(toKhaiCanHuy.getSoTk());
        cancelContentDto.setDeclarationOffice(toKhaiCanHuy.getHaiQuanTiepNhanKb());
        cancelContentDto.setDeclarationKindCode(toKhaiCanHuy.getMaLoaiHinh());
        if (toKhaiCanHuy.getLoaiTkHq().equals(MessageType.TK_NHAP_KHAU.getValue())) {
            cancelContentDto.setImporterCode(toKhaiCanHuy.getMaNguoiNk());
            cancelContentDto.setImporterName(toKhaiCanHuy.getTenNguoiNk());
        } else {
            cancelContentDto.setImporterCode(toKhaiCanHuy.getMaNguoiXk());
            cancelContentDto.setImporterName(toKhaiCanHuy.getTenNguoiXk());
        }
        cancelContentDto.setFunction(MessageFuntion.HUY.getValue());
        cancelContentDto.setDocumentType(toKhaiCanHuy.getLoaiTkHq());
        cancelContentDto.setAcceptanceDate(VnaccsConvert.date2String(toKhaiCanHuy.getNgayDangKy(),
                EFormatDateTime.YYYY_MM_DD_H.getValueEnum()));
        cancelContentDto.setAcceptanceOffice(toKhaiCanHuy.getHaiQuanTiepNhanKb());
        cancelContentDto.setIssueDate(
                VnaccsConvert.date2String(mauToKhaiHq.getNgayTao(), EFormatDateTime.YYYY_MM_DD_H.getValueEnum()));
        cancelContentDto.setRegisteredDate(
                VnaccsConvert.date2String(mauToKhaiHq.getNgayTao(), EFormatDateTime.YYYY_MM_DD_H.getValueEnum()));
        cancelContentDto.setClearanceDate(
                VnaccsConvert.date2String(mauToKhaiHq.getNgayTao(), EFormatDateTime.YYYY_MM_DD.getValueEnum()));

        /* init SendApplicationDto */
        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(toKhaiCanHuy.getTenDoanhNghiep());
        objsendApplication.setCompanyIdentity(toKhaiCanHuy.getMaDoanhNghiep());
        /* init FromDto */
        FromDto objFrom = new FromDto();
        objFrom.setName(toKhaiCanHuy.getTenNguoiKhaiHq());
        objFrom.setIdentity(toKhaiCanHuy.getMaNguoiKhaiHq());
        /* init ToDto */
        ToDto objTo = new ToDto();
        objTo.setName(toKhaiCanHuy.getTenHaiQuanTiepNhanKb());
        objTo.setIdentity(toKhaiCanHuy.getHaiQuanTiepNhanKb());
        /* init SubjectDto */
        ThongTinXuLyTkEntity ttXuLyTk = new ThongTinXuLyTkEntity();
        ttXuLyTk.setMaChucNang(MessageFuntion.HUY.getValue());
        ttXuLyTk.setMauTkHqId(mauToKhaiHq.getId());
        ttXuLyTk.setNgayTao(currentDate);
        ttXuLyTk.setMaDoanhNghiep(maDoanhNghiep);
        ThongTinXuLyTkEntity ttXuLyTkResponse = thongTinXuLyTkRepositoryImpl.insert(ttXuLyTk);

        SubjectDto objSubject = new SubjectDto(toKhaiCanHuy.getLoaiTkHq(), MessageFuntion.HUY.getValue());
        objSubject.setRefId(mauToKhaiHq.getId());
        /* init ReferenceDto */
        ReferenceDto objreference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");

        /* init BodyTKDto */
        BodyDto<CancelContentDto> objBody = new BodyDto<>();
        objBody.setContent(cancelContentDto);
        /* init HeaderSendDto */
        HeaderSendDto objHeader = new HeaderSendDto();
        objHeader.setDeclarantChannel(cancelContentDto.getDeclarantChannel());
        objHeader.setFrom(objFrom);
        objHeader.setReference(objreference);
        objHeader.setSendApplication(objsendApplication);
        objHeader.setSubject(objSubject);
        objHeader.setTo(objTo);
        /* init SignatureDto */
        SignatureDto objSignature = new SignatureDto();
        objSignature.setData("");
        objSignature.setFileCert("");
        /* init TKMsgDto */
        CancelRequestDto cancelRequestDto = new CancelRequestDto();
        cancelRequestDto.setHeader(objHeader);
        cancelRequestDto.setBody(objBody);
        cancelRequestDto.setSignature(objSignature);
        cancelRequestDto.setClientCustomField(ttXuLyTkResponse.getId());
        String jsonTopic = JacksonEx.getInstance().object2String(cancelRequestDto);
        return jsonTopic;
    }

    /**
     * @param soTiepNhan
     * @return
     */
    public List<KeyValueDto> selectSoTiepNhan(Integer soTiepNhan, String loaiTkHq) {
        return mauToKhaiHqRepositoryImpl.selectSoTiepNhan(soTiepNhan, loaiTkHq);
    }

    /**
     * @param soTk
     * @return
     */
    public List<KeyValueDto> selectSoTk(Integer soTk, String loaiTkHq) {
        return mauToKhaiHqRepositoryImpl.selectSoTk(soTk, loaiTkHq);
    }

    /**
     * @param isSigned
     * @param request
     * @return
     */
    @Transactional
    public String soDinhDanh(Integer isSigned, SoDinhDanhRequest request) {
        String maDoanhNghiep = request.getMaDoanhNghiep();
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            String signedData = request.getDataSigned();
            log.info("<--------------------------message xin so dinh danh----------------------------->");
            log.info(signedData);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicId, signedData);
            return signedData;
        }
        DinhDanhHangHoaTkEntity dinhDanhHangHoaTkEntity = new DinhDanhHangHoaTkEntity();
        BeanUtils.copyProperties(request, dinhDanhHangHoaTkEntity);
        dinhDanhHangHoaTkEntity.setNgayTao(currentDate);
        dinhDanhHangHoaTkEntity.setNguoiTao(currentUser);
        dinhDanhHangHoaTkEntity.setTrangThai(MessageFuntion.KHAI_BAO.getValue());
        dinhDanhHangHoaTkEntity.setMaDoanhNghiep(maDoanhNghiep);
        DinhDanhHangHoaTkEntity dinhDanhHangHoaTkInsert = dinhDanhHangHoaTkRepositoryImpl.insert(dinhDanhHangHoaTkEntity);
        CargoCtrlNoContentDto contentDto = SoDinhDanhMapper.INSTANCE.soDinhDanhEntityToCargoCtrlNoContent(dinhDanhHangHoaTkEntity);
        contentDto.setDocumentType(MessageType.SO_DINH_DANH.getValue());
        /* init SendApplicationDto */
        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(dinhDanhHangHoaTkInsert.getTenDoanhNghiep());
        objsendApplication.setCompanyIdentity(dinhDanhHangHoaTkInsert.getMaDoanhNghiep());
        /* init FromDto */
        FromDto objFrom = new FromDto();
        objFrom.setName(dinhDanhHangHoaTkInsert.getTenNguoiKhaiHq());
        objFrom.setIdentity(dinhDanhHangHoaTkInsert.getMaNguoiKhaiHq());
        /* init ToDto */
        ToDto objTo = new ToDto();
        objTo.setName(dinhDanhHangHoaTkInsert.getTenHqTiepNhan());
        objTo.setIdentity(dinhDanhHangHoaTkInsert.getHqTiepNhan());
        SubjectDto objSubject = new SubjectDto(MessageType.SO_DINH_DANH.getValue(), MessageFuntion.KHAI_BAO.getValue());
        objSubject.setRefId(dinhDanhHangHoaTkInsert.getId());
        /* init ReferenceDto */
        ReferenceDto objreference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");
        /* init HeaderSendDto */
        HeaderSendDto objHeader = new HeaderSendDto();
        objHeader.setDeclarantChannel(dinhDanhHangHoaTkInsert.getKenhKhaiBao());
        objHeader.setFrom(objFrom);
        objHeader.setReference(objreference);
        objHeader.setSendApplication(objsendApplication);
        objHeader.setSubject(objSubject);
        objHeader.setTo(objTo);
        /* init SignatureDto */
        SignatureDto objSignature = new SignatureDto();
        objSignature.setData("");
        objSignature.setFileCert("");
        /* init BodyTKDto */
        BodyDto<CargoCtrlNoContentDto> objBody = new BodyDto<>();
        objBody.setContent(contentDto);
        /* init TKMsgDto */
        RequestDto requestDto = new RequestDto();
        requestDto.setHeader(objHeader);
        requestDto.setBody(objBody);
        requestDto.setSignature(objSignature);
        String jsonTopic = JacksonEx.getInstance().object2String(requestDto);
        return jsonTopic;
    }

    /**
     * @param keySearchSoDinhDanhTk
     * @return
     */
    public PageableResponse<DinhDanhHangHoaTkEntity> dsSoDinhDanh(KeySearchSoDinhDanhTk keySearchSoDinhDanhTk) {
        return new PageableResponseEx<DinhDanhHangHoaTkEntity>(dinhDanhHangHoaTkRepositoryImpl.search(keySearchSoDinhDanhTk));
    }

    /**
     * @param toKhaiId
     * @param isSigned
     * @param request
     * @return
     * @throws ResourceNotFoundException
     */
    @Transactional
    public String khaiContainerTk(String documentType, String toKhaiId, Integer isSigned, ContainerTkRequest request) throws ResourceNotFoundException {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            String signedData = request.getDataSigned();
            log.info("<--------------------------message ds containers----------------------------->");
            log.info(signedData);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicId, signedData);
            return signedData;
        }
        /* Lay thong tin to khai */
        Optional<MauToKhaiHqEntity> toKhaiOptional = mauToKhaiHqRepositoryImpl.findById(toKhaiId);
        MauToKhaiHqEntity toKhai = toKhaiOptional.orElse(null);
        if (toKhai == null) {
            throw new ResourceNotFoundException(ExceptionMessage.NOT_FOUND);
        }
        String maDoanhNghiep = toKhai.getMaDoanhNghiep();
        tkContainerRepositoryImpl.updateInactive(toKhaiId, documentType);
        TkContainerEntity tkContainerEntity = new TkContainerEntity();
        tkContainerEntity.setNgayTao(currentDate);
        tkContainerEntity.setNguoiTao(currentUser);
        tkContainerEntity.setMauKbHqId(toKhaiId);
        tkContainerEntity.setLoaiChungTu(documentType);
        tkContainerEntity.setMaChucNang(MessageFuntion.KHAI_BAO.getValue());
        tkContainerEntity.setMauKbHqId(toKhai.getId());
        tkContainerEntity.setHaiQuanTiepNhan(toKhai.getHaiQuanTiepNhanKb());
        tkContainerEntity.setMaNguoiKhaiHq(toKhai.getMaNguoiKhaiHq());
        tkContainerEntity.setTenNguoiKhaiHq(toKhai.getTenNguoiKhaiHq());
        tkContainerEntity.setTenDoanhNghiep(toKhai.getTenDoanhNghiep());
        tkContainerEntity.setMaDoanhNghiep(maDoanhNghiep);
        tkContainerEntity.setMaLoaiHinh(toKhai.getMaLoaiHinh());
        tkContainerEntity.setKenhKhaiBao(toKhai.getKenhKhaiBao());
        tkContainerEntity.setGhiChu(request.getGhiChu());
        tkContainerEntity.setNgayDangKy(toKhai.getNgayDangKy());
        if (toKhai.getLoaiTkHq().equals(MessageType.TK_NHAP_KHAU.getValue())) {
            tkContainerEntity.setDiaChiDoanhNghiep(toKhai.getDiaChiNguoiNk());
            tkContainerEntity.setNoiKhaiBao(toKhai.getDiaChiNguoiNk());
        } else if (toKhai.getLoaiTkHq().equals(MessageType.TK_XUAT_KHAU.getValue())) {
            tkContainerEntity.setDiaChiDoanhNghiep(toKhai.getDiaChiNguoiXk());
            tkContainerEntity.setNoiKhaiBao(toKhai.getDiaChiNguoiXk());
        }
        TkContainerEntity containerInfoInsert = tkContainerRepositoryImpl.insert(tkContainerEntity);
        ContainerNoContentDto contentDto = ContainerTkMapper.INSTANCE.thongTinContainerToContainerNoContent(containerInfoInsert);
        contentDto.setDocumentType(documentType);
        contentDto.setDeclarationNo(toKhai.getSoTk());
        if (documentType.equals(MessageType.CONTAINER_KB.getValue())) {
            // delete old containers
            containerInfoRepositoryImpl.delete(containerInfoInsert.getId());
            List<ContainerNosDto> containerNos = new ArrayList<>();
            for (ContainerRequest item : request.getContainers()) {
                ContainerInfoEntity containerEntity = new ContainerInfoEntity();
                containerEntity.setVanDonSo(item.getSoVanDon());
                containerEntity.setSoContainer(item.getSoContainer());
                containerEntity.setSoSeal(item.getSoSeal());
                containerEntity.setTkContainersId(containerInfoInsert.getId());
                containerEntity.setNgayTao(currentDate);
                containerEntity.setNguoiTao(currentUser);
                containerEntity.setGhiChu(item.getGhiChu());
                containerEntity.setContainer("1");
                containerEntity.setMaDoanhNghiep(maDoanhNghiep);
                ContainerInfoEntity containerTk = containerInfoRepositoryImpl.insert(containerEntity);
                ContainerNosDto containerNosDto = ContainerTkMapper.INSTANCE.tkContainerToContainerNos(containerTk);
                containerNos.add(containerNosDto);
            }
            contentDto.setContainerNos(containerNos);
        }
        /* init SendApplicationDto */
        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(toKhai.getTenDoanhNghiep());
        objsendApplication.setCompanyIdentity(toKhai.getMaDoanhNghiep());
        /* init FromDto */
        FromDto objFrom = new FromDto();
        objFrom.setName(toKhai.getTenNguoiKhaiHq());
        objFrom.setIdentity(toKhai.getMaNguoiKhaiHq());
        /* init ToDto */
        ToDto objTo = new ToDto();
        objTo.setName(toKhai.getTenHaiQuanTiepNhanKb());
        objTo.setIdentity(toKhai.getHaiQuanTiepNhanKb());
        SubjectDto objSubject = new SubjectDto(documentType, MessageFuntion.KHAI_BAO.getValue());
        objSubject.setRefId(containerInfoInsert.getId());
        /* init ReferenceDto */
        ReferenceDto objreference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");
        /* init HeaderSendDto */
        HeaderSendDto objHeader = new HeaderSendDto();
        objHeader.setDeclarantChannel(toKhai.getKenhKhaiBao());
        objHeader.setFrom(objFrom);
        objHeader.setReference(objreference);
        objHeader.setSendApplication(objsendApplication);
        objHeader.setSubject(objSubject);
        objHeader.setTo(objTo);
        /* init SignatureDto */
        SignatureDto objSignature = new SignatureDto();
        objSignature.setData("");
        objSignature.setFileCert("");
        /* init BodyTKDto */
        BodyDto<ContainerNoContentDto> objBody = new BodyDto<>();
        objBody.setContent(contentDto);
        /* init TKMsgDto */
        RequestDto requestDto = new RequestDto();
        requestDto.setHeader(objHeader);
        requestDto.setBody(objBody);
        requestDto.setSignature(objSignature);
        String jsonTopic = JacksonEx.getInstance().object2String(requestDto);
        return jsonTopic;
    }

    /**
     * @param keySearchContainers
     * @return
     */
    @Transactional
    public ContainerTkResponse dsContainers(KeySearchContainers keySearchContainers) throws ResourceNotFoundException {
        ContainerTkResponse res = new ContainerTkResponse();
        TkContainerEntity tkContainerEntity = tkContainerRepositoryImpl.findByToKhaiId(keySearchContainers.getToKhaiId(), keySearchContainers.getDocumentType()).orElse(new TkContainerEntity());
        BeanUtils.copyProperties(tkContainerEntity, res);
        keySearchContainers.setTkContainerId(tkContainerEntity.getId());
        Page<ContainerInfoEntity> dsContainers = containerInfoRepositoryImpl.search(keySearchContainers);
        res.setContainers(new PageableResponseEx<>(dsContainers));
        return res;
    }

    /**
     * @param toKhaiId
     * @param tkContainerId
     * @param isSigned
     * @param signedData
     * @return
     */
    @Transactional
    public String hoiDsContainers(String toKhaiId, String tkContainerId, Integer isSigned, String signedData) {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            log.info("<--------------------------message hỏi tt ds containers----------------------------->");
            log.info(signedData);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicQuestion, signedData);
            return signedData;
        }
        /* Lay thong tin to khai */
        MauToKhaiHqEntity toKhaiHqEntity = mauToKhaiHqRepositoryImpl.findById(toKhaiId).orElse(null);
        if (toKhaiHqEntity == null) {
            throw new ResourceNotFoundException(ExceptionMessage.NOT_FOUND);
        }
        String maDoanhNghiep = toKhaiHqEntity.getMaDoanhNghiep();
        TkContainerEntity tkContainerEntity = tkContainerRepositoryImpl.findById(tkContainerId).orElse(null);
        if (tkContainerEntity == null) {
            throw new ResourceNotFoundException("Không tìm thấy tờ khai.");
        }
        String questionType = MessageFuntion.HOI_THEO_REF_ID.getValue();
        ReferenceDto objReference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");

        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(tkContainerEntity.getTenDoanhNghiep());
        objsendApplication.setCompanyIdentity(tkContainerEntity.getMaDoanhNghiep());

        FromDto objFrom = new FromDto();
        objFrom.setName(tkContainerEntity.getTenNguoiKhaiHq());
        objFrom.setIdentity(tkContainerEntity.getMaNguoiKhaiHq());

        ToDto objTo = new ToDto();
        objTo.setName(toKhaiHqEntity.getTenHaiQuanTiepNhanKb());
        objTo.setIdentity(toKhaiHqEntity.getHaiQuanTiepNhanKb());

        ThongTinXuLyTkEntity ttXuLyTk = new ThongTinXuLyTkEntity();
        ttXuLyTk.setMaChucNang(questionType);
        ttXuLyTk.setMauTkHqId(tkContainerEntity.getId());
        ttXuLyTk.setNgayTao(currentDate);
        ttXuLyTk.setMaDoanhNghiep(maDoanhNghiep);
        ThongTinXuLyTkEntity ttXuLyTkResponse = thongTinXuLyTkRepositoryImpl.insert(ttXuLyTk);

        SubjectDto objSubject = new SubjectDto(tkContainerEntity.getLoaiChungTu());
        objSubject.setRefId(tkContainerEntity.getId());
        objSubject.setFunction(questionType);

        QuestionContentDto objContentTKHoiTT = new QuestionContentDto();
        objContentTKHoiTT.setDocumentType(tkContainerEntity.getLoaiChungTu());
        objContentTKHoiTT.setFunction(questionType);

        objContentTKHoiTT.setRefId(tkContainerEntity.getId());
        objContentTKHoiTT.setDeclarantChannel(tkContainerEntity.getKenhKhaiBao());
        objContentTKHoiTT.setRefCustomsId(tkContainerEntity.getSoTiepNhan());
        if (tkContainerEntity.getKenhKhaiBao() == KenhKhaiBaoEnum.DN_TU_KHAI.getValue()) {
            objContentTKHoiTT.setDeclarantCode(tkContainerEntity.getMaNguoiKhaiHq());
            objContentTKHoiTT.setDeclarantName(tkContainerEntity.getTenNguoiKhaiHq());
        } else {
            objContentTKHoiTT.setDeclarantCode(tkContainerEntity.getMaNguoiKhaiHq());
            objContentTKHoiTT.setDeclarantName(tkContainerEntity.getTenNguoiKhaiHq());
        }
        objContentTKHoiTT.setImporterCode(tkContainerEntity.getMaDoanhNghiep());
        objContentTKHoiTT.setImporterName(tkContainerEntity.getTenDoanhNghiep());
        HeaderSendDto objHeaderSendDto = new HeaderSendDto();
        objHeaderSendDto.setFrom(objFrom);
        objHeaderSendDto.setReference(objReference);
        objHeaderSendDto.setDeclarantChannel(tkContainerEntity.getKenhKhaiBao());
        objHeaderSendDto.setSendApplication(objsendApplication);
        objHeaderSendDto.setSubject(objSubject);
        objHeaderSendDto.setTo(objTo);

        BodyDto<QuestionContentDto> objBodyTKHoiTT = new BodyDto<>();
        objBodyTKHoiTT.setContent(objContentTKHoiTT);

        /* init SignatureDto */
        SignatureDto objSignature = new SignatureDto();
        objSignature.setData("");
        objSignature.setFileCert("");

        QuestionMessageRequestDto requestDto = new QuestionMessageRequestDto();
        requestDto.setHeader(objHeaderSendDto);
        requestDto.setBody(objBodyTKHoiTT);
        requestDto.setSignature(objSignature);
        requestDto.setClientCustomField(ttXuLyTkResponse.getId());
        String jsonToTopic = JacksonEx.getInstance().object2String(requestDto);
        log.info("<--------------------------message hỏi thông tin ds containers----------------------------->");
        log.info(jsonToTopic);
        log.info("<------------------------------------------------------------------------------------>");
        return jsonToTopic;
    }


    @SneakyThrows
    public ResponseEntity<byte[]> exportTKN(String id) {

        Optional<MauToKhaiHqEntity> mauToKhaiHq = mauToKhaiHqRepositoryImpl.findById(id);
        MauToKhaiHqEntity hq = new MauToKhaiHqEntity();
        BeanUtils.copyProperties(mauToKhaiHq.get(), hq);
        String pathTemplate = "classpath:929.jrxml";

        //load file and compile it
        JRBeanCollectionDataSource dsGiayPhep = new JRBeanCollectionDataSource(giayPhepNhapKhauTkRepositoryImpl.findByToKhaiId(id));
        JRBeanCollectionDataSource dsKhoanDieuChinh = new JRBeanCollectionDataSource(khoanDieuChinhTkRepositoryImpl.findByToKhaiId(id));
        JRBeanCollectionDataSource dsVanDon = new JRBeanCollectionDataSource(tkVanDonNkRepositoryImpl.findByToKhaiId(id));
        JRBeanCollectionDataSource dsThongTinTc = new JRBeanCollectionDataSource(ttTrungChuyenRepositoryImpl.findByToKhaiId(id));
        JRBeanCollectionDataSource dsDinhKemKbdt = new JRBeanCollectionDataSource(soDinhKemKbdtRepositoryImpl.findByToKhaiId(id));
        JRBeanCollectionDataSource dsDiaDiemXepHang = new JRBeanCollectionDataSource(diaDiemXepHangTkRepositoryImpl.findByToKhaiId(id));

        Map<String, Object> parameters = new HashMap<>();
//        to khai nhap khau
        List<DmHangHoaTkEntity> dmHangHoaTkEntities = danhDmHangHoaTkRepositoryImpl.findByToKhaiId(hq.getId());

        DmHangHoaTkEntity dmHangHoaTkEntity = new DmHangHoaTkEntity();
        List<DmHangHoaTkEntity> listHangHoa = new ArrayList<>();
        List<DsHangVaThueDto> dsHangVaThueDto_list = new ArrayList<>();

        for (int i = 0; i < dmHangHoaTkEntities.size(); i++) {
            dmHangHoaTkEntity = dmHangHoaTkEntities.get(i);
            DsHangVaThueDto dsHangVaThueDtoEntity = new DsHangVaThueDto();

            dsHangVaThueDtoEntity.setMaHs(dmHangHoaTkEntity.getMaHang());
            dsHangVaThueDtoEntity.setMaQlr(dmHangHoaTkEntity.getMaQlr());
            dsHangVaThueDtoEntity.setTenHang(dmHangHoaTkEntity.getTenHang());
            dsHangVaThueDtoEntity.setTriGiaTinhThue(dmHangHoaTkEntity.getTriGiaTinhThue());
            dsHangVaThueDtoEntity.setThueSuat(dmHangHoaTkEntity.getThueSuat());
            dsHangVaThueDtoEntity.setSttHangTntx(dmHangHoaTkEntity.getSttHangTntx());
            dsHangVaThueDtoEntity.setLuong(dmHangHoaTkEntity.getLuong());
            dsHangVaThueDtoEntity.setTriGiaKb(dmHangHoaTkEntity.getTriGiaKb());
            dsHangVaThueDtoEntity.setLuong2(dmHangHoaTkEntity.getLuong2());
            dsHangVaThueDtoEntity.setDgiaKb(dmHangHoaTkEntity.getDgiaKb());
            dsHangVaThueDtoEntity.setMaMucThueTuyetDoi(dmHangHoaTkEntity.getMaMucThueTuyetDoi());
            dsHangVaThueDtoEntity.setNuocXxMa(dmHangHoaTkEntity.getNuocXxMa());
            dsHangVaThueDtoEntity.setMaNgoaiHanNgach(dmHangHoaTkEntity.getMaNgoaiHanNgach());

            List<ThueKhoanThuKhacEntity> dsthuethukhac = thueKhoanThuKhacRepositoryImpl.findByHangHoaTkId(dmHangHoaTkEntity.getId());
            for (int j = 0; j <= dsthuethukhac.size(); j++) {
                if (dsthuethukhac.size() == 1) {
                    dsHangVaThueDtoEntity.setThueMst1(dsthuethukhac.get(0).getThueMst());
                    dsHangVaThueDtoEntity.setThueMst2("");
                    dsHangVaThueDtoEntity.setThueMst3("");
                    dsHangVaThueDtoEntity.setThueMst4("");
                    dsHangVaThueDtoEntity.setThueMst5("");
                    dsHangVaThueDtoEntity.setMaMienThue1(dsthuethukhac.get(0).getMaMienThue());
                    dsHangVaThueDtoEntity.setMaMienThue2("");
                    dsHangVaThueDtoEntity.setMaMienThue3("");
                    dsHangVaThueDtoEntity.setMaMienThue4("");
                    dsHangVaThueDtoEntity.setMaMienThue5("");
                } else if (dsthuethukhac.size() == 2) {
                    dsHangVaThueDtoEntity.setThueMst1(dsthuethukhac.get(0).getThueMst());
                    dsHangVaThueDtoEntity.setThueMst2(dsthuethukhac.get(1).getThueMst());
                    dsHangVaThueDtoEntity.setThueMst3("");
                    dsHangVaThueDtoEntity.setThueMst4("");
                    dsHangVaThueDtoEntity.setThueMst5("");
                    dsHangVaThueDtoEntity.setMaMienThue1(dsthuethukhac.get(0).getMaMienThue());
                    dsHangVaThueDtoEntity.setMaMienThue2(dsthuethukhac.get(1).getMaMienThue());
                    dsHangVaThueDtoEntity.setMaMienThue3("");
                    dsHangVaThueDtoEntity.setMaMienThue4("");
                    dsHangVaThueDtoEntity.setMaMienThue5("");
                } else if (dsthuethukhac.size() == 3) {
                    dsHangVaThueDtoEntity.setThueMst1(dsthuethukhac.get(0).getThueMst());
                    dsHangVaThueDtoEntity.setThueMst2(dsthuethukhac.get(1).getThueMst());
                    dsHangVaThueDtoEntity.setThueMst3(dsthuethukhac.get(2).getThueMst());
                    dsHangVaThueDtoEntity.setThueMst4("");
                    dsHangVaThueDtoEntity.setThueMst5("");
                    dsHangVaThueDtoEntity.setMaMienThue1(dsthuethukhac.get(0).getMaMienThue());
                    dsHangVaThueDtoEntity.setMaMienThue2(dsthuethukhac.get(1).getMaMienThue());
                    dsHangVaThueDtoEntity.setMaMienThue3(dsthuethukhac.get(2).getMaMienThue());
                    dsHangVaThueDtoEntity.setMaMienThue4("");
                    dsHangVaThueDtoEntity.setMaMienThue5("");
                } else if (dsthuethukhac.size() == 4) {
                    dsHangVaThueDtoEntity.setThueMst1(dsthuethukhac.get(0).getThueMst());
                    dsHangVaThueDtoEntity.setThueMst2(dsthuethukhac.get(1).getThueMst());
                    dsHangVaThueDtoEntity.setThueMst3(dsthuethukhac.get(2).getThueMst());
                    dsHangVaThueDtoEntity.setThueMst4(dsthuethukhac.get(3).getThueMst());
                    dsHangVaThueDtoEntity.setThueMst5("");
                    dsHangVaThueDtoEntity.setMaMienThue1(dsthuethukhac.get(0).getMaMienThue());
                    dsHangVaThueDtoEntity.setMaMienThue2(dsthuethukhac.get(1).getMaMienThue());
                    dsHangVaThueDtoEntity.setMaMienThue3(dsthuethukhac.get(2).getMaMienThue());
                    dsHangVaThueDtoEntity.setMaMienThue4(dsthuethukhac.get(3).getMaMienThue());
                    dsHangVaThueDtoEntity.setMaMienThue5("");
                } else if (dsthuethukhac.size() == 4) {
                    dsHangVaThueDtoEntity.setThueMst1(dsthuethukhac.get(0).getThueMst());
                    dsHangVaThueDtoEntity.setThueMst2(dsthuethukhac.get(1).getThueMst());
                    dsHangVaThueDtoEntity.setThueMst3(dsthuethukhac.get(2).getThueMst());
                    dsHangVaThueDtoEntity.setThueMst4(dsthuethukhac.get(3).getThueMst());
                    dsHangVaThueDtoEntity.setThueMst5(dsthuethukhac.get(4).getThueMst());
                    dsHangVaThueDtoEntity.setMaMienThue1(dsthuethukhac.get(0).getMaMienThue());
                    dsHangVaThueDtoEntity.setMaMienThue2(dsthuethukhac.get(1).getMaMienThue());
                    dsHangVaThueDtoEntity.setMaMienThue3(dsthuethukhac.get(2).getMaMienThue());
                    dsHangVaThueDtoEntity.setMaMienThue4(dsthuethukhac.get(3).getMaMienThue());
                    dsHangVaThueDtoEntity.setMaMienThue5(dsthuethukhac.get(4).getMaMienThue());
                } else {
                    dsHangVaThueDtoEntity.setThueMst1("");
                    dsHangVaThueDtoEntity.setThueMst2("");
                    dsHangVaThueDtoEntity.setThueMst3("");
                    dsHangVaThueDtoEntity.setThueMst4("");
                    dsHangVaThueDtoEntity.setThueMst5("");
                    dsHangVaThueDtoEntity.setMaMienThue1("");
                    dsHangVaThueDtoEntity.setMaMienThue2("");
                    dsHangVaThueDtoEntity.setMaMienThue3("");
                    dsHangVaThueDtoEntity.setMaMienThue4("");
                    dsHangVaThueDtoEntity.setMaMienThue5("");
                }
            }
            dsHangVaThueDto_list.add(dsHangVaThueDtoEntity);
        }
        JRBeanCollectionDataSource dsHang = new JRBeanCollectionDataSource(listHangHoa);

        JRBeanCollectionDataSource dsHangVaThue = new JRBeanCollectionDataSource(dsHangVaThueDto_list);
        parameters.put("dsHangVaThue", dsHangVaThue);

        //du lieu trang 1-1
        parameters.put("soTk", mauToKhaiHq.get().getSoTk());
        parameters.put("soTkDauTien", mauToKhaiHq.get().getSoTkDauTien());
        parameters.put("maLoaiHinh", mauToKhaiHq.get().getMaLoaiHinh());
        parameters.put("tenHaiQuanTiepNhanKb", mauToKhaiHq.get().getTenHaiQuanTiepNhanKb());
        parameters.put("plKths", mauToKhaiHq.get().getPlKths());
        parameters.put("ngayDangKy", mauToKhaiHq.get().getNgayDangKy());
        parameters.put("ngayKhaiDuKien", mauToKhaiHq.get().getNgayKhaiDuKien());
        parameters.put("soTkXktc", mauToKhaiHq.get().getSoTkXktc());
        parameters.put("thoiHanTaiNhap", mauToKhaiHq.get().getThoiHanTaiNhap());
        //du lieu trang 1-2
        parameters.put("maNguoiNk", mauToKhaiHq.get().getMaNguoiNk());
        parameters.put("tenNguoiNk", mauToKhaiHq.get().getTenNguoiNk());
        parameters.put("maBuuChinhNguoiNk", mauToKhaiHq.get().getMaBuuChinhNguoiNk());
        parameters.put("diaChiNguoiNk", mauToKhaiHq.get().getDiaChiNguoiNk());
        parameters.put("sdtNguoiNk", mauToKhaiHq.get().getSdtNguoiNk());
        parameters.put("maNguoiUyThacNk", mauToKhaiHq.get().getMaNguoiUyThacNk());
        parameters.put("tenNguoiUyThacNk", mauToKhaiHq.get().getTenNguoiUyThacNk());
        //du lieu trang 1-3
        parameters.put("maNguoiXk", mauToKhaiHq.get().getMaNguoiXk());
        parameters.put("tenNguoiXk", mauToKhaiHq.get().getTenNguoiXk());
        parameters.put("maBuuChinhNguoiXk", mauToKhaiHq.get().getMaBuuChinhNguoiXk());
        parameters.put("diaChiXk1", mauToKhaiHq.get().getDiaChiXk1());
        parameters.put("diaChiXk2", mauToKhaiHq.get().getDiaChiXk2());
        parameters.put("diaChiXk3", mauToKhaiHq.get().getDiaChiXk3());
        parameters.put("diaChiXk4", mauToKhaiHq.get().getDiaChiXk4());
        parameters.put("maNuocXk", mauToKhaiHq.get().getMaNuocXk());
        //du lieu trang 1-4
        parameters.put("soVanDon", mauToKhaiHq.get().getSoVanDon());
        parameters.put("dsVanDon", dsVanDon);
        parameters.put("soLuongKien", mauToKhaiHq.get().getSoLuongKien());
        parameters.put("tongTrongLuong", mauToKhaiHq.get().getTongTrongLuong());
        parameters.put("soContainer", mauToKhaiHq.get().getSoContainer());
        parameters.put("diaDiemLuuKhoHangCtqdk", mauToKhaiHq.get().getDiaDiemLuuKhoHangCtqdk());
        parameters.put("tenDiaDiemDoHang", mauToKhaiHq.get().getTenDiaDiemDoHang());
        parameters.put("tenDiaDiemXepHang", mauToKhaiHq.get().getTenDiaDiemXepHang());
        parameters.put("tenPhuongTienVanChuyen", mauToKhaiHq.get().getTenPhuongTienVanChuyen());
        parameters.put("ngayDen", mauToKhaiHq.get().getNgayDen());
        parameters.put("kyHieuSoHieu", mauToKhaiHq.get().getKyHieuSoHieu());
        parameters.put("ngayNhapKhoDauTien", mauToKhaiHq.get().getNgayNhapKhoDauTien());
        parameters.put("maVb", mauToKhaiHq.get().getMaVb());
        //du lieu trang 1-5
        parameters.put("hoaDonSo", mauToKhaiHq.get().getHoaDonSo());
        parameters.put("hoaDonSoDienTu", mauToKhaiHq.get().getHoaDonSoDienTu());
        parameters.put("hoaDonNgayPhatHanh", mauToKhaiHq.get().getHoaDonNgayPhatHanh());
        parameters.put("hoaDonPtThanhToan", mauToKhaiHq.get().getHoaDonPtThanhToan());
        parameters.put("hoaDonTongTriGiaKb", mauToKhaiHq.get().getHoaDonTongTriGiaKb());
        parameters.put("tongTriGiaTinhThue", mauToKhaiHq.get().getTongTriGiaTinhThue());
        parameters.put("dcTriGiaTongHsPhanBo", mauToKhaiHq.get().getDcTriGiaTongHsPhanBo());
        parameters.put("ketQuaXuLy", mauToKhaiHq.get().getKetQuaXuLy());
        //du lieu trang 1-6
        parameters.put("dsGiayPhep", dsGiayPhep);
        //du lieu trang 1-7
        parameters.put("tgMaPlTk", mauToKhaiHq.get().getTgMaPlTk());
        parameters.put("dsKhoanDieuChinh", dsKhoanDieuChinh);
        parameters.put("vcPhi", mauToKhaiHq.get().getVcPhi());
        parameters.put("bhPhi", mauToKhaiHq.get().getBhPhi());
        parameters.put("chiTietTkTriGia", mauToKhaiHq.get().getChiTietTkTriGia());
        //du lieu trang 1-8
        parameters.put("tongTriGiaTinhThue", mauToKhaiHq.get().getTongTriGiaTinhThue());
        parameters.put("tongTriGiaTinhThueMaTienTe", mauToKhaiHq.get().getTongTriGiaTinhThueMaTienTe());
        parameters.put("maXacDinhThoiHanNopThue", mauToKhaiHq.get().getMaXacDinhThoiHanNopThue());
        parameters.put("maLyDoDnbp", mauToKhaiHq.get().getMaLyDoDnbp());
        parameters.put("nguoiNopThue", mauToKhaiHq.get().getNguoiNopThue());

//        du lieu trang 2-2
        parameters.put("dsDinhKemKbdt", dsDinhKemKbdt);

        //du lieu trang 2-3
        parameters.put("ghiChu", mauToKhaiHq.get().getGhiChu());
        parameters.put("soQuanLyNbdn", mauToKhaiHq.get().getSoQuanLyNbdn());

        //du lieu trang 2-4
        parameters.put("phanLoaiChiThiHq", mauToKhaiHq.get().getPhanLoaiChiThiHq());

        //du lieu trang 2-5
        parameters.put("ngayCapSoTiepNhan", mauToKhaiHq.get().getNgayCapSoTiepNhan());
        parameters.put("dsThongTinTc", dsThongTinTc);

//        du lieu trang 3-2
        parameters.put("maNgoaiHanNgach", dmHangHoaTkEntity.getMaNgoaiHanNgach());
        parameters.put("maHs", dmHangHoaTkEntity.getMaHs());
        parameters.put("tenHang", dmHangHoaTkEntity.getTenHang());
        parameters.put("maQlr", dmHangHoaTkEntity.getMaQlr());
        parameters.put("soDanhMucKhoanDieuChinh", dmHangHoaTkEntity.getSoDanhMucKhoanDieuChinh());
        parameters.put("triGiaTinhThue", dmHangHoaTkEntity.getTriGiaTinhThue());
        parameters.put("thueSuat", dmHangHoaTkEntity.getThueSuat());
        parameters.put("thueGiam", dmHangHoaTkEntity.getThueGiam());
        parameters.put("sttHangTntx", dmHangHoaTkEntity.getSttHangTntx());
        parameters.put("luong", dmHangHoaTkEntity.getLuong());
        parameters.put("luong2", dmHangHoaTkEntity.getLuong2());
        parameters.put("dgiaKb", dmHangHoaTkEntity.getDgiaKb());
        parameters.put("maMucThueTuyetDoi", dmHangHoaTkEntity.getMaMucThueTuyetDoi());
        parameters.put("nuocXxMa", dmHangHoaTkEntity.getNuocXxMa());
        parameters.put("dsHang", dsHang);
        parameters.put("hoaDonMaTienTe", mauToKhaiHq.get().getHoaDonMaTienTe());
        parameters.put("ngayTao", mauToKhaiHq.get().getNgayTao());
        parameters.put("sdtNguoiXk", mauToKhaiHq.get().getSdtNguoiXk());
        parameters.put("thoiHanTaiXuat", mauToKhaiHq.get().getThoiHanTaiXuat());
        parameters.put("maNguoiUyThacXk", mauToKhaiHq.get().getMaNguoiXk());
        parameters.put("maDiaDiemNhanHangCuoi", mauToKhaiHq.get().getMaDiaDiemNhanHangCuoi());
        parameters.put("ngayHangDiDuKien", mauToKhaiHq.get().getNgayHangDiDuKien());
        parameters.put("tenNguoiUyThacXk", mauToKhaiHq.get().getTenNguoiUyThacXk());
        parameters.put("maNuocNk", mauToKhaiHq.get().getMaNuocNk());
        parameters.put("diaDiemDichVcbt", mauToKhaiHq.get().getDiaDiemDichVcbt());
        parameters.put("plKhongCanQuyDoiVND", mauToKhaiHq.get().getPlKhongCanQuyDoiVND());
        parameters.put("dsDiaDiemXepHang", dsDiaDiemXepHang);

        return JasperReportsEx.getResponseEntityByTemplate(parameters, pathTemplate, "toKhaiNhap.pdf");
    }

    public ResponseEntity<byte[]> exportTKX(String id) {
        Optional<MauToKhaiHqEntity> mauToKhaiHq = mauToKhaiHqRepositoryImpl.findById(id);
        MauToKhaiHqEntity hq = new MauToKhaiHqEntity();
        BeanUtils.copyProperties(mauToKhaiHq.get(), hq);
        String pathTemplate = "classpath:930.jrxml";

        //load file and compile it
        JRBeanCollectionDataSource dsGiayPhep = new JRBeanCollectionDataSource(giayPhepNhapKhauTkRepositoryImpl.findByToKhaiId(id));
        JRBeanCollectionDataSource dsKhoanDieuChinh = new JRBeanCollectionDataSource(khoanDieuChinhTkRepositoryImpl.findByToKhaiId(id));
        JRBeanCollectionDataSource dsVanDon = new JRBeanCollectionDataSource(tkVanDonNkRepositoryImpl.findByToKhaiId(id));
        JRBeanCollectionDataSource dsThongTinTc = new JRBeanCollectionDataSource(ttTrungChuyenRepositoryImpl.findByToKhaiId(id));
        JRBeanCollectionDataSource dsDinhKemKbdt = new JRBeanCollectionDataSource(soDinhKemKbdtRepositoryImpl.findByToKhaiId(id));
        JRBeanCollectionDataSource dsHang = new JRBeanCollectionDataSource(danhDmHangHoaTkRepositoryImpl.findByToKhaiId(id));
        JRBeanCollectionDataSource dsDiaDiemXepHang = new JRBeanCollectionDataSource(diaDiemXepHangTkRepositoryImpl.findByToKhaiId(id));
        List<DmHangHoaTkEntity> dsHangHoa = danhDmHangHoaTkRepositoryImpl.findByToKhaiId(id);

        DmHangHoaTkEntity dmHangHoab = new DmHangHoaTkEntity();
        for (DmHangHoaTkEntity item : dsHangHoa) {
            BeanUtils.copyProperties(item, dmHangHoab);
        }
        JRBeanCollectionDataSource dsThueKhoanThuKhac = new JRBeanCollectionDataSource(thueKhoanThuKhacRepositoryImpl.findByHangHoaTkId(dmHangHoab.getId()));

        Optional<TkContainerEntity> tkContainer = tkContainerRepositoryImpl.findByToKhaiId(id, "303");

        List<ContainerInfoEntity> containerInfor = containerInfoRepositoryImpl.findByTkContainerId(tkContainer.get().getId());

        Map<String, Object> parameters = new HashMap<>();
//        to khai nhap khau

        for (int i = 0; i < 50; i++) {
            if (containerInfor.size() > i) {
                ContainerInfoEntity item = containerInfor.get(i);
                int o = i + 1;
                parameters.put("containerNo" + o, item.getSoContainer());
            } else {
                int x = i + 1;
                parameters.put("containerNo" + x, "");
            }
        }

        //du lieu trang 1-1
        parameters.put("soTk", mauToKhaiHq.get().getSoTk());
        parameters.put("soTkDauTien", mauToKhaiHq.get().getSoTkDauTien());
        parameters.put("maLoaiHinh", mauToKhaiHq.get().getMaLoaiHinh());
        parameters.put("tenHaiQuanTiepNhanKb", mauToKhaiHq.get().getTenHaiQuanTiepNhanKb());
        parameters.put("plKths", mauToKhaiHq.get().getPlKths());
        parameters.put("ngayDangKy", mauToKhaiHq.get().getNgayDangKy());
        parameters.put("ngayKhaiDuKien", mauToKhaiHq.get().getNgayKhaiDuKien());
        parameters.put("soTkXktc", mauToKhaiHq.get().getSoTkXktc());
        parameters.put("thoiHanTaiNhap", mauToKhaiHq.get().getThoiHanTaiNhap());
        //du lieu trang 1-2
        parameters.put("maNguoiNk", mauToKhaiHq.get().getMaNguoiNk());
        parameters.put("tenNguoiNk", mauToKhaiHq.get().getTenNguoiNk());
        parameters.put("maBuuChinhNguoiNk", mauToKhaiHq.get().getMaBuuChinhNguoiNk());
        parameters.put("diaChiNguoiNk", mauToKhaiHq.get().getDiaChiNguoiNk());
        parameters.put("sdtNguoiNk", mauToKhaiHq.get().getSdtNguoiNk());
        parameters.put("maNguoiUyThacNk", mauToKhaiHq.get().getMaNguoiUyThacNk());
        parameters.put("tenNguoiUyThacNk", mauToKhaiHq.get().getTenNguoiUyThacNk());
        //du lieu trang 1-3
        parameters.put("maNguoiXk", mauToKhaiHq.get().getMaNguoiXk());
        parameters.put("tenNguoiXk", mauToKhaiHq.get().getTenNguoiXk());
        parameters.put("maBuuChinhNguoiXk", mauToKhaiHq.get().getMaBuuChinhNguoiXk());
        parameters.put("diaChiXk1", mauToKhaiHq.get().getDiaChiXk1());
        parameters.put("diaChiXk2", mauToKhaiHq.get().getDiaChiXk2());
        parameters.put("diaChiXk3", mauToKhaiHq.get().getDiaChiXk3());
        parameters.put("diaChiXk4", mauToKhaiHq.get().getDiaChiXk4());
        parameters.put("maNuocXk", mauToKhaiHq.get().getMaNuocXk());
        //du lieu trang 1-4
        parameters.put("soVanDon", mauToKhaiHq.get().getSoVanDon());
        parameters.put("dsVanDon", dsVanDon);
        parameters.put("soLuongKien", mauToKhaiHq.get().getSoLuongKien());
        parameters.put("tongTrongLuong", mauToKhaiHq.get().getTongTrongLuong());
        parameters.put("soContainer", mauToKhaiHq.get().getSoContainer());
        parameters.put("diaDiemLuuKhoHangCtqdk", mauToKhaiHq.get().getDiaDiemLuuKhoHangCtqdk());
        parameters.put("tenDiaDiemDoHang", mauToKhaiHq.get().getTenDiaDiemDoHang());
        parameters.put("tenDiaDiemXepHang", mauToKhaiHq.get().getTenDiaDiemXepHang());
        parameters.put("tenPhuongTienVanChuyen", mauToKhaiHq.get().getTenPhuongTienVanChuyen());
        parameters.put("ngayDen", mauToKhaiHq.get().getNgayDen());
        parameters.put("kyHieuSoHieu", mauToKhaiHq.get().getKyHieuSoHieu());
        parameters.put("ngayNhapKhoDauTien", mauToKhaiHq.get().getNgayNhapKhoDauTien());
        parameters.put("maVb", mauToKhaiHq.get().getMaVb());
        //du lieu trang 1-5
        parameters.put("hoaDonSo", mauToKhaiHq.get().getHoaDonSo());
        parameters.put("hoaDonSoDienTu", mauToKhaiHq.get().getHoaDonSoDienTu());
        parameters.put("hoaDonNgayPhatHanh", mauToKhaiHq.get().getHoaDonNgayPhatHanh());
        parameters.put("hoaDonPtThanhToan", mauToKhaiHq.get().getHoaDonPtThanhToan());
        parameters.put("hoaDonTongTriGiaKb", mauToKhaiHq.get().getHoaDonTongTriGiaKb());
        parameters.put("tongTriGiaTinhThue", mauToKhaiHq.get().getTongTriGiaTinhThue());
        parameters.put("dcTriGiaTongHsPhanBo", mauToKhaiHq.get().getDcTriGiaTongHsPhanBo());
        parameters.put("ketQuaXuLy", mauToKhaiHq.get().getKetQuaXuLy());
        //du lieu trang 1-6
        parameters.put("dsGiayPhep", dsGiayPhep);
        //du lieu trang 1-7
        parameters.put("tgMaPlTk", mauToKhaiHq.get().getTgMaPlTk());
        parameters.put("dsKhoanDieuChinh", dsKhoanDieuChinh);
        parameters.put("vcPhi", mauToKhaiHq.get().getVcPhi());
        parameters.put("bhPhi", mauToKhaiHq.get().getBhPhi());
        parameters.put("chiTietTkTriGia", mauToKhaiHq.get().getChiTietTkTriGia());
        //du lieu trang 1-8
        parameters.put("tongTriGiaTinhThue", mauToKhaiHq.get().getTongTriGiaTinhThue());
        parameters.put("tongTriGiaTinhThueMaTienTe", mauToKhaiHq.get().getTongTriGiaTinhThueMaTienTe());
        parameters.put("maXacDinhThoiHanNopThue", mauToKhaiHq.get().getMaXacDinhThoiHanNopThue());
        parameters.put("maLyDoDnbp", mauToKhaiHq.get().getMaLyDoDnbp());
        parameters.put("nguoiNopThue", mauToKhaiHq.get().getNguoiNopThue());

//        du lieu trang 2-2
        parameters.put("dsDinhKemKbdt", dsDinhKemKbdt);

        //du lieu trang 2-3
        parameters.put("ghiChu", mauToKhaiHq.get().getGhiChu());
        parameters.put("soQuanLyNbdn", mauToKhaiHq.get().getSoQuanLyNbdn());

        //du lieu trang 2-4
        parameters.put("phanLoaiChiThiHq", mauToKhaiHq.get().getPhanLoaiChiThiHq());

        //du lieu trang 2-5
        parameters.put("ngayCapSoTiepNhan", mauToKhaiHq.get().getNgayCapSoTiepNhan());
        parameters.put("dsThongTinTc", dsThongTinTc);

//        du lieu trang 3-2
        parameters.put("maNgoaiHanNgach", dmHangHoab.getMaNgoaiHanNgach());
        parameters.put("maHs", dmHangHoab.getMaHs());
        parameters.put("tenHang", dmHangHoab.getTenHang());
        parameters.put("maQlr", dmHangHoab.getMaQlr());
        parameters.put("soDanhMucKhoanDieuChinh", dmHangHoab.getSoDanhMucKhoanDieuChinh());
        parameters.put("triGiaTinhThue", dmHangHoab.getTriGiaTinhThue());
        parameters.put("thueSuat", dmHangHoab.getThueSuat());
        parameters.put("thueGiam", dmHangHoab.getThueGiam());
        parameters.put("sttHangTntx", dmHangHoab.getSttHangTntx());
        parameters.put("luong", dmHangHoab.getLuong());
        parameters.put("luong2", dmHangHoab.getLuong2());
        parameters.put("dgiaKb", dmHangHoab.getDgiaKb());
        parameters.put("maMucThueTuyetDoi", dmHangHoab.getMaMucThueTuyetDoi());
        parameters.put("nuocXxMa", dmHangHoab.getNuocXxMa());

//        du lieu trang 3-2
        parameters.put("dsHang", dsHang);
        parameters.put("dsThueKhoanThuKhac", dsThueKhoanThuKhac);

//        to khai xuat khau
//        trang 1-6
        parameters.put("hoaDonMaTienTe", mauToKhaiHq.get().getHoaDonMaTienTe());
//        trang 1-11
        parameters.put("ngayTao", mauToKhaiHq.get().getNgayTao());
//        trang 2-1
        parameters.put("sdtNguoiXk", mauToKhaiHq.get().getSdtNguoiXk());
        parameters.put("thoiHanTaiXuat", mauToKhaiHq.get().getThoiHanTaiXuat());
        parameters.put("maNguoiUyThacXk", mauToKhaiHq.get().getMaNguoiXk());
        parameters.put("maDiaDiemNhanHangCuoi", mauToKhaiHq.get().getMaDiaDiemNhanHangCuoi());
        parameters.put("ngayHangDiDuKien", mauToKhaiHq.get().getNgayHangDiDuKien());
        parameters.put("tenNguoiUyThacXk", mauToKhaiHq.get().getTenNguoiUyThacXk());
        parameters.put("maNuocNk", mauToKhaiHq.get().getMaNuocNk());
        parameters.put("diaDiemDichVcbt", mauToKhaiHq.get().getDiaDiemDichVcbt());
        parameters.put("plKhongCanQuyDoiVND", mauToKhaiHq.get().getPlKhongCanQuyDoiVND());
//        trang 2-2
        parameters.put("dsDiaDiemXepHang", dsDiaDiemXepHang);

        return JasperReportsEx.getResponseEntityByTemplate(parameters, pathTemplate, "toKhaiXuat.pdf");
    }
}