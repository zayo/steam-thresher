package cz.liftago.core.navigation.blocks

import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Args
import kotlin.reflect.KClass

/**
 * Interface for mapping of [Action] to specified [NavHostFragment] responsible for navigation.
 *
 * **Important** one [Action] can be contained by exactly one [ActionHandler] otherwise the runtime
 * exception occurs. While this puts restrictions on [Action], it does not imply the opposite.
 *
 * It is possible/allowed (sometimes even recommended) to create multiple [ActionHandler] instances
 * which references the same [handler].
 */
interface ActionHandler {

    /**
     * KClasses of supported [Action]s by provided [handler].
     */
    val actions: Set<KClass<out Action<Args>>>

    /**
     * KClass of [NavHostFragment]'s subclass responsible for navigating provided [actions].
     */
    val handler: KClass<out NavHostFragment>
}