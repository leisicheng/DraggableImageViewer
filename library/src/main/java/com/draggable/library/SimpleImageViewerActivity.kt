package com.draggable.library

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.draggable.library.extension.view.DraggableImageView
import com.draggable.library.extension.entities.DraggableImageInfo

//查看单张图片
class SimpleImageViewerActivity : AppCompatActivity() {

    companion object {
        private const val PARAMS = "params"
        private const val IMAGE_URL = "image_url"
        fun start(context: Context, draggableInfo: DraggableImageInfo) {
            val intent = Intent(context, SimpleImageViewerActivity::class.java)
            intent.putExtra(PARAMS, draggableInfo)
            context.startActivity(intent)
            if (context is Activity) {
                context.overridePendingTransition(0, 0)
            }
        }
    }

    private val draggableImageView by lazy {
        DraggableImageView(this).apply {
            layoutParams =
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            actionListenr = object : DraggableImageView.ActionListener {
                override fun onExit() {
                    finish()
                    overridePendingTransition(0, 0)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.transparentStatusBar(this)
        setContentView(draggableImageView)
        val info = intent.getSerializableExtra(PARAMS) as? DraggableImageInfo
        if (info != null) {
            draggableImageView.showImageWithAnimator(info)
        }
    }

    override fun onBackPressed() {
        draggableImageView.close()
    }

}