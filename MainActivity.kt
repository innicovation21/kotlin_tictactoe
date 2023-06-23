package com.kotlinproject.tictactoekotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.kotlinproject.tictactoekotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //region Baustellen

    //region MVP (Minimum Viable Product)
    // belegtes Feld, darf nicht verändert werden können
    // Resetten / New Game
    // Info über aktuellen Spieler
    //TODO Siegbedingung und Folgen
    //TODO (NEU) Info über Sieger / Unentschieden
    //endregion

    //region Feature I
    //TODO Spielernamenanzeige
    //TODO Punktezähler
    //endregion

    //region Feature II (lokaler Speicher + SecondActivity)
    //TODO (NEU) zusätzliche Activity für Namenseingabe, Speicherung und Zuweisung
    //TODO (NEU) Lokale Speicherung der Spielerinformationen
    //TODO (NEU) Activity wechseln können (von Main zu Second und zurück)
    //TODO (NEU) Datentransfer zwischen Activities (Bsp neuer Name)
    //endregion

    //region Feature III
    //TODO (NEU) Anzeige Siege insgesamt
    //TODO (NEU) Liste aller eingetragenen Spieler
    //endregion

    //endregion


    //region Erstellung der Variablen für die Activity

    //TODO Info über aktuellen Spieler
    // Variablen für SpielerZeichen und Anzeige Spieler
    private val playerX: String = "X"
    private val playerO: String = "O"
    // Variable für eine Liste, in der wir alle Buttons zusammenführen
    private lateinit var btnList: List<Button>
    // currentPlayer true steht für Spieler 1 / wenn false, dann Spieler 2
    private var currentPlayer: Boolean = true

    // Einbindung der Binding-Class, um Zugriff auf unsere Views zu haben
    private lateinit var binding: ActivityMainBinding

    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Verwendung der Binding-Class
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //region Initialisierung der Variablen

        // tvNext gibt direkt an, wer beginnt
        binding.tvNext.text = "Spieler $playerX beginnt"

        // Buttons in die Button-Liste einfügen (Initialisierung)
        btnList= listOf(binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7, binding.btn8,
            binding.btn9)

        //endregion

        //region Funktionen zuweisen

        //ALT: binding.btn1.setOnClickListener { markField() } // Problem: gilt nur für btn1

        // e = Element der Liste (ein Button, weil Liste nur Buttons enthalten kann)
        // Funktion wird so ALLEN Feldern zugewiesen
        btnList.forEach { e -> e.setOnClickListener { newMarkField(e) } }

        //Resetten / New Game (Funktion einem Button zuweisen)
        binding.btnReset.setOnClickListener { resetGame() }

        //endregion
    }

    //TODO Siegbedingung und Folgen
    // Funktion zu Abfrage ob Siegbedingung erüllt
    private fun checkWinner(){
        // erste siegbedingung
        if (btnList[0].text == btnList[1].text && btnList[0].text == btnList[2].text && btnList[2].text.isNotEmpty()){ //row1
            Log.i("SIEGER", "1,2,3")
        } else if (btnList[3].text == btnList[4].text && btnList[3].text == btnList[5].text && btnList[5].text.isNotEmpty()){ //row2
            Log.i("SIEGER", "4,5,6")
        } else if (btnList[6].text == btnList[7].text && btnList[6].text == btnList[8].text && btnList[8].text.isNotEmpty()){ //row3
            Log.i("SIEGER", "7,8,9")
        } else if (btnList[0].text == btnList[3].text && btnList[0].text == btnList[6].text && btnList[6].text.isNotEmpty()){ //col1
            Log.i("SIEGER", "1,4,7")
        } else if (btnList[1].text == btnList[4].text && btnList[1].text == btnList[7].text && btnList[7].text.isNotEmpty()){ //col2
            Log.i("SIEGER", "2,5,8")
        } else if (btnList[2].text == btnList[5].text && btnList[2].text == btnList[8].text && btnList[8].text.isNotEmpty()){ //col3
            Log.i("SIEGER", "3,6,9")
        } else if (btnList[0].text == btnList[4].text && btnList[0].text == btnList[8].text && btnList[8].text.isNotEmpty()){ //olur
            Log.i("SIEGER", "1,5,9")
        } else if (btnList[2].text == btnList[4].text && btnList[2].text == btnList[6].text && btnList[6].text.isNotEmpty()){ //orul
            Log.i("SIEGER", "3,5,7")
        }
    }


    // Resetten / New Game (Funktion für diese Aktion definieren)
    private fun resetGame() {
        // forEach geht einmal durch die Buttonliste und ändert den Text bei
        // jedem Spielfeld auf einen leeren String
        btnList.forEach { e -> e.text = "" }
    }

    // Funktion zum Markieren der Felder (Abhängig vom Spieler(currentPlayer))
    // Problem: Nicht wiederverwendbar, weil gilt nur für btn1
    private fun markField() {
        // Bedingung: currentPlayer = true -> dann:
        if (currentPlayer) {
            // btn1.text ein "X" einfügen
            binding.btn1.text = "X"
            // und Orientierungsvariable auf "false" switchen
            currentPlayer = false
        } else { // ansonsten:
            // btn1.text ein "O" einfügen
            binding.btn1.text = "O"
            // und Orientierungsvariable auf "true" switchen
            currentPlayer = true
        }
    }

    // Lösung: Wiederverwendbare Funktion für Buttons/Felder
    // Feld belegen mit X oder O
    @SuppressLint("SetTextI18n") // Hinweis ignorieren
    private fun newMarkField(btn: Button){ //Paramater in Form des angeklickten Buttons
        // isEmpty = nur wenn das Feld leer ist, kann eine Veränderung/Belegung erfolgen
        if (btn.text.isEmpty()){
            if (currentPlayer){
                btn.text = playerX
                // wenn x gesetzt, hinweis auf nächsten Spieler
                binding.tvNext.text = "Spieler $playerO ist am Zug"
                currentPlayer = false
            }
            else {
                btn.text = playerO
                // wenn o gesetzt, hinweis auf nächsten Spieler
                binding.tvNext.text = "Spieler $playerX ist am Zug"
                currentPlayer = true
            }
        }
        // überprüfung ob Siegeszug
        checkWinner()


    }



}
