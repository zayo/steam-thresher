package cz.liftago.core.navigation

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Base abstract class holding extras/arguments in a type-safe manner needed for navigation. This class implements
 * [Parcelable] thus it can be stored into a [Bundle] or [Parcel].
 *
 * Example of creating and using an [Args] implementation:
 * ```
 * @Parcelize
 * class FeatureArgs(val number: Int) : Args()
 * ```
 *
 * See also `ArgsUtils` file for common usage methods.
 */
abstract class Args : Parcelable

/**
 * Empty extras/arguments implementation.
 */
@Parcelize
object EmptyArgs : Args()
