package cz.liftago.core.navigation.blocks

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.transition.Slide
import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Args
import cz.liftago.core.navigation.Navigator
import cz.liftago.core.navigation.utils.requireNavigationAction
import javax.inject.Inject

/**
 * Base class of the Navigation Host Fragment.
 *
 * Example of usage:
 * ```
 * @AndroidEntryPoint
 * class FeatureFragment : NavHostFragment(R.layout.fragment_feature) {
 *
 * @Inject
 * lateinit var navigator: Navigator
 *
 * private val binding by viewBindings<FragmentFeatureBindings>()
 *
 * override fun onCreateView(...): View = binding.root
 *
 * override fun handle(action: Action<Args>) {
 *    ...setup ui for that action...
 *    ...`finishFragment()`...
 *    ...`navigator.navigate()`...
 * }
 *```
 *
 * **important** The concrete subclass of [NavHostFragment] denotes one level of navigation,
 * regardless of actions handled. This means, that if the fragment emits an [Action] through
 * [Navigator] and he is also the handler, it will replace previous action, not creating any
 * backstack. This is intended for Compose-based navigation patterns that are able to handle
 * backstack on it's own. If it is required to have previous action in the backstack, it means
 * the second action needs to be handled by different [NavHostFragment] or this instance must
 * remember some local stack of actions that he can then navigate on his own (with use of
 * system's back handler component).
 *
 * @see NavHostComposeFragment
 */
abstract class NavHostFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    // Hilt can't take care of default param values.
    constructor() : this(0)

    @Inject
    lateinit var navigator: Navigator

    protected open val useDefaultTransition: Boolean
        get() = true

    protected val fragmentNavigator: FragmentNavigator by lazy {
        // Compose self with Navigator instance.
        object : FragmentNavigator, Navigator by navigator {
            override fun finishFragment(action: Action<Args>?) =
                this@NavHostFragment.finishFragment(action)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (useDefaultTransition) {
            enterTransition = Slide(Gravity.RIGHT).apply { duration = 300L }
            exitTransition = Slide(Gravity.LEFT).apply { duration = 300L }
            reenterTransition = Slide(Gravity.LEFT).apply { duration = 300L }
            returnTransition = Slide(Gravity.RIGHT).apply { duration = 300L }
            allowEnterTransitionOverlap = true
            allowReturnTransitionOverlap = true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handle(requireArguments().requireNavigationAction())
    }

    /**
     * Finishes fragment/activity.
     *
     * @param result optionally can result in follow up action this fragment will be replaced with.
     *
     * @see FragmentNavigator
     * @see fragmentNavigator
     */
    protected fun finishFragment(result: Action<Args>? = null) {
        activity?.run {
            if (this is FragmentContainer) {
                finishFragment(result)
            } else {
                finish()
            }
        }
    }

    protected fun Action<Args>?.unknownAction(): Nothing {
        error("Unable to handle '$this', did you messed with [ActionHandler]'s?")
    }

    /**
     * Implementing subclass must handle the [action]. This can be called multiple times if the
     * current fragment is on top.
     */
    abstract fun handle(action: Action<Args>)
}