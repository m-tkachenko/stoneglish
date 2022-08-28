package pl.salo.stoneglish

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.salo.stoneglish.presentation.auth.AuthActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val intent = Intent(this@MainActivity, AuthActivity::class.java)
        startActivity(intent)
        super.onCreate(savedInstanceState)
    }
}