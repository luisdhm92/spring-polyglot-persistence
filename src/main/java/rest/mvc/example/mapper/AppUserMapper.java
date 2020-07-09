package rest.mvc.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import rest.mvc.example.domain.AppUser;
import rest.mvc.example.model.AppUserDTO;

@Mapper
public interface AppUserMapper {

    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);

    AppUserDTO appUserToAppUserDTO(AppUser user);

    AppUser appUserDTOToAppUser(AppUserDTO appUserDTO);

}
