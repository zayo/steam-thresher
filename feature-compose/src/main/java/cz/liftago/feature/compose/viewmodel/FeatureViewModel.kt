package cz.liftago.feature.compose.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeatureViewModel @Inject constructor(
    private val navigator: Navigator,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), FeatureComposeViewModel {

    private val param = savedStateHandle.getStateFlow("arg", "<empty_arg>")

    override val identity = "FeatureViewModel(${param.value})@${hashCode()}"

    init {
        ViewModelLogger.init(identity)
    }

    override fun navigate(action: Action<*>) = navigator.navigate(action = action)

    override fun onCleared() {
        super.onCleared()
        ViewModelLogger.clear(identity)
    }
}