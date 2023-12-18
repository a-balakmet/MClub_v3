package kz.magnum.app.ui.screens.loginBlock.otpInput

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.RECEIVER_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

@Composable
fun SmsRetrieverUserConsentBroadcast(
    smsCodeLength: Int = 4,
    onSmsReceived: (message: String, code: String) -> Unit,
) {
    val context = LocalContext.current
    var shouldRegisterReceiver by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        SmsRetriever.getClient(context)
            .startSmsUserConsent(null)
            .addOnSuccessListener {
                shouldRegisterReceiver = true
            }
    }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                val message: String? = it.data!!.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                message?.let {
                    val verificationCode = getVerificationCodeFromSms(message, smsCodeLength)

                    onSmsReceived(message, verificationCode)
                }
                shouldRegisterReceiver = false
            }
            //else { }
        }
    if (shouldRegisterReceiver) {
        SystemBroadcastReceiver(
            systemAction = SmsRetriever.SMS_RETRIEVED_ACTION,
            broadCastPermission = SmsRetriever.SEND_PERMISSION,
        ) { intent ->
            if (intent != null && SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
                val extras = intent.extras
                val smsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as Status
                when (smsRetrieverStatus.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        // Get consent intent
                        val consentIntent = extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                        try {
                            // Start activity to show consent dialog to user, activity must be started in
                            // 5 minutes, otherwise you'll receive another TIMEOUT intent
                            launcher.launch(consentIntent)
                        } catch (e: ActivityNotFoundException) {
                            Log.e("sms error", e.toString())
                        }
                    }
                    //CommonStatusCodes.TIMEOUT -> {}
                }
            }
        }
    }
}


@Composable
fun SystemBroadcastReceiver(
    systemAction: String,
    broadCastPermission: String,
    onSystemEvent: (intent: Intent?) -> Unit
) {
    val context = LocalContext.current
    DisposableEffect(context, systemAction) {
        val intentFilter = IntentFilter(systemAction)
        val broadcast = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                onSystemEvent(intent)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(broadcast, intentFilter, RECEIVER_EXPORTED)
        } else {
            context.registerReceiver(broadcast, intentFilter)
        }
        onDispose { context.unregisterReceiver(broadcast) }
    }
    DisposableEffect(context, broadCastPermission) {
        val intentFilter = IntentFilter(broadCastPermission)
        val broadcast = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                onSystemEvent(intent)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(broadcast, intentFilter, RECEIVER_EXPORTED)
        } else {
            context.registerReceiver(broadcast, intentFilter)
        }
        onDispose { context.unregisterReceiver(broadcast) }
    }

}

internal fun getVerificationCodeFromSms(sms: String, smsCodeLength: Int): String =
    sms.filter { it.isDigit() }.substring(0 until smsCodeLength)