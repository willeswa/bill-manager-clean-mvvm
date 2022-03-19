package app.monkpad.billmanager.presentation.profilescreen

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import app.monkpad.billmanager.R
import app.monkpad.billmanager.databinding.FragmentProfileScreenBinding
import app.monkpad.billmanager.framework.BillManagerViewModelFactory
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.utils.PercentFormatter
//import com.google.android.gms.ads.AdRequest

class ProfileScreenFragment : Fragment() {
    private val viewModel: ProfileScreenViewModel by activityViewModels {
        BillManagerViewModelFactory
    }
    private lateinit var binding: FragmentProfileScreenBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentProfileScreenBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val overviewPie = binding.overviewPie

//        val adRequest: AdRequest = AdRequest.Builder().build()
//        binding.adView2.loadAd(adRequest)

        viewModel.bills.observe(viewLifecycleOwner, { bills ->
            overviewPie.invalidate()

            val xvalues:List<String> = bills.groupBy{it.categoryName}.keys.toList()

            val entries:List<Entry> = bills.groupBy{it.categoryName}.mapValues{
                (category, billsByCategory) -> billsByCategory.sumOf{it.amount.toDouble()}
            }.values.mapIndexed { index, d ->  Entry(d.toFloat(), index)}

            val colors:List<Int> = listOf(
               resources.getColor(R.color.real_estate_blue),
                resources.getColor(R.color.education_lilac),
                resources.getColor(R.color.food_maize),
                resources.getColor(R.color.utility_orange),
                resources.getColor(R.color.health_care_pink),
                resources.getColor(R.color.personal_drab),
               resources.getColor( R.color.investment_green),
                resources.getColor(R.color.primary_light),
                resources.getColor(R.color.error_color),
                resources.getColor(R.color.white)
            )

            val pieDataSet = PieDataSet(entries, "")
            pieDataSet.sliceSpace = 2f

            pieDataSet.colors = colors

            val tf = ResourcesCompat.getFont(requireContext(), R.font.roboto_black)
            pieDataSet.valueTypeface = tf

            pieDataSet.valueTextColor = resources.getColor(R.color.primary_variant)
            pieDataSet.valueFormatter = PercentFormatter()
            val data = PieData(xvalues, pieDataSet)

            overviewPie.holeRadius = 30f
            overviewPie.setDescription("")
            overviewPie.animateY(500)
            overviewPie.legend.isEnabled = false
            overviewPie.setUsePercentValues(true)

            overviewPie.data = data

        })

    }


}