package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {
    @Mock
    private Bun mockBun;

    @Mock
    private Ingredient mockIngredient1;

    @Mock
    private Ingredient mockIngredient2;


    @Test
    public void testSetBuns() {
        Burger burger = new Burger();
        burger.setBuns(mockBun);
        assertSame("Булочка должна устанавливаться правильно", mockBun, burger.bun);
    }

    @Test
    public void testAddIngredient() {
        Burger burger = new Burger();
        burger.addIngredient(mockIngredient1);
        assertEquals("Ingredients list should have 1 element", 1, burger.ingredients.size());
        assertSame("Added ingredient should match", mockIngredient1, burger.ingredients.get(0));
    }

    @Test
    public void testRemoveIngredient() {
        Burger burger = new Burger();
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        burger.removeIngredient(0);
        assertEquals("Ingredients list should have 1 element after removal", 1, burger.ingredients.size());
        assertSame("Remaining ingredient should be the second one", mockIngredient2, burger.ingredients.get(0));
    }

    @Test
    public void testMoveIngredient() {
        Burger burger = new Burger();
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

//        burger.moveIngredient(0, 1);
//
//        assertEquals("Ingredients list should still have 2 elements", 2, burger.ingredients.size());
//        assertSame("First element should now be the second ingredient", mockIngredient2, burger.ingredients.get(0));
//        assertSame("Second element should now be the first ingredient", mockIngredient1, burger.ingredients.get(1));

        assertSame("Before move: first ingredient should be mockIngredient1", mockIngredient1, burger.ingredients.get(0));
        assertSame("Before move: second ingredient should be mockIngredient2", mockIngredient2, burger.ingredients.get(1));

        burger.moveIngredient(0, 1);

        assertEquals("Ingredients list should still have 2 elements", 2, burger.ingredients.size());
        assertSame("After move: first element should be mockIngredient2", mockIngredient2, burger.ingredients.get(0));
        assertSame("After move: second element should be mockIngredient1", mockIngredient1, burger.ingredients.get(1));

    }

    @Test
    public void testGetPriceWithIngredients() {
        Burger burger = new Burger();
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        when(mockBun.getPrice()).thenReturn(100.0f);
        when(mockIngredient1.getPrice()).thenReturn(50.0f);
        when(mockIngredient2.getPrice()).thenReturn(75.0f);

        float expectedPrice = (100.0f * 2) + 50.0f + 75.0f; //булочка цена * 2 (две булочки)
        float actualPrice = burger.getPrice();

        assertEquals("Price should be calculated correctly", expectedPrice, actualPrice, 0.001f);
        verify(mockBun, times(1)).getPrice();
        verify(mockIngredient1).getPrice();
        verify(mockIngredient2).getPrice();
    }

    @Test
    public void testGetPriceWithoutIngredients() {
        Burger burger = new Burger();
        burger.setBuns(mockBun);

        when(mockBun.getPrice()).thenReturn(100.0f);

        float expectedPrice = 100.0f * 2;
        float actualPrice = burger.getPrice();

        assertEquals("Price should be only buns price when no ingredients", expectedPrice, actualPrice, 0.001f);
        verify(mockBun, times(1)).getPrice();
    }

    @Test
    public void testGetReceipt() {
        Burger burger = new Burger();
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);

        when(mockBun.getName()).thenReturn("Test Bun");
        when(mockBun.getPrice()).thenReturn(100.0f);
        when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient1.getName()).thenReturn("Test Sauce");
        when(mockIngredient1.getPrice()).thenReturn(50.0f);

        String receipt = burger.getReceipt();

        assertNotNull("Receipt should not be null", receipt);
        assertTrue("Receipt should contain bun name", receipt.contains("Test Bun"));
        assertTrue("Receipt should contain ingredient type", receipt.contains("sauce"));
        assertTrue("Receipt should contain ingredient name", receipt.contains("Test Sauce"));
        assertTrue("Receipt should contain total price", receipt.contains("250,000000"));


    }

    @Test
    public void testGetReceiptWithFillingIngredients() {
        Burger burger = new Burger();
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);

        when(mockBun.getName()).thenReturn("Test Bun");
        when(mockBun.getPrice()).thenReturn(100.0f);
        when(mockIngredient1.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient1.getName()).thenReturn("Test Filling");
        when(mockIngredient1.getPrice()).thenReturn(50.0f);

        String receipt = burger.getReceipt();

        assertNotNull("Receipt should not be null", receipt);
        assertTrue("Receipt should contain filling name", receipt.contains("filling"));
        assertTrue("Receipt should contain ingredient name", receipt.contains("Test Filling"));

    }

    @Test
    public void testGetReceiptWithNoIngredients() {
        Burger burger = new Burger();
        burger.setBuns(mockBun);

        when(mockBun.getName()).thenReturn("Test Bun");
        when(mockBun.getPrice()).thenReturn(100.0f);

        String receipt = burger.getReceipt();

        assertNotNull("Receipt should not be null", receipt);
        assertTrue("Receipt should contain bun name", receipt.contains("Test Bun"));
        assertTrue("Receipt should contain price for bun only", receipt.contains("200,000000"));
    }
}