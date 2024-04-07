package com.cropx.academymatch

data class Match(
    val uuid: String,
    val userGeneralData: UserGeneralData,
    var personalPreferences: PersonalPreferences? = null,
    var matchPercentage: Int? = null,
    var matches: ArrayList<Pair<String, Int>> = arrayListOf()
)

data class UserGeneralData(
    val fullName: String,
    val email: String,
    val password: String,
    val universityName: String,
    val faculty: String,
    val gender: Gender,
    val coursesNumber: List<Int>,
    val imgUrl: String? = null
)

data class PersonalPreferences(
    val studyPreference: StudyPreference,
    val workDays: List<WorkDay>,
    val workType: WorkType,
    val studyTarget: StudyTarget,
    val mainLanguage: Language,
    val partnerGenderPreference: Gender,
    val description: String,
)

enum class Gender {
    Male, Female, No_Preference
}

enum class StudyPreference {
    Frontal, Remote, No_Preference
}

enum class WorkType {
    Together, Split
}

enum class StudyTarget {
    High, Medium, Low
}

enum class Language {
    Hebrew, English, Arabic
}

enum class WorkDay {
    Sunday, Monday, Tuesday, Wednesday, Thursday, Friday
}