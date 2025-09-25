package com.thanhtam.linhsondich.fragment


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.thanhtam.linhsondich.R
import com.thanhtam.linhsondich.core.*
import com.thanhtam.linhsondich.databinding.DialogXemhinhBinding
import com.thanhtam.linhsondich.databinding.FragmentDichResultBinding
import com.thanhtam.linhsondich.model.*
//import com.thanhtam.linhsondich.roomdata.DichViewModel
//import com.thanhtam.linhsondich.roomdata.dichdatamodel
import com.davemorrissey.labs.subscaleview.ImageSource
import java.util.*

class DichResult : Fragment() {
    private var _binding: FragmentDichResultBinding? = null

    private val binding get() = _binding!!
    companion object {
        var key = "dichresult"
    }
    var ghiChu = ""
//    private lateinit var dichViewModel: DichViewModel
    var luu = 0
    lateinit var ntn: String
    lateinit var TK: String
    lateinit var CH: String
    lateinit var TIETKHI: String
    lateinit var chuoisoten: String
    lateinit var canChiNgayThang: CanChiNgayThang
    lateinit var QueChinh: chuoique
    lateinit var QueBien: chuoique
    lateinit var QueHo: chuoique
    lateinit var haodong: haodong
    lateinit var thongtin: NgayGioThangNamDL
    lateinit var tqc:String
    lateinit var tqb:String
    //    lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ntn = it.getString("Key.NTN")!!
            TK = it.getString("Key.TK")!!
            CH = it.getString("Key.CH")!!
            luu = it.getInt("Key.Luu",0)
            TIETKHI = it.getString("Key.TIETKHI")!!
            chuoisoten = it.getString("Key.SoTen", "")!!
            canChiNgayThang = it.getParcelable("Key.CCNT")!!
            QueBien = it.getParcelable("Key.QueBien")!!
            QueChinh = it.getParcelable("Key.QueChinh")!!
            QueHo = it.getParcelable("Key.QueHo")!!
            haodong = it.getParcelable("Key.HaoDong")!!
            thongtin = it.getParcelable("Key.TT")!!
            ghiChu = it.getString("Key.GhiChu","")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        
        _binding = FragmentDichResultBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)

        val _QueChinh = context?.let {
            _Que(
                QueChinh.h1,
                QueChinh.h2,
                QueChinh.h3,
                QueChinh.h4,
                QueChinh.h5,
                QueChinh.h6,
                it
            ).__Que()
        }
        val _QueBien = context?.let {
            _Que(
                QueBien.h1,
                QueBien.h2,
                QueBien.h3,
                QueBien.h4,
                QueBien.h5,
                QueBien.h6,
                it
            ).__Que()
        }
        val _QueHo = context?.let {
            _Que(
                QueHo.h1,
                QueHo.h2,
                QueHo.h3,
                QueHo.h4,
                QueHo.h5,
                QueHo.h6,
                it
            ).__Que()
        }
        tqc = _QueChinh!!.tenque
        tqb = _QueBien!!.tenque
        binding.txttenque.text = _QueChinh.tenque
        binding.txttqb.text = _QueBien.tenque
        HinhQue(binding.h1, haodong.hd1, _QueChinh.h1,null)
        HinhQue(binding.h2, haodong.hd2, _QueChinh.h2,null)
        HinhQue(binding.h3, haodong.hd3, _QueChinh.h3,null)
        HinhQue(binding.h4, haodong.hd4, _QueChinh.h4,null)
        HinhQue(binding.h5, haodong.hd5, _QueChinh.h5,null)
        HinhQue(binding.h6, haodong.hd6, _QueChinh.h6,null)
        HinhNho(binding.hh1, haodong.hd1, _QueChinh.h1)
        HinhNho(binding.hh2, haodong.hd2, _QueChinh.h2)
        HinhNho(binding.hh3, haodong.hd3, _QueChinh.h3)
        HinhNho(binding.hh4, haodong.hd4, _QueChinh.h4)
        HinhNho(binding.hh5, haodong.hd5, _QueChinh.h5)
        HinhNho(binding.hh6, haodong.hd6, _QueChinh.h6)
        HinhQue(binding.h18, null, _QueBien.h6,null)
        HinhQue(binding.h17, null, _QueBien.h5,null)
        HinhQue(binding.h16, null, _QueBien.h4,null)
        HinhQue(binding.h15, null, _QueBien.h3,null)
        HinhQue(binding.h14, null, _QueBien.h2,null)
        HinhQue(binding.h13, null, _QueBien.h1,null)
        Log.d("chuoisoten", "onCreateView: $chuoisoten")
        binding.txttenqueho.text = _QueHo!!.tenque
        if (chuoisoten!="Mai Hoa"){
            binding.txttenqueho.text = ""
        }
        HinhQue(binding.h12, null, _QueHo.h6,chuoisoten)
        HinhQue(binding.h11, null, _QueHo.h5,chuoisoten)
        HinhQue(binding.h10, null, _QueHo.h4,chuoisoten)
        HinhQue(binding.h9, null, _QueHo.h3,chuoisoten)
        HinhQue(binding.h8, null, _QueHo.h2,chuoisoten)
        HinhQue(binding.h7, null, _QueHo.h1,chuoisoten)
        binding.tuongquechinh.text = _QueChinh.tuongque
        binding.tuongquebien.text = _QueBien.tuongque
        //tenquehotq.text=_QueHo!!.tuongque
        binding.ltchi6.text = _QueChinh.chi6
        binding.ltchi5.text = _QueChinh.chi5
        binding.ltchi4.text = _QueChinh.chi4
        binding.ltchi3.text = _QueChinh.chi3
        binding.ltchi2.text = _QueChinh.chi2
        binding.ltchi1.text = _QueChinh.chi1
        binding.ltchi12.text = _QueBien.chi6
        binding.ltchi11.text = _QueBien.chi5
        binding.ltchi10.text = _QueBien.chi4
        binding.ltchi9.text = _QueBien.chi3
        binding.ltchi8.text = _QueBien.chi2
        binding.ltchi7.text = _QueBien.chi1
        CTDichResult().TinhTheUng(
            binding.tu1,
            binding.tu2,
            binding.tu3,
            binding.tu4,
            binding.tu5,
            binding.tu6,
            _QueChinh.the,
        )
        LucThan(binding.lt7, _QueChinh.cung, _QueBien.chi1.substring(2))
        LucThan(binding.lt8, _QueChinh.cung, _QueBien.chi2.substring(2))
        LucThan(binding.lt9, _QueChinh.cung, _QueBien.chi3.substring(2))
        LucThan(binding.lt10, _QueChinh.cung, _QueBien.chi4.substring(2))
        LucThan(binding.lt11, _QueChinh.cung, _QueBien.chi5.substring(2))
        LucThan(binding.lt12, _QueChinh.cung, _QueBien.chi6.substring(2))
        LucThan(binding.lt1, _QueChinh.cung, _QueChinh.chi1.substring(2))
        LucThan(binding.lt2, _QueChinh.cung, _QueChinh.chi2.substring(2))
        LucThan(binding.lt3, _QueChinh.cung, _QueChinh.chi3.substring(2))
        LucThan(binding.lt4, _QueChinh.cung, _QueChinh.chi4.substring(2))
        LucThan(binding.lt5, _QueChinh.cung, _QueChinh.chi5.substring(2))
        LucThan(binding.lt6, _QueChinh.cung, _QueChinh.chi6.substring(2))
        LucThu(canChiNgayThang.canngay, binding.lth1, binding.lth2, binding.lth3, binding.lth4, binding.lth5, binding.lth6)
        CTTTK(
            binding.tk1,
            binding.tk2,
            binding.tk3,
            binding.tk4,
            binding.tk5,
            binding.tk6,
            binding.tk7,
            binding.tk8,
            binding.tk9,
            binding.tk10,
            binding.tk11,
            binding.tk12,
            TK,
            _QueChinh.chi1.substring(2),
            _QueChinh.chi2.substring(2),
            _QueChinh.chi3.substring(2),
            _QueChinh.chi4.substring(2),
            _QueChinh.chi5.substring(2),
            _QueChinh.chi6.substring(2),
            _QueBien.chi1.substring(2),
            _QueBien.chi2.substring(2),
            _QueBien.chi3.substring(2),
            _QueBien.chi4.substring(2),
            _QueBien.chi5.substring(2),
            _QueBien.chi6.substring(2)
        )
        CTTPT(
            binding.pt1,
            binding.pt2,
            binding.pt3,
            binding.pt4,
            binding.pt5,
            binding.pt6,
            _QueChinh.pt1,
            _QueChinh.pt2,
            _QueChinh.vtpt1,
            _QueChinh.vtpt2
        )
        ThuocTinh(binding.txtThuoctinhQuechinh, _QueChinh.cung, _QueChinh.thuoctinh)
        ThuocTinh(binding.txtThuoctinhQuebien, _QueBien.cung, _QueBien.thuoctinh)

        /////Than Sat
        val thanSat = context?.let {
            ThanSat(
                it,
                canChiNgayThang,
                _QueChinh.the,
                QueChinh,
                TIETKHI,
            )
        }

        binding.txtThansatHao1.text = thanSat!!.ThanSat(_QueChinh.chi1.substring(2), haodong.hd1)
        binding.txtThansatHao2.text = thanSat.ThanSat(_QueChinh.chi2.substring(2), haodong.hd2)
        binding.txtThansatHao3.text = thanSat.ThanSat(_QueChinh.chi3.substring(2), haodong.hd3)
        binding.txtThansatHao4.text = thanSat.ThanSat(_QueChinh.chi4.substring(2), haodong.hd4)
        binding.txtThansatHao5.text = thanSat.ThanSat(_QueChinh.chi5.substring(2), haodong.hd5)
        binding.txtThansatHao6.text = thanSat.ThanSat(_QueChinh.chi6.substring(2), haodong.hd6)
        binding.txtHao1.text = "Hào 1 " + _QueChinh.chi1
        binding.txtHao2.text = "Hào 2 " + _QueChinh.chi2
        binding.txtHao3.text = "Hào 3 " + _QueChinh.chi3
        binding.txtHao4.text = "Hào 4 " + _QueChinh.chi4
        binding.txtHao5.text = "Hào 5 " + _QueChinh.chi5
        binding.txtHao6.text = "Hào 6 " + _QueChinh.chi6
        ////// HighLight
        HighLight(binding.ltchi1, binding.lt1, binding.tu1, binding.tk1, binding.pt1, binding.ltchi7, binding.lt7, binding.tk7, binding.lth1, haodong.hd1, chuoisoten,context!!)
        HighLight(binding.ltchi2, binding.lt2, binding.tu2, binding.tk2, binding.pt2, binding.ltchi8, binding.lt8, binding.tk8, binding.lth2, haodong.hd2, chuoisoten,context!!)
        HighLight(binding.ltchi3, binding.lt3, binding.tu3, binding.tk3, binding.pt3, binding.ltchi9, binding.lt9, binding.tk9, binding.lth3, haodong.hd3, chuoisoten,context!!)
        HighLight(binding.ltchi4, binding.lt4, binding.tu4, binding.tk4, binding.pt4, binding.ltchi10, binding.lt10, binding.tk10, binding.lth4, haodong.hd4, chuoisoten,context!!)
        HighLight(binding.ltchi5, binding.lt5, binding.tu5, binding.tk5, binding.pt5, binding.ltchi11, binding.lt11, binding.tk11, binding.lth5, haodong.hd5, chuoisoten,context!!)
        HighLight(binding.ltchi6, binding.lt6, binding.tu6, binding.tk6, binding.pt6, binding.ltchi12, binding.lt12, binding.tk12, binding.lth6, haodong.hd6, chuoisoten,context!!)


        ////
//        luangiai()
        var thansat = 1
        var luangiai = 1
        binding.txtThansat.setOnClickListener {
            thansat += 1
            if (thansat % 2 == 0) {
                binding.con.visibility = View.GONE
                binding.txtThansat.setTextColor(Color.BLACK)
            } else {
                binding.con.visibility = View.VISIBLE
                binding.txtThansat.setTextColor(ContextCompat.getColor(context!!,R.color.reda))
            }
        }
        binding.txtLuangiai.setOnClickListener {
            luangiai += 1
            if (luangiai % 2 == 0) {
                binding.chau.visibility = View.VISIBLE
                binding.txtLuangiai.setTextColor(ContextCompat.getColor(requireContext(), R.color.reda))
            } else {
                binding.chau.visibility = View.GONE
                binding.txtLuangiai.setTextColor(Color.BLACK)
            }
        }

        binding.txtQuechinh.text = "Quẻ Chính"
        binding.txtQuebien.text = "Quẻ Biến"
        binding.txtLuangiaiQuechinh.text = _QueChinh.luangiai + "\n"

        val arrHaoDong =
            arrayOf(haodong.hd1, haodong.hd2, haodong.hd3, haodong.hd4, haodong.hd5, haodong.hd6)
        val arrLoiHao = arrayOf(_QueChinh.loihao1,
            _QueChinh.loihao2,
            _QueChinh.loihao3,
            _QueChinh.loihao4,
            _QueChinh.loihao5,
            _QueChinh.loihao6)

        for (i in arrHaoDong.indices) {
            if (arrHaoDong[i] == 1) {
                binding.txtLuangiaiQuechinh.text = binding.txtLuangiaiQuechinh.text.toString() + arrLoiHao[i]
            }
        }
        binding.txtLuangiaiQuebien.text = _QueBien.luangiai


        var phucngam = ""
        var noiphucngam = ""
        var ngoaiphucngam = ""
        var phanngam = ""
        var noiphanngam = ""
        var ngoaiphanngam = ""
        val chichinh1 = _QueChinh.chi1.substring(2)
        val chibien1 = _QueBien.chi1.substring(2)
        val chichinh4 = _QueChinh.chi4.substring(2)
        val chibien4 = _QueBien.chi4.substring(2)
        if (haodong.hd1 == 1 || haodong.hd2 == 1 || haodong.hd3 == 1) {
            if (chichinh1 == chibien1) {
                phucngam = "-Nội Phục Ngâm"
                noiphucngam = "npn"
            }
        }
        if (haodong.hd4 == 1 || haodong.hd5 == 1 || haodong.hd6 == 1) {
            if (chichinh4 == chibien4) {
                phucngam = "-Ngoại Phục Ngâm"
                ngoaiphucngam = "npn"
            }
        }
        if (noiphucngam == "npn" && ngoaiphucngam == "npn") {
            phucngam = "-Phục Ngâm"
        }
        if ((_QueChinh.h2 == 1 && chichinh1 == "Tí" && chibien1 == "Sửu") || (chichinh1 == "Sửu" && chibien1 == "Tí" && _QueBien.h2 == 1) || (chichinh1 == "Mão" && chibien1 == "Dần") || (chibien1 == "Mão" && chichinh1 == "Dần") || (chichinh1 == "Thìn" && chibien1 == "Mùi") || (chibien1 == "Thìn" && chichinh1 == "Mùi") || (_QueChinh.h2 == 0 && chichinh1 == "Tí" && chibien1 == "Tị") || (_QueBien.h2 == 0 && chibien1 == "Tí" && chichinh1 == "Tị")) {
            phanngam = "-Nội Phản Ngâm"
            noiphanngam = "npn"
        }
        if ((_QueChinh.h5 == 1 && chichinh4 == "Ngọ" && chibien4 == "Mùi") || (chichinh4 == "Mùi" && chibien4 == "Ngọ" && _QueBien.h5 == 1) || (chichinh4 == "Dậu" && chibien4 == "Thân") || (chibien4 == "Dậu" && chichinh4 == "Thân") || (chichinh4 == "Tuất" && chibien4 == "Sửu") || (chibien4 == "Tuất" && chichinh4 == "Sửu") || (_QueChinh.h5 ==0 &&chichinh4 == "Ngọ" && chibien4 == "Hợi")|| (_QueBien.h5 ==0 &&chibien4 == "Ngọ" && chichinh4 == "Hợi")) {
            phanngam = "-Ngoại Phản Ngâm"
            ngoaiphanngam = "npn"
        }
        if (noiphanngam == "npn" && ngoaiphanngam == "npn") {
            phucngam = "-Phản Ngâm"
        }
        binding.txtntn.text = "$ntn-$chuoisoten$phucngam$phanngam" + if (CH == "") "" else "\n$CH"
        
        return root
    }

    private lateinit var bindingDialog: DialogXemhinhBinding
    private fun openDialog(bitmap: Bitmap) {
        bindingDialog = DialogXemhinhBinding.inflate(LayoutInflater.from(activity))
        val builder = AlertDialog.Builder(activity)
        builder.setView(bindingDialog.root)
        val dialog = builder.create()
        dialog.show()
        bindingDialog.imgTest.setImage(ImageSource.bitmap(bitmap))

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_dich_result, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_chupmanhinh -> {
                val date = Date()
                context?.let { it1 ->
                    TakeSS(
                        binding.contentmhResultFragment,
                        it1,
                    ).savescrolltoGallery()
                }
            }
            R.id.menu_xem -> {
                val b = binding.contentmhResultFragment.drawToBitmap()
                openDialog(b)
            }
        }
        return true
    }

}