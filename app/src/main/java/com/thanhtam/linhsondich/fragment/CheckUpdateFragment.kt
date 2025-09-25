package com.thanhtam.linhsondich.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.thanhtam.linhsondich.databinding.FragmentCheckUpdateBinding
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import java.util.concurrent.TimeUnit

class CheckUpdateFragment : Fragment() {
    private var _binding: FragmentCheckUpdateBinding? = null

    private val binding get() = _binding!!
    private var mFirebaseRemoteConfig: FirebaseRemoteConfig? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        _binding = FragmentCheckUpdateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.txtCheckupdate.setOnClickListener {
            initRemoteConfig()
        }
        initRemoteConfig()
        return root
    }
    private fun initRemoteConfig() {

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val firebaseDefaultMap = HashMap<String, Any>()
        firebaseDefaultMap[VERSION_CODE_KEY] = currentVersionCode
        Log.d("LoadQue", "initRemoteConfig: $currentVersionCode")
        mFirebaseRemoteConfig!!.setDefaultsAsync(firebaseDefaultMap)
        mFirebaseRemoteConfig!!.setConfigSettingsAsync(
            FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(TimeUnit.HOURS.toSeconds(0))
                .build())
        mFirebaseRemoteConfig!!.fetch().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                //activate most recently fetch config value
                mFirebaseRemoteConfig!!.activate().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //calling function to check if new version is available or not
                        val latestAppVersion = mFirebaseRemoteConfig!!.getDouble(VERSION_CODE_KEY).toInt()
                        activity?.runOnUiThread { checkForUpdate(latestAppVersion) }
                    }
                }
            }
            else{
                Log.d("versionCode", "initRemoteConfig: Fail")
            }
        }
    }
    private fun checkForUpdate(latestAppVersion: Int) {
        if (latestAppVersion > currentVersionCode) {
            goToPlayStore()
        }
        else Toast.makeText(context, "Đang Là Phiên Bản Mới Nhất", Toast.LENGTH_SHORT).show()
    }

    private val currentVersionCode: Int
        get() {
            var versionCode = 1
            try {
                val pInfo =
                    context?.packageName?.let { context?.packageManager?.getPackageInfo(it, 0) }
                if (pInfo != null) {
                    versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        pInfo.longVersionCode.toInt()
                    } else {
                        pInfo.versionCode
                    }
                }
            } catch (e: PackageManager.NameNotFoundException) {
                //log exception
            }
            return versionCode
        }

    private fun goToPlayStore() {
        try {
            val appStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.thanhtam.linhsondich"))
            appStoreIntent.setPackage("com.android.vending")
            startActivity(appStoreIntent)
        } catch (exception: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.thanhtam.linhsondich")))
        }
    }


    companion object {
        private const val VERSION_CODE_KEY = "version_code"
    }
}