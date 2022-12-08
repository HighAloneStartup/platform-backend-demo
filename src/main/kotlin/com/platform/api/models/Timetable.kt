package com.platform.api.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.ArrayList

@Document(collection = "timetables")
class Timetable(
        @Id
        val id: String = String(),
        val name: String,
        val mon: ArrayList<String>,
        val tue: ArrayList<String>,
        val wed: ArrayList<String>,
        val thu: ArrayList<String>,
        val fri: ArrayList<String>
)