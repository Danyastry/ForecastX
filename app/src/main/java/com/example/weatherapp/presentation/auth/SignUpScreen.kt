package com.example.weatherapp.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherapp.presentation.common.AlreadyHaveAnAccount
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.font_unt
import es.dmoral.toasty.Toasty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController, viewModel: AuthViewModel = viewModel()) {
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isFocusedEmail by remember {
        mutableStateOf(false)
    }
    var isFocusedPassword by remember {
        mutableStateOf(false)
    }
    val authState = viewModel.authState.observeAsState()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Registered -> {
                navController.navigate("home")
                Toasty.success(context, "Registration successful", Toast.LENGTH_LONG).show()
            }


            is AuthState.Error -> Toasty.error(
                context, "Login or Password entered incorrectly", Toast.LENGTH_LONG
            ).show()

            else -> Unit
        }

    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp)
                .pointerInput(Unit) {
                    detectTapGestures {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_signup),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(top = 54.dp)
                    .height(150.dp)
                    .align(Alignment.Start)
            )
            Text(
                text = "Sign Up",
                fontSize = 25.sp,
                fontFamily = font_unt,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Sign Up and be always with us",
                fontSize = 19.sp,
                fontFamily = font_unt,
                color = Color.LightGray,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(30.dp))

            TextField(
                value = email, onValueChange = { email = it }, placeholder = {
                    if (!isFocusedEmail) {
                        Text(
                            text = "Email Address", style = TextStyle(
                                fontSize = 20.sp,
                                fontFamily = font_unt,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isFocusedEmail = focusState.isFocused
                    },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    unfocusedIndicatorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    cursorColor = Color.White

                ),
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = font_unt,
                    fontWeight = FontWeight.Bold
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            )


            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = password, onValueChange = { password = it }, placeholder = {
                    if (!isFocusedPassword) {
                        Text(
                            text = "Password", style = TextStyle(
                                fontSize = 20.sp,
                                fontFamily = font_unt,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isFocusedPassword = focusState.isFocused
                    },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    unfocusedIndicatorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    cursorColor = Color.White

                ),
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = font_unt,
                    fontWeight = FontWeight.Bold
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
                ),
                visualTransformation = if (!passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    val icon =
                        if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(imageVector = icon, contentDescription = null, tint = Color.White)

                    }
                }
            )
            Row(
                modifier = Modifier.padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (password.length >= 6) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.Green,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Password is Valid",
                        fontSize = 14.sp,
                        color = Color.Green,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null, tint = Color.Red, modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Password must be at least 6 characters",
                        fontSize = 14.sp,
                        color = Color.Red,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        Toasty.error(context, "Email or Password can't be empty", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        viewModel.signUp(email, password)
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                ),
                shape = ShapeDefaults.Large
            ) {
                Text(
                    text = "Sign Up",
                    fontSize = 20.sp,
                    fontFamily = font_unt,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            AlreadyHaveAnAccount(onTap = {
                navController.navigate("login")
            })
        }

    }
}