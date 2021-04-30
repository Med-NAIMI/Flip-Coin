package com.example.coinflip

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import kotlin.math.round

class MainActivity : AppCompatActivity() {
    //Declaring references to components that will change via functions
    lateinit var coinIcon:ImageView
    lateinit var totalFlips:TextView
    lateinit var  totalHeads:TextView
    lateinit var totalTails:TextView
    lateinit var headsPercentage:TextView
    lateinit var tailsPercentage:TextView
    lateinit var headsProgress: ProgressBar
    lateinit var tailsProgress: ProgressBar
    lateinit var numberOfSimulations:EditText

    //the button here will e modified
    lateinit var simulate:Button

    private var heads=0
    private var tails=0
    private var total=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Getting references to buttons that the value will not change after by a method
        val flip:Button= findViewById(R.id.main_activity_bt_flip)
        val reset:Button=findViewById(R.id.main_activity_bt_reset)
        simulate=findViewById(R.id.main_activity_bt_simulate)
        val simulationSwitch:SwitchCompat=findViewById(R.id.main_activity_sw_simulate)

        //getting references to editable layouts
        coinIcon=findViewById(R.id.main_Activity_iv_coin)
        totalFlips=findViewById(R.id.main_activity_tv_flips)
        totalHeads=findViewById(R.id.main_activity_tv_heads)
        totalTails=findViewById(R.id.main_activity_tv_tails)
        headsPercentage=findViewById(R.id.main_activity_tv_heads_percent)
        tailsPercentage=findViewById(R.id.main_activity_tv_tails_percent)
        headsProgress=findViewById(R.id.main_activity_pb_heads)
        tailsProgress=findViewById(R.id.main_activity_pb_tails)
        numberOfSimulations=findViewById(R.id.main_activity_et_submit_number)

        //calling event listeners
        flip.setOnClickListener { flipCoin() }
        reset.setOnClickListener { update("reset") }
        simulate.setOnClickListener { simulate() }
        simulationSwitch.setOnCheckedChangeListener { buttonView, isChecked -> switchToSimulationMode(isChecked) }


    }

    //flipping the coin is simulated by generating random value 0 or 1
    private fun flipCoin() {
        var randomNumber=(0..1).random()

        //update the laypouts based on the flip's results "update method"
        if(randomNumber==0)
        {
            update("head")
        }else{
            update("tail")
        }
    }
    private fun update(result: String){

        //editing counters accordingly
        if(result=="head"){
            heads++
            //display head as an icon
            coinIcon.setImageResource(R.drawable.ic_head)
            total++
        }else if(result=="tail"){
            tails++
            //display tail as an icon
            coinIcon.setImageResource(R.drawable.ic_tail)
            total++

        }else if(result=="reset"){//reset data
            //rendering the default image
            coinIcon.setImageResource(R.drawable.ic_thumb_up)

            //setting counters back to 0
            heads=0
            tails=0
            total=0
        }

        //updating views
        totalFlips.text= "total flips: $total "
        totalHeads.text= "total heads: $heads "
        totalTails.text= "total tails: $tails "


        //handling statistics via updateStatistics() method
        updateStatistics()
    }


    private fun updateStatistics() {
        var headsPercentageNumber="0"
        var tailsPercentageNumber="0"

        if(total!=0){
            //calculating percentages

            //var headsPercentageNumber= round(heads.toDouble()/total *100)
            // var tailsPercentageNumber= round(tails.toDouble()/total *100)

            //using string.format() to keep 2 digits after comma
            headsPercentageNumber= String.format("%.2f", heads.toDouble()/total *100)
            tailsPercentageNumber= String.format("%.2f", tails.toDouble()/total *100)
        }

        //updating the text views
        headsPercentage.text="heads: $headsPercentageNumber %"
        tailsPercentage.text="tails: $tailsPercentageNumber %"

        //updating progress bars
        headsProgress.progress=headsPercentageNumber.toDouble().toInt()
        tailsProgress.progress=tailsPercentageNumber.toDouble().toInt()
    }


    //we ve refactored the code, now update("reset") is taking care of the reset event
    private fun reset() {
        //rendering the default image
        coinIcon.setImageResource(R.drawable.ic_thumb_up)

        //setting counters back to 0
        heads=0
        tails=0
        total=0

        //updating views
        totalFlips.text= "total flips: $total "
        totalHeads.text= "total heads: $heads "
        totalTails.text= "total tails: $tails "


        //handling statistics via updateStatistics() method
        updateStatistics()

    }

    private fun simulate() {
        //by defaute simulate = flip one time, if the submitted number =0 or =""
        var numberOfSimulationsToMake=1

        //if no
        var numberOfSimulationsFromInput= numberOfSimulations.text.toString()
        if(numberOfSimulationsFromInput !="" && numberOfSimulationsFromInput!="0"){
            numberOfSimulationsToMake=numberOfSimulationsFromInput.toInt()
        }


        for (i in 1..numberOfSimulationsToMake){
            flipCoin()
        }

        //hide keybord after submitting a number
        hideKeyboard()

        //clear input
        numberOfSimulations.setText("")
    }

    private fun hideKeyboard() {
        val imm=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(numberOfSimulations.windowToken,0)
    }

    private fun  switchToSimulationMode(isChecked: Boolean) {

        if(isChecked){
            //display simulation stuff
            numberOfSimulations.visibility=View.VISIBLE
            simulate.visibility=View.VISIBLE
        }else{
            //hide simulation stuff
            numberOfSimulations.visibility=View.INVISIBLE
            simulate.visibility=View.INVISIBLE
        }
    }
}