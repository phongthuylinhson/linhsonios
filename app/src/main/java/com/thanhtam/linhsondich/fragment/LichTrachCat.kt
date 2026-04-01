package com.thanhtam.linhsondich.fragment

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.thanhtam.linhsondich.R
import com.thanhtam.linhsondich.common.Common
import com.thanhtam.linhsondich.core.Lunar2Solar
import com.thanhtam.linhsondich.core.Solar2Lunar
import com.thanhtam.linhsondich.databinding.FragmentLichTrachCatBinding
import com.thanhtam.linhsondich.model.CanChi
import com.thanhtam.linhsondich.model.NgayGioThangNam
import com.thanhtam.linhsondich.model.NgayGioThangNamDL
import com.thanhtam.linhsondich.viewmodels.LiveDataNgayThangNam
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import java.util.*


class LichTrachCat : Fragment() {

    private var _binding: FragmentLichTrachCatBinding? = null

    private val binding get() = _binding!!
    
    var GIO_HD = arrayOf(
        "110100101100",
        "001101001011",
        "110011010010",
        "101100110100",
        "001011001101",
        "010010110011"
    )
    val LOG = "GIOTT"
    lateinit var viewModel: LiveDataNgayThangNam
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLichTrachCatBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this)[LiveDataNgayThangNam::class.java]
        binding.txtSer2.setOnClickListener { openFaceBook() }
        binding.txtSer3.setOnClickListener { sendEmail() }
        binding.sdt1.setOnClickListener { phoneCall1() }
        binding.sdt2.setOnClickListener { phoneCall2() }
        val cal=Calendar.getInstance()
//        ngayduong.maxValue=31
//        ngayduong.minValue=1
//        thangduong.maxValue=12
//        thangduong.minValue=1
        binding.namduong.maxValue=2199
        binding.namduong.minValue=1900
        binding.namduong.value=cal.get(Calendar.YEAR)
        //ngayduong.setValue() =cal.get(Calendar.DATE)
        binding.namduong.setFormatter { value -> value.toString() }
        binding.ngayduong.value=cal.get(Calendar.DATE)
        binding.thangduong.value=cal.get(Calendar.MONTH)+1
        binding.ngayam.maxValue=31
        binding.ngayam.minValue=1
        binding.thangam.maxValue=12
        binding.thangam.minValue=1
        binding.namam.maxValue=2199
        binding.namam.minValue=1900
        binding.namam.setFormatter { value -> value.toString() }
        var ntnam=Solar2Lunar(viewModel.dd,viewModel.mm,viewModel.yy,6,0)
        binding.namam.value=ntnam.Solar2Lunar()[2]
        binding.thangam.value=ntnam.Solar2Lunar()[1]
        binding.ngayam.value=ntnam.Solar2Lunar()[0]
        binding.txtGioHD.text=getGioHoangDao(ntnam.JD().toInt())
        binding.txtDate.text=ntnam._lichtrachcat()
        binding.txtGioTT.text = getGioTT(ntnam.JD().toInt(),ntnam.nguyetTuong())
        /// ngày dương
        binding.ngayduong.setOnValueChangedListener { ngayduong, i, i2 -> viewModel.dd=ngayduong.value
            ntnam=Solar2Lunar(viewModel.dd,viewModel.mm,viewModel.yy,viewModel.hh,viewModel.mi)
            val b= NgayGioThangNam(ntnam.Solar2Lunar()[0],viewModel.hh,ntnam.Solar2Lunar()[1],ntnam.Solar2Lunar()[2],viewModel.mi)
            viewModel.Test.value=b
            viewModel.Test.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                binding.thangam.value=it.thang
                binding.ngayam.value=it.ngay
                binding.namam.value=it.nam
                val ntnamlich=viewModel.test(it.ngay,it.thang,it.nam,6)
                viewModel.ddd=ntnamlich.dddd
                viewModel.mmm=ntnamlich.mmmm
                viewModel.yyy=ntnamlich.yyyy
                binding.txtGioHD.text=getGioHoangDao(ntnam.JD().toInt())
                binding.txtDate.text=ntnam._lichtrachcat()
                binding.txtGioTT.text = getGioTT(ntnam.JD().toInt(),ntnam.nguyetTuong())
            })}
        /// tháng dương
        binding.thangduong.setOnValueChangedListener { thangduong, i, i2 -> viewModel.mm=thangduong.value
            ntnam=Solar2Lunar(viewModel.dd,viewModel.mm,viewModel.yy,viewModel.hh,viewModel.mi)
            val b= NgayGioThangNam(ntnam.Solar2Lunar()[0],viewModel.hh,ntnam.Solar2Lunar()[1],ntnam.Solar2Lunar()[2],viewModel.mi)
            viewModel.Test.value=b
            viewModel.Test.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                binding.thangam.value=it.thang
                binding.ngayam.value=it.ngay
                binding.namam.value=it.nam
                val ntnamlich=viewModel.test(it.ngay,it.thang,it.nam,6)
                viewModel.ddd=ntnamlich.dddd
                viewModel.mmm=ntnamlich.mmmm
                viewModel.yyy=ntnamlich.yyyy
                binding.txtGioHD.text=getGioHoangDao(ntnam.JD().toInt())
                binding.txtDate.text=ntnam._lichtrachcat()
                binding.txtGioTT.text = getGioTT(ntnam.JD().toInt(),ntnam.nguyetTuong())
            })}
        //// năm dương
        binding.namduong.setOnValueChangedListener { namduong, i, i2 -> viewModel.yy=namduong.value
            ntnam=Solar2Lunar(viewModel.dd,viewModel.mm,viewModel.yy,viewModel.hh,viewModel.mi)
            val b= NgayGioThangNam(ntnam.Solar2Lunar()[0],viewModel.hh,ntnam.Solar2Lunar()[1],ntnam.Solar2Lunar()[2],viewModel.mi)
            viewModel.Test.value=b
            viewModel.Test.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                binding.thangam.value=it.thang
                binding.ngayam.value=it.ngay
                binding.namam.value=it.nam
                val ntnamlich=viewModel.test(it.ngay,it.thang,it.nam,6)
                viewModel.ddd=ntnamlich.dddd
                viewModel.mmm=ntnamlich.mmmm
                viewModel.yyy=ntnamlich.yyyy
                binding.txtGioHD.text=getGioHoangDao(ntnam.JD().toInt())
                binding.txtDate.text=ntnam._lichtrachcat()
                binding.txtGioTT.text = getGioTT(ntnam.JD().toInt(),ntnam.nguyetTuong())
            })}

        var ntnduong=Lunar2Solar(viewModel.yyy,viewModel.mmm,viewModel.ddd,false,6,7)
        binding.namduong.value=viewModel.yy
        binding.thangduong.value=viewModel.mm
        binding.ngayduong.value=viewModel.dd
        //// ngày âm
        binding.ngayam.setOnValueChangedListener { ngayam, i, i2 -> viewModel.ddd=ngayam.value
            ntnduong=Lunar2Solar(viewModel.yyy,viewModel.mmm,viewModel.ddd,false,6,7)
            val b= NgayGioThangNamDL(ntnduong.lunar2solar().day,viewModel.hh,ntnduong.lunar2solar().month,ntnduong.lunar2solar().year,0)
            viewModel.TT.value=b
            viewModel.TT.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                binding.thangduong.value=it.thang
                binding.ngayduong.value=it.ngay
                binding.namduong.value=it.nam
                viewModel.dd=it.ngay
                viewModel.mm=it.thang
                viewModel.yy=it.nam
                ntnam=Solar2Lunar(viewModel.dd,viewModel.mm,viewModel.yy,viewModel.hh,viewModel.mi)
                binding.txtGioHD.text=getGioHoangDao(ntnam.JD().toInt())
                binding.txtDate.text=ntnam._lichtrachcat()
                binding.txtGioTT.text = getGioTT(ntnam.JD().toInt(),ntnam.nguyetTuong())
            })}
        //// tháng âm
        binding.thangam.setOnValueChangedListener { thangam, i, i2 -> viewModel.mmm=thangam.value
            ntnduong=Lunar2Solar(viewModel.yyy,viewModel.mmm,viewModel.ddd,false,6,7)
            val b= NgayGioThangNamDL(ntnduong.lunar2solar().day,viewModel.hh,ntnduong.lunar2solar().month,ntnduong.lunar2solar().year,0)
            viewModel.TT.value=b
            viewModel.TT.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                binding.thangduong.value=it.thang
                binding.ngayduong.value=it.ngay
                binding.namduong.value=it.nam
                viewModel.dd=it.ngay
                viewModel.mm=it.thang
                viewModel.yy=it.nam
                ntnam=Solar2Lunar(viewModel.dd,viewModel.mm,viewModel.yy,viewModel.hh,viewModel.mi)
                binding.txtGioHD.text=getGioHoangDao(ntnam.JD().toInt())
                binding.txtDate.text=ntnam._lichtrachcat()
                binding.txtGioTT.text = getGioTT(ntnam.JD().toInt(),ntnam.nguyetTuong())
            })}
        //// năm âm
        binding.namam.setOnValueChangedListener { namam, i, i2 -> viewModel.yyy=namam.value
            ntnduong=Lunar2Solar(viewModel.yyy,viewModel.mmm,viewModel.ddd,false,6,7)
            val b= NgayGioThangNamDL(ntnduong.lunar2solar().day,viewModel.hh,ntnduong.lunar2solar().month,ntnduong.lunar2solar().year,0)
            viewModel.TT.value=b
            viewModel.TT.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                binding.thangduong.value=it.thang
                binding.ngayduong.value=it.ngay
                binding.namduong.value=it.nam
                viewModel.dd=it.ngay
                viewModel.mm=it.thang
                viewModel.yy=it.nam
                ntnam=Solar2Lunar(viewModel.dd,viewModel.mm,viewModel.yy,viewModel.hh,viewModel.mi)
                binding.txtGioHD.text=getGioHoangDao(ntnam.JD().toInt())
                binding.txtDate.text=ntnam._lichtrachcat()
                binding.txtGioTT.text = getGioTT(ntnam.JD().toInt(),ntnam.nguyetTuong())
            })}
//        binding.ngaythang.setOnClickListener {
//            val bundle= Bundle().apply {
//                putString("KEY.Str",ntnam._canchingay())
//            }
//            Navigation.findNavController(it).navigate(R.id.nav_lichTrachCatDetails, bundle)
//        }


        inAppReview()


        return root

    }

    fun getGioHoangDao(jd:Int):String {
        val chiOfDay = (jd+1) % 12
        val gioHD = GIO_HD[chiOfDay % 6] // same values for Ty' (1) and Ngo. (6), for Suu and Mui etc.
        var ret = ""
        var count = 0
        for (i in 0..11) {
            if (gioHD[i] == '1') {
                ret += CanChi().chi[i]
                ret += " (${(i*2+23)%24}-${(i*2+1)%24})"
                if (count++ < 5) ret += ", "
                if (count == 3) ret += '\n'
            }
        }
        return ret
    }
    /// tim can ngay de xem cung co am quy nhan va duong quy nhan o dau
    /// tim nguyet tuong
    /// tu cung co duong quy nhan hoac am quy nhan di den cung hoi la bao nhiu buoc (DQN di thuan, AQN di nghich)
    /// tu nguyet tuong cung di thuan/nghich bao nhieu buoc do, neu nam trong tu mao -> than la co DQN, nam trong Dau->Dan la co AQN
    fun getGioTT(jd:Int, nguyetTuong:String):String {
        //val can = CanChi().can
        val chi = CanChi().chi
//        var can = arrayOf("Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ", "Canh", "Tân", "Nhâm", "Quý")
        var ret = ""
        val canngay = (jd + 9) % 10
        var DQN = ""
        var AQN = ""
        var vtdqn = 0
        var vtaqn = 0
        var dqn = 0 // la so tu vi tri duong quy nhan tang len chi hoi
        var aqn = 0 // la so tu vi tri am quy nhan tang len chi hoi
        var ntd = 0 // la so tu vi tri nguyet tuong tang theo duong quy nhan
        var nta = 0 // la so tu vi tri nguyet tuong tang theo am quy nhan
        when (canngay){
            0->{
                DQN = "Mùi"
                AQN = "Sửu"
            }
            1->{
                DQN = "Thân"
                AQN = "Tý"
            }
            2->{
                DQN = "Dậu"
                AQN = "Hợi"
            }
            3->{
                DQN = "Hợi"
                AQN = "Dậu"
            }
            4,6->{
                DQN = "Sửu"
                AQN = "Mùi"
            }
            5->{
                DQN = "Tí"
                AQN = "Thân"
            }
            7->{
                DQN = "Dần"
                AQN = "Ngọ"
            }
            8->{
                DQN = "Mão"
                AQN = "Tỵ"
            }
            9->{
                DQN = "Tỵ"
                AQN = "Mão"
            }
        }
//        Log.d(LOG, "DQN: $DQN , AQN: $AQN")
        for (i in chi.indices){
            if (DQN==chi[i]) {
                vtdqn = i
                break
            }
        }
        Log.d(LOG, "vtdqn: $vtdqn ")
        for (i in chi.indices){
            if (chi[vtdqn]=="Hợi"){
                dqn = i
                break
            }
            vtdqn++
        }
        Log.d(LOG, "dqn: $dqn ")


        for (i in chi.indices){
            if (AQN==chi[i]) {
                vtaqn = i
                break
            }
        }
//        Log.d(LOG, "vtaqn : $vtaqn")
        for (i in chi.indices){
            if (chi[vtaqn]=="Hợi"){
                aqn = i
                if (aqn!=0) aqn = 12-aqn
                break
            }
            vtaqn++
        }
//        Log.d(LOG, "aqn : $aqn ")
        for (i in chi.indices){
            if (nguyetTuong==chi[i]){
                Log.d(LOG, "getGioTT: $i")
                ntd = if (i + dqn > 11) i + dqn - 12 else i + dqn
                break
            }
        }
        for (i in chi.indices){
            if (nguyetTuong==chi[i]){
                Log.d(LOG, "getGioTT: $i")
                nta = if (i - aqn < 0) i - aqn + 12 else i - aqn
                break
            }
        }
        var gioTTD=""
        var gioTTA = ""
        when(chi[ntd]){
            "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi", "Thân"->gioTTD = "giờ " + chi[ntd]
        }
        when(chi[nta]){
            "Dậu", "Tuất", "Hợi","Tí", "Sửu", "Dần"->gioTTA = " giờ " + chi[nta]
        }
        ret = gioTTD + gioTTA
        return ret
    }
    private fun getFacebookPageURL(context: Context): String? {
        val packageManager: PackageManager = context.packageManager
        return try {
            val versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
            if (versionCode >= 3002850) { //newer versions of fb app
                "fb://facewebmodal/f?href=${Common.FACEBOOK_URL}"
            } else { //older versions of fb app
                "fb://page/${Common.FACEBOOK_PAGE_ID}"
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Common.FACEBOOK_URL //normal web url
        }
    }

    private fun openFaceBook() {
        val facebookIntent = Intent(Intent.ACTION_VIEW)
        val facebookUrl = context?.let { getFacebookPageURL(it) }
        facebookIntent.data = Uri.parse(facebookUrl)
        startActivity(facebookIntent)
    }

    private fun phoneCall1(){
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${Common.PHONE1}")
        }
        startActivity(intent)
    }

    private fun phoneCall2(){
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${Common.PHONE2}")
        }
        startActivity(intent)
    }

    private fun sendEmail() {
        /*ACTION_SEND action to launch an email client installed on your Android device.*/
        val mIntent = Intent(Intent.ACTION_SEND)
        /*To send an email you need to specify mailto: as URI using setData() method
        and data type will be to text/plain using setType() method*/
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        // put recipient email in intent
        /* recipient is put as array because you may wanna send email to multiple emails
           so enter comma(,) separated emails, it will be stored in array*/
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf( Common.GMAIL_ID))
        //put the Subject in the intent
        mIntent.putExtra(Intent.EXTRA_SUBJECT, Common.GMAIL_subject)
//        //put the message in the intent
//        mIntent.putExtra(Intent.EXTRA_TEXT, message)


        try {
            //start email intent
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        }
        catch (e: Exception){
            //if any thing goes wrong for example no email client application or any exception
            //get and show exception message
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }

    }


    private fun inAppReview(){
        val manager = ReviewManagerFactory.create(context!!)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // We got the ReviewInfo object
                val reviewInfo = task.result
            } else {
                // There was some problem, log or handle the error code.
                @ReviewErrorCode val reviewErrorCode = (task.exception as ReviewException).errorCode
            }
        }
    }

}
