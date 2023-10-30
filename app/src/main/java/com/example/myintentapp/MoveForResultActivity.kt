package com.example.myintentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioGroup

class MoveForResultActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnChoose: Button
    private lateinit var rgNumber: RadioGroup

    companion object {
        const val EXTRA_SELECTED_VALUE = "extra_selected_value"
        const val RESULT_CODE = 110
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_for_result)

        // memperkenalkan object view pada activity
        btnChoose = findViewById(R.id.btn_choose)
        rgNumber = findViewById(R.id.rg_number)

        // menambahkan event on click pada button
        btnChoose.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        // jika yang di klik adalah button choose
        if (v?.id == R.id.btn_choose) {
            // check apakah ada object RadioButton yang dipilih
            if (rgNumber.checkedRadioButtonId > 0) {
                // nilai sebelum memilih adalah 0
                var value = 0
                // check radio yang dipilih yang mana
                when (rgNumber.checkedRadioButtonId) {
                    // kalau yang 50, nilainya jadi 50
                    R.id.rb_50 -> value = 50
                    // kalau yang 100, nilainya jadi 100
                    R.id.rb_100 -> value = 100
                    // kalau yang 150, nilainya jadi 150
                    R.id.rb_150 -> value = 150
                    // kalau yang 200, nilainya jadi 200
                    R.id.rb_200 -> value = 200
                }
                // buat intent
                val resultIntent = Intent()
                // meletakkan variabel value ke dalam metode putExtra(Key, Value)
                resultIntent.putExtra(EXTRA_SELECTED_VALUE, value)
                //  jadikan obyek resultIntent yang telah dibuat sebelumnya menjadi parameter dari setResult(RESULT_CODE, Intent)
                setResult(RESULT_CODE, resultIntent)
                // panggil method finish() untuk menutup MoveForResultActivity dan kembali ke Activity sebelumnya.
                // Ketika MoveForResultActivity telah tertutup sempurna, callback ActivityResultLauncher pada MainActivity akan dijalankan.
                finish()
            }
        }
    }
}