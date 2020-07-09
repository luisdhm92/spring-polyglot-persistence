package rest.mvc.example.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import rest.mvc.example.exception.ResourceNotFoundException;
import rest.mvc.example.model.AppUserDTO;
import rest.mvc.example.service.AppUserService;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AppUserControllerTest extends AbstractRestControllerTest {

    private static final String BASE_URL_1 = AppUserController.BASE_URL.concat("/1");

    @InjectMocks
    private AppUserController appUserController;

    @Mock
    private AppUserService appUserService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc =
                MockMvcBuilders.standaloneSetup(appUserController).setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }

    @Test
    public void testListAppUsers() throws Exception {
        //given
        AppUserDTO appUser1 = new AppUserDTO();
        appUser1.setFirstName("John");
        appUser1.setLastName("Doe");
        appUser1.setUrl(BASE_URL_1);

        AppUserDTO appUser2 = new AppUserDTO();
        appUser2.setFirstName("Harry");
        appUser2.setLastName("Ford");
        appUser2.setUrl(AppUserController.BASE_URL.concat("2"));

        when(appUserService.getAllAppUsers()).thenReturn(Arrays.asList(appUser1, appUser2));

        //expect
        mockMvc.perform(get(AppUserController.BASE_URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.users", hasSize(2)));
    }

    @Test
    public void testGetAppUserById() throws Exception {
        //given
        AppUserDTO appUser1 = new AppUserDTO();
        appUser1.setFirstName("John");
        appUser1.setLastName("Wayne");
        appUser1.setUrl(BASE_URL_1);

        when(appUserService.getAppUserById(anyLong())).thenReturn(appUser1);

        //expect
        mockMvc.perform(get(BASE_URL_1).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.firstName", equalTo("John")));
    }

    @Test
    public void createAppUser() throws Exception {
        //given
        AppUserDTO userDTO = new AppUserDTO();
        userDTO.setFirstName("Harry");
        userDTO.setLastName("Ford");

        AppUserDTO returnDTO = new AppUserDTO();
        returnDTO.setFirstName(userDTO.getFirstName());
        returnDTO.setLastName(userDTO.getLastName());
        returnDTO.setUrl(BASE_URL_1);

        when(appUserService.createNewAppUser(userDTO)).thenReturn(returnDTO);

        //expect
        mockMvc.perform(post(AppUserController.BASE_URL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(userDTO))).andExpect(status().isCreated());
    }

    @Test
    public void testUpdateAppUser() throws Exception {
        //given
        AppUserDTO customer = new AppUserDTO();
        customer.setFirstName("Harry");
        customer.setLastName("Ford");

        AppUserDTO returnDTO = new AppUserDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName(customer.getLastName());
        returnDTO.setUrl(BASE_URL_1);

        when(appUserService.saveAppUserByDTO(anyLong(), any(AppUserDTO.class))).thenReturn(returnDTO);

        //expect
        mockMvc.perform(put(BASE_URL_1).contentType(MediaType.APPLICATION_JSON).content(asJsonString(customer))).andExpect(status().isOk()).andExpect(jsonPath("$.firstName", equalTo("Harry"))).andExpect(jsonPath("$.lastName", equalTo("Ford"))).andExpect(jsonPath("$.app_user_url", equalTo(BASE_URL_1)));
    }

    @Test
    public void testPatchAppUser() throws Exception {
        //given
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setFirstName("Harry");

        AppUserDTO returnDTO = new AppUserDTO();
        returnDTO.setFirstName(appUserDTO.getFirstName());
        returnDTO.setLastName("Ford");
        returnDTO.setUrl(BASE_URL_1);

        when(appUserService.patchAppUser(anyLong(), any(AppUserDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch(BASE_URL_1).contentType(MediaType.APPLICATION_JSON).content(asJsonString(appUserDTO))).andExpect(status().isOk()).andExpect(jsonPath("$.firstName", equalTo("Harry"))).andExpect(jsonPath("$.lastName", equalTo("Ford"))).andExpect(jsonPath("$.app_user_url", equalTo(BASE_URL_1)));
    }

    @Test
    public void testDeleteAppUser() throws Exception {
        mockMvc.perform(delete(BASE_URL_1).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        verify(appUserService).deleteAppUserById(anyLong());
        verify(appUserService, times(1)).deleteAppUserById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {
        when(appUserService.getAppUserById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(AppUserController.BASE_URL.concat("/222")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

}
