package rest.mvc.example.mapper;

import org.junit.Test;
import rest.mvc.example.domain.AppUser;
import rest.mvc.example.model.AppUserDTO;

public class AppUserMapperTest {

    public static final String FIRSTNAME = "Jimmy";
    public static final String LASTNAME = "Fallon";

    private AppUserMapper appUserMapper = AppUserMapper.INSTANCE;

    @Test
    public void appUserToAppUserDTO() throws Exception {
        //given
        AppUser appUser = new AppUser();
        appUser.setFirstName(FIRSTNAME);
        appUser.setLastName(LASTNAME);

        //when
        AppUserDTO appUserDTO = appUserMapper.appUserToAppUserDTO(appUser);

        //then
        assertEquals(FIRSTNAME, appUserDTO.getFirstName());
        assertEquals(LASTNAME, appUserDTO.getLastName());
    }

}
