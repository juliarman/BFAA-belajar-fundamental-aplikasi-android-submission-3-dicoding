package com.juliarman.appsgithubuser.activity

import android.content.res.Resources
import android.view.KeyEvent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.juliarman.appsgithubuser.R

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    private val dummyUsername = "juliarman"


    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun searchViewClick(){

        onView(withId(R.id.search_view)).perform(click())

    }

}