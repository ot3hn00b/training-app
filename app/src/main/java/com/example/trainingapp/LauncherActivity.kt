package com.example.trainingapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.trainingapp.data.UserProfileRepository

class LauncherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = UserProfileRepository(applicationContext)
        val target = if (repository.hasBirthday()) {
            MainActivity::class.java
        } else {
            BirthdaySetupActivity::class.java
        }
        startActivity(Intent(this, target))
        finish()
    }
}
