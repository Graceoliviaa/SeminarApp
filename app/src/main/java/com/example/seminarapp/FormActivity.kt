package com.example.seminarapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        // Setup Spinner
        val seminars = listOf("Pilih Seminar", "AI Seminar", "Mobile Development",
            "Cyber Security", "Data Science", "UI/UX Design")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, seminars)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.spinnerSeminar).adapter = adapter

        // Validasi email realtime
        val etEmail = findViewById<EditText>(R.id.etEmailForm)
        val tvEmailError = findViewById<TextView>(R.id.tvEmailFormError)
        etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString().trim()
                tvEmailError.visibility = if (email.isNotEmpty() && !email.endsWith("@gmail.com"))
                    TextView.VISIBLE else TextView.GONE
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Validasi HP realtime
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val tvPhoneError = findViewById<TextView>(R.id.tvPhoneError)
        etPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val phone = s.toString().trim()
                val phoneRegex = Regex("^08[0-9]{8,10}$")
                tvPhoneError.visibility = if (phone.isNotEmpty() && !phone.matches(phoneRegex))
                    TextView.VISIBLE else TextView.GONE
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Submit
        findViewById<Button>(R.id.btnSubmit).setOnClickListener {
            if (validateForm()) showConfirmationDialog()
        }

        // Bottom Navigation
        setupBottomNav()
    }

    private fun validateForm(): Boolean {
        val nama = findViewById<EditText>(R.id.etNama).text.toString().trim()
        val email = findViewById<EditText>(R.id.etEmailForm).text.toString().trim()
        val phone = findViewById<EditText>(R.id.etPhone).text.toString().trim()
        val rgGender = findViewById<RadioGroup>(R.id.rgGender)
        val spinner = findViewById<Spinner>(R.id.spinnerSeminar)
        val cbSetuju = findViewById<CheckBox>(R.id.cbSetuju)

        if (nama.isEmpty()) {
            Toast.makeText(this, "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (email.isEmpty() || !email.endsWith("@gmail.com")) {
            Toast.makeText(this, "Email harus menggunakan @gmail.com!", Toast.LENGTH_SHORT).show()
            return false
        }
        val phoneRegex = Regex("^08[0-9]{8,10}$")
        if (!phone.matches(phoneRegex)) {
            Toast.makeText(this, "Nomor HP tidak valid! Harus diawali 08, 10-12 digit", Toast.LENGTH_LONG).show()
            return false
        }
        if (rgGender.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Pilih jenis kelamin!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (spinner.selectedItemPosition == 0) {
            Toast.makeText(this, "Pilih seminar terlebih dahulu!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!cbSetuju.isChecked) {
            Toast.makeText(this, "Anda harus menyetujui syarat dan ketentuan!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun showConfirmationDialog() {
        val nama = findViewById<EditText>(R.id.etNama).text.toString()
        val email = findViewById<EditText>(R.id.etEmailForm).text.toString()
        val phone = findViewById<EditText>(R.id.etPhone).text.toString()
        val rgGender = findViewById<RadioGroup>(R.id.rgGender)
        val gender = findViewById<RadioButton>(rgGender.checkedRadioButtonId).text.toString()
        val seminar = findViewById<Spinner>(R.id.spinnerSeminar).selectedItem.toString()

        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Data")
            .setMessage("Apakah data yang kamu masukkan sudah benar?\n\nNama: $nama\nEmail: $email\nHP: $phone\nSeminar: $seminar")
            .setPositiveButton("Ya, Kirim") { _, _ ->
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("NAMA", nama)
                intent.putExtra("EMAIL", email)
                intent.putExtra("PHONE", phone)
                intent.putExtra("GENDER", gender)
                intent.putExtra("SEMINAR", seminar)
                startActivity(intent)
            }
            .setNegativeButton("Cek Lagi", null)
            .show()
    }

    private fun setupBottomNav() {
        findViewById<LinearLayout>(R.id.navHome).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.navForm).setOnClickListener {
            // Sudah di Form
        }
        findViewById<LinearLayout>(R.id.navResult).setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("FROM_NAV", true)
            startActivity(intent)
        }
    }
}