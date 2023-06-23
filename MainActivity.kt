package com.kotlinproject.tictactoekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.kotlinproject.tictactoekotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //region Baustellen

    //region MVP (Minimum Viable Product)
    //TODO belegtes Feld, darf nicht verändert werden können
    //TODO Resetten / New Game
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

    // Variablen definieren
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

        // Buttons in die Button-Liste einfügen (Initialisierung)
        btnList= listOf(binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7, binding.btn8,
            binding.btn9)

        //endregion

        //region Funktionen zuweisen

        // e = Element der Liste (ein Button, weil Liste nur Buttons enthalten kann)
        btnList.forEach { e -> e.setOnClickListener { newMarkField(e) } }


        // Felder mit Funktionalität versehen
        //binding.btn1.setOnClickListener { markField() } // Problem: gilt nur für btn1


        //endregion
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
    private fun newMarkField(btn: Button){ //Paramater in Form des angeklickten Buttons
        if (currentPlayer){
            btn.text = "X"
            currentPlayer = false
        }
        else {
            btn.text = "O"
            currentPlayer = true
        }
    }

}
