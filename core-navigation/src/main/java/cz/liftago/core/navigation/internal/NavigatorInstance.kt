package cz.liftago.core.navigation.internal

import cz.liftago.core.navigation.ActionsDelegate

/**
 * Internally holds [DefaultNavigator] instance which is later used for [NavigatorBroadcastReceiver].
 */
internal object NavigatorInstance {

    /**
     * Default navigator.
     */
    lateinit var navigator: DefaultNavigator
        private set

    /**
     * Initialize instance of [DefaultNavigator] and sets it as current value for [navigator].
     *
     * @return Returns new instance of [DefaultNavigator].
     */
    fun init(delegate: ActionsDelegate): DefaultNavigator =
        DefaultNavigator(delegate).also { navigator = it }
}
