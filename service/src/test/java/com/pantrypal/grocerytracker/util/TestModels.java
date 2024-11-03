package com.pantrypal.grocerytracker.util;

import com.pantrypal.grocerytracker.dto.GroceryItemDto;
import com.pantrypal.grocerytracker.dto.ModifyAmountRequest;
import com.pantrypal.grocerytracker.dto.PantryItemDto;
import com.pantrypal.grocerytracker.model.GroceryItem;
import com.pantrypal.grocerytracker.model.PantryItem;
import com.pantrypal.grocerytracker.model.Product;
import com.pantrypal.grocerytracker.model.User;
import com.pantrypal.grocerytracker.model.enums.GroceryType;
import com.pantrypal.grocerytracker.model.unit.Gram;
import com.pantrypal.grocerytracker.model.unit.Liter;
import com.pantrypal.grocerytracker.model.unit.Milliliter;

import java.time.LocalDate;
import java.util.List;

public class TestModels {
    // IDs
    public static final Long ID_1 = 1L;
    public static final Long ID_2 = 2L;
    public static final Long ID_3 = 3L;
    public static final Long ID_4 = 4L;

    // Product names
    public static final String PRODUCT_NAME_MILK = "Milk";
    public static final String PRODUCT_NAME_BUTTER = "Butter";

    // User credentials
    public static final String USER_USERNAME = "username";
    public static final String USER_EMAIL = "email@mail.com";
    public static final String USER_PASSWORD = "pass123";

    // Amounts
    public static final double AMOUNT_0_POINT_1 = 0.1;
    public static final double AMOUNT_2_POINT_3 = 2.3;
    public static final double AMOUNT_2_POINT_5 = 2.5;
    public static final double AMOUNT_200 = 200.0;
    public static final double AMOUNT_300 = 300.0;
    public static final double AMOUNT_500 = 500.0;

    // Dates
    public static final LocalDate DATE_NOW = LocalDate.now();
    public static final LocalDate DATE_DAY_AFTER_NOW = LocalDate.now().plusDays(1);
    public static final LocalDate DATE_WEEK_AFTER_NOW = LocalDate.now().plusWeeks(1);
    public static final LocalDate DATE_MONTH_AFTER_NOW = LocalDate.now().plusMonths(1);
    public static final LocalDate DATE_YEAR_AFTER_NOW = LocalDate.now().plusYears(1);

    public static User getUser() {
        User user = new User();
        user.setId(ID_1);
        user.setUsername(USER_USERNAME);
        user.setEmail(USER_EMAIL);
        user.setPassword(USER_PASSWORD);
        return user;
    }

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
        groceryItem.setUser(getUser());
        groceryItem.setProduct(getMilkProduct());
        groceryItem.setId(ID_1);
        groceryItem.setAmount(AMOUNT_2_POINT_5);
        groceryItem.setUnit(new Liter());
        groceryItem.setPurchasedDate(DATE_NOW);
        groceryItem.setExpirationDate(DATE_WEEK_AFTER_NOW);
        groceryItem.setGroceryType(GroceryType.DAIRY);
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
        groceryItemDto.setGroceryType(GroceryType.DAIRY);
        return groceryItemDto;
    }

    public static GroceryItem getButterGroceryItem() {
        GroceryItem groceryItem = new GroceryItem();
        groceryItem.setUser(getUser());
        groceryItem.setProduct(getButterProduct());
        groceryItem.setId(ID_2);
        groceryItem.setAmount(AMOUNT_500);
        groceryItem.setUnit(new Gram());
        groceryItem.setPurchasedDate(DATE_DAY_AFTER_NOW);
        groceryItem.setExpirationDate(DATE_YEAR_AFTER_NOW);
        groceryItem.setGroceryType(GroceryType.DAIRY);
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
        groceryItemDto.setGroceryType(GroceryType.DAIRY);
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
        pantryItem.setGroceryItem(getMilkGroceryItem());
        pantryItem.setUser(getUser());
        pantryItem.setId(ID_3);
        pantryItem.setQuantityInStock(AMOUNT_2_POINT_5);
        return pantryItem;
    }

    public static PantryItemDto getMilkPantryItemDto() {
        PantryItemDto pantryItemDto = new PantryItemDto();
        pantryItemDto.setId(ID_3);
        pantryItemDto.setName(PRODUCT_NAME_MILK);
        pantryItemDto.setInitialAmount(AMOUNT_2_POINT_5);
        pantryItemDto.setCurrentAmount(AMOUNT_2_POINT_5);
        pantryItemDto.setUnit(new Liter());
        pantryItemDto.setPurchasedDate(DATE_NOW);
        pantryItemDto.setExpirationDate(DATE_WEEK_AFTER_NOW);
        pantryItemDto.setGroceryType(GroceryType.DAIRY);
        return pantryItemDto;
    }

    public static PantryItem getButterPantryItem() {
        PantryItem pantryItem = new PantryItem();
        pantryItem.setGroceryItem(getButterGroceryItem());
        pantryItem.setUser(getUser());
        pantryItem.setId(ID_4);
        pantryItem.setQuantityInStock(AMOUNT_500);
        return pantryItem;
    }

    public static PantryItemDto getButterPantryItemDto() {
        PantryItemDto pantryItemDto = new PantryItemDto();
        pantryItemDto.setId(ID_4);
        pantryItemDto.setName(PRODUCT_NAME_BUTTER);
        pantryItemDto.setInitialAmount(AMOUNT_500);
        pantryItemDto.setCurrentAmount(AMOUNT_500);
        pantryItemDto.setUnit(new Gram());
        pantryItemDto.setPurchasedDate(DATE_DAY_AFTER_NOW);
        pantryItemDto.setExpirationDate(DATE_YEAR_AFTER_NOW);
        pantryItemDto.setGroceryType(GroceryType.DAIRY);
        return pantryItemDto;
    }

    public static List<PantryItem> getListOfTwoPantryItems() {
        return List.of(getMilkPantryItem(), getButterPantryItem());
    }

    public static List<PantryItemDto> getListOfTwoPantryItemDtos() {
        return List.of(getMilkPantryItemDto(), getButterPantryItemDto());
    }

    public static ModifyAmountRequest getModifyAmountRequest() {
        ModifyAmountRequest request = new ModifyAmountRequest();
        request.setAmount(AMOUNT_200);
        request.setUnit(new Milliliter());
        return request;
    }
}
