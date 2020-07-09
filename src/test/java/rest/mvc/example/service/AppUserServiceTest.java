package rest.mvc.example.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rest.mvc.example.controller.AppUserController;
import rest.mvc.example.domain.AppUser;
import rest.mvc.example.mapper.AppUserMapper;
import rest.mvc.example.model.AppUserDTO;
import rest.mvc.example.repository.AppUserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AppUserServiceTest {

    private static final String BASE_URL_1 = AppUserController.BASE_URL.concat("1");

    private AppUserMapper appUserMapper = AppUserMapper.INSTANCE;

    private AppUserService appUserService;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        appUserService = new AppUserServiceImpl(appUserMapper, appUserRepository, passwordEncoder);
    }

    @Test
    public void getAllAppUsers() throws Exception {
        //given
        AppUser appUser1 = new AppUser();
        appUser1.setId(1l);
        appUser1.setFirstName("Michale");
        appUser1.setLastName("Weston");

        AppUser appUser2 = new AppUser();
        appUser2.setId(2l);
        appUser2.setFirstName("Sam");
        appUser2.setLastName("Axe");

        when(appUserRepository.findAll()).thenReturn(Arrays.asList(appUser1, appUser2));

        //when
        List<AppUserDTO> appUsersDTOS = appUserService.getAllAppUsers();

        //then
        assertEquals(2, appUsersDTOS.size());

    }

    @Test
    public void getAppUsersById() throws Exception {
        //given
        AppUser appUser1 = new AppUser();
        appUser1.setId(1l);
        appUser1.setFirstName("John");
        appUser1.setLastName("Doe");

        when(appUserRepository.findById(anyLong())).thenReturn(Optional.ofNullable(appUser1));

        //when
        AppUserDTO appUserDTO = appUserService.getAppUserById(1L);

        assertEquals("John", appUserDTO.getFirstName());
    }

    @Test
    public void createNewAppUser() throws Exception {
        //given
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setFirstName("John");

        AppUser savedUser = new AppUser();
        savedUser.setFirstName(appUserDTO.getFirstName());
        savedUser.setLastName(appUserDTO.getLastName());
        savedUser.setId(1l);

        when(appUserRepository.save(any(AppUser.class))).thenReturn(savedUser);

        //when
        AppUserDTO savedDto = appUserService.createNewAppUser(appUserDTO);

        //then
        assertEquals(appUserDTO.getFirstName(), savedDto.getFirstName());
        assertEquals(BASE_URL_1, savedDto.getUrl());
    }

    @Test
    public void saveAppUserByDTO() throws Exception {
        //given
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setFirstName("John");

        AppUser appUser = new AppUser();
        appUser.setFirstName(appUserDTO.getFirstName());
        appUser.setLastName(appUserDTO.getLastName());
        appUser.setId(1l);

        when(appUserRepository.save(any(AppUser.class))).thenReturn(appUser);

        //when
        AppUserDTO savedDto = appUserService.saveAppUserByDTO(1L, appUserDTO);

        //then
        assertEquals(appUserDTO.getFirstName(), savedDto.getFirstName());
        assertEquals(BASE_URL_1, savedDto.getUrl());
    }

    @Test
    public void deleteAppUserById() throws Exception {
        Long id = 1L;
        appUserService.deleteAppUserById(id);
        verify(appUserRepository, times(1)).deleteById(anyLong());
    }

}
