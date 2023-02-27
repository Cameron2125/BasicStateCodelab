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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


private lateinit var auth: FirebaseAuth;

@Composable
fun StatefulCounter(modifier: Modifier = Modifier, context: ComponentActivity,wellnessViewModel: WellnessViewModel,) {
    //var count by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(
        count = wellnessViewModel.count,
        onIncrement = { wellnessViewModel.upDateCount()
                       },
        modifier = modifier,
        context = context
    )
}

@Composable
fun StatelessCounter(count: MutableState<Int>, onIncrement: () -> Unit, modifier: Modifier = Modifier, context: ComponentActivity) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var text2 by remember { mutableStateOf(TextFieldValue("")) }
    Column(modifier = modifier.padding(16.dp)) {
        if (count.value > 0) {
            Text("You've had ${count.value} glasses.")
        }
        Button(
            onClick = onIncrement,
            enabled = count.value < 10,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Add one")
        }

        // Outlined Text Input Field
        OutlinedTextField(
            value = text,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            label = { Text(text = "email") },
            placeholder = { Text(text = "user@gmail.com") },
            onValueChange = {
                text = it
            },
        )
        OutlinedTextField(
            value = text2,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            label = { Text(text = "password") },
            placeholder = { Text(text = "1234567") },
            onValueChange = {
                text2 = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Button(
            onClick = { SignIn(context,text.text.toString().trim(),text2.text.toString().trim()) },
            enabled = true,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Sign In")
        }
        Button(
            onClick = { CreateAccount(context,text.text.toString().trim(),text2.text.toString().trim()) },
            enabled = true,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Create account")
        }
    }
}

private fun SignIn(context: ComponentActivity, email: String, password: String){

    auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(context) {
        if (it.isSuccessful) {
            Toast.makeText(context, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
        } else
            Toast.makeText(context, "Log In failed ${it.exception?.toString()}", Toast.LENGTH_LONG).show()
        Log.d("my tag","${it.exception?.toString()}")
    }
}
private fun CreateAccount(context: ComponentActivity, email: String, password: String){

    auth = Firebase.auth

    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(context) {
        if (it.isSuccessful) {
            Toast.makeText(context, "Successfully Singed Up", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(context, "Singed Up Failed! ${it.exception?.toString()}", Toast.LENGTH_LONG).show()
            Log.d("my tag","${it.exception?.toString()}")
        }
    }
}

private fun addTast(){



}
