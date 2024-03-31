package com.pantrypal.grocerytracker.constants;

public class Constants {
    private Constants() {}

    // Base path for the grocery item API
    public static final String GROCERY_ITEM_API_BASE_PATH = "/api/grocery-items";

    // Alive endpoint path
    public static final String ALIVE_ENDPOINT_PATH = "/alive";

    // Subpaths for CRUD operations
    public static final String GROCERY_ITEM_API_GET_ALL = "";
    public static final String GROCERY_ITEM_API_GET_BY_ID = "/{id}";
    public static final String GROCERY_ITEM_API_CREATE = "";
    public static final String GROCERY_ITEM_API_UPDATE = "/{id}";
    public static final String GROCERY_ITEM_API_DELETE_ALL = "";
    public static final String GROCERY_ITEM_API_DELETE = "/{id}";

    // Error messages
    public static final String ERROR_MESSAGE_GROCERY_ITEM_NOT_FOUND = "Grocery item not found";
    public static final String ERROR_MESSAGE_INVALID_GROCERY_ITEM_REQUEST = "Invalid grocery item request";

    // Success messages
    public static final String SUCCESS_MESSAGE_GROCERY_ITEM_CREATED = "Grocery item created successfully";
    public static final String SUCCESS_MESSAGE_GROCERY_ITEM_UPDATED = "Grocery item updated successfully";
    public static final String SUCCESS_MESSAGE_GROCERY_ITEM_DELETED = "Grocery item deleted successfully";

    // Constants for UnitSerializer and UnitDeserializer
    public static final String UNIT_GRAM = "gram";
    public static final String UNIT_MILLILITER = "milliliter";
    public static final String UNIT_LITER = "liter";

    // Constants for IOException messages
    public static final String IO_EXCEPTION_UNKNOWN_UNIT_TYPE = "Unknown unit type: ";
    public static final String IO_EXCEPTION_DESERIALIZING_UNIT = "Error deserializing Unit";

    // Alive message
    public static final String ALIVE_MESSAGE = "PantryPalBackend (GroceryItem) is alive!";

}
