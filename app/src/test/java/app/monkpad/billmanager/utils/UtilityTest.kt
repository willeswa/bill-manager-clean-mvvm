package app.monkpad.billmanager.utils

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class UtilityTest {

    @Test
    fun testFormattedDate_takeLong_returnDateInString(){
        //GIVEN a timestamp in MilliSeconds
        val timestamp = 1642016475261

        //WHEN you format the date
        val formattedDate = Utility.formattedDate(1642016475261)

        //THEN the resulting date is a human readable string
        assertThat(formattedDate, `is`("Wed, 12 Jan 2022"))
    }

}