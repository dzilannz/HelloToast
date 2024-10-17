package lat.pam.hellotoast

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    private val model: NameViewModel by viewModels()
    private var mCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge UI
        enableEdgeToEdge()

        // Make sure you're setting the right layout
        setContentView(R.layout.activity_main)

        // Initialize views
        val mShowCount = findViewById<TextView>(R.id.show_count)
        val buttonCountUp = findViewById<Button>(R.id.button_count)
        val buttonToast = findViewById<Button>(R.id.button_toast)
        val buttonSwitchPage = findViewById<Button>(R.id.button_switchpage)
        val buttonBrowser = findViewById<Button>(R.id.button_browser)

        // Observer to update the count text
        val nameObserver = Observer<Int> { newName ->
            mShowCount?.text = newName.toString() // null-safe access to mShowCount
        }
        model.currentName.observe(this, nameObserver)

        // Set click listeners for buttons
        buttonCountUp?.setOnClickListener {
            mCount += 1
            model.currentName.value = mCount
        }

        buttonToast?.setOnClickListener {
            val countText = mShowCount?.text.toString()
            Toast.makeText(this, "Angka yang dimunculkan: $countText", Toast.LENGTH_LONG).show()
        }

        buttonSwitchPage?.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        buttonBrowser?.setOnClickListener {
            val intentBrowse = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"))
            startActivity(intentBrowse)
        }

        // Optional: Handle edge-to-edge UI padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
