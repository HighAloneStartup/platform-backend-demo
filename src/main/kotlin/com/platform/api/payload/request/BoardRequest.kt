package com.platform.api.payload.request

import javax.validation.constraints.NotBlank

class BoardRequest (
    var name: @NotBlank String,
    var roles: ArrayList<String>
    )