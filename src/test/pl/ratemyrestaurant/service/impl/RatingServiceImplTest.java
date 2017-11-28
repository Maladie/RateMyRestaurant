package pl.ratemyrestaurant.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.factories.RatingFactory;
import pl.ratemyrestaurant.model.Rating;
import pl.ratemyrestaurant.model.Vote;
import pl.ratemyrestaurant.repository.IngredientRepository;
import pl.ratemyrestaurant.repository.RatingRepository;
import pl.ratemyrestaurant.repository.RestaurantRepository;
import pl.ratemyrestaurant.service.RatingService;

@RunWith(SpringJUnit4ClassRunner.class)
public class RatingServiceImplTest {

    private static RatingFactory ratingFactory = new RatingFactory();

    @Test
    public void shouldIncreaseThumbsUpByOne() throws Exception {

        //given
        RatingRepository mockRepo = Mockito.mock(RatingRepository.class);
        RestaurantRepository mockRestRepo = Mockito.mock(RestaurantRepository.class);
        IngredientRepository mockIngredientRepository = Mockito.mock(IngredientRepository.class);
        RatingService ratingService = new RatingServiceImpl(mockRepo, mockRestRepo, mockIngredientRepository);
        Rating rating = ratingFactory.getModelRating();
        Vote vote = new Vote("", 123L, true);
        Mockito.doReturn(rating).when(mockRepo).findByRestaurant_IdAndIngredient_Id("", 123L);


        //when
        RatingDTO updatedRating = ratingService.addOrUpdateRating(vote);

        //then
        Assert.assertEquals(11, updatedRating.getThumb().getThumbsUp());

    }



}