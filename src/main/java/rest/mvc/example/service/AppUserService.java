package rest.mvc.example.service;

import rest.mvc.example.model.AppUserDTO;

import java.util.List;

public interface AppUserService {

    List<AppUserDTO> getAllAppUsers();

    AppUserDTO getAppUserById(Long id);

    AppUserDTO createNewAppUser(AppUserDTO appUserDTO);

    AppUserDTO saveAppUserByDTO(Long id, AppUserDTO appUserDTO);

    AppUserDTO patchAppUser(Long id, AppUserDTO appUserDTO);

    void deleteAppUserById(Long id);

}
