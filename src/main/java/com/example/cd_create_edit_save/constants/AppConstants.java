package com.example.cd_create_edit_save.constants;

/**
 * Centralized application constants.
 */
public final class AppConstants {

	public static final String PRODUCT_API_BASE_PATH = "/api/v1/products";

	public static final String PRODUCT_SHORT_CODES = "/product-short-codes";
	public static final String API_V1_PRODUCT_SHORT_CODES = PRODUCT_API_BASE_PATH + PRODUCT_SHORT_CODES;


	public static final String FEE_TYPE_SHORT_CODES = "/fee-type-short-codes";
	public static final String API_V1_FEE_TYPE_SHORT_CODES = PRODUCT_API_BASE_PATH + FEE_TYPE_SHORT_CODES;

	public static final String REWARDS_TYPE_SHORT_CODES = "/rewards-type-short-codes";
	public static final String API_V1_REWARDS_TYPE_SHORT_CODES = PRODUCT_API_BASE_PATH + REWARDS_TYPE_SHORT_CODES;

	public static final String PRIN_CODES = "/prin-codes";
	public static final String API_V1_PRIN_CODES = PRODUCT_API_BASE_PATH + PRIN_CODES;

	public static final String CHA_CODES = "/cha-codes";
	public static final String API_V1_CHA_CODES = PRODUCT_API_BASE_PATH + CHA_CODES;

	public static final String CWS_PROD_CODES = "/cws-prod-codes";
	public static final String API_V1_CWS_PROD = PRODUCT_API_BASE_PATH + CWS_PROD_CODES;

	public static final String USERS = "/users";
	public static final String API_V1_USERS= PRODUCT_API_BASE_PATH + USERS;

	public static final String GET_PRODUCTS = "/products" ;
	public static final String GET_PRODUCTS_BY_PARAMETER = "/products-by-parameter";
	public static final String EXPORT_PRODUCTS = "/export-products";

   public static final Long MAX_LIMIT = 1000L;
   public static final Long MIN_OFFSET = 0L;

	private AppConstants() {
	}
}
