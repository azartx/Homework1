package com.example.homework5.data.staticData

import com.example.homework5.R

class Constants {

    // constants for some classes in project
    companion object {
        // intent tags
        const val OBJECT = "editCar"
        const val POSITION_CAR_IN_DB = "carId"
        const val PARENT_CAR = "parentCarName"

        // colors for work_activities
        const val PENDING = R.color.pendingColor
        const val IN_PROGRESS = R.color.inProgressColor
        const val COMPLETE = R.color.completeColor
        const val DEFAULT_COLOR = R.color.defaultColor

        // string constants for work_activities
        const val PROGRESS_PENDING = R.string.pending
        const val PROGRESS_IN_PROGRESS = R.string.in_progress
        const val PROGRESS_COMPLETE = R.string.complete

        // car fragments
        const val CAR_RECYCLE_FRAGMENT = 1
        const val CAR_ADD_FRAGMENT = 2
        const val CAR_EDIT_FRAGMENT = 3
        const val CAR_INFO_FRAGMENT = 4

        // work fragments
        const val WORK_RECYCLE_FRAGMENT = 5
        const val WORK_ADD_FRAGMENT = 6
        const val WORK_EDIT_FRAGMENT = 7
    }
}