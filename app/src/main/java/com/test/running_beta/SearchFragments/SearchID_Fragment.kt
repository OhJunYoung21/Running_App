package com.test.running_beta.SearchFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.test.running_beta.AppDatabase
import com.test.running_beta.ApplicationClass.MyApplication
import com.test.running_beta.databinding.FragmentSearchIDBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchID_Fragment : Fragment() {

    lateinit var binding: FragmentSearchIDBinding

    lateinit var name: String

    lateinit var number: String

    lateinit var application: MyApplication

    lateinit var db:AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentSearchIDBinding.inflate(layoutInflater)

        binding.searchBtn.setOnClickListener {

            name = binding.Name.text.toString().trim()

            number = binding.Number.text.toString().trim()

            CoroutineScope(Dispatchers.IO).launch {

                val id = findId(name, number)

                withContext(Dispatchers.Main){

                    Toast.makeText(requireContext(), "아이디는 ${id}입니다.", Toast.LENGTH_SHORT).show()

                }

            }


        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return binding.root
    }

    suspend fun findId(name: String, number: String): String {

        db = AppDatabase.getInstance(requireContext())

        val id = db.getUserDAO().getIdByName(name,number)

        return id

    }

}