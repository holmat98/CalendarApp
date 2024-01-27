package com.mateuszholik.calendarapp.ui.provider

import androidx.annotation.StringRes
import com.mateuszholik.calendarapp.R
import com.mateuszholik.calendarapp.ui.provider.ColorsProvider.ColorInfo
import javax.inject.Inject

interface ColorsProvider {

    fun provide(): List<ColorInfo>

    data class ColorInfo(
        val value: Int,
        @StringRes val name: Int,
    )
}

class ColorsProviderImpl @Inject constructor() : ColorsProvider {

    override fun provide(): List<ColorInfo> =
        listOf(
            ColorInfo(-1331, R.string.color_lemon_chiffon),
            ColorInfo(-5171, R.string.color_blanched_almond),
            ColorInfo(-6987, R.string.color_moccasin),
            ColorInfo(-1466246, R.string.color_light_coral),
            ColorInfo(-23296, R.string.color_orange),
            ColorInfo(-38476, R.string.color_deep_pink),
            ColorInfo(-65281, R.string.color_magenta),
            ColorInfo(-16711936, R.string.color_lime),
            ColorInfo(-16711809, R.string.color_spring_green),
            ColorInfo(-5247250, R.string.color_pale_turquoise),
            ColorInfo(-10510688, R.string.color_blue_cadet),
        )
}
