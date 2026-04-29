package com.example.seminarapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val namaUser = intent.getStringExtra("NAMA_USER") ?: "pengguna"
        findViewById<TextView>(R.id.tvNamaUser).text = namaUser

        findViewById<Button>(R.id.btnDaftar).setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
        }

        setupBottomNav()
    }

    private fun setupBottomNav() {
        findViewById<LinearLayout>(R.id.navHome).setOnClickListener {
            // Sudah di Home
        }
        findViewById<LinearLayout>(R.id.navForm).setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.navResult).setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("FROM_NAV", true)
            startActivity(intent)
        }
    }
}