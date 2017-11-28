package pl.ratemyrestaurant.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.ratemyrestaurant.dto.FoodTypeDTO;
import pl.ratemyrestaurant.model.FoodType;
import pl.ratemyrestaurant.repository.FoodTypeRepository;
import pl.ratemyrestaurant.service.FoodTypeService;
import pl.ratemyrestaurant.service.impl.FoodTypeServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.ratemyrestaurant.utils.TestUtils.asJsonString;

@RunWith(MockitoJUnitRunner.class)
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
                .andExpect(jsonPath("$[1].name", is("food2")));
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
                .andExpect(jsonPath("$.name", is(foodName)));
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
                .andExpect(status().isNotFound());
        verify(foodTypeRepository, times(1)).findByName(foodName);
    }

    @Test
    public void shouldReturn200AndFoodTypeDTO_whenAddingNewValidFoodType() throws Exception{
        //given
        String foodName = "new_food_name";
        FoodTypeDTO foodTypeDTO = new FoodTypeDTO(foodName);

        //when
        doReturn(null).when(foodTypeRepository).findByNameIgnoreCase(foodName);
        foodTypeController = new FoodTypeController(foodTypeService);

        //then
        String responseContent = mockMvc.perform(post(foodEndpoint).accept(MediaType.APPLICATION_JSON_UTF8).contentType(MediaType.APPLICATION_JSON_UTF8).content(asJsonString(foodTypeDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

        verify(foodTypeRepository, times(1)).findByNameIgnoreCase(foodName);
        verify(foodTypeRepository, times(1)).save(any(FoodType.class));
        Assert.assertEquals(asJsonString(foodTypeDTO), responseContent);
    }

    @Test
    public void shouldReturn422_whenCreatingFoodTypeAndGivenFoodTypeAlreadyExistsInDB() throws Exception{
        //given
        String foodName = "food_name_alreadyExists";
        FoodType foodType = new FoodType(foodName);
        FoodTypeDTO foodTypeDTO = new FoodTypeDTO(foodName);
        //when
        when(foodTypeRepository.findByNameIgnoreCase(foodName)).thenReturn(foodType);
        //then
        mockMvc.perform(post(foodEndpoint).accept(MediaType.APPLICATION_JSON_UTF8).contentType(MediaType.APPLICATION_JSON_UTF8).content(asJsonString(foodTypeDTO)))
                .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("$.httpStatusCode", is(422)))
        .andExpect(jsonPath("$.desc", is("FoodType already exists")));
        verify(foodTypeRepository, times(1)).findByNameIgnoreCase(foodName);
    }

    private List<FoodType> mockFoodTypes() {
        FoodType foodType1 = new FoodType();
        foodType1.setName("food1");
        FoodType foodType2 = new FoodType();
        foodType2.setName("food2");
        return Arrays.asList(foodType1, foodType2);
    }

}