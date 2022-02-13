package com.thusee.donutscore.views.score

import android.view.View
import androidx.navigation.NavController
import com.thusee.donutscore.R
import com.thusee.donutscore.views.scoredetails.ScoreDetailsFragment

class ScoreClickListener(private val navController: NavController, private val rootView: ScoreView): View.OnClickListener {
    override fun onClick(view: View?) {
        when (view?.id){

            R.id.scoreView -> {
                ScoreDetailsFragment.startFragment(navController, rootView.getScoreDate())
            }
        }
    }
}