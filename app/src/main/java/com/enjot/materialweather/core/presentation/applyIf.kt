package com.enjot.materialweather.core.presentation

import androidx.compose.ui.Modifier


inline fun Modifier.applyIf(
    condition: Boolean,
    modifier: Modifier.() -> Modifier
) = if (condition) this.modifier() else this


inline fun Modifier.applyIfElse(
    condition: Boolean,
    modifierIf: Modifier.() -> Modifier,
    modifierElse: Modifier.() -> Modifier
) = if (condition) this.modifierIf() else this.modifierElse()