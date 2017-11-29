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
import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.factories.RatingFactory;
import pl.ratemyrestaurant.service.RatingService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.ratemyrestaurant.utils.TestUtils.asJsonString;

@RunWith(SpringJUnit4ClassRunner.class)
public class RatingControllerTest {
    private MockMvc mockMvc;
    private static final String ratingEndpoint = "/rating";

    @Mock
    private RatingService ratingService;
    @InjectMocks
    private RatingController ratingController = new RatingController(ratingService);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
    }

    @Test
    public void shouldReturn200AndUpdatedRating_whenVotingWithValidRatingIdGiven() throws Exception {
        //given
        Long givenId = 33L;
        boolean givenUpvote = true;
        RatingDTO ratingDTO = new RatingFactory().getModelRatingDto();
        //when
        doReturn(ratingDTO).when(ratingService).rateIngredient(givenId, givenUpvote);
        //then
        String stringResponse = mockMvc.perform(
                put(ratingEndpoint + "/{ratingID}", givenId)
                        .content(asJsonString(givenUpvote))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn().getResponse().getContentAsString();
        verify(ratingService, times(1)).rateIngredient(givenId, givenUpvote);
        assertEquals(asJsonString(ratingDTO), stringResponse);
    }

    @Test
    public void shouldReturn404_whenVotingWithInvalidRatingID() throws Exception {
        //given
        Long givenId = 33L;
        boolean givenUpvote = true;
        //when
        doReturn(null).when(ratingService).rateIngredient(givenId, givenUpvote);
        //then
        mockMvc.perform(
                put(ratingEndpoint + "/{ratingID}", givenId)
                        .content(asJsonString(givenUpvote))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
        verify(ratingService, times(1)).rateIngredient(givenId, givenUpvote);
    }
}