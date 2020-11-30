package com.example.visiontranslator.presentation.ui.preview

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.visiontranslator.AppApplication
import com.example.visiontranslator.databinding.FragmentPreviewImgBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
                binding.lifecycleOwner = this
                binding.viewModel = viewModel
                lifecycle.addObserver(viewModel)
            }
        }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}