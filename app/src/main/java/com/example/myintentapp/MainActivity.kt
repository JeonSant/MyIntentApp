package com.example.myintentapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var tvResult: TextView

    // agar activity dapat mengembalikan nilai, buat ActivityResultLauncher
    // daftarkan jenis kembalian dengan registerForActivityResult, ActivityResultContract dipakai karena kita mendapatkan nilai kembalian setelah memanggil activity baru
    private val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // lakukan perbandingan apakah nilai resultCode sama yang dikirim oleh MoveForResultActivity
        //  juga diperiksa, apakah data yang dikembalikan bernilai null atau tidak
        if (result.resultCode == MoveForResultActivity.RESULT_CODE && result.data != null) {
            // Bila semua kondisi terpenuhi, data RadioButton yang dipilih akan ditampilkan di TextView tvResult
            val selectedValue = result.data?.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE, 0)
            tvResult.text = "Hasil : $selectedValue"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // memperkenalkan object view pada layout ke activity
        val btnMoveActivity: Button = findViewById(R.id.btn_move_activity)
        val btnMoveWithDataActivity: Button = findViewById(R.id.btn_move_activity_data)
        val btnMoveWithObjectActivity: Button = findViewById(R.id.btn_move_activity_object)
        val btnDialPhone: Button = findViewById(R.id.btn_dial_number)
        val btnMoveWithResult: Button = findViewById(R.id.btn_move_for_result)
        tvResult = findViewById(R.id.tv_result)

        // menambahkan event on click pada button btnMoveActivity
        btnMoveActivity.setOnClickListener(this)
        btnMoveWithDataActivity.setOnClickListener(this)
        btnMoveWithObjectActivity.setOnClickListener(this)
        btnDialPhone.setOnClickListener(this)
        btnMoveWithResult.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            // intent without send data
            R.id.btn_move_activity -> {
                val moveIntent = Intent(this@MainActivity, MoveActivity::class.java)
                startActivity(moveIntent)
            }

            // send data with intent
            R.id.btn_move_activity_data -> {
                val moveWithDataIntent = Intent(this@MainActivity, MoveWithDataActivity::class.java)
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_NAME, "DicodingAcademy Boy")
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_AGE, 5)
                startActivity(moveWithDataIntent)
            }

            // send data with intent with parcelable
            R.id.btn_move_activity_object -> {
                val person = Person(
                    "DicodingAcademy",
                    5,
                    "academy@dicoding.com",
                    "Bandung"
                )

                val moveWithObjectIntent =
                    Intent(this@MainActivity, MoveWithObjectActivity::class.java)
                moveWithObjectIntent.putExtra(MoveWithObjectActivity.EXTRA_PERSON, person)
                startActivity(moveWithObjectIntent)
            }

            // implicit intent
            R.id.btn_dial_number -> {
                val phoneNumber = "081210841382"
                val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                startActivity(dialPhoneIntent)
            }

            // berpindah dari satu activity ke activity lainnya menggunakan intent,
            // lalu kembali lagi dengan nilai kembalian
            R.id.btn_move_for_result -> {
                val moveForResultIntent =
                    Intent(this@MainActivity, MoveForResultActivity::class.java)
                // dapat dilihat disini agar intent mengembalikkan nilai maka menjalankan intentnya pakai launch
                resultLauncher.launch(moveForResultIntent)
            }
        }
    }
}