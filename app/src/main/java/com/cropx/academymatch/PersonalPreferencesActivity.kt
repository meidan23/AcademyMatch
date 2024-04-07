package com.cropx.academymatch

import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.cropx.academymatch.databinding.ActivityPersonalPreferencesBinding

class PersonalPreferencesActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPersonalPreferencesBinding.inflate(layoutInflater)
    }

    private var workDaysAvailability: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.genderRadioGroup.setOnCheckedChangeListener { _, _ ->
            setFormAvailability()
        }

        binding.genderRadioGroup.setOnCheckedChangeListener { _, _ ->
            setFormAvailability()
        }

        binding.spokenLanguagesRadioGroup.setOnCheckedChangeListener { _, _ ->
            setFormAvailability()
        }

        binding.studyTargetRadioGroup.setOnCheckedChangeListener { _, _ ->
            setFormAvailability()
        }

        binding.studyPreferenceRadioGroup.setOnCheckedChangeListener { _, _ ->
            setFormAvailability()
        }

        binding.selfDescriptionEditText.addTextChangedListener { setFormAvailability() }

        val workDaysCheckboxes = listOf(
            binding.workDaySunday,
            binding.workDayMonday,
            binding.workDayTuesday,
            binding.workDayWednesday,
            binding.workDayThursday,
            binding.workDayFriday,
        )

        val workDaysCheckChangeListener = { _: CompoundButton, _: Boolean ->
            workDaysAvailability = workDaysCheckboxes.any { it.isChecked }
            setFormAvailability()
        }
        workDaysCheckboxes.forEach { it.setOnCheckedChangeListener(workDaysCheckChangeListener) }

        binding.submitButton.setOnClickListener {
            val partnerGenderPreference = when (binding.genderRadioGroup.checkedRadioButtonId) {
                binding.maleRadioButton.id -> Gender.Male
                binding.femaleRadioButton.id -> Gender.Female
                binding.noGenderPrefRadioButton.id -> Gender.No_Preference
                else -> Gender.No_Preference
            }
            val mainLanguage = when (binding.spokenLanguagesRadioGroup.checkedRadioButtonId) {
                binding.hebrewRadioButton.id -> Language.Hebrew
                binding.arabicRadioButton.id -> Language.Arabic
                binding.englishRadioButton.id -> Language.English
                else -> Language.Hebrew
            }
            val studyTarget = when (binding.studyTargetRadioGroup.checkedRadioButtonId) {
                binding.studyTargetHighRadioButton.id -> StudyTarget.High
                binding.studyTargetLowRadioButton.id -> StudyTarget.Low
                binding.studyTargetMediumRadioButton.id -> StudyTarget.Medium
                else -> StudyTarget.Medium
            }
            val workType = when (binding.workTypeRadioGroup.checkedRadioButtonId) {
                binding.workTypeSplitRadioButton.id -> WorkType.Split
                binding.workTypeTogetherRadioButton.id -> WorkType.Together
                else -> WorkType.Together
            }
            val studyPreference = when (binding.studyPreferenceRadioGroup.checkedRadioButtonId) {
                binding.studyPreferenceNoPref.id -> StudyPreference.No_Preference
                binding.frontalRadioButton.id -> StudyPreference.Frontal
                binding.remoteRadioButton.id -> StudyPreference.Remote
                else -> StudyPreference.No_Preference
            }

            val workDays = workDaysCheckboxes.filter { it.isChecked }
            val selectedWorkDays = workDays.map {
                when (it) {
                    binding.workDaySunday -> WorkDay.Sunday
                    binding.workDayMonday -> WorkDay.Monday
                    binding.workDayTuesday -> WorkDay.Tuesday
                    binding.workDayWednesday -> WorkDay.Wednesday
                    binding.workDayThursday -> WorkDay.Thursday
                    binding.workDayFriday -> WorkDay.Friday
                    else -> throw IllegalArgumentException("Unexpected checkbox")
                }
            }

            val personalPreferences = PersonalPreferences(
                studyPreference = studyPreference,
                workDays = selectedWorkDays,
                workType = workType,
                studyTarget = studyTarget,
                mainLanguage = mainLanguage,
                partnerGenderPreference = partnerGenderPreference,
                description = binding.selfDescriptionEditText.text.toString()
            )
            UserDataService.setPersonalPreferences(personalPreferences)

            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setFormAvailability() {
        binding.submitButton.isEnabled =
            binding.selfDescriptionEditText.text!!.isNotEmpty() && binding.genderRadioGroup.checkedRadioButtonId != -1 && binding.spokenLanguagesRadioGroup.checkedRadioButtonId != -1 && binding.studyTargetRadioGroup.checkedRadioButtonId != -1 && binding.studyPreferenceRadioGroup.checkedRadioButtonId != -1 && binding.workTypeRadioGroup.checkedRadioButtonId != -1 && workDaysAvailability
    }
}