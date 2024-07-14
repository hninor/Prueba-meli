package com.hninor.pruebameli.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hninor.pruebameli.api.ApiResponseStatus
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MeliRemoteRepositoryTest {
    private lateinit var repository: MeliRemoteRepository

    @Before
    fun setUp() {
        repository = MeliRemoteRepository()
    }
    @Test
    fun fetchCharacters() = runTest {
        val response = repository.fetchResults("Motorola")
        if (response is ApiResponseStatus.Success) {
            Assert.assertNotNull(response.data)
        } else {
            assert(response is ApiResponseStatus.Error)
        }

    }
}