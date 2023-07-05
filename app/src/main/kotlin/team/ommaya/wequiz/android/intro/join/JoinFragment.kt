/*
 * Designed and developed by "옴마야" Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/mash-up-kr/WeQuiz-Android/blob/main/LICENSE
 */

package team.ommaya.wequiz.android.intro.join

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import team.ommaya.wequiz.android.R
import team.ommaya.wequiz.android.base.BaseViewBindingFragment
import team.ommaya.wequiz.android.databinding.FragmentJoinBinding

class JoinFragment : BaseViewBindingFragment<FragmentJoinBinding>(FragmentJoinBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.apply {
            btnJoinNext.setOnClickListener {
                findNavController().navigate(R.id.action_joinFragment_to_welcomeFragment)
            }

            btnJoinBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}
