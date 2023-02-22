package cz.liftago.core.demo

import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Args

interface Notificator {
    fun notify(action: Action<Args>)
}