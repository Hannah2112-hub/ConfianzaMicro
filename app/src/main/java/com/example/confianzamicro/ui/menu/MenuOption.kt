package com.example.confianzamicro.ui.menu

import kotlin.reflect.KClass

data class MenuOption(
    val title: String,
    val iconRes: Int,
    val target: KClass<*>
)
