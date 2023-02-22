package cz.liftago.core.demo

import cz.liftago.core.navigation.Action

/**
 * Basic provider of all nav actions to other modules, so they can have a dynamic ui.
 *
 * Further on, this could become a data model shared through VM.
 */
interface ActionsEnumerator {

    fun navigable(): List<Action<*>>
}