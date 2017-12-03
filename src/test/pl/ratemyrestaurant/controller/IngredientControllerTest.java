package pl.ratemyrestaurant.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.model.Info;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.repository.IngredientRepository;
import pl.ratemyrestaurant.service.IngredientService;
import pl.ratemyrestaurant.service.impl.IngredientServiceImpl;
import pl.ratemyrestaurant.type.APIInfoCodes;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.ratemyrestaurant.utils.TestUtils.asJsonString;
import static pl.ratemyrestaurant.utils.TestUtils.asObjectJsonString;

@RunWith(SpringJUnit4ClassRunner.class)
public class IngredientControllerTest {

    private MockMvc mockMvc;
    private static final String ingredientsEndpoint = "/ingredients";
    @Mock
    private IngredientService ingredientService;
    @InjectMocks
    private IngredientController ingredientController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void getAllIngredients() throws Exception {
        //when
        doReturn(getMockList()).when(ingredientService).getAllIngredientsDTO();
        //then
        String responseContent = mockMvc.perform(get(ingredientsEndpoint))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andReturn().getResponse().getContentAsString();
        verify(ingredientService, times(1)).getAllIngredientsDTO();
        Assert.assertEquals(asJsonString(getMockList()), responseContent);
    }

    @Test
    public void addIngredient() throws Exception {
    }

    @Test
    public void shouldReturnBadRequest_whenIngredientIsGetByInvalidId() throws Exception {
        //given
        Long id = -2L;
        //when
        doReturn(null).when(ingredientService).getIngredientDTOById(id);
        //then
        mockMvc.perform(get(ingredientsEndpoint + "/{ingredientId}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest());
        verify(ingredientService, times(1)).getIngredientDTOById(id);
    }

    @Test
    public void shouldReturnOK_whenIngredientIsGetByValidId() throws Exception {
        //given
        Long id = 100L;
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setName("Ingredient_name");
        //when
        doReturn(ingredientDTO).when(ingredientService).getIngredientDTOById(id);
        //then
        mockMvc.perform(get(ingredientsEndpoint + "/{ingredientId}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(ingredientDTO.getName())));
        verify(ingredientService, times(1)).getIngredientDTOById(id);
    }

    @Test
    public void shouldReturn422_whenIngredientAlreadyExists() throws Exception {
        //given
        IngredientRepository repository = Mockito.mock(IngredientRepository.class);
        ingredientService = new IngredientServiceImpl(repository);
        mockMvc = MockMvcBuilders.standaloneSetup(new IngredientController(ingredientService)).build();

        IngredientDTO testIngredient = new IngredientDTO();
        testIngredient.setName("potato");
        //when
        Info info = new Info.InfoBuilder()
                .setDescription("Ingredient name already exist in DB.")
                .setHttpStatusCode(422L)
                .setInfoCode(APIInfoCodes.INGREDIENT_ALREADY_EXISTS).build();
        doReturn(true).when(repository).existsByName(anyString());
        //then
        String responseContent = mockMvc.perform(post(ingredientsEndpoint).content(asJsonString(testIngredient)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        Info responseInfo = asObjectJsonString(responseContent, Info.class);
        Assert.assertEquals(info.getInfoCode(), responseInfo.getInfoCode());
        Assert.assertEquals(info.getHttpStatusCode(), responseInfo.getHttpStatusCode());
        Assert.assertEquals(info.getDesc(), responseInfo.getDesc());
    }

    @Test
    public void shouldReturn404andInfo_whenInvalidDataProvided() throws Exception {
        //given
        IngredientRepository repository = Mockito.mock(IngredientRepository.class);
        ingredientService = new IngredientServiceImpl(repository);
        mockMvc = MockMvcBuilders.standaloneSetup(new IngredientController(ingredientService)).build();

        IngredientDTO testIngredient = new IngredientDTO();
        testIngredient.setName("");
        //when
        Info info = new Info.InfoBuilder()
                .setDescription("Ingredient name invalid. Check object")
                .setHttpStatusCode(400L)
                .setInfoCode(APIInfoCodes.INVALID_INGREDIENT_DATA).build();
        doReturn(true).when(repository).existsByName(anyString());
        //then
        String responseContent = mockMvc.perform(post(ingredientsEndpoint).content(asJsonString(testIngredient)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        Info responseInfo = asObjectJsonString(responseContent, Info.class);
        Assert.assertEquals(info.getInfoCode(), responseInfo.getInfoCode());
        Assert.assertEquals(info.getHttpStatusCode(), responseInfo.getHttpStatusCode());
        Assert.assertEquals(info.getDesc(), responseInfo.getDesc());
    }


    @Test
    public void shouldAddIngredientAndStatus201_whenValidDataGiven() throws Exception {
        //given
        IngredientDTO testIngredient = new IngredientDTO();
        testIngredient.setName("testName");
        //when
        doReturn(testIngredient).when(ingredientService).addIngredient(any(IngredientDTO.class));
        //then
        mockMvc.perform(post(ingredientsEndpoint).content(asJsonString(testIngredient)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(testIngredient.getName())));

        verify(ingredientService, times(1)).addIngredient(any(IngredientDTO.class));

    }

    private static Set<Ingredient> getMockList() {
        Ingredient ingredient1 = new Ingredient("mock2");
        ingredient1.setId(1);
        Ingredient ingredient2 = new Ingredient("mock1");
        ingredient2.setId(20);
        Ingredient ingredient3 = new Ingredient("mock3");
        ingredient3.setId(30);
        Set<Ingredient> ingredients = new LinkedHashSet<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
        ingredients.add(ingredient3);
        return ingredients;
    }


}