package com.pantrypal.grocerytracker.constants;

public class Constants {
    private Constants() {}

    // Base path for the grocery item API
    public static final String GROCERY_ITEM_API_BASE_PATH = "/api/grocery-items";
    public static final String PANTRY_ITEM_API_BASE_PATH = "/api/pantry-items";
    public static final String AUTH_API_BASE_PATH = "/api/auth";

    // Table names
    public static final String PRODUCT_TABLE_NAME = "products";
    public static final String GROCERY_ITEM_TABLE_NAME = "grocery_items";
    public static final String PANTRY_ITEM_TABLE_NAME = "pantry_items";
    public static final String USER_TABLE_NAME = "users";

    // Table column names
    public static final String PRODUCT_TABLE_ID_NAME = "product_id";
    public static final String GROCERY_ITEM_TABLE_ID_NAME = "grocery_item_id";

    // Alive endpoint path
    public static final String ALIVE_ENDPOINT_PATH = "/alive";

    // Sub-paths for CRUD operations - grocery item API
    public static final String GROCERY_ITEM_API_GET_ALL = "";
    public static final String GROCERY_ITEM_API_GET_BY_ID = "/{id}";
    public static final String GROCERY_ITEM_API_CREATE = "";
    public static final String GROCERY_ITEM_API_UPDATE = "/{id}";
    public static final String GROCERY_ITEM_API_DELETE = "/{id}";

    // Sub-paths for CRUD operations - pantry item API
    public static final String PANTRY_ITEM_API_GET_ALL = "";
    public static final String PANTRY_ITEM_API_GET_BY_ID = "/{id}";
    public static final String PANTRY_ITEM_API_MODIFY = "/{id}";

    // Sub-paths for auth API
    public static final String AUTH_API_LOGIN = "/login";
    public static final String AUTH_API_REGISTER = "/register";

    // Success messages
    public static final String SUCCESS_MESSAGE_GROCERY_ITEM_CREATED = "Grocery item created successfully";
    public static final String SUCCESS_MESSAGE_GROCERY_ITEM_UPDATED = "Grocery item updated successfully";
    public static final String SUCCESS_MESSAGE_GROCERY_ITEM_DELETED = "Grocery item deleted successfully";

    // JSON formats
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    // Constants for Unit
    public static final String UNIT_GRAM = "gram";
    public static final String UNIT_MILLILITER = "milliliter";
    public static final String UNIT_LITER = "liter";

    // Error messages
    public static final String ERROR_MESSAGE_PANTRY_ITEM_NOT_FOUND = "Pantry item not found";
    public static final String ERROR_MESSAGE_PANTRY_ITEM_NOT_FOUND_WITH_ID =
            ERROR_MESSAGE_PANTRY_ITEM_NOT_FOUND + " with ID: ";
    public static final String ERROR_MESSAGE_PANTRY_ITEM_NOT_FOUND_WITH_GROCERY_ITEM_ID =
            ERROR_MESSAGE_PANTRY_ITEM_NOT_FOUND + " with grocery item ID: ";
    public static final String ERROR_MESSAGE_INVALID_GROCERY_ITEM_REQUEST = "Invalid grocery item request";
    public static final String ERROR_MESSAGE_INSUFFICIENT_QUANTITY = "Insufficient quantity to modify";

    public static final String ILLEGAL_ARGUMENT_EXCEPTION_UNKNOWN_UNIT_STRING = "Unknown unit string: ";

    // Alive message
    public static final String GROCERY_ITEM_ALIVE_MESSAGE = "PantryPalBackend (GroceryItem) is alive!";
    public static final String PANTRY_ITEM_ALIVE_MESSAGE = "PantryPalBackend (PantryItem) is alive!";

}
