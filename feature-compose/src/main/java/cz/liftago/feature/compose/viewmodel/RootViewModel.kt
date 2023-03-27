package cz.liftago.feature.compose.viewmodel

import androidx.lifecycle.ViewModel
import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val navigator: Navigator,
) : ViewModel(), FeatureComposeViewModel {

    override val identity = "RootViewModel@${hashCode()}"

    init {
        ViewModelLogger.init(identity)
    }

    override fun navigate(action: Action<*>) = navigator.navigate(action = action)

    override fun onCleared() {
        super.onCleared()
        ViewModelLogger.clear(identity)
    }
}