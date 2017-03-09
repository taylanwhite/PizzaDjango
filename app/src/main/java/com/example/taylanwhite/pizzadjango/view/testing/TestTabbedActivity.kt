package com.example.taylanwhite.pizzadjango.view.testing

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.example.taylanwhite.pizzadjango.R
import com.example.taylanwhite.pizzadjango.models.MeatToppingResults
import com.example.taylanwhite.pizzadjango.models.MeatToppings
import com.example.taylanwhite.pizzadjango.presenter.PizzaService
import com.example.taylanwhite.pizzadjango.presenter.TestFragmentRecycler
import izeni.pizza.presenter.DividerItemDecoration
import izeni.pizza.presenter.MeatAdapterRecycler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class TestTabbedActivity : AppCompatActivity() {
    lateinit var mAdapter: TestFragmentRecycler

    var recyclerView: RecyclerView? = null
    val pizzaList = ArrayList<MeatToppingResults>()


    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [FragmentPagerAdapter] derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    /**
     * The [ViewPager] that will host the section contents.
     */
    private var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_tabbed)

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container) as ViewPager
        mViewPager!!.adapter = mSectionsPagerAdapter

        mAdapter = TestFragmentRecycler(pizzaList)
        recyclerView = findViewById(R.id.recycler_view) as? RecyclerView
        val mLayoutManager = LinearLayoutManager(this@TestTabbedActivity)
        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.addItemDecoration(DividerItemDecoration(this@TestTabbedActivity, LinearLayoutManager.VERTICAL))
        recyclerView?.adapter = mAdapter
        getMeats()


        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_test_tabbed, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {
        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater!!.inflate(R.layout.activity_meat_options, container, false)
            val textView = rootView.findViewById(R.id.section_label) as? TextView
            rootView.setBackgroundColor(Color.WHITE)


            textView?.text = getString(R.string.section_format, arguments.getInt(ARG_SECTION_NUMBER))
            return rootView

        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "test"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            // Show 10 total pages.
            return 10
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "SECTION 1"
                1 -> return "SECTION 11"
                2 -> return "SECTION 3"
                3 -> return "SECTION 4"
                4 -> return "SECTION 5"
                5 -> return "SECTION 6"
                6 -> return "SECTION 7"
                7 -> return "SECTION 8"
                8 -> return "SECTION 9"
                9 -> return "SECTION 10"
            }
            return null
        }
    }

    private fun getMeats(page: Int? = null) {
        var subStr = ""
        PizzaService.api.getMeatToppings(page).enqueue(object : Callback<MeatToppings> {
            override fun onFailure(call: Call<MeatToppings>?, t: Throwable?) {
                val connectionError = "Could not connect to service. (Are you connected to the internet?)"
                Toast.makeText(this@TestTabbedActivity, connectionError, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<MeatToppings>?, response: Response<MeatToppings>?) {
                if (response?.isSuccessful ?: false) {
                    response?.body()?.let { response ->


                        if (response.next != null) {

                            subStr = response.next.substring(response.next.length - 1)

                        }


                        if (response.next != null) {
                            getMeats(subStr.toInt())
//                           page = response.next
                        }

                        for (item in response.results) {
                            pizzaList.add(item)
                            //reverse arraylist to show todays deal first
                            //Collections.reverse(pizzaList)

                            mAdapter.notifyDataSetChanged()


                        }
                    }
                }
            }

        })
    }
}
