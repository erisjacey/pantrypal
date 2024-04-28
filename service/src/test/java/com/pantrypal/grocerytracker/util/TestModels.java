package com.pantrypal.grocerytracker.util;

import com.pantrypal.grocerytracker.dto.GroceryItemDto;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.model.Product;
import com.pantrypal.grocerytracker.model.unit.Gram;
import com.pantrypal.grocerytracker.model.unit.Liter;

import java.time.LocalDate;
import java.util.List;

public class TestModels {
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
        return new Product(PRODUCT_NAME_MILK);
    }

    public static Product getButterProduct() {
        return new Product(PRODUCT_NAME_BUTTER);
    }

    public static GroceryItem getMilkGroceryItem() {
        GroceryItem groceryItem = new GroceryItem();
        groceryItem.setProduct(getMilkProduct());
        groceryItem.setAmount(AMOUNT_2_POINT_5);
        groceryItem.setUnit(new Liter());
        groceryItem.setPurchasedDate(DATE_NOW);
        groceryItem.setExpirationDate(DATE_WEEK_AFTER_NOW);
        return groceryItem;
    }

    public static GroceryItemDto getMilkGroceryItemDto() {
        GroceryItemDto groceryItemDto = new GroceryItemDto();
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
        groceryItem.setAmount(AMOUNT_500);
        groceryItem.setUnit(new Gram());
        groceryItem.setPurchasedDate(DATE_DAY_AFTER_NOW);
        groceryItem.setExpirationDate(DATE_YEAR_AFTER_NOW);
        return groceryItem;
    }

    public static GroceryItemDto getButterGroceryItemDto() {
        GroceryItemDto groceryItemDto = new GroceryItemDto();
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
}
