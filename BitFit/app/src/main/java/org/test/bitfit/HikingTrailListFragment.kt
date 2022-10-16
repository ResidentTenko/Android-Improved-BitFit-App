package org.test.bitfit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.test.bitfit.databinding.ActivityMainBinding


class HikingTrailListFragment : Fragment() {

    private val hikingTrails = mutableListOf<HikingTrailEntity>()
    private lateinit var hikingTrailRecyclerView: RecyclerView
    private lateinit var hikingTrailAdapter: HikingTrailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_hiking_trail_list, container, false)
        // Add these configurations for the recyclerView and to configure the adapter
        val layoutManager = LinearLayoutManager(context)
        hikingTrailRecyclerView = view.findViewById(R.id.trailRv)
        hikingTrailRecyclerView.layoutManager = layoutManager
        hikingTrailRecyclerView.setHasFixedSize(true)
        hikingTrailAdapter = HikingTrailAdapter(view.context, hikingTrails)
        hikingTrailRecyclerView.adapter = hikingTrailAdapter

        // Update the return statement to return the inflated view from above
        return view
    }

    companion object {
        fun newInstance(): HikingTrailListFragment{
            return HikingTrailListFragment()
        }
    }

    private fun fetchHikingTrails(){
        lifecycleScope.launch {
            (activity?.application as HikingTrailApplication).db.hikingTrailDao().getAll().collect{ databaseList ->
                databaseList.map { entity ->
                    HikingTrailEntity(
                        entity.trailName,
                        entity.milesTravelled,
                        entity.workoutTime,
                    )
                }.also { mappedList ->
                    hikingTrails.clear()
                    hikingTrails.addAll(mappedList)
                    hikingTrailAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Call the new method within onViewCreated
        fetchHikingTrails()
    }
}