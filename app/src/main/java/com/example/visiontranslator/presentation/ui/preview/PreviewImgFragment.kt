package com.example.visiontranslator.presentation.ui.preview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.visiontranslator.AppApplication
import com.example.visiontranslator.databinding.FragmentPreviewImgBinding
import javax.inject.Inject

/**
 * プレビューの画像を表示するフラグメント
 */
class PreviewImgFragment : Fragment() {

    companion object {
        fun newInstance(): PreviewImgFragment =
            PreviewImgFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PreviewViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(PreviewViewModel::class.java)
    }

    private lateinit var binding: FragmentPreviewImgBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AppApplication.component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentPreviewImgBinding.inflate(inflater, container, false)
        .also {
            binding = it
            binding.let { binding ->
                binding.lifecycleOwner = viewLifecycleOwner
                binding.viewModel = viewModel
            }
        }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}
