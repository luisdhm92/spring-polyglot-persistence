package rest.mvc.example.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rest.mvc.example.controller.AppUserController;
import rest.mvc.example.domain.AppUser;
import rest.mvc.example.exception.ResourceNotFoundException;
import rest.mvc.example.mapper.AppUserMapper;
import rest.mvc.example.model.AppUserDTO;
import rest.mvc.example.repository.AppUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserMapper appUserMapper;
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AppUserServiceImpl(AppUserMapper customerMapper, AppUserRepository customerRepository,
                              BCryptPasswordEncoder passwordEncoder) {
        this.appUserMapper = customerMapper;
        this.appUserRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<AppUserDTO> getAllAppUsers() {
        return appUserRepository.findAll().stream().map(user -> {
            AppUserDTO appUserDTO = appUserMapper.appUserToAppUserDTO(user);
            appUserDTO.setUrl(AppUserController.BASE_URL + user.getId());
            return appUserDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public AppUserDTO getAppUserById(Long id) {
        AppUserDTO appUserDTO =
                appUserMapper.appUserToAppUserDTO(appUserRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
        appUserDTO.setUrl(AppUserController.BASE_URL + id);
        return appUserDTO;
    }

    @Override
    public AppUserDTO createNewAppUser(AppUserDTO appUserDTO) {
        appUserDTO.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
        return saveAndReturnDTO(appUserMapper.appUserDTOToAppUser(appUserDTO));
    }

    private AppUserDTO saveAndReturnDTO(AppUser appUser) {
        AppUser savedUser = appUserRepository.save(appUser);
        AppUserDTO returnDto = appUserMapper.appUserToAppUserDTO(savedUser);
        returnDto.setUrl(AppUserController.BASE_URL + savedUser.getId());
        return returnDto;
    }

    @Override
    public AppUserDTO saveAppUserByDTO(Long id, AppUserDTO appUserDTO) {
        AppUser appUser = appUserMapper.appUserDTOToAppUser(appUserDTO);
        appUser.setId(id);
        return saveAndReturnDTO(appUser);
    }

    @Override
    public AppUserDTO patchAppUser(Long id, AppUserDTO appUserDTO) {
        return appUserRepository.findById(id).map(appUser -> {
            if (appUserDTO.getFirstName() != null) {
                appUser.setFirstName(appUserDTO.getFirstName());
            }
            if (appUserDTO.getLastName() != null) {
                appUser.setLastName(appUserDTO.getLastName());
            }
            AppUserDTO returnDto = appUserMapper.appUserToAppUserDTO(appUserRepository.save(appUser));
            returnDto.setUrl(AppUserController.BASE_URL + id);
            return returnDto;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteAppUserById(Long id) {
        appUserRepository.deleteById(id);
    }
}
