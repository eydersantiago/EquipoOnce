package com.uv.routinesappuv.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.uv.routinesappuv.repository.RutinasRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    // Rule para que las tareas LiveData se ejecuten de manera sincrónica
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    // Dispatcher para pruebas en coroutines
    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: LoginViewModel
    private lateinit var repository: RutinasRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Mock del repository
        repository = mock()

        // Inicialización del ViewModel con el mock del repository
        viewModel = LoginViewModel(mock()).apply {
            repository = this@LoginViewModelTest.repository
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `registerUser success`() {
        val email = "test@example.com"
        val password = "password"

        // Simular éxito en el registro
        whenever(repository.registerUser(email, password, any())).thenAnswer {
            val callback = it.arguments[2] as (Boolean) -> Unit
            callback(true)
        }

        // Invocar al método del ViewModel
        viewModel.registerUser(email, password) { result ->
            assert(result) { "Registration should be successful" }
        }
    }

    @Test
    fun `registerUser failure`() {
        val email = "test@example.com"
        val password = "password"

        // Mock del método registerUser del repository para simular fallo
        doAnswer { invocation ->
            val callback: (Boolean) -> Unit = invocation.getArgument(2)
            callback.invoke(false)
        }.`when`(repository).registerUser(email, password, any())

        // Invocar al método del ViewModel
        viewModel.registerUser(email, password) { result ->
            assert(!result) { "Registration should fail" }
        }
    }
}
