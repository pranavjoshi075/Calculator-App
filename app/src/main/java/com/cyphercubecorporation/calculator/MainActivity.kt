package com.cyphercubecorporation.calculator

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.operator.Operator


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setOnClickListener for Numerical
        tvOne.setOnClickListener{appendExpression("1", true)}
        tvTwo.setOnClickListener{appendExpression("2", true)}
        tvThree.setOnClickListener{appendExpression("3", true)}
        tvFour.setOnClickListener{appendExpression("4", true)}
        tvFive.setOnClickListener{appendExpression("5", true)}
        tvSix.setOnClickListener{appendExpression("6", true)}
        tvSeven.setOnClickListener{appendExpression("7", true)}
        tvEight.setOnClickListener{appendExpression("8", true)}
        tvNine.setOnClickListener{appendExpression("9", true)}
        tvZero.setOnClickListener{appendExpression("0", true)}
        tvDot.setOnClickListener{appendExpression(".", true)}

        //setOnClickListener for Operators
        tvPlus.setOnClickListener{appendExpression("+", false)}
        tvMinus.setOnClickListener{appendExpression("-", false)}
        tvMul.setOnClickListener{appendExpression("*", false)}
        tvDivide.setOnClickListener{appendExpression("/", false)}
        tvOpen.setOnClickListener{appendExpression("(", false)}
        tvClose.setOnClickListener{appendExpression(")", false)}
        tvExponential.setOnClickListener{appendExpression("^", false)}
        tvSin.setOnClickListener{appendExpression("sin", false)}
        tvCos.setOnClickListener{appendExpression("cos", false)}
        tvTan.setOnClickListener{appendExpression("tan", false)}
        tvFactorial.setOnClickListener{appendExpression("!", false)}
        tvSquare.setOnClickListener{appendExpression("^2", false)}
        tvSquareRoot.setOnClickListener{appendExpression("√", false)}
        tvPercentage.setOnClickListener{appendExpression("%", false)}
        tvLn.setOnClickListener{appendExpression("ln", false)}
        tvLog.setOnClickListener{appendExpression("log", false)}

        //on landing page Hide the additional buttons by default
        var toggle : Boolean = true
        tvLn.visibility = View.GONE
        tvLog.visibility = View.GONE
        tvSin.visibility = View.GONE
        tvCos.visibility = View.GONE
        tvTan.visibility = View.GONE
        tvPercentage.visibility = View.GONE
        tvSquare.visibility = View.GONE
        tvSquareRoot.visibility = View.GONE
        tvExponential.visibility = View.GONE
        tvFactorial.visibility = View.GONE

        //Info Button
        buttonInfo.setOnClickListener{
            var intent  = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }
        //Toggle Button
        buttonToggle.setOnClickListener{
            //Animation for Toggle Button
            var animator :ObjectAnimator = ObjectAnimator.ofFloat(buttonToggle, "rotation", 0f, 180f)
            animator.setDuration(500)
            animator.start()

            toggle = !toggle
            if(toggle){
                // Hide the additional buttons
                tvLn.visibility = View.GONE
                tvLog.visibility = View.GONE
                tvSin.visibility = View.GONE
                tvCos.visibility = View.GONE
                tvTan.visibility = View.GONE
                tvPercentage.visibility = View.GONE
                tvSquare.visibility = View.GONE
                tvSquareRoot.visibility = View.GONE
                tvExponential.visibility = View.GONE
                tvFactorial.visibility = View.GONE
            }else{
                //Show additional buttons
                tvLn.visibility = View.VISIBLE
                tvLog.visibility = View.VISIBLE
                tvSin.visibility = View.VISIBLE
                tvCos.visibility = View.VISIBLE
                tvTan.visibility = View.VISIBLE
                tvPercentage.visibility = View.VISIBLE
                tvSquare.visibility = View.VISIBLE
                tvSquareRoot.visibility = View.VISIBLE
                tvExponential.visibility = View.VISIBLE
                tvFactorial.visibility = View.VISIBLE
            }
        }

        //Clear All Button
        tvClear.setOnClickListener{
            tvResult.text =""
            tvExpression.text =""
        }

        //Back Button
        tvBack.setOnClickListener{
            val string = tvExpression.text.toString()
            if(string.isNotBlank()){
                tvExpression.text = string.substring(0, string.length-1)
            }
            tvResult.text =""
        }

        //Equal Button
        tvEquals.setOnClickListener{
            try{
                var mathematicalExpression : String = tvExpression.text.toString()
                var sanitizedExpression : String = sanitize(mathematicalExpression)
                val input = ExpressionBuilder(sanitizedExpression).operator(factorial).build()
                val output = input.evaluate()
                val longOutput = output.toLong()
                if (output == longOutput.toDouble()){
                    tvResult.text = longOutput.toString()
                }else{
                    tvResult.text = output.toString()
                }
            }
            catch(e:Exception){
                Toast.makeText(this,e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    //Sanitize the string before passing it to ExpressionBuilder
    private fun sanitize(mathematicalExpression: String): String {
        return mathematicalExpression.replace("√","sqrt")
            .replace("%","/100")
            .replace("ln","log ")
            .replace("log","log10 ")
    }

    //append the serial string while typing the expression
    private fun appendExpression(string: String, canClear : Boolean){
        if(tvResult.text.isNotEmpty()){
            tvExpression.text = ""
        }

        if (canClear) {
            tvResult.text = ""
            tvExpression.append(string)
        } else {
            tvExpression.append(tvResult.text)
            tvExpression.append(string)
            tvResult.text = ""
        }
    }

    //Overriding the method from ExpressionBuilder for factorial implementation
    var factorial: Operator = object :
        Operator("!",1,true,PRECEDENCE_POWER + 1) {
        override fun apply(vararg args: Double): Double {
            val arg = args[0].toInt()
            require(arg.toDouble() == args[0]) { "Operand for factorial has to be an integer" }
            require(arg >= 0) { "The operand of the factorial can not be less than zero" }
            var result = 1.0
            for (i in 1..arg) {
                result *= i.toDouble()
            }
            return result
        }

    }
}