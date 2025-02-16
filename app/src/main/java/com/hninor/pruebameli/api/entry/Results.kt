package com.hninor.pruebameli.api.entry

import com.google.gson.annotations.SerializedName


data class Results (

  @SerializedName("id"                  ) var id                 : String?               = null,
  @SerializedName("title"               ) var title              : String?               = null,
  @SerializedName("condition"           ) var condition          : String?               = null,
  @SerializedName("thumbnail_id"        ) var thumbnailId        : String?               = null,
  @SerializedName("catalog_product_id"  ) var catalogProductId   : String?               = null,
  @SerializedName("listing_type_id"     ) var listingTypeId      : String?               = null,
  @SerializedName("permalink"           ) var permalink          : String?               = null,
  @SerializedName("buying_mode"         ) var buyingMode         : String?               = null,
  @SerializedName("site_id"             ) var siteId             : String?               = null,
  @SerializedName("category_id"         ) var categoryId         : String?               = null,
  @SerializedName("domain_id"           ) var domainId           : String?               = null,
  @SerializedName("variation_id"        ) var variationId        : String?               = null,
  @SerializedName("thumbnail"           ) var thumbnail          : String?               = null,
  @SerializedName("currency_id"         ) var currencyId         : String?               = null,
  @SerializedName("order_backend"       ) var orderBackend       : Int?                  = null,
  @SerializedName("price"               ) var price              : Int?                  = null,
  @SerializedName("original_price"      ) var originalPrice      : String?               = null,
  @SerializedName("sale_price"          ) var salePrice          : String?               = null,
  @SerializedName("available_quantity"  ) var availableQuantity  : Int?                  = null,
  @SerializedName("official_store_id"   ) var officialStoreId    : String?               = null,
  @SerializedName("use_thumbnail_id"    ) var useThumbnailId     : Boolean?              = null,
  @SerializedName("accepts_mercadopago" ) var acceptsMercadopago : Boolean?              = null,
  @SerializedName("variation_filters"   ) var variationFilters   : ArrayList<String>     = arrayListOf(),
  @SerializedName("shipping"            ) var shipping           : Shipping?             = Shipping(),
  @SerializedName("stop_time"           ) var stopTime           : String?               = null,
  @SerializedName("seller"              ) var seller             : Seller?               = Seller(),
  @SerializedName("attributes"          ) var attributes         : ArrayList<Attributes> = arrayListOf(),
  @SerializedName("installments"        ) var installments       : Installments?         = Installments(),
  @SerializedName("winner_item_id"      ) var winnerItemId       : String?               = null,
  @SerializedName("catalog_listing"     ) var catalogListing     : Boolean?              = null,
  @SerializedName("discounts"           ) var discounts          : String?               = null,
  @SerializedName("promotions"          ) var promotions         : ArrayList<String>     = arrayListOf(),
  @SerializedName("inventory_id"        ) var inventoryId        : String?               = null

)