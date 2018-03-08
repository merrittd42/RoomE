package com.inallofexistence.greatestdevelopersever.roome.model

import java.util.Date

data class Bill(var name: String, var paidBy: User, var totalDue: String, var paidUsers: List<User>, var endDate: Date)
