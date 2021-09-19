package com.android.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.testapp.ui.album.AlbumFragment

/**
 * Main launcher activity.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            //Display album fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AlbumFragment.newInstance())
                .commitNow()
        }
    }
}