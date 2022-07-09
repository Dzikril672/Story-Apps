package com.dzikril.submission1.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dzikril.submission1.R
import com.dzikril.submission1.data.local.entity.Story
import com.dzikril.submission1.databinding.ActivityDetailBinding
import com.dzikril.submission1.utils.setLocalDateFormat

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.tittle_detail_activity)

        val story = intent.getParcelableExtra<Story>(EXTRA_STORY)
        binding.apply {
            tvDetailUsername.text = story?.name
            tvCreatedAt.setLocalDateFormat(story?.createdAt.toString())
            tvDetailDescription.text = story?.description
        }
        Glide.with(this)
            .load(story?.photoUrl)
            .placeholder(R.drawable.image_loading)
            .error(R.drawable.image_error)
            .into(binding.imgAvatar)
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}