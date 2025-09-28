package com.thanhtam.linhsondich.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory.create
import com.thanhtam.linhsondich.R
import com.thanhtam.linhsondich.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var reviewManager: ReviewManager
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar

    // Trình khởi chạy để xử lý kết quả của luồng cập nhật
    private val appUpdateResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode != RESULT_OK) {
            // Xử lý khi người dùng hủy hoặc quá trình cập nhật không thành công
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Cấu hình cửa sổ trước khi set content view
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Khởi tạo các thành phần giao diện
        initView()
        // Yêu cầu quyền truy cập bộ nhớ
        verifyStoragePermission(this)
        // Kiểm tra cập nhật ứng dụng
        checkForAppUpdate()
    } // <-- DẤU NGOẶC ĐÓNG CỦA onCreate

    private fun initView() {
        toolbar = findViewById(R.id.toolbar)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Sửa lỗi cú pháp: Tách 2 dòng
        setSupportActionBar(toolbar)

        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        // --- BẮT ĐẦU LOGIC XỬ LÝ LOGO ---
        val startDestinationId = navController.graph.startDestinationId
        val logoImageView = findViewById<ImageView>(R.id.toolbar_logo)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == startDestinationId) {
                // Màn hình chính
                logoImageView.visibility = View.VISIBLE
                supportActionBar?.title = ""
            } else {
                // Các màn hình khác
                logoImageView.visibility = View.GONE
                supportActionBar?.title = destination.label
            }
        }
        // --- KẾT THÚC LOGIC ---

        // Các thiết lập còn lại (KHÔNG lặp lại)
        findViewById<NavigationView>(R.id.nav_menu).setupWithNavController(navController)
        setupDrawerAnimation()
        reviewManager = create(this)

        if (!hasUserReviewed()) {
            requestReview()
        }
    }

    private fun checkForAppUpdate() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    appUpdateResultLauncher, // Sử dụng launcher đã đăng ký
                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                )
            }
        }
    }

    private fun requestReview() {
        val requestReviewFlow = reviewManager.requestReviewFlow()
        requestReviewFlow.addOnCompleteListener { request ->
            if (request.isSuccessful) {
                val reviewInfo = request.result
                val launchReviewFlow = reviewManager.launchReviewFlow(this, reviewInfo)
                launchReviewFlow.addOnCompleteListener {
                    if (it.isSuccessful) {
                        markUserReviewed()
                    }
                }
            }
        }
    }

    private fun hasUserReviewed(): Boolean {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        return sharedPreferences.getBoolean("has_reviewed", false)
    }

    private fun markUserReviewed() {
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

            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerStateChanged(newState: Int) {}
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSION_STORAGE = arrayOf(
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
