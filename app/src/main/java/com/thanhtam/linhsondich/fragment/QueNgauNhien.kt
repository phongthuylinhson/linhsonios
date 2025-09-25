package com.thanhtam.linhsondich.fragment

import android.app.Service
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.thanhtam.linhsondich.R
import com.thanhtam.linhsondich.core.CTNgauNhien
import com.thanhtam.linhsondich.databinding.FragmentQueNgauNhienBinding
import com.thanhtam.linhsondich.model.NTN
import com.thanhtam.linhsondich.model.NgayGioThangNamDL
import java.util.*


class QueNgauNhien : Fragment() {
    private var _binding: FragmentQueNgauNhienBinding? = null

    private val binding get() = _binding!!

    var lq = 0
    var cb1 = 0
    var cb2 = 0
    var cb3 = 0
    var cb4 = 0
    var cb5 = 0
    var cb6 = 0
    var h1 = 0
    var h2 = 0
    var h3 = 0
    var h4 = 0
    var h5 = 0
    var h6 = 0
    var nam=0
    var thang=0
    var ngay=0
    var gio=0
    var phut=0

    //    val random1 = (0..3).random()
//    val random2 = (0..3).random()
//    val random3 = (0..3).random()
//    val random4 = (0..3).random()
//    val random5 = (0..3).random()
//    val random6 = (0..3).random()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentQueNgauNhienBinding.inflate(inflater, container, false)
        val root: View = binding.root
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setHasOptionsMenu(true)

        val rd = Random()
        val random1 = rd.nextInt(4)
        val random2 = rd.nextInt(4)
        val random3 = rd.nextInt(4)
        val random4 = rd.nextInt(4)
        val random5 = rd.nextInt(4)
        val random6 = rd.nextInt(4)
        Log.d("NgauNhien", "onViewCreated: $random1 $random2 $random3 $random4 $random5 $random6")
        val amin: Animation = AnimationUtils.loadAnimation(context, R.anim.shake)
        val amin_stop: Animation = AnimationUtils.loadAnimation(context, R.anim.shake_stop)
        binding.xu1.setImageResource(R.drawable.xuduong)
        binding.xu2.setImageResource(R.drawable.xuduong)
        binding.xu3.setImageResource(R.drawable.xuduong)
        binding.btnQnnRs.setOnClickListener {
            lq = 0
            binding.img1.setBackgroundResource(0)
            binding.img2.setBackgroundResource(0)
            binding.img3.setBackgroundResource(0)
            binding.img4.setBackgroundResource(0)
            binding.img5.setBackgroundResource(0)
            binding.img6.setBackgroundResource(0)
            binding.btnQnnKq.text = "HÀO SƠ"
            binding.xu1.startAnimation(amin_stop)
        }
        binding.btnQnnKq.setOnClickListener {
            var connectivity =
                context?.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
            lq += 1
            if (lq == 1) {
                binding.xu1.startAnimation(amin)
                binding.xu2.startAnimation(amin)
                binding.xu3.startAnimation(amin)
                binding.btnQnnKq.text = "DỪNG"
            }
            if (lq == 2) {
                binding.xu1.startAnimation(amin_stop)
                binding.xu2.startAnimation(amin_stop)
                binding.xu3.startAnimation(amin_stop)
                binding.btnQnnKq.text = "HÀO 2"
                when (random1) {
                    0 -> {
                        h1 = 0
                        cb1 = 0
                        binding.img1.setBackgroundResource(R.drawable.am)
                        binding.xu1.setImageResource(R.drawable.xuam)
                        binding.xu2.setImageResource(R.drawable.xuduong)
                        binding.xu3.setImageResource(R.drawable.xuduong)
                    }
                    1 -> {
                        h1 = 1
                        cb1 = 0
                        binding.img1.setBackgroundResource(R.drawable.duong)
                        binding.xu1.setImageResource(R.drawable.xuam)
                        binding.xu2.setImageResource(R.drawable.xuam)
                        binding.xu3.setImageResource(R.drawable.xuduong)
                    }
                    2 -> {
                        h1 = 0
                        cb1 = 1
                        binding.img1.setBackgroundResource(R.drawable.amdong)
                        binding.xu1.setImageResource(R.drawable.xuam)
                        binding.xu2.setImageResource(R.drawable.xuam)
                        binding.xu3.setImageResource(R.drawable.xuam)
                    }
                    3 -> {
                        h1 = 1
                        cb1 = 1
                        binding.img1.setBackgroundResource(R.drawable.duongdong)
                        binding.xu1.setImageResource(R.drawable.xuduong)
                        binding.xu2.setImageResource(R.drawable.xuduong)
                        binding.xu3.setImageResource(R.drawable.xuduong)
                    }
                }
            }
            if (lq == 3) {
                binding.xu1.startAnimation(amin)
                binding.xu2.startAnimation(amin)
                binding.xu3.startAnimation(amin)
                binding.btnQnnKq.text = "DỪNG"
            }
            if (lq == 4) {
                binding.xu1.startAnimation(amin_stop)
                binding.xu2.startAnimation(amin_stop)
                binding.xu3.startAnimation(amin_stop)
                binding.btnQnnKq.text = "HÀO 3"
                when (random2) {
                    0 -> {
                        h2 = 0
                        cb2 = 0
                        binding.img2.setBackgroundResource(R.drawable.am)
                        binding.xu1.setImageResource(R.drawable.xuam)
                        binding.xu2.setImageResource(R.drawable.xuduong)
                        binding.xu3.setImageResource(R.drawable.xuduong)
                    }
                    1 -> {
                        h2 = 1
                        cb2 = 0
                        binding.img2.setBackgroundResource(R.drawable.duong)
                        binding.xu1.setImageResource(R.drawable.xuam)
                        binding.xu2.setImageResource(R.drawable.xuam)
                        binding.xu3.setImageResource(R.drawable.xuduong)
                    }
                    2 -> {
                        h2 = 0
                        cb2 = 1
                        binding.img2.setBackgroundResource(R.drawable.amdong)
                        binding.xu1.setImageResource(R.drawable.xuam)
                        binding.xu2.setImageResource(R.drawable.xuam)
                        binding.xu3.setImageResource(R.drawable.xuam)
                    }
                    3 -> {
                        h2 = 1
                        cb2 = 1
                        binding.img2.setBackgroundResource(R.drawable.duongdong)
                        binding.xu1.setImageResource(R.drawable.xuduong)
                        binding.xu2.setImageResource(R.drawable.xuduong)
                        binding.xu3.setImageResource(R.drawable.xuduong)
                    }
                }
            }
            if (lq == 5) {
                binding.xu1.startAnimation(amin)
                binding.xu2.startAnimation(amin)
                binding.xu3.startAnimation(amin)
                binding.btnQnnKq.text = "DỪNG"
            }
            if (lq == 6) {
                binding.xu1.startAnimation(amin_stop)
                binding.xu2.startAnimation(amin_stop)
                binding.xu3.startAnimation(amin_stop)
                binding.btnQnnKq.text = "HÀO 3"
                when (random3) {
                    0 -> {
                        h3 = 0
                        cb3 = 0
                        binding.img3.setBackgroundResource(R.drawable.am)
                        binding.xu1.setImageResource(R.drawable.xuam)
                        binding.xu2.setImageResource(R.drawable.xuduong)
                        binding.xu3.setImageResource(R.drawable.xuduong)
                    }
                    1 -> {
                        h3 = 1
                        cb3 = 0
                        binding.img3.setBackgroundResource(R.drawable.duong)
                        binding.xu1.setImageResource(R.drawable.xuam)
                        binding.xu2.setImageResource(R.drawable.xuam)
                        binding.xu3.setImageResource(R.drawable.xuduong)
                    }
                    2 -> {
                        h3 = 0
                        cb3 = 1
                        binding.img3.setBackgroundResource(R.drawable.amdong)
                        binding.xu1.setImageResource(R.drawable.xuam)
                        binding.xu2.setImageResource(R.drawable.xuam)
                        binding.xu3.setImageResource(R.drawable.xuam)
                    }
                    3 -> {
                        h3 = 1
                        cb3 = 1
                        binding.img3.setBackgroundResource(R.drawable.duongdong)
                        binding.xu1.setImageResource(R.drawable.xuduong)
                        binding.xu2.setImageResource(R.drawable.xuduong)
                        binding.xu3.setImageResource(R.drawable.xuduong)
                    }
                }
            }
            if (lq == 7) {
                binding.xu1.startAnimation(amin)
                binding.xu2.startAnimation(amin)
                binding.xu3.startAnimation(amin)
                binding.btnQnnKq.text = "DỪNG"
            }
            if (lq == 8) {
                binding.xu1.startAnimation(amin_stop)
                binding.xu2.startAnimation(amin_stop)
                binding.xu3.startAnimation(amin_stop)
                binding.btnQnnKq.text = "HÀO 4"
                when (random4) {
                    0 -> {
                        h4 = 0
                        cb4 = 0
                        binding.img4.setBackgroundResource(R.drawable.am)
                        binding.xu1.setImageResource(R.drawable.xuam)
                        binding.xu2.setImageResource(R.drawable.xuduong)
                        binding.xu3.setImageResource(R.drawable.xuduong)
                    }
                    1 -> {
                        h4 = 1
                        cb4 = 0
                        binding.img4.setBackgroundResource(R.drawable.duong)
                        binding.xu1.setImageResource(R.drawable.xuam)
                        binding.xu2.setImageResource(R.drawable.xuam)
                        binding.xu3.setImageResource(R.drawable.xuduong)
                    }
                    2 -> {
                        h4 = 0
                        cb4 = 1
                        binding.img4.setBackgroundResource(R.drawable.amdong)
                        binding.xu1.setImageResource(R.drawable.xuam)
                        binding.xu2.setImageResource(R.drawable.xuam)
                        binding.xu3.setImageResource(R.drawable.xuam)
                    }
                    3 -> {
                        h4 = 1
                        cb4 = 1
                        binding.img4.setBackgroundResource(R.drawable.duongdong)
                        binding.xu1.setImageResource(R.drawable.xuduong)
                        binding.xu2.setImageResource(R.drawable.xuduong)
                        binding.xu3.setImageResource(R.drawable.xuduong)
                    }
                }
            }
            if (lq == 9) {
                binding.xu1.startAnimation(amin)
                binding.xu2.startAnimation(amin)
                binding.xu3.startAnimation(amin)
                binding.btnQnnKq.text = "DỪNG"
            }
            if (lq == 10) {
                binding.xu1.startAnimation(amin_stop)
                binding.xu2.startAnimation(amin_stop)
                binding.xu3.startAnimation(amin_stop)
                binding.btnQnnKq.text = "HÀO 5"
                when (random5) {
                    0 -> {
                        h5 = 0
                        cb5 = 0
                        binding.img5.setBackgroundResource(R.drawable.am)
                        binding.xu1.setImageResource(R.drawable.xuam)
                        binding.xu2.setImageResource(R.drawable.xuduong)
                        binding.xu3.setImageResource(R.drawable.xuduong)
                    }
                    1 -> {
                        h5 = 1
                        cb5 = 0
                        binding.img5.setBackgroundResource(R.drawable.duong)
                        binding.xu1.setImageResource(R.drawable.xuam)
                        binding.xu2.setImageResource(R.drawable.xuam)
                        binding.xu3.setImageResource(R.drawable.xuduong)
                    }
                    2 -> {
                        h5 = 0
                        cb5 = 1
                        binding.img5.setBackgroundResource(R.drawable.amdong)
                        binding.xu1.setImageResource(R.drawable.xuam)
                        binding.xu2.setImageResource(R.drawable.xuam)
                        binding.xu3.setImageResource(R.drawable.xuam)
                    }
                    3 -> {
                        h5 = 1
                        cb5 = 1
                        binding.img5.setBackgroundResource(R.drawable.duongdong)
                        binding.xu1.setImageResource(R.drawable.xuduong)
                        binding.xu2.setImageResource(R.drawable.xuduong)
                        binding.xu3.setImageResource(R.drawable.xuduong)
                    }
                }
            }
            if (lq == 11) {
                binding.xu1.startAnimation(amin)
                binding.xu2.startAnimation(amin)
                binding.xu3.startAnimation(amin)
                binding.btnQnnKq.text = "DỪNG"
            }
            if (lq == 12) {
                binding.xu1.startAnimation(amin_stop)
                binding.xu2.startAnimation(amin_stop)
                binding.xu3.startAnimation(amin_stop)
                binding.btnQnnKq.text = "Lập Quẻ"
                when (random6) {
                    0 -> {
                        h6 = 0
                        cb6 = 0
                        binding.img6.setBackgroundResource(R.drawable.am)
                        binding.xu1.setImageResource(R.drawable.xuam)
                        binding.xu2.setImageResource(R.drawable.xuduong)
                        binding.xu3.setImageResource(R.drawable.xuduong)
                    }
                    1 -> {
                        h6 = 1
                        cb6 = 0
                        binding.img6.setBackgroundResource(R.drawable.duong)
                        binding.xu1.setImageResource(R.drawable.xuam)
                        binding.xu2.setImageResource(R.drawable.xuam)
                        binding.xu3.setImageResource(R.drawable.xuduong)
                    }
                    2 -> {
                        h6 = 0
                        cb6 = 1
                        binding.img6.setBackgroundResource(R.drawable.amdong)
                        binding.xu1.setImageResource(R.drawable.xuam)
                        binding.xu2.setImageResource(R.drawable.xuam)
                        binding.xu3.setImageResource(R.drawable.xuam)
                    }
                    3 -> {
                        h6 = 1
                        cb6 = 1
                        binding.img6.setBackgroundResource(R.drawable.duongdong)
                        binding.xu1.setImageResource(R.drawable.xuduong)
                        binding.xu2.setImageResource(R.drawable.xuduong)
                        binding.xu3.setImageResource(R.drawable.xuduong)
                    }
                }
            }
            if (lq == 13) {
                var info: NetworkInfo? = null
                if (connectivity != null) {
                    info = connectivity.activeNetworkInfo
                    if (info != null) {
                        if (info.state == NetworkInfo.State.CONNECTED) {
                            val cal = Calendar.getInstance()
                            nam = cal.get(Calendar.YEAR)
                            thang = (cal.get(Calendar.MONTH) + 1)
                            ngay = cal.get(Calendar.DATE)
                            gio = cal.get(Calendar.HOUR_OF_DAY)
                            phut = cal.get(Calendar.MINUTE)
                            val ntn = NTN(
                                ngay.toLong(),
                                thang.toLong(),
                                nam.toLong(),
                                gio,
                                phut
                            )
                            val ctNgauNhien =
                                CTNgauNhien(cb1, cb2, cb3, cb4, cb5, cb6, h1, h2, h3, h4, h5, h6)
                            val bundle = Bundle().apply {
                                putString("Key.NTN", ntn._ngaythang())
                                putString("Key.TIETKHI", ntn.tietkhi())
                                putString("Key.CH", binding.edtCauhoi.text.toString())
                                putString("Key.TK", ntn.TuanKhong())
                                putParcelable(
                                    "Key.HaoDong",
                                    ctNgauNhien.TinhHaoDong()
                                )
                                putInt("Key.Luu", 1)
                                putParcelable("Key.QueChinh", ctNgauNhien._ChuoiQueNgauNhien())
                                putParcelable("Key.QueBien", ctNgauNhien._ChuoiQueBienNgauNhien())
                                putParcelable("Key.QueHo", ctNgauNhien._XuLyQueHoNgauNhien())
                                putParcelable("Key.CCNT", ntn.canChiNgayThang)
                                putParcelable("Key.TT",
                                    NgayGioThangNamDL(ngay, gio, thang, nam, phut))
                                putString("Key.SoTen", "Ngẫu Nhiên")
                            }
                            lq = 0
                            Navigation.findNavController(it).navigate(R.id.dichResult, bundle)

                        }
                    }else Toast.makeText(context,"Chưa kết nối mạng", Toast.LENGTH_LONG).show()
                }
            }
        }
        
        return root
    }


    fun main(args: Array<String>) {
        val rd = Random() // khai báo 1 đối tượng Random
        val number = rd.nextInt() // trả về 1 số nguyên bất kỳ
        println("Số vừa được sinh ra là $number")
        val number1 = rd.nextInt(4) // trả về 1 số nguyên nằm trong phạm vi [0...3)
        println("Số vừa được sinh ra là $number1")

        // trả về 1 số nguyên nằm trong phạm vi [-4...-1]
        // đối với rd.nextInt(4) thì số lớn nhất là 3 và số nhỏ nhất là 0
        // ta có 3 - 4 = -1 và 0 - 4 = -4
        // nên các số được sinh ra sẽ nằm trong đoạn [-4...-1]
        val number2 = -4 + rd.nextInt(4)
        println("Số vừa được sinh ra là $number2")
    }

}
