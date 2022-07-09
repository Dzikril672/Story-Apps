package com.dzikril.submission1.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.dzikril.submission1.JsonConverter
import com.dzikril.submission1.R
import com.dzikril.submission1.data.remote.retrofit.ApiConfig
import com.dzikril.submission1.utils.EspressoIdlingResources
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class MainActivityTest {

    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        mockWebServer.start(8080)
        ApiConfig.BASE_URL = "http://127.0.0.1:8080/"
        IdlingRegistry.getInstance().register(EspressoIdlingResources.countingIdlingResource)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.countingIdlingResource)
    }

    @Test
    fun getStoriesSuccess() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("responses_success.json"))
        mockWebServer.enqueue(mockResponse)
        Espresso.onView(withId(R.id.rv_story)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.rv_story))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
    }

}