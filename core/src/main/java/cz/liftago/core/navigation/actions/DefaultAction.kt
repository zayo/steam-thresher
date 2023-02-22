package cz.liftago.core.navigation.actions

import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Args
import cz.liftago.core.navigation.EmptyArgs
import cz.liftago.core.navigation.EmptyArgsAction
import cz.liftago.core.navigation.Navigator
import cz.liftago.core.navigation.RootAction
import cz.liftago.core.navigation.blocks.NavHostActivity
import cz.liftago.core.navigation.blocks.NavHostFragment
import kotlinx.parcelize.Parcelize

/**
 * Default action to be used when [NavHostActivity] is missing action. E.g. started from
 * launcher icon.
 *
 * Note: While it is generally possible to handle local navigation within [NavHostFragment] itself,
 * it's still recommended to send it through [Navigator] to navigate always from top to bottom.
 *
 * @see Action
 * @see RootAction
 * @see EmptyArgsAction
 * @see Args
 * @see EmptyArgs
 */
@Parcelize
object DefaultAction: EmptyArgsAction(), RootAction
