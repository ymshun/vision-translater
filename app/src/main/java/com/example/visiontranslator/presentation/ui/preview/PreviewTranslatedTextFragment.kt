package com.example.visiontranslator.presentation.ui.preview

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.visiontranslator.AppApplication
import com.example.visiontranslator.databinding.FragmentPreviewTranslatedTextBinding
import javax.inject.Inject

class PreviewTranslatedTextFragment : Fragment() {

    companion object {
        fun newInstance(): PreviewTranslatedTextFragment =
            PreviewTranslatedTextFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PreviewViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(PreviewViewModel::class.java)
    }

    private lateinit var binding: FragmentPreviewTranslatedTextBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AppApplication.component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentPreviewTranslatedTextBinding.inflate(inflater, container, false)
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
        setupEditText()
    }

    /**
     * 翻訳テキストを表示するEditTextの設定
     */
    private fun setupEditText() {
        viewModel.editModeTranslatedText.observe(viewLifecycleOwner) {
            // キーボードの表示
            if (!it) return@observe
            binding.previewTranslateText.isFocusableInTouchMode = it
            binding.previewTranslateText.requestFocus()
            val inputMng = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMng.showSoftInput(binding.previewTranslateText, InputMethodManager.SHOW_FORCED)
        }
    }
}
