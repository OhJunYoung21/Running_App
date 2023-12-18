package com.test.running_beta.UI

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.test.running_beta.LoginActivity
import com.test.running_beta.databinding.CustomDialogBinding

/**interface ConfirmDialogInterface {

/**인터페이스 내부에는 함수를 선언해줍니다**/

}**/


//ConfiemDialog는 내가 넘겨주는 id,password를 받으면 되고, 해당 변수는 content에 넣어주고 ${content}형태로 사용할 것이다.
class ConfirmDialog(
    context: Context, title: String, content_1: String, content_2: String, id: Int
) : DialogFragment() {

    private lateinit var binding: CustomDialogBinding

    private lateinit var title: String

    private lateinit var content_1: String

    private lateinit var content_2: String

    private var id: Int? = null

    init {
        this.title = title
        this.content_1 = content_1
        this.content_2 = content_2
        this.id = id
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {


        binding = CustomDialogBinding.inflate(layoutInflater)

        val view = binding.root

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogTitleTv.text = title

        /** null값이 들어오는 부분을 처리한다**/

        if (content_1 == null && content_2 == null) {

            binding.dialogDescTv.visibility = View.GONE

        } else {

            binding.dialogDescTv.text = "회원님의 ${content_1}는 ${content_2}입니다."

        }

        //취소 버튼 클릭시 이벤트

        binding.cancelBtn.setOnClickListener {

            dismiss()

        }

        //로그인 화면 버튼 클릭시 이벤트

        binding.toLoginBtn.setOnClickListener {

            dismiss()

            (context as? Activity)?.finish()

            val intent = Intent(context, LoginActivity::class.java)

            startActivity(intent)

        }
        return view
    }


}