package com.tourify.tourifyapp.data.sources

import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.constants.Constants
import com.tourify.tourifyapp.constants.Constants.CATEGORY_ADVENTURE
import com.tourify.tourifyapp.constants.Constants.CATEGORY_EDUCATION_CULTURE
import com.tourify.tourifyapp.constants.Constants.CATEGORY_FAMILY_VACATION
import com.tourify.tourifyapp.constants.Constants.CATEGORY_HONEYMOON
import com.tourify.tourifyapp.constants.Constants.CATEGORY_ICONIC_SIGHT
import com.tourify.tourifyapp.constants.Constants.CATEGORY_PHOTOGRAPHY_VIDEOGRAPHY
import com.tourify.tourifyapp.constants.Constants.CATEGORY_RECREATION
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
            1, R.drawable.ic_natural,
            CATEGORY_ADVENTURE,
            ColorBlue
        ),
        ModelWisataCategory(
            2,
            R.drawable.ic_beach,
            CATEGORY_FAMILY_VACATION,
            ColorBlueLight
        ),
        ModelWisataCategory(
            3,
            R.drawable.ic_sunset_sea,
            CATEGORY_HONEYMOON,
            ColorOrange
        ),
        ModelWisataCategory(
            4,
            R.drawable.ic_museum,
            CATEGORY_EDUCATION_CULTURE,
            ColorGreen
        ),
        ModelWisataCategory(
            5,
            R.drawable.ic_photography_lens,
            CATEGORY_PHOTOGRAPHY_VIDEOGRAPHY,
            ColorChocolate
        ),
        ModelWisataCategory(
            6,
            R.drawable.ic_historical,
            CATEGORY_RECREATION,
            ColorMaroon
        ),
        ModelWisataCategory(
            7,
            R.drawable.ic_landmark,
            CATEGORY_ICONIC_SIGHT,
            ColorDanger
        )
    )
}
