package com.example.mytictactoe

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.mytictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    //region ViewBinding & lokaler Speicher
    private lateinit var binding: ActivityMainBinding
    // lateinit, weil wir zum jetzigen Zeitpunkt noch nicht auf die bindings der Button zugriff haben

    // um den localen Speicher des mobilen Endgerätes nutzen zu können:
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor

    //endregion

    //region Variablen

    // Variable für eine Liste an Buttons
    private lateinit var btnList: List<Button>
    // Variable für den aktuellen Spieler
    private var currentPlayer = "X"
    // Variable für Sperrung des Spielfeldes
    private var isLocked = false
    // Variablen für Spieler 1 und 2
    private var player1 = "Tick (X)"
    private var player2 = "Track (O)"
    // Variablen für Spielerpunkte
    private var scrP1 = 0
    private var scrP2 = 0

    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        //region Initialisierungen & Variablen

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialisierung der btnList
        btnList = listOf<Button>(binding.btn1, binding.btn2, binding.btn3, binding.btn4, binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9)
        // jeder button in dieser Liste bekommt die markField-Funktion in den setOnClickListener
        btnList.forEach { button -> button.setOnClickListener { markField(button) } }


        // Initialisierung der sharedPreferences:
        sharedPreferences = getSharedPreferences("unserSpeicher", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        // gespeicherte Punktestände den Variablen zuordnen
        scrP1 = sharedPreferences.getInt("Score1", 0)
        scrP2 = sharedPreferences.getInt("Score2", 0)

        // Variablen zum Ablegen der erhaltenen Namen aus MainActivity2 (via Intent)
        val name1 = intent.getStringExtra("name1")
        val name2 = intent.getStringExtra("name2")

        //endregion

        //region TextViews
        // Spielernamen werden den TextViews zugeordnet, sofern welche übergeben wurden
        if (name1!=null){
            binding.player1Tv.text = name1
        } else {
            binding.player1Tv.text = player1
        }
        if (name2!=null){
            binding.player2Tv.text = name2
        } else {
            binding.player2Tv.text = player2
        }

        // Punktestände den TextViews zuordnen:
        binding.pl1Score.text = scrP1.toString()
        binding.pl2Score.text = scrP2.toString()

        //endregion

        //region Buttons
        // Restart-Button wird die clearAll-Funktion zugewiesen
        binding.restartBtn.setOnClickListener { clearAll() }
        binding.resetBtn.setOnClickListener { resetScore() }
        binding.nextActBtn.setOnClickListener { nextScreen() }
        //endregion

/*       //Beispiele:

        // um etwas im lokalen Speicher abzugelegen
        editor.putInt("test", 0).apply()

        // um aus dem lokalen Speicher auszulesen ("key", <defaultwert>)
        var test = sharedPreferences.getInt("test",0)
        Log.i("SP", "$test")

        // 1. zu Beginn spielerpunkte mit wert aus lokalem speicher überschreiben (hier könnte ein Problem auftreten)
        // 2. nach jedem sieg, punktestand aus lokalem speicher überschreiben

        // 3. button implementieren, mit dem die Punktestände resettet werden*/

    } // <- Ende: onCreate()

    // Funktionen

    //region markField()
    // Funktion zum Setzen von X oder O
    private fun markField(myBtn: Button){
        // nur ausführen, wenn das Feld leer ist

        // wenn die Variable für den Locked-Status auf false steht (so initialisiert)
        if (!isLocked){
            if (myBtn.text.isEmpty()){
                // wenn currentPlayer X ist...
                if (currentPlayer == "X") {
                    // ...wird ein X gesetzt
                    myBtn.text = "X"
                    // und currentPlayer geändert
                    currentPlayer = "O"
                } else {
                    myBtn.text = "O"
                    currentPlayer = "X"
                }
            }
            // anschließende Überprüfung der Siegbedingungen
            checkWin()
        }

    } //<- fun markField()
    //endregion

    //region clearAll()
    // Funktion zum Aufräumen (Alle Felder leeren)
    private fun clearAll(){
        btnList.forEach { it.text = "" }
        // isLocked wieder auf false, damit erneut gespielt werden kann
        isLocked = false
    } //<- fun clearAll()
    //endregion

/* //Beispiele

    Text-Änderung - Beispiel
   private fun showX(){
        binding.btn1.text = "X"
    }*/

    //region checkWin()
    // Funktion zum Überprüfen der Siegbedingungen (3 in a row)
    private fun checkWin(){
        //Abfrage Reihe 1
        if (btnList[0].text == btnList[1].text && btnList[0].text  == btnList[2].text && btnList[0].text.isNotEmpty()) {
            // Für Ausgabe im Logcat
            // Filter: package:mine tag:WIN <- entsprechend dem selbst gewählten tag
            //Log.i("WIN", "winner")
            // "Spielfeld sperren"
            // isLocked = true
            callWinner()
        }
        //Abfrage Reihe 2
        else if (btnList[3].text == btnList[4].text && btnList[3].text  == btnList[5].text && btnList[3].text.isNotEmpty()) {
            callWinner()
        }
        //Abfrage Reihe 3
        else if (btnList[6].text == btnList[7].text && btnList[6].text  == btnList[8].text && btnList[6].text.isNotEmpty()) {
            callWinner()
        }
        //Abfrage Spalte 1
        else if (btnList[0].text == btnList[3].text && btnList[0].text  == btnList[6].text && btnList[0].text.isNotEmpty()) {
            callWinner()
        }
        //Abfrage Spalte 2
        else if (btnList[1].text == btnList[4].text && btnList[1].text  == btnList[7].text && btnList[1].text.isNotEmpty()) {
            callWinner()
        }
        //Abfrage Spalte 3
        else if (btnList[2].text == btnList[5].text && btnList[2].text  == btnList[8].text && btnList[2].text.isNotEmpty()) {
            callWinner()
        }
        //Abfrage Diagonale links oben - rechts unten
        else if (btnList[0].text == btnList[4].text && btnList[0].text  == btnList[8].text && btnList[0].text.isNotEmpty()) {
            callWinner()
        }
        //Abfrage Diagonale links unten - rechts oben
        else if (btnList[2].text == btnList[4].text && btnList[2].text  == btnList[6].text && btnList[2].text.isNotEmpty()) {
            callWinner()
        } else {
            Log.e("active" , "no winner")
        }
    } //<- fun checkWin()
    //endregion

    //region callWinner()
    private fun callWinner(){
        isLocked = true
        if (currentPlayer == "O") {
            // score von P1 um 1 erhöhen
            scrP1++
            // Punkteanzeige mit dem neuen Punktestand (scrP1) überschreiben
            binding.pl1Score.text = scrP1.toString()
            editor.putInt("Score1", scrP1).apply()
            Log.i("TWI", "$player1 hat gewonnen")
        } else {
            scrP2++
            binding.pl2Score.text = scrP2.toString()
            editor.putInt("Score2", scrP2).apply()
            Log.i("TWI", "$player2 hat gewonnen")}
    } //<- fun callWinner()
    //endregion

    //region resetScore()
    private fun resetScore(){
        editor.putInt("Score1", 0)
        editor.putInt("Score2", 0)
        scrP1 = 0
        scrP2 = 0
        binding.pl1Score.text = scrP1.toString()
        binding.pl2Score.text = scrP2.toString()

    }
    //endregion

    //region nextScreen()
    private fun nextScreen(){
        // starten einer anderen Activity (in der variable "intent" legen wir fest,
        // um welche Activity es sich handelt)
        val intent = Intent(this, MainActivity2::class.java)
        // wir geben die namen von Spieler 1 und 2 mit
        intent.putExtra("name1", binding.player1Tv.text.toString())
        intent.putExtra("name2", binding.player2Tv.text.toString())
        // starten der zugewiesenen Activity
        startActivity(intent)
    }
    //endregion

} // <- Ende: MainActivity

