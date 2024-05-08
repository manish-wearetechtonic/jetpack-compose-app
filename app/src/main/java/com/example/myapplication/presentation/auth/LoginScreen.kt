package com.example.myapplication.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.auth.AccessTokenValidator
import com.example.myapplication.auth.AuthRepository
import com.example.myapplication.data.Result
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController, authRepository: AuthRepository) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val emailFocusRequester = FocusRequester()
    val passwordFocusRequester = FocusRequester()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { passwordFocusRequester.requestFocus() }),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(emailFocusRequester)
                .padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(passwordFocusRequester)
                .padding(bottom = 16.dp)
        )
        Button(
            onClick = {
                if(email.isEmpty()){
                    Toast.makeText(context,"Email can not empty", Toast.LENGTH_SHORT).show()
                }
                if(password.isEmpty()){
                    Toast.makeText(context,"Password can not empty", Toast.LENGTH_SHORT).show()
                }
                if(email.isNotEmpty() && password.isNotEmpty()){
                    scope.launch {
                        authRepository.login(email, password).collect { result ->
                            when (result) {
                                is Result.Error -> {
                                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                                }
                                is Result.Success -> {
                                    result.data?.token?.let {
                                        AccessTokenValidator.saveUserCredentials(
                                            context,
                                            it,
                                            "",
                                            24,
                                            720
                                        )
                                    }
                                    navController.navigate("home")
                                }
                            }
                        }
                    }
                }

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

    }
}
