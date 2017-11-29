package pl.ratemyrestaurant.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.ratemyrestaurant.dto.NewUserDTO;
import pl.ratemyrestaurant.model.Info;
import pl.ratemyrestaurant.service.UserService;
import pl.ratemyrestaurant.type.APIInfoCodes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.ratemyrestaurant.utils.TestUtils.asJsonString;

@RunWith(SpringJUnit4ClassRunner.class)
public class RegisterControllerTest {

    private MockMvc mockMvc;

    private UserService userService;

    @Before
    public void setUp() {
        userService = mock(UserService.class);
        RegisterController registerController = new RegisterController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(registerController).build();
    }

    @Test
    public void shouldReturn400AndINVALID_USERNAMEInfoCode_whenNewUserInvalidUsernameGiven() throws Exception {
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setUsername(null);
        newUserDTO.setPassword("password");
        Info mockInfo = mockInfo("Invalid Username", APIInfoCodes.INVALID_USERNAME);
        doReturn(mockInfo).when(userService).register(any(NewUserDTO.class));
        String responseContent = mockMvc.perform(post("/register")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(newUserDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        verify(userService, times(1)).register(any(NewUserDTO.class));
        Assert.assertEquals(asJsonString(mockInfo), responseContent);
    }

    @Test
    public void shouldReturn400AndINVALID_PASSWORDInfoCode_whenNewUserHasInvalidPassword() throws Exception {
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setUsername("newUserDTO");
        newUserDTO.setPassword(null);
        Info mockInfo = mockInfo("Invalid Password", APIInfoCodes.INVALID_PASSWORD);
        doReturn(mockInfo).when(userService).register(any(NewUserDTO.class));
        String responseContent = mockMvc.perform(post("/register")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(newUserDTO)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        verify(userService, times(1)).register(any(NewUserDTO.class));
        Assert.assertEquals(asJsonString(mockInfo), responseContent);
    }

    @Test
    public void should400AndUSERNAME_ALREADY_USERD_whenNewUserUsernameAlreadyUsed() throws Exception {
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setUsername("usedUsername");
        newUserDTO.setPassword("password");
        Info mockInfo = mockInfo("Invalid Password", APIInfoCodes.USERNAME_ALREADY_USED);
        doReturn(mockInfo).when(userService).register(any(NewUserDTO.class));
        String responseContent = mockMvc.perform(post("/register")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(newUserDTO)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        verify(userService, times(1)).register(any(NewUserDTO.class));
        Assert.assertEquals(asJsonString(mockInfo), responseContent);
    }

    @Test
    public void shouldReturn201AndOKInfoCode_whenNewUserSuccessfullyRegistered() throws Exception {
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setUsername("newUserDTO");
        newUserDTO.setPassword("password");
        Info mockInfo = mockInfo("Invalid Password", APIInfoCodes.OK);
        doReturn(mockInfo).when(userService).register(any(NewUserDTO.class));
        String responseContent = mockMvc.perform(post("/register")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(newUserDTO)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        verify(userService, times(1)).register(any(NewUserDTO.class));
        Assert.assertEquals(asJsonString(mockInfo), responseContent);
    }

    private static Info mockInfo(String mockMessage, APIInfoCodes infoCode) {
        return new Info.InfoBuilder().setDescription(mockMessage).setInfoCode(infoCode).setHttpStatusCode(400).build();
    }
}