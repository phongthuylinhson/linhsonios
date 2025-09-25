package com.thanhtam.linhsondich.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.thanhtam.linhsondich.R
import com.thanhtam.linhsondich.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory.create

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var reviewManager: ReviewManager
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        verifyStoragePermission(this)
        update()
    }

    private fun update(){
        val appUpdateManager = AppUpdateManagerFactory.create(this)

// Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                // This example applies an immediate update. To apply a flexible update
                // instead, pass in AppUpdateType.FLEXIBLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                val activityResultLauncher: ActivityResultLauncher<IntentSenderRequest>? = null
                appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // an activity result launcher registered via registerForActivityResult
                    activityResultLauncher!!,
                    // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                    // flexible updates.
                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build())
            }
        }
    }

    private fun initView() {
        //binding.contentview.visibility = View.VISIBLE
        //binding.txtCheckconnect.visibility = View.GONE
        toolbar = findViewById(R.id.toolbar)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController
        //drawerLayout = findViewById(R.id.drawerLayout)

        setSupportActionBar(toolbar)
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.nav_menu).setupWithNavController(navController)
        setupDrawerAnimation()
        //initRemoteConfig() /// kiểm tra phiên bản
        reviewManager = create(this)

        // Kiểm tra xem người dùng đã đánh giá ứng dụng hay chưa
        if (!hasUserReviewed()) {
            // Nếu chưa đánh giá, hiển thị yêu cầu đánh giá
            requestReview()
        }
    }

    private fun requestReview() {
        val requestReviewFlow = reviewManager.requestReviewFlow()
        requestReviewFlow.addOnCompleteListener { request ->
            if (request.isSuccessful) {
                // Nhận được ReviewInfo, hiển thị xác nhận đánh giá
                val reviewInfo = request.result
                val launchReviewFlow = reviewManager.launchReviewFlow(this, reviewInfo)
                launchReviewFlow.addOnCompleteListener {
                    // Xử lý sau khi người dùng đã xem xét ứng dụng
                    if (it.isSuccessful) {
                        // Người dùng đã đánh giá, lưu trạng thái này
                        markUserReviewed()
                    }
                }
            } else {
                // Xử lý lỗi khi yêu cầu xem xét ứng dụng
            }
        }
    }

    private fun hasUserReviewed(): Boolean {
        // Thực hiện kiểm tra trạng thái đánh giá, ví dụ: dùng SharedPreferences
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        return sharedPreferences.getBoolean("has_reviewed", false)
    }

    private fun markUserReviewed() {
        // Lưu trạng thái đã đánh giá, ví dụ: dùng SharedPreferences
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("has_reviewed", true).apply()
    }

    private fun setupDrawerAnimation() {
        binding.drawerLayout.elevation = 0f
        binding.drawerLayout.setScrimColor(Color.TRANSPARENT)
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val diffScaledOffset = slideOffset * (1 - 0.2f)
                val offsetScale = 1 - diffScaledOffset
                binding.contentview.scaleX = offsetScale
                binding.contentview.scaleY = offsetScale

                val xOffset = drawerView.width * slideOffset
                val xOffsetdiff = binding.contentview.width * diffScaledOffset / 2
                val xTranslation = xOffset - xOffsetdiff
                binding.contentview.translationX = xTranslation
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {
            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()

    }

//    override fun onBackPressed() {
//        if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
//            binding.drawerLayout.closeDrawer(GravityCompat.START)
//        } else
//            super.onBackPressed()
//    }

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSION_STORAGE = arrayOf<String>(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    fun verifyStoragePermission(activity: Activity) {
        val permission =
            ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity,
                PERMISSION_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )

        }
    }

}