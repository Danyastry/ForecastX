import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherapp.Models.AuthState
import com.example.weatherapp.R
import com.example.weatherapp.ViewModel.AuthViewModel
import com.example.weatherapp.ui.theme.font_basic
import com.example.weatherapp.ui.theme.monsterrat
import com.example.weatherapp.ui.theme.monsterrat_bold
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.delay

@Composable
fun MainScreen(navController: NavController, authViewModel: AuthViewModel) {

    var startAnimation by remember {
        mutableStateOf(false)
    }
    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1.8f else 5f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )
    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1.0f else 0f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )
    val autoState = authViewModel.authState.observeAsState()
    val auth = Firebase.auth
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(1000)
        startAnimation = true

    }
    Column(
        modifier = Modifier.alpha(alpha),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_world),
            tint = Color.Gray,
            contentDescription = null,
            modifier = Modifier
                .scale(scale)
                .padding(top = 25.dp)
        )
        Text(
            text = "Discover the Weather",
            fontSize = 25.sp,
            fontFamily = font_basic,
            color = Color.LightGray,
            modifier = Modifier.alpha(alpha)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp, vertical = 15.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(Color.LightGray)
                .alpha(alpha)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Explore global map of wind,\nweather, and wind conditions",
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontFamily = monsterrat_bold,
                    modifier = Modifier.alpha(alpha)
                )
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = "Planning your trip become more easier with ForecastX, you can instantly see the whole word weather within few second",
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontFamily = monsterrat,
                    modifier = Modifier.alpha(alpha)
                )
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = {
                        when (autoState.value) {
                            is AuthState.Authenticated -> navController.navigate("home")
                            is AuthState.Unauthenticated -> navController.navigate("login")
                            else -> Unit
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .padding(horizontal = 40.dp)
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                ) {
                    Text(
                        text = "Get Start",
                        fontSize = 20.sp,
                        fontFamily = monsterrat_bold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))

                Row() {
                    Text(
                        text = "Already have an account? ",
                        fontSize = 15.sp,
                        fontFamily = monsterrat,
                        color = Color.Black
                    )
                    Text(
                        text = "Sign In",
                        fontSize = 16.sp,
                        fontFamily = monsterrat_bold,
                        color = Color.Black,
                        modifier = Modifier.clickable {
                            if (auth.currentUser == null) {
                                navController.navigate("login")
                            } else {
                                navController.navigate("home").also {
                                    Toasty.info(
                                        context,
                                        "You are already logged in",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

