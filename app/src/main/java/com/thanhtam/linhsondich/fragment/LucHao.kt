package com.thanhtam.linhsondich.fragment

import android.annotation.SuppressLint
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.Navigation
import com.thanhtam.linhsondich.R
import com.thanhtam.linhsondich.core.CTLucHaoUI
import com.thanhtam.linhsondich.core.Solar2Lunar
import com.thanhtam.linhsondich.databinding.FragmentLucHaoBinding
import com.thanhtam.linhsondich.model.*
import com.thanhtam.linhsondich.viewmodels.LiveDataNgayThangNam
import java.util.*


class LucHaoViewModel : ViewModel() {
    var h1Text = MutableLiveData("Dương")
    var h2Text = MutableLiveData("Dương")
    var h3Text = MutableLiveData("Dương")
    var h4Text = MutableLiveData("Dương")
    var h5Text = MutableLiveData("Dương")
    var h6Text = MutableLiveData("Dương")
    // ... các trường khác cho h3, h4, h5, h6, checkbox, và EditText
}

class LucHao : Fragment() {
    private val viewModel1: LucHaoViewModel by viewModels()
    private var _binding: FragmentLucHaoBinding? = null

    private val binding get() = _binding!!

    var ngay: Int = 0
    var gio: Int = 0
    var thang: Int = 0
    var nam: Int = 0
    var phut: Int = 0
    lateinit var viewModel:LiveDataNgayThangNam
    lateinit var receiver: BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this).get(LiveDataNgayThangNam::class.java)
        receiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                viewModel.TT.value = p1?.getParcelableExtra(DichResult.key)!!
            }
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, IntentFilter(DichResult.key))
        arguments?.let {
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentLucHaoBinding.inflate(inflater, container, false)
        val root: View = binding.root


// Khôi phục trạng thái từ ViewModel
        viewModel1.h1Text.observe(viewLifecycleOwner) { binding.h1.text = it }
        viewModel1.h2Text.observe(viewLifecycleOwner) { binding.h2.text = it }
        viewModel1.h3Text.observe(viewLifecycleOwner) { binding.h3.text = it }
        viewModel1.h4Text.observe(viewLifecycleOwner) { binding.h4.text = it }
        viewModel1.h5Text.observe(viewLifecycleOwner) { binding.h5.text = it }
        viewModel1.h6Text.observe(viewLifecycleOwner) { binding.h6.text = it }

        // Cập nhật ViewModel khi giao diện thay đổi
        binding.h1.setOnClickListener {
            val newText = if (binding.h1.text == "Dương") "Âm" else "Dương"
            viewModel1.h1Text.value = newText
        }
        binding.h2.setOnClickListener {
            val newText = if (binding.h2.text == "Dương") "Âm" else "Dương"
            viewModel1.h2Text.value = newText
        }
        binding.h3.setOnClickListener {
            val newText = if (binding.h3.text == "Dương") "Âm" else "Dương"
            viewModel1.h3Text.value = newText
        }
        binding.h4.setOnClickListener {
            val newText = if (binding.h4.text == "Dương") "Âm" else "Dương"
            viewModel1.h4Text.value = newText
        }
        binding.h5.setOnClickListener {
            val newText = if (binding.h5.text == "Dương") "Âm" else "Dương"
            viewModel1.h5Text.value = newText
        }
        binding.h6.setOnClickListener {
            val newText = if (binding.h6.text == "Dương") "Âm" else "Dương"
            viewModel1.h6Text.value = newText
        }

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //setHasOptionsMenu(true)

        val context=context
        viewModel= ViewModelProvider(this)[LiveDataNgayThangNam::class.java]
        var connectivity =
            context?.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
        //GetDaTaFormButtonLucHao(binding.h1, binding.h2, binding.h3, binding.h4, binding.h5, binding.h6)
        val cal = Calendar.getInstance()

        if (viewModel.TT.value==null) {
            binding.edty.setText(cal.get(Calendar.YEAR).toString())
            binding.edtt.text = (cal.get(Calendar.MONTH) + 1).toString()
            binding.edtd.text = (cal.get(Calendar.DATE)).toString()
            binding.edtg.text = (cal.get(Calendar.HOUR_OF_DAY)).toString()
            binding.edtp.text = (cal.get(Calendar.MINUTE)).toString()
        } else {
            viewModel.TT.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                binding.edtt.text=it.thang.toString()
                binding.edtd.text=it.ngay.toString()
                binding.edtg.text=it.gio.toString()
                binding.edtp.text=it.phut.toString()
                binding.edty.setText(it.nam.toString())
            })
        }
        binding.edtt.setOnClickListener(View.OnClickListener { setThang(binding.edtt,context).menu_thang() })
        binding.edtd.setOnClickListener(View.OnClickListener { setNgay(binding.edtd,context).menu_ngay() })
        binding.edtg.setOnClickListener(View.OnClickListener { setGio(binding.edtg,context).menu_gio() })
        binding.edtp.setOnClickListener(View.OnClickListener { setPhut(binding.edtp,context).menu_phut() })

        binding.btnlqLuchaoFragment.setOnClickListener {
            val imm: InputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.edty.windowToken, 0)
            if (CheckDich(binding.edty).kiemtra()){
                gio = binding.edtg.text.toString().toInt()
                phut = Integer.parseInt(binding.edtp.text.toString())
                ngay = Integer.parseInt(binding.edtd.text.toString())
                thang = Integer.parseInt(binding.edtt.text.toString())
                nam = Integer.parseInt(binding.edty.text.toString())

                val ntn = Solar2Lunar(ngay, thang, nam, gio,phut)
                val ctLucHao =
                    CTLucHaoUI(binding.cb1, binding.cb2, binding.cb3, binding.cb4, binding.cb5, binding.cb6, binding.h1, binding.h2, binding.h3, binding.h4, binding.h5, binding.h6)
                val bundle = Bundle().apply {
                    putString("Key.NTN", ntn._ngaythang())
                    putString("Key.TIETKHI", ntn.nguyetLenh())
                    putString("Key.TK", ntn.TuanKhong())
                    putString("Key.CH", binding.edtCauhoi.text.toString())
                    putInt("Key.Luu", 1)
                    putParcelable(
                        "Key.HaoDong",
                        ctLucHao.TinhHaoDong()
                    )
                    putParcelable("Key.QueChinh", ctLucHao._ChuoiQueLucHao())
                    putParcelable("Key.QueBien", ctLucHao._ChuoiQueBienLucHao())
                    putParcelable("Key.QueHo", ctLucHao._XuLyQueHoLucHao())
                    putParcelable("Key.CCNT", ntn.canChiNgayThang as android.os.Parcelable)
                    putString("Key.SoTen", "  PP: Lục Hào")
                    putParcelable(
                        "Key.TT",
                        NgayGioThangNamDL(ngay, gio, thang, nam, phut)
                    )
                }

                Navigation.findNavController(it).navigate(R.id.dichResult, bundle)
            }else return@setOnClickListener

        }


        return root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
