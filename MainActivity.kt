package com.kotlinproject.tictactoe_example

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.kotlinproject.tictactoe_example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    
    //region Erstellung der Variablen für die Activity
    // currentPlayer true steht für Spieler 1(X) / wenn false, dann Spieler 2(O)
    private var currentPlayer: Boolean = true
    // Variablen für SpielerZeichen und Anzeige Spieler
    private var playerX: String = "Spieler 1" //X
    private var playerO: String = "Spieler 2" //O
    // Variablen für Spielerpunkte
    private var scrP1 = 0
    private var scrP2 = 0
    // Variable für Matchsieger
    private var matchWinner: String = ""
    // variable für spielende
    private var matchFinished: Boolean = false
    // Variable für eine Liste, in der wir alle Buttons zusammenführen
    private lateinit var btnList: List<Button>
    // Variable für AlertDialog
    private lateinit var showEnd: AlertDialog
    // Einbindung der Binding-Class, um Zugriff auf unsere Views zu haben
    private lateinit var binding: ActivityMainBinding
    // Einbindung des lokalen Speichers und einem dazugehörigen Editor
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor

    //endregion
    
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //region Sonstiges
        
        //region AlertDialog bei Sieg
        showEnd = AlertDialog.Builder(this)
            .setView(R.layout.custom_alert)
            .setOnDismissListener {
                resetGame()
                Toast.makeText(this, "$matchWinner hat gewonnen.", Toast.LENGTH_SHORT).show()
            }
            .create()
        //endregion

        //region Initialisierung der SharedPreferences
        sharedPreferences = getSharedPreferences("unserSpeicher", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        //endregion

        //endregion

        //region Initialisierung der Variablen

        //region Score der Spieler eintragen

        // Abrufen der lokal gespeicherten Punktestände von Spieler 1 und Spieler 2 und den Variablen zuweisen
        scrP1 = sharedPreferences.getInt("scrP1", 0)
        scrP2 = sharedPreferences.getInt("scrP2", 0)

        binding.tvPlayer1Score.text = scrP1.toString()
        binding.tvPlayer2Score.text = scrP2.toString()

        //endregion


        //region Buttons in die Button-Liste einfügen (Initialisierung)
        btnList= listOf(binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7, binding.btn8,
            binding.btn9)
        //endregion

        //region Namen bei Activity-Start zuweisen
        // Mögliche mitgegebene IntentPakete abrufen (bei Appstart existieren diese nicht, daher "null")
        val getName1 = intent.getStringExtra("name1")
        // wenn getName1 nicht null ist (das geht nur, wenn die mainactivity von activity2 aus gestartet wurde)
        if (getName1!=null){
            playerX = getName1.toString()
            //dann wird der name aus der namensänderung eingesetzt
            binding.tvPlayer1Name.text = "$getName1 (X)"
        }else {
            // ansonsten, da appstart, wird die default variable eingesetzt
            binding.tvPlayer1Name.text = "$playerX (X)"
        }


        val getName2 = intent.getStringExtra("name2")
        if (getName2!=null){
            playerO = getName2
            binding.tvPlayer2Name.text = "$getName2 (O)"
        }else {
            binding.tvPlayer2Name.text = "$playerO (O)"
        }

        //endregion



        // tvNext gibt direkt an, wer beginnt
        binding.tvNext.text = "$playerX beginnt" //Hinweise aufgrund hardcoded Text. Wir belassen es dabei für diese BeispielApp

        //endregion

        //region Funktionen zuweisen
        // Funktion wird so ALLEN Feldern zugewiesen
        btnList.forEach { e -> e.setOnClickListener { newMarkField(e) } }


        binding.btnNewGame.setOnClickListener { resetGame() }

        binding.nextActBtn.setOnClickListener { nextScreen() }

        binding.resetBtn.setOnClickListener { resetScore() }

        //endregion
    }

    //region Funktionen
    // Funktion zum Überprüfen ob Siegszenario erfüllt
    private fun checkWinner(){
        // Siegbedingungen
        if (btnList[0].text == btnList[1].text && btnList[0].text == btnList[2].text && btnList[2].text.isNotEmpty()){ //row1
            //Log.i("SIEGER", "1,2,3")
            // match wird als "beendet" gekennzeichnet
            //matchFinished = true
            matchDone()
        } else if (btnList[3].text == btnList[4].text && btnList[3].text == btnList[5].text && btnList[5].text.isNotEmpty()){ //row2
            matchDone()
        } else if (btnList[6].text == btnList[7].text && btnList[6].text == btnList[8].text && btnList[8].text.isNotEmpty()){ //row3
            matchDone()
        } else if (btnList[0].text == btnList[3].text && btnList[0].text == btnList[6].text && btnList[6].text.isNotEmpty()){ //col1
            matchDone()
        } else if (btnList[1].text == btnList[4].text && btnList[1].text == btnList[7].text && btnList[7].text.isNotEmpty()){ //col2
            matchDone()
        } else if (btnList[2].text == btnList[5].text && btnList[2].text == btnList[8].text && btnList[8].text.isNotEmpty()){ //col3
            matchDone()
        } else if (btnList[0].text == btnList[4].text && btnList[0].text == btnList[8].text && btnList[8].text.isNotEmpty()){ //olur
            matchDone()
        } else if (btnList[2].text == btnList[4].text && btnList[2].text == btnList[6].text && btnList[6].text.isNotEmpty()){ //orul
            matchDone()
        }
    }

    // Funktion fürs Spielende
    private fun matchDone(){
        matchFinished = true
        showEnd.show()
        //wenn aktuell Spieler X dran wäre
        if (currentPlayer){
            //dann hat kurz zuvor Spieler O gewonnen
            matchWinner = playerO
            scrP2++
            editor.putInt("scrP2", scrP2).apply()
            binding.tvPlayer2Score.text = scrP2.toString()
        } else {
            // ansonsten hat Spieler X gewonnen.
            matchWinner = playerX
            scrP1++
            editor.putInt("scrP1", scrP1).apply()
            binding.tvPlayer1Score.text = scrP1.toString()
        }
    }
    // Resetten / New Game (Funktion für diese Aktion definieren)
    private fun resetGame() {
        // forEach geht einmal durch die Buttonliste und ändert den Text bei
        // jedem Spielfeld auf einen leeren String
        btnList.forEach { e -> e.text = "" }
        // belegbarkeit der Felder wieder herstellen
        matchFinished = false
    }

    // Feld belegen mit X oder O
    @SuppressLint("SetTextI18n") // Hinweis ignorieren
    private fun newMarkField(btn: Button){ //Paramater in Form des angeklickten Buttons
        // abfrage, ob spiel noch offen oder schon finished
        if (!matchFinished){
            // isEmpty = nur wenn das Feld leer ist, kann eine Veränderung/Belegung erfolgen
            if (btn.text.isEmpty()){
                if (currentPlayer){
                    btn.text = "X"
                    // wenn x gesetzt, hinweis auf nächsten Spieler
                    binding.tvNext.text = "$playerO ist am Zug"
                    currentPlayer = false
                }
                else {
                    btn.text = "O"
                    // wenn o gesetzt, hinweis auf nächsten Spieler
                    binding.tvNext.text = "$playerX ist am Zug"
                    currentPlayer = true
                }
            }
            // überprüfung ob Siegeszug
            checkWinner()
        }
    }

    // zum Screen für Namensänderung
    private fun nextScreen(){
        // starten einer anderen Activity (in der variable "intent" legen wir fest,
        // um welche Activity es sich handelt)
        val intent = Intent(this, SecondActivity::class.java)
        // wir geben die namen von Spieler 1 und 2 mit
        intent.putExtra("name1", playerX)
        intent.putExtra("name2", playerO)
        // starten der zugewiesenen Activity
        startActivity(intent)
    }

        // Funktion zum Resetten der Punktezähler(Variable, SharedPref, TextView)
    private fun resetScore(){
        // Zähler im lokalen Speicher auf 0
        editor.putInt("scrP1", 0).apply()
        editor.putInt("scrP2", 0).apply()
        // Variablen für Punkte auf 0
        scrP1 = 0
        scrP2 = 0
        // Punkteanzeige aktualisieren
        binding.tvPlayer1Score.text = scrP1.toString()
        binding.tvPlayer2Score.text = scrP2.toString()
    }

    //endregion
}
