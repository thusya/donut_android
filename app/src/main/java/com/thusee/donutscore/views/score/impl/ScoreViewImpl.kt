package com.thusee.donutscore.views.score.impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.thusee.donutscore.databinding.FragmentScoreBinding
import com.thusee.donutscore.usecase.model.ScoreDataMapper
import com.thusee.donutscore.utils.handleError
import com.thusee.donutscore.views.score.ScoreClickListener
import com.thusee.donutscore.views.score.ScoreView

class ScoreViewImpl: ScoreView {

    private var _binding: FragmentScoreBinding? = null
    private val binding get() = _binding!!

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun changeUiState(viewState: ScoreView.UiState) {
        when (viewState) {
            is ScoreView.UiState.ShowProgressBar -> binding.rowLoadingAnim.visibility = View.VISIBLE
            is ScoreView.UiState.HideProgressBar -> binding.rowLoadingAnim.visibility = View.GONE
            else -> {}
        }
    }

    override fun changeState(state: ScoreView.State) {
        when (state) {
            is ScoreView.State.DisplayData -> updateData(state.data)
            is ScoreView.State.ErrorHandle -> Toast.makeText(
                binding.root.context,
                binding.root.context.handleError(state.e),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun dispose() {
        _binding = null
    }

    override fun updateData(data: ScoreDataMapper?) {

        binding.scoreView.currentScore = "${data?.currentScore}"
        binding.scoreView.totalScore = "${data?.totalScore}"
        data?.let {
            binding.scoreView.setPercentageStatus(
                it.getPercentage(),
                "${it.currentScore}",
                "${it.totalScore}"
            )
        }
    }

    override fun setCallBack(listener: ScoreClickListener) {
        binding.scoreView.setOnClickListener(listener)
    }


}