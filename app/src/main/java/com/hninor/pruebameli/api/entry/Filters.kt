package com.hninor.pruebameli.api.entry

import com.google.gson.annotations.SerializedName


data class Filters (

  @SerializedName("id"     ) var id     : String?           = null,
  @SerializedName("name"   ) var name   : String?           = null,
  @SerializedName("type"   ) var type   : String?           = null,
  @SerializedName("values" ) var values : ArrayList<Values> = arrayListOf()

)