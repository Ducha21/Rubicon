package vn.rts.customs.eclare.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.rts.customs.eclare.entity.*;
import vn.rts.customs.eclare.repository.impl.*;
import vn.rts.customs.eclare.request.search.*;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare.ExceptionMessage;
import vn.rts.customs.eclare.util.VnaccsConvert;
import vn.rts.customs.lib.dto.PageableResponse;
import vn.rts.customs.lib.dto.PageableResponseEx;
import vn.rts.customs.lib.exception.ConflictException;
import vn.rts.customs.lib.exception.ResourceNotFoundException;
import vn.rts.customs.lib.service.WebApiCommonServiceImpl;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DeclarationDetailService extends WebApiCommonServiceImpl<MauToKhaiHqRepositoryImpl, KeySearchListObj> {

    @Autowired
    private MauToKhaiHqRepositoryImpl mauToKhaiHqRepositoryImpl;
    @Autowired
    private ChiThiHqTkRepositoryImpl chiThiHqTkRepositoryImpl;
    @Autowired
    private DmHangHoaTkRepositoryImpl daDmHangHoaTkRepositoryImpl;
    @Autowired
    private TkVanDonRepositoryImpl tkVanDonRepositoryImpl;
    @Autowired
    private GiayPhepTkRepositoryImpl giayPhepTkRepositoryImpl;
    @Autowired
    private KhoanDieuChinhTkRepositoryImpl khoanDieuChinhTkRepositoryImpl;
    @Autowired
    private SoDinhKemKbdtRepositoryImpl soDinhKemKbdtRepositoryImpl;
    @Autowired
    private TtTrungChuyenRepositoryImpl ttTrungChuyenRepositoryImpl;
    @Autowired
    private ThueKhoanThuKhacRepositoryImpl thueKhoanThuKhacRepositoryImpl;
    @Autowired
    private MauToKhaiHqTemplateRepositoryImpl mauToKhaiHqTemplateRepositoryImpl;
    @Autowired
    private ThongTinXuLyTkRepositoryImpl thongTinXuLyTkRepositoryImpl;
    @Autowired
    private DiaDiemXepHangTkRepositoryImpl diaDiemXepHangTkRepositoryImpl;
    @Autowired
    private CoHangHoaTkRepositoryImpl coHangHoaTkRepositoryImpl;

    @Autowired
    DmHangHoaRepositoryImpl dmHangHoaRepositoryImpl;

    public PageableResponse<MauToKhaiHqEntity> searchTK(KeySearchMauToKhaiHq keySearchListObj) {
        Page<MauToKhaiHqEntity> a = mauToKhaiHqRepositoryImpl.search(keySearchListObj);
        return new PageableResponseEx<MauToKhaiHqEntity>(a);
    }

    public MauToKhaiHqEntity selectItemTK(String id) throws ResourceNotFoundException {
        Optional<MauToKhaiHqEntity> thongTinChung = mauToKhaiHqRepositoryImpl.findById(id);
        if (!thongTinChung.isPresent()) {
            throw new ResourceNotFoundException("Không tìm thấy thông tin tờ khai.");
        }
        MauToKhaiHqEntity response = thongTinChung.get();

        List<DmHangHoaTkEntity> dsHang = daDmHangHoaTkRepositoryImpl.findByToKhaiId(id);
        for (DmHangHoaTkEntity dmHangHoaTkEntity : dsHang) {
            List<ThueKhoanThuKhacEntity> dsThueKhoanThuKhac = thueKhoanThuKhacRepositoryImpl
                    .findByHangHoaTkId(dmHangHoaTkEntity.getId());
            dmHangHoaTkEntity.setDsThueKhoanThuKhac(dsThueKhoanThuKhac);

            List<CoHangHoaTkEntity> colst = coHangHoaTkRepositoryImpl.findByDmHangHoaTkId(dmHangHoaTkEntity.getId());
            dmHangHoaTkEntity.setDsCo(colst);
        }
        response.setDsHang(dsHang);

        List<TkVanDonEntity> dsVanDon = tkVanDonRepositoryImpl.findByToKhaiId(id);
        response.setDsVanDon(dsVanDon);

        List<GiayPhepTkEntity> dsGiayPhep = giayPhepTkRepositoryImpl.findByToKhaiId(id);
        response.setDsGiayPhep(dsGiayPhep);

        List<KhoanDieuChinhTkEntity> dsKhoanDieuChinh = khoanDieuChinhTkRepositoryImpl.findByToKhaiId(id);
        response.setDsKhoanDieuChinh(dsKhoanDieuChinh);

        List<SoDinhKemKbdtEntity> dsDinhKemKbdt = soDinhKemKbdtRepositoryImpl.findByToKhaiId(id);
        response.setDsDinhKemKbdt(dsDinhKemKbdt);

        List<TtTrungChuyenEntity> dsThongTinTc = ttTrungChuyenRepositoryImpl.findByToKhaiId(id);
        response.setDsThongTinTc(dsThongTinTc);

        List<DiaDiemXepHangTkEntity> dsDiaDiemXepHang = diaDiemXepHangTkRepositoryImpl.findByToKhaiId(id);
        response.setDsDiaDiemXepHang(dsDiaDiemXepHang);
        return response;
    }


    public PageableResponse<ChiThiHqTkEntity> searchChiThiHqTk(String toKhaiId, KeySearchChiThiHqTk keySearchListObj) {
        return new PageableResponseEx<ChiThiHqTkEntity>(chiThiHqTkRepositoryImpl.search(toKhaiId, keySearchListObj));
    }

    @Transactional
    public MauToKhaiHqTemplateEntity createTemplate(String templateContent, String doanhNghiepId, String name, String loaiToKhai) {
        MauToKhaiHqTemplateEntity template = new MauToKhaiHqTemplateEntity();
        template.setName(name);
        template.setLoaiTkHq(loaiToKhai);
        template.setDoanhNghiepId(doanhNghiepId);
        template.setContent(templateContent);
        template.setNgayTao(VnaccsConvert.getCurrentDateTime());
        template.setMaDoanhNghiep(doanhNghiepId);
        template = mauToKhaiHqTemplateRepositoryImpl.insert(template);
        return template;
    }

    public List<MauToKhaiHqTemplateEntity> searchTemplate(String doanhNghiepId, String loaiToKhai, String templateName) {
        List<MauToKhaiHqTemplateEntity> listTemplate = mauToKhaiHqTemplateRepositoryImpl.search(doanhNghiepId, loaiToKhai, templateName);
        return listTemplate;
    }

    @Transactional
    public MauToKhaiHqTemplateEntity updateTemplate(Long templateId, String templateName, String templateContent) throws ResourceNotFoundException {
        Optional<MauToKhaiHqTemplateEntity> templateOptional = mauToKhaiHqTemplateRepositoryImpl.findById(templateId);
        if (!templateOptional.isPresent()) {
            throw new ResourceNotFoundException(ExceptionMessage.NOT_FOUND);
        }
        MauToKhaiHqTemplateEntity template = templateOptional.get();
        template.setContent(templateContent);
        mauToKhaiHqTemplateRepositoryImpl.update(template);
        return template;
    }

    @Transactional
    public void deleteTemplate(Long templateId) throws ResourceNotFoundException {
        Optional<MauToKhaiHqTemplateEntity> templateOptional = mauToKhaiHqTemplateRepositoryImpl.findById(templateId);
        if (!templateOptional.isPresent()) {
            throw new ResourceNotFoundException(ExceptionMessage.NOT_FOUND);
        }
        mauToKhaiHqTemplateRepositoryImpl.delete(templateOptional.get());
    }

    @Transactional
    public void deleteTk(String tkId) throws ResourceNotFoundException {
        Optional<MauToKhaiHqEntity> tknOptional = mauToKhaiHqRepositoryImpl.findById(tkId);

        if (!tknOptional.isPresent()) {
            throw new ResourceNotFoundException(ExceptionMessage.NOT_FOUND);
        }

        if (tknOptional.get().getSoTiepNhan() != null) {
            throw new ConflictException(ExceptionMessage.ERROR_STN);
        }

        List<TkVanDonEntity> tkVanDonObjs = tkVanDonRepositoryImpl.findByToKhaiId(tkId);
        List<GiayPhepTkEntity> giayPhepTkObjs = giayPhepTkRepositoryImpl.findByToKhaiId(tkId);
        List<SoDinhKemKbdtEntity> soDinhKemKbdtObjs = soDinhKemKbdtRepositoryImpl.findByToKhaiId(tkId);
        List<TtTrungChuyenEntity> ttTrungChuyenObjs = ttTrungChuyenRepositoryImpl.findByToKhaiId(tkId);
        List<DmHangHoaTkEntity> dmHangHoaObjs = daDmHangHoaTkRepositoryImpl.findByToKhaiId(tkId);


        // delete tờ khai and relations
        mauToKhaiHqRepositoryImpl.delete(tknOptional.get());
        tkVanDonRepositoryImpl.deleteByToKhaiId(tkVanDonObjs);
        giayPhepTkRepositoryImpl.deleteByToKhaiId(giayPhepTkObjs);
        daDmHangHoaTkRepositoryImpl.deleteByToKhaiId(dmHangHoaObjs);
        soDinhKemKbdtRepositoryImpl.deleteByToKhaiId(soDinhKemKbdtObjs);
        ttTrungChuyenRepositoryImpl.deleteByToKhaiId(ttTrungChuyenObjs);

    }

    public List<ThongTinXuLyTkEntity> selectThongTinXyLyTK(String mauTkHqId) {
        return thongTinXuLyTkRepositoryImpl.findByMauTkHqId(mauTkHqId);
    }

    public List<DmHangHoaTkEntity> selectHangHoaTK(String toKhaiId) throws ResourceNotFoundException {
        return daDmHangHoaTkRepositoryImpl.findByToKhaiId(toKhaiId);
    }

    @Transactional
    public DmHangHoaEntity createDmHangHoa(DmHangHoaEntity dmHangHoaEntity) {
        String maDoanhNghiep = this.getCurrentUserInfo().org();
        dmHangHoaEntity.setNgayTao(VnaccsConvert.getCurrentDateTime());
        dmHangHoaEntity.setNguoiTao(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
        dmHangHoaEntity.setMaDoanhNghiep(maDoanhNghiep);
        dmHangHoaEntity = dmHangHoaRepositoryImpl.insert(dmHangHoaEntity);
        return dmHangHoaEntity;
    }

    @Transactional
    public DmHangHoaEntity updateDmHangHoa(Long id, DmHangHoaEntity updateItem) throws ResourceNotFoundException {
        Optional<DmHangHoaEntity> dmHangHoaEntityOptional = dmHangHoaRepositoryImpl.findById(id);
        if (!dmHangHoaEntityOptional.isPresent()) {
            throw new ResourceNotFoundException(ExceptionMessage.NOT_FOUND);
        }
        DmHangHoaEntity dmHangHoaEntity = dmHangHoaEntityOptional.get();
        BeanUtils.copyProperties(updateItem, dmHangHoaEntity, "id");
        dmHangHoaEntity.setNgaySua(VnaccsConvert.getCurrentDateTime());
        dmHangHoaEntity.setNguoiSua(this.getCurrentUserInfo() == null ? "" : this.getCurrentUserInfo().uid());
        dmHangHoaEntity = dmHangHoaRepositoryImpl.update(dmHangHoaEntity);
        return dmHangHoaEntity;
    }

    public List<KeyValueDto> searchDmHangHoa(KeySearchKeyValue objInput) {
        return dmHangHoaRepositoryImpl.search(objInput);
    }

    @Transactional
    public void deleteDmHangHoa(Long id) throws ResourceNotFoundException {
        Optional<DmHangHoaEntity> dmHangHoaEntityOptional = dmHangHoaRepositoryImpl.findById(id);
        if (!dmHangHoaEntityOptional.isPresent()) {
            throw new ResourceNotFoundException(ExceptionMessage.NOT_FOUND);
        }
        DmHangHoaEntity dmHangHoaEntity = dmHangHoaEntityOptional.get();
        dmHangHoaRepositoryImpl.delete(dmHangHoaEntity);
    }
}
