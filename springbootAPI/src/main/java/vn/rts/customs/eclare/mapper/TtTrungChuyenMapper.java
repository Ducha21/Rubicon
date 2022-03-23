package vn.rts.customs.eclare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import vn.rts.customs.eclare.dto.body.TransitInfosDto;
import vn.rts.customs.eclare.entity.TtTrungChuyenEntity;

@Mapper
public interface TtTrungChuyenMapper {
	TtTrungChuyenMapper INSTANCE = Mappers.getMapper(TtTrungChuyenMapper.class);
	@Mappings({
		@Mapping(source="diaDiemTrungChuyenBcbt",target="transitLocationBondedTransport"),
		@Mapping(source="ngayDen",target="transitArrivalDateOfTransport", dateFormat = "yyyy-MM-dd"),
		@Mapping(source="ngayKhoiHanh",target="transitStartDateOfTransport", dateFormat = "yyyy-MM-dd")
	})
	TransitInfosDto ttTrungChuyenToTransitInfosDto(TtTrungChuyenEntity ttTrungChuyenEntity);
}
