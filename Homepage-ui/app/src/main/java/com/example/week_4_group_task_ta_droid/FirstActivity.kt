package com.example.week_4_group_task_ta_droid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_first.*
import kotlin.math.abs

/**
 * Activity for the first task contains a viewpager and recyclerviews used to recreate a given UI
 */
class FirstActivity : AppCompatActivity() {

    /* List of tasks item used to populate the recycler view for task items
     * with their respective image and description
     */
    private val tasks = listOf(
            Tasks(R.drawable.ic_calendar, "Agenda"),
            Tasks(R.drawable.ic_lecture, "Speakers"),
            Tasks(R.drawable.ic_solution, "Sponsors"),
            Tasks(R.drawable.ic_cafe, "Exhibitors"),
            Tasks(R.drawable.ic_add_message, "Contact us"),
            Tasks(R.drawable.ic_map, "Map"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        // Sets up an adapter and layoutManager for the taskItems
        val taskItemsAdapter = TaskItemsAdapter(this, tasks)
        val taskLayoutManager = GridLayoutManager(this, resources.getInteger(R.integer.task_grid_span))

        taskItems.apply {
            adapter = taskItemsAdapter
            layoutManager = taskLayoutManager
        }

        // Sets up an adapter and layoutManager for the twitterFeeds
        val twitterFeedsLayoutManager = LinearLayoutManager(this)
        val twitterFeedsAdapter = TwitterFeedsAdapter(this, resources.getInteger(R.integer.twitter_feeds_item))
        twitterFeeds.apply {
            adapter = twitterFeedsAdapter
            layoutManager = twitterFeedsLayoutManager
        }



        myViewPager.apply {
            // Configures the adapter of the viewpager to the ViewPagerAdapter class created
            adapter = ViewPagerAdapter( 3)

            // Sets the viewpager not to clip its children and resize
            clipToPadding = false

            // The child of the viewgroup isnt clipped to its bound
            clipChildren = false
            offscreenPageLimit = 2  // make sure left/right item is rendered
        }

        // Increases the distance in pixels between any two pages
        val marginTransformer = MarginPageTransformer(30)

        /*
         Allows for combination of multiple transformation that is to be performed which includes
         the marginPage transformation and reduction in Y axis for the page based on the page
         and position of the item in the viewPager by 0.25 page 0 -> front and center
         */
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(marginTransformer)
        compositePageTransformer.addTransformer { page, position ->
            page.scaleY = 1 - (0.25f * abs(position))
        }

        myViewPager.setPageTransformer(compositePageTransformer)

        /*
         * Hooks up the viewpager with the tabs layout which defines horizontal tabs
         * in which each tab is replaced with a dot based on the number of items in the
         * viewpager
         */
        TabLayoutMediator(tabDots, myViewPager) { tab, _ ->
            myViewPager.setCurrentItem(tab.position, true)
        }.attach()

    }

    // Creates the menu item for the toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}