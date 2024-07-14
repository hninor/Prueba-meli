package com.hninor.pruebameli.api.entry

import com.google.gson.annotations.SerializedName


data class Shipping (

  @SerializedName("store_pick_up"  ) var storePickUp   : Boolean?          = null,
  @SerializedName("free_shipping"  ) var freeShipping  : Boolean?          = null,
  @SerializedName("logistic_type"  ) var logisticType  : String?           = null,
  @SerializedName("mode"           ) var mode          : String?           = null,
  @SerializedName("tags"           ) var tags          : ArrayList<String> = arrayListOf(),
  @SerializedName("benefits"       ) var benefits      : String?           = null,
  @SerializedName("promise"        ) var promise       : String?           = null,
  @SerializedName("shipping_score" ) var shippingScore : Int?              = null

)