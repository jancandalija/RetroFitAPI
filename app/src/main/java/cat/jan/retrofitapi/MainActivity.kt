package cat.jan.retrofitapi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cat.jan.retrofitapi.API.retrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var languages = emptyList<Language>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getLanguages()
    }

    private fun getLanguages() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = retrofitService.getLanguages()

            if (result.isSuccessful) {
                languages = result.body() ?: emptyList()
            } else {
                showError()
            }
        }
    }

    private fun showError() {
        runOnUiThread {
            Toast.makeText(this, "Error de la peticio", Toast.LENGTH_SHORT).show()
        }
    }
}