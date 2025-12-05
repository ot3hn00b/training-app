package com.example.trainingapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.example.trainingapp.data.UserProfileRepository
import com.example.trainingapp.ui.theme.TrainingAppTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
    var showDatePicker by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    val dateFormatter = remember { DateTimeFormatter.ofPattern("MMM d, yyyy") }
    val confirmationDateFormatter = remember { DateTimeFormatter.ofPattern("d MMMM yyyy") }
    val colorScheme = MaterialTheme.colorScheme

    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = {
                Text(text = stringResource(id = R.string.confirm_birth_date_title))
            },
            text = {
                Text(
                    text = stringResource(
                        id = R.string.confirm_birth_date_message,
                        selectedDate.format(confirmationDateFormatter)
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showConfirmationDialog = false
                    selectedDate.let { date ->
                        onBirthdayConfirmed(date)
                        Toast.makeText(
                            context,
                            context.getString(R.string.birthday_saved_toast),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }) {
                    Text(text = stringResource(id = R.string.confirm_button_label))
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmationDialog = false }) {
                    Text(text = stringResource(id = R.string.change_date_button_label))
                }
            }
        )
    }

    if (showDatePicker) {
        DisposableEffect(Unit) {
            val listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
            }

            val dialog = DatePickerDialog(
                context,
                listener,
                selectedDate.year,
                selectedDate.monthValue - 1,
                selectedDate.dayOfMonth
            ).apply {
                datePicker.maxDate = System.currentTimeMillis()
                setOnDismissListener { showDatePicker = false }
            }

            dialog.show()

            onDispose {
                dialog.setOnDismissListener(null)
                dialog.dismiss()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = context.getString(R.string.birthday_title),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp
            ),
            color = colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = context.getString(R.string.birthday_explanation),
            style = MaterialTheme.typography.bodyLarge,
            color = colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = context.getString(R.string.birthday_picker_label),
                style = MaterialTheme.typography.titleMedium,
                color = colorScheme.onBackground,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = colorScheme.surface,
                shadowElevation = 1.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = selectedDate.format(dateFormatter),
                        style = MaterialTheme.typography.titleMedium,
                        color = colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = { showDatePicker = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorScheme.primary,
                            contentColor = colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = context.getString(R.string.select_date),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                showConfirmationDialog = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.primary,
                contentColor = colorScheme.onPrimary
            )
        ) {
            Text(
                text = context.getString(R.string.confirm_birthday),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
