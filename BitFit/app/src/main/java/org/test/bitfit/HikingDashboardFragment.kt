package org.test.bitfit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.LineChart
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class HikingDashboardFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hiking_dashboard, container, false)
    }

    private fun fetchTotals(){
        val totalMiles = view?.findViewById<TextView>(R.id.totalMilesTv)
        val totalHours = view?.findViewById<TextView>(R.id.totalTimeTv)
        val avgMPH = view?.findViewById<TextView>(R.id.avgMPHTv)

        fun round(f: Float): Float {
            return Math.round(f * 100) / 100.0f
        }

        lifecycleScope.launch(IO) {
            totalMiles?.text =
                "${(activity?.application as HikingTrailApplication).db.hikingTrailDao()
                    .getTotalMiles().toString()} miles"

            totalHours?.text =
                "${(activity?.application as HikingTrailApplication).db.hikingTrailDao()
                    .getTotalHours().toString()} hours"

            avgMPH?.text = round(((activity?.application as HikingTrailApplication).db.hikingTrailDao()
                .getTotalMiles()/(activity?.application as HikingTrailApplication).db.hikingTrailDao()
                .getTotalHours())).toString()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Call the new method within onViewCreated
        fetchTotals()
    }

}