package spiral.bit.dev.safy.views

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import dagger.hilt.android.AndroidEntryPoint
import net.objecthunter.exp4j.ExpressionBuilder
import spiral.bit.dev.safy.R
import spiral.bit.dev.safy.databinding.ActivityCalcBinding
import javax.inject.Inject

@AndroidEntryPoint
class CalcActivity : AppCompatActivity() {

    private lateinit var calcBinding: ActivityCalcBinding

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calcBinding = ActivityCalcBinding.inflate(layoutInflater)
        setContentView(calcBinding.root)
        initViews()
        initHint()
    }

    private fun initHint() {
        if (!sharedPref.getBoolean("firstTime", false)) {
            TapTargetView.showFor(this,
                TapTarget.forView(
                    findViewById(R.id.txt_calc_label),
                    "Удерживайте на надписи",
                    "Чтобы попасть в приватное хранилище"
                )
                    .outerCircleColor(R.color.white)
                    .outerCircleAlpha(0.96f)
                    .targetCircleColor(R.color.light_green)
                    .titleTextSize(20)
                    .titleTextColor(R.color.black)
                    .descriptionTextSize(15)
                    .descriptionTextColor(R.color.black)
                    .textColor(R.color.black)
                    .textTypeface(Typeface.DEFAULT_BOLD)
                    .dimColor(R.color.white)
                    .drawShadow(true)
                    .cancelable(true)
                    .tintTarget(true)
                    .transparentTarget(false)
                    .icon(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_slide_show
                        )
                    )
                    .targetRadius(30),
                object : TapTargetView.Listener() {
                    override fun onTargetClick(view: TapTargetView) {
                        super.onTargetClick(view)
                        view.dismiss(true)
                    }
                })
            sharedPref.edit().putBoolean("firstTime", true).apply()
        }
    }

    private fun initViews() {
        calcBinding.btnCalcNum0.setOnClickListener {
            setNumOrOperation("0")
        }

        calcBinding.btnCalcNum1.setOnClickListener {
            setNumOrOperation("1")
        }

        calcBinding.btnCalcNum2.setOnClickListener {
            setNumOrOperation("2")
        }

        calcBinding.btnCalcNum3.setOnClickListener {
            setNumOrOperation("3")
        }

        calcBinding.btnCalcNum4.setOnClickListener {
            setNumOrOperation("4")
        }

        calcBinding.btnCalcNum5.setOnClickListener {
            setNumOrOperation("5")
        }

        calcBinding.btnCalcNum6.setOnClickListener {
            setNumOrOperation("6")
        }

        calcBinding.btnCalcNum7.setOnClickListener {
            setNumOrOperation("7")
        }

        calcBinding.btnCalcNum8.setOnClickListener {
            setNumOrOperation("8")
        }

        calcBinding.btnCalcNum9.setOnClickListener {
            setNumOrOperation("9")
        }

        calcBinding.btnOperationDel.setOnClickListener {
            calcBinding.etInputNumbers.setText("")
            calcBinding.txtResult.text = ""
        }

        calcBinding.btnOperationMultiply.setOnClickListener {
            setNumOrOperation(" * ")
        }

        calcBinding.btnOperationDivide.setOnClickListener {
            setNumOrOperation(" / ")
        }

        calcBinding.btnOperationMinus.setOnClickListener {
            setNumOrOperation(" - ")
        }

        calcBinding.btnOperationPlus.setOnClickListener {
            setNumOrOperation(" + ")
        }

        calcBinding.btnCalcPoint.setOnClickListener {
            setNumOrOperation(".")
        }

        calcBinding.btnOperationBack.setOnClickListener {
            val string = calcBinding.etInputNumbers.text.toString()
            if (string.isNotEmpty()) {
                calcBinding.etInputNumbers.setText(string.substring(0, string.length - 1))
            }
            calcBinding.txtResult.text = ""
        }

        calcBinding.btnOperationEquals.setOnClickListener {
            try {
                val ex = ExpressionBuilder(calcBinding.etInputNumbers.text.toString()).build()
                val result = ex.evaluate()
                val longRes = result.toLong()
                if (result == longRes.toDouble()) {
                    calcBinding.txtResult.text = longRes.toString()
                } else {
                    calcBinding.txtResult.text = result.toString()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        calcBinding.txtCalcLabel.setOnLongClickListener {
            startActivity(Intent(this, LockActivity::class.java))
            return@setOnLongClickListener true
        }
    }

    private fun setNumOrOperation(str: String) {
        calcBinding.etInputNumbers.append(str)
    }
}