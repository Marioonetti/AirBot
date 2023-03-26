package com.airbot.domain.model

import java.util.*

data class MessageAirBot(
  val role: String,
  val content: String,
  var time: Long = Calendar.getInstance().timeInMillis

)