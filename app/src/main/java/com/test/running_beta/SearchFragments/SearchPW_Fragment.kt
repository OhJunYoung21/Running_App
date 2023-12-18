package com.test.running_beta.SearchFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.running_beta.UI.ConfirmDialog
import com.test.running_beta.databinding.FragmentSearchPWBinding
import com.test.running_beta.roomDB.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class SearchPW_Fragment : Fragment() {


    lateinit var binding: FragmentSearchPWBinding

    lateinit var id: String

    lateinit var name: String

    lateinit var number: String

    private lateinit var password: String

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = "비밀번호 찾기"

        val content_1 = "비밀번호"

        binding = FragmentSearchPWBinding.inflate(layoutInflater)

        binding.searchBtn.setOnClickListener {

            id = binding.ID.text.toString().trim()

            name = binding.Name.text.toString().trim()

            number = binding.Number.text.toString().trim()

            CoroutineScope(Dispatchers.Main).launch {

                password = findAsyncPw(id, name, number)

                val dialog = ConfirmDialog(requireContext(), title, content_1, password, 1)

                dialog.isCancelable = false

                dialog.show(requireFragmentManager(), "findPwProcess")


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


    private fun findPw(id: String, name: String, number: String): String {

        db = AppDatabase.getInstance(requireContext())

        val password_1 =db.getUserDAO().getPasswordById(id, name, number)

        return password_1

    }

    private suspend fun findAsyncPw(id: String, name: String, number: String): String {
        return CoroutineScope(Dispatchers.IO).async {
            findPw(id, name, number)
        }.await()

    }
}