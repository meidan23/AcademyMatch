package com.cropx.academymatch

import java.util.UUID

object UserDataService {
    fun setUserGeneralData(userGeneralData: UserGeneralData) {
        currentUser = Match(UUID.randomUUID().toString(), userGeneralData = userGeneralData)
    }

    fun setPersonalPreferences(personalPreferences: PersonalPreferences) {
        currentUser?.personalPreferences = personalPreferences
    }

    var currentUser: Match? = null
}