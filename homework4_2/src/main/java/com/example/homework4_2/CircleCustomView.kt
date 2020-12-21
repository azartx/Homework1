package com.example.homework4_2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Region
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CircleCustomView : View {

    private var defaultStateFlag: Boolean = true

    private var viewWidth: Float = 0F
    private var viewHeight: Float = 0F

    private var centerX: Float = 0F
    private var centerY: Float = 0F
    private val radius: Float = 650F

    // дополнительно сделал интовые, что бы потом не вызывать постоянно toInt()
    private var intCenterX = 0
    private var intCenterY = 0
    private val intRadius = 650

    private val rect = RectF()

    private var paints = arrayOf(Paint(), Paint(), Paint(), Paint(), Paint())
    private var regions = arrayOf(Region(), Region(), Region(), Region(), Region())
    private var colors = arrayOf(-3355444,
            -16776961,
            -16711681,
            -12303292,
            -16711936,
            -65281,
            -65536,
            -256,
            -3355444)

    var customClickListener: OnTouchCustomClickListener? = null

    interface OnTouchCustomClickListener {
        fun onClickCoords(view: View, x: Int, y: Int, color: Int)
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        viewWidth = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        viewHeight = MeasureSpec.getSize(heightMeasureSpec).toFloat()

        centerX = viewWidth / 2
        centerY = viewHeight / 2

        intCenterX = viewWidth.toInt() / 2
        intCenterY = viewHeight.toInt() / 2

        rect.set(centerX - radius / 2,
                centerY - radius / 2,
                centerX + radius / 2,
                centerY + radius / 2)

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (defaultStateFlag) {
            paints[0].color = Color.RED
            paints[1].color = Color.YELLOW
            paints[2].color = Color.BLUE
            paints[3].color = Color.GREEN
            paints[4].color = Color.GRAY

            defaultStateFlag = false
        }
    }

    // создал тут объекты, компилятор тут не ругается...
    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        regions[0] = Region(intCenterX - intRadius, intCenterY - intRadius, intCenterX, intCenterY) // лево верх
        regions[1] = Region(intCenterX, intCenterY - intRadius, intCenterX + intRadius, intCenterY) // право верх
        regions[2] = Region(intCenterX - intRadius, intCenterY, intCenterX, intCenterY + intRadius) // низ лево
        regions[3] = Region(intCenterX, intCenterY, intCenterX + intRadius, intCenterY + intRadius) // низ право
        regions[4] = Region(intCenterX - 100, intCenterY - 100, intCenterX + 100, intCenterY + 100)
    }

    override fun onDraw(canvas: Canvas?) {

        canvas?.drawArc(rect, 180F, 90F, true, paints[0]) //верх лево
        canvas?.drawArc(rect, 270F, 90F, true, paints[1]) //верх право
        canvas?.drawArc(rect, 90F, 90F, true, paints[2]) //низ лево
        canvas?.drawArc(rect, 0F, 90F, true, paints[3]) //низ право
        canvas?.drawCircle(centerX, centerY, 100F, paints[4])
        super.onDraw(canvas)
    }

    fun customListenerData(customClickListener: OnTouchCustomClickListener) {
        this.customClickListener = customClickListener
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_DOWN) {
            val x = event.x
            val y = event.y

            if (regions[4].contains(x.toInt(), y.toInt())) {
                customClickListener?.onClickCoords(this, x.toInt(), y.toInt(), paints[4].color)
                for (i in 0..3) {
                    paints[i].color = colors.random()
                    invalidate()
                }
            } else {
                for (i in 0..3) {
                    if (regions[i].contains(x.toInt(), y.toInt())) {
                        paints[i].color = colors.random()
                        customClickListener?.onClickCoords(this, x.toInt(), y.toInt(), paints[i].color)
                        invalidate()
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

}