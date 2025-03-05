package cat.jan.retrofitapi

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cat.jan.retrofitapi.API.retrofitService
import cat.jan.retrofitapi.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    var languages = emptyList<Language>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getLanguages()
    }

    private fun getLanguages() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // TODO: retrofitService.getLanguages() provoca "Unable to resolve host "ws.detectlanguage.com": No address associated with hostname"
                val result = retrofitService.getLanguages()

                if (result.isSuccessful) {
                    languages = result.body() ?: emptyList()
                    showSuccess()
                } else {
                    showError()
                }
            } catch (e: Exception) {
                Log.e("JAN", "" + e.message )
                showError()
            }
        }
    }

    private fun showSuccess() {
        runOnUiThread {
            if (languages.isNotEmpty()) {
                Toast.makeText(this, languages[0].name, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showError() {
        runOnUiThread {
            Toast.makeText(this, "Error de la peticio", Toast.LENGTH_SHORT).show()
        }
    }
}