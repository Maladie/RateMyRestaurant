package pl.ratemyrestaurant.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.dto.RestaurantDTO;
import pl.ratemyrestaurant.dto.RestaurantPIN;
import pl.ratemyrestaurant.exception.IngredientRatingNotFoundException;
import pl.ratemyrestaurant.exception.RestaurantNotFoundException;
import pl.ratemyrestaurant.factories.RatingFactory;
import pl.ratemyrestaurant.model.Info;
import pl.ratemyrestaurant.model.Location;
import pl.ratemyrestaurant.model.UserSearchCircle;
import pl.ratemyrestaurant.service.RatingService;
import pl.ratemyrestaurant.service.RestaurantService;
import pl.ratemyrestaurant.type.APIInfoCodes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.ratemyrestaurant.utils.TestUtils.asJsonString;
import static pl.ratemyrestaurant.utils.TestUtils.asObjectJsonString;

@RunWith(SpringJUnit4ClassRunner.class)
public class RestaurantControllerTest {
    private MockMvc mockMvc;
    private static final String restaurantsEndpoint = "/restaurants";
    private RestaurantService restaurantService;
    private RatingService ratingService;
    private RestaurantController restaurantController;

    @Before
    public void setUp() throws Exception {
        restaurantService = mock(RestaurantService.class);
        ratingService = mock(RatingService.class);
        restaurantController = new RestaurantController(restaurantService, ratingService);
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
    }

    @Test
    public void shouldReturn200AndFullRestaurantInfo_whenValidIdGiven() throws Exception {
        String restaurantID = "restaurant_ID";
        RestaurantDTO restaurantDTO = getMockRestaurantDTO(restaurantID);
        doReturn(restaurantDTO).when(restaurantService).getOrRetrieveRestaurantDTOByID(restaurantID);
        String responseContent = mockMvc.perform(get(restaurantsEndpoint + "/{restaurantID}", restaurantID).accept(MediaType.APPLICATION_JSON_UTF8))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        verify(restaurantService, times(1)).getOrRetrieveRestaurantDTOByID(restaurantID);
        verifyNoMoreInteractions(restaurantService);
        Assert.assertEquals(asJsonString(restaurantDTO), responseContent);
    }

    @Test
    public void shouldReturn404_whenInvalidOrNonExistentRestaurantIdGiven() throws Exception {
        String restaurantID = "restaurant_ID_not_in_DB";
        doReturn(null).when(restaurantService).getOrRetrieveRestaurantDTOByID(restaurantID);
        mockMvc.perform(get(restaurantsEndpoint + "/{restaurantID}", restaurantID).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
        verify(restaurantService, times(1)).getOrRetrieveRestaurantDTOByID(restaurantID);
        verifyNoMoreInteractions(restaurantService);
    }

    @Test
    public void shouldReturn200AndCollectionOfRestaurantPins_whenValidLocationDataGivenAndAnyRestaurantFoundThere() throws Exception {
        String restaurantID_1 = "restaurant_1_in_radius";
        String restaurantID_2 = "restaurant_2_in_radius";
        RestaurantPIN restaurantPIN1 = getMockRestaurantPIN(restaurantID_1);
        RestaurantPIN restaurantPIN2 = getMockRestaurantPIN(restaurantID_2);
        Set<RestaurantPIN> restaurantPINS = new HashSet<>(Arrays.asList(restaurantPIN1, restaurantPIN2));
        // any userSearchCircle, because is created from parameters just before service call
        doReturn(restaurantPINS).when(restaurantService).retrieveRestaurantsInRadius(any(UserSearchCircle.class));
        String responseContent = mockMvc.perform(get(restaurantsEndpoint + "/areaSearch")
                .param("lat", "19.1")
                .param("lng", "51.1")
                .param("radius", "500")
                .param("type", "bar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andReturn().getResponse().getContentAsString();
        verify(restaurantService, times(1)).retrieveRestaurantsInRadius(any(UserSearchCircle.class));
        verifyNoMoreInteractions(restaurantService);
        Assert.assertEquals(asJsonString(restaurantPINS), responseContent);
    }

    @Test
    public void shouldReturn200AndEmptyCollectionOfRestaurantPins_whenValidLocationDataGivenButNoRestaurantsFound() throws Exception {
        Set<RestaurantPIN> restaurantPINS = new HashSet<>();
        doReturn(restaurantPINS).when(restaurantService).retrieveRestaurantsInRadius(any(UserSearchCircle.class));
        mockMvc.perform(get(restaurantsEndpoint + "/areaSearch")
                .param("lat", "19.1")
                .param("lng", "51.1")
                .param("radius", "500")
                .param("type", "bar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)));
        verify(restaurantService).retrieveRestaurantsInRadius(any(UserSearchCircle.class));
        verifyNoMoreInteractions(restaurantService);
    }

    @Test
    public void shouldReturn201AndCreatedIngredientRating_whenValidDataGiven() throws Exception {
        RatingDTO ratingDTO = new RatingFactory().getModelRatingDto();
        String restaurantId = ratingDTO.getRestaurant().getId();
        Long ingredientId = ratingDTO.getIngredient().getId();
        doReturn(ratingDTO).when(ratingService).addNewIngredientRating(restaurantId, ingredientId);

        String contentResponse = mockMvc.perform(post(restaurantsEndpoint + "/{restaurantID}/ingredientRatings", restaurantId)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(asJsonString(ingredientId)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        verify(ratingService, times(1)).addNewIngredientRating(restaurantId, ingredientId);
        Assert.assertEquals(asJsonString(ratingDTO), contentResponse);
    }

    @Test
    public void shouldReturn422AndInfo_whenValidDataGivenButIngredientAlreadyCreated() throws Exception {
        String restaurantId = "restaurant_ID";
        Long ingredientId = 12L;
        Info info = new Info.InfoBuilder()
                .setHttpStatusCode(422L)
                .setInfoCode(APIInfoCodes.ENTITY_ALREADY_EXISTS)
                .setDescription("Could not create. Ingredient rating already exists").build();
        // returns null if already created
        doReturn(null).when(ratingService).addNewIngredientRating(restaurantId, ingredientId);

        String contentResponse = mockMvc.perform(post(restaurantsEndpoint + "/{restaurantID}/ingredientRatings", restaurantId)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(asJsonString(ingredientId)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        verify(ratingService, times(1)).addNewIngredientRating(restaurantId, ingredientId);

        Info responseInfo = asObjectJsonString(contentResponse, Info.class);
        // info.key skipped -> each Info instance has random key
        Assert.assertEquals(info.getInfoCode(), responseInfo.getInfoCode());
        Assert.assertEquals(info.getHttpStatusCode(), responseInfo.getHttpStatusCode());
        Assert.assertEquals(info.getDesc(), responseInfo.getDesc());
    }

    @Test
    public void shouldReturn200AndVotedRating_whenCompleteValidDataGiven() throws Exception {
        RatingDTO ratingDTO = new RatingFactory().getModelRatingDto();
        String restaurantId = ratingDTO.getRestaurant().getId();
        Long ingredientId = ratingDTO.getIngredient().getId();
        Boolean upVote = true;
        doReturn(ratingDTO).when(ratingService).rateIngredient(restaurantId, ingredientId, upVote);

        String contentResponse = mockMvc.perform(put(restaurantsEndpoint + "/{restaurantID}/ingredientRatings/{ingredientId}", restaurantId, ingredientId)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(upVote)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        verify(ratingService, times(1)).rateIngredient(restaurantId, ingredientId, upVote);
        Assert.assertEquals(asJsonString(ratingDTO), contentResponse);
    }

    @Test
    public void shouldReturn404AndErrorInfo_whenGivenRestaurantIdIsNotPresentInDB() throws Exception {
        RatingDTO ratingDTO = new RatingFactory().getModelRatingDto();
        String restaurantId = ratingDTO.getRestaurant().getId();
        Long ingredientId = ratingDTO.getIngredient().getId();
        Boolean upVote = true;
        Info info = new Info.InfoBuilder()
                .setHttpStatusCode(422L)
                .setInfoCode(APIInfoCodes.RESTAURANT_NOT_EXISTS)
                .setDescription("Could not create. Restaurant with restaurantID does not exist: " + restaurantId).build();

        doThrow(new RestaurantNotFoundException("Invalid RestaurantID")).when(ratingService).rateIngredient(restaurantId, ingredientId, upVote);

        String contentResponse = mockMvc.perform(put(restaurantsEndpoint + "/{restaurantID}/ingredientRatings/{ingredientId}", restaurantId, ingredientId)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(asJsonString(ingredientId)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        verify(ratingService, times(1)).rateIngredient(restaurantId, ingredientId, upVote);
        Info responseInfo = asObjectJsonString(contentResponse, Info.class);
        // info.key skipped -> each Info instance has random key
        Assert.assertEquals(info.getInfoCode(), responseInfo.getInfoCode());
        Assert.assertEquals(info.getHttpStatusCode(), responseInfo.getHttpStatusCode());
        Assert.assertEquals(info.getDesc(), responseInfo.getDesc());
    }

    @Test
    public void shouldReturn404AndErrorInfo_whenGivenIngredientIdIsNotPresentInDB() throws Exception {
        RatingDTO ratingDTO = new RatingFactory().getModelRatingDto();
        String restaurantId = ratingDTO.getRestaurant().getId();
        Long ingredientId = ratingDTO.getIngredient().getId();
        Boolean upVote = true;
        Info info = new Info.InfoBuilder()
                .setHttpStatusCode(422L)
                .setInfoCode(APIInfoCodes.INGREDIENT_RATING_NOT_EXISTS)
                .setDescription("Could not create. IngredientRating with ingredientID does not exist: " + ingredientId).build();
        // returns null if already created
        doThrow(new IngredientRatingNotFoundException("Invalid ingredientID")).when(ratingService).rateIngredient(restaurantId, ingredientId, upVote);

        String contentResponse = mockMvc.perform(put(restaurantsEndpoint + "/{restaurantID}/ingredientRatings/{ingredientId}", restaurantId, ingredientId)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(asJsonString(ingredientId)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        verify(ratingService, times(1)).rateIngredient(restaurantId, ingredientId, upVote);
        Info responseInfo = asObjectJsonString(contentResponse, Info.class);
        // info.key skipped -> each Info instance has random key
        Assert.assertEquals(info.getInfoCode(), responseInfo.getInfoCode());
        Assert.assertEquals(info.getHttpStatusCode(), responseInfo.getHttpStatusCode());
        Assert.assertEquals(info.getDesc(), responseInfo.getDesc());
    }

    private static RestaurantDTO getMockRestaurantDTO(String restaurantID) {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setId(restaurantID);
        restaurantDTO.setNewlyCreated(false);
        restaurantDTO.setName("mock_restaurant");
        restaurantDTO.setFoodTypes(new HashSet<>());
        restaurantDTO.setIngredientRatings(new HashSet<>());
        restaurantDTO.setLocation(new Location(19.4, 51.1));
        return restaurantDTO;
    }

    private static RestaurantPIN getMockRestaurantPIN(String restaurantID) {
        RestaurantPIN restaurantPIN = new RestaurantPIN();
        restaurantPIN.setId(restaurantID);
        restaurantPIN.setName("restaurant_name");
        restaurantPIN.setLocation(new Location(19.1, 51.1));
        return restaurantPIN;
    }

}