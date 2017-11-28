package pl.ratemyrestaurant.controller;

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
import pl.ratemyrestaurant.model.FoodType;
import pl.ratemyrestaurant.repository.FoodTypeRepository;
import pl.ratemyrestaurant.service.FoodTypeService;
import pl.ratemyrestaurant.service.impl.FoodTypeServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class FoodTypeControllerTest {
    private MockMvc mockMvc;
    private static final String foodEndpoint = "/foodTypes";

    @Mock
    private FoodTypeRepository foodTypeRepository;
    @InjectMocks
    private FoodTypeService foodTypeService = new FoodTypeServiceImpl(foodTypeRepository);
    @InjectMocks
    private FoodTypeController foodTypeController = new FoodTypeController(foodTypeService);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(foodTypeController).build();
    }

    @Test
    public void shouldReturnAllFoodTypesAnd200() throws Exception{
        //when
        when(foodTypeRepository.findAll()).thenReturn(mockFoodTypes());
        //then
        mockMvc.perform(get(foodEndpoint).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("food1")))
                .andExpect(jsonPath("$[1].name", is("food2")))
        .andDo(print());
    }

    @Test
    public void shouldReturnFoodTypeAnd200_whenValidFoodNameProvided() throws Exception {
        //given
        String foodName = "food2";
        FoodType food2 = new FoodType(foodName);
        //when
        when(foodTypeRepository.findByName(foodName)).thenReturn(food2);
        //then
        mockMvc.perform(get(foodEndpoint+"/{foodName}",foodName).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(foodName)))
        .andDo(print());
    }

    @Test
    public void shouldReturn404_whenProvidedFoodNameNotExistsInDB() throws Exception{
        //given
        String foodName = "food_name_missing";
        //when
        when(foodTypeRepository.findByName(foodName)).thenReturn(null);
//        foodTypeService = new FoodTypeServiceImpl(foodTypeRepository);
        //then
        mockMvc.perform(get(foodEndpoint+"/{foodName}",foodName).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andDo(print());
        verify(foodTypeRepository, times(1)).findByName(foodName);
    }

    @Test
    public void shouldReturnCreatedAndFoodTypeDTO_whenAddingNewFoodTypeWithValidName() throws Exception{
        //given
        String foodName = "food_name_missing";
        //when
        when(foodTypeRepository.findByName(foodName)).thenReturn(null);
        //then
        mockMvc.perform(get(foodEndpoint+"/{foodName}",foodName).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andDo(print());
        verify(foodTypeRepository, times(1)).findByName(foodName);
    }

    private List<FoodType> mockFoodTypes() {
        FoodType foodType1 = new FoodType();
        foodType1.setName("food1");
        FoodType foodType2 = new FoodType();
        foodType2.setName("food2");
        return Arrays.asList(foodType1, foodType2);
    }

}