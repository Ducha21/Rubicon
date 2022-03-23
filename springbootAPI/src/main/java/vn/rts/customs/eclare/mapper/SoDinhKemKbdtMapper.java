package vn.rts.customs.eclare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import vn.rts.customs.eclare.dto.body.ElectronicAttachmentDto;
import vn.rts.customs.eclare.entity.SoDinhKemKbdtEntity;

@Mapper
public interface SoDinhKemKbdtMapper {
	SoDinhKemKbdtMapper INSTANCE = Mappers.getMapper(SoDinhKemKbdtMapper.class);
	
	@Mappings({
		@Mapping(source="plDinhKemDt",target="classificationElectronicAttachment"),
		@Mapping(source="soDinhKemDt",target="electronicAttachmentNo")
	})
	ElectronicAttachmentDto soDinhKemKbdtToElectronicAttachmentDto(SoDinhKemKbdtEntity soDinhKemKbdtEntity);
}
