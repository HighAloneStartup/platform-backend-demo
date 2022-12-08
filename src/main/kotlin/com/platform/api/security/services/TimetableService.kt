package com.platform.api.security.services

import com.platform.api.models.Timetable
import com.platform.api.models.User
import com.platform.api.payload.request.TimetableRequest
import com.platform.api.repository.TimetableRepository
import org.springframework.stereotype.Service
import java.sql.Time

@Service
class TimetableService(
    private val timetableRepository: TimetableRepository
) {
    open fun postTimetable(timetableRequest:TimetableRequest) : Timetable
    {
        val timetable = Timetable(
                name = timetableRequest.name,
                mon = timetableRequest.mon,
                tue = timetableRequest.tue,
                wed = timetableRequest.wed,
                thu = timetableRequest.thu,
                fri = timetableRequest.fri)

        return timetableRepository.save(timetable)
    }

    open fun getTimetable(user: User) : Timetable
    {

        val name : String = "22" + user.gradeYear.toString() + "0" + user.classGroup.toString()
        return timetableRepository.findByName(name)
    }

}