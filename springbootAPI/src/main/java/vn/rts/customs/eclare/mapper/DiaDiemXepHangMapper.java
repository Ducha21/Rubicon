package vn.rts.customs.eclare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import vn.rts.customs.eclare.dto.body.VanningPlacesDto;
import vn.rts.customs.eclare.entity.DiaDiemXepHangTkEntity;

@Mapper
public interface DiaDiemXepHangMapper {
	DiaDiemXepHangMapper INSTANCE = Mappers.getMapper(DiaDiemXepHangMapper.class);
	@Mappings({
		@Mapping(source = "maDiaDiemXepHang", target = "vanningPlaceCode"),
		@Mapping(source = "tenDiaDiemXepHang", target = "vanningPlaceName"),
		@Mapping(source = "diaChiDiaDiemXepHang", target = "vanningPlaceAddress"),
	})
	VanningPlacesDto diaDiemXepHangToVanningPlacesDto(DiaDiemXepHangTkEntity diaDiemXepHangTkEntity);
}
