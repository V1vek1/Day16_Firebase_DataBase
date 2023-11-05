package com.helllo.day16database

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference //Yha maine Firebase Library se Reference liya h

    //Maine Global Variable/Object "KEY" bnaya jo ki iss "SignInActivity" Class me kahi bhi use ho sakta h
    companion object
    {                         //Package Name      //Activity Name
        const val KEY1 = "com.helllo.day16database.SignInActivity.mail"
        const val KEY2 = "com.helllo.day16database.SignInActivity.name"
        const val KEY3 = "com.helllo.day16database.SignInActivity.id"
    }

    //Jaisa ki hum jante h ki "OnCreate" ek Method h
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val signInButton = findViewById<Button>(R.id.btnSignIn)
        val userName= findViewById<TextInputEditText>(R.id.userNameEditText)

        signInButton.setOnClickListener {

            val uniqueId = userName.text.toString()  //Yha maine "uniqueId" name ka Variable bnaya Or iss
                                                                 //Variable me, maine "userName" variable me
                                                                 //jo bhi text h wo text "uniqueId" me store
                                                                 //kra rhe h


            //Agar "uniqueId" empty Yani khali nahi h to, readData Method ko call karke use karo
            if (uniqueId.isNotEmpty())
            {
                readData(uniqueId)
            }

            //Warna User ko Toast Show karo ki, Aapna User name Enter kare
            else
            {
                Toast.makeText(this,"Please Enter the User ID", Toast.LENGTH_SHORT).show()
            }
        }
    }




    //Yaha maine ek Method bnaya h joki Private h or uska Name h "readData"
    private fun readData(uniqueId: String)
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users") //maine yha Firebase se "Users" Node
                                                                                      //ka Address liya h

        //Jab "uniqueId" se Data mil jaye to uske Child Class ko get karlo yani lelo
        databaseReference.child(uniqueId).get().addOnSuccessListener {

            //Agar user ka "uniqueId"  match kar jata h hmare database se to Yeh karo
            if (it.exists())
            {
               //yani User ko ek new Screen per lejao, Or Uss User ke Yeh sabhi Details ko new Screen per show karo
                val email = it.child("email").value
                val name = it.child("name").value
                val userId = it.child("uniqueId").value

                //yha maine "intentWelcome" name ka Variable bnaya jisme maine "welcomeActivity" Screen ko Start kiya
                val intentWelcome = Intent(this, WelcomeActivity::class.java)

                //Yha maine "intentWelcome" me KEY ki Hep se "email, name, userID" string ke form me Store kra rhe h
                intentWelcome.putExtra(KEY1, email.toString())
                intentWelcome.putExtra(KEY2, name.toString())
                intentWelcome.putExtra(KEY3, userId.toString())

                startActivity(intentWelcome)

            }

            //Agar User ka "uniqueID" hmare databse se match nahi karta h to Yeh Toast Show kardo
            else
            {
                Toast.makeText(this,"User does noy Exist",Toast.LENGTH_SHORT).show()
            }


            //Agar Hamare Firebase me hi kuch Problem h ya User ka Internet acche se nahi work kar rha h to
            //yeh Toast Show kardo

        }.addOnFailureListener{
            Toast.makeText(this,"Failed, Error in Database",Toast.LENGTH_SHORT).show()
        }

    }
}