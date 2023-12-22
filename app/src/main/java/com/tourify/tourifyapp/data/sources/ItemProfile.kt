package com.tourify.tourifyapp.data.sources

import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.model.ModelItemProfile

object ItemProfile {
    val data = listOf(
        ModelItemProfile(
            id = 1,
            icon = R.drawable.ic_user,
            label = R.string.my_account,
            desc = R.string.make_changes_to_your_account,
        ),
        ModelItemProfile(
            id = 2,
            icon = R.drawable.ic_wallet,
            label = R.string.payment_options,
            desc = R.string.make_changes_to_your_account,
        ),
        ModelItemProfile(
            id = 3,
            icon = R.drawable.ic_key,
            label = R.string.change_password,
            desc = R.string.manage_device_security
        ),
        ModelItemProfile(
            id = 4,
            icon = R.drawable.ic_globe,
            label = R.string.language,
            desc = R.string.choose_your_preferred_language,
        ),
        ModelItemProfile(
            id = 5,
            icon = R.drawable.ic_logout,
            label = R.string.logout,
            desc = R.string.secure_your_account_further,
        ),
        ModelItemProfile(
            id = 6,
            icon = R.drawable.ic_bell2,
            label = R.string.help_support,
            desc = R.string.can_we_help_you,
        ),
        ModelItemProfile(
            id = 7,
            icon = R.drawable.ic_heart,
            label = R.string.about_the_app,
            desc = R.string.discover_information_about_this_app,
        )
    )
}

