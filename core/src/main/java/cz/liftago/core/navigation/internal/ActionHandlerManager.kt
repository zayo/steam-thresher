package cz.liftago.core.navigation.internal

import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Args
import cz.liftago.core.navigation.blocks.ActionHandler
import cz.liftago.core.navigation.blocks.NavHostActivity
import cz.liftago.core.navigation.blocks.NavHostFragment
import kotlin.reflect.KClass

/**
 * Internal manager for [NavHostActivity] to be able to decide which [ActionHandler] should be
 * used/created.
 */
internal class ActionHandlerManager(
    handlers: Set<ActionHandler>
) {
    private val actionToHandler: Map<KClass<out Action<*>>, ActionHandler>

    init {
        // flatMap: Project to Pair<Action; Handler>.
        // groupBy: Reduce to Map<Action; List<Handler>>
        // also: Check that there is exactly one handler per action.
        // mapValues: Map<Action;Handler> where values.size == keys.size
        actionToHandler = handlers
            .flatMap { handler -> handler.actions.map { it to handler } }
            .groupBy({ it.first }, { it.second })
            .also { checkHandlerConflicts(it) }
            .mapValues { (_, handlers) -> handlers.first() }
    }

    /**
     * Finds the [KClass] of [NavHostFragment] able to handle [action].
     */
    fun findActionHandler(action: Action<Args>): KClass<out NavHostFragment> =
        requireNotNull(actionToHandler[action::class]) {
            "Unable to find 'ActionHandler' for action '$action'"
        }.handler
}

private fun checkHandlerConflicts(actionToHandlers: Map<KClass<out Action<Args>>, List<ActionHandler>>) {
    val conflicts = actionToHandlers.filterValues { it.size > 1 }
    if (conflicts.isNotEmpty()) {
        val conflictsJoined = conflicts.map { (action, handlers) ->
            "$action handled by: ${handlers.joinToString()}"
        }.joinToString("\n")
        throw IllegalArgumentException("ActionsHandler conflicts: $conflictsJoined")
    }
    // Unhandled actions cannot exists by nature of actions being extracted from handlers.
    // That kind of issue leads to RuntimeError when navigating is attempted.
}