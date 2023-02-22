package cz.liftago.core.navigation.blocks

import android.app.Activity
import androidx.fragment.app.Fragment
import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Args

/**
 * Interface definition for an [Activity] providing container for [Fragment]s.
 */
internal interface FragmentContainer {

    /**
     * Finishes currently visible [Fragment] from the container. If there are no more [Fragment]s,
     * [Activity.finish] might be called.
     */
    fun finishFragment(result: Action<Args>? = null)
}
