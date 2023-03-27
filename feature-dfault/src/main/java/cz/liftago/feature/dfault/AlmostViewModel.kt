package cz.liftago.feature.dfault

import cz.liftago.core.demo.ActionsEnumerator
import cz.liftago.core.demo.Notificator
import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Args
import cz.liftago.core.navigation.RootAction
import cz.liftago.core.navigation.blocks.FragmentNavigator

class AlmostViewModel(
    private val action: Action<Args>,
    private val enumerator: ActionsEnumerator,
    private val fragmentNavigator: FragmentNavigator,
    private val notificator: Notificator,
) {

    private val actions = enumerator.navigable()
    val state: List<String> = actions.map { act ->
        listOfNotNull(
            "[Root]".takeIf { act is RootAction },
            "[Self]".takeIf { act::class == action::class },
            act::class.simpleName
        ).joinToString(" ")
    }

    fun notify(position: Int) {
        notificator.notify(actions[position])
    }

    fun navigate(position: Int) {
        fragmentNavigator.navigate(actions[position])
    }

    fun finishFragment(position: Int) {
        fragmentNavigator.finishFragment(actions[position])
    }

    fun finishFragment() {
        fragmentNavigator.finishFragment()
    }
}