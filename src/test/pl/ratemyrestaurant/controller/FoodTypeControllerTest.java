package pl.ratemyrestaurant.controller;

import org.junit.Assert;
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
import pl.ratemyrestaurant.dto.FoodTypeDTO;
import pl.ratemyrestaurant.exception.NoSuchFoodTypeException;
import pl.ratemyrestaurant.model.FoodType;
import pl.ratemyrestaurant.model.Info;
import pl.ratemyrestaurant.service.FoodTypeService;
import pl.ratemyrestaurant.type.APIInfoCodes;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.ratemyrestaurant.utils.TestUtils.asJsonString;

@RunWith(SpringJUnit4ClassRunner.class)
public class FoodTypeControllerTest {
    private MockMvc mockMvc;
    private static final String foodEndpoint = "/foodTypes";

    @Mock
    private FoodTypeService foodTypeService;
    @InjectMocks
    private FoodTypeController foodTypeController = new FoodTypeController(foodTypeService);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(foodTypeController).build();
    }

    @Test
    public void shouldReturnAllFoodTypesAnd200() throws Exception {
        //when
        doReturn(mockFoodTypes()).when(foodTypeService).getAllFoodTypesDTO();
        //then
        String responseContent = mockMvc.perform(get(foodEndpoint).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("food1")))
                .andExpect(jsonPath("$[1].name", is("food2")))
                .andReturn().getResponse().getContentAsString();
        verify(foodTypeService, times(1)).getAllFoodTypesDTO();
        Assert.assertEquals(asJsonString(mockFoodTypes()), responseContent);
    }

    @Test
    public void shouldReturnFoodTypeAnd200_whenValidFoodNameProvided() throws Exception {
        //given
        String foodName = "food2";
        FoodTypeDTO food2 = new FoodTypeDTO(foodName);
        //when
        doReturn(food2).when(foodTypeService).getFoodTypeDTOByName(foodName);
        //then
        String responseContent = mockMvc.perform(get(foodEndpoint + "/{foodName}", foodName).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        verify(foodTypeService, times(1)).getFoodTypeDTOByName(foodName);
        Assert.assertEquals(asJsonString(food2), responseContent);
    }

    @Test
    public void shouldReturn404_whenProvidedFoodTypeNotExistsInDB() throws Exception {
        //given
        String foodName = "food_name_missing";
        //when
        Info info = new Info.InfoBuilder()
                .setDescription("No such foodType: " + foodName)
                .setInfoCode(APIInfoCodes.FOOD_TYPE_NOT_FOUND)
                .setHttpStatusCode(404L).build();
        doThrow(new NoSuchFoodTypeException(info)).when(foodTypeService).getFoodTypeDTOByName(foodName);
        //then
        String responseContent = mockMvc.perform(get(foodEndpoint + "/{foodName}", foodName).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

        verify(foodTypeService, times(1)).getFoodTypeDTOByName(foodName);
        Assert.assertEquals(asJsonString(info), responseContent);
    }

    @Test
    public void shouldReturn200AndFoodTypeDTO_whenAddingNewValidFoodType() throws Exception {
        //given
        String foodName = "new_food_name";
        FoodTypeDTO foodTypeDTO = new FoodTypeDTO(foodName);
        //when
        doReturn(foodTypeDTO).when(foodTypeService).addNewFoodType(any(FoodTypeDTO.class));
        //then
        String responseContent = mockMvc.perform(post(foodEndpoint)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(foodTypeDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

        verify(foodTypeService, times(1)).addNewFoodType(any(FoodTypeDTO.class));
        Assert.assertEquals(asJsonString(foodTypeDTO), responseContent);
    }

    @Test
    public void shouldReturn422_whenCreatingFoodTypeAndGivenFoodTypeAlreadyExistsInDB() throws Exception {
        //given
        String foodName = "food_name_alreadyExists";
        FoodTypeDTO foodTypeDTO = new FoodTypeDTO(foodName);
        //when
        doReturn(null).when(foodTypeService).addNewFoodType(foodTypeDTO);
        //then
        mockMvc.perform(post(foodEndpoint).accept(MediaType.APPLICATION_JSON_UTF8).contentType(MediaType.APPLICATION_JSON_UTF8).content(asJsonString(foodTypeDTO)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.httpStatusCode", is(422)))
                .andExpect(jsonPath("$.infoCode", is(APIInfoCodes.ENTITY_ALREADY_EXISTS.toString())))
                .andExpect(jsonPath("$.desc", is("FoodType already exists")));
        verify(foodTypeService, times(1)).addNewFoodType(any(FoodTypeDTO.class));
    }

    private List<FoodType> mockFoodTypes() {
        FoodType foodType1 = new FoodType();
        foodType1.setName("food1");
        FoodType foodType2 = new FoodType();
        foodType2.setName("food2");
        return Arrays.asList(foodType1, foodType2);
    }

}