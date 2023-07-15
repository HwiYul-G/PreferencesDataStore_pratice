package com.example.preferencesdatastore_pratice.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.preferencesdatastore_pratice.databinding.ActivityMainBinding
import com.example.preferencesdatastore_pratice.models.User
import com.example.preferencesdatastore_pratice.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        binding.btnSave.setOnClickListener {
            val age = binding.etAge.text.toString()
            val name = binding.etName.text.toString()
            btnSave(age, name)
        }

        viewModel.getUser.observe(this) {
            binding.tvAge.text = it.age.toString()
            binding.tvName.text = it.name
        }

    }

    private fun btnSave(age : String, name: String){
        val user = User(age.toInt(), name)
        viewModel.saveUser(user)
    }


}