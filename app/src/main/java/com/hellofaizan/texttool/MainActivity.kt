package com.hellofaizan.texttool

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.icu.number.IntegerWidth
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import com.google.android.play.core.assetpacks.v
import com.hellofaizan.texttool.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {}
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = this.resources.getColor(R.color.navC)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this,R.color.navC)))

        binding.clearTxt.setOnClickListener{
            binding.editText.setText("")
            binding.previewText.text = "Enter text to preview."
            Toast.makeText(this, "Text Cleared!", Toast.LENGTH_SHORT).show()
        }
        binding.copyTxt.setOnClickListener{
            val text = binding.editText.text.toString()
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", text)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_LONG).show()
        }

        binding.openSource.setOnClickListener{
            intent = Intent(Intent(Intent.ACTION_VIEW))
            intent.data = Uri.parse("https://github.com/hellofaizan/TextTool")
            startActivity(intent)
        }

        binding.editText.addTextChangedListener { it ->
            val characters = it.toString().length
            val numberOfInputWords = it.toString().trim()
            val words = numberOfInputWords.split("\\s+".toRegex()).size

            binding.chWords.text = "${characters} Characters and ${words} Words"
            binding.previewText.text = it.toString()

            binding.upperCase.setOnClickListener{
                val text: String = binding.editText.text.toString().uppercase()
                val s = StringBuilder().append(text).toString()

                binding.editText.setText(""+s)
                binding.previewText.text = ""+s
            }
            binding.titleCase.setOnClickListener{
                val text2 = binding.editText.text.toString().trim().capitalizeWords()
                val s = StringBuilder().append(text2).toString()

                binding.editText.setText(""+s)
                binding.previewText.text = ""+s
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.topnav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.itemId

        if (id == R.id.gSpo) {
            intent = Intent(Intent(Intent.ACTION_VIEW))
            intent.data = Uri.parse("https://github.com/sponsors/hellofaizan")
            startActivity(intent)
            return true
        }
        if (id == R.id.gOS) {
            intent = Intent(Intent(Intent.ACTION_VIEW))
            intent.data = Uri.parse("https://discord.gg/invite/EHthxHRUmr")
            startActivity(intent)
            return true
        }
        if (id == R.id.ig) {
            intent = Intent(Intent(Intent.ACTION_VIEW))
            intent.data = Uri.parse("https://instagram.com/hellofaizan")
            startActivity(intent)
            return true
        }
        if (id == R.id.tw) {
            intent = Intent(Intent(Intent.ACTION_VIEW))
            intent.data = Uri.parse("https://twitter.com/HelloFaizandev")
            startActivity(intent)
            return true
        }
        if (id == R.id.share) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type="text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "TextTool text analysis Open Source app on Github.\n\nDownload Now\nhttps://play.google.com/store/apps/details?id=$packageName")
            startActivity(Intent.createChooser(shareIntent,"Share via"))
            return true

        }
        if (id == R.id.ad) {
            intent = Intent(Intent(Intent.ACTION_VIEW))
            intent.data = Uri.parse("https://www.youtube.com/@HelloFaizan")
            startActivity(intent)
            return true
        }
        if (id == R.id.git) {
            intent = Intent(Intent(Intent.ACTION_VIEW))
            intent.data = Uri.parse("https://github.com/hellofaizan")
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press again to Exit app", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")
}