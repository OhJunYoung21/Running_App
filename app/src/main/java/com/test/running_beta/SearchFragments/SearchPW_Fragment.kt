package com.test.running_beta.SearchFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.test.running_beta.ApplicationClass.MyApplication
import com.test.running_beta.databinding.FragmentSearchPWBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchPW_Fragment : Fragment() {


    lateinit var binding: FragmentSearchPWBinding

    lateinit var id: String

    lateinit var name: String

    lateinit var number: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentSearchPWBinding.inflate(layoutInflater)

        binding.searchBtn.setOnClickListener {

            id = binding.ID.text.toString().trim()

            name = binding.Name.text.toString().trim()

            number = binding.Number.toString().trim()

            CoroutineScope(Dispatchers.IO).launch {

                val password = findPw(id, name, number)

                if (password != null) {

                    withContext(Dispatchers.Main) {

                        Toast.makeText(
                            requireContext(),
                            "비밀번호는 ${password}입니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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


    suspend fun findPw(id: String, name: String, number: String): String {

        val application = MyApplication()

        val password = application.db.getUserDAO().getPasswordById(id, name, number)

        return password

    }
}