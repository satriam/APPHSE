package com.example.hseapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView

class HaulingActivity : AppCompatActivity() {
    private lateinit var editTextArray: Array<EditText>
    private lateinit var checkBoxArray: Array<CheckBox>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hauling)

        editTextArray = Array(26) { findViewById(getEditTextResourceId(it)) }
        checkBoxArray = Array(26) { findViewById(getCheckBoxResourceId(it)) }

        // Menambahkan TextWatcher ke semua EditText
        for (i in 0 until 26) {
            editTextArray[i].addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    validateInput(i)
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // Tidak perlu melakukan apa-apa di sini
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    // Tidak perlu melakukan apa-apa di sini
                }
            })
        }

        // Menambahkan OnCheckedChangeListener ke semua CheckBox
        for (i in 0 until 26) {
            checkBoxArray[i].setOnCheckedChangeListener { _, _ ->
                validateInput(i)
            }
        }
    }

    private fun validateInput(index: Int) {
        val isChecked = checkBoxArray[index].isChecked
        val inputText = editTextArray[index].text.toString().trim()

        if (!isChecked && inputText.isEmpty()) {
            // CheckBox tidak dicentang dan EditText kosong
            editTextArray[index].error = "Isi EditText ini"
            // Tindakan lain sesuai kebutuhan
        } else {
            // CheckBox dicentang atau EditText tidak kosong
            editTextArray[index].error = null // Hapus pesan kesalahan jika ada
            // Tindakan lain sesuai kebutuhan
        }
    }

    private fun getEditTextResourceId(index: Int): Int {
        // Mengembalikan ID EditText sesuai dengan indeks
        val editTextIds = arrayOf(
            R.id.kd1, R.id.kd2, R.id.kd3, R.id.kd4, R.id.kd5,
            R.id.kd6, R.id.kd7, R.id.kd8, R.id.kd9, R.id.kd10,
            R.id.kd11, R.id.kd12, R.id.kd13, R.id.kd14, R.id.kd15,
            R.id.kd16, R.id.kd17, R.id.kd18, R.id.kd19, R.id.kd20,
            R.id.kd21, R.id.kd22, R.id.kd23, R.id.kd24, R.id.kd25, R.id.kd26,
        )
        return editTextIds[index]
    }

    private fun getCheckBoxResourceId(index: Int): Int {
        // Mengembalikan ID CheckBox sesuai dengan indeks
        val checkBoxIds = arrayOf(
            R.id.ck1, R.id.ck2, R.id.ck3, R.id.ck4, R.id.ck5,
            R.id.ck6, R.id.ck7, R.id.ck8, R.id.ck9, R.id.ck10,
            R.id.ck11, R.id.ck12, R.id.ck13, R.id.ck14, R.id.ck15,
            R.id.ck16, R.id.ck17, R.id.ck18, R.id.ck19, R.id.ck20,
            R.id.ck21, R.id.ck22, R.id.ck23, R.id.ck24, R.id.ck25, R.id.ck26
        )
        return checkBoxIds[index]
    }
}
