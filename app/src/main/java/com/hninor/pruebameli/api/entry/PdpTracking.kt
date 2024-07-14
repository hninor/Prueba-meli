package com.hninor.pruebameli.api.entry

import com.google.gson.annotations.SerializedName


data class PdpTracking (

  @SerializedName("group"        ) var group       : Boolean?          = null,
  @SerializedName("product_info" ) var productInfo : ArrayList<String> = arrayListOf()

)