package com.tourify.tourifyapp.data.sources

import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.constants.Constants
import com.tourify.tourifyapp.model.WisataCategoryModel
import com.tourify.tourifyapp.ui.theme.ColorBlue
import com.tourify.tourifyapp.ui.theme.ColorBlueLight
import com.tourify.tourifyapp.ui.theme.ColorChocolate
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorGreen
import com.tourify.tourifyapp.ui.theme.ColorMaroon
import com.tourify.tourifyapp.ui.theme.ColorOrange

object ItemWisataCategory {
    val dataCategory = listOf(
        WisataCategoryModel(
            1, R.drawable.ic_beach,
            R.string.category_beach,
            Constants.CATEGORY_BEACH,
            ColorBlue
        ),
        WisataCategoryModel(
            2,
            R.drawable.ic_lake,
            R.string.category_lake,
            Constants.CATEGORY_LAKE,
            ColorBlueLight
        ),
        WisataCategoryModel(
            3,
            R.drawable.ic_waterfall,
            R.string.category_waterfall,
            Constants.CATEGORY_WATERFALL,
            ColorOrange
        ),
        WisataCategoryModel(
            5,
            R.drawable.ic_natural,
            R.string.category_natural,
            Constants.CATEGORY_NATURAL,
            ColorGreen
        ),
        WisataCategoryModel(
            6,
            R.drawable.ic_museum,
            R.string.category_museum,
            Constants.CATEGORY_MUSEUM,
            ColorChocolate
        ),
        WisataCategoryModel(
            7,
            R.drawable.ic_historical,
            R.string.category_historical,
            Constants.CATEGORY_HISTORICAL,
            ColorMaroon
        ),
        WisataCategoryModel(
            8,
            R.drawable.ic_landmark,
            R.string.category_landmark,
            Constants.CATEGORY_LANDMARK,
            ColorDanger
        )
    )
}
