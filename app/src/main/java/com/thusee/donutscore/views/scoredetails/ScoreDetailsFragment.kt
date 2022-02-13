package com.thusee.donutscore.views.scoredetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.thusee.donutscore.R
import com.thusee.donutscore.base.BaseFragment
import com.thusee.donutscore.databinding.FragmentDetailsBinding
import com.thusee.donutscore.usecase.model.ScoreDataMapper

class ScoreDetailsFragment : BaseFragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private var scoreData: ScoreDataMapper? = ScoreDataMapper()


    companion object {
        const val SCORE_DATA = "com.thusee.donutscore.views.scoredetails.SCORE_DATA"

        fun startFragment(navController: NavController, scoreData: ScoreDataMapper){
            navController.navigate(R.id.nav_details, bundleOf(SCORE_DATA to scoreData))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scoreData = arguments?.getParcelable(SCORE_DATA)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        setData()
        return binding.root
    }

    private fun setData() {
        binding.scoreBand.text = "${resources.getString(R.string.score_band)} ${scoreData?.currentScore}"
        binding.clientRef.text = "${resources.getString(R.string.client_ref)} ${scoreData?.clientRef}"
        binding.currentShortTermDebt.text ="Current Short Term Debt: ${scoreData?.currentShortTermDebt}"
        binding.currentShortTermNonPromotionalDebt.text = "Current Short Term Non-Promotional Debt: ${scoreData?.currentLongTermNonPromotionalDebt}"
        binding.currentShortTermCreditLimit.text = "Current Short Term Credit Limit: ${scoreData?.currentShortTermCreditLimit}"
        binding.currentShortTermCreditUtilisation.text = "Current Short Term Credit Utilisation: ${scoreData?.currentShortTermCreditUtilisation}"
        binding.changeInShortTermDebt.text = "Change In Short Term Debt: ${scoreData?.changeInShortTermDebt}"
        binding.currentLongTermDebt.text = "Current Long Term Debt: ${scoreData?.currentLongTermDebt}"
        binding.currentLongTermNonPromotionalDebt.text = "Current Long TermNon Promotional Debt: ${scoreData?.currentLongTermNonPromotionalDebt}"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}