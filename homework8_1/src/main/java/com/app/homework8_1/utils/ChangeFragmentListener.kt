package com.app.homework8_1.utils

import android.os.Bundle

/**
 *  Слушатель смены фрагментов
 */

interface ChangeFragmentListener {
    fun onChangeFragment(id: Int, args: Bundle?)
}