package pl.salo.stoneglish

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.presentation.auth.AuthActivity
import pl.salo.stoneglish.presentation.core.CoreActivity
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()

        val user = auth.currentUser
        Log.d(TAG, "Current user id is: ${user?.uid}")

        val intent = if (user != null) goToCoreActivity() else goToAuthActivity()
        startActivity(intent)
    }

    private fun goToAuthActivity() = Intent(this@MainActivity, AuthActivity::class.java)
    private fun goToCoreActivity() = Intent(this@MainActivity, CoreActivity::class.java)

}