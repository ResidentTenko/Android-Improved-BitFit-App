package org.test.bitfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import org.test.bitfit.databinding.ActivityMainBinding

/** Part 1
 * 1. Create room database
 * 2. Populate database values using edit text in Activity 2
 * 3. Update recycler view using room database
 */

/** Part 2
 * 1. Create two Activities (A & B)
 * 2. Activity A contains two fragments (Trail & Summary)
 * 3. Activity B contains has 0 fragments but collects input values
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.hiking_trail_frame_layout, fragment)
        fragmentTransaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // go to TrailDetailActivity on click
        val newTrailButton = findViewById<Button>(R.id.addTrail)
        newTrailButton.setOnClickListener{
            val intent = Intent(this, TrailDetailActivity::class.java)
            startActivity(intent)
        }

        val fragmentManager: FragmentManager = supportFragmentManager

        // define your fragments here
        val fragment1: Fragment = HikingTrailListFragment()
        val fragment2: Fragment = HikingDashboardFragment()
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // handle navigation selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment

            // when expression
            when (item.itemId) {

                R.id.hiking_trails -> fragment = fragment1
                R.id.trails_dashboard -> fragment = fragment2
            }
            replaceFragment(fragment)
            true
        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.hiking_trails


    }
}