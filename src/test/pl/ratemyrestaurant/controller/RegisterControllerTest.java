package pl.ratemyrestaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.ratemyrestaurant.dto.NewUserDTO;
import pl.ratemyrestaurant.model.User;
import pl.ratemyrestaurant.repository.UserRepository;
import pl.ratemyrestaurant.service.UserService;
import pl.ratemyrestaurant.service.impl.UserServiceImpl;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class RegisterControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService = new UserServiceImpl(userRepository);
    @InjectMocks
    private RegisterController registerController = new RegisterController(userService);

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(registerController).build();
    }
    //skipping tests of following fields in info response:
    // key: (random generated),
    // decs: (basic error code description)
    // object: (could be anything including null)

    @Test
    public void shouldReturnBadRequestStatusAndInfoWithCode_201_whenNewUserHasInvalidUsername() throws Exception {
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setUsername(null);
        newUserDTO.setPassword("password");
        mockMvc.perform(post("/register")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(newUserDTO)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.httpStatusCode", is(400)));
    }

    @Test
    public void shouldReturnBadRequestStatusAndInfoWithCode_202_whenNewUserHasInvalidPassword() throws Exception {
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setUsername("newUserDTO");
        newUserDTO.setPassword(null);
        mockMvc.perform(post("/register")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(newUserDTO)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.httpStatusCode", is(400)));
    }
    @Test
    public void shouldReturnBadRequestStatusAndInfoWithCode_209_whenNewUserUsernameAlreadyUsed() throws Exception {
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setUsername("usedUsername");
        newUserDTO.setPassword("password");
        when(userRepository.findByUsernameIgnoreCase(newUserDTO.getUsername())).thenReturn(mockUser(newUserDTO));
        mockMvc.perform(post("/register")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(newUserDTO)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.httpStatusCode", is(400)));
    }
    @Test
    public void shouldReturnCreatedStatusAndInfoWithCode_0_whenNewUserSuccessfullyRegistered() throws Exception {
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setUsername("newUserDTO");
        newUserDTO.setPassword("password");
        mockMvc.perform(post("/register")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(newUserDTO)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.httpStatusCode", is(200)));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static User mockUser(NewUserDTO newUserDTO){
        User user = new User();
        user.setUsername(newUserDTO.getUsername());
        user.setPassword(newUserDTO.getPassword());
        return user;
    }
}