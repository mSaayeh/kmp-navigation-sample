import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * @author Mohannad El-Sayeh email(eng.mohannadelsayeh@gmail.com)
 * @date 28/12/2023
 */
class HomeScreenModel: ScreenModel {
    private val _text = MutableStateFlow("")
    val text = _text.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _targetScreen = MutableStateFlow<Screen?>(null)
    val targetScreen = _targetScreen.asStateFlow()

    fun onTextChange(value: String) {
        _text.update {
            value
        }
        _error.update { null }
    }

    fun onNextClicked() {
        if (_text.value.isNotBlank()) {
            _targetScreen.update { DetailsScreen(_text.value) }
        }
        else {
            _error.update { "Please enter a name." }
        }
    }

    fun doneNavigating() {
        _targetScreen.update { null }
    }
}