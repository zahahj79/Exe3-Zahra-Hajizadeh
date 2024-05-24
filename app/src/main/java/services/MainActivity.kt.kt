package services

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import services.ui.BroadCasterTheme

class `MainActivity` : ComponentActivity() {

    private val Broadcast = Broadcast()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(
            Broadcast,
            IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

        setContent {
            BroadCasterTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(onClick = {
                        Intent(applicationContext, LongRunningService::class.java).also {
                            it.action = LongRunningService.Actions.START.toString()
                            it.putExtra("state" , 1);
                            startService(it)
                        }
                    }) {
                        Text(text="Star Service")
                    }

                    Button(onClick = {3
                        Intent(applicationContext, LongRunningService::class.java).also {
                            it.action = LongRunningService.Actions.STOP.toString()
                            startService(it)
                        }
                    }) {
                        Text(text="Stop Service")
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(Broadcast)
    }
}