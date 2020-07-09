package rest.mvc.example.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import rest.mvc.example.domain.AppUser;
import rest.mvc.example.mapper.AppUserMapper;
import rest.mvc.example.model.AppUserDTO;
import rest.mvc.example.repository.AppUserRepository;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AppUserServiceITTest {

    private static final long USER_ID = 1;

    private AppUserService appUserService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        System.out.println("Loading Customer Data");
        System.out.println(appUserRepository.findAll().size());

        appUserService = new AppUserServiceImpl(AppUserMapper.INSTANCE, appUserRepository, passwordEncoder);
    }

    @Test
    public void patchAppUserUpdateFirstName() throws Exception {
        String updatedName = "UpdatedName";

        AppUser originalAppUser = appUserRepository.getOne(USER_ID);
        assertNotNull(originalAppUser);

        //save original first name
        String originalFirstName = originalAppUser.getFirstName();
        String originalLastName = originalAppUser.getLastName();

        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setFirstName(updatedName);

        appUserService.patchAppUser(USER_ID, appUserDTO);

        AppUser updatedAppUser = appUserRepository.findById(USER_ID).get();

        assertNotNull(updatedAppUser);
        assertEquals(updatedName, updatedAppUser.getFirstName());
//        assertThat(originalFirstName, not(equalTo(updatedAppUser.getFirstName())));
        assertThat(originalLastName, equalTo(updatedAppUser.getLastName()));
    }
}
