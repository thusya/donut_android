package com.thusee.donutscore.views.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.thusee.donutscore.base.BaseFragment
import com.thusee.donutscore.views.score.observers.ScoreLoadObserver
import com.thusee.donutscore.views.score.observers.UiViewObserver
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScoreFragment: BaseFragment() {

    private val rootView: ScoreView by inject()
    private val viewModel: ScoreViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = rootView.inflate(inflater, container, savedInstanceState)
        setupObserver()
        setupListener()

        return view
    }

    private fun setupObserver() {
        viewModel.scoreLiveData.observe(viewLifecycleOwner, ScoreLoadObserver(rootView))
        viewModel.scoreUiViewState.observe(viewLifecycleOwner, UiViewObserver(rootView))
    }

    private fun setupListener() {
        rootView.setCallBack(ScoreClickListener(findNavController(), rootView))
    }

    override fun onDestroy() {
        super.onDestroy()
        rootView.dispose()
    }

}