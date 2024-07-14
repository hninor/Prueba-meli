package com.hninor.pruebameli.api.entry

import com.google.gson.annotations.SerializedName


data class Values (

  @SerializedName("id"      ) var id      : String? = null,
  @SerializedName("name"    ) var name    : String? = null,
  @SerializedName("results" ) var results : Int?    = null

)