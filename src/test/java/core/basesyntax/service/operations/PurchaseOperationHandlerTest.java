package core.basesyntax.service.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.exeptions.NegativeNumberExeption;
import core.basesyntax.exeptions.UnsupportedOperationExeption;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private PurchaseOperationHandler purchaseOperationHandler;

    @BeforeEach
    public void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void testApply_ValidTransaction_Ok() {
        Storage.fruits.put(new Fruit("apple"), 10);
        FruitTransactionDto dto = new FruitTransactionDto("p", "apple", 5);
        purchaseOperationHandler.apply(dto);

        assertEquals(5, Storage.fruits.get(new Fruit("apple")));
    }

    @Test
    public void testApply_InvalidTransaction_NotOk() {
        FruitTransactionDto dto = new FruitTransactionDto("s", "apple", 5);

        assertThrows(UnsupportedOperationExeption.class, () -> purchaseOperationHandler.apply(dto));
    }

    @Test
    public void testApply_NegativeQuantity_NotOk() {
        Storage.fruits.put(new Fruit("apple"), 10);
        FruitTransactionDto dto = new FruitTransactionDto("p", "apple", 15);

        assertThrows(NegativeNumberExeption.class, () -> purchaseOperationHandler.apply(dto));
    }

    @Test
    public void testIsApplicable_ValidOperationType_Ok() {
        FruitTransactionDto dto = new FruitTransactionDto("p", "apple", 5);
        boolean result = purchaseOperationHandler.isApplicable(dto);

        assertEquals(true, result);
    }

    @Test
    public void testIsApplicable_InvalidOperationType_Not_Ok() {
        FruitTransactionDto dto = new FruitTransactionDto("s", "apple", 5);
        boolean result = purchaseOperationHandler.isApplicable(dto);

        assertEquals(false, result);
    }
}