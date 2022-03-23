
package vn.rts.customs.eclare.service;

import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.rts.customs.eclare.entity.CauHinhHeThongEntity;
import vn.rts.customs.eclare.entity.ChuKySoEntity;
import vn.rts.customs.eclare.entity.ThietLapThongSoEntity;
import vn.rts.customs.eclare.repository.impl.CauHinhHeThongRepositoryImpl;
import vn.rts.customs.eclare.repository.impl.ChuKySoRepositoryImpl;
import vn.rts.customs.eclare.repository.impl.ThietLapThongSoRepositoryImpl;
import vn.rts.customs.eclare.request.CompanyDataSourceRequest;
import vn.rts.customs.eclare.request.search.KeySearchCauHinhHeThong;
import vn.rts.customs.eclare.request.search.KeySearchListObj;
import vn.rts.customs.eclare.request.search.KeySearchSystemSettingDto;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare;
import vn.rts.customs.eclare.util.ConstantEtcCustomsEClare.EntityTableEtcCustomsEClare;
import vn.rts.customs.lib.dto.PageableResponse;
import vn.rts.customs.lib.dto.PageableResponseEx;
import vn.rts.customs.lib.exception.ConflictException;
import vn.rts.customs.lib.exception.ResourceNotFoundException;
import vn.rts.customs.lib.key.SequenceKey;
import vn.rts.customs.lib.service.WebApiBaseServiceImplWithSp;
import vn.rts.customs.lib.util.JacksonEx;

import java.util.List;
import java.util.Optional;

/**
 * @author CCS4EO_DEV
 */

@Service
@EqualsAndHashCode(callSuper = false)
public class ThietLapThongSoService extends
        WebApiBaseServiceImplWithSp<ThietLapThongSoEntity, Long, SequenceKey, ThietLapThongSoRepositoryImpl, KeySearchListObj> {

    @Value("${spring.kafka.producer.topic-tcp-priority-org}")
    private String topicTcpPriorityOrg;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ThietLapThongSoRepositoryImpl thietLapThongSoRepositoryImpl;

    @Autowired
    private CauHinhHeThongRepositoryImpl cauHinhHeThongRepositoryImpl;

    @Autowired
    private ChuKySoRepositoryImpl chuKySoRepositoryImpl;


    public ThietLapThongSoService() {
        this.setTableName(EntityTableEtcCustomsEClare.getEntityTableFullName(EntityTableEtcCustomsEClare.THIET_LAP_THONG_SO));
    }

    public PageableResponseEx<ThietLapThongSoEntity> search(KeySearchSystemSettingDto objInput) {
        return new PageableResponseEx<>(this.getObjIRepository().search(objInput));
    }

    @Override
    public ThietLapThongSoEntity convertId2Entity(SequenceKey objInput) {
        ThietLapThongSoEntity objRet = new ThietLapThongSoEntity();
        objRet.setId(objInput.getId());
        return objRet;
    }

	@Transactional
	public CauHinhHeThongEntity createCauHinhHeThong(CauHinhHeThongEntity cauHinhHeThongEntity) {
		String maDoanhNghiep = this.getCurrentUserInfo().org();
		cauHinhHeThongEntity.setMaDoanhNghiep(maDoanhNghiep);
		cauHinhHeThongEntity = cauHinhHeThongRepositoryImpl.insert(cauHinhHeThongEntity);
		return cauHinhHeThongEntity;
	}

	@Transactional
	public CauHinhHeThongEntity updateCauHinhHeThong(Long id, CauHinhHeThongEntity updateItem) throws ResourceNotFoundException {
		Optional<CauHinhHeThongEntity> CauHinhHeThongEntityOptional = cauHinhHeThongRepositoryImpl.findById(id);
		if (!CauHinhHeThongEntityOptional.isPresent()) {
			throw new ResourceNotFoundException(ConstantEtcCustomsEClare.ExceptionMessage.NOT_FOUND);
		}
		CauHinhHeThongEntity cauHinhHeThongEntity = CauHinhHeThongEntityOptional.get();
		cauHinhHeThongEntity.setValue(updateItem.getValue());
		cauHinhHeThongEntity = cauHinhHeThongRepositoryImpl.update(cauHinhHeThongEntity);
		return cauHinhHeThongEntity;
	}

    public CauHinhHeThongEntity getCauHinhHeThongById(Long id) throws ResourceNotFoundException {
        Optional<CauHinhHeThongEntity> cauHinhHeThongEntityOptional = cauHinhHeThongRepositoryImpl.findById(id);
        if (!cauHinhHeThongEntityOptional.isPresent()) {
            throw new ResourceNotFoundException(ConstantEtcCustomsEClare.ExceptionMessage.NOT_FOUND);
        }
        CauHinhHeThongEntity cauHinhHeThongEntity = cauHinhHeThongEntityOptional.get();
        return cauHinhHeThongEntity;
    }

	@Transactional
	public void xoaCauHinhHeThong(Long id) throws ResourceNotFoundException {
		Optional<CauHinhHeThongEntity> cauHinhHeThongEntityOptional = cauHinhHeThongRepositoryImpl.findById(id);
		if (!cauHinhHeThongEntityOptional.isPresent()) {
			throw new ResourceNotFoundException(ConstantEtcCustomsEClare.ExceptionMessage.NOT_FOUND);
		}
		CauHinhHeThongEntity cauHinhHeThongEntity = cauHinhHeThongEntityOptional.get();
		if (cauHinhHeThongEntity.getIsDeletable() == 0) {
			throw new ConflictException("");
		}
		cauHinhHeThongRepositoryImpl.delete(cauHinhHeThongEntity);
	}

    public PageableResponse<CauHinhHeThongEntity> searchCauHinhHeThong(KeySearchCauHinhHeThong keySearchListObj) {
        Page<CauHinhHeThongEntity> a = cauHinhHeThongRepositoryImpl.search(keySearchListObj);
        return new PageableResponseEx<CauHinhHeThongEntity>(a);
    }

	@Transactional
	public ChuKySoEntity createChuKySo(ChuKySoEntity chuKySoEntity) {
		String maDoanhNghiep = this.getCurrentUserInfo().org();
		chuKySoEntity.setMaDoanhNghiep(maDoanhNghiep);
		chuKySoEntity = chuKySoRepositoryImpl.insert(chuKySoEntity);
		return chuKySoEntity;
	}

	@Transactional
	public ChuKySoEntity updateChuKySo(Long id, ChuKySoEntity updateItem) throws ResourceNotFoundException {
		Optional<ChuKySoEntity> chuKySoEntityEntityOptional = chuKySoRepositoryImpl.findById(id);
		if (!chuKySoEntityEntityOptional.isPresent()) {
			throw new ResourceNotFoundException(ConstantEtcCustomsEClare.ExceptionMessage.NOT_FOUND);
		}
		ChuKySoEntity chuKySoEntity = chuKySoEntityEntityOptional.get();
		//cauHinhHeThongEntity.setValue(updateItem.getValue());
		BeanUtils.copyProperties(updateItem, chuKySoEntity, "id","maDoanhNghiep","tenChungThuSo","soCMT");
		chuKySoEntity = chuKySoRepositoryImpl.update(chuKySoEntity);
		return chuKySoEntity;
	}

    public List<ChuKySoEntity> getChuKySo(String maDoanhNghiep) {
        List<ChuKySoEntity> listChuKySoEntity = chuKySoRepositoryImpl.search(maDoanhNghiep);
        return listChuKySoEntity;
    }

    public Optional<ThietLapThongSoEntity> findByDoanhNghiepId(String doanhNghiepId) {
        return thietLapThongSoRepositoryImpl.findByDoanhNghiepId(doanhNghiepId);
    }

    public void initDataRouting() {
        CompanyDataSourceRequest request = new CompanyDataSourceRequest();
        request.setIsInit(1);
        String jsonTopic = JacksonEx.getInstance().object2String(request);
        kafkaTemplate.send(topicTcpPriorityOrg, jsonTopic);
    }

	@Transactional
	public String upgradeCompany(String orgCode, String scompanyRequest) {
		CompanyDataSourceRequest request = new CompanyDataSourceRequest();
		String[] orgs = new String[]{orgCode};
		request.setOrgCodes(orgs);
		request.setIsInit(0);
		String jsonToTopic = JacksonEx.getInstance().object2String(request);
		this.kafkaTemplate.send(topicTcpPriorityOrg, jsonToTopic);
		return "Ok";
	}
}
    