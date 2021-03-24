package com.example.talentsofttestwallyd.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.talentsofttestwallyd.R
import com.example.talentsofttestwallyd.databinding.FragmentNewsDetailBinding
import com.example.talentsofttestwallyd.model.Articles
import com.example.talentsofttestwallyd.viewModel.NewsViewModel
import com.example.talentsofttestwallyd.utils.DateUtils
import com.example.talentsofttestwallyd.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.IOException

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
const val ARG_ARTICLES = "articles"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsDetailFragment : Fragment() {
    private var articles: Articles? = null
    private lateinit var binding: FragmentNewsDetailBinding
    private val newsViewModel: NewsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            articles = bundle.getParcelable(ARG_ARTICLES)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObserver()
        bindData()
    }

    private fun bindData() {
        binding.textViewContent.text = articles?.content
        binding.textviewPublishedAt.text = articles?.publishedAt?.let { DateUtils().parseDate(it) }
        when {
            articles?.author.toString().contentEquals("null") -> {
                binding.textViewAuthor.visibility = View.GONE
            }
            else -> {
                binding.textViewAuthor.visibility = View.VISIBLE
                val builder = StringBuilder()
                builder.append(binding.root.resources.getString(R.string.author))
                builder.append(articles?.author)
                binding.textViewAuthor.text = builder.toString()
            }
        }

        when {
            articles?.source?.name.toString().contentEquals("null") -> {
                binding.textViewSource.visibility = View.GONE
            }
            else -> {
                binding.textViewSource.visibility = View.VISIBLE
                val builder = StringBuilder()
                builder.append(binding.root.resources.getString(R.string.source))
                builder.append(articles?.source?.name)
                binding.textViewSource.text = builder.toString()
            }
        }
    }

    private fun setUpObserver() {
        newsViewModel.imageNews.observe(viewLifecycleOwner, { responseBody ->
            when (responseBody) {
                is Resource.Success -> {
                    downloadImage(responseBody.data)
                }
                is Resource.Error -> {
                    Log.e(
                        NewsDetailFragment.javaClass.simpleName,
                        responseBody.exception.localizedMessage
                    )
                }
            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param articles Parameter .
         * @return A new instance of fragment NewsDetailFragment.
         */
        @JvmStatic
        fun newInstance(articles: Articles) =
            NewsDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_ARTICLES, articles)
                }
            }
    }

    private fun downloadImage(responseBody: ResponseBody): Bitmap? {
        val inputStream = responseBody.byteStream()
        var bitmap: Bitmap? = null
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val bitmapFactoryOptions = BitmapFactory.Options()
            bitmapFactoryOptions.inSampleSize = 1
            try {
                bitmap = BitmapFactory.decodeStream(inputStream, null, bitmapFactoryOptions)
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            withContext(Dispatchers.Main) {
                binding.newsImage.setImageBitmap(bitmap)
            }
        }
        return bitmap
    }
}