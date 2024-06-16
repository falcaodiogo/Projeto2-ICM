package ua.deti.pt.wearosapp.repositories

import androidx.health.services.client.data.ComparisonType
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.DataTypeCondition
import androidx.health.services.client.data.PassiveGoal

val dailyStepsGoal by lazy {
    val condition = DataTypeCondition(
        dataType = DataType.STEPS_DAILY,
        threshold = 3, // trigger every threshold steps
        comparisonType = ComparisonType.GREATER_THAN_OR_EQUAL
    )
    PassiveGoal(condition)
}

val floorsGoal by lazy {
    val condition = DataTypeCondition(
        dataType = DataType.FLOORS_DAILY,
        threshold = 1.0, // trigger every threshold floors
        comparisonType = ComparisonType.GREATER_THAN_OR_EQUAL
    )
    PassiveGoal(condition)
}