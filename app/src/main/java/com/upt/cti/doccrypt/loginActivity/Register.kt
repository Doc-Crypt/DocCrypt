package com.upt.cti.doccrypt.loginActivity

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.upt.cti.doccrypt.R

class Register : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var visibilityButton : ImageButton
    private lateinit var userRadioButton: RadioButton
    private lateinit var notaryRadioButton: RadioButton

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialising auth object
        auth = FirebaseAuth.getInstance()

        visibilityButton = findViewById(R.id.register_password_visibility)
        email = findViewById(R.id.register_email)
        password = findViewById(R.id.register_password)

        visibilityButton.setOnClickListener {
            if (password.transformationMethod.equals(HideReturnsTransformationMethod.getInstance())) {
                password.transformationMethod = PasswordTransformationMethod.getInstance()
                visibilityButton.setImageResource(R.drawable.eye_show)
            } else {
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                visibilityButton.setImageResource(R.drawable.eye_hide)
            }
        }

        findViewById<Button>(R.id.create_account_button).setOnClickListener {
            signUpUser()
        }

        findViewById<TextView>(R.id.sign_in_button).setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    private fun signUpUser() {
        val email = email.text.toString()
        val pass = password.text.toString()

        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        userRadioButton = findViewById(R.id.type_user)
        notaryRadioButton = findViewById(R.id.type_notary)

        userRadioButton.setOnCheckedChangeListener { _, isChecked ->
            run {
                if (isChecked)
                    notaryRadioButton.isChecked = false
            }
        }

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}