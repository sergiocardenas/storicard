package com.sc.card.presenter.screen

import android.graphics.Bitmap
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sc.card.R
import com.sc.card.presenter.extension.validateEmail
import com.sc.card.presenter.state.UserState
import com.sc.card.presenter.viewModel.RegisterViewModel
import com.sc.card.ui.theme.CardTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onRegisterClick: (user: UserState) -> Unit,
    onTakePhotoClick: (user: UserState) -> Unit,
    onSuccessClick: () -> Unit,
){
    val success = viewModel.success.collectAsState()

    if(success.value){
        RegisterSuccessScreen(onSuccessClick)
    }else{
        RegisterDataScreen(viewModel,onRegisterClick, onTakePhotoClick)
    }
}


@Composable
fun RegisterSuccessScreen(
    onSuccessClick: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Your data have been register successfully",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSuccessClick,
            colors =  ButtonDefaults.buttonColors(colorResource(id = R.color.purple_500)),
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = "Accept",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterDataScreen(
    viewModel: RegisterViewModel,
    onRegisterClick: (user: UserState) -> Unit,
    onTakePhotoClick: (user: UserState) -> Unit,
) {
    val photoString = viewModel.photo.collectAsState()
    var photoBitmap by remember { mutableStateOf<Bitmap?>(null) }

    var email by remember { mutableStateOf(viewModel.userData.value.email) }
    var name by remember { mutableStateOf(viewModel.userData.value.name) }
    var lastName by remember { mutableStateOf(viewModel.userData.value.lastName) }
    var password by remember { mutableStateOf(viewModel.userData.value.password) }

    fun getCurrentUser() = UserState(
        email = email,
        name = name,
        lastName = lastName,
        password = password,
    )

    // Animation states
    var isEmailShaking by remember { mutableStateOf(false) }
    var isNameShaking by remember { mutableStateOf(false) }
    var isLastNameShaking by remember { mutableStateOf(false) }
    var isPasswordShaking by remember { mutableStateOf(false) }
    var isPhotoShaking by remember { mutableStateOf(false) }
    val infiniteTransition = rememberInfiniteTransition()

    val shakeOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(photoString) {
        launch {
            photoBitmap = if(photoString.value.isNotEmpty()){
                viewModel.photoBitmap.value
            }else{
                null
            }
        }
    }

    LaunchedEffect(isEmailShaking) {
        launch {
            delay(500)
            isEmailShaking = false
        }
    }
    LaunchedEffect(isNameShaking) {
        launch {
            delay(500)
            isNameShaking = false
        }
    }
    LaunchedEffect(isLastNameShaking) {
        launch {
            delay(500)
            isLastNameShaking = false
        }
    }
    LaunchedEffect(isPasswordShaking) {
        launch {
            delay(500)
            isPasswordShaking = false
        }
    }
    LaunchedEffect(isPhotoShaking) {
        launch {
            delay(500)
            isPhotoShaking = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            isError = isEmailShaking && email.isEmpty(),
            modifier = Modifier
                .offset(x = if (isEmailShaking) shakeOffset.dp else 0.dp)
                .fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            isError = isNameShaking && name.isEmpty(),
            modifier = Modifier
                .offset(x = if (isNameShaking) shakeOffset.dp else 0.dp)
                .fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            isError = isLastNameShaking && lastName.isEmpty(),
            modifier = Modifier
                .offset(x = if (isLastNameShaking) shakeOffset.dp else 0.dp)
                .fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = isPasswordShaking && password.isEmpty(),
            modifier = Modifier
                .offset(x = if (isPasswordShaking) shakeOffset.dp else 0.dp)
                .fillMaxWidth(),
        )


        Spacer(modifier = Modifier.height(16.dp))

        if(photoString.value.isNotEmpty()){
            photoBitmap?.let {bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .offset(x = if (isPhotoShaking) shakeOffset.dp else 0.dp)
                        .width(60.dp)
                        .height(60.dp)
                )
            }
        }else{
            Image(
                painterResource(R.drawable.ic_launcher_background),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .offset(x = if (isPhotoShaking) shakeOffset.dp else 0.dp)
                    .width(60.dp)
                    .height(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onTakePhotoClick(getCurrentUser())
            },
            colors =  ButtonDefaults.buttonColors(colorResource(id = R.color.purple_500)),
            modifier = Modifier
                .offset(x = if (isPhotoShaking) shakeOffset.dp else 0.dp)
                .height(60.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = "Take Photo",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick =
            {
                isEmailShaking = email.isEmpty() && !email.validateEmail()
                isNameShaking = name.isEmpty()
                isLastNameShaking = lastName.isEmpty()
                isPasswordShaking = password.isEmpty()
                isPhotoShaking = photoString.value.isEmpty()

                onRegisterClick(getCurrentUser())
            },
            colors =  ButtonDefaults.buttonColors(colorResource(id = R.color.purple_700)),
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = "Register",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    CardTheme {
        RegisterDataScreen(RegisterViewModel(),{ }, { })
    }
}
