package cz.liftago.core.navigation.blocks

import android.content.Intent
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import cz.liftago.core.R
import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Args
import cz.liftago.core.navigation.RootAction
import cz.liftago.core.navigation.actions.DefaultAction
import cz.liftago.core.navigation.internal.ActionHandlerManager
import cz.liftago.core.navigation.utils.hasNavigationAction
import cz.liftago.core.navigation.utils.putNavigationAction
import cz.liftago.core.navigation.utils.requireNavigationAction
import javax.inject.Inject

/**
 * Base class providing internal navigation supporting multiple [NavHostFragment]s inside.
 *
 * For proper inner navigation, the descendant [NavHostActivity] needs to be configured
 * as `launchMode=singleTop` in the `AndroidManifest` file.
 *
 * Note: This is meant to be the only activity right now. It's not strictly forbidden to create new
 * activities, or even subclasses of this activity, but it wasn't considered during development. So
 * make sure you test your solution properly.
 */
abstract class NavHostActivity : FragmentActivity(R.layout.activity_fragment_container),
    FragmentContainer {

    @Inject
    internal lateinit var manager: ActionHandlerManager

    private val currentFragment: NavHostFragment?
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as? NavHostFragment

    @CallSuper
    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            if (!intent.hasNavigationAction()) {
                delegateInternal(DefaultAction)
            } else {
                delegateInternal(intent.requireNavigationAction())
            }
        }
    }

    @CallSuper
    final override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)

        if (intent.hasNavigationAction()) {
            delegateInternal(action = intent.requireNavigationAction())
        }
    }

    private fun delegateInternal(action: Action<Args>) {
        val currentFragment = currentFragment

        val handlingFragmentClass = manager.findActionHandler(action)
        fun createNew(): NavHostFragment = handlingFragmentClass.java.newInstance().apply {
            arguments = Bundle().putNavigationAction(action)
        }

        when {
            /**
             * No Fragment in the container or [RootAction].
             * Clear backstack and insert the fragment.
             */
            currentFragment == null || action is RootAction -> {
                repeat(supportFragmentManager.backStackEntryCount) {
                    supportFragmentManager.popBackStackImmediate()
                }
                supportFragmentManager.commitNow {
                    replace(R.id.fragment_container_view, createNew())
                }
            }

            /**
             * Existing fragment is on top, call handle instead of creating new fragment.
             * This by default leads to no back navigation to previous actions handled by the same
             * handlingFragment.
             */
            currentFragment::class == handlingFragmentClass -> {
                currentFragment.arguments = Bundle().putNavigationAction(action)
                currentFragment.handle(action)
            }

            /**
             * Otherwise use new [NavHostFragment] and add to back stack.
             */
            else -> {
                supportFragmentManager.commit {
                    replace(R.id.fragment_container_view, createNew())
                        .addToBackStack(action::class.simpleName)
                }
            }
        }
    }

    /**
     * Adds ability for [NavHostFragment] to finish.
     * It can send also [result] with new [Action] that should be navigated to.
     */
    final override fun finishFragment(result: Action<Args>?) {
        if (supportFragmentManager.backStackEntryCount == 0) {
            require(result is RootAction?) {
                "Finishing last [${NavHostFragment::class.simpleName}]" +
                        " with action '$result' is allowed" +
                        " only when action is [${RootAction::class.simpleName}]!"
            }
            if (result != null) {
                delegateInternal(action = result)
            } else {
                finish()
            }
        } else {
            supportFragmentManager.popBackStack()
            if (result != null) {
                delegateInternal(action = result)
            }
        }
    }
}
