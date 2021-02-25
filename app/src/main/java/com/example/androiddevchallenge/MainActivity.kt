/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    val puppyListState = rememberLazyListState()
    var screen: Screens by remember { mutableStateOf(Screens.PuppyList) }

    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                val navigationIcon: @Composable () -> Unit = {
                    if (screen is Screens.PuppyDetail) {
                        IconButton(
                            onClick = { screen = Screens.PuppyList }
                        ) {
                            Image(
                                imageVector = Icons.Sharp.ArrowBack,
                                contentDescription = "Go back to list",
                                colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                            )
                        }
                    }
                }
                TopAppBar(
                    title = {
                        Text(
                            text =
                            if (screen is Screens.PuppyDetail)
                                (screen as Screens.PuppyDetail).puppy.name
                            else "Adopt a puppy",
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    navigationIcon = if (screen is Screens.PuppyDetail) navigationIcon else null
                )
            }
        ) {
            when (screen) {
                Screens.PuppyList -> {
                    PuppyList(
                        changeScreen = { screen = it },
                        state = puppyListState,
                        modifier = Modifier
                            .padding(16.dp)
                            .padding(it)
                    )
                }
                is Screens.PuppyDetail -> {
                    BackHandler(onBack = { screen = Screens.PuppyList })
                    PuppyDetail(
                        puppy = (screen as Screens.PuppyDetail).puppy,
                        modifier = Modifier
                            .padding(16.dp)
                            .padding(it)
                    )
                }
            }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}

sealed class Screens {
    object PuppyList : Screens()
    class PuppyDetail(val puppy: Puppy) : Screens()
}

enum class Size {
    small, medium, large
}

enum class Gender {
    male, female
}

data class Puppy(
    val petId: Int,
    val image: String,
    val additionalImages: List<String> = emptyList(),
    val name: String,
    val breed: String,
    val age: String,
    val gender: Gender,
    val color: String,
    val spayedNeutered: Boolean,
    val size: Size,
    val description: String = ""
)
