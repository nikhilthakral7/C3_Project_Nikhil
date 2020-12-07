import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.time.Clock;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime;
    LocalTime closingTime;
    ArrayList<String> selectedItems;

    @BeforeEach
    public void arrange() {
        this.openingTime = LocalTime.parse("10:30:00");
        this.closingTime = LocalTime.parse("22:00:00");
        this.restaurant = new Restaurant("Amelie's cafe","Chennai", openingTime, closingTime);
        this.restaurant.addToMenu("Sweet corn soup",119);
        this.restaurant.addToMenu("Vegetable lasagne", 269);
        this.selectedItems = new ArrayList<String>();
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant mockedRestaurant = Mockito.spy(this.restaurant);
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("11:00:00"));
        assertTrue(mockedRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant mockedRestaurant = Mockito.spy(this.restaurant);
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("23:30:00"));
        assertFalse(mockedRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class, ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>ORDER<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void price_when_no_items_selected_should_be_zero(){
        assertEquals(restaurant.getTotalPrice(selectedItems), 0);
    }

    @Test
    public void price_when_one_item_selected_should_return_price_of_item(){
        selectedItems.add(restaurant.getMenu().get(0).getName());
        assertEquals(restaurant.getTotalPrice(selectedItems), restaurant.getMenu().get(0).getPrice());
    }

    @Test
    public void price_when_multiple_items_selected_should_return_sum_of_price_of_item(){
        selectedItems.add(restaurant.getMenu().get(0).getName());
        selectedItems.add(restaurant.getMenu().get(1).getName());
        assertEquals(restaurant.getTotalPrice(selectedItems), restaurant.getMenu().get(0).getPrice() + restaurant.getMenu().get(1).getPrice());
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<ORDER>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}