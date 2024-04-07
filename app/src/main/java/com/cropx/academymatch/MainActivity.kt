package com.cropx.academymatch

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cropx.academymatch.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), DataUpdateListener {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val exploreMatchFragment = ExploreMatchesFragment()
    val viewMatchesFragment = ViewMatchesFragment()

    private val fm = supportFragmentManager.beginTransaction()

    private var activeFragment = Fragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        PreferencesHelper.userData = Gson().toJson(UserDataService.currentUser)

        fm.add(
            R.id.fragment_container, viewMatchesFragment, ViewMatchesFragment::class.java.simpleName
        ).hide(viewMatchesFragment)
        fm.add(
            R.id.fragment_container,
            exploreMatchFragment,
            ExploreMatchesFragment::class.java.simpleName
        ).commit()
        activeFragment = exploreMatchFragment

        binding.openMenuButton.setOnClickListener {
            MenuDialog(object : IMenuDialog {
                override fun onViewMatchesClicked() {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(viewMatchesFragment).commit()
                    activeFragment = viewMatchesFragment
                }

                override fun onFindNewMatchesClicked() {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(exploreMatchFragment).commit()
                    activeFragment = exploreMatchFragment
                }

                override fun onMyProfileClicked() {
                    MatchSummaryDialog(UserDataService.currentUser!!).show(
                        supportFragmentManager,
                        "MatchSummaryDialog"
                    )
                }

            }).show(supportFragmentManager, "MenuDialog")
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
            }
        })
    }

    override fun onDataUpdated() {
        viewMatchesFragment.dataUpdated()
    }
}

interface DataUpdateListener {
    fun onDataUpdated()
}