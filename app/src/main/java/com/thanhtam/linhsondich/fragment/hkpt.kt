package com.thanhtam.linhsondich.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.thanhtam.linhsondich.databinding.FragmentHkptBinding

class hkpt : Fragment() {

    private var _binding: FragmentHkptBinding? = null
    private val binding get() = _binding!!

    // Tên file để lưu SharedPreferences
    private val PREFS_NAME = "HkptPrefs"
    private val KEY_VAN = "selectedVan"
    private val KEY_DEGREE = "savedDegree"

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHkptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Khởi tạo SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // 2. Thiết lập các thành phần UI
        setupVanSpinner()
        setupDegreeInput()

        // 3. Đọc dữ liệu đã lưu và hiển thị lên UI, đồng thời cập nhật la bàn lần đầu
        loadSavedData()
    }

    private fun setupVanSpinner() {
        val vanData = (1..9).map { "Vận $it" }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, vanData)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.vanSpinner.adapter = adapter

        // Lắng nghe sự kiện khi người dùng chọn một Vận mới
        binding.vanSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Lấy vận được chọn (position + 1 vì Vận bắt đầu từ 1)
                val selectedVan = position + 1
                saveInt(KEY_VAN, selectedVan) // Lưu Vận đã chọn
                updateLaBan() // Gọi hàm cập nhật la bàn
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupDegreeInput() {
        binding.degreeInput.addTextChangedListener { text ->
            saveString(KEY_DEGREE, text.toString()) // Lưu Độ số đã nhập
            updateLaBan() // Gọi hàm cập nhật la bàn
        }
    }

    private fun loadSavedData() {
        // Đọc Vận đã lưu, mặc định là 9 nếu chưa có gì
        val savedVan = sharedPreferences.getInt(KEY_VAN, 9)
        binding.vanSpinner.setSelection(savedVan - 1)

        // Đọc Độ số đã lưu, mặc định là chuỗi rỗng
        val savedDegree = sharedPreferences.getString(KEY_DEGREE, "")
        if (savedDegree != null && savedDegree.isNotEmpty()) {
            binding.degreeInput.setText(savedDegree)
        }

        // Gọi cập nhật la bàn với dữ liệu vừa đọc được
        updateLaBan()
    }

    // === ĐÂY LÀ HÀM BỊ THIẾU MÀ BẠN ĐÃ PHÁT HIỆN ===
    private fun updateLaBan() {
        if (_binding == null) return // Tránh crash nếu fragment đã bị hủy

        // Lấy giá trị Vận và Độ từ UI
        val selectedVan = binding.vanSpinner.selectedItemPosition + 1
        val degreeText = binding.degreeInput.text.toString()

        // 1. Cập nhật Vận cho la bàn (luôn luôn gọi)
        binding.laBanProView.updateVan(selectedVan)

        // 2. Cập nhật Độ số (nếu có) và tính toán lại phi tinh
        if (degreeText.isNotEmpty()) {
            try {
                val degree = degreeText.toFloat()
                // Gọi hàm cập nhật độ khi có giá trị hợp lệ
                binding.laBanProView.updateDirection(degree)

            } catch (e: NumberFormatException) {
                // Xử lý nếu người dùng nhập không phải là số
                if (binding.degreeInput.isFocused) {
                    binding.degreeInput.error = "Vui lòng nhập số"
                }
            }
        } else {
            // Nếu không có độ, ta vẫn cần tính phi tinh dựa trên Vận và độ mặc định (0 độ)
            // Điều này đảm bảo khi người dùng xóa hết số, la bàn vẫn cập nhật theo Vận mới
            binding.laBanProView.updateDirection(0f)
        }
    }

    // Hàm tiện ích để lưu giá trị Int
    private fun saveInt(key: String, value: Int) {
        with(sharedPreferences.edit()) {
            putInt(key, value)
            apply()
        }
    }

    // Hàm tiện ích để lưu giá trị String
    private fun saveString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
