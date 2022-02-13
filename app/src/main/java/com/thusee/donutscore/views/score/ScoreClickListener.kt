package com.thusee.donutscore.views.score

import android.view.View
import androidx.navigation.NavController
import com.thusee.donutscore.R
import com.thusee.donutscore.views.scoredetails.ScoreDetailsFragment

class ScoreClickListener(private val navController: NavController, private val viewModel: ScoreViewModel): View.OnClickListener {
    override fun onClick(view: View?) {
        when (view?.id){

            R.id.scoreView -> {
                viewModel.getScoreDate()
                    ?.let { ScoreDetailsFragment.startFragment(navController, it) }
            }
        }
    }
}