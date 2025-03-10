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
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnInicia.setOnClickListener {
            val text = binding.edtParaula.text.toString()

            if (text.isNotEmpty()) {
                getTextLanguage(text)
            }
        }
    }

    private fun getTextLanguage(text: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = retrofitService.getTextLanguage(text)
            if (result.isSuccessful) {
                checkResult(result.body())
            } else {
                showError()
            }
        }
    }

    private fun checkResult(detectionResponse: DetectionResponse?) {
        if (detectionResponse != null && !detectionResponse.data.detections.isNullOrEmpty()) {
            val correctLanguages = detectionResponse.data.detections.filter {
                it.isReliable
            }
            if (correctLanguages.isNotEmpty()) {

                val langName = languages.find { it.code == correctLanguages.first().language }

                if (langName != null) {
                    runOnUiThread {
                        Toast.makeText(this,"L'idioma és: ${langName.name}", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun getLanguages() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // TODO: retrofitService.getLanguages() provoca
                //  "Unable to resolve host "ws.detectlanguage.com": No address associated with hostname"
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