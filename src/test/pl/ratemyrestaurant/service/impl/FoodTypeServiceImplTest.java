package pl.ratemyrestaurant.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.ratemyrestaurant.dto.FoodTypeDTO;
import pl.ratemyrestaurant.model.FoodType;
import pl.ratemyrestaurant.repository.FoodTypeRepository;

import java.util.NoSuchElementException;

@RunWith(SpringJUnit4ClassRunner.class)
public class FoodTypeServiceImplTest {

    @Test
    public void shouldNotReturnNullObject() throws Exception {

        //given
        FoodTypeRepository repoMock = Mockito.mock(FoodTypeRepository.class);
        FoodType foodType = new FoodType();
        Mockito.doReturn(foodType).when(repoMock).findByName(Mockito.anyString());
        FoodTypeServiceImpl foodTypeServiceImpl = new FoodTypeServiceImpl(repoMock);

        //when
        FoodTypeDTO result = foodTypeServiceImpl.getFoodTypeDTOByName("jestem bezużytecznym stringiem");

        //then
        Assert.assertNotNull(result);


    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementException() throws Exception {

        //given
        FoodTypeRepository repoMock = Mockito.mock(FoodTypeRepository.class);
        Mockito.doReturn(null).when(repoMock).findByName(Mockito.anyString());
        FoodTypeServiceImpl foodTypeServiceImpl = new FoodTypeServiceImpl(repoMock);

        //when
        FoodTypeDTO result = foodTypeServiceImpl.getFoodTypeDTOByName("jakiegokolwiek stringa");
    }

}