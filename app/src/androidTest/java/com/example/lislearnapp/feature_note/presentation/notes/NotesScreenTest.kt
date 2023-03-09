package com.example.lislearnapp.feature_note.presentation.notes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lislearnapp.core.util.TestTags
import com.example.lislearnapp.di.AppModule
import com.example.lislearnapp.feature_note.presentation.MainActivity
import com.example.lislearnapp.feature_note.presentation.notes.components.NotesScreen
import com.example.lislearnapp.feature_note.presentation.util.Screen
import com.example.lislearnapp.ui.theme.LisLearnAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesScreenTest{

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalAnimationApi::class)
    @Before
    fun setup(){
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            LisLearnAppTheme {
                NavHost(navController = navController, startDestination = Screen.NotesScreen.route){
                    composable(Screen.NotesScreen.route){
                        NotesScreen(navController = navController)
                    }
                }
            }
        }
    }



}