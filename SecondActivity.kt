package com.kotlinproject.tictactoe_example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kotlinproject.tictactoe_example.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    //region Erstellung der Variablen für die Activity
    private lateinit var binding: ActivitySecondBinding
    private lateinit var name1: String
    private lateinit var name2: String

    //endregion
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_second)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //region Initialisierung von Variablen
        name1 = intent.getStringExtra("name1").toString()
        name2 = intent.getStringExtra("name2").toString()

        // Spielernamen(welche übergeben wurden) werden den TextViews zugeordnet
        binding.tvOldPlayer1.text = name1
        binding.tvOldPlayer2.text = name2
        
        //endregion
        
        //region Funktionen zuweisen

        // Funktion für die Rückkehr zur Mainactivity
        binding.backBtn.setOnClickListener { goBack() }
        // Funktion zum überschreiben der Spielernamen
        binding.submitNames.setOnClickListener { changeNames() }

        //endregion
        
    }

    //region Funktionen
    
    // Funktion um Namens-Variablen neuen Wert zuzuweisen
    private fun changeNames() {
        //wird nur ausgeführt, wenn auch entsprechend Text Eingegeben wurde
        if (binding.inputP1.text.isNotEmpty())
            // name1 bekommt als neuen Wert, was entsprechend eingegeben wurde
            name1 = binding.inputP1.text.toString()

        if (binding.inputP2.text.isNotEmpty())
            name2 = binding.inputP2.text.toString()
    }

    // Funktion für die Rückkehr zur Mainactivity
    private fun goBack() {
        val intent = Intent(this, MainActivity::class.java)
        // namens-Variablen werden dem intent mitgegeben
        intent.putExtra("name1", name1)
        intent.putExtra("name2", name2)
        startActivity(intent)
    }
    
    //endregion
}
