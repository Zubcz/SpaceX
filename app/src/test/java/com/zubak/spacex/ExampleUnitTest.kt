package com.zubak.spacex

import com.zubak.spacex.ui.filters.FiltersFragment
import kotlinx.android.synthetic.main.fragment_filters.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun onlyOneFilterSelected_isCorrect() {
        val filters = FiltersFragment()
        filters.mission_name_checkbox.isChecked = true
        filters.rocket_name_checkbox.isChecked = true
        assert(!filters.mission_name_checkbox.isChecked)
    }
}
