package com.upt.cti.doccrypt.loginActivity

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.interfaceActivity.UserInterface

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var visibilityButton : ImageButton
    private lateinit var email: EditText
    private lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        visibilityButton = findViewById(R.id.login_password_visibility)
        email = findViewById(R.id.login_email)
        password = findViewById(R.id.login_password)

        visibilityButton.setOnClickListener {
            if (password.transformationMethod.equals(HideReturnsTransformationMethod.getInstance())) {
                password.transformationMethod = PasswordTransformationMethod.getInstance()
                visibilityButton.setImageResource(R.drawable.eye_show)
            } else {
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                visibilityButton.setImageResource(R.drawable.eye_hide)
            }
        }

        findViewById<Button>(R.id.login_button).setOnClickListener {
            login()
        }

        findViewById<Button>(R.id.register_button).setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }

    private fun login(){
        val email = email.text.toString()
        val password = password.text.toString()
        Log.e(TAG, email)
        Log.e(TAG, password)
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, UserInterface::class.java)
                startActivity(intent)

            } else
                Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
        }
    }
}