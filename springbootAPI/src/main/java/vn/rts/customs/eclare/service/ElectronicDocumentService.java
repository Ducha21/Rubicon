package vn.rts.customs.eclare.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.rts.customs.eclare.dto.body.CtdtBodyDto;
import vn.rts.customs.eclare.dto.body.SignatureDto;
import vn.rts.customs.eclare.dto.body.electronic_doc.*;
import vn.rts.customs.eclare.dto.header.*;
import vn.rts.customs.eclare.entity.*;
import vn.rts.customs.eclare.enums.MessageFuntion;
import vn.rts.customs.eclare.enums.MessageType;
import vn.rts.customs.eclare.mapper.ctdt.giayphep.*;
import vn.rts.customs.eclare.repository.impl.*;
import vn.rts.customs.eclare.request.ctdt.*;
import vn.rts.customs.eclare.request.search.KeySearchChungTuDinhKemTk;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare;
import vn.rts.customs.eclare.util.JsonUtils;
import vn.rts.customs.eclare.util.VnaccsConvert;
import vn.rts.customs.lib.exception.BadRequestException;
import vn.rts.customs.lib.exception.ConflictException;
import vn.rts.customs.lib.exception.ResourceNotFoundException;
import vn.rts.customs.lib.key.SequenceKey;
import vn.rts.customs.lib.service.WebApiBaseServiceImplWithSp;
import vn.rts.customs.lib.util.JacksonEx;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ElectronicDocumentService extends
        WebApiBaseServiceImplWithSp<ChungTuDinhKemTkEntity, String, SequenceKey, ChungTuDinhKemTkRepositoryImpl, KeySearchListObj> {

    /* declare topic id */
    @Value("${spring.kafka.producer.topic-tcp-declare-id}")
    private String sTopicId;
    /* question topic id */
    @Value("${spring.kafka.producer.topic-tcp-question-id}")
    private String sTopicQuestion;

    @Autowired
    private ChungTuDinhKemTkRepositoryImpl chungTuDinhKemTkRepositoryImpl;

    @Autowired
    private CtdtGiayPhepRepositoryImpl ctdtGiayPhepRepositoryImpl;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private CtdtHoaDonTmRepositoryImpl ctdtHoaDonTmRepositoryImpl;

    @Autowired
    private CtdtCtat5PhanTramRepositoryImpl ctdtCtat5PhanTramRepositoryImpl;

    @Autowired
    private CtdtFileDinhKemRepositoryImpl ctdtFileDinhKemRepositoryImpl;

    @Autowired
    private MauToKhaiHqRepositoryImpl mauToKhaiHqRepositoryImpl;

    @Autowired
    private CtdtCoRepositoryImpl ctdtCoRepositoryImpl;

    @Autowired
    private CtdtVanDonRepositoryImpl ctdtVanDonRepositoryImpl;

    @Autowired
    private CtdtVanDonContainerRepositoryImpl ctdtVanDonContainerRepositoryImpl;

    @Autowired
    private CtdtVanDonNhanhRepositoryImpl ctdtVanDonNhanhRepositoryImpl;

    @Autowired
    private CtdtBkhhRepositoryImpl ctdtBkhhRepositoryImpl;

    @Autowired
    private CtdtGiayCnktcnRepositoryImpl ctdtGiayCnktcnRepositoryImpl;

    @Autowired
    private CtdtMmtbRepositoryImpl ctdtMmtbRepositoryImpl;

    @Autowired
    private CtdtCmtcRepositoryImpl ctdtCmtcRepositoryImpl;

    @Autowired
    private CtdtTktgRepositoryImpl ctdtTktgRepositoryImpl;

    @Autowired
    private CtdtHopDongUtRepositoryImpl ctdtHopDongUtRepositoryImpl;

    @Autowired
    private DmHangHoaTkRepositoryImpl dmHangHoaTkRepositoryImpl;

    /**
     * @param ctdtGiayPhepRequests
     * @param mauTkHqId
     * @param isSigned
     * @return
     * @throws ResourceNotFoundException
     * @throws JSONException
     */
    @Transactional
    public String guiCtdtGiayPhep(CtdtGiayPhepRequests ctdtGiayPhepRequests, String mauTkHqId, Integer isSigned) throws ResourceNotFoundException {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            String dataSigned = ctdtGiayPhepRequests.getDataSigned();
            String refId = (String) JsonUtils.getValueWithElement(dataSigned, "$.body.content.refId");
            ChungTuDinhKemTkEntity params = new ChungTuDinhKemTkEntity();
            params.setTrangThai("1"); // Da gui
            params.setId(refId);
            params.setNgaySua(currentDate);
            ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.updateStatus(params);
            if (chungTuDinhKemTkEntity == null) {
                throw new ConflictException("Cập nhật trạng thái chứng từ không thành công.");
            }
            log.info("<--------------------------message tờ khai nhập----------------------------->");
            log.info(dataSigned);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicId, dataSigned);
            return "Gửi chứng từ lên hệ thống tiếp nhận hải quan thành công.";
        }
        Optional<MauToKhaiHqEntity> mauToKhaiHqEntity = mauToKhaiHqRepositoryImpl.findById(mauTkHqId);
        if (!mauToKhaiHqEntity.isPresent()) {
            throw new BadRequestException("Không tồn tại mẫu tờ khai hải quan có id " + mauTkHqId);
        }
        if (mauToKhaiHqEntity.get().getSoTiepNhan() == null) {
            throw new BadRequestException("Tờ khai hải quan có id " + mauTkHqId + " chưa có số tiếp nhận");
        }
        MauToKhaiHqEntity toKhai = mauToKhaiHqEntity.get();
        String maDoanhNghiep = toKhai.getMaDoanhNghiep();
        ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = new ChungTuDinhKemTkEntity();
        chungTuDinhKemTkEntity.setMauTkHqId(toKhai.getId());
        chungTuDinhKemTkEntity.setLoaiChungTu(MessageType.CTDT_GIAY_PHEP.getValue());
        chungTuDinhKemTkEntity.setKenhKhaiBao(toKhai.getKenhKhaiBao());
        chungTuDinhKemTkEntity.setChucNangChungTu(MessageFuntion.KHAI_BAO.getValue());
        chungTuDinhKemTkEntity.setNoiKhaiBao(toKhai.getHaiQuanTiepNhanKb());
        chungTuDinhKemTkEntity.setHqTiepNhanChungTu(toKhai.getHaiQuanTiepNhanKb()); // Cần confirm lại
        chungTuDinhKemTkEntity.setNgayTao(currentDate);
        chungTuDinhKemTkEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
        chungTuDinhKemTkEntity.setMaDoanhNghiep(maDoanhNghiep);
        chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.insert(chungTuDinhKemTkEntity);

        // Object LicenseContentDto đã extend từ object chứa thông tin chung nên ta map
        // entity thông tin chung vào object này
        LicenseContentDto licenseContentDto = CtdtGiayPhepMapper.INSTANCE
                .chungTuDinhKemTkToLicenseContentDto(chungTuDinhKemTkEntity);

        licenseContentDto.setRefCustomsId(String.valueOf(toKhai.getSoTiepNhan()));
        /** Người khai HQ */
        licenseContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        licenseContentDto.setDeclarantName(toKhai.getTenNguoiKhaiHq());
        /** Doanh nghiệp XNK */
        if (toKhai.getLoaiTkHq().equals(MessageType.TK_NHAP_KHAU.getValue())) {
            licenseContentDto.setImporterCode(toKhai.getMaNguoiNk());
            licenseContentDto.setImporterName(toKhai.getTenNguoiNk());
            licenseContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        } else {
            licenseContentDto.setImporterCode(toKhai.getMaNguoiXk());
            licenseContentDto.setImporterName(toKhai.getTenNguoiXk());
            licenseContentDto.setImporterAddress(toKhai.getDiaChiNguoiXk());
        }
        /** Thông tin tờ khai */
        licenseContentDto.setDeclarantCode(toKhai.getSoTk());
        licenseContentDto.setRegisteredDate(VnaccsConvert.date2String(toKhai.getNgayTraLoi(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD.getValueEnum()));
        licenseContentDto.setDeclarationKindCode(toKhai.getMaLoaiHinh());
        licenseContentDto.setDeclarationOffice(toKhai.getHaiQuanTiepNhanKb());

        licenseContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        licenseContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        licenseContentDto.setDeclarationNo(toKhai.getSoTk());

        // List object license để gửi lên hải quan
        List<LicenseContentDto.License> licenses = new ArrayList<>();

        for (CtdtGiayPhepRequests.CtdtGiayPhepRequest item : ctdtGiayPhepRequests.getCtdt()) {
            CtdtGiayPhepEntity ctdtGiayPhepEntity = new CtdtGiayPhepEntity();
            BeanUtils.copyProperties(item, ctdtGiayPhepEntity);
            ctdtGiayPhepEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());

            // add goodsitems to license
            List<LicenseContentDto.GoodItem> goodsItems = new ArrayList<>();
            StringBuilder dsHang = new StringBuilder();
            for (CtdtGiayPhepRequests.CtdtThongTinHangHoaRequest ctdtThongTinHangHoaRequest : item
                    .getDsHang()) {
                // Update ghi chu khac hang hoa
                DmHangHoaTkEntity dmHangHoaTkEntity = dmHangHoaTkRepositoryImpl.findById(ctdtThongTinHangHoaRequest.getId()).orElse(null);
                if (dmHangHoaTkEntity == null) {
                    throw new ResourceNotFoundException("Không tìm thấy thông tin hàng hóa.");
                }
                dmHangHoaTkRepositoryImpl.update(dmHangHoaTkEntity);
                LicenseContentDto.GoodItem goodsItem = CtdtGiayPhepMapper.INSTANCE
                        .ctdtThongTinHangHoaRequestToGoodsItems(ctdtThongTinHangHoaRequest);
                goodsItems.add(goodsItem);
                if (dsHang.length() == 0) {
                    dsHang.append(ctdtThongTinHangHoaRequest.getId().toString());
                } else {
                    dsHang.append(",");
                    dsHang.append(ctdtThongTinHangHoaRequest.getId().toString());
                }
            }
            ctdtGiayPhepEntity.setHangHoaId(dsHang.toString());
            ctdtGiayPhepEntity.setNgayTao(currentDate);
            ctdtGiayPhepEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtGiayPhepEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtGiayPhepEntity = ctdtGiayPhepRepositoryImpl.insert(ctdtGiayPhepEntity);
            // Map entity license sang dto license
            LicenseContentDto.License license = CtdtGiayPhepMapper.INSTANCE
                    .ctdtGiayPhepToLicenseContentDto(ctdtGiayPhepEntity);
            CtdtFileDinhKemEntity ctdtFileDinhKemEntity = new CtdtFileDinhKemEntity();
            BeanUtils.copyProperties(item.getFileDinhKem(), ctdtFileDinhKemEntity);
            String fileId = this.uploadFileV6(Base64.decodeBase64(item.getFileDinhKem().getFile()),
                    item.getFileDinhKem().getTenFileChungTu(), "0000", "");
            ctdtFileDinhKemEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtFileDinhKemEntity.setFileId(fileId);
            ctdtFileDinhKemEntity.setLoaiChungTuId(ctdtGiayPhepEntity.getId());
            ctdtFileDinhKemEntity.setNgayTao(currentDate);
            ctdtFileDinhKemEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtFileDinhKemEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtFileDinhKemEntity = ctdtFileDinhKemRepositoryImpl.insert(ctdtFileDinhKemEntity);

            license.setGoodsItems(goodsItems);
            // Set thông tin file trong object license dto
            license.setFileName(ctdtFileDinhKemEntity.getTenFileChungTu());
            licenses.add(license);
        }
        licenseContentDto.setLicenses(licenses);
        /* init ReferenceDto */
        ReferenceDto objreference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");
        /* init SendApplicationDto */
        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(toKhai.getTenNguoiKhaiHq());
        objsendApplication.setCompanyIdentity(toKhai.getMaNguoiKhaiHq());
        /* init FromDto */
        FromDto objFrom = new FromDto();
        objFrom.setName(toKhai.getTenNguoiKhaiHq());
        objFrom.setIdentity(toKhai.getMaNguoiKhaiHq());
        /* init ToDto */
        ToDto objTo = new ToDto();
        objTo.setName(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        objTo.setIdentity(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        /* init SubjectDto */

        SubjectDto objSubject = new SubjectDto(MessageType.CTDT_GIAY_PHEP.getValue(), MessageFuntion.KHAI_BAO.getValue());
        objSubject.setRefId(chungTuDinhKemTkEntity.getId());

        /* init BodyTKDto */
        CtdtBodyDto<LicenseContentDto> objBody = new CtdtBodyDto<>();
        objBody.setContent(licenseContentDto);

        /* init HeaderSendDto */
        HeaderSendDto objHeader = new HeaderSendDto();
        objHeader.setDeclarantChannel(mauToKhaiHqEntity.get().getKenhKhaiBao());
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
        CtdtLicenseRequestDto ctdtLicenseRequestDto = new CtdtLicenseRequestDto();
        ctdtLicenseRequestDto.setHeader(objHeader);
        ctdtLicenseRequestDto.setBody(objBody);
        ctdtLicenseRequestDto.setSignature(objSignature);
        String jsonTopic = JacksonEx.getInstance().object2String(ctdtLicenseRequestDto);
        return jsonTopic;
    }

    /**
     * @param ctdtHoaDonTmRequests
     * @param mauTkHqId
     * @param isSigned
     * @return
     */
    @Transactional
    public String khaiHoaDonThuongMai(CtdtHoaDonTmRequests ctdtHoaDonTmRequests, String mauTkHqId, Integer isSigned) {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            String dataSigned = ctdtHoaDonTmRequests.getDataSigned();
            String refId = (String) JsonUtils.getValueWithElement(dataSigned, "$.body.content.refId");
            ChungTuDinhKemTkEntity params = new ChungTuDinhKemTkEntity();
            params.setTrangThai("1"); // Da gui
            params.setId(refId);
            params.setNgaySua(currentDate);
            ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.updateStatus(params);
            if (chungTuDinhKemTkEntity == null) {
                throw new ConflictException("Cập nhật trạng thái chứng từ không thành công.");
            }
            log.info("<--------------------------message tờ khai nhập----------------------------->");
            log.info(dataSigned);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicId, dataSigned);
            return "Gửi chứng từ lên hệ thống tiếp nhận hải quan thành công.";
        }
        Optional<MauToKhaiHqEntity> mauToKhaiHqEntity = mauToKhaiHqRepositoryImpl.findById(mauTkHqId);
        if (!mauToKhaiHqEntity.isPresent()) {
            throw new BadRequestException("Không tồn tại mẫu tờ khai hải quan có id " + mauTkHqId);
        }
        if (mauToKhaiHqEntity.get().getSoTiepNhan() == null) {
            throw new BadRequestException("Tờ khai hải quan có id " + mauTkHqId + " chưa có số tiếp nhận");
        }

        // Set thông tin và lưu ChungTuDinhKemTkEntity
        MauToKhaiHqEntity toKhai = mauToKhaiHqEntity.get();
        String maDoanhNghiep = toKhai.getMaDoanhNghiep();
        ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = new ChungTuDinhKemTkEntity();
        chungTuDinhKemTkEntity.setMauTkHqId(toKhai.getId());
        chungTuDinhKemTkEntity.setLoaiChungTu(MessageType.CTDT_HOA_DON_TM.getValue());
        chungTuDinhKemTkEntity.setKenhKhaiBao(toKhai.getKenhKhaiBao());
        chungTuDinhKemTkEntity.setChucNangChungTu(MessageFuntion.KHAI_BAO.getValue());
        chungTuDinhKemTkEntity.setNoiKhaiBao(toKhai.getHaiQuanTiepNhanKb());
        chungTuDinhKemTkEntity.setHqTiepNhanChungTu(toKhai.getHaiQuanTiepNhanKb()); // Cần confirm lại
        chungTuDinhKemTkEntity.setNgayTao(currentDate);
        chungTuDinhKemTkEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
        chungTuDinhKemTkEntity.setMaDoanhNghiep(maDoanhNghiep);
        chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.insert(chungTuDinhKemTkEntity);
        // Object LicenseContentDto đã extend từ object chứa thông tin chung nên ta map
        // entity thông tin chung vào object này
        InvoiceContentDto invoiceContentDto = CtdtHoaDonTmMapper.INSTANCE
                .chungTuDinhKemToKhaiToInvoiceContentDto(chungTuDinhKemTkEntity);

        invoiceContentDto.setRefCustomsId(String.valueOf(toKhai.getSoTiepNhan()));
        /** Người khai HQ */
        invoiceContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        invoiceContentDto.setDeclarantName(toKhai.getTenNguoiKhaiHq());
        /** Doanh nghiệp XNK */
        if (toKhai.getLoaiTkHq().equals(MessageType.TK_NHAP_KHAU.getValue())) {
            invoiceContentDto.setImporterCode(toKhai.getMaNguoiNk());
            invoiceContentDto.setImporterName(toKhai.getTenNguoiNk());
            invoiceContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        } else {
            invoiceContentDto.setImporterCode(toKhai.getMaNguoiXk());
            invoiceContentDto.setImporterName(toKhai.getTenNguoiXk());
            invoiceContentDto.setImporterAddress(toKhai.getDiaChiNguoiXk());
        }
        /** Thông tin tờ khai */
        invoiceContentDto.setDeclarantCode(toKhai.getSoTk());
        invoiceContentDto.setRegisteredDate(VnaccsConvert.date2String(toKhai.getNgayTraLoi(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD.getValueEnum()));
        invoiceContentDto.setDeclarationKindCode(toKhai.getMaLoaiHinh());
        invoiceContentDto.setDeclarationOffice(toKhai.getHaiQuanTiepNhanKb());

        invoiceContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        invoiceContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        invoiceContentDto.setDeclarationNo(toKhai.getSoTk());

        // List object license để gửi lên hải quan
        List<InvoiceContentDto.Invoice> invoices = new ArrayList<>();
        for (CtdtHoaDonTmRequests.CtdtHoaDonTmRequest item : ctdtHoaDonTmRequests.getCtdt()) {
            CtdtHoaDonTmEntity ctdtHoaDonTmEntity = new CtdtHoaDonTmEntity();
            BeanUtils.copyProperties(item, ctdtHoaDonTmEntity);
            ctdtHoaDonTmEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtHoaDonTmEntity.setNgayTao(currentDate);
            ctdtHoaDonTmEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtHoaDonTmEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtHoaDonTmEntity = ctdtHoaDonTmRepositoryImpl.insert(ctdtHoaDonTmEntity);

            InvoiceContentDto.Invoice invoice = CtdtHoaDonTmMapper.INSTANCE
                    .ctdtHoaDonTmToInvoiceContentDto(ctdtHoaDonTmEntity);

            CtdtFileDinhKemEntity ctdtFileDinhKemEntity = new CtdtFileDinhKemEntity();
            BeanUtils.copyProperties(item.getFileDinhKem(), ctdtFileDinhKemEntity);
            String fileId = this.uploadFileV6(Base64.decodeBase64(item.getFileDinhKem().getFile()),
                    item.getFileDinhKem().getTenFileChungTu(), "0000", "");
            ctdtFileDinhKemEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtFileDinhKemEntity.setFileId(fileId);
            ctdtFileDinhKemEntity.setLoaiChungTuId(ctdtHoaDonTmEntity.getId());
            ctdtFileDinhKemEntity.setNgayTao(currentDate);
            ctdtFileDinhKemEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtFileDinhKemEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtFileDinhKemEntity = ctdtFileDinhKemRepositoryImpl.insert(ctdtFileDinhKemEntity);

            // Set thông tin file vào object Invoice gửi lên hải quan
            invoice.setFileName(ctdtFileDinhKemEntity.getTenFileChungTu());
            // Thêm invoice vào list invoice
            invoices.add(invoice);
        }
        invoiceContentDto.setInvoices(invoices);

        /* init ReferenceDto */
        ReferenceDto objreference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");
        /* init SendApplicationDto */
        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(toKhai.getTenNguoiKhaiHq());
        objsendApplication.setCompanyIdentity(toKhai.getMaNguoiKhaiHq());
        /* init FromDto */
        FromDto objFrom = new FromDto();
        objFrom.setName(toKhai.getTenNguoiKhaiHq());
        objFrom.setIdentity(toKhai.getMaNguoiKhaiHq());
        /* init ToDto */
        ToDto objTo = new ToDto();
        objTo.setName(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        objTo.setIdentity(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        /* init SubjectDto */

        SubjectDto objSubject = new SubjectDto(MessageType.CTDT_HOA_DON_TM.getValue(), MessageFuntion.KHAI_BAO.getValue());
        objSubject.setRefId(chungTuDinhKemTkEntity.getId());
        /* init BodyTKDto */
        CtdtBodyDto<InvoiceContentDto> objBody = new CtdtBodyDto<>();
        objBody.setContent(invoiceContentDto);

        /* init HeaderSendDto */
        HeaderSendDto objHeader = new HeaderSendDto();
        objHeader.setDeclarantChannel(mauToKhaiHqEntity.get().getKenhKhaiBao());
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
        CtdtInvoiceRequestDto ctdtInvoiceRequestDto = new CtdtInvoiceRequestDto();
        ctdtInvoiceRequestDto.setHeader(objHeader);
        ctdtInvoiceRequestDto.setBody(objBody);
        ctdtInvoiceRequestDto.setSignature(objSignature);
        String jsonTopic = JacksonEx.getInstance().object2String(ctdtInvoiceRequestDto);
        return jsonTopic;
    }

    /**
     * @param ctdtHhnkThueGtgt5PtRequests
     * @param mauTkHqId
     * @param isSigned
     * @return
     */
    @Transactional
    public String guiCtdtHhnkThueGtgt5Pt(CtdtHhnkThueGtgt5PtRequests ctdtHhnkThueGtgt5PtRequests, String mauTkHqId, Integer isSigned) {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            String dataSigned = ctdtHhnkThueGtgt5PtRequests.getDataSigned();
            String refId = (String) JsonUtils.getValueWithElement(dataSigned, "$.body.content.refId");
            ChungTuDinhKemTkEntity params = new ChungTuDinhKemTkEntity();
            params.setTrangThai("1"); // Da gui
            params.setId(refId);
            params.setNgaySua(currentDate);
            ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.updateStatus(params);
            if (chungTuDinhKemTkEntity == null) {
                throw new ConflictException("Cập nhật trạng thái chứng từ không thành công.");
            }
            log.info("<--------------------------message tờ khai nhập----------------------------->");
            log.info(dataSigned);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicId, dataSigned);
            return "Gửi chứng từ lên hệ thống tiếp nhận hải quan thành công.";
        }
        Optional<MauToKhaiHqEntity> mauToKhaiHqEntity = mauToKhaiHqRepositoryImpl.findById(mauTkHqId);
        if (!mauToKhaiHqEntity.isPresent()) {
            throw new BadRequestException("Không tồn tại mẫu tờ khai hải quan có id " + mauTkHqId);
        }
        if (mauToKhaiHqEntity.get().getSoTiepNhan() == null) {
            throw new BadRequestException("Tờ khai hải quan có id " + mauTkHqId + " chưa có số tiếp nhận");
        }
        // Set thông tin và lưu ChungTuDinhKemTkEntity
        MauToKhaiHqEntity toKhai = mauToKhaiHqEntity.get();
        String maDoanhNghiep = toKhai.getMaDoanhNghiep();
        ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = new ChungTuDinhKemTkEntity();
        chungTuDinhKemTkEntity.setMauTkHqId(toKhai.getId());
        chungTuDinhKemTkEntity.setLoaiChungTu(MessageType.CTDT_CTAT_5_PT.getValue());
        chungTuDinhKemTkEntity.setKenhKhaiBao(toKhai.getKenhKhaiBao());
        chungTuDinhKemTkEntity.setChucNangChungTu(MessageFuntion.KHAI_BAO.getValue());
        chungTuDinhKemTkEntity.setNoiKhaiBao(toKhai.getHaiQuanTiepNhanKb());
        chungTuDinhKemTkEntity.setHqTiepNhanChungTu(toKhai.getHaiQuanTiepNhanKb()); // Cần confirm lại
        chungTuDinhKemTkEntity.setNgayTao(currentDate);
        chungTuDinhKemTkEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
        chungTuDinhKemTkEntity.setMaDoanhNghiep(maDoanhNghiep);
        chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.insert(chungTuDinhKemTkEntity);

        ImportedGoodsWith5PercentTaxContentDto importedGoodsWith5PercentTaxContentDto = CtdtHhhkThueGtgt5PtMapper.INSTANCE
                .chungTuDinhKemToKhaiToImportedGoodsWith5PercentTaxContentDto(chungTuDinhKemTkEntity);

        importedGoodsWith5PercentTaxContentDto.setRefCustomsId(String.valueOf(toKhai.getSoTiepNhan()));
        /** Người khai HQ */
        importedGoodsWith5PercentTaxContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        importedGoodsWith5PercentTaxContentDto.setDeclarantName(toKhai.getTenNguoiKhaiHq());
        /** Doanh nghiệp XNK */
        if (toKhai.getLoaiTkHq().equals(MessageType.TK_NHAP_KHAU.getValue())) {
            importedGoodsWith5PercentTaxContentDto.setImporterCode(toKhai.getMaNguoiNk());
            importedGoodsWith5PercentTaxContentDto.setImporterName(toKhai.getTenNguoiNk());
            importedGoodsWith5PercentTaxContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        } else {
            importedGoodsWith5PercentTaxContentDto.setImporterCode(toKhai.getMaNguoiXk());
            importedGoodsWith5PercentTaxContentDto.setImporterName(toKhai.getTenNguoiXk());
            importedGoodsWith5PercentTaxContentDto.setImporterAddress(toKhai.getDiaChiNguoiXk());
        }
        /** Thông tin tờ khai */
        importedGoodsWith5PercentTaxContentDto.setDeclarantCode(toKhai.getSoTk());
        importedGoodsWith5PercentTaxContentDto.setRegisteredDate(VnaccsConvert.date2String(toKhai.getNgayTraLoi(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD.getValueEnum()));
        importedGoodsWith5PercentTaxContentDto.setDeclarationKindCode(toKhai.getMaLoaiHinh());
        importedGoodsWith5PercentTaxContentDto.setDeclarationOffice(toKhai.getHaiQuanTiepNhanKb());

        importedGoodsWith5PercentTaxContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        importedGoodsWith5PercentTaxContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        importedGoodsWith5PercentTaxContentDto.setDeclarationNo(toKhai.getSoTk());

        // List object license để gửi lên hải quan
        List<ImportedGoodsWith5PercentTaxContentDto.ImportedGoodsWith5PercentTax> ImportedGoods = new ArrayList<>();
        for (CtdtHhnkThueGtgt5PtRequests.CtdtHhnkThueGtgt5PtRequest item : ctdtHhnkThueGtgt5PtRequests
                .getCtdt()) {
            CtdtCtat5PhanTramEntity ctdtCtat5PhanTramEntity = new CtdtCtat5PhanTramEntity();
            BeanUtils.copyProperties(item, ctdtCtat5PhanTramEntity);
            ctdtCtat5PhanTramEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtCtat5PhanTramEntity.setNgayTao(currentDate);
            ctdtCtat5PhanTramEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtCtat5PhanTramEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtCtat5PhanTramEntity = ctdtCtat5PhanTramRepositoryImpl.insert(ctdtCtat5PhanTramEntity);

            ImportedGoodsWith5PercentTaxContentDto.ImportedGoodsWith5PercentTax importedGoodsWith5PercentTax = CtdtHhhkThueGtgt5PtMapper.INSTANCE
                    .ctdtHhnkThue5PtToImportedGoodsWith5PercentTaxContentDto(ctdtCtat5PhanTramEntity);

            CtdtFileDinhKemEntity ctdtFileDinhKemEntity = new CtdtFileDinhKemEntity();
            BeanUtils.copyProperties(item.getFileDinhKem(), ctdtFileDinhKemEntity);
            String fileId = this.uploadFileV6(Base64.decodeBase64(item.getFileDinhKem().getFile()),
                    item.getFileDinhKem().getTenFileChungTu(), "0000", "");
            ctdtFileDinhKemEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtFileDinhKemEntity.setFileId(fileId);
            ctdtFileDinhKemEntity.setLoaiChungTuId(ctdtCtat5PhanTramEntity.getId());
            ctdtFileDinhKemEntity.setNgayTao(currentDate);
            ctdtFileDinhKemEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtFileDinhKemEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtFileDinhKemEntity = ctdtFileDinhKemRepositoryImpl.insert(ctdtFileDinhKemEntity);

            // Set thông tin file vào object importedGoodsWith5PercentTax gửi lên hải quan
            importedGoodsWith5PercentTax.setFileName(ctdtFileDinhKemEntity.getTenFileChungTu());
            // Thêm invoice vào list importedGoodsWith5PercentTax
            ImportedGoods.add(importedGoodsWith5PercentTax);
        }
        importedGoodsWith5PercentTaxContentDto.setCertTaxs(ImportedGoods);
        /* init ReferenceDto */
        ReferenceDto objreference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");
        /* init SendApplicationDto */
        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(toKhai.getTenNguoiKhaiHq());
        objsendApplication.setCompanyIdentity(toKhai.getMaNguoiKhaiHq());
        /* init FromDto */
        FromDto objFrom = new FromDto();
        objFrom.setName(toKhai.getTenNguoiKhaiHq());
        objFrom.setIdentity(toKhai.getMaNguoiKhaiHq());
        /* init ToDto */
        ToDto objTo = new ToDto();
        objTo.setName(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        objTo.setIdentity(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        /* init SubjectDto */

        SubjectDto objSubject = new SubjectDto(MessageType.CTDT_CTAT_5_PT.getValue(), MessageFuntion.KHAI_BAO.getValue());
        objSubject.setRefId(chungTuDinhKemTkEntity.getId());
        /* init BodyTKDto */
        CtdtBodyDto<ImportedGoodsWith5PercentTaxContentDto> objBody = new CtdtBodyDto<>();
        objBody.setContent(importedGoodsWith5PercentTaxContentDto);

        /* init HeaderSendDto */
        HeaderSendDto objHeader = new HeaderSendDto();
        objHeader.setDeclarantChannel(mauToKhaiHqEntity.get().getKenhKhaiBao());
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
        CtdtImportedGoodsWith5PercentTaxRequestDto ctdtImportedGoodsWith5PercentTaxRequestDto = new CtdtImportedGoodsWith5PercentTaxRequestDto();
        ctdtImportedGoodsWith5PercentTaxRequestDto.setHeader(objHeader);
        ctdtImportedGoodsWith5PercentTaxRequestDto.setBody(objBody);
        ctdtImportedGoodsWith5PercentTaxRequestDto.setSignature(objSignature);
        String jsonTopic = JacksonEx.getInstance().object2String(ctdtImportedGoodsWith5PercentTaxRequestDto);
        return jsonTopic;
    }

    /**
     * @param ctdtCoRequests
     * @param mauTkHqId
     * @param isSigned
     * @return
     */
    @Transactional
    public String guiCtdtCO(CtdtCoRequests ctdtCoRequests, String mauTkHqId, Integer isSigned) {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            String dataSigned = ctdtCoRequests.getDataSigned();
            String refId = (String) JsonUtils.getValueWithElement(dataSigned, "$.body.content.refId");
            ChungTuDinhKemTkEntity params = new ChungTuDinhKemTkEntity();
            params.setTrangThai("1"); // Da gui
            params.setId(refId);
            params.setNgaySua(currentDate);
            ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.updateStatus(params);
            if (chungTuDinhKemTkEntity == null) {
                throw new ConflictException("Cập nhật trạng thái chứng từ không thành công.");
            }
            log.info("<--------------------------message tờ khai nhập----------------------------->");
            log.info(dataSigned);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicId, dataSigned);
            return "Gửi chứng từ lên hệ thống tiếp nhận hải quan thành công.";
        }
        Optional<MauToKhaiHqEntity> mauToKhaiHqEntity = mauToKhaiHqRepositoryImpl.findById(mauTkHqId);
        if (!mauToKhaiHqEntity.isPresent()) {
            throw new BadRequestException("Không tồn tại mẫu tờ khai hải quan có id " + mauTkHqId);
        }
        if (mauToKhaiHqEntity.get().getSoTiepNhan() == null) {
            throw new BadRequestException("Tờ khai hải quan có id " + mauTkHqId + " chưa có số tiếp nhận");
        }

        // Set thông tin và lưu ChungTuDinhKemTkEntity
        MauToKhaiHqEntity toKhai = mauToKhaiHqEntity.get();
        String maDoanhNghiep = toKhai.getMaDoanhNghiep();
        ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = new ChungTuDinhKemTkEntity();
        chungTuDinhKemTkEntity.setMauTkHqId(toKhai.getId());
        chungTuDinhKemTkEntity.setLoaiChungTu(MessageType.CTDT_CO.getValue());
        chungTuDinhKemTkEntity.setKenhKhaiBao(toKhai.getKenhKhaiBao());
        chungTuDinhKemTkEntity.setChucNangChungTu(MessageFuntion.KHAI_BAO.getValue());
        chungTuDinhKemTkEntity.setNoiKhaiBao(toKhai.getHaiQuanTiepNhanKb());
        chungTuDinhKemTkEntity.setHqTiepNhanChungTu(toKhai.getHaiQuanTiepNhanKb()); // Cần confirm lại
        chungTuDinhKemTkEntity.setNgayTao(currentDate);
        chungTuDinhKemTkEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
        chungTuDinhKemTkEntity.setMaDoanhNghiep(maDoanhNghiep);
        chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.insert(chungTuDinhKemTkEntity);

        CertificateOfOriginContentDto certificateOfOriginContentDto = CtdtCoMapper.INSTANCE
                .chungTuDinhKemToKhaiToCertificateOfOriginContentDto(chungTuDinhKemTkEntity);


        certificateOfOriginContentDto.setRefCustomsId(String.valueOf(toKhai.getSoTiepNhan()));
        /** Người khai HQ */
        certificateOfOriginContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        certificateOfOriginContentDto.setDeclarantName(toKhai.getTenNguoiKhaiHq());
        /** Doanh nghiệp XNK */
        if (toKhai.getLoaiTkHq().equals(MessageType.TK_NHAP_KHAU.getValue())) {
            certificateOfOriginContentDto.setImporterCode(toKhai.getMaNguoiNk());
            certificateOfOriginContentDto.setImporterName(toKhai.getTenNguoiNk());
            certificateOfOriginContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        } else {
            certificateOfOriginContentDto.setImporterCode(toKhai.getMaNguoiXk());
            certificateOfOriginContentDto.setImporterName(toKhai.getTenNguoiXk());
            certificateOfOriginContentDto.setImporterAddress(toKhai.getDiaChiNguoiXk());
        }

        /** Thông tin tờ khai */
        certificateOfOriginContentDto.setDeclarantCode(toKhai.getSoTk());
        certificateOfOriginContentDto.setRegisteredDate(VnaccsConvert.date2String(toKhai.getNgayTraLoi(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD.getValueEnum()));
        certificateOfOriginContentDto.setDeclarationKindCode(toKhai.getMaLoaiHinh());
        certificateOfOriginContentDto.setDeclarationOffice(toKhai.getHaiQuanTiepNhanKb());
        certificateOfOriginContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        certificateOfOriginContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        certificateOfOriginContentDto.setDeclarationNo(toKhai.getSoTk());

        List<CertificateOfOriginContentDto.CertificateOfOrigin> certificateOfOrigins = new ArrayList<>();
        for (CtdtCoRequests.CtdtCoRequest item : ctdtCoRequests.getCtdt()) {
            CtdtCoEntity ctdtCoEntity = new CtdtCoEntity();
            BeanUtils.copyProperties(item, ctdtCoEntity);
            ctdtCoEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtCoEntity.setNgayTao(currentDate);
            ctdtCoEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtCoEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtCoEntity = ctdtCoRepositoryImpl.insert(ctdtCoEntity);

            CertificateOfOriginContentDto.CertificateOfOrigin certificateOfOrigin = CtdtCoMapper.INSTANCE
                    .ctdtCoToCertificateOfOriginContentDto(ctdtCoEntity);

            CtdtFileDinhKemEntity ctdtFileDinhKemEntity = new CtdtFileDinhKemEntity();
            BeanUtils.copyProperties(item.getFileDinhKem(), ctdtFileDinhKemEntity);
            String fileId = this.uploadFileV6(Base64.decodeBase64(item.getFileDinhKem().getFile()),
                    item.getFileDinhKem().getTenFileChungTu(), "0000", "");
            ctdtFileDinhKemEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtFileDinhKemEntity.setFileId(fileId);
            ctdtFileDinhKemEntity.setLoaiChungTuId(ctdtCoEntity.getId());
            ctdtFileDinhKemEntity.setNgayTao(currentDate);
            ctdtFileDinhKemEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtFileDinhKemEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtFileDinhKemEntity = ctdtFileDinhKemRepositoryImpl.insert(ctdtFileDinhKemEntity);

            // Set thông tin file vào object Invoice gửi lên hải quan
            certificateOfOrigin.setFileName(ctdtFileDinhKemEntity.getTenFileChungTu());
            // Thêm invoice vào list invoice
            certificateOfOrigins.add(certificateOfOrigin);
        }
        certificateOfOriginContentDto.setCertificateOfOrigins(certificateOfOrigins);

        /* init ReferenceDto */
        ReferenceDto objreference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");
        /* init SendApplicationDto */
        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(toKhai.getTenNguoiKhaiHq());
        objsendApplication.setCompanyIdentity(toKhai.getMaNguoiKhaiHq());
        /* init FromDto */
        FromDto objFrom = new FromDto();
        objFrom.setName(toKhai.getTenNguoiKhaiHq());
        objFrom.setIdentity(toKhai.getMaNguoiKhaiHq());
        /* init ToDto */
        ToDto objTo = new ToDto();
        objTo.setName(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        objTo.setIdentity(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        /* init SubjectDto */

        SubjectDto objSubject = new SubjectDto(MessageType.CTDT_CO.getValue(), MessageFuntion.KHAI_BAO.getValue());
        objSubject.setRefId(chungTuDinhKemTkEntity.getId());
        /* init BodyTKDto */
        CtdtBodyDto<CertificateOfOriginContentDto> objBody = new CtdtBodyDto<>();
        objBody.setContent(certificateOfOriginContentDto);

        /* init HeaderSendDto */
        HeaderSendDto objHeader = new HeaderSendDto();
        objHeader.setDeclarantChannel(mauToKhaiHqEntity.get().getKenhKhaiBao());
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
        CtdtCertificateOfOriginRequestDto ctdtCertificateOfOriginRequestDto = new CtdtCertificateOfOriginRequestDto();
        ctdtCertificateOfOriginRequestDto.setHeader(objHeader);
        ctdtCertificateOfOriginRequestDto.setBody(objBody);
        ctdtCertificateOfOriginRequestDto.setSignature(objSignature);
        String jsonTopic = JacksonEx.getInstance().object2String(ctdtCertificateOfOriginRequestDto);
        return jsonTopic;
    }

    /**
     * @param ctdtVanDonRequests
     * @param mauTkHqId
     * @param isSigned
     * @return
     */
    @Transactional
    public String guiCtdtVanDon(CtdtVanDonRequests ctdtVanDonRequests, String mauTkHqId, Integer isSigned) {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            String dataSigned = ctdtVanDonRequests.getDataSigned();
            String refId = (String) JsonUtils.getValueWithElement(dataSigned, "$.body.content.refId");
            ChungTuDinhKemTkEntity params = new ChungTuDinhKemTkEntity();
            params.setTrangThai("1"); // Da gui
            params.setId(refId);
            params.setNgaySua(currentDate);
            ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.updateStatus(params);
            if (chungTuDinhKemTkEntity == null) {
                throw new ConflictException("Cập nhật trạng thái chứng từ không thành công.");
            }
            log.info("<--------------------------message ctdt van don----------------------------->");
            log.info(dataSigned);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicId, dataSigned);
            return "Gửi chứng từ lên hệ thống tiếp nhận hải quan thành công.";
        }
        Optional<MauToKhaiHqEntity> mauToKhaiHqEntity = mauToKhaiHqRepositoryImpl.findById(mauTkHqId);
        if (!mauToKhaiHqEntity.isPresent()) {
            throw new BadRequestException("Không tồn tại mẫu tờ khai hải quan có id " + mauTkHqId);
        }
        if (mauToKhaiHqEntity.get().getSoTiepNhan() == null) {
            throw new BadRequestException("Tờ khai hải quan có id " + mauTkHqId + " chưa có số tiếp nhận");
        }
        MauToKhaiHqEntity toKhai = mauToKhaiHqEntity.get();
        String maDoanhNghiep = toKhai.getMaDoanhNghiep();
        ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = new ChungTuDinhKemTkEntity();
        chungTuDinhKemTkEntity.setMauTkHqId(toKhai.getId());
        chungTuDinhKemTkEntity.setLoaiChungTu(MessageType.CTDT_VAN_DON.getValue());
        chungTuDinhKemTkEntity.setKenhKhaiBao(toKhai.getKenhKhaiBao());
        chungTuDinhKemTkEntity.setChucNangChungTu(MessageFuntion.KHAI_BAO.getValue());
        chungTuDinhKemTkEntity.setNoiKhaiBao(toKhai.getHaiQuanTiepNhanKb());
        chungTuDinhKemTkEntity.setHqTiepNhanChungTu(toKhai.getHaiQuanTiepNhanKb()); // Cần confirm lại
        chungTuDinhKemTkEntity.setNgayTao(currentDate);
        chungTuDinhKemTkEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
        chungTuDinhKemTkEntity.setMaDoanhNghiep(maDoanhNghiep);
        chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.insert(chungTuDinhKemTkEntity);

        BillOfLadingContentDto billOfLadingContentDto = CtdtVanDonMapper.INSTANCE
                .chungTuDinhKemToKhaiToBillOfLadingContentDto(chungTuDinhKemTkEntity);

        billOfLadingContentDto.setRefCustomsId(String.valueOf(toKhai.getSoTiepNhan()));
        /** Người khai HQ */
        billOfLadingContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        billOfLadingContentDto.setDeclarantName(toKhai.getTenNguoiKhaiHq());
        /** Doanh nghiệp XNK */
        if (toKhai.getLoaiTkHq().equals(MessageType.TK_NHAP_KHAU.getValue())) {
            billOfLadingContentDto.setImporterCode(toKhai.getMaNguoiNk());
            billOfLadingContentDto.setImporterName(toKhai.getTenNguoiNk());
            billOfLadingContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        } else {
            billOfLadingContentDto.setImporterCode(toKhai.getMaNguoiXk());
            billOfLadingContentDto.setImporterName(toKhai.getTenNguoiXk());
            billOfLadingContentDto.setImporterAddress(toKhai.getDiaChiNguoiXk());
        }

        /** Thông tin tờ khai */
        billOfLadingContentDto.setDeclarantCode(toKhai.getSoTk());
        billOfLadingContentDto.setRegisteredDate(VnaccsConvert.date2String(toKhai.getNgayTraLoi(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD.getValueEnum()));
        billOfLadingContentDto.setDeclarationKindCode(toKhai.getMaLoaiHinh());
        billOfLadingContentDto.setDeclarationOffice(toKhai.getHaiQuanTiepNhanKb());

        billOfLadingContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        billOfLadingContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        billOfLadingContentDto.setDeclarationNo(toKhai.getSoTk());
        // Danh sach BillOfLading gui len hai quan
        List<BillOfLadingContentDto.BillOfLading> billOfLadings = new ArrayList<>();

        for (CtdtVanDonRequests.CtdtVanDonRequest item : ctdtVanDonRequests.getCtdt()) {
            CtdtVanDonEntity ctdtVanDonEntity = new CtdtVanDonEntity();
            BeanUtils.copyProperties(item, ctdtVanDonEntity);
            ctdtVanDonEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtVanDonEntity.setNgayTao(currentDate);
            ctdtVanDonEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtVanDonEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtVanDonEntity = ctdtVanDonRepositoryImpl.insert(ctdtVanDonEntity);

            BillOfLadingContentDto.BillOfLading billOfLading = CtdtVanDonMapper.INSTANCE
                    .ctdtVanDonEntityToctdtVanDonEntityToBillOfLading(ctdtVanDonEntity);

            CtdtFileDinhKemEntity ctdtFileDinhKemEntity = new CtdtFileDinhKemEntity();

            BeanUtils.copyProperties(item.getFileDinhKem(), ctdtFileDinhKemEntity);
            String fileId = this.uploadFileV6(Base64.decodeBase64(item.getFileDinhKem().getFile()),
                    item.getFileDinhKem().getTenFileChungTu(), "0000", "");
            ctdtFileDinhKemEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtFileDinhKemEntity.setFileId(fileId);
            ctdtFileDinhKemEntity.setLoaiChungTuId(ctdtVanDonEntity.getId());
            ctdtFileDinhKemEntity.setNgayTao(currentDate);
            ctdtFileDinhKemEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtFileDinhKemEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtFileDinhKemEntity = ctdtFileDinhKemRepositoryImpl.insert(ctdtFileDinhKemEntity);

            List<BillOfLadingContentDto.ContainerNo> containerNos = new ArrayList<>();
            for (CtdtVanDonRequests.CtdtVanDonDRequest ctdtVanDonDRequest : item.getDsContainer()) {
                CtdtVanDonContainerEntity ctdtVanDonContainerEntity = new CtdtVanDonContainerEntity();
                BeanUtils.copyProperties(ctdtVanDonDRequest, ctdtVanDonContainerEntity);
                ctdtVanDonContainerEntity.setCtdtVanDonId(ctdtVanDonEntity.getId());
                ctdtVanDonContainerEntity.setNgayTao(currentDate);
                ctdtVanDonContainerEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
                ctdtVanDonContainerEntity.setMaDoanhNghiep(maDoanhNghiep);
                ctdtVanDonContainerEntity = ctdtVanDonContainerRepositoryImpl.insert(ctdtVanDonContainerEntity);
                BillOfLadingContentDto.ContainerNo containerNo = CtdtVanDonMapper.INSTANCE
                        .ctdtVanDonDEntityToContainerNo(ctdtVanDonContainerEntity);
                containerNos.add(containerNo);
            }
            billOfLading.setContainerNos(containerNos);

            List<BillOfLadingContentDto.BranchDetail> branchDetails = new ArrayList<>();
            for (CtdtVanDonRequests.CtdtVanDonNhanhRequest ctdtVanDonNhanhRequest : item.getDsVanDonNhanh()) {
                CtdtVanDonNhanhEntity ctdtVanDonNhanhEntity = new CtdtVanDonNhanhEntity();
                BeanUtils.copyProperties(ctdtVanDonNhanhRequest, ctdtVanDonNhanhEntity);
                ctdtVanDonNhanhEntity.setCtdtVanDonId(ctdtVanDonEntity.getId());
                ctdtVanDonNhanhEntity.setNgayTao(currentDate);
                ctdtVanDonNhanhEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
                ctdtVanDonNhanhEntity.setMaDoanhNghiep(maDoanhNghiep);
                ctdtVanDonNhanhEntity = ctdtVanDonNhanhRepositoryImpl.insert(ctdtVanDonNhanhEntity);
                BillOfLadingContentDto.BranchDetail branchDetail = CtdtVanDonMapper.INSTANCE
                        .ctdtVanDonNhanhEntityToBranchDetail(ctdtVanDonNhanhEntity);
                branchDetails.add(branchDetail);
            }
            billOfLading.setBranchDetails(branchDetails);

            // Set thông tin file vào object Invoice gửi lên hải quan
            billOfLading.setFileName(ctdtFileDinhKemEntity.getTenFileChungTu());
            // Thêm invoice vào list invoice
            billOfLadings.add(billOfLading);
        }
        billOfLadingContentDto.setBillOfLadings(billOfLadings);

        /* init ReferenceDto */
        ReferenceDto objreference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");
        /* init SendApplicationDto */
        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(toKhai.getTenNguoiKhaiHq());
        objsendApplication.setCompanyIdentity(toKhai.getMaNguoiKhaiHq());
        /* init FromDto */
        FromDto objFrom = new FromDto();
        objFrom.setName(toKhai.getTenNguoiKhaiHq());
        objFrom.setIdentity(toKhai.getMaNguoiKhaiHq());
        /* init ToDto */
        ToDto objTo = new ToDto();
        objTo.setName(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        objTo.setIdentity(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        /* init SubjectDto */

        SubjectDto objSubject = new SubjectDto(MessageType.CTDT_VAN_DON.getValue(), MessageFuntion.KHAI_BAO.getValue());
        objSubject.setRefId(chungTuDinhKemTkEntity.getId());
        /* init BodyTKDto */
        CtdtBodyDto<BillOfLadingContentDto> objBody = new CtdtBodyDto<>();
        objBody.setContent(billOfLadingContentDto);

        /* init HeaderSendDto */
        HeaderSendDto objHeader = new HeaderSendDto();
        objHeader.setDeclarantChannel(mauToKhaiHqEntity.get().getKenhKhaiBao());
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
        CtdtBillOfLadingRequestDto ctdtInvoiceRequestDto = new CtdtBillOfLadingRequestDto();
        ctdtInvoiceRequestDto.setHeader(objHeader);
        ctdtInvoiceRequestDto.setBody(objBody);
        ctdtInvoiceRequestDto.setSignature(objSignature);
        String jsonTopic = JacksonEx.getInstance().object2String(ctdtInvoiceRequestDto);
        return jsonTopic;

    }

    /**
     * @param ctdtBkhhRequests
     * @param mauTkHqId
     * @param isSigned
     * @return
     */
    @Transactional
    public String guiCtdtBkhh(CtdtBkhhRequests ctdtBkhhRequests, String mauTkHqId, Integer isSigned) {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            String dataSigned = ctdtBkhhRequests.getDataSigned();
            String refId = (String) JsonUtils.getValueWithElement(dataSigned, "$.body.content.refId");
            ChungTuDinhKemTkEntity params = new ChungTuDinhKemTkEntity();
            params.setTrangThai("1"); // Da gui
            params.setId(refId);
            params.setNgaySua(currentDate);
            ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.updateStatus(params);
            if (chungTuDinhKemTkEntity == null) {
                throw new ConflictException("Cập nhật trạng thái chứng từ không thành công.");
            }
            log.info("<--------------------------message tờ khai nhập----------------------------->");
            log.info(dataSigned);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicId, dataSigned);
            return "Gửi chứng từ lên hệ thống tiếp nhận hải quan thành công.";
        }
        Optional<MauToKhaiHqEntity> mauToKhaiHqEntity = mauToKhaiHqRepositoryImpl.findById(mauTkHqId);
        if (!mauToKhaiHqEntity.isPresent()) {
            throw new BadRequestException("Không tồn tại mẫu tờ khai hải quan có id " + mauTkHqId);
        }
        if (mauToKhaiHqEntity.get().getSoTiepNhan() == null) {
            throw new BadRequestException("Tờ khai hải quan có id " + mauTkHqId + " chưa có số tiếp nhận");
        }

        // Set thông tin và lưu ChungTuDinhKemTkEntity
        MauToKhaiHqEntity toKhai = mauToKhaiHqEntity.get();
        String maDoanhNghiep = toKhai.getMaDoanhNghiep();
        ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = new ChungTuDinhKemTkEntity();
        chungTuDinhKemTkEntity.setMauTkHqId(toKhai.getId());
        chungTuDinhKemTkEntity.setLoaiChungTu(MessageType.CTDT_BKHH.getValue());
        chungTuDinhKemTkEntity.setKenhKhaiBao(toKhai.getKenhKhaiBao());
        chungTuDinhKemTkEntity.setChucNangChungTu(MessageFuntion.KHAI_BAO.getValue());
        chungTuDinhKemTkEntity.setNoiKhaiBao(toKhai.getHaiQuanTiepNhanKb());
        chungTuDinhKemTkEntity.setHqTiepNhanChungTu(toKhai.getHaiQuanTiepNhanKb()); // Cần confirm lại
        chungTuDinhKemTkEntity.setNgayTao(currentDate);
        chungTuDinhKemTkEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
        chungTuDinhKemTkEntity.setMaDoanhNghiep(maDoanhNghiep);
        chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.insert(chungTuDinhKemTkEntity);

        GoodsDetailContentDto goodsDetailContentDto = CtdtBkhhMapper.INSTANCE
                .chungTuDinhKemToKhaiToGoodsDetailContentDto(chungTuDinhKemTkEntity);
        goodsDetailContentDto.setRefCustomsId(String.valueOf(toKhai.getSoTiepNhan()));
        /** Người khai HQ */
        goodsDetailContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        goodsDetailContentDto.setDeclarantName(toKhai.getTenNguoiKhaiHq());
        /** Doanh nghiệp XNK */
        if (toKhai.getLoaiTkHq().equals(MessageType.TK_NHAP_KHAU.getValue())) {
            goodsDetailContentDto.setImporterCode(toKhai.getMaNguoiNk());
            goodsDetailContentDto.setImporterName(toKhai.getTenNguoiNk());
            goodsDetailContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        } else {
            goodsDetailContentDto.setImporterCode(toKhai.getMaNguoiXk());
            goodsDetailContentDto.setImporterName(toKhai.getTenNguoiXk());
            goodsDetailContentDto.setImporterAddress(toKhai.getDiaChiNguoiXk());
        }
        /** Thông tin tờ khai */
        goodsDetailContentDto.setDeclarantCode(toKhai.getSoTk());
        goodsDetailContentDto.setRegisteredDate(VnaccsConvert.date2String(toKhai.getNgayTraLoi(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD.getValueEnum()));
        goodsDetailContentDto.setDeclarationKindCode(toKhai.getMaLoaiHinh());
        goodsDetailContentDto.setDeclarationOffice(toKhai.getHaiQuanTiepNhanKb());

        goodsDetailContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        goodsDetailContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        goodsDetailContentDto.setDeclarationNo(toKhai.getSoTk());

        List<GoodsDetailContentDto.GoodsDetail> goodsDetails = new ArrayList<>();
        for (CtdtBkhhRequests.CtdtBkhhRequest item : ctdtBkhhRequests.getCtdt()) {
            CtdtBkhhEntity ctdtBkhhEntity = new CtdtBkhhEntity();
            BeanUtils.copyProperties(item, ctdtBkhhEntity);
            ctdtBkhhEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtBkhhEntity.setNgayTao(currentDate);
            ctdtBkhhEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtBkhhEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtBkhhEntity = ctdtBkhhRepositoryImpl.insert(ctdtBkhhEntity);

            GoodsDetailContentDto.GoodsDetail goodsDetail = CtdtBkhhMapper.INSTANCE
                    .ctdtCoToCertificateOfOriginContentDto(ctdtBkhhEntity);

            CtdtFileDinhKemEntity ctdtFileDinhKemEntity = new CtdtFileDinhKemEntity();
            BeanUtils.copyProperties(item.getFileDinhKem(), ctdtFileDinhKemEntity);
            String fileId = this.uploadFileV6(Base64.decodeBase64(item.getFileDinhKem().getFile()),
                    item.getFileDinhKem().getTenFileChungTu(), "0000", "");
            ctdtFileDinhKemEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtFileDinhKemEntity.setFileId(fileId);
            ctdtFileDinhKemEntity.setLoaiChungTuId(ctdtBkhhEntity.getId());
            ctdtFileDinhKemEntity.setNgayTao(currentDate);
            ctdtFileDinhKemEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtFileDinhKemEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtFileDinhKemEntity = ctdtFileDinhKemRepositoryImpl.insert(ctdtFileDinhKemEntity);

            // Set thông tin file vào object Invoice gửi lên hải quan
            goodsDetail.setFileName(ctdtFileDinhKemEntity.getTenFileChungTu());
            // Thêm invoice vào list invoice
            goodsDetails.add(goodsDetail);
        }
        goodsDetailContentDto.setGoodsDetails(goodsDetails);

        /* init ReferenceDto */
        ReferenceDto objreference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");
        /* init SendApplicationDto */
        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(toKhai.getTenNguoiKhaiHq());
        objsendApplication.setCompanyIdentity(toKhai.getMaNguoiKhaiHq());
        /* init FromDto */
        FromDto objFrom = new FromDto();
        objFrom.setName(toKhai.getTenNguoiKhaiHq());
        objFrom.setIdentity(toKhai.getMaNguoiKhaiHq());
        /* init ToDto */
        ToDto objTo = new ToDto();
        objTo.setName(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        objTo.setIdentity(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        /* init SubjectDto */

        SubjectDto objSubject = new SubjectDto(MessageType.CTDT_BKHH.getValue(), MessageFuntion.KHAI_BAO.getValue());
        objSubject.setRefId(chungTuDinhKemTkEntity.getId());
        /* init BodyTKDto */
        CtdtBodyDto<GoodsDetailContentDto> objBody = new CtdtBodyDto<>();
        objBody.setContent(goodsDetailContentDto);

        /* init HeaderSendDto */
        HeaderSendDto objHeader = new HeaderSendDto();
        objHeader.setDeclarantChannel(mauToKhaiHqEntity.get().getKenhKhaiBao());
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
        GoodsDetailRequestDto goodsDetailRequestDto = new GoodsDetailRequestDto();
        goodsDetailRequestDto.setHeader(objHeader);
        goodsDetailRequestDto.setBody(objBody);
        goodsDetailRequestDto.setSignature(objSignature);
        String jsonTopic = JacksonEx.getInstance().object2String(goodsDetailRequestDto);
        return jsonTopic;
    }

    /**
     * @param ctdtGiayCnktcnRequests
     * @param mauTkHqId
     * @param isSigned
     * @return
     */
    @Transactional
    public String guiCtdtGiayCnktcn(CtdtGiayCnktcnRequests ctdtGiayCnktcnRequests, String mauTkHqId, Integer isSigned) throws ResourceNotFoundException {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            String dataSigned = ctdtGiayCnktcnRequests.getDataSigned();
            String refId = (String) JsonUtils.getValueWithElement(dataSigned, "$.body.content.refId");
            ChungTuDinhKemTkEntity params = new ChungTuDinhKemTkEntity();
            params.setTrangThai("1"); // Da gui
            params.setId(refId);
            params.setNgaySua(currentDate);
            ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.updateStatus(params);
            if (chungTuDinhKemTkEntity == null) {
                throw new ConflictException("Cập nhật trạng thái chứng từ không thành công.");
            }
            log.info("<--------------------------message tờ khai nhập----------------------------->");
            log.info(dataSigned);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicId, dataSigned);
            return "Gửi chứng từ lên hệ thống tiếp nhận hải quan thành công.";
        }
        Optional<MauToKhaiHqEntity> mauToKhaiHqEntity = mauToKhaiHqRepositoryImpl.findById(mauTkHqId);
        if (!mauToKhaiHqEntity.isPresent()) {
            throw new BadRequestException("Không tồn tại mẫu tờ khai hải quan có id " + mauTkHqId);
        }
        if (mauToKhaiHqEntity.get().getSoTiepNhan() == null) {
            throw new BadRequestException("Tờ khai hải quan có id " + mauTkHqId + " chưa có số tiếp nhận");
        }
        // Set thông tin và lưu ChungTuDinhKemTkEntity
        MauToKhaiHqEntity toKhai = mauToKhaiHqEntity.get();
        String maDoanhNghiep = toKhai.getMaDoanhNghiep();
        ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = new ChungTuDinhKemTkEntity();
        chungTuDinhKemTkEntity.setMauTkHqId(toKhai.getId());
        chungTuDinhKemTkEntity.setLoaiChungTu(MessageType.CTDT_GIAY_CNKTCN.getValue());
        chungTuDinhKemTkEntity.setKenhKhaiBao(toKhai.getKenhKhaiBao());
        chungTuDinhKemTkEntity.setChucNangChungTu(MessageFuntion.KHAI_BAO.getValue());
        chungTuDinhKemTkEntity.setNoiKhaiBao(toKhai.getHaiQuanTiepNhanKb());
        chungTuDinhKemTkEntity.setHqTiepNhanChungTu(toKhai.getHaiQuanTiepNhanKb()); // Cần confirm lại
        chungTuDinhKemTkEntity.setNgayTao(currentDate);
        chungTuDinhKemTkEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
        chungTuDinhKemTkEntity.setMaDoanhNghiep(maDoanhNghiep);
        chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.insert(chungTuDinhKemTkEntity);

        SpecializedCersContentDto specializedCersContentDto = CtdtGiayCnktcnMapper.INSTANCE
                .chungTuDinhKemTkToSpecializedCersContentDto(chungTuDinhKemTkEntity);

        specializedCersContentDto.setRefCustomsId(String.valueOf(toKhai.getSoTiepNhan()));
        /** Người khai HQ */
        specializedCersContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        specializedCersContentDto.setDeclarantName(toKhai.getTenNguoiKhaiHq());
        /** Doanh nghiệp XNK */
        if (toKhai.getLoaiTkHq().equals(MessageType.TK_NHAP_KHAU.getValue())) {
            specializedCersContentDto.setImporterCode(toKhai.getMaNguoiNk());
            specializedCersContentDto.setImporterName(toKhai.getTenNguoiNk());
            specializedCersContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        } else {
            specializedCersContentDto.setImporterCode(toKhai.getMaNguoiXk());
            specializedCersContentDto.setImporterName(toKhai.getTenNguoiXk());
            specializedCersContentDto.setImporterAddress(toKhai.getDiaChiNguoiXk());
        }

        /** Thông tin tờ khai */
        specializedCersContentDto.setDeclarantCode(toKhai.getSoTk());
        specializedCersContentDto.setRegisteredDate(VnaccsConvert.date2String(toKhai.getNgayTraLoi(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD.getValueEnum()));
        specializedCersContentDto.setDeclarationKindCode(toKhai.getMaLoaiHinh());
        specializedCersContentDto.setDeclarationOffice(toKhai.getHaiQuanTiepNhanKb());

        specializedCersContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        specializedCersContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        specializedCersContentDto.setDeclarationNo(toKhai.getSoTk());

        List<SpecializedCersContentDto.SpecializedCers> specializedCers = new ArrayList<>();
        for (CtdtGiayCnktcnRequests.CtdtGiayCnktcnRequest item : ctdtGiayCnktcnRequests.getCtdt()) {
            CtdtGiayCnktcnEntity ctdtGiayCnktcnEntity = new CtdtGiayCnktcnEntity();
            BeanUtils.copyProperties(item, ctdtGiayCnktcnEntity);
            ctdtGiayCnktcnEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());

            // add goodsitems to license
            StringBuilder dsHang = new StringBuilder();
            List<SpecializedCersContentDto.GoodItem> goodsItems = new ArrayList<>();
            for (CtdtGiayCnktcnRequests.CtdtThongTinHangHoaRequest ctdtThongTinHangHoaRequest : item
                    .getDsHang()) {
                // Update ghi chu khac hang hoa
                DmHangHoaTkEntity dmHangHoaTkEntity = dmHangHoaTkRepositoryImpl.findById(ctdtThongTinHangHoaRequest.getId()).orElse(null);
                if (dmHangHoaTkEntity == null) {
                    throw new ResourceNotFoundException("Không tìm thấy thông tin hàng hóa.");
                }
                dmHangHoaTkRepositoryImpl.update(dmHangHoaTkEntity);
                SpecializedCersContentDto.GoodItem goodsItem = CtdtGiayCnktcnMapper.INSTANCE
                        .ctdtThongTinHangHoaRequestToGoodsItems(ctdtThongTinHangHoaRequest);
                goodsItems.add(goodsItem);
                if (dsHang.length() == 0) {
                    dsHang.append(ctdtThongTinHangHoaRequest.getId().toString());
                } else {
                    dsHang.append(",");
                    dsHang.append(ctdtThongTinHangHoaRequest.getId().toString());
                }
            }
            ctdtGiayCnktcnEntity.setHangHoaId(dsHang.toString());
            ctdtGiayCnktcnEntity.setNgayTao(currentDate);
            ctdtGiayCnktcnEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtGiayCnktcnEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtGiayCnktcnEntity = ctdtGiayCnktcnRepositoryImpl.insert(ctdtGiayCnktcnEntity);

            SpecializedCersContentDto.SpecializedCers specializedCer = CtdtGiayCnktcnMapper.INSTANCE
                    .ctdtGiayCnktcnEntityToSpecializedCers(ctdtGiayCnktcnEntity);

            CtdtFileDinhKemEntity ctdtFileDinhKemEntity = new CtdtFileDinhKemEntity();
            BeanUtils.copyProperties(item.getFileDinhKem(), ctdtFileDinhKemEntity);
            String fileId = this.uploadFileV6(Base64.decodeBase64(item.getFileDinhKem().getFile()),
                    item.getFileDinhKem().getTenFileChungTu(), "0000", "");
            ctdtFileDinhKemEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtFileDinhKemEntity.setFileId(fileId);
            ctdtFileDinhKemEntity.setLoaiChungTuId(ctdtGiayCnktcnEntity.getId());
            ctdtFileDinhKemEntity.setNgayTao(currentDate);
            ctdtFileDinhKemEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtGiayCnktcnEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtFileDinhKemEntity = ctdtFileDinhKemRepositoryImpl.insert(ctdtFileDinhKemEntity);
            specializedCer.setGoodsItems(goodsItems);
            // Set thông tin file vào object Invoice gửi lên hải quan
            specializedCer.setFileName(ctdtFileDinhKemEntity.getTenFileChungTu());
            specializedCers.add(specializedCer);
        }
        specializedCersContentDto.setSpecializedCers(specializedCers);

        /* init ReferenceDto */
        ReferenceDto objreference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");
        /* init SendApplicationDto */
        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(toKhai.getTenNguoiKhaiHq());
        objsendApplication.setCompanyIdentity(toKhai.getMaNguoiKhaiHq());
        /* init FromDto */
        FromDto objFrom = new FromDto();
        objFrom.setName(toKhai.getTenNguoiKhaiHq());
        objFrom.setIdentity(toKhai.getMaNguoiKhaiHq());
        /* init ToDto */
        ToDto objTo = new ToDto();
        objTo.setName(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        objTo.setIdentity(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        /* init SubjectDto */

        SubjectDto objSubject = new SubjectDto(MessageType.CTDT_GIAY_CNKTCN.getValue(), MessageFuntion.KHAI_BAO.getValue());
        objSubject.setRefId(chungTuDinhKemTkEntity.getId());
        /* init BodyTKDto */
        CtdtBodyDto<SpecializedCersContentDto> objBody = new CtdtBodyDto<>();
        objBody.setContent(specializedCersContentDto);

        /* init HeaderSendDto */
        HeaderSendDto objHeader = new HeaderSendDto();
        objHeader.setDeclarantChannel(mauToKhaiHqEntity.get().getKenhKhaiBao());
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
        CtdtSpecializedCersRequestDto ctdtSpecializedCersRequestDto = new CtdtSpecializedCersRequestDto();
        ctdtSpecializedCersRequestDto.setHeader(objHeader);
        ctdtSpecializedCersRequestDto.setBody(objBody);
        ctdtSpecializedCersRequestDto.setSignature(objSignature);
        String jsonTopic = JacksonEx.getInstance().object2String(ctdtSpecializedCersRequestDto);
        return jsonTopic;
    }

    /**
     * @param ctdtMmtbRequests
     * @param mauTkHqId
     * @param isSigned
     * @return
     */
    @Transactional
    public String guiCtdtMmtb(CtdtMmtbRequests ctdtMmtbRequests, String mauTkHqId, Integer isSigned) {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            String dataSigned = ctdtMmtbRequests.getDataSigned();
            String refId = (String) JsonUtils.getValueWithElement(dataSigned, "$.body.content.refId");
            ChungTuDinhKemTkEntity params = new ChungTuDinhKemTkEntity();
            params.setTrangThai("1"); // Da gui
            params.setId(refId);
            params.setNgaySua(currentDate);
            ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.updateStatus(params);
            if (chungTuDinhKemTkEntity == null) {
                throw new ConflictException("Cập nhật trạng thái chứng từ không thành công.");
            }
            log.info("<--------------------------message tờ khai nhập----------------------------->");
            log.info(dataSigned);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicId, dataSigned);
            return "Gửi chứng từ lên hệ thống tiếp nhận hải quan thành công.";
        }
        Optional<MauToKhaiHqEntity> mauToKhaiHqEntity = mauToKhaiHqRepositoryImpl.findById(mauTkHqId);
        if (!mauToKhaiHqEntity.isPresent()) {
            throw new BadRequestException("Không tồn tại mẫu tờ khai hải quan có id " + mauTkHqId);
        }
        if (mauToKhaiHqEntity.get().getSoTiepNhan() == null) {
            throw new BadRequestException("Tờ khai hải quan có id " + mauTkHqId + " chưa có số tiếp nhận");
        }
        // Set thông tin và lưu ChungTuDinhKemTkEntity
        MauToKhaiHqEntity toKhai = mauToKhaiHqEntity.get();
        String maDoanhNghiep = toKhai.getMaDoanhNghiep();
        ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = new ChungTuDinhKemTkEntity();
        chungTuDinhKemTkEntity.setMauTkHqId(toKhai.getId());
        chungTuDinhKemTkEntity.setLoaiChungTu(MessageType.CTDT_MMTB.getValue());
        chungTuDinhKemTkEntity.setKenhKhaiBao(toKhai.getKenhKhaiBao());
        chungTuDinhKemTkEntity.setChucNangChungTu(MessageFuntion.KHAI_BAO.getValue());
        chungTuDinhKemTkEntity.setNoiKhaiBao(toKhai.getHaiQuanTiepNhanKb());
        chungTuDinhKemTkEntity.setHqTiepNhanChungTu(toKhai.getHaiQuanTiepNhanKb()); // Cần confirm lại
        chungTuDinhKemTkEntity.setNgayTao(currentDate);
        chungTuDinhKemTkEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
        chungTuDinhKemTkEntity.setMaDoanhNghiep(maDoanhNghiep);
        chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.insert(chungTuDinhKemTkEntity);

        EquipmentContentDto equipmentContentDto = CtdtMmtbMapper.INSTANCE
                .chungTuDinhKemToKhaiToEquipmentContentDto(chungTuDinhKemTkEntity);
        equipmentContentDto.setRefCustomsId(String.valueOf(toKhai.getSoTiepNhan()));
        /** Người khai HQ */
        equipmentContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        equipmentContentDto.setDeclarantName(toKhai.getTenNguoiKhaiHq());
        /** Doanh nghiệp XNK */
        if (toKhai.getLoaiTkHq().equals(MessageType.TK_NHAP_KHAU.getValue())) {
            equipmentContentDto.setImporterCode(toKhai.getMaNguoiNk());
            equipmentContentDto.setImporterName(toKhai.getTenNguoiNk());
            equipmentContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        } else {
            equipmentContentDto.setImporterCode(toKhai.getMaNguoiXk());
            equipmentContentDto.setImporterName(toKhai.getTenNguoiXk());
            equipmentContentDto.setImporterAddress(toKhai.getDiaChiNguoiXk());
        }

        /** Thông tin tờ khai */
        equipmentContentDto.setDeclarantCode(toKhai.getSoTk());
        equipmentContentDto.setRegisteredDate(VnaccsConvert.date2String(toKhai.getNgayTraLoi(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD.getValueEnum()));
        equipmentContentDto.setDeclarationKindCode(toKhai.getMaLoaiHinh());
        equipmentContentDto.setDeclarationOffice(toKhai.getHaiQuanTiepNhanKb());

        equipmentContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        equipmentContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        equipmentContentDto.setDeclarationNo(toKhai.getSoTk());

        List<EquipmentContentDto.Equipment> equipments = new ArrayList<>();
        for (CtdtMmtbRequests.CtdtMmtbRequest item : ctdtMmtbRequests.getCtdt()) {
            CtdtMmtbEntity ctdtMmtbEntity = new CtdtMmtbEntity();
            BeanUtils.copyProperties(item, ctdtMmtbEntity);
            ctdtMmtbEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtMmtbEntity.setNgayTao(currentDate);
            ctdtMmtbEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtMmtbEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtMmtbEntity = ctdtMmtbRepositoryImpl.insert(ctdtMmtbEntity);

            EquipmentContentDto.Equipment equipment = CtdtMmtbMapper.INSTANCE
                    .ctdtMmtbToEquipmentContentDto(ctdtMmtbEntity);

            CtdtFileDinhKemEntity ctdtFileDinhKemEntity = new CtdtFileDinhKemEntity();
            BeanUtils.copyProperties(item.getFileDinhKem(), ctdtFileDinhKemEntity);
            String fileId = this.uploadFileV6(Base64.decodeBase64(item.getFileDinhKem().getFile()),
                    item.getFileDinhKem().getTenFileChungTu(), "0000", "");
            ctdtFileDinhKemEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtFileDinhKemEntity.setFileId(fileId);
            ctdtFileDinhKemEntity.setLoaiChungTuId(ctdtMmtbEntity.getId());
            ctdtFileDinhKemEntity.setNgayTao(currentDate);
            ctdtFileDinhKemEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtFileDinhKemEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtFileDinhKemEntity = ctdtFileDinhKemRepositoryImpl.insert(ctdtFileDinhKemEntity);
            equipment.setFileName(ctdtFileDinhKemEntity.getTenFileChungTu());

            // Thêm invoice vào list invoice
            equipments.add(equipment);
        }
        equipmentContentDto.setEquipments(equipments);

        /* init ReferenceDto */
        ReferenceDto objreference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");
        /* init SendApplicationDto */
        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(toKhai.getTenNguoiKhaiHq());
        objsendApplication.setCompanyIdentity(toKhai.getMaNguoiKhaiHq());
        /* init FromDto */
        FromDto objFrom = new FromDto();
        objFrom.setName(toKhai.getTenNguoiKhaiHq());
        objFrom.setIdentity(toKhai.getMaNguoiKhaiHq());
        /* init ToDto */
        ToDto objTo = new ToDto();
        objTo.setName(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        objTo.setIdentity(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        /* init SubjectDto */

        SubjectDto objSubject = new SubjectDto(MessageType.CTDT_MMTB.getValue(), MessageFuntion.KHAI_BAO.getValue());
        objSubject.setRefId(chungTuDinhKemTkEntity.getId());
        /* init BodyTKDto */
        CtdtBodyDto<EquipmentContentDto> objBody = new CtdtBodyDto<>();
        objBody.setContent(equipmentContentDto);

        /* init HeaderSendDto */
        HeaderSendDto objHeader = new HeaderSendDto();
        objHeader.setDeclarantChannel(mauToKhaiHqEntity.get().getKenhKhaiBao());
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
        CtdtEquipmentRequestDto ctdtEquipmentRequestDto = new CtdtEquipmentRequestDto();
        ctdtEquipmentRequestDto.setHeader(objHeader);
        ctdtEquipmentRequestDto.setBody(objBody);
        ctdtEquipmentRequestDto.setSignature(objSignature);
        String jsonTopic = JacksonEx.getInstance().object2String(ctdtEquipmentRequestDto);
        return jsonTopic;
    }

    /**
     * @param ctdtHopDongUtRequests
     * @param mauTkHqId
     * @param isSigned
     * @return
     */
    @Transactional
    public String guiCtdtHopDongUt(CtdtHopDongUtRequests ctdtHopDongUtRequests, String mauTkHqId, Integer isSigned) {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            String dataSigned = ctdtHopDongUtRequests.getDataSigned();
            String refId = (String) JsonUtils.getValueWithElement(dataSigned, "$.body.content.refId");
            ChungTuDinhKemTkEntity params = new ChungTuDinhKemTkEntity();
            params.setTrangThai("1"); // Da gui
            params.setId(refId);
            params.setNgaySua(currentDate);
            ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.updateStatus(params);
            if (chungTuDinhKemTkEntity == null) {
                throw new ConflictException("Cập nhật trạng thái chứng từ không thành công.");
            }
            log.info("<--------------------------message tờ khai nhập----------------------------->");
            log.info(dataSigned);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicId, dataSigned);
            return "Gửi chứng từ lên hệ thống tiếp nhận hải quan thành công.";
        }
        Optional<MauToKhaiHqEntity> mauToKhaiHqEntity = mauToKhaiHqRepositoryImpl.findById(mauTkHqId);
        if (!mauToKhaiHqEntity.isPresent()) {
            throw new BadRequestException("Không tồn tại mẫu tờ khai hải quan có id " + mauTkHqId);
        }
        if (mauToKhaiHqEntity.get().getSoTiepNhan() == null) {
            throw new BadRequestException("Tờ khai hải quan có id " + mauTkHqId + " chưa có số tiếp nhận");
        }
        MauToKhaiHqEntity toKhai = mauToKhaiHqEntity.get();
        String maDoanhNghiep = toKhai.getMaDoanhNghiep();
        ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = new ChungTuDinhKemTkEntity();
        chungTuDinhKemTkEntity.setMauTkHqId(toKhai.getId());
        chungTuDinhKemTkEntity.setLoaiChungTu(MessageType.CTDT_HOP_DONG_UT.getValue());
        chungTuDinhKemTkEntity.setKenhKhaiBao(toKhai.getKenhKhaiBao());
        chungTuDinhKemTkEntity.setChucNangChungTu(MessageFuntion.KHAI_BAO.getValue());
        chungTuDinhKemTkEntity.setNoiKhaiBao(toKhai.getHaiQuanTiepNhanKb());
        chungTuDinhKemTkEntity.setHqTiepNhanChungTu(toKhai.getHaiQuanTiepNhanKb()); // Cần confirm lại
        chungTuDinhKemTkEntity.setNgayTao(currentDate);
        chungTuDinhKemTkEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
        chungTuDinhKemTkEntity.setMaDoanhNghiep(maDoanhNghiep);
        chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.insert(chungTuDinhKemTkEntity);

        TrustContractContentDto trustContractContentDto = CtdtHopDongUtMapper.INSTANCE
                .chungTuDinhKemToKhaiToTrustContractContentDto(chungTuDinhKemTkEntity);

        trustContractContentDto.setRefCustomsId(String.valueOf(toKhai.getSoTiepNhan()));
        /** Người khai HQ */
        trustContractContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        trustContractContentDto.setDeclarantName(toKhai.getTenNguoiKhaiHq());
        /** Doanh nghiệp XNK */
        if (toKhai.getLoaiTkHq().equals(MessageType.TK_NHAP_KHAU.getValue())) {
            trustContractContentDto.setImporterCode(toKhai.getMaNguoiNk());
            trustContractContentDto.setImporterName(toKhai.getTenNguoiNk());
            trustContractContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        } else {
            trustContractContentDto.setImporterCode(toKhai.getMaNguoiXk());
            trustContractContentDto.setImporterName(toKhai.getTenNguoiXk());
            trustContractContentDto.setImporterAddress(toKhai.getDiaChiNguoiXk());
        }
        /** Thông tin tờ khai */
        trustContractContentDto.setDeclarantCode(toKhai.getSoTk());
        trustContractContentDto.setRegisteredDate(VnaccsConvert.date2String(toKhai.getNgayTraLoi(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD.getValueEnum()));
        trustContractContentDto.setDeclarationKindCode(toKhai.getMaLoaiHinh());
        trustContractContentDto.setDeclarationOffice(toKhai.getHaiQuanTiepNhanKb());

        trustContractContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        trustContractContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        trustContractContentDto.setDeclarationNo(toKhai.getSoTk());

        List<TrustContractContentDto.TrustContract> trustContracts = new ArrayList<>();
        for (CtdtHopDongUtRequests.HopDongUtRequest item : ctdtHopDongUtRequests.getCtdt()) {
            CtdtHopDongUtEntity ctdtHopDongUtEntity = new CtdtHopDongUtEntity();
            BeanUtils.copyProperties(item, ctdtHopDongUtEntity);
            ctdtHopDongUtEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtHopDongUtEntity.setNgayTao(currentDate);
            ctdtHopDongUtEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtHopDongUtEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtHopDongUtEntity = ctdtHopDongUtRepositoryImpl.insert(ctdtHopDongUtEntity);

            String fileId = this.uploadFileV6(Base64.decodeBase64(item.getFileDinhKem().getFile()),
                    item.getFileDinhKem().getTenFileChungTu(), "0000", "");

            TrustContractContentDto.TrustContract trustContract = CtdtHopDongUtMapper.INSTANCE
                    .ctdtHopDongUtToTrustContractContentDto(ctdtHopDongUtEntity);

            CtdtFileDinhKemEntity ctdtFileDinhKemEntity = new CtdtFileDinhKemEntity();
            BeanUtils.copyProperties(item.getFileDinhKem(), ctdtFileDinhKemEntity);
            ctdtFileDinhKemEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtFileDinhKemEntity.setFileId(fileId);
            ctdtFileDinhKemEntity.setLoaiChungTuId(ctdtHopDongUtEntity.getId());
            ctdtFileDinhKemEntity.setNgayTao(currentDate);
            ctdtFileDinhKemEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtFileDinhKemEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtFileDinhKemEntity = ctdtFileDinhKemRepositoryImpl.insert(ctdtFileDinhKemEntity);

            // Set thông tin file trong object license dto
            trustContract.setFileName(ctdtFileDinhKemEntity.getTenFileChungTu());
            trustContracts.add(trustContract);
        }
        trustContractContentDto.setTrustContracts(trustContracts);

        /* init ReferenceDto */
        ReferenceDto objreference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");
        /* init SendApplicationDto */
        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(toKhai.getTenNguoiKhaiHq());
        objsendApplication.setCompanyIdentity(toKhai.getMaNguoiKhaiHq());
        /* init FromDto */
        FromDto objFrom = new FromDto();
        objFrom.setName(toKhai.getTenNguoiKhaiHq());
        objFrom.setIdentity(toKhai.getMaNguoiKhaiHq());
        /* init ToDto */
        ToDto objTo = new ToDto();
        objTo.setName(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        objTo.setIdentity(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        /* init SubjectDto */

        SubjectDto objSubject = new SubjectDto(MessageType.CTDT_HOP_DONG_UT.getValue(), MessageFuntion.KHAI_BAO.getValue());
        objSubject.setRefId(chungTuDinhKemTkEntity.getId());
        /* init BodyTKDto */
        CtdtBodyDto<TrustContractContentDto> objBody = new CtdtBodyDto<>();
        objBody.setContent(trustContractContentDto);
        /* init HeaderSendDto */
        HeaderSendDto objHeader = new HeaderSendDto();
        objHeader.setDeclarantChannel(mauToKhaiHqEntity.get().getKenhKhaiBao());
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
        CtdtTrustContractRequestDto ctdtTrustContractRequestDto = new CtdtTrustContractRequestDto();
        ctdtTrustContractRequestDto.setHeader(objHeader);
        ctdtTrustContractRequestDto.setBody(objBody);
        ctdtTrustContractRequestDto.setSignature(objSignature);
        String jsonTopic = JacksonEx.getInstance().object2String(ctdtTrustContractRequestDto);
        return jsonTopic;
    }

    /**
     * @param ctdtCmtcRequests
     * @param mauTkHqId
     * @param isSigned
     * @return
     */
    @Transactional
    public String guiCtdtCmtc(CtdtCmtcRequests ctdtCmtcRequests, String mauTkHqId, Integer isSigned) {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            String dataSigned = ctdtCmtcRequests.getDataSigned();
            String refId = (String) JsonUtils.getValueWithElement(dataSigned, "$.body.content.refId");
            ChungTuDinhKemTkEntity params = new ChungTuDinhKemTkEntity();
            params.setTrangThai("1"); // Da gui
            params.setId(refId);
            params.setNgaySua(currentDate);
            ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.updateStatus(params);
            if (chungTuDinhKemTkEntity == null) {
                throw new ConflictException("Cập nhật trạng thái chứng từ không thành công.");
            }
            log.info("<--------------------------message tờ khai nhập----------------------------->");
            log.info(dataSigned);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicId, dataSigned);
            return "Gửi chứng từ lên hệ thống tiếp nhận hải quan thành công.";
        }
        Optional<MauToKhaiHqEntity> mauToKhaiHqEntity = mauToKhaiHqRepositoryImpl.findById(mauTkHqId);
        if (!mauToKhaiHqEntity.isPresent()) {
            throw new BadRequestException("Không tồn tại mẫu tờ khai hải quan có id " + mauTkHqId);
        }
        if (mauToKhaiHqEntity.get().getSoTiepNhan() == null) {
            throw new BadRequestException("Tờ khai hải quan có id " + mauTkHqId + " chưa có số tiếp nhận");
        }

        // Set thông tin và lưu ChungTuDinhKemTkEntity
        MauToKhaiHqEntity toKhai = mauToKhaiHqEntity.get();
        String maDoanhNghiep = toKhai.getMaDoanhNghiep();
        ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = new ChungTuDinhKemTkEntity();
        chungTuDinhKemTkEntity.setMauTkHqId(toKhai.getId());
        chungTuDinhKemTkEntity.setLoaiChungTu(MessageType.CTDT_CMTC.getValue());
        chungTuDinhKemTkEntity.setKenhKhaiBao(toKhai.getKenhKhaiBao());
        chungTuDinhKemTkEntity.setChucNangChungTu(MessageFuntion.KHAI_BAO.getValue());
        chungTuDinhKemTkEntity.setNoiKhaiBao(toKhai.getHaiQuanTiepNhanKb());
        chungTuDinhKemTkEntity.setHqTiepNhanChungTu(toKhai.getHaiQuanTiepNhanKb()); // Cần confirm lại
        chungTuDinhKemTkEntity.setNgayTao(currentDate);
        chungTuDinhKemTkEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
        chungTuDinhKemTkEntity.setMaDoanhNghiep(maDoanhNghiep);
        chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.insert(chungTuDinhKemTkEntity);

        CertificateContentDto certificateContentDto = CtdtCmtcMapper.INSTANCE
                .chungTuDinhKemToKhaiToCertificateContentDto(chungTuDinhKemTkEntity);

        certificateContentDto.setRefCustomsId(String.valueOf(toKhai.getSoTiepNhan()));
        /** Người khai HQ */
        certificateContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        certificateContentDto.setDeclarantName(toKhai.getTenNguoiKhaiHq());
        /** Doanh nghiệp XNK */
        if (toKhai.getLoaiTkHq().equals(MessageType.TK_NHAP_KHAU.getValue())) {
            certificateContentDto.setImporterCode(toKhai.getMaNguoiNk());
            certificateContentDto.setImporterName(toKhai.getTenNguoiNk());
            certificateContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        } else {
            certificateContentDto.setImporterCode(toKhai.getMaNguoiXk());
            certificateContentDto.setImporterName(toKhai.getTenNguoiXk());
            certificateContentDto.setImporterAddress(toKhai.getDiaChiNguoiXk());
        }
        /** Thông tin tờ khai */
        certificateContentDto.setDeclarantCode(toKhai.getSoTk());
        certificateContentDto.setRegisteredDate(VnaccsConvert.date2String(toKhai.getNgayTraLoi(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD.getValueEnum()));
        certificateContentDto.setDeclarationKindCode(toKhai.getMaLoaiHinh());
        certificateContentDto.setDeclarationOffice(toKhai.getHaiQuanTiepNhanKb());

        certificateContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        certificateContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        certificateContentDto.setDeclarationNo(toKhai.getSoTk());

        List<CertificateContentDto.Certificate> certificates = new ArrayList<>();
        for (CtdtCmtcRequests.CtdtCmtcRequest item : ctdtCmtcRequests.getCtdt()) {
            CtdtCmtcEntity ctdtCmtcEntity = new CtdtCmtcEntity();
            BeanUtils.copyProperties(item, ctdtCmtcEntity);
            ctdtCmtcEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtCmtcEntity.setNgayTao(currentDate);
            ctdtCmtcEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtCmtcEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtCmtcEntity = ctdtCmtcRepositoryImpl.insert(ctdtCmtcEntity);

            CertificateContentDto.Certificate certificate = CtdtCmtcMapper.INSTANCE
                    .ctdtCmtcToCertificateContentDto(ctdtCmtcEntity);

            CtdtFileDinhKemEntity ctdtFileDinhKemEntity = new CtdtFileDinhKemEntity();
            BeanUtils.copyProperties(item.getFileDinhKem(), ctdtFileDinhKemEntity);
            String fileId = this.uploadFileV6(Base64.decodeBase64(item.getFileDinhKem().getFile()),
                    item.getFileDinhKem().getTenFileChungTu(), "0000", "");
            ctdtFileDinhKemEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtFileDinhKemEntity.setFileId(fileId);
            ctdtFileDinhKemEntity.setLoaiChungTuId(ctdtCmtcEntity.getId());
            ctdtFileDinhKemEntity.setNgayTao(currentDate);
            ctdtFileDinhKemEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtFileDinhKemEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtFileDinhKemEntity = ctdtFileDinhKemRepositoryImpl.insert(ctdtFileDinhKemEntity);

            // Set thông tin file vào object Invoice gửi lên hải quan
            certificate.setFileName(ctdtFileDinhKemEntity.getTenFileChungTu());
            // Thêm invoice vào list invoice
            certificates.add(certificate);
        }
        certificateContentDto.setCertificates(certificates);

        /* init ReferenceDto */
        ReferenceDto objreference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");
        /* init SendApplicationDto */
        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(toKhai.getTenNguoiKhaiHq());
        objsendApplication.setCompanyIdentity(toKhai.getMaNguoiKhaiHq());
        /* init FromDto */
        FromDto objFrom = new FromDto();
        objFrom.setName(toKhai.getTenNguoiKhaiHq());
        objFrom.setIdentity(toKhai.getMaNguoiKhaiHq());
        /* init ToDto */
        ToDto objTo = new ToDto();
        objTo.setName(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        objTo.setIdentity(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        /* init SubjectDto */

        SubjectDto objSubject = new SubjectDto(MessageType.CTDT_CMTC.getValue(), MessageFuntion.KHAI_BAO.getValue());
        objSubject.setRefId(chungTuDinhKemTkEntity.getId());
        /* init BodyTKDto */
        CtdtBodyDto<CertificateContentDto> objBody = new CtdtBodyDto<>();
        objBody.setContent(certificateContentDto);

        /* init HeaderSendDto */
        HeaderSendDto objHeader = new HeaderSendDto();
        objHeader.setDeclarantChannel(mauToKhaiHqEntity.get().getKenhKhaiBao());
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
        CtdtCertificateRequestDto ctdtCertificateRequestDto = new CtdtCertificateRequestDto();
        ctdtCertificateRequestDto.setHeader(objHeader);
        ctdtCertificateRequestDto.setBody(objBody);
        ctdtCertificateRequestDto.setSignature(objSignature);
        String jsonTopic = JacksonEx.getInstance().object2String(ctdtCertificateRequestDto);
        return jsonTopic;
    }


    /**
     * @param ctdtTktgRequests
     * @param mauTkHqId
     * @param isSigned
     * @return
     * @throws ResourceNotFoundException
     * @throws ConflictException
     */
    @Transactional
    public String guiCtdtTktg(CtdtTktgRequests ctdtTktgRequests, String mauTkHqId, Integer isSigned)
            throws ResourceNotFoundException, ConflictException {
        String currentUser = this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid();
        Date currentDate = VnaccsConvert.getCurrentDateTime();
        if (isSigned != null && isSigned == 1) {
            String dataSigned = ctdtTktgRequests.getDataSigned();
            String refId = (String) JsonUtils.getValueWithElement(dataSigned, "$.body.content.refId");
            ChungTuDinhKemTkEntity params = new ChungTuDinhKemTkEntity();
            params.setTrangThai("1"); // Da gui
            params.setId(refId);
            params.setNgaySua(currentDate);
            ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.updateStatus(params);
            if (chungTuDinhKemTkEntity == null) {
                throw new ConflictException("Cập nhật trạng thái chứng từ không thành công.");
            }
            log.info("<--------------------------message tờ khai nhập----------------------------->");
            log.info(dataSigned);
            log.info("<--------------------------------------------------------------------------->");
            this.kafkaTemplate.send(sTopicId, dataSigned);
            return "Gửi chứng từ lên hệ thống tiếp nhận hải quan thành công.";
        }
        Optional<MauToKhaiHqEntity> mauToKhaiHqEntity = mauToKhaiHqRepositoryImpl.findById(mauTkHqId);
        if (!mauToKhaiHqEntity.isPresent()) {
            throw new ResourceNotFoundException("Không tồn tại mẫu tờ khai hải quan có id " + mauTkHqId);
        }
        if (mauToKhaiHqEntity.get().getSoTiepNhan() == null) {
            throw new ConflictException("Tờ khai hải quan có id " + mauTkHqId + " chưa có số tiếp nhận");
        }
        MauToKhaiHqEntity toKhai = mauToKhaiHqEntity.get();
        String maDoanhNghiep = toKhai.getMaDoanhNghiep();
        ChungTuDinhKemTkEntity chungTuDinhKemTkEntity = new ChungTuDinhKemTkEntity();
        chungTuDinhKemTkEntity.setMauTkHqId(toKhai.getId());
        chungTuDinhKemTkEntity.setLoaiChungTu(MessageType.CTDT_TKTG.getValue());
        chungTuDinhKemTkEntity.setKenhKhaiBao(toKhai.getKenhKhaiBao());
        chungTuDinhKemTkEntity.setChucNangChungTu(MessageFuntion.KHAI_BAO.getValue());
        chungTuDinhKemTkEntity.setNoiKhaiBao(toKhai.getHaiQuanTiepNhanKb());
        chungTuDinhKemTkEntity.setHqTiepNhanChungTu(toKhai.getHaiQuanTiepNhanKb()); // Cần confirm lại
        chungTuDinhKemTkEntity.setNgayTao(currentDate);
        chungTuDinhKemTkEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
        chungTuDinhKemTkEntity.setMaDoanhNghiep(maDoanhNghiep);
        chungTuDinhKemTkEntity = chungTuDinhKemTkRepositoryImpl.insert(chungTuDinhKemTkEntity);

        ValuationDeclarationContentDto valuationDeclarationContentDto = CtdtTktgMapper.INSTANCE
                .chungTuDinhKemTkToValuationDeclarationContentDto(chungTuDinhKemTkEntity);
        valuationDeclarationContentDto.setRefCustomsId(String.valueOf(toKhai.getSoTiepNhan()));
        /** Người khai HQ */
        valuationDeclarationContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        valuationDeclarationContentDto.setDeclarantName(toKhai.getTenNguoiKhaiHq());
        /** Doanh nghiệp XNK */
        if (toKhai.getLoaiTkHq().equals(MessageType.TK_NHAP_KHAU.getValue())) {
            valuationDeclarationContentDto.setImporterCode(toKhai.getMaNguoiNk());
            valuationDeclarationContentDto.setImporterName(toKhai.getTenNguoiNk());
            valuationDeclarationContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        } else {
            valuationDeclarationContentDto.setImporterCode(toKhai.getMaNguoiXk());
            valuationDeclarationContentDto.setImporterName(toKhai.getTenNguoiXk());
            valuationDeclarationContentDto.setImporterAddress(toKhai.getDiaChiNguoiXk());
        }
        /** Thông tin tờ khai */
        valuationDeclarationContentDto.setDeclarantCode(toKhai.getSoTk());
        valuationDeclarationContentDto.setRegisteredDate(VnaccsConvert.date2String(toKhai.getNgayTraLoi(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD.getValueEnum()));
        valuationDeclarationContentDto.setDeclarationKindCode(toKhai.getMaLoaiHinh());
        valuationDeclarationContentDto.setDeclarationOffice(toKhai.getHaiQuanTiepNhanKb());

        valuationDeclarationContentDto.setDeclarantCode(toKhai.getMaNguoiKhaiHq());
        valuationDeclarationContentDto.setImporterAddress(toKhai.getDiaChiNguoiNk());
        valuationDeclarationContentDto.setDeclarationNo(toKhai.getSoTk());

        // List object license để gửi lên hải quan
        List<ValuationDeclarationContentDto.ValuationDeclaration> valuationDeclarations = new ArrayList<>();

        for (CtdtTktgRequests.CtdtTktgRequest item : ctdtTktgRequests.getCtdt()) {
            CtdtTktgEntity ctdtTktgEntity = new CtdtTktgEntity();
            BeanUtils.copyProperties(item, ctdtTktgEntity);
            ctdtTktgEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtTktgEntity.setNgayTao(currentDate);
            ctdtTktgEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtTktgEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtTktgEntity = ctdtTktgRepositoryImpl.insert(ctdtTktgEntity);

            // Map entity license sang dto license
            ValuationDeclarationContentDto.ValuationDeclaration valuationDeclaration = CtdtTktgMapper.INSTANCE
                    .ctdtTktgToValuationDeclarationContentDto(ctdtTktgEntity);

            CtdtFileDinhKemEntity ctdtFileDinhKemEntity = new CtdtFileDinhKemEntity();
            BeanUtils.copyProperties(item.getFileDinhKem(), ctdtFileDinhKemEntity);
            String fileId = this.uploadFileV6(Base64.decodeBase64(item.getFileDinhKem().getFile()),
                    item.getFileDinhKem().getTenFileChungTu(), "0000", "");
            ctdtFileDinhKemEntity.setChungTuDinhKemTkId(chungTuDinhKemTkEntity.getId());
            ctdtFileDinhKemEntity.setFileId(fileId);
            ctdtFileDinhKemEntity.setLoaiChungTuId(ctdtTktgEntity.getId());
            ctdtFileDinhKemEntity.setNgayTao(currentDate);
            ctdtFileDinhKemEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
            ctdtFileDinhKemEntity.setMaDoanhNghiep(maDoanhNghiep);
            ctdtFileDinhKemEntity = ctdtFileDinhKemRepositoryImpl.insert(ctdtFileDinhKemEntity);

            // Set thông tin file trong object license dto
            valuationDeclaration.setFileName(ctdtFileDinhKemEntity.getTenFileChungTu());
            valuationDeclarations.add(valuationDeclaration);
        }
        valuationDeclarationContentDto.setValuationDeclarations(valuationDeclarations);
        /* init ReferenceDto */
        ReferenceDto objreference = new ReferenceDto(ConstantEtcCustomsEClare.APPLICATION_VERSION, "");
        /* init SendApplicationDto */
        SendApplicationDto objsendApplication = new SendApplicationDto();
        objsendApplication.setCompanyName(toKhai.getTenNguoiKhaiHq());
        objsendApplication.setCompanyIdentity(toKhai.getMaNguoiKhaiHq());
        /* init FromDto */
        FromDto objFrom = new FromDto();
        objFrom.setName(toKhai.getTenNguoiKhaiHq());
        objFrom.setIdentity(toKhai.getMaNguoiKhaiHq());
        /* init ToDto */
        ToDto objTo = new ToDto();
        objTo.setName(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        objTo.setIdentity(mauToKhaiHqEntity.get().getHaiQuanTiepNhanKb());
        /* init SubjectDto */

        SubjectDto objSubject = new SubjectDto(MessageType.CTDT_TKTG.getValue(), MessageFuntion.KHAI_BAO.getValue());
        objSubject.setRefId(chungTuDinhKemTkEntity.getId());
        /* init BodyTKDto */
        CtdtBodyDto<ValuationDeclarationContentDto> objBody = new CtdtBodyDto<>();
        objBody.setContent(valuationDeclarationContentDto);

        /* init HeaderSendDto */
        HeaderSendDto objHeader = new HeaderSendDto();
        objHeader.setDeclarantChannel(mauToKhaiHqEntity.get().getKenhKhaiBao());
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
        CtdtValuationDeclarationRequestDto ctdtValuationDeclarationRequestDto = new CtdtValuationDeclarationRequestDto();
        ctdtValuationDeclarationRequestDto.setHeader(objHeader);
        ctdtValuationDeclarationRequestDto.setBody(objBody);
        ctdtValuationDeclarationRequestDto.setSignature(objSignature);
        String jsonTopic = JacksonEx.getInstance().object2String(ctdtValuationDeclarationRequestDto);
        return jsonTopic;
    }

    @Override
    public ChungTuDinhKemTkEntity convertId2Entity(SequenceKey objInput) {
        return null;
    }

    /**
     * @param objInput
     * @return
     */
    public Page<CtdtAttachedResponseDto> selectCtdkTkBymauTkHqId(KeySearchChungTuDinhKemTk objInput) {
        List<CtdtAttachedResponseDto> chungTuDinhKemResponseList = new ArrayList<>();

        Page<ChungTuDinhKemTkEntity> chungTuDinhKemTk = chungTuDinhKemTkRepositoryImpl
                .selectCtdkTkBymauTkHqId(objInput);
        if (chungTuDinhKemTk == null) return null;
        for (ChungTuDinhKemTkEntity item : chungTuDinhKemTk) {
            String loaiChungTu = item.getLoaiChungTu();
            // lay giay phep
            if (loaiChungTu.equals(MessageType.CTDT_GIAY_PHEP.getValue())) {
                List<CtdtGiayPhepEntity> ctdtGiayPhepEntities = ctdtGiayPhepRepositoryImpl
                        .searchByChungTuDinhKemTkId(item.getId());
                for (CtdtGiayPhepEntity ctdtGiayPhep : ctdtGiayPhepEntities) {
                    CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                            .searchByChungTuDinhKemTkIdAndFileId(ctdtGiayPhep.getId(), item.getId());
                    CtdtAttachedResponseDto chungTuDinhKem = new CtdtAttachedResponseDto();
                    chungTuDinhKem.setNgayDangKyCt(item.getNgayDangKyCt());
                    chungTuDinhKem.setSoTn(item.getSoTn());
                    chungTuDinhKem.setNgayTao(VnaccsConvert.date2String(item.getNgayTao(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD_H.getValueEnum()));
                    chungTuDinhKem.setHqTiepNhan(item.getHqTraLoiCt());
                    chungTuDinhKem.setTrangThai(item.getTrangThai());
                    chungTuDinhKem.setNgayHqPhanHoi(item.getNgayHqPhanHoi());
                    chungTuDinhKem.setNoiDungHqPhanHoi(item.getNoiDungHqPhanHoi());
                    chungTuDinhKem.setLoaiChungTu(item.getLoaiChungTu());
                    chungTuDinhKem.setGhiChu(ctdtGiayPhep.getGhiChuKhac());
                    chungTuDinhKem.setCtdtFileDinhKem(ctdtFileDinhKem);
                    chungTuDinhKemResponseList.add(chungTuDinhKem);
                }
            }
            // lay hoa don
            if (loaiChungTu.equals(MessageType.CTDT_HOA_DON_TM.getValue())) {
                List<CtdtHoaDonTmEntity> ctdtHoaDonTmEntities = ctdtHoaDonTmRepositoryImpl
                        .searchByChungTuDinhKemTkId(item.getId());
                for (CtdtHoaDonTmEntity ctdtHoaDonTm : ctdtHoaDonTmEntities) {
                    CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                            .searchByChungTuDinhKemTkIdAndFileId(ctdtHoaDonTm.getId(), item.getId());
                    CtdtAttachedResponseDto chungTuDinhKem = new CtdtAttachedResponseDto();
                    chungTuDinhKem.setNgayDangKyCt(item.getNgayDangKyCt());
                    chungTuDinhKem.setSoTn(item.getSoTn());
                    chungTuDinhKem.setNgayTao(VnaccsConvert.date2String(item.getNgayTao(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD_H.getValueEnum()));
                    chungTuDinhKem.setHqTiepNhan(item.getHqTraLoiCt());
                    chungTuDinhKem.setTrangThai(item.getTrangThai());
                    chungTuDinhKem.setNgayHqPhanHoi(item.getNgayHqPhanHoi());
                    chungTuDinhKem.setNoiDungHqPhanHoi(item.getNoiDungHqPhanHoi());
                    chungTuDinhKem.setLoaiChungTu(item.getLoaiChungTu());
                    chungTuDinhKem.setGhiChu(ctdtHoaDonTm.getGhiChuKhac());
                    chungTuDinhKem.setCtdtFileDinhKem(ctdtFileDinhKem);
                    chungTuDinhKemResponseList.add(chungTuDinhKem);
                }
            }
            // lay c/o
            if (loaiChungTu.equals(MessageType.CTDT_CO.getValue())) {
                List<CtdtCoEntity> ctdtCoEntities = ctdtCoRepositoryImpl.searchByChungTuDinhKemTkId(item.getId());
                for (CtdtCoEntity ctdtCo : ctdtCoEntities) {
                    CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                            .searchByChungTuDinhKemTkIdAndFileId(ctdtCo.getId(), item.getId());
                    CtdtAttachedResponseDto chungTuDinhKem = new CtdtAttachedResponseDto();
                    chungTuDinhKem.setNgayDangKyCt(item.getNgayDangKyCt());
                    chungTuDinhKem.setSoTn(item.getSoTn());
                    chungTuDinhKem.setNgayTao(VnaccsConvert.date2String(item.getNgayTao(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD_H.getValueEnum()));
                    chungTuDinhKem.setHqTiepNhan(item.getHqTraLoiCt());
                    chungTuDinhKem.setTrangThai(item.getTrangThai());
                    chungTuDinhKem.setNgayHqPhanHoi(item.getNgayHqPhanHoi());
                    chungTuDinhKem.setNoiDungHqPhanHoi(item.getNoiDungHqPhanHoi());
                    chungTuDinhKem.setLoaiChungTu(item.getLoaiChungTu());
                    chungTuDinhKem.setGhiChu(ctdtCo.getGhiChuKhac());
                    chungTuDinhKem.setCtdtFileDinhKem(ctdtFileDinhKem);
                    chungTuDinhKemResponseList.add(chungTuDinhKem);
                }
            }
            // lay van don
            if (loaiChungTu.equals(MessageType.CTDT_VAN_DON.getValue())) {
                List<CtdtVanDonEntity> ctdtVanDonEntities = ctdtVanDonRepositoryImpl
                        .searchByChungTuDinhKemTkId(item.getId());
                for (CtdtVanDonEntity ctdtVanDon : ctdtVanDonEntities) {
                    CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                            .searchByChungTuDinhKemTkIdAndFileId(ctdtVanDon.getId(), item.getId());
                    CtdtAttachedResponseDto chungTuDinhKem = new CtdtAttachedResponseDto();
                    chungTuDinhKem.setNgayDangKyCt(item.getNgayDangKyCt());
                    chungTuDinhKem.setSoTn(item.getSoTn());
                    chungTuDinhKem.setNgayTao(VnaccsConvert.date2String(item.getNgayTao(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD_H.getValueEnum()));
                    chungTuDinhKem.setHqTiepNhan(item.getHqTraLoiCt());
                    chungTuDinhKem.setTrangThai(item.getTrangThai());
                    chungTuDinhKem.setNgayHqPhanHoi(item.getNgayHqPhanHoi());
                    chungTuDinhKem.setNoiDungHqPhanHoi(item.getNoiDungHqPhanHoi());
                    chungTuDinhKem.setLoaiChungTu(item.getLoaiChungTu());
                    chungTuDinhKem.setGhiChu(ctdtVanDon.getGhiChuKhac());
                    chungTuDinhKem.setCtdtFileDinhKem(ctdtFileDinhKem);
                    chungTuDinhKemResponseList.add(chungTuDinhKem);
                }
            }
            // lay bang ke hang hoa
            if (loaiChungTu.equals(MessageType.CTDT_BKHH.getValue())) {
                List<CtdtBkhhEntity> bkhhEntities = ctdtBkhhRepositoryImpl.searchByChungTuDinhKemTkId(item.getId());
                for (CtdtBkhhEntity bkhh : bkhhEntities) {
                    CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                            .searchByChungTuDinhKemTkIdAndFileId(bkhh.getId(), item.getId());
                    CtdtAttachedResponseDto chungTuDinhKem = new CtdtAttachedResponseDto();
                    chungTuDinhKem.setNgayDangKyCt(item.getNgayDangKyCt());
                    chungTuDinhKem.setSoTn(item.getSoTn());
                    chungTuDinhKem.setNgayTao(VnaccsConvert.date2String(item.getNgayTao(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD_H.getValueEnum()));
                    chungTuDinhKem.setHqTiepNhan(item.getHqTraLoiCt());
                    chungTuDinhKem.setTrangThai(item.getTrangThai());
                    chungTuDinhKem.setNgayHqPhanHoi(item.getNgayHqPhanHoi());
                    chungTuDinhKem.setNoiDungHqPhanHoi(item.getNoiDungHqPhanHoi());
                    chungTuDinhKem.setLoaiChungTu(item.getLoaiChungTu());
                    chungTuDinhKem.setGhiChu(bkhh.getGhiChuKhac());
                    chungTuDinhKem.setCtdtFileDinhKem(ctdtFileDinhKem);
                    chungTuDinhKemResponseList.add(chungTuDinhKem);
                }
            }
            // lay giay chung nhan kiem tra chuyen nganh
            if (loaiChungTu.equals(MessageType.CTDT_GIAY_CNKTCN.getValue())) {
                List<CtdtGiayCnktcnEntity> giayCnktcnEntities = ctdtGiayCnktcnRepositoryImpl
                        .searchByChungTuDinhKemTkId(item.getId());
                for (CtdtGiayCnktcnEntity ctdtGiayCnktcn : giayCnktcnEntities) {
                    CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                            .searchByChungTuDinhKemTkIdAndFileId(ctdtGiayCnktcn.getId(), item.getId());
                    CtdtAttachedResponseDto chungTuDinhKem = new CtdtAttachedResponseDto();
                    chungTuDinhKem.setNgayDangKyCt(item.getNgayDangKyCt());
                    chungTuDinhKem.setSoTn(item.getSoTn());
                    chungTuDinhKem.setNgayTao(VnaccsConvert.date2String(item.getNgayTao(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD_H.getValueEnum()));
                    chungTuDinhKem.setHqTiepNhan(item.getHqTraLoiCt());
                    chungTuDinhKem.setTrangThai(item.getTrangThai());
                    chungTuDinhKem.setNgayHqPhanHoi(item.getNgayHqPhanHoi());
                    chungTuDinhKem.setNoiDungHqPhanHoi(item.getNoiDungHqPhanHoi());
                    chungTuDinhKem.setLoaiChungTu(item.getLoaiChungTu());
                    chungTuDinhKem.setGhiChu(ctdtGiayCnktcn.getGhiChuKhac());
                    chungTuDinhKem.setCtdtFileDinhKem(ctdtFileDinhKem);
                    chungTuDinhKemResponseList.add(chungTuDinhKem);
                }
            }
            // lay chung minh to chuc
            if (loaiChungTu.equals(MessageType.CTDT_CMTC.getValue())) {
                List<CtdtCmtcEntity> cmtcEntities = ctdtCmtcRepositoryImpl.searchByChungTuDinhKemTkId(item.getId());
                for (CtdtCmtcEntity ctdtCmtc : cmtcEntities) {
                    CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                            .searchByChungTuDinhKemTkIdAndFileId(ctdtCmtc.getId(), item.getId());
                    CtdtAttachedResponseDto chungTuDinhKem = new CtdtAttachedResponseDto();
                    chungTuDinhKem.setNgayDangKyCt(item.getNgayDangKyCt());
                    chungTuDinhKem.setSoTn(item.getSoTn());
                    chungTuDinhKem.setNgayTao(VnaccsConvert.date2String(item.getNgayTao(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD_H.getValueEnum()));
                    chungTuDinhKem.setHqTiepNhan(item.getHqTraLoiCt());
                    chungTuDinhKem.setTrangThai(item.getTrangThai());
                    chungTuDinhKem.setNgayHqPhanHoi(item.getNgayHqPhanHoi());
                    chungTuDinhKem.setNoiDungHqPhanHoi(item.getNoiDungHqPhanHoi());
                    chungTuDinhKem.setLoaiChungTu(item.getLoaiChungTu());
                    chungTuDinhKem.setGhiChu(ctdtCmtc.getGhiChuKhac());
                    chungTuDinhKem.setCtdtFileDinhKem(ctdtFileDinhKem);
                    chungTuDinhKemResponseList.add(chungTuDinhKem);
                }
            }
            // lay hop dong uy thac
            if (loaiChungTu.equals(MessageType.CTDT_HOP_DONG_UT.getValue())) {
                List<CtdtHopDongUtEntity> ctdtHopDongUtEntities = ctdtHopDongUtRepositoryImpl
                        .searchByChungTuDinhKemTkId(item.getId());
                for (CtdtHopDongUtEntity ctdtHopDongUt : ctdtHopDongUtEntities) {
                    CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                            .searchByChungTuDinhKemTkIdAndFileId(ctdtHopDongUt.getId(), item.getId());
                    CtdtAttachedResponseDto chungTuDinhKem = new CtdtAttachedResponseDto();
                    chungTuDinhKem.setNgayDangKyCt(item.getNgayDangKyCt());
                    chungTuDinhKem.setSoTn(item.getSoTn());
                    chungTuDinhKem.setNgayTao(VnaccsConvert.date2String(item.getNgayTao(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD_H.getValueEnum()));
                    chungTuDinhKem.setHqTiepNhan(item.getHqTraLoiCt());
                    chungTuDinhKem.setTrangThai(item.getTrangThai());
                    chungTuDinhKem.setNgayHqPhanHoi(item.getNgayHqPhanHoi());
                    chungTuDinhKem.setNoiDungHqPhanHoi(item.getNoiDungHqPhanHoi());
                    chungTuDinhKem.setLoaiChungTu(item.getLoaiChungTu());
                    chungTuDinhKem.setGhiChu(ctdtHopDongUt.getGhiChuKhac());
                    chungTuDinhKem.setCtdtFileDinhKem(ctdtFileDinhKem);
                    chungTuDinhKemResponseList.add(chungTuDinhKem);
                }
            }
            // lay to khai tri gia
            if (loaiChungTu.equals(MessageType.CTDT_TKTG.getValue())) {
                List<CtdtTktgEntity> ctdtTktgEntities = ctdtTktgRepositoryImpl.searchByChungTuDinhKemTkId(item.getId());
                for (CtdtTktgEntity ctdtTktg : ctdtTktgEntities) {
                    CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                            .searchByChungTuDinhKemTkIdAndFileId(ctdtTktg.getId(), item.getId());
                    CtdtAttachedResponseDto chungTuDinhKem = new CtdtAttachedResponseDto();
                    chungTuDinhKem.setNgayDangKyCt(item.getNgayDangKyCt());
                    chungTuDinhKem.setSoTn(item.getSoTn());
                    chungTuDinhKem.setNgayTao(VnaccsConvert.date2String(item.getNgayTao(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD_H.getValueEnum()));
                    chungTuDinhKem.setHqTiepNhan(item.getHqTraLoiCt());
                    chungTuDinhKem.setTrangThai(item.getTrangThai());
                    chungTuDinhKem.setNgayHqPhanHoi(item.getNgayHqPhanHoi());
                    chungTuDinhKem.setNoiDungHqPhanHoi(item.getNoiDungHqPhanHoi());
                    chungTuDinhKem.setLoaiChungTu(item.getLoaiChungTu());
                    chungTuDinhKem.setGhiChu(ctdtTktg.getGhiChuKhac());
                    chungTuDinhKem.setCtdtFileDinhKem(ctdtFileDinhKem);
                    chungTuDinhKemResponseList.add(chungTuDinhKem);
                }
            }
            // lay may moc thiet bi
            if (loaiChungTu.equals(MessageType.CTDT_MMTB.getValue())) {
                List<CtdtMmtbEntity> mmtbEntities = ctdtMmtbRepositoryImpl.searchByChungTuDinhKemTkId(item.getId());
                for (CtdtMmtbEntity ctdtMmtb : mmtbEntities) {
                    CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                            .searchByChungTuDinhKemTkIdAndFileId(ctdtMmtb.getId(), item.getId());
                    CtdtAttachedResponseDto chungTuDinhKem = new CtdtAttachedResponseDto();
                    chungTuDinhKem.setNgayDangKyCt(item.getNgayDangKyCt());
                    chungTuDinhKem.setSoTn(item.getSoTn());
                    chungTuDinhKem.setNgayTao(VnaccsConvert.date2String(item.getNgayTao(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD_H.getValueEnum()));
                    chungTuDinhKem.setHqTiepNhan(item.getHqTraLoiCt());
                    chungTuDinhKem.setTrangThai(item.getTrangThai());
                    chungTuDinhKem.setNgayHqPhanHoi(item.getNgayHqPhanHoi());
                    chungTuDinhKem.setNoiDungHqPhanHoi(item.getNoiDungHqPhanHoi());
                    chungTuDinhKem.setLoaiChungTu(item.getLoaiChungTu());
                    chungTuDinhKem.setGhiChu(ctdtMmtb.getGhiChuKhac());
                    chungTuDinhKem.setCtdtFileDinhKem(ctdtFileDinhKem);
                    chungTuDinhKemResponseList.add(chungTuDinhKem);
                }
            }

            // lay GTGT 5%
            if (loaiChungTu.equals(MessageType.CTDT_CTAT_5_PT.getValue())) {
                List<CtdtCtat5PhanTramEntity> ctat5PhanTramEntities = ctdtCtat5PhanTramRepositoryImpl
                        .searchByChungTuDinhKemTkId(item.getId());
                for (CtdtCtat5PhanTramEntity ctat5PhanTram : ctat5PhanTramEntities) {
                    CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                            .searchByChungTuDinhKemTkIdAndFileId(ctat5PhanTram.getId(), item.getId());
                    CtdtAttachedResponseDto chungTuDinhKem = new CtdtAttachedResponseDto();
                    chungTuDinhKem.setNgayDangKyCt(item.getNgayDangKyCt());
                    chungTuDinhKem.setSoTn(item.getSoTn());
                    chungTuDinhKem.setNgayTao(VnaccsConvert.date2String(item.getNgayTao(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD_H.getValueEnum()));
                    chungTuDinhKem.setHqTiepNhan(item.getHqTraLoiCt());
                    chungTuDinhKem.setTrangThai(item.getTrangThai());
                    chungTuDinhKem.setNgayHqPhanHoi(item.getNgayHqPhanHoi());
                    chungTuDinhKem.setNoiDungHqPhanHoi(item.getNoiDungHqPhanHoi());
                    chungTuDinhKem.setLoaiChungTu(item.getLoaiChungTu());
                    chungTuDinhKem.setGhiChu(ctat5PhanTram.getGhiChuKhac());
                    chungTuDinhKem.setCtdtFileDinhKem(ctdtFileDinhKem);
                    chungTuDinhKemResponseList.add(chungTuDinhKem);
                }
            }
        }
        int start = objInput.getPage() * objInput.getSize();
        int end = Math.min((objInput.getPageEnd()), chungTuDinhKemResponseList.size());
        return new PageImpl<>(end == 0 ? chungTuDinhKemResponseList : chungTuDinhKemResponseList.subList(start, end),
                PageRequest.of(objInput.getPage(), objInput.getSize()), chungTuDinhKemResponseList.size());
    }

    /**
     * @param sid
     * @return file
     */
    public ResponseEntity<byte[]> downloadFileV6Demo(String sid) {
        return this.downloadFileV6(sid);
    }

    /**
     * @param ctdtId chung tu dien tu id
     * @return detail chung tu
     * @throws ResourceNotFoundException
     */
    public CtdtDetailResponseDto selectDetailCtdkTkById(String ctdtId) throws ResourceNotFoundException {
        CtdtDetailResponseDto detailChungTuDinhKemResponse = new CtdtDetailResponseDto();

        Optional<ChungTuDinhKemTkEntity> chungTuDinhKemTk = chungTuDinhKemTkRepositoryImpl.findById(ctdtId);
        if (!chungTuDinhKemTk.isPresent()) {
            throw new ResourceNotFoundException("Không tìm thấy chứng từ.");
        }
        ChungTuDinhKemTkEntity chungTuDinhKem = chungTuDinhKemTk.get();
        String loaiChungTu = chungTuDinhKem.getLoaiChungTu();

        // Giay phep
        if (loaiChungTu.equals(MessageType.CTDT_GIAY_PHEP.getValue())) {
            Optional<CtdtGiayPhepEntity> ctdtGiayPhep = ctdtGiayPhepRepositoryImpl
                    .searchOptionalByChungTuDinhKemTkId(chungTuDinhKem.getId());
            CtdtGiayPhepEntity ctdtGiayPhepEntity = ctdtGiayPhep.get();
            String[] hangHoaIds = ctdtGiayPhepEntity.getHangHoaId() == null ? new String[0] : ctdtGiayPhepEntity.getHangHoaId().split(",");
            List<CtdtGiayPhepRequests.CtdtThongTinHangHoaRequest> hangLst = new ArrayList<>();
            for (String sttHangItem : hangHoaIds) {
                Optional<DmHangHoaTkEntity> hangHoa = dmHangHoaTkRepositoryImpl.findById(Long.parseLong(sttHangItem));
                hangHoa.ifPresent(dmHangHoaTkEntity -> {
                    CtdtGiayPhepRequests.CtdtThongTinHangHoaRequest item = new CtdtGiayPhepRequests.CtdtThongTinHangHoaRequest();
                    item.setId(dmHangHoaTkEntity.getId());
                    item.setMaSoHangHoa(dmHangHoaTkEntity.getMaHs());
                    item.setSoThuTuHang(dmHangHoaTkEntity.getSttHang());
                    item.setTenHangHoa(dmHangHoaTkEntity.getTenHang());
                    item.setSoLuong(dmHangHoaTkEntity.getLuong());
                    item.setDonViTinh(dmHangHoaTkEntity.getMaDvt());
                    item.setTriGia(dmHangHoaTkEntity.getTriGiaKb());
                    item.setNguyenTe(dmHangHoaTkEntity.getDgiaKbMaNt());
                    hangLst.add(item);
                });
            }
            ctdtGiayPhepEntity.setDsHang(hangLst);
            CtdtFileDinhKemEntity giayPhepFileDk = ctdtFileDinhKemRepositoryImpl
                    .searchByChungTuDinhKemTkIdAndFileId(ctdtGiayPhep.get().getId(), chungTuDinhKem.getId());
            CtdtDetailResponseDto.ctdt<CtdtGiayPhepEntity> ctdt = new CtdtDetailResponseDto.ctdt<CtdtGiayPhepEntity>();
            ctdt.setChungTu(ctdtGiayPhepEntity);
            ctdt.setCtdtFileDinhKem(giayPhepFileDk);
            CtdtDetailResponseDto.ctdkTk<CtdtGiayPhepEntity> ctdktk = new CtdtDetailResponseDto.ctdkTk<CtdtGiayPhepEntity>();
            ctdktk.setCtdt(ctdt);
            ctdktk.setChungTuDinhKemTk(chungTuDinhKemTk);
            CtdtDetailResponseDto detailCtdkTk = new CtdtDetailResponseDto();
            detailCtdkTk.setCtdktk(ctdktk);
            return detailCtdkTk;
        }
        // Hoa don tm
        if (loaiChungTu.equals(MessageType.CTDT_HOA_DON_TM.getValue())) {
            Optional<CtdtHoaDonTmEntity> ctdtHoaDon = ctdtHoaDonTmRepositoryImpl
                    .searchOptionalByChungTuDinhKemTkId(chungTuDinhKemTk.get().getId());
            CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                    .searchByChungTuDinhKemTkIdAndFileId(ctdtHoaDon.get().getId(), chungTuDinhKemTk.get().getId());
            CtdtDetailResponseDto.ctdt<CtdtHoaDonTmEntity> ctdt = new CtdtDetailResponseDto.ctdt<CtdtHoaDonTmEntity>();
            ctdt.setChungTu(ctdtHoaDon.get());
            ctdt.setCtdtFileDinhKem(ctdtFileDinhKem);
            CtdtDetailResponseDto.ctdkTk<CtdtHoaDonTmEntity> ctdktk = new CtdtDetailResponseDto.ctdkTk<CtdtHoaDonTmEntity>();
            ctdktk.setCtdt(ctdt);
            ctdktk.setChungTuDinhKemTk(chungTuDinhKemTk);
            CtdtDetailResponseDto detailCtdkTk = new CtdtDetailResponseDto();
            detailCtdkTk.setCtdktk(ctdktk);
            return detailCtdkTk;
        }
        // Lay C/O
        if (loaiChungTu.equals(MessageType.CTDT_CO.getValue())) {
            Optional<CtdtCoEntity> ctdtCo = ctdtCoRepositoryImpl
                    .searchOptionalByChungTuDinhKemTkId(chungTuDinhKemTk.get().getId());
            CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                    .searchByChungTuDinhKemTkIdAndFileId(ctdtCo.get().getId(), chungTuDinhKemTk.get().getId());
            CtdtDetailResponseDto.ctdt<CtdtCoEntity> ctdt = new CtdtDetailResponseDto.ctdt<CtdtCoEntity>();
            ctdt.setChungTu(ctdtCo.get());
            ctdt.setCtdtFileDinhKem(ctdtFileDinhKem);
            CtdtDetailResponseDto.ctdkTk<CtdtCoEntity> ctdktk = new CtdtDetailResponseDto.ctdkTk<CtdtCoEntity>();
            ctdktk.setCtdt(ctdt);
            ctdktk.setChungTuDinhKemTk(chungTuDinhKemTk);
            CtdtDetailResponseDto detailCtdkTk = new CtdtDetailResponseDto();
            detailCtdkTk.setCtdktk(ctdktk);
            return detailCtdkTk;
        }
        // lay van don
        if (loaiChungTu.equals(MessageType.CTDT_VAN_DON.getValue())) {
            Optional<CtdtVanDonEntity> ctdtVanDon = ctdtVanDonRepositoryImpl
                    .searchOptionalByChungTuDinhKemTkId(chungTuDinhKemTk.get().getId());
            if (!ctdtVanDon.isPresent()) throw new ResourceNotFoundException("");
            CtdtVanDonEntity ctdtVanDonEntity = ctdtVanDon.get();
            CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                    .searchByChungTuDinhKemTkIdAndFileId(ctdtVanDonEntity.getId(), chungTuDinhKemTk.get().getId());
            // get ds container
            List<CtdtVanDonContainerEntity> dsContainer = ctdtVanDonContainerRepositoryImpl.findByVanDonId(ctdtVanDonEntity.getId());
            ctdtVanDonEntity.setDsContainer(dsContainer);
            List<CtdtVanDonNhanhEntity> dsVanDonNhanh = ctdtVanDonNhanhRepositoryImpl.findByVanDonId(ctdtVanDonEntity.getId());
            ctdtVanDonEntity.setDsVanDonNhanh(dsVanDonNhanh);
            CtdtDetailResponseDto.ctdt<CtdtVanDonEntity> ctdt = new CtdtDetailResponseDto.ctdt<CtdtVanDonEntity>();
            ctdt.setChungTu(ctdtVanDonEntity);
            ctdt.setCtdtFileDinhKem(ctdtFileDinhKem);
            CtdtDetailResponseDto.ctdkTk<CtdtVanDonEntity> ctdktk = new CtdtDetailResponseDto.ctdkTk<CtdtVanDonEntity>();
            ctdktk.setCtdt(ctdt);
            ctdktk.setChungTuDinhKemTk(chungTuDinhKemTk);
            CtdtDetailResponseDto detailCtdkTk = new CtdtDetailResponseDto();
            detailCtdkTk.setCtdktk(ctdktk);
            return detailCtdkTk;
        }
        // lay bang ke chi tiet hang hoa
        if (loaiChungTu.equals(MessageType.CTDT_BKHH.getValue())) {
            Optional<CtdtBkhhEntity> ctdtBkhh = ctdtBkhhRepositoryImpl
                    .searchOptionalByChungTuDinhKemTkId(chungTuDinhKemTk.get().getId());

            CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                    .searchByChungTuDinhKemTkIdAndFileId(ctdtBkhh.get().getId(), chungTuDinhKemTk.get().getId());
            CtdtDetailResponseDto.ctdt<CtdtBkhhEntity> ctdt = new CtdtDetailResponseDto.ctdt<CtdtBkhhEntity>();
            ctdt.setChungTu(ctdtBkhh.get());
            ctdt.setCtdtFileDinhKem(ctdtFileDinhKem);
            CtdtDetailResponseDto.ctdkTk<CtdtBkhhEntity> ctdktk = new CtdtDetailResponseDto.ctdkTk<CtdtBkhhEntity>();
            ctdktk.setCtdt(ctdt);
            ctdktk.setChungTuDinhKemTk(chungTuDinhKemTk);
            CtdtDetailResponseDto detailCtdkTk = new CtdtDetailResponseDto();
            detailCtdkTk.setCtdktk(ctdktk);
            return detailCtdkTk;
        }
        // lay giay chung nhan kiem tra chuyen nganh
        if (loaiChungTu.equals(MessageType.CTDT_GIAY_CNKTCN.getValue())) {
            Optional<CtdtGiayCnktcnEntity> ctdtGiayCnktcn = ctdtGiayCnktcnRepositoryImpl
                    .searchOptionalByChungTuDinhKemTkId(chungTuDinhKemTk.get().getId());
            CtdtGiayCnktcnEntity ctdtGiayCnktcnEntity = ctdtGiayCnktcn.orElse(null);
            if (ctdtGiayCnktcnEntity == null) return null;
            String[] hangHoaIds = ctdtGiayCnktcnEntity.getHangHoaId() == null ? new String[0] : ctdtGiayCnktcnEntity.getHangHoaId().split(",");
            List<CtdtGiayCnktcnRequests.CtdtThongTinHangHoaRequest> hangLst = new ArrayList<>();
            for (String sttHangItem : hangHoaIds) {
                Optional<DmHangHoaTkEntity> hangHoa = dmHangHoaTkRepositoryImpl.findById(Long.parseLong(sttHangItem));
                hangHoa.ifPresent(dmHangHoaTkEntity -> {
                    CtdtGiayCnktcnRequests.CtdtThongTinHangHoaRequest item = new CtdtGiayCnktcnRequests.CtdtThongTinHangHoaRequest();
                    item.setId(dmHangHoaTkEntity.getId());
                    item.setMaSoHangHoa(dmHangHoaTkEntity.getMaHs());
                    item.setSoThuTuHang(dmHangHoaTkEntity.getSttHang());
                    item.setTenHangHoa(dmHangHoaTkEntity.getTenHang());
                    item.setSoLuong(dmHangHoaTkEntity.getLuong());
                    item.setDonViTinh(dmHangHoaTkEntity.getMaDvt());
                    item.setTriGia(dmHangHoaTkEntity.getTriGiaKb());
                    item.setNguyenTe(dmHangHoaTkEntity.getDgiaKbMaNt());
                    hangLst.add(item);
                });
            }
            ctdtGiayCnktcnEntity.setDsHang(hangLst);
            CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                    .searchByChungTuDinhKemTkIdAndFileId(ctdtGiayCnktcn.get().getId(), chungTuDinhKemTk.get().getId());
            CtdtDetailResponseDto.ctdt<CtdtGiayCnktcnEntity> ctdt = new CtdtDetailResponseDto.ctdt<CtdtGiayCnktcnEntity>();
            ctdt.setChungTu(ctdtGiayCnktcn.get());
            ctdt.setCtdtFileDinhKem(ctdtFileDinhKem);
            CtdtDetailResponseDto.ctdkTk<CtdtGiayCnktcnEntity> ctdktk = new CtdtDetailResponseDto.ctdkTk<CtdtGiayCnktcnEntity>();
            ctdktk.setCtdt(ctdt);
            ctdktk.setChungTuDinhKemTk(chungTuDinhKemTk);
            CtdtDetailResponseDto detailCtdkTk = new CtdtDetailResponseDto();
            detailCtdkTk.setCtdktk(ctdktk);
            return detailCtdkTk;
        }
        // lay chung minh to chuc
        if (loaiChungTu.equals(MessageType.CTDT_CMTC.getValue())) {
            Optional<CtdtCmtcEntity> ctdtCmtc = ctdtCmtcRepositoryImpl
                    .searchOptionalByChungTuDinhKemTkId(chungTuDinhKemTk.get().getId());
            CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                    .searchByChungTuDinhKemTkIdAndFileId(ctdtCmtc.get().getId(), chungTuDinhKemTk.get().getId());
            CtdtDetailResponseDto.ctdt<CtdtCmtcEntity> ctdt = new CtdtDetailResponseDto.ctdt<CtdtCmtcEntity>();
            ctdt.setChungTu(ctdtCmtc.get());
            ctdt.setCtdtFileDinhKem(ctdtFileDinhKem);
            CtdtDetailResponseDto.ctdkTk<CtdtCmtcEntity> ctdktk = new CtdtDetailResponseDto.ctdkTk<CtdtCmtcEntity>();
            ctdktk.setCtdt(ctdt);
            ctdktk.setChungTuDinhKemTk(chungTuDinhKemTk);
            CtdtDetailResponseDto detailCtdkTk = new CtdtDetailResponseDto();
            detailCtdkTk.setCtdktk(ctdktk);
            return detailCtdkTk;
        }
        // lay hop dong uy thac
        if (loaiChungTu.equals(MessageType.CTDT_HOP_DONG_UT.getValue())) {
            Optional<CtdtHopDongUtEntity> ctdtHopDongUt = ctdtHopDongUtRepositoryImpl
                    .searchOptionalByChungTuDinhKemTkId(chungTuDinhKemTk.get().getId());
            CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                    .searchByChungTuDinhKemTkIdAndFileId(ctdtHopDongUt.get().getId(), chungTuDinhKemTk.get().getId());
            CtdtDetailResponseDto.ctdt<CtdtHopDongUtEntity> ctdt = new CtdtDetailResponseDto.ctdt<CtdtHopDongUtEntity>();
            ctdt.setChungTu(ctdtHopDongUt.get());
            ctdt.setCtdtFileDinhKem(ctdtFileDinhKem);
            CtdtDetailResponseDto.ctdkTk<CtdtHopDongUtEntity> ctdktk = new CtdtDetailResponseDto.ctdkTk<CtdtHopDongUtEntity>();
            ctdktk.setCtdt(ctdt);
            ctdktk.setChungTuDinhKemTk(chungTuDinhKemTk);
            CtdtDetailResponseDto detailCtdkTk = new CtdtDetailResponseDto();
            detailCtdkTk.setCtdktk(ctdktk);
            return detailCtdkTk;
        }
        // lay to khai tri gia
        if (loaiChungTu.equals(MessageType.CTDT_TKTG.getValue())) {
            Optional<CtdtTktgEntity> ctdtTktg = ctdtTktgRepositoryImpl
                    .searchOptionalByChungTuDinhKemTkId(chungTuDinhKemTk.get().getId());

            CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                    .searchByChungTuDinhKemTkIdAndFileId(ctdtTktg.get().getId(), chungTuDinhKemTk.get().getId());
            CtdtDetailResponseDto.ctdt<CtdtTktgEntity> ctdt = new CtdtDetailResponseDto.ctdt<CtdtTktgEntity>();
            ctdt.setChungTu(ctdtTktg.get());
            ctdt.setCtdtFileDinhKem(ctdtFileDinhKem);
            CtdtDetailResponseDto.ctdkTk<CtdtTktgEntity> ctdktk = new CtdtDetailResponseDto.ctdkTk<CtdtTktgEntity>();
            ctdktk.setCtdt(ctdt);
            ctdktk.setChungTuDinhKemTk(chungTuDinhKemTk);
            CtdtDetailResponseDto detailCtdkTk = new CtdtDetailResponseDto();
            detailCtdkTk.setCtdktk(ctdktk);
            return detailCtdkTk;
        }
        // lay danh muc may moc thiet bị
        if (loaiChungTu.equals(MessageType.CTDT_MMTB.getValue())) {
            Optional<CtdtMmtbEntity> ctdtMmtb = ctdtMmtbRepositoryImpl
                    .searchOptionalByChungTuDinhKemTkId(chungTuDinhKemTk.get().getId());

            CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl
                    .searchByChungTuDinhKemTkIdAndFileId(ctdtMmtb.get().getId(), chungTuDinhKemTk.get().getId());
            CtdtDetailResponseDto.ctdt<CtdtMmtbEntity> ctdt = new CtdtDetailResponseDto.ctdt<CtdtMmtbEntity>();
            ctdt.setChungTu(ctdtMmtb.get());
            ctdt.setCtdtFileDinhKem(ctdtFileDinhKem);
            CtdtDetailResponseDto.ctdkTk<CtdtMmtbEntity> ctdktk = new CtdtDetailResponseDto.ctdkTk<CtdtMmtbEntity>();
            ctdktk.setCtdt(ctdt);
            ctdktk.setChungTuDinhKemTk(chungTuDinhKemTk);
            CtdtDetailResponseDto detailCtdkTk = new CtdtDetailResponseDto();
            detailCtdkTk.setCtdktk(ctdktk);
            return detailCtdkTk;
        }
        // lay ctat 5%
        if (loaiChungTu.equals(MessageType.CTDT_CTAT_5_PT.getValue())) {
            Optional<CtdtCtat5PhanTramEntity> ctdtCtat5PhanTram = ctdtCtat5PhanTramRepositoryImpl
                    .searchOptionalByChungTuDinhKemTkId(chungTuDinhKemTk.get().getId());
            CtdtFileDinhKemEntity ctdtFileDinhKem = ctdtFileDinhKemRepositoryImpl.searchByChungTuDinhKemTkIdAndFileId(
                    ctdtCtat5PhanTram.get().getId(), chungTuDinhKemTk.get().getId());
            CtdtDetailResponseDto.ctdt<CtdtCtat5PhanTramEntity> ctdt = new CtdtDetailResponseDto.ctdt<CtdtCtat5PhanTramEntity>();
            ctdt.setChungTu(ctdtCtat5PhanTram.get());
            ctdt.setCtdtFileDinhKem(ctdtFileDinhKem);
            CtdtDetailResponseDto.ctdkTk<CtdtCtat5PhanTramEntity> ctdktk = new CtdtDetailResponseDto.ctdkTk<CtdtCtat5PhanTramEntity>();
            ctdktk.setCtdt(ctdt);
            ctdktk.setChungTuDinhKemTk(chungTuDinhKemTk);
            CtdtDetailResponseDto detailCtdkTk = new CtdtDetailResponseDto();
            detailCtdkTk.setCtdktk(ctdktk);
            return detailCtdkTk;
        }
        return detailChungTuDinhKemResponse;
    }
}
