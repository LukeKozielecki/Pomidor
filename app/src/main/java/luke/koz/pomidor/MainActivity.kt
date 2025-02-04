package luke.koz.pomidor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import luke.koz.pomidor.ui.PomPomScreen
import luke.koz.pomidor.ui.theme.PomidorTheme
import luke.koz.pomidor.utils.NotificationUtils
import luke.koz.pomidor.viewmodel.PomViewModel
import luke.koz.pomidor.viewmodel.PomViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: PomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        NotificationUtils.createNotificationChannel(this)
        viewModel = ViewModelProvider(
            this, PomViewModelFactory()
        )[PomViewModel::class.java]
        setContent {
            PomidorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PomPomScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}