package pl.ratemyrestaurant.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.factories.RatingFactory;
import pl.ratemyrestaurant.model.Rating;
import pl.ratemyrestaurant.repository.RatingRepository;
import pl.ratemyrestaurant.service.RatingService;

@RunWith(SpringJUnit4ClassRunner.class)
public class RatingServiceImplTest {

    private static RatingFactory ratingFactory = new RatingFactory();

    @Test
    public void shouldIncreaseThumbsUpByOne() throws Exception {

        //given
        RatingRepository mockRepo = Mockito.mock(RatingRepository.class);
        RatingService ratingService = new RatingServiceImpl(mockRepo);
        Rating rating = ratingFactory.getModelRating();
        Mockito.doReturn(rating).when(mockRepo).findRatingByIngredient_Id(Mockito.anyLong());

        //when
        RatingDTO updatedRating = ratingService.rateIngredient(123L, true);

        //then
        Assert.assertEquals(12, updatedRating.getThumb().getThumbsUp());

    }



}