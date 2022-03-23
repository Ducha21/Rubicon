package vn.rts.customs.eclare.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import vn.rts.customs.eclare.dto.body.CargoNosDto;
import vn.rts.customs.eclare.entity.TkVanDonEntity;
import vn.rts.customs.eclare.util.VnaccsConvert;
import vn.rts.customs.eclare.util.VnaccsConvert.EFormatDateTime;

@Mapper
public interface TkVanDonNkMapper {
	TkVanDonNkMapper INSTANCE = Mappers.getMapper(TkVanDonNkMapper.class);

	@Mappings({
		@Mapping(source="vanDonSo",target="billNo"),
		@Mapping(source="ngayVanDon",target="billDate", qualifiedByName = "formatToDate"),
		@Mapping(source="soLuongKien",target="cargoPiece"),
		@Mapping(source="soLuongKienDvt",target="pieceUnitCode"),
		@Mapping(source="soDinhDanh",target="cargoCtrlNo"),
		@Mapping(source="trongLuong",target="cargoWeightGross"),
		@Mapping(source="trongLuongDvt",target="weightUnitCode")
	})
	CargoNosDto TkVanDonNkToCargoNosDto(TkVanDonEntity tkVanDonNkEntity);
	
	@Named("formatToDate")
	public static String formatToDate(String source) {
		return VnaccsConvert.stringDate2String(source, EFormatDateTime.YYYY_MM_DD.getValueEnum());
	}

}
