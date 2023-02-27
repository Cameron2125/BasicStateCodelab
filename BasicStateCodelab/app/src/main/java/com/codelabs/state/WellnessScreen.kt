/*
 * Copyright 2022 The Android Open Source Project
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
package com.codelabs.state

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel  = viewModel(),
    context: ComponentActivity
) {
    //wellnessViewModel.createDoa(context)
    var addTask by remember { mutableStateOf(TextFieldValue("")) }
    Column(modifier = modifier) {

        OutlinedTextField(
            value = addTask,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            label = { Text(text = "task name") },
            onValueChange = {
                addTask = it
            },
        )
        Button(
            onClick = { wellnessViewModel.add(addTask.text.toString()) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Add task")
        }
        StatefulCounter(context = context, wellnessViewModel = wellnessViewModel)

        WellnessTasksList(
            list = wellnessViewModel.tasks.toMutableList(),
            wellnessViewModel = wellnessViewModel
        )
    }
}




