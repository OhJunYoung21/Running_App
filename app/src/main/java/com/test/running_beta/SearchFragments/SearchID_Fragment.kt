package com.test.running_beta.SearchFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.running_beta.ApplicationClass.MyApplication
import com.test.running_beta.UI.ConfirmDialog
import com.test.running_beta.databinding.FragmentSearchIDBinding
import com.test.running_beta.roomDB.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class SearchID_Fragment : Fragment() {

    lateinit var binding: FragmentSearchIDBinding

    lateinit var name: String

    lateinit var number: String

    lateinit var application: MyApplication

    lateinit var db: AppDatabase

    lateinit var id: String

    private val title: String = "아이디 찾기"

    private val content_1: String = "아이디"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentSearchIDBinding.inflate(layoutInflater)

        binding.searchBtn.setOnClickListener {

            name = binding.Name.text.toString().trim()

            number = binding.Number.text.toString().trim()

            CoroutineScope(Dispatchers.Main).launch {

                id = findIdAsync(name, number)

                val dialogFragment = ConfirmDialog(requireContext(), title, content_1, id, 0)

                dialogFragment.isCancelable = false

                dialogFragment.show(requireFragmentManager(), "findIdProcess")

                cancel()

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

    private fun findId(name: String, number: String): String {

        db = AppDatabase.getInstance(requireContext())

        val id = db.getUserDAO().getIdByName(name, number)

        return id
    }

    private suspend fun findIdAsync(name: String, number: String): String {
        return CoroutineScope(Dispatchers.IO).async {
            findId(name, number)
        }.await()
    }

}