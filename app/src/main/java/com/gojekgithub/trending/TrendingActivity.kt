package com.gojekgithub.trending

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.gojekgithub.trending.databinding.TrendingLayoutBinding
import com.gojekgithub.trending.ui.main.MainFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.trending_layout.view.*
import javax.inject.Inject


class TrendingActivity : AppCompatActivity(), HasAndroidInjector {

    private lateinit var binding: TrendingLayoutBinding

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = TrendingLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        view.toolbar.overflowIcon = ContextCompat.getDrawable(this, R.drawable.more_black)
        setSupportActionBar(view.toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_stars -> true
            R.id.action_name -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}