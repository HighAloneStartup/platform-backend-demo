package com.platform.api.payload.response

import com.platform.api.models.Timetable
import java.util.ArrayList

class TimetableResponse(
        timeTable : Timetable
) {
    val name = timeTable.name
    val mon = timeTable.mon
    val tue = timeTable.tue
    val wed = timeTable.wed
    val thu = timeTable.thu
    val fri = timeTable.fri
}