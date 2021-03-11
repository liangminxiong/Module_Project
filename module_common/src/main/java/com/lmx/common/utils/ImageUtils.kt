package com.lmx.common.utils

import android.os.Build
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.api.load
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.transform.RoundedCornersTransformation

/**
 * Created by lmx on 2021/1/20
 * Describe:
 * 这应该是一个很新的一个图片加载库，完全使用kotlin编写，使用了kotlin的协程，图片网络请求方式默认为Okhttp,相比较于我们常用的Picasso，Glide或者Fresco，它有以下几个特点：
 * 1、足够快速，它在内存、图片存储、图片的采样、Bitmap重用、暂停\取消下载等细节方面都有很大的优化(相比于上面讲的三大框架)；
 * 2、足够轻量，只有大概1500个核心方法，当然也是相对于PGF而言的；
 * 3、足够新，也足够现代！使用了最新的Koltin协程所编写，充分发挥了CPU的性能，同时也使用了OKHttp、Okio、LifeCycle等比较新式的Android库。
 * Coil默认提供了四种变换同时可支持多种变换：模糊变换BlurTransformation、圆形变换CircleCropTransformation、灰度变换GrayscaleTransformation 和 圆角变换RoundedCornersTransformation
 */
object ImageUtils {

    @BindingAdapter(value = ["url", "placeholder"], requireAll = false)
    @JvmStatic
    fun setImageUrl(imageView: ImageView, url: String, placeholder: Int) {
        setImageUrl(
            imageView,
            url,
            placeholder,
            0f
        )
    }


    @JvmStatic
    fun setImageUrl(imageView: ImageView, url: String, placeholder: Int, radius: Float) {
        val context = imageView.context ?: return
        val gifImageLoader = ImageLoader(context) {
            componentRegistry {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    add(ImageDecoderDecoder())
                } else {
                    add(GifDecoder())
                }
            }
        }
        imageView.load(url, gifImageLoader) {
            placeholder(placeholder)
            // 淡入淡出
            crossfade(true)
//            transformations(GrayscaleTransformation())
//            transformations(GrayscaleTransformation(),RoundedCornersTransformation(
//                    topLeft = radius,
//                    topRight = radius,
//                    bottomLeft = radius,
//                    bottomRight = radius
//                ))
            transformations(
                RoundedCornersTransformation(
                    topLeft = radius,
                    topRight = radius,
                    bottomLeft = radius,
                    bottomRight = radius
                )
            )
        }
    }

    @BindingAdapter(value = ["url", "placeholder"], requireAll = false)
    @JvmStatic
    fun setSvgUrl(imageView: ImageView, url: String, placeholder: Int) {
        val context = imageView.context ?: return
        val svgImageLoader = ImageLoader(context) {
            componentRegistry {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    add(ImageDecoderDecoder())
                } else {
                    add(SvgDecoder(context))
                }
            }
        }
        imageView.load(url, svgImageLoader) {
            placeholder(placeholder)
        }
        //非View式的加载：
//  Coil.load(context, url) {
//            target { drawable ->
//            }
//        }
//        resize模式：
//        val drawable = Coil.get(url) {
//            size(width, height)
//        }
    }
}