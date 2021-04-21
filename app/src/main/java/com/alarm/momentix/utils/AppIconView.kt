//package com.alarm.momentix.utils
//
//
//import android.R
//import android.animation.Animator
//import android.animation.ValueAnimator
//import android.animation.ValueAnimator.AnimatorUpdateListener
//import android.content.Context
//import android.graphics.*
//import android.media.ThumbnailUtils
//import android.util.AttributeSet
//import android.view.View
//import android.view.animation.DecelerateInterpolator
//import android.view.animation.OvershootInterpolator
//import androidx.annotation.DrawableRes
//
//
//class AppIconView @JvmOverloads constructor(
//    context: Context?,
//    attrs: AttributeSet? = null,
//    defStyleAttr: Int = 0
//) :
//    View(context, attrs, defStyleAttr) {
//    private var bgBitmap: Bitmap? = null
//    private var fgSecondsBitmap: Bitmap? = null
//    private var fgMinutesBitmap: Bitmap? = null
//    private var fgHoursBitmap: Bitmap? = null
//    private var fgBitmap: Bitmap? = null
//    private val paint: Paint
//    private var size = 0
//    private var bgRotation = 1f
//    private var mRotation = 1f
//    private var bgScale = 0f
//    private var fgScale = 0f
//    private var path: Path? = null
//    private var animator: ValueAnimator?
//    private fun getBitmap(size: Int, @DrawableRes resource: Int): Bitmap {
//        val options = BitmapFactory.Options()
//        options.inScaled = false
//        return ThumbnailUtils.extractThumbnail(
//            BitmapFactory.decodeResource(
//                resources,
//                resource,
//                options
//            ), size, size
//        )
//    }
//
//    private fun getFgMatrix(bitmap: Bitmap?): Matrix {
//        val matrix = Matrix()
//        matrix.postTranslate((-bitmap!!.width / 2).toFloat(), (-bitmap.height / 2).toFloat())
//        matrix.postScale(fgScale, fgScale)
//        return matrix
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        val size = Math.min(canvas.width, canvas.height)
//        if (this.size != size || fgBitmap == null || path == null) {
//            this.size = size
//            bgBitmap = getBitmap(size, R.mipmap.ic_launcher_bg)
//            fgSecondsBitmap = getBitmap(size, R.mipmap.ic_launcher_fg_seconds)
//            fgMinutesBitmap = getBitmap(size, R.mipmap.ic_launcher_fg_minutes)
//            fgHoursBitmap = getBitmap(size, R.mipmap.ic_launcher_fg_hours)
//            fgBitmap = getBitmap(size, R.mipmap.ic_launcher_fg)
//            path = Path()
//            path!!.arcTo(RectF(0, 0, size.toFloat(), size.toFloat()), 0f, 359f)
//            path!!.close()
//        }
//        var matrix = Matrix()
//        matrix.postScale(
//            bgScale * 0.942986f,
//            bgScale * 0.942986f,
//            (size / 2).toFloat(),
//            (size / 2).toFloat()
//        )
//        val path = Path()
//        this.path!!.transform(matrix, path)
//        canvas.drawPath(path, paint)
//        matrix = getFgMatrix(bgBitmap)
//        matrix.postRotate(bgRotation * 120)
//        matrix.postTranslate((bgBitmap!!.width / 2).toFloat(), (bgBitmap!!.height / 2).toFloat())
//        canvas.drawBitmap(bgBitmap!!, matrix, paint)
//        matrix = getFgMatrix(fgSecondsBitmap)
//        matrix.postRotate(-rotation * 720)
//        matrix.postTranslate(
//            (fgSecondsBitmap!!.width / 2).toFloat(),
//            (fgSecondsBitmap!!.height / 2).toFloat()
//        )
//        canvas.drawBitmap(fgSecondsBitmap!!, matrix, paint)
//        matrix = getFgMatrix(fgMinutesBitmap)
//        matrix.postRotate(-rotation * 360)
//        matrix.postTranslate(
//            (fgMinutesBitmap!!.width / 2).toFloat(),
//            (fgMinutesBitmap!!.height / 2).toFloat()
//        )
//        canvas.drawBitmap(fgMinutesBitmap!!, matrix, paint)
//        matrix = getFgMatrix(fgHoursBitmap)
//        matrix.postRotate(-rotation * 60)
//        matrix.postTranslate(
//            (fgHoursBitmap!!.width / 2).toFloat(),
//            (fgHoursBitmap!!.height / 2).toFloat()
//        )
//        canvas.drawBitmap(fgHoursBitmap!!, matrix, paint)
//        matrix = getFgMatrix(fgBitmap)
//        matrix.postTranslate((fgBitmap!!.width / 2).toFloat(), (fgBitmap!!.height / 2).toFloat())
//        canvas.drawBitmap(fgBitmap!!, matrix, paint)
//    }
//
//    fun addListener(listener: Animator.AnimatorListener?) {
//        if (animator != null) animator.addListener(listener)
//    }
//
//    fun removeListener(listener: Animator.AnimatorListener?) {
//        if (animator != null) animator.removeListener(listener)
//    }
//
//    init {
//        paint = Paint()
//        paint.isAntiAlias = true
//        paint.color = Color.parseColor("#212121")
//        paint.isDither = true
//        animator = ValueAnimator.ofFloat(bgScale, 0.8f)
//        animator.setInterpolator(OvershootInterpolator())
//        animator.setDuration(750)
//        animator.setStartDelay(250)
//        animator.addUpdateListener(AnimatorUpdateListener { animator: ValueAnimator ->
//            bgScale = animator.animatedValue as Float
//            invalidate()
//        })
//        animator.start()
//        animator = ValueAnimator.ofFloat(rotation, 0f)
//        animator.setInterpolator(DecelerateInterpolator())
//        animator.setDuration(1000)
//        animator.setStartDelay(250)
//        animator.addUpdateListener(AnimatorUpdateListener { animator: ValueAnimator ->
//            fgScale = animator.animatedFraction * 0.8f
//            rotation = animator.animatedValue as Float
//            invalidate()
//        })
//        animator.start()
//        animator = ValueAnimator.ofFloat(bgRotation, 0f)
//        animator.setInterpolator(DecelerateInterpolator())
//        animator.setDuration(1250)
//        animator.setStartDelay(250)
//        animator.addUpdateListener(AnimatorUpdateListener { animator: ValueAnimator ->
//            bgRotation = animator.animatedValue as Float
//            invalidate()
//        })
//        animator.start()
//    }
//}