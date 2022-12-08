package com.platform.api.payload.request

import java.util.ArrayList

class TimetableRequest (
    val name: String,
    val mon: ArrayList<String>,
    val tue: ArrayList<String>,
    val wed: ArrayList<String>,
    val thu: ArrayList<String>,
    val fri: ArrayList<String>
)
{

}