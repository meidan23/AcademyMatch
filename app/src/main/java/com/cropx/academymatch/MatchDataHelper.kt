package com.cropx.academymatch

object MatchDataHelper {

    fun generatePossibleMatches(courseNumber: Int): List<Match> {
        return usersList.filter {
            it.userGeneralData.coursesNumber.contains(courseNumber)
        }.map { potentialMatch ->
            val matchPercentage = calculateMatchPercentage(potentialMatch)

            potentialMatch.matchPercentage = matchPercentage.toInt()
            potentialMatch
        }.sortedByDescending { it.matchPercentage }
    }

    fun getLinkedMatches(): List<Match> {
        return UserDataService.currentUser?.matches!!.map { match ->
            usersList.find { it.uuid == match.first }!!
        }.distinct()
    }

    fun isLinkedByCourseNumber(courseNumber: Int? = null, matchUuid: String): Boolean {
        if (courseNumber == null) {
            return true
        }
        val matchesList = UserDataService.currentUser!!.matches.filter {
            it.second == courseNumber
        }
        return matchesList.contains(Pair(matchUuid, courseNumber))
    }

    private fun calculateMatchPercentage(
        match: Match
    ): Double {
        var score = 0.0
        val maxScore = 6.0 // Adjust based on the number of criteria you evaluate

        // Study Preference (simple equality check)
        if (UserDataService.currentUser!!.personalPreferences!!.studyPreference == match.personalPreferences!!.studyPreference) score += 1

        // Work Days (intersection over union)
        val commonDays = UserDataService.currentUser!!.personalPreferences!!.workDays.intersect(
            match.personalPreferences!!.workDays.toSet()
        ).size.toDouble()
        val totalDays =
            UserDataService.currentUser!!.personalPreferences!!.workDays.union(match.personalPreferences!!.workDays).size.toDouble()
        score += commonDays / totalDays // This gives a fractional score based on overlap

        // Work Type (simple equality check)
        if (UserDataService.currentUser!!.personalPreferences!!.workType == match.personalPreferences!!.workType) score += 1

        // Study Target (simple equality check)
        if (UserDataService.currentUser!!.personalPreferences!!.studyTarget == match.personalPreferences!!.studyTarget) score += 1

        // Main Language (simple equality check)
        if (UserDataService.currentUser!!.personalPreferences!!.mainLanguage == match.personalPreferences!!.mainLanguage) score += 1

        // Partner Gender Preference (simple equality check)
        if (UserDataService.currentUser!!.personalPreferences!!.partnerGenderPreference == match.personalPreferences!!.partnerGenderPreference) score += 1

        // Calculate the percentage
        return (score / maxScore) * 100
    }

    private val usersList = listOf(
        Match(
            uuid = "Uudsfgidfgod", userGeneralData = UserGeneralData(
                fullName = "Marvin",
                email = "Tyler",
                password = "Markeshia",
                universityName = "Monisha",
                faculty = "Jenise",
                gender = Gender.Female,
                coursesNumber = listOf(7),
                imgUrl = "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?q=80&w=3270&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            ), personalPreferences = PersonalPreferences(
                studyPreference = StudyPreference.Frontal,
                workDays = listOf(WorkDay.Monday, WorkDay.Wednesday),
                workType = WorkType.Split,
                studyTarget = StudyTarget.Medium,
                mainLanguage = Language.Hebrew,
                partnerGenderPreference = Gender.Male,
                description = "I am very friendly and love to study",
            )

        ), Match(
            uuid = "sddfgfgb", userGeneralData = UserGeneralData(
                fullName = "Fatima",
                email = "Swisaa",
                password = "Markeshia",
                universityName = "Harvard",
                faculty = "Madima",
                gender = Gender.Female,
                coursesNumber = listOf(8),
                imgUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fstock.adobe.com%2Fsearch%3Fk%3Dperson&psig=AOvVaw0cAScRN1mp8afFcmKLPlWj&ust=1711876517611000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCNiExo_Tm4UDFQAAAAAdAAAAABAE"
            ), personalPreferences = PersonalPreferences(
                studyPreference = StudyPreference.Remote,
                workDays = listOf(WorkDay.Thursday, WorkDay.Monday, WorkDay.Friday),
                workType = WorkType.Split,
                studyTarget = StudyTarget.High,
                mainLanguage = Language.Arabic,
                partnerGenderPreference = Gender.Female,
                description = "Ronni",
            )
        ), Match(
            uuid = "ertert", userGeneralData = UserGeneralData(
                fullName = "John",
                email = "john@example.com",
                password = "password",
                universityName = "University of Something",
                faculty = "Computer Science",
                gender = Gender.Male,
                coursesNumber = listOf(9, 7),
                imgUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fstock.adobe.com%2Fsearch%3Fk%3Dperson&psig=AOvVaw0cAScRN1mp8afFcmKLPlWj&ust=1711876517611000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCNiExo_Tm4UDFQAAAAAdAAAAABAE"
            ), personalPreferences = PersonalPreferences(
                studyPreference = StudyPreference.No_Preference,
                workDays = listOf(
                    WorkDay.Monday, WorkDay.Wednesday, WorkDay.Friday, WorkDay.Tuesday
                ),
                workType = WorkType.Split,
                studyTarget = StudyTarget.Low,
                mainLanguage = Language.English,
                partnerGenderPreference = Gender.Male,
                description = "Megin",
            )
        ), Match(
            uuid = "qewrwer", userGeneralData = UserGeneralData(
                fullName = "Yasu",
                email = "john@example.com",
                password = "password",
                universityName = "University of Something",
                faculty = "Computer Science",
                gender = Gender.Male,
                coursesNumber = listOf(9, 7),
                imgUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fstock.adobe.com%2Fsearch%3Fk%3Dperson&psig=AOvVaw0cAScRN1mp8afFcmKLPlWj&ust=1711876517611000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCNiExo_Tm4UDFQAAAAAdAAAAABAE"
            ), personalPreferences = PersonalPreferences(
                studyPreference = StudyPreference.Remote,
                workDays = listOf(
                    WorkDay.Monday, WorkDay.Wednesday
                ),
                workType = WorkType.Split,
                studyTarget = StudyTarget.Low,
                mainLanguage = Language.Arabic,
                partnerGenderPreference = Gender.Female,
                description = "Megin",
            )
        ), Match(
            uuid = "qeweytjtyjrwer", userGeneralData = UserGeneralData(
                fullName = "Momo",
                email = "john@example.com",
                password = "password",
                universityName = "University of Something",
                faculty = "Computer Science",
                gender = Gender.Male,
                coursesNumber = listOf(9, 7),
                imgUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fstock.adobe.com%2Fsearch%3Fk%3Dperson&psig=AOvVaw0cAScRN1mp8afFcmKLPlWj&ust=1711876517611000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCNiExo_Tm4UDFQAAAAAdAAAAABAE"
            ), personalPreferences = PersonalPreferences(
                studyPreference = StudyPreference.Remote,
                workDays = listOf(
                    WorkDay.Monday, WorkDay.Wednesday, WorkDay.Friday, WorkDay.Tuesday
                ),
                workType = WorkType.Together,
                studyTarget = StudyTarget.Medium,
                mainLanguage = Language.English,
                partnerGenderPreference = Gender.Male,
                description = "Megin",
            )
        ), Match(
            uuid = "iojotymomt", userGeneralData = UserGeneralData(
                fullName = "Toto",
                email = "john@example.com",
                password = "password",
                universityName = "University of Something",
                faculty = "Computer Science",
                gender = Gender.Male,
                coursesNumber = listOf(9, 7),
                imgUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fstock.adobe.com%2Fsearch%3Fk%3Dperson&psig=AOvVaw0cAScRN1mp8afFcmKLPlWj&ust=1711876517611000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCNiExo_Tm4UDFQAAAAAdAAAAABAE"
            ), personalPreferences = PersonalPreferences(
                studyPreference = StudyPreference.Remote,
                workDays = listOf(
                    WorkDay.Wednesday, WorkDay.Friday, WorkDay.Tuesday
                ),
                workType = WorkType.Split,
                studyTarget = StudyTarget.Low,
                mainLanguage = Language.English,
                partnerGenderPreference = Gender.Male,
                description = "Megin",
            )
        )
    )
}
