package cz.liftago.feature.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import cz.liftago.core.demo.ActionsEnumerator
import cz.liftago.core.demo.Notificator
import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Args
import cz.liftago.core.navigation.RootAction
import cz.liftago.core.navigation.blocks.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class FeatureFragment : NavHostFragment(R.layout.fragment_feature) {

    @Inject
    lateinit var enumerator: ActionsEnumerator

    @Inject
    lateinit var notificator: Notificator

    private var notifyIndex: Int = 0
    private var navigateIndex: Int = 0
    private var finishIndex: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireView().findViewById<MaterialToolbar>(R.id.toolbar).setNavigationOnClickListener {
            finishFragment()
        }
    }

    override fun handle(action: Action<Args>) {
        val actions = enumerator.navigable()
        val actionsAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            actions.map { act ->
                listOfNotNull(
                    "[Root]".takeIf { act is RootAction },
                    "[Self]".takeIf { act::class == action::class },
                    act::class.simpleName
                ).joinToString(" ")
            }
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        requireView().findViewById<Spinner>(R.id.notify_spinner).apply {
            adapter = actionsAdapter
            setSelection(notifyIndex)
            onItemSelectedListener = OnSelectedListener { notifyIndex = it }
        }
        requireView().findViewById<Spinner>(R.id.navigate_spinner).apply {
            adapter = actionsAdapter
            setSelection(navigateIndex)
            onItemSelectedListener = OnSelectedListener { navigateIndex = it }
        }
        requireView().findViewById<Spinner>(R.id.finish_spinner).apply {
            adapter = actionsAdapter
            setSelection(finishIndex)
            onItemSelectedListener = OnSelectedListener { finishIndex = it }
        }

        requireView().findViewById<Button>(R.id.notify_button).setOnClickListener {
            notificator.notify(actions[notifyIndex])
        }
        requireView().findViewById<Button>(R.id.navigate_button).setOnClickListener {
            navigator.navigate(actions[navigateIndex])
        }
        requireView().findViewById<Button>(R.id.finish_button).setOnClickListener {
            finishFragment(actions[finishIndex])
        }
    }
}

private class OnSelectedListener(private val handle: (Int) -> Unit) : OnItemSelectedListener {
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) = handle(p2)

    override fun onNothingSelected(p0: AdapterView<*>?) = Unit
}