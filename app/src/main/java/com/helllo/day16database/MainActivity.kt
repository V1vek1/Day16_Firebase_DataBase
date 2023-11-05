package com.helllo.day16database

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    //Yha maine "lateinit" Datatype use kiya, Lateint ka matlab h ki isko iss "code" ko ya Variable ko baad me
    //"initialize karo jabhi or jaha bhi program me call kiya jaye Or iska Type h "DatabaseReference"
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Yha per maine hamesa ki tarah sabhi Variables bnaye h Or sabko Fetch kiya h xml file se
         val signButton= findViewById<Button>(R.id.btnSignUp)
         val etName= findViewById<TextInputEditText>(R.id.etName)
         val  etMail= findViewById<TextInputEditText>(R.id.etMail)
         val userId= findViewById<TextInputEditText>(R.id.etUserName)
         val userPassword= findViewById<TextInputEditText>(R.id.etPassword)



        //Yha maine "signbutton" ko bnaya ki tumper jab koi click kare to tum yeh sab "data" aapne me save karlo
        signButton.setOnClickListener {

            val name= etName.text.toString()
            val mail= etMail.text.toString()
            val uniqueId= userId.text.toString()
            val password= userPassword.text.toString()



            //maine ek "Object" bnaya, "Users" calss ki help se, jisme maine kuch parameters Pass kiye h
            val user = User(name, mail, password, uniqueId)



            //maine yaha Lateinit "Database" Variable ko bola ki jobhi input aa rha h users se usko lo pr "firebase"
            //per "Users" section me bhej do
            database = FirebaseDatabase.getInstance().getReference("Users") //Matlab This is a Address of Firebase "user' Path



            //aab yaha per mai "child" class bnakar :Firebase ke database me bhej rhe h,Or last me ek
            //"FailureListener" add kiya jisme ek "Toast" pass kiya, Jisse yeh pta chale ki Users ne
            //Registration kiya ya nahi
            database.child(uniqueId).setValue(user).addOnSuccessListener {

                etName.text?.clear() //Iss Code ka matlab h ki jab, Users Successfully Register karle to, Login
                                     //page se "Name" wala "EditText" Clear ho jayega yani khali ho jayega, Isi Tarike se hum
                                     //sabhi "EditText" ko CLear kar sakte h

                //Yeh toast Tab show hoga jab User Successfully Register karlega humare App per
                Toast.makeText(this,"User Registered", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
            }
        }

        val signIntent = findViewById<TextView>(R.id.tvSignIN)

        signIntent.setOnClickListener{
            val openSignInActivity = Intent(this, SignInActivity::class.java)
            startActivity(openSignInActivity)
        }

    }
}