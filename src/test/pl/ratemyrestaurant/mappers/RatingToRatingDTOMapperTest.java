package pl.ratemyrestaurant.mappers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.ratemyrestaurant.dto.RatingDTO;
import pl.ratemyrestaurant.factories.RatingFactory;
import pl.ratemyrestaurant.model.Rating;

@RunWith(SpringJUnit4ClassRunner.class)
public class RatingToRatingDTOMapperTest {

    static private RatingFactory ratingFactory = new RatingFactory();

    @Test
    public void shouldReturnCorrespondingRatingDtoFromGivenRating() throws Exception {

        //given
        Rating modelRating = ratingFactory.getModelRating();
        RatingDTO modelRatingDTO = ratingFactory.getModelRatingDto();

        //when
        RatingDTO resultRatingDTO = RatingToRatingDTOMapper.mapRatingToRatingDto(modelRating);

        //then
        Assert.assertEquals(modelRatingDTO.getThumb().getThumbsDown(), resultRatingDTO.getThumb().getThumbsDown());
        Assert.assertEquals(modelRatingDTO.getThumb().getThumbsUp(), resultRatingDTO.getThumb().getThumbsUp());
        Assert.assertEquals(modelRatingDTO.getIngredient().getId(), resultRatingDTO.getIngredient().getId());
    }

    @Test
    public void shouldCreateCopyOfRestaurantObject(){

        //given
        Rating modelRating = ratingFactory.getModelRating();
        RatingDTO modelRatingDTO = ratingFactory.getModelRatingDto();

        //when
        RatingDTO resultRatingDTO = RatingToRatingDTOMapper.mapRatingToRatingDto(modelRating);

        //then
        Assert.assertNotEquals(modelRatingDTO.getRestaurant(), resultRatingDTO.getRestaurant());

    }

    @Test
    public void shouldReturnCorrespondingRatingFromGivenRatingDTO() throws Exception {

        //given
        Rating modelRating = ratingFactory.getModelRating();
        RatingDTO modelRatingDTO = ratingFactory.getModelRatingDto();

        //when
        Rating resultRating = RatingToRatingDTOMapper.ratingDTOToRating(modelRatingDTO);

        //then
        Assert.assertEquals(modelRating.getIngredient().getName(), resultRating.getIngredient().getName());
        Assert.assertEquals(modelRating.getIngredient().getId(), resultRating.getIngredient().getId());
        Assert.assertEquals(modelRating.getThumb().getThumbsDown(), resultRating.getThumb().getThumbsDown());

    }

    @Test
    public void shouldCreateCopyOfThumbObject(){

        //given
        Rating modelRating = ratingFactory.getModelRating();
        RatingDTO modelRatingDTO = ratingFactory.getModelRatingDto();

        //when
        Rating resultRating = RatingToRatingDTOMapper.ratingDTOToRating(modelRatingDTO);

        //then
        Assert.assertNotEquals(modelRating.getThumb(), resultRating.getThumb());

    }





}