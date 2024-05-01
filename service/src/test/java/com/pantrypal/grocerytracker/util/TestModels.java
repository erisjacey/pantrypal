package com.pantrypal.grocerytracker.util;

import com.pantrypal.grocerytracker.dto.GroceryItemDto;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.model.PantryItem;
import com.pantrypal.grocerytracker.model.Product;
import com.pantrypal.grocerytracker.model.unit.Gram;
import com.pantrypal.grocerytracker.model.unit.Liter;

import java.time.LocalDate;
import java.util.List;

public class TestModels {
    // IDs
    public static final Long ID_1 = 1L;
    public static final Long ID_2 = 2L;
    public static final Long ID_3 = 3L;

    // Product names
    public static final String PRODUCT_NAME_MILK = "Milk";
    public static final String PRODUCT_NAME_BUTTER = "Butter";

    // Amounts
    public static final double AMOUNT_2_POINT_5 = 2.5;
    public static final double AMOUNT_300 = 300.0;
    public static final double AMOUNT_500 = 500.0;

    // Dates
    public static final LocalDate DATE_NOW = LocalDate.now();
    public static final LocalDate DATE_DAY_AFTER_NOW = LocalDate.now().plusDays(1);
    public static final LocalDate DATE_WEEK_AFTER_NOW = LocalDate.now().plusWeeks(1);
    public static final LocalDate DATE_MONTH_AFTER_NOW = LocalDate.now().plusMonths(1);
    public static final LocalDate DATE_YEAR_AFTER_NOW = LocalDate.now().plusYears(1);

    public static Product getMilkProduct() {
        Product product = new Product(PRODUCT_NAME_MILK);
        product.setId(ID_1);
        return product;
    }

    public static Product getButterProduct() {
        Product product = new Product(PRODUCT_NAME_BUTTER);
        product.setId(ID_2);
        return product;
    }

    public static GroceryItem getMilkGroceryItem() {
        GroceryItem groceryItem = new GroceryItem();
        groceryItem.setProduct(getMilkProduct());
        groceryItem.setId(ID_1);
        groceryItem.setAmount(AMOUNT_2_POINT_5);
        groceryItem.setUnit(new Liter());
        groceryItem.setPurchasedDate(DATE_NOW);
        groceryItem.setExpirationDate(DATE_WEEK_AFTER_NOW);
        return groceryItem;
    }

    public static GroceryItemDto getMilkGroceryItemDto() {
        GroceryItemDto groceryItemDto = new GroceryItemDto();
        groceryItemDto.setId(ID_1);
        groceryItemDto.setName(PRODUCT_NAME_MILK);
        groceryItemDto.setAmount(AMOUNT_2_POINT_5);
        groceryItemDto.setUnit(new Liter());
        groceryItemDto.setPurchasedDate(DATE_NOW);
        groceryItemDto.setExpirationDate(DATE_WEEK_AFTER_NOW);
        return groceryItemDto;
    }

    public static GroceryItem getButterGroceryItem() {
        GroceryItem groceryItem = new GroceryItem();
        groceryItem.setProduct(getButterProduct());
        groceryItem.setId(ID_2);
        groceryItem.setAmount(AMOUNT_500);
        groceryItem.setUnit(new Gram());
        groceryItem.setPurchasedDate(DATE_DAY_AFTER_NOW);
        groceryItem.setExpirationDate(DATE_YEAR_AFTER_NOW);
        return groceryItem;
    }

    public static GroceryItemDto getButterGroceryItemDto() {
        GroceryItemDto groceryItemDto = new GroceryItemDto();
        groceryItemDto.setId(ID_2);
        groceryItemDto.setName(PRODUCT_NAME_BUTTER);
        groceryItemDto.setAmount(AMOUNT_500);
        groceryItemDto.setUnit(new Gram());
        groceryItemDto.setPurchasedDate(DATE_DAY_AFTER_NOW);
        groceryItemDto.setExpirationDate(DATE_YEAR_AFTER_NOW);
        return groceryItemDto;
    }

    public static List<GroceryItem> getListOfTwoGroceryItems() {
        return List.of(getMilkGroceryItem(), getButterGroceryItem());
    }

    public static List<GroceryItemDto> getListOfTwoGroceryItemDtos() {
        return List.of(getMilkGroceryItemDto(), getButterGroceryItemDto());
    }

    public static PantryItem getMilkPantryItem() {
        PantryItem pantryItem = new PantryItem();
        GroceryItem groceryItem = getMilkGroceryItem();
        pantryItem.setGroceryItem(groceryItem);
        pantryItem.setId(ID_3);
        pantryItem.setQuantityInStock(groceryItem.getAmount());
        return pantryItem;
    }
}
