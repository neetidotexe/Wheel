package com.example.myapplication

import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
import org.junit.Test


@RunWith(JUnit4::class)
class MainViewModelTest{

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `when progress submitted is empty then show empty error message`(){
        val viewModel = MainViewModel()
        viewModel.onClickSubmit("")
        Assert.assertEquals(ErrorType.EMPTY_INPUT , viewModel.errorType.value)
    }

    @Test
    fun `when progress submitted is less than 0 then show invalid progress error message`(){
        val viewModel = MainViewModel()
        viewModel.onClickSubmit("-1")
        Assert.assertEquals(ErrorType.INVALID_INPUT , viewModel.errorType.value)
    }

    @Test
    fun `when progress submitted is greater than 100 then show invalid progress error message`(){
        val viewModel = MainViewModel()
        viewModel.onClickSubmit("107")
        Assert.assertEquals(ErrorType.INVALID_INPUT , viewModel.errorType.value)
    }

    @Test
    fun `when progress submitted is invalid string then show error message`(){
        val viewModel = MainViewModel()
        viewModel.onClickSubmit("y87j")
        Assert.assertEquals(ErrorType.INVALID_INPUT , viewModel.errorType.value)
    }

    @Test
    fun `when progress submitted is between 0 and 100 then show progress on bar`(){
        val viewModel = MainViewModel()
        viewModel.onClickSubmit("18")
        Assert.assertEquals(ErrorType.NONE , viewModel.errorType.value)
        Assert.assertEquals(18.0 , viewModel.progressValue.value )
    }
}