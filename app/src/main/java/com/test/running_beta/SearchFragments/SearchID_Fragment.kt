package com.test.running_beta.SearchFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
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
    private lateinit var id: String
    private val title: String = "아이디 찾기"
    private val content_1: String = "아이디"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentSearchIDBinding.inflate(layoutInflater)

        binding.searchBtn.setOnClickListener {

            name = binding.Name.text.toString().trim()

            number = binding.Number.text.toString().trim()
            //TODO:ID 검색 완료시 프래그먼트 전역변수에 값 할당(지연초기화 사용)

            CoroutineScope(Dispatchers.IO).launch {
                id = if (findIdAsync(name, number) != null) {
                    findIdAsync(name, number).toString()
                } else {
                    ""
                }
                val dialogFragment = ConfirmDialog(title, content_1, id, 0)
                dialogFragment.isCancelable = false
                dialogFragment.show(requireFragmentManager(), "findIdProcess")
                cancel()
                Log.d("finalId", id)
                setFragmentResult("requestKey", bundleOf("bundleKey" to id))
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

    private suspend fun findIdAsync(name: String, number: String): String? {
        return CoroutineScope(Dispatchers.IO).async {
            findId(name, number)
        }.await()
    }
}

