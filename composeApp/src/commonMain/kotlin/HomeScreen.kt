
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

object HomeScreen : Screen {
    @Composable
    override fun Content() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
        ) {
            val homeScreenModel = rememberScreenModel { HomeScreenModel() }
            val navigator = LocalNavigator.currentOrThrow

            val text by homeScreenModel.text.collectAsState()
            val error by homeScreenModel.error.collectAsState()
            val targetScreen by homeScreenModel.targetScreen.collectAsState()

            LaunchedEffect(targetScreen) {
                targetScreen?.let {
                    navigator push targetScreen!!
                    homeScreenModel.doneNavigating()
                }
            }

            OutlinedTextField(
                text,
                homeScreenModel::onTextChange,
                modifier = Modifier.fillMaxWidth(0.8f).onKeyEvent {
                    if (it.key == Key.Enter) {
                        homeScreenModel.onNextClicked()
                        true
                    } else {
                        false
                    }
                },
                keyboardActions = KeyboardActions(
                    onGo = { homeScreenModel.onNextClicked() }
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                maxLines = 1,
                singleLine = true,
                label = { Text("Name") },
                isError = error != null,
            )
            AnimatedVisibility(error != null) {
                error?.let {
                    Text(
                        text = it,
                        modifier = Modifier.fillMaxWidth(0.8f).padding(start = 4.dp),
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colors.error
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Button(onClick = homeScreenModel::onNextClicked) {
                Text("Next")
            }
        }
    }
}