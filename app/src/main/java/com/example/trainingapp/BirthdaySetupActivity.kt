package com.example.trainingapp

import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.trainingapp.data.UserProfileRepository
import com.example.trainingapp.ui.theme.TrainingAppTheme
import java.time.LocalDate

class BirthdaySetupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrainingAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BirthdaySetupScreen(onBirthdayConfirmed = { date ->
                        val repository = UserProfileRepository(applicationContext)
                        repository.saveBirthday(date.year, date.monthValue, date.dayOfMonth)
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    })
                }
            }
        }
    }
}

@Composable
fun BirthdaySetupScreen(onBirthdayConfirmed: (LocalDate) -> Unit) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = context.getString(R.string.birthday_explanation),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        AndroidView(
            modifier = Modifier.padding(horizontal = 8.dp),
            factory = { ctx ->
                DatePicker(ctx).apply {
                    val current = selectedDate
                    init(
                        current.year,
                        current.monthValue - 1,
                        current.dayOfMonth
                    ) { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                        selectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
                    }
                }
            },
            update = { datePicker ->
                val current = selectedDate
                datePicker.updateDate(current.year, current.monthValue - 1, current.dayOfMonth)
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            selectedDate.let { date ->
                onBirthdayConfirmed(date)
                Toast.makeText(context, context.getString(R.string.birthday_saved_toast), Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = context.getString(R.string.confirm_birthday))
        }
    }
}
