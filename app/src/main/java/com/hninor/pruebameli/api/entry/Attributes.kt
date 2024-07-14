package com.hninor.pruebameli.api.entry

import com.google.gson.annotations.SerializedName


data class Attributes (

  @SerializedName("id"         ) var id        : String? = null,
  @SerializedName("name"       ) var name      : String? = null,
  @SerializedName("value_name" ) var valueName : String? = null,
  @SerializedName("value_type" ) var valueType : String? = null

)