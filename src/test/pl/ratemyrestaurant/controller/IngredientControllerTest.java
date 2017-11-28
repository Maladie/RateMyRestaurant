package pl.ratemyrestaurant.controller;

import org.hamcrest.Matchers;
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
import pl.ratemyrestaurant.repository.IngredientRepository;
import pl.ratemyrestaurant.service.IngredientService;
import pl.ratemyrestaurant.service.impl.IngredientServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.ratemyrestaurant.utils.TestUtils.asJsonString;

@RunWith(SpringJUnit4ClassRunner.class)
public class IngredientControllerTest {

    private MockMvc mockMvc;
    @Mock
    private IngredientRepository ingredientRepository;
    @InjectMocks
    private IngredientService ingredientService = new IngredientServiceImpl(ingredientRepository);
    @InjectMocks
    private IngredientController ingredientController = new IngredientController(ingredientService);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void getAllIngredients() throws Exception {
        when(ingredientRepository.findAll()).thenReturn(getMockList());
        mockMvc.perform(get("/ingredients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)));
        verify(ingredientRepository, times(1)).findAll();
    }

    @Test
    public void addIngredient() throws Exception {
    }

    @Test
    public void shouldReturnBadRequest_whenIngredientIsGetByInvalidId() throws Exception {
        Long id = -2L;
        when(ingredientRepository.findOne(id)).thenReturn(null);
        mockMvc.perform(get("/ingredients/{ingredientId}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest());
        verify(ingredientRepository, times(1)).findOne(id);

    }

    @Test
    public void shouldReturnOK_whenIngredientIsGetByValidId() throws Exception {
        Long id = 100L;
        Ingredient testIngredient = new Ingredient("test");
        testIngredient.setId(id);
        when(ingredientRepository.findOne(id)).thenReturn(testIngredient);
        mockMvc.perform(get("/ingredients/{ingredientId}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testIngredient.getName())));
        verify(ingredientRepository, times(1)).findOne(id);

    }

    @Test
    public void shouldReturn422_whenInvalidDataProvided() throws Exception {
        IngredientDTO testIngredient = new IngredientDTO();
        testIngredient.setName(null);
        mockMvc.perform(post("/ingredients").content(asJsonString(testIngredient)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.httpStatusCode",is(422)))
                .andExpect(jsonPath("$.key", is(Matchers.any(String.class))));

    }


    @Test
    public void shouldAddIngredientAndStatusCreated_whenValidDataProvided() throws Exception {
        //given
        IngredientDTO testIngredient = new IngredientDTO();
        testIngredient.setName("testName");
        Ingredient ingredientToSave = new Ingredient();
        ingredientToSave.setName(testIngredient.getName());
        //when
        when(ingredientRepository.findAll()).thenReturn(getMockList());
        when(ingredientRepository.save(ingredientToSave)).thenReturn(ingredientToSave);
        //then
        mockMvc.perform(post("/ingredients").content(asJsonString(testIngredient)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(testIngredient.getName())));

        verify(ingredientRepository, times(1)).findAll();
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));

    }

    private static List<Ingredient> getMockList() {
        Ingredient ingredient1 = new Ingredient("mock2");
        ingredient1.setId(1);
        Ingredient ingredient2 = new Ingredient("mock1");
        ingredient2.setId(20);
        Ingredient ingredient3 = new Ingredient("mock3");
        ingredient3.setId(30);
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
        ingredients.add(ingredient3);
        return ingredients;
    }


}