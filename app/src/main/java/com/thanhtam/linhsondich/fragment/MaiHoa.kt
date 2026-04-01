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
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.Navigation
import com.thanhtam.linhsondich.R
import com.thanhtam.linhsondich.core.CTMaiHoa
import com.thanhtam.linhsondich.core.Solar2Lunar
import com.thanhtam.linhsondich.databinding.FragmentMaiHoaBinding
import com.thanhtam.linhsondich.model.*
import com.thanhtam.linhsondich.viewmodels.LiveDataNgayThangNam
import java.util.*


class MaiHoa : Fragment() {
    private var _binding: FragmentMaiHoaBinding? = null

    private val binding get() = _binding!!


    var ngay: Int = 0
    var gio: Int = 0
    var thang: Int = 0
    var nam: Int = 0
    var phut: Int = 0
    var chuoisoten = ""
    var somaihoa = 0
    var count = 0
    var baSoSP = ""
    lateinit var viewModel: LiveDataNgayThangNam
    lateinit var receiver: BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LiveDataNgayThangNam::class.java)
        receiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                viewModel.TT.value = p1?.getParcelableExtra(DichResult.key)!!
            }
        }
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(receiver, IntentFilter(DichResult.key))
        arguments?.let {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentMaiHoaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //setHasOptionsMenu(true)

        val context = context
        viewModel = ViewModelProvider(this)[LiveDataNgayThangNam::class.java]
        var connectivity =
            context?.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
        val cal = Calendar.getInstance()

        if (viewModel.TT.value == null) {
            binding.edty.setText(cal.get(Calendar.YEAR).toString())
            binding.edtt.text = (cal.get(Calendar.MONTH) + 1).toString()
            binding.edtd.text = (cal.get(Calendar.DATE)).toString()
            binding.edtg.text = (cal.get(Calendar.HOUR_OF_DAY)).toString()
            binding.edtp.text = (cal.get(Calendar.MINUTE)).toString()
        } else {
            viewModel.TT.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                binding.edtt.text = it.thang.toString()
                binding.edtd.text = it.ngay.toString()
                binding.edtg.text = it.gio.toString()
                binding.edtp.text = it.phut.toString()
                binding.edty.setText(it.nam.toString())
            })
        }
        binding.edtt.setOnClickListener(View.OnClickListener { setThang(binding.edtt, context).menu_thang() })
        binding.edtd.setOnClickListener(View.OnClickListener { setNgay(binding.edtd, context).menu_ngay() })
        binding.edtg.setOnClickListener(View.OnClickListener { setGio(binding.edtg, context).menu_gio() })
        binding.edtp.setOnClickListener(View.OnClickListener { setPhut(binding.edtp, context).menu_phut() })
        binding.rdoThuong.setOnClickListener {
            binding.edtSo.visibility = View.GONE
            binding.edtTen.visibility = View.GONE
            binding.edtSo.setText("")
            binding.edtTen.setText("")
        }
        binding.rdoSo.setOnClickListener {
            binding.edtSo.visibility = View.VISIBLE
            binding.edtTen.visibility = View.GONE
            binding.edtTen.setText("")
            binding.edtSo.hint = "1,3,Series"
            binding.edtSo.setText("")
            binding.edtSo.filters = arrayOf<InputFilter>(LengthFilter(15))
        }
//        rdo_series.setOnClickListener {
//            edt_so.visibility = View.VISIBLE
//            edt_ten.visibility = View.GONE
//            edt_ten.setText("")
//            edt_so.hint = "Nhập Series"
//            edt_so.setText("")
//            edt_so.filters = arrayOf<InputFilter>(LengthFilter(15))
//        }
        binding.rdoTen.setOnClickListener {
            binding.edtSo.visibility = View.GONE
            binding.edtTen.visibility = View.VISIBLE
            binding.edtSo.setText("")
        }
        binding.contentMaihoa.setOnClickListener {
            count++
            somaihoa = if (count % 5 == 0) 1 else 0
            if (count % 5 == 0) {
                binding.edtSo.hint = "1,3(2),Series"
                baSoSP = "(2)"
            } else {
                binding.edtSo.hint = "1,3,Series"
                baSoSP = ""
            }
        }
        binding.btnlqMaihoaFragment.setOnClickListener {
            val imm: InputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.edty.windowToken, 0)
            val imn: InputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imn.hideSoftInputFromWindow(binding.edtSo.windowToken, 0)
            val imo: InputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imo.hideSoftInputFromWindow(binding.edtTen.windowToken, 0)
            if (CheckDich(binding.edty).kiemtra()) {
                chuoisoten = if (binding.rdoTen.isChecked || binding.rdoSo.isChecked) {
                    binding.edtSo.text.toString() + baSoSP + binding.edtTen.text.toString()
                } else "Mai Hoa"
                gio = binding.edtg.text.toString().toInt()
                phut = Integer.parseInt(binding.edtp.text.toString())
                ngay = Integer.parseInt(binding.edtd.text.toString())
                thang = Integer.parseInt(binding.edtt.text.toString())
                nam = Integer.parseInt(binding.edty.text.toString())
                val ntn = Solar2Lunar(ngay, thang, nam, gio,phut)
                val dd = ntn.Solar2Lunar()[0]
                val mm = ntn.Solar2Lunar()[1]
                val yy = ntn.Solar2Lunar()[2]

                val type = when {
                    binding.rdoThuong.isChecked -> MaiHoaType.THUONG
                    binding.rdoSo.isChecked -> MaiHoaType.SO
                    binding.rdoTen.isChecked -> MaiHoaType.TEN
                    else -> MaiHoaType.THUONG
                }

                val chuoiSoVal = binding.edtSo.text.toString()
                val tenVal = binding.edtTen.text.toString()

                // Validation logic moved from the old CTMaiHoa
                var isValid = true
                if (type == MaiHoaType.SO) {
                    if (chuoiSoVal.isEmpty()) {
                        binding.edtSo.error = "Bạn Chưa Nhập Số"
                        isValid = false
                    } else if (chuoiSoVal.contains("0") && (chuoiSoVal.length == 1 || chuoiSoVal.length == 3)) {
                        binding.edtSo.error = "Không Nhập Số 0"
                        isValid = false
                    }
                } else if (type == MaiHoaType.TEN) {
                    if (tenVal.isEmpty()) {
                        binding.edtTen.error = "Bạn Chưa Nhập Tên"
                        isValid = false
                    }
                }

                if (isValid) {
                    // Using the CTMaiHoa from shared module
                    val que = CTMaiHoa(
                        dd,
                        mm,
                        ((yy + 8) % 12) + 1,
                        gio,
                        somaihoa,
                        type,
                        chuoiSoVal,
                        tenVal
                    )
                    
                    val queChinh = que.tinhQueChinh()
                    val haoDong = que.tinhHaoDong()
                    val queBien = que.tinhQueBien(queChinh, haoDong)
                    val queHo = que.tinhQueHo(queChinh)
                    
                    val bundle = Bundle().apply {
                        putString("Key.NTN", ntn._ngaythang())
                        putString("Key.TIETKHI", ntn.nguyetLenh())
                        putString("Key.TK", ntn.TuanKhong())
                        putString("Key.CH", binding.edtCauhoi.text.toString())
                        putString("Key.SoTen", chuoisoten)
                        putInt("Key.Luu", 1)
                        putParcelable("Key.CCNT", ntn.canChiNgayThang)
                        putParcelable("Key.QueChinh", queChinh)
                        putParcelable("Key.HaoDong", haoDong)
                        putParcelable("Key.QueBien", queBien)
                        putParcelable("Key.QueHo", queHo)
                        putParcelable(
                            "Key.TT",
                            NgayGioThangNamDL(ngay, gio, thang, nam, phut)
                        )
                    }
                    Navigation.findNavController(it).navigate(R.id.dichResult, bundle)
                } else return@setOnClickListener
            } else return@setOnClickListener
        }
        
        return root

    }

    
    fun menu_ngay() {
        val menu = PopupMenu(context, binding.edtd)
        menu.menuInflater.inflate(R.menu.menu_ngay, menu.menu)
        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.ngay1 -> binding.edtd.text = "1"
                R.id.ngay2 -> binding.edtd.text = "2"
                R.id.ngay3 -> binding.edtd.text = "3"
                R.id.ngay4 -> binding.edtd.text = "4"
                R.id.ngay5 -> binding.edtd.text = "5"
                R.id.ngay6 -> binding.edtd.text = "6"
                R.id.ngay7 -> binding.edtd.text = "7"
                R.id.ngay8 -> binding.edtd.text = "8"
                R.id.ngay9 -> binding.edtd.text = "9"
                R.id.ngay10 -> binding.edtd.text = "10"
                R.id.ngay11 -> binding.edtd.text = "11"
                R.id.ngay12 -> binding.edtd.text = "12"
                R.id.ngay13 -> binding.edtd.text = "13"
                R.id.ngay14 -> binding.edtd.text = "14"
                R.id.ngay15 -> binding.edtd.text = "15"
                R.id.ngay16 -> binding.edtd.text = "16"
                R.id.ngay17 -> binding.edtd.text = "17"
                R.id.ngay18 -> binding.edtd.text = "18"
                R.id.ngay19 -> binding.edtd.text = "19"
                R.id.ngay20 -> binding.edtd.text = "20"
                R.id.ngay21 -> binding.edtd.text = "21"
                R.id.ngay22 -> binding.edtd.text = "22"
                R.id.ngay23 -> binding.edtd.text = "23"
                R.id.ngay24 -> binding.edtd.text = "24"
                R.id.ngay25 -> binding.edtd.text = "25"
                R.id.ngay26 -> binding.edtd.text = "26"
                R.id.ngay27 -> binding.edtd.text = "27"
                R.id.ngay28 -> binding.edtd.text = "28"
                R.id.ngay29 -> binding.edtd.text = "29"
                R.id.ngay30 -> binding.edtd.text = "30"
                R.id.ngay31 -> binding.edtd.text = "31"
            }
            false
        }
        menu.show()
    }

    fun menu_thang() {
        val menu = PopupMenu(context, binding.edtt)
        menu.menuInflater.inflate(R.menu.menu_thang, menu.menu)
        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.thang1 -> binding.edtt.text = "1"
                R.id.thang2 -> binding.edtt.text = "2"
                R.id.thang3 -> binding.edtt.text = "3"
                R.id.thang4 -> binding.edtt.text = "4"
                R.id.thang5 -> binding.edtt.text = "5"
                R.id.thang6 -> binding.edtt.text = "6"
                R.id.thang7 -> binding.edtt.text = "7"
                R.id.thang8 -> binding.edtt.text = "8"
                R.id.thang9 -> binding.edtt.text = "9"
                R.id.thang10 -> binding.edtt.text = "10"
                R.id.thang11 -> binding.edtt.text = "11"
                R.id.thang12 -> binding.edtt.text = "12"
            }
            false
        }
        menu.show()
    }

    fun menu_gio() {
        val menu = PopupMenu(context, binding.edtg)
        menu.menuInflater.inflate(R.menu.menu_gio, menu.menu)
        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.gio1 -> binding.edtg.text = "1"
                R.id.gio2 -> binding.edtg.text = "2"
                R.id.gio3 -> binding.edtg.text = "3"
                R.id.gio4 -> binding.edtg.text = "4"
                R.id.gio5 -> binding.edtg.text = "5"
                R.id.gio6 -> binding.edtg.text = "6"
                R.id.gio7 -> binding.edtg.text = "7"
                R.id.gio8 -> binding.edtg.text = "8"
                R.id.gio9 -> binding.edtg.text = "9"
                R.id.gio10 -> binding.edtg.text = "10"
                R.id.gio11 -> binding.edtg.text = "11"
                R.id.gio12 -> binding.edtg.text = "12"
            }
            false
        }
        menu.show()
    }

    @SuppressLint("SetTextI18n")
    fun menu_phut() {
        val menu = PopupMenu(context, binding.edtp)
        menu.menuInflater.inflate(R.menu.menu_phut, menu.menu)
        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.phut0 -> binding.edtp.text = "0"
                R.id.phut1 -> binding.edtp.text = "1"
                R.id.phut2 -> binding.edtp.text = "2"
                R.id.phut3 -> binding.edtp.text = "3"
                R.id.phut4 -> binding.edtp.text = "4"
                R.id.phut5 -> binding.edtp.text = "5"
                R.id.phut6 -> binding.edtp.text = "6"
                R.id.phut7 -> binding.edtp.text = "7"
                R.id.phut8 -> binding.edtp.text = "8"
                R.id.phut9 -> binding.edtp.text = "9"
                R.id.phut10 -> binding.edtp.text = "10"
                R.id.phut11 -> binding.edtp.text = "11"
                R.id.phut12 -> binding.edtp.text = "12"
                R.id.phut13 -> binding.edtp.text = "13"
                R.id.phut14 -> binding.edtp.text = "14"
                R.id.phut15 -> binding.edtp.text = "15"
                R.id.phut16 -> binding.edtp.text = "16"
                R.id.phut17 -> binding.edtp.text = "17"
                R.id.phut18 -> binding.edtp.text = "18"
                R.id.phut19 -> binding.edtp.text = "19"
                R.id.phut20 -> binding.edtp.text = "20"
                R.id.phut21 -> binding.edtp.text = "21"
                R.id.phut22 -> binding.edtp.text = "22"
                R.id.phut23 -> binding.edtp.text = "23"
                R.id.phut24 -> binding.edtp.text = "24"
                R.id.phut25 -> binding.edtp.text = "25"
                R.id.phut26 -> binding.edtp.text = "26"
                R.id.phut27 -> binding.edtp.text = "27"
                R.id.phut28 -> binding.edtp.text = "28"
                R.id.phut29 -> binding.edtp.text = "29"
                R.id.phut30 -> binding.edtp.text = "30"
                R.id.phut31 -> binding.edtp.text = "31"
                R.id.phut32 -> binding.edtp.text = "32"
                R.id.phut33 -> binding.edtp.text = "33"
                R.id.phut34 -> binding.edtp.text = "34"
                R.id.phut35 -> binding.edtp.text = "35"
                R.id.phut36 -> binding.edtp.text = "36"
                R.id.phut37 -> binding.edtp.text = "37"
                R.id.phut38 -> binding.edtp.text = "38"
                R.id.phut39 -> binding.edtp.text = "39"
                R.id.phut40 -> binding.edtp.text = "40"
                R.id.phut41 -> binding.edtp.text = "41"
                R.id.phut42 -> binding.edtp.text = "42"
                R.id.phut43 -> binding.edtp.text = "43"
                R.id.phut44 -> binding.edtp.text = "44"
                R.id.phut45 -> binding.edtp.text = "45"
                R.id.phut46 -> binding.edtp.text = "46"
                R.id.phut47 -> binding.edtp.text = "47"
                R.id.phut48 -> binding.edtp.text = "48"
                R.id.phut49 -> binding.edtp.text = "49"
                R.id.phut50 -> binding.edtp.text = "50"
                R.id.phut51 -> binding.edtp.text = "51"
                R.id.phut52 -> binding.edtp.text = "52"
                R.id.phut53 -> binding.edtp.text = "53"
                R.id.phut54 -> binding.edtp.text = "54"
                R.id.phut55 -> binding.edtp.text = "55"
                R.id.phut56 -> binding.edtp.text = "56"
                R.id.phut57 -> binding.edtp.text = "57"
                R.id.phut58 -> binding.edtp.text = "58"
                R.id.phut59 -> binding.edtp.text = "59"
            }
            false
        }
        menu.show()
    }
}