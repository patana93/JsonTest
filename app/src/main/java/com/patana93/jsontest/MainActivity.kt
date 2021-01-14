package com.patana93.jsontest

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File


class MainActivity : AppCompatActivity() {
    private var stringBuilder:StringBuilder?=null
    private lateinit var textView: TextView
    private lateinit var fileName: String
    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var postAdapter: MyAdapter? = null
    private var postArray = arrayListOf<Post>()
    private var filePath = ""

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
            //imgProfile.setImageURI(fileUri)

            //You can get File object from intent
            val file:File = ImagePicker.getFile(data)!!

            //You can also get File Path from intent
            filePath = ImagePicker.getFilePath(data)!!


            writeJSONtoFile(fileName, filePath)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        //imgProfile = findViewById(R.id.cover)
        textView = findViewById(R.id.text_view)
        fileName = applicationContext.filesDir.toString() + "/PostJson.json"

        gridLayoutManager = GridLayoutManager(
            this.applicationContext,
            1,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(true)

        postArray = readJSONfromFile(fileName)

        postAdapter = MyAdapter(this.applicationContext, postArray)
        recyclerView?.adapter = postAdapter


        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener{
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .start()
        }

        val button2 = findViewById<Button>(R.id.button2)

        button2.setOnClickListener{
            readJSONfromFile(fileName)
            //postAdapter!!.notifyDataSetChanged()
        }

        val button3 = findViewById<Button>(R.id.button3)

        button3.setOnClickListener{
            writeJSONtoFile(fileName, filePath)
            //postAdapter!!.notifyDataSetChanged()
        }

        // Get the file Location and name where Json File are get stored

        //call write Json method

        //Read the written Json File
        //
    }
    private fun writeJSONtoFile(s: String, pathImage: String) {
        //Create list to store the all Tags
        var tags = arrayListOf(Detail("ciao"), Detail("ciao2"))
        //Create a Object of Post
        val post = Post("Json Tutorial", "www.nplix.com", "Pawan Kumar", pathImage, tags)
        //Create a Object of Gson
        val gson = Gson()
        //Convert the Json object to JsonString
        val jsonString:String = gson.toJson(post)

        //Initialize the File Writer and write into file
        val file=File(s)
        file.writeText(jsonString)
        readJSONfromFile(s)
    }
    private fun readJSONfromFile(f: String): ArrayList<Post> {

        //Creating a new Gson object to read data
        val gson = Gson()
        //Read the PostJSON.json file
        val bufferedReader: BufferedReader = File(f).bufferedReader()
        // Read the text from buffferReader and store in String variable
        val inputString = bufferedReader.use { it.readText() }

        //Convert the Json File to Gson Object
        val post = gson.fromJson(inputString, Post::class.java)
        //Initialize the String Builder
        stringBuilder = StringBuilder("Post Details\n---------------------")
        Log.d("Kotlin", post.postHeading!!)
        stringBuilder?.append("\nPost Heading: " + post.postHeading)
        stringBuilder?.append("\nPost URL: " + post.postUrl)
        stringBuilder?.append("\nPost Author: " + post.postAuthor)
        stringBuilder?.append("\nPost ImagePath: " + post.postImagePath)
        stringBuilder?.append("\nTags:")
        //get the all Tags

        //post.postTag?.forEach { tag -> stringBuilder?.append("$tag,") }
        //Display the all Json object in text View
        textView.text = stringBuilder.toString()

        postArray.add(post)

        //val uri = Uri.fromFile(File(post.postImagePath!!))
        postAdapter?.notifyDataSetChanged()

        return postArray
    }
}