package com.thanhtam.linhsondich.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import com.thanhtam.linhsondich.R
import com.thanhtam.linhsondich.databinding.ActivityMainBinding
import com.thanhtam.linhsondich.databinding.ActivitySplashBinding
import java.util.*

class SplashActivity : AppCompatActivity() {
    var h1 = 0
    var h2 = 0
    var h3 = 0
    var h4 = 0
    var h5 = 0
    var h6 = 0
    var phut: Int = 0
    lateinit var handler: Handler
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val cal = Calendar.getInstance()
        phut = cal.get(Calendar.MINUTE)

        val rd = Random()
        h1 = rd.nextInt(7)
//        h2 = rd.nextInt(2)
//        h3 = rd.nextInt(2)
//        h4 = rd.nextInt(2)
//        h5 = rd.nextInt(2)
//        h6 = rd.nextInt(2)
        //setimage(h1, binding.splH6)
        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 2000)
    }


    override fun onResume() {
        super.onResume()

        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 2000)
    }
}
