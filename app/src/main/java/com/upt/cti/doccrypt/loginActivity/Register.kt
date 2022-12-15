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

const val BASE_URL = "http://192.168.0.107:8075"


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
        registrationOnTheServer()
//        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
//            if (it.isSuccessful) {
//                Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
//                finish()
//            } else {
//                Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    private fun registrationOnTheServer(){
        val params = HashMap<String, Objects>()
        Log.e(TAG, "response")
        params["username"] = fullName.text as Objects
        params["email"] = email.text as Objects
        params["firstName"] = email.text.toString().split(" ")[0] as Objects
        params["lastName"] = email.text.toString().split(" ")[1] as Objects
        params["password"] = password.text as Objects
        params["isNotary"] = notaryRadioButton.isChecked as Objects

        Log.e(TAG, "response $params")
        val queue = Volley.newRequestQueue(this)
        var jsonRequest = JSONObject(params.toString())
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            "$BASE_URL/api/v1/reg/registration",
            jsonRequest,
            { response ->
                Log.d(ContentValues.TAG, "Response: %s".format(response.toString()))
                Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
            },
            { error ->
                Toast.makeText(this, "Singed Up failed", Toast.LENGTH_SHORT).show()
                Log.e(ContentValues.TAG, "Error: %s".format(error.toString()))
            }
        )
        queue.add(jsonObjectRequest)
    }
}