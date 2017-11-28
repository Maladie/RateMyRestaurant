package pl.ratemyrestaurant.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class RestaurantControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    private RestaurantController restaurantController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
    }

    @Test
    public void test() {

    }
}