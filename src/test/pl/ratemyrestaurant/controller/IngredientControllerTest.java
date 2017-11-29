package pl.ratemyrestaurant.controller;

import org.hamcrest.Matchers;
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
import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.service.IngredientService;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.ratemyrestaurant.utils.TestUtils.asJsonString;

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
        mockMvc.perform(get(ingredientsEndpoint+"/{ingredientId}", id)
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
        mockMvc.perform(get(ingredientsEndpoint+"/{ingredientId}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(ingredientDTO.getName())));
        verify(ingredientService, times(1)).getIngredientDTOById(id);
    }

    @Test
    public void shouldReturn422_whenInvalidDataProvided() throws Exception {
        //given
        IngredientDTO testIngredient = new IngredientDTO();
        testIngredient.setName(null);
        //when
        doReturn(null).when(ingredientService).addIngredient(testIngredient);
        //then
        mockMvc.perform(post(ingredientsEndpoint).content(asJsonString(testIngredient)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.httpStatusCode",is(422)))
                .andExpect(jsonPath("$.key", is(Matchers.any(String.class))));
        verify(ingredientService, times(1)).addIngredient(any(IngredientDTO.class));
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