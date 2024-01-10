package com.test.running_beta.UI

import android.Manifest
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.test.running_beta.RunActivity
import com.test.running_beta.databinding.CustomDialogBinding
import com.test.running_beta.databinding.PermissionDialogBinding

/**interface ConfirmDialogInterface {

/**인터페이스 내부에는 함수를 선언해줍니다**/

}**/


//ConfiemDialog는 내가 넘겨주는 id,password를 받으면 되고, 해당 변수는 content에 넣어주고 ${content}형태로 사용할 것이다.
class ConfirmDialog(
    title: String, content1: String, content2: String, id: Int
) : DialogFragment() {

    private lateinit var binding: CustomDialogBinding

    private var title: String
    private var content1: String
    private var content2: String
    private var id: Int? = null

    init {
        this.title = title
        this.content1 = content1
        this.content2 = content2
        this.id = id
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = CustomDialogBinding.inflate(layoutInflater)

        val view = binding.root

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogTitleTv.text = title

        /** null값이 들어오는 부분을 처리한다**/

        if (content1 == "아이디" && content2 == "") {
            binding.dialogDescTv.text = "매칭되는 아이디가 없습니다. 다시 입력하세요"
            binding.confirmBtn.text = "확인"
            binding.confirmBtn.setOnClickListener {
                dismiss()
            }

        } else if (content1 == "비밀번호" && content2 == "") {
            binding.dialogDescTv.text = "매칭되는 비밀번호가 없습니다. 다시 입력하세요"
            binding.confirmBtn.text = "확인"
            binding.confirmBtn.setOnClickListener {
                dismiss()
            }
        } else {
            binding.dialogDescTv.text = "회원님의 ${content1}는 ${content2}입니다."
            //취소 버튼 클릭시 이벤트
            binding.cancelBtn.setOnClickListener {
                dismiss()
            }
            //로그인 화면 버튼 클릭시 이벤트
            binding.confirmBtn.setOnClickListener {
                dismiss()
            }
        }
        return view
    }


}


class PermissionDialog(context: Context, id: Int) : DialogFragment() {
    private lateinit var binding: PermissionDialogBinding
    private var check = 0
    private var id: Int? = null

    init {
        this.id = id
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = PermissionDialogBinding.inflate(layoutInflater)

        val view = binding.root
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.checkBtn.setOnClickListener {

            ActivityCompat.requestPermissions(
                requireContext() as Activity,
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                RunActivity.requestCode
            )
        }

        binding.cancelBtn.setOnClickListener {
            requireActivity().finish()
        }


        return view
    }


    fun getCheck(): Int {

        return check

    }


}