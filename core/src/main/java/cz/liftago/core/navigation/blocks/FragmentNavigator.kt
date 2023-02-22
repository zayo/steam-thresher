package cz.liftago.core.navigation.blocks

import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Args
import cz.liftago.core.navigation.Navigator

/**
 * Extends the [Navigator] with [finishFragment] defined in the [NavHostFragment].
 * This is just shortcut for passing [Navigator] into other classes without need to pass
 * [NavHostFragment.finishFragment] specifically.
 *
 * @see NavHostFragment
 */
interface FragmentNavigator : Navigator {

    /**
     * Call to finish current fragment.
     * Optionally can contain [Action] that will replace the current [Action].
     *
     * Use with caution as this is very delicate api, in most cases [Navigator.navigate]
     * is the best option to use.
     */
    fun finishFragment(action: Action<Args>? = null)
}