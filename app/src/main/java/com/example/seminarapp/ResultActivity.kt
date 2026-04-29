package com.example.seminarapp

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val fromNav = intent.getBooleanExtra("FROM_NAV", false)

        if (fromNav) {
            findViewById<TextView>(R.id.tvResultTitle).text = "Belum Ada Pendaftaran"
            findViewById<TextView>(R.id.tvResultStatus).text = "○"
        } else {
            findViewById<TextView>(R.id.tvNamaResult).text = intent.getStringExtra("NAMA") ?: "-"
            findViewById<TextView>(R.id.tvEmailResult).text = intent.getStringExtra("EMAIL") ?: "-"
            findViewById<TextView>(R.id.tvPhoneResult).text = intent.getStringExtra("PHONE") ?: "-"
            findViewById<TextView>(R.id.tvSeminarResult).text = intent.getStringExtra("SEMINAR") ?: "-"
        }

        setupBottomNav()
    }

    private fun setupBottomNav() {
        findViewById<LinearLayout>(R.id.navHome).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.navForm).setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.navResult).setOnClickListener {
            // Sudah di Result
        }
    }
}