package com.kurly.android.mockserver

import com.kurly.android.mockserver.core.FileProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
internal class MockServerTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fileProvider: FileProvider

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun `리소스 파일 읽기 테스트`() {
        println(fileProvider.getJsonFromAsset("file_read_test.json"))
    }
}
