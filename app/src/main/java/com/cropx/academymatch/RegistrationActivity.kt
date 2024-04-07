package com.cropx.academymatch

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.cropx.academymatch.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityRegistrationBinding.inflate(layoutInflater)
    }
    private lateinit var coursesNumbersAdapter: CoursesNumbersAdapter

    private val mTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}
        override fun afterTextChanged(editable: Editable) {
            checkFormAvailability()
        }
    }

    private fun checkFormAvailability() {
        binding.nextButton.isEnabled =
            binding.emailEditText.text!!.isNotEmpty() && binding.passwordEditText.text!!.isNotEmpty() && binding.passwordEditText.text!!.isNotEmpty() && binding.universityNameEditText.text!!.isNotEmpty() && binding.facultyEditText.text!!.isNotEmpty() && binding.genderRadioGroup.checkedRadioButtonId != -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fullNameEditText.addTextChangedListener(mTextWatcher)
        binding.emailEditText.addTextChangedListener(mTextWatcher)
        binding.passwordEditText.addTextChangedListener(mTextWatcher)
        binding.universityNameEditText.addTextChangedListener(mTextWatcher)
        binding.facultyEditText.addTextChangedListener(mTextWatcher)
        binding.genderRadioGroup.setOnCheckedChangeListener { _, _ ->
            checkFormAvailability()
        }

        val courseList: ArrayList<String> = arrayListOf("")

        coursesNumbersAdapter = CoursesNumbersAdapter(courseList, object : ICoursesNumbersAdapter {
            override fun onAddCourseClicked() {
                courseList.add("")
                binding.coursesRv.adapter!!.notifyItemInserted(courseList.size - 1)
            }

            override fun onEditTextUpdated(position: Int, value: String) {
                courseList[position] = value
            }
        })
        binding.coursesRv.adapter = coursesNumbersAdapter

        binding.nextButton.setOnClickListener {
            val gender = when (binding.genderRadioGroup.checkedRadioButtonId) {
                binding.maleRadioButton.id -> Gender.Male
                binding.femaleRadioButton.id -> Gender.Female
                else -> Gender.Male
            }

            val coursesNumbers = coursesNumbersAdapter.getCoursesNumbers()

            val userGeneralData = UserGeneralData(
                fullName = binding.fullNameEditText.text.toString(),
                email = binding.emailEditText.text.toString(),
                password = binding.passwordEditText.text.toString(),
                universityName = binding.universityNameEditText.text.toString(),
                faculty = binding.facultyEditText.text.toString(),
                gender = gender,
                coursesNumber = coursesNumbers
            )
            UserDataService.setUserGeneralData(userGeneralData)

            startActivity(Intent(this, PersonalPreferencesActivity::class.java))
        }
    }
}