package com.thanhtam.linhsondich.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.thanhtam.linhsondich.R
import com.thanhtam.linhsondich.databinding.FragmentHkptBinding

class hkpt : Fragment() {

    private var _binding: FragmentHkptBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHkptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Thiết lập Spinner
        setupVanSpinner()

        // Tự động cập nhật la bàn khi người dùng nhập độ số
        binding.degreeInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val degreesStr = s.toString()
                if (degreesStr.isNotEmpty()) {
                    try {
                        val degrees = degreesStr.toFloat()
                        // SỬA LỖI: Bỏ comment để kích hoạt lại la bàn
                        binding.laBanProView.setDirection(degrees)
                    } catch (e: NumberFormatException) {
                        // Bỏ qua lỗi định dạng số
                    }
                }
            }
        })
    }

    private fun setupVanSpinner() {
        // Tạo danh sách dữ liệu cho Spinner
        val vanList = (1..9).map { "Vận $it" }

        // Tạo ArrayAdapter để hiển thị dữ liệu
        val adapter = ArrayAdapter(
            requireContext(),
            // Sử dụng layout có sẵn của Android cho chữ màu trắng
            android.R.layout.simple_spinner_item,
            vanList
        ).apply {
            // Sử dụng layout có sẵn cho danh sách xổ xuống
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        // Gắn adapter vào Spinner
        binding.vanSpinner.adapter = adapter

        // Thiết lập giá trị mặc định là "Vận 8"
        val defaultSelection = "Vận 8"
        val defaultPosition = adapter.getPosition(defaultSelection)
        if (defaultPosition >= 0) {
            binding.vanSpinner.setSelection(defaultPosition)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
