package spiral.bit.dev.safy.views

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import dagger.hilt.android.AndroidEntryPoint
import spiral.bit.dev.safy.R
import javax.inject.Inject

@AndroidEntryPoint
class LockActivity : AppCompatActivity() {

    private lateinit var lockPatternView: PatternLockView

    @Inject
    lateinit var sharedPref: SharedPreferences
    private lateinit var labelHelpTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)

        labelHelpTv = findViewById(R.id.lock_help_tv)
        lockPatternView = findViewById(R.id.lock_pattern_view)
        if (sharedPref.contains("patternLockString")) labelHelpTv.text =
            getString(R.string.enter_graphic_key_label)
        else labelHelpTv.text = getString(R.string.set_graphic_key_label)
        lockPatternView.addPatternLockListener(object : PatternLockViewListener {
            override fun onStarted() {
            }

            override fun onProgress(progressPattern: MutableList<PatternLockView.Dot>?) {
            }

            override fun onComplete(pattern: MutableList<PatternLockView.Dot>?) {
                val patternString: String = pattern.toString()
                if (sharedPref.contains("patternLockString")) {
                    if (sharedPref.getString("patternLockString", "")
                            ?.equals(patternString) == true
                    ) {
                        startActivity(Intent(this@LockActivity, HideActivity::class.java))
                    } else {
                        Toast.makeText(
                            this@LockActivity,
                            getString(R.string.wrong_key_toast),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } else {
                    sharedPref.edit()
                        .putString("patternLockString", patternString)
                        .apply()
                    Toast.makeText(
                        this@LockActivity,
                        getString(R.string.graphic_key_set_toast),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    startActivity(Intent(this@LockActivity, HideActivity::class.java))
                }
            }

            override fun onCleared() {
            }
        })
    }
}