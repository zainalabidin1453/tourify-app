package com.tourify.tourifyapp.data.sources

import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.constants.Constants
import com.tourify.tourifyapp.model.ModelWisataCategory
import com.tourify.tourifyapp.ui.theme.ColorBlue
import com.tourify.tourifyapp.ui.theme.ColorBlueLight
import com.tourify.tourifyapp.ui.theme.ColorChocolate
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorGreen
import com.tourify.tourifyapp.ui.theme.ColorMaroon
import com.tourify.tourifyapp.ui.theme.ColorOrange

object ItemWisataCategory {
    val dataCategory = listOf(
        ModelWisataCategory(
            1, R.drawable.ic_beach,
            R.string.category_beach,
            Constants.CATEGORY_BEACH,
            ColorBlue
        ),
        ModelWisataCategory(
            2,
            R.drawable.ic_lake,
            R.string.category_lake,
            Constants.CATEGORY_LAKE,
            ColorBlueLight
        ),
        ModelWisataCategory(
            3,
            R.drawable.ic_waterfall,
            R.string.category_waterfall,
            Constants.CATEGORY_WATERFALL,
            ColorOrange
        ),
        ModelWisataCategory(
            5,
            R.drawable.ic_natural,
            R.string.category_natural,
            Constants.CATEGORY_NATURAL,
            ColorGreen
        ),
        ModelWisataCategory(
            6,
            R.drawable.ic_museum,
            R.string.category_museum,
            Constants.CATEGORY_MUSEUM,
            ColorChocolate
        ),
        ModelWisataCategory(
            7,
            R.drawable.ic_historical,
            R.string.category_historical,
            Constants.CATEGORY_HISTORICAL,
            ColorMaroon
        ),
        ModelWisataCategory(
            8,
            R.drawable.ic_landmark,
            R.string.category_landmark,
            Constants.CATEGORY_LANDMARK,
            ColorDanger
        )
    )
}
