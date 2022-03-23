package vn.rts.customs.eclare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import vn.rts.customs.eclare.dto.body.PermitsDto;
import vn.rts.customs.eclare.entity.GiayPhepTkEntity;

@Mapper
public interface GiayPhepNhapKhauTkMapper {
	GiayPhepNhapKhauTkMapper INSTANCE = Mappers.getMapper(GiayPhepNhapKhauTkMapper.class);
	
	@Mappings({
		@Mapping(source="maPlGiayPhepNk",target="permitType"),
		@Mapping(source="soPkGiayPhepNk",target="permitLicenseNo")
	})
	PermitsDto giayPhepNhapKhauTkToPermitsDto(GiayPhepTkEntity GiayPhepNhapKhauTkEntity);
}
