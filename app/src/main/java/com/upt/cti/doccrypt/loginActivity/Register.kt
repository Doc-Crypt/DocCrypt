package com.upt.cti.doccrypt.loginActivity

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.authentication_manager.AuthenticationManager
import com.upt.cti.doccrypt.authentication_manager.BASE_URL
import org.json.JSONObject
import java.util.Objects

class Register : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var fullName: EditText
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
        fullName = findViewById(R.id.full_name)
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
        val fullName = fullName.text.toString()

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
        if(notaryRadioButton.isChecked){
            val intent = Intent(this, NotaryVerification::class.java)
            intent.putExtra("email", email)
            intent.putExtra("password", pass)
            intent.putExtra("fullName", fullName)
            startActivity(intent)
        }else{
            registrationUserOnTheServer()
        }

    }

    private fun registrationUserOnTheServer(){
        val params = HashMap<String, String>()
        Log.e(TAG, "response")
        params["username"] = fullName.text.toString().split(" ")[0]
        params["email"] = email.text.toString()
        params["firstName"] = fullName.text.toString().split(" ")[0]
        params["lastName"] = fullName.text.toString().split(" ")[1]
        params["password"] = password.text.toString()
        params["isNotary"] = notaryRadioButton.isChecked.toString()

        Log.e(TAG, "response $params")
        val queue = Volley.newRequestQueue(this)
        var jsonRequest = JSONObject(params.toString())
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            "$BASE_URL/api/v1/reg/registration",
            jsonRequest,
            { response ->
                Log.d(ContentValues.TAG, "Response: %s".format(response.toString()))
                Toast.makeText(this, "Successfully registered", Toast.LENGTH_SHORT).show()
            },
            { error ->
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                Log.e(ContentValues.TAG, "Error: %s".format(error.toString()))
            }
        )
        queue.add(jsonObjectRequest)
    }
}