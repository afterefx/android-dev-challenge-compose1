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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.imageloading.ImageLoadState

@Composable
fun PuppyDetail(puppy: Puppy, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyRow {
                    item {
                        DogImage(puppy.image)
                    }
                    items(puppy.additionalImages) {
                        DogImage(dogImage = it)
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Info("Pet ID", puppy.petId.toString())
            Info("Age", puppy.age)
            Info("Gender", if (puppy.gender == Gender.male) "Male" else "Female")
            Info("Breed", puppy.breed)
            Info("Color", puppy.color)
            Info("Spayed/Neutered", if (puppy.spayedNeutered) "Yes" else "No")
            Info(
                "Size",
                when (puppy.size) {
                    Size.small -> "Small"
                    Size.medium -> "Medium"
                    Size.large -> "Large"
                }
            )
            puppy.description.takeIf { it.isNotEmpty() }?.let {
                Info("Description", it)
            }
        }
    }
}

@Composable
private fun DogImage(dogImage: String) {
    CoilImage(data = dogImage) {
        when (it) {
            ImageLoadState.Empty -> { /* empty content */
            }
            ImageLoadState.Loading -> {
                Text("Loading...")
            }
            is ImageLoadState.Success -> {
                Image(
                    painter = it.painter,
                    contentDescription = null,
                    modifier = Modifier.height(200.dp),
                    contentScale = ContentScale.FillHeight
                )
            }
            is ImageLoadState.Error -> {
                Text("Error loading picture.")
            }
        }
    }
}

@Composable
fun Info(label: String, value: String, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .then(modifier)
    ) {
        Text("$label:")
        Spacer(modifier = Modifier.width(6.dp))
        Text(value)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PuppyPreview() {
    PuppyDetail(puppies[0])
}
