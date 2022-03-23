package vn.rts.customs.eclare.mapper;

import org.mapstruct.Mapper;

import vn.rts.customs.eclare.dto.TkdnMsgDto;
import vn.rts.customs.eclare.entity.MauToKhaiHqEntity;
import vn.rts.customs.lib.mapper.IWebApiBaseMapper;

@Mapper
public interface IDeclarationMapper extends IWebApiBaseMapper<MauToKhaiHqEntity, TkdnMsgDto> {

}
