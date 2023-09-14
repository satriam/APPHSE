package com.example.hseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.example.hseapp.dataclass.SignInBody
import com.example.hseapp.retrofit.RetrofitInstance
import com.example.hseapp.retrofit.SessionManager
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)
        setContentView(R.layout.activity_login)
        onStart()
        checklogin()
        auth()
    }
    private fun checklogin(){
        if (sessionManager.isLogin()!! ){

            val intent = Intent(this@LoginActivity, MainActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
        }

    private fun auth(){
        sessionManager = SessionManager(this)
        val apiClient = RetrofitInstance.Create(this)

        val etemail =findViewById<TextInputEditText>(R.id.emailEt)
        val etpassword =findViewById<TextInputEditText>(R.id.passET)
        val btnlogin =findViewById<Button>(R.id.button)

        btnlogin.setOnClickListener{
            val progressBar =findViewById<ProgressBar>(R.id.progressBar)
            progressBar.visibility = View.VISIBLE

            // Lakukan permintaan login ke server di sini, misalnya menggunakan Retrofit atau Volley.
            apiClient.signin(
                etemail.text.toString().trim(),
                etpassword.text.toString().trim()
            ).enqueue(object : Callback<SignInBody> {
                override fun onResponse(call: Call<SignInBody>, response: Response<SignInBody>) {
                    val loginresponse =response.body()

                    Log.d("login",loginresponse.toString())

                    if (loginresponse != null) {
                        progressBar.visibility = View.GONE


                        showAlertDialog("Login Berhasil", "Selamat datang") {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                            sessionManager.saveAuthToken(loginresponse.jwt)
                            sessionManager.saveId(loginresponse.user.id)
                            sessionManager.setLoggin(true)
                        }
                    }else{
                        progressBar.visibility = View.GONE
                        showAlertDialog("GAGAl", "Periksa Kembali Data"){

                        }
                    }
                }

                override fun onFailure(call: Call<SignInBody>, t: Throwable) {
                    Log.e("error",t.toString())
                }

            })




        }

    }

    private fun showAlertDialog(title: String, message: String, callback: () -> Unit) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                callback() // Panggil callback saat tombol "OK" pada dialog diklik
            }
            .create()

        alertDialog.show()
    }


}
