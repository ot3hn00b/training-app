package com.example.trainingapp.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import java.time.LocalDate
import java.time.Period

class UserProfileRepository(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun hasBirthday(): Boolean {
        return preferences.contains(KEY_BIRTHDAY)
    }

    fun saveBirthday(year: Int, month: Int, day: Int) {
        val birthday = LocalDate.of(year, month, day)
        preferences.edit().putString(KEY_BIRTHDAY, birthday.toString()).apply()
        Log.d(TAG, "Birthday saved: $birthday, age now: ${getAgeInYears()}")
    }

    fun getBirthday(): LocalDate? {
        val stored = preferences.getString(KEY_BIRTHDAY, null) ?: return null
        return try {
            LocalDate.parse(stored)
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to parse stored birthday", exception)
            null
        }
    }

    fun getAgeInYears(): Int? {
        val birthday = getBirthday() ?: return null
        val today = LocalDate.now()
        if (birthday.isAfter(today)) return null
        return Period.between(birthday, today).years
    }

    companion object {
        private const val PREFS_NAME = "user_profile"
        private const val KEY_BIRTHDAY = "birthday"
        private const val TAG = "UserProfileRepository"
    }
}
