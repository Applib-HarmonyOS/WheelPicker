package com.itheima.wheelpicker;

import com.itheima.wheelpicker.util.AttrUtil;
import com.itheima.wheelpicker.util.LogUtil;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.ScrollHelper;
import ohos.agp.components.VelocityDetector;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.render.ThreeDimView;
import ohos.agp.text.Font;
import ohos.agp.utils.Color;
import ohos.agp.utils.Matrix;
import ohos.agp.utils.Rect;
import ohos.agp.utils.RectFloat;
import ohos.agp.utils.TextAlignment;
import ohos.agp.utils.TextTool;
import ohos.app.Context;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;
import ohos.multimodalinput.event.MmiPoint;
import ohos.multimodalinput.event.TouchEvent;

/**
 * 滚轮选择器
 * <p>
 * WheelPicker
 *
 * @author AigeStudio 2015-12-12
 * @author AigeStudio 2016-06-17
 *         更新项目结构
 *         <p>
 *         New project structure
 * @version 1.1.0
 */
public class WheelPicker extends Component implements IDebug, IWheelPicker, Runnable, Component.DrawTask, Component.TouchEventListener {
    /**
     * 滚动状态标识值
     *
     * @see OnWheelChangeListener#onWheelScrollStateChanged(int)
     */
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_SCROLLING = 2;

    /**
     * 数据项对齐方式标识值
     *
     * @see #setItemAlign(int)
     */
    public static final int ALIGN_CENTER = 0;
    public static final int ALIGN_LEFT = 1;
    public static final int ALIGN_RIGHT = 2;

    private static final String TAG = WheelPicker.class.getSimpleName();

    private final EventHandler mHandler = new EventHandler(EventRunner.create());

    /**
     * Determines whether the current scrolling animation is triggered by touchEvent or setSelectedItemPosition.
     * User added eventListeners will only be fired after touchEvents.
     */
    private boolean isTouchTriggered;

    private Paint mPaint;
    private ScrollHelper mScroller;
    private VelocityDetector mTracker;

    /**
     * 相关监听器
     *
     * @see OnWheelChangeListener,OnItemSelectedListener
     */
    private OnItemSelectedListener mOnItemSelectedListener;
    private OnWheelChangeListener mOnWheelChangeListener;

    private Rect mRectDrawn;
    private Rect mRectIndicatorHead;
    private Rect mRectIndicatorFoot;
    private Rect mRectCurrentItem;

    private Matrix mMatrixRotate;
    private Matrix mMatrixDepth;

    /**
     * 数据源
     */
    private List<String> mData;

    /**
     * 最宽的文本
     *
     * @see #setMaximumWidthText(String)
     */
    private String mMaxWidthText;

    /**
     * 滚轮选择器中可见的数据项数量和滚轮选择器将会绘制的数据项数量
     *
     * @see #setVisibleItemCount(int)
     */
    private int mVisibleItemCount;
    private int mDrawnItemCount;

    /**
     * 滚轮选择器将会绘制的Item数量的一半
     */
    private int mHalfDrawnItemCount;

    /**
     * 单个文本最大宽高
     */
    private int mTextMaxWidth;
    private int mTextMaxHeight;

    /**
     * 数据项文本颜色以及被选中的数据项文本颜色
     *
     * @see #setItemTextColor(int)
     * @see #setSelectedItemTextColor(int)
     */
    private int mItemTextColor;
    private int mSelectedItemTextColor;

    /**
     * 数据项文本尺寸
     *
     * @see #setItemTextSize(int)
     */
    private int mItemTextSize;

    /**
     * 指示器尺寸
     *
     * @see #setIndicatorSize(int)
     */
    private int mIndicatorSize;

    /**
     * 指示器颜色
     *
     * @see #setIndicatorColor(int)
     */
    private int mIndicatorColor;

    /**
     * 幕布颜色
     *
     * @see #setCurtainColor(int)
     */
    private int mCurtainColor;

    /**
     * 数据项之间间距
     *
     * @see #setItemSpace(int)
     */
    private int mItemSpace;

    /**
     * 数据项对齐方式
     *
     * @see #setItemAlign(int)
     */
    private int mItemAlign;

    /**
     * 滚轮选择器单个数据项高度以及单个数据项一半的高度
     */
    private int mItemHeight;
    private int mHalfItemHeight;

    /**
     * 滚轮选择器内容区域高度的一半
     */
    private int mHalfWheelHeight;

    /**
     * 当前被选中的数据项所显示的数据在数据源中的位置
     *
     * @see #setSelectedItemPosition(int)
     */
    private int mSelectedItemPosition;

    /**
     * 当前被选中的数据项所显示的数据在数据源中的位置
     *
     * @see #getCurrentItemPosition()
     */
    private int mCurrentItemPosition;

    /**
     * 滚轮滑动时可以滑动到的最小/最大的Y坐标
     */
    private int mMinFlingY;
    private int mMaxFlingY;

    /**
     * 滚轮滑动时的最小/最大速度
     */
    private int mMinimumVelocity;

    /**
     * 滚轮选择器中心坐标
     */
    private int mWheelCenterX;
    private int mWheelCenterY;

    /**
     * 滚轮选择器绘制中心坐标
     */
    private int mDrawnCenterX;
    private int mDrawnCenterY;

    /**
     * 滚轮选择器视图区域在Y轴方向上的偏移值
     */
    private int mScrollOffsetY;

    /**
     * 滚轮选择器中最宽或最高的文本在数据源中的位置
     */
    private int mTextMaxWidthPosition;

    /**
     * 用户手指上一次触摸事件发生时事件Y坐标
     */
    private int mLastPointY;

    /**
     * 手指触摸屏幕时事件点的Y坐标
     */
    private int mDownPointY;

    /**
     * 点击与触摸的切换阀值
     */
    private int mTouchSlop;

    /**
     * 滚轮选择器的每一个数据项文本是否拥有相同的宽度
     *
     * @see #setSameWidth(boolean)
     */
    private boolean hasSameWidth;

    /**
     * 是否显示指示器
     *
     * @see #setIndicator(boolean)
     */
    private boolean hasIndicator;

    /**
     * 是否显示幕布
     *
     * @see #setCurtain(boolean)
     */
    private boolean hasCurtain;

    /**
     * 是否显示空气感效果
     *
     * @see #setAtmospheric(boolean)
     */
    private boolean hasAtmospheric;

    /**
     * 数据是否循环展示
     *
     * @see #setCyclic(boolean)
     */
    private boolean isCyclic;

    /**
     * 滚轮是否为卷曲效果
     *
     * @see #setCurved(boolean)
     */
    private boolean isCurved;

    /**
     * 是否为点击模式
     */
    private boolean isClick;

    /**
     * 是否为强制结束滑动
     */
    private boolean isForceFinishScroll;

    private boolean isDebug;

    private String fontPath;


    public WheelPicker(Context context) {
        this(context, null);
    }

    public WheelPicker(Context context, AttrSet attrs) {
        super(context, attrs);
        try {
            mData = Arrays.asList(this.getResourceManager().getElement(ResourceTable.Strarray_WheelArrayDefault).getStringArray());
        } catch (IOException | WrongTypeException | NotExistException e) {
            e.printStackTrace();
        }
        init(context, attrs);

        // 可见数据项改变后更新与之相关的参数
        // Update relevant parameters when the count of visible item changed
        updateVisibleItemCount();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
//        Paint.LINEAR_TEXT_FLAG
        mPaint.setTextSize(mItemTextSize);

        if (!TextTool.isNullOrEmpty(fontPath)){
            Font typeface = AttrUtil.createFont(context, fontPath);
            mPaint.setFont(typeface);
        }

        // 更新文本对齐方式
        // Update alignment of text
        updateItemTextAlign();

        // 计算文本尺寸
        // Correct sizes of text
        computeTextSize();

        mScroller = new ScrollHelper();

        mMinimumVelocity = 150;
        mTouchSlop = 24;


        mRectDrawn = new Rect();

        mRectIndicatorHead = new Rect();
        mRectIndicatorFoot = new Rect();

        mRectCurrentItem = new Rect();

        mMatrixRotate = new Matrix();
        mMatrixDepth = new Matrix();

        new EventHandler(EventRunner.getMainEventRunner()).postTask(() -> addDrawTask(this));
        setTouchEventListener(this);
    }

    private void init(Context context, AttrSet attrSet) {
        new WheelPicker.StyledAttributes(context, attrSet);
    }

    private class StyledAttributes {

        StyledAttributes(Context context, AttrSet attrSet) {
            mItemTextSize = (int) AttrUtil.getDimension(attrSet, AttrUtil.getString(context, ResourceTable.String_wheelItemTextSize),
                    AttrUtil.getDimen(context, ResourceTable.Float_WheelItemTextSize));

            mVisibleItemCount = AttrUtil.getIntegerValue(attrSet, AttrUtil.getString(context, ResourceTable.String_wheelVisibleItemCount), 7);

            mSelectedItemPosition = AttrUtil.getIntegerValue(attrSet, AttrUtil.getString(context, ResourceTable.String_wheelSelectedItemPosition), 0);

            hasSameWidth = AttrUtil.getBooleanValue(attrSet, AttrUtil.getString(context, ResourceTable.String_wheelSameWidth), false);

            mTextMaxWidthPosition = AttrUtil.getIntegerValue(attrSet, AttrUtil.getString(context, ResourceTable.String_wheelMaximumWidthTextPosition), -1);

            mMaxWidthText = AttrUtil.getStringValue(attrSet, AttrUtil.getString(context, ResourceTable.String_wheelMaximumWidthText), "");

            mSelectedItemTextColor = AttrUtil.getColorValue(attrSet, AttrUtil.getString(context, ResourceTable.String_wheelSelectedItemTextColor), -1);

            mItemTextColor = AttrUtil.getColorValue(attrSet, AttrUtil.getString(context, ResourceTable.String_wheelItemTextColor), 0xFF888888);

            mItemSpace = (int) AttrUtil.getDimension(attrSet, AttrUtil.getString(context, ResourceTable.String_wheelItemSpace),
                    AttrUtil.getDimen(context, ResourceTable.Float_WheelItemSpace));

            isCyclic = AttrUtil.getBooleanValue(attrSet, AttrUtil.getString(context, ResourceTable.String_wheelCyclic), false);

            initIndicator(context, attrSet);

            initCurtain(context, attrSet);

            hasAtmospheric = AttrUtil.getBooleanValue(attrSet, AttrUtil.getString(context, ResourceTable.String_wheelAtmospheric), false);

            isCurved = AttrUtil.getBooleanValue(attrSet, AttrUtil.getString(context, ResourceTable.String_wheelCurved), false);

            String textAlign = AttrUtil.getStringValue(attrSet, AttrUtil.getString(context, ResourceTable.String_wheelItemAlign), "center");

            mItemAlign = getItemTextAlign(textAlign.toLowerCase(Locale.ENGLISH));

            initTextFont(context, attrSet);
        }

        private void initTextFont(Context context, AttrSet attrSet) {
            fontPath = AttrUtil.getStringValue(attrSet, AttrUtil.getString(context, ResourceTable.String_wheelFontPath), "");
        }

        private void initCurtain(Context context, AttrSet attrSet) {
            hasCurtain = AttrUtil.getBooleanValue(attrSet, AttrUtil.getString(context, ResourceTable.String_wheelCurtain), false);

            mCurtainColor = AttrUtil.getColorValue(attrSet, AttrUtil.getString(context, ResourceTable.String_wheelCurtainColor), 0x88FFFFFF);
        }

        private void initIndicator(Context context, AttrSet attrSet) {
            hasIndicator = AttrUtil.getBooleanValue(attrSet, AttrUtil.getString(context, ResourceTable.String_wheelIndicator), false);
            mIndicatorColor = AttrUtil.getColorValue(attrSet, AttrUtil.getString(context, ResourceTable.String_wheelIndicatorColor), 0xFFEE3333);

            mIndicatorSize = (int) AttrUtil.getDimension(attrSet, AttrUtil.getString(context, ResourceTable.String_wheelIndicatorSize),
                    AttrUtil.getDimen(context, ResourceTable.Float_WheelIndicatorSize));
        }
        private int getItemTextAlign(String s) {
            switch (s) {
                case "left":
                    return ALIGN_LEFT;
                case "right":
                    return ALIGN_RIGHT;
                default:
                    return ALIGN_CENTER;
            }
        }
    }

    private void updateVisibleItemCount() {
        if (mVisibleItemCount < 2)
            throw new ArithmeticException("Wheel's visible item count can not be less than 2!");

        // 确保滚轮选择器可见数据项数量为奇数
        // Be sure count of visible item is odd number
        if (mVisibleItemCount % 2 == 0)
            mVisibleItemCount += 1;
        mDrawnItemCount = mVisibleItemCount + 2;
        mHalfDrawnItemCount = mDrawnItemCount / 2;
    }

    private void computeTextSize() {
        mTextMaxWidth = 0;
        mTextMaxHeight = 0;
        if (hasSameWidth) {
            mTextMaxWidth = (int) mPaint.measureText(String.valueOf(mData.get(0)));
        } else if (isPosInRang(mTextMaxWidthPosition)) {
            mTextMaxWidth = (int) mPaint.measureText
                    (String.valueOf(mData.get(mTextMaxWidthPosition)));
        }
        else if (!TextTool.isNullOrEmpty(mMaxWidthText)) {
            mTextMaxWidth = (int) mPaint.measureText(mMaxWidthText);
        }
        else {
            for (Object obj : mData) {
                String text = String.valueOf(obj);
                int width = (int) mPaint.measureText(text);
                mTextMaxWidth = Math.max(mTextMaxWidth, width);
            }
        }
        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        mTextMaxHeight = (int) (metrics.bottom - metrics.top);
    }

    private void updateItemTextAlign() {
        switch (mItemAlign) {
            case ALIGN_LEFT:
                mPaint.setTextAlign(TextAlignment.LEFT);
                break;
            case ALIGN_RIGHT:
                mPaint.setTextAlign(TextAlignment.RIGHT);
                break;
            default:
                mPaint.setTextAlign(TextAlignment.CENTER);
                break;
        }
    }

    public void onMeasure () {

        // 计算原始内容尺寸
        // Correct sizes of original content
        int resultWidth = mTextMaxWidth;
        int resultHeight = mTextMaxHeight * mVisibleItemCount + mItemSpace * (mVisibleItemCount - 1);

        // 如果开启弯曲效果则需要重新计算弯曲后的尺寸
        // Correct view sizes again if curved is enable
        if (isCurved) {
            resultHeight = (int) (2 * resultHeight / Math.PI);
        }
        if (isDebug) {
            LogUtil.error(TAG, "Wheel's content size is (" + resultWidth + ":" + resultHeight + ")");
        }

        // 考虑内边距对尺寸的影响
        // Consideration padding influence the view sizes
        resultWidth += getPaddingLeft() + getPaddingRight();
        resultHeight += getPaddingTop() + getPaddingBottom();
        if (isDebug) {
            LogUtil.error(TAG, "Wheel's size is (" + resultWidth + ":" + resultHeight + ")");
        }

        setComponentSize ( resultWidth ,  resultHeight );

        onSizeChanged ();
    }

    public void onSizeChanged () {
        // 设置内容区域
        // Set content region
        mRectDrawn.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(),
                getHeight() - getPaddingBottom());
        if (isDebug)
            LogUtil.error(TAG, "Wheel's drawn rect size is (" + mRectDrawn.getWidth() + ":" +
                    mRectDrawn.getHeight() + ") and location is (" + mRectDrawn.left + ":" +
                    mRectDrawn.top + ")");

        // 获取内容区域中心坐标
        // Get the center coordinates of content region
        mWheelCenterX = mRectDrawn.getCenterX();
        mWheelCenterY = mRectDrawn.getCenterY();

        // 计算数据项绘制中心
        // Correct item drawn center
        computeDrawnCenter();

        mHalfWheelHeight = mRectDrawn.getHeight() / 2;

        mItemHeight = mRectDrawn.getHeight() / mVisibleItemCount;
        mHalfItemHeight = mItemHeight / 2;

        // 初始化滑动最大坐标
        // Initialize fling max Y-coordinates
        computeFlingLimitY();

        // 计算指示器绘制区域
        // Correct region of indicator
        computeIndicatorRect();

        // 计算当前选中的数据项区域
        // Correct region of current select item
        computeCurrentItemRect();
    }

    private void computeDrawnCenter() {
        switch (mItemAlign) {
            case ALIGN_LEFT:
                mDrawnCenterX = mRectDrawn.left;
                break;
            case ALIGN_RIGHT:
                mDrawnCenterX = mRectDrawn.right;
                break;
            default:
                mDrawnCenterX = mWheelCenterX;
                break;
        }
        mDrawnCenterY = (int) (mWheelCenterY - ((mPaint.ascent() + mPaint.descent()) / 2));
    }

    private void computeFlingLimitY() {
        int currentItemOffset = mSelectedItemPosition * mItemHeight;
        mMinFlingY = isCyclic ? Integer.MIN_VALUE :
                -mItemHeight * (mData.size() - 1) + currentItemOffset;
        mMaxFlingY = isCyclic ? Integer.MAX_VALUE : currentItemOffset;
    }

    private void computeIndicatorRect() {
        if (!hasIndicator) return;
        int halfIndicatorSize = mIndicatorSize / 2;
        int indicatorHeadCenterY = mWheelCenterY + mHalfItemHeight;
        int indicatorFootCenterY = mWheelCenterY - mHalfItemHeight;
        mRectIndicatorHead.set(mRectDrawn.left, indicatorHeadCenterY - halfIndicatorSize,
                mRectDrawn.right, indicatorHeadCenterY + halfIndicatorSize);
        mRectIndicatorFoot.set(mRectDrawn.left, indicatorFootCenterY - halfIndicatorSize,
                mRectDrawn.right, indicatorFootCenterY + halfIndicatorSize);
    }

    private void computeCurrentItemRect() {
        if (!hasCurtain && mSelectedItemTextColor == -1) return;
        mRectCurrentItem.set(mRectDrawn.left, mWheelCenterY - mHalfItemHeight, mRectDrawn.right,
                mWheelCenterY + mHalfItemHeight);
    }

    @Override
    public void onDraw(Component component, Canvas canvas) {
        onMeasure();
        if (null != mOnWheelChangeListener)
            mOnWheelChangeListener.onWheelScrolled(mScrollOffsetY);

        if (mData.isEmpty()){return;}

        int drawnDataStartPos = -mScrollOffsetY / mItemHeight - mHalfDrawnItemCount;
        for (int drawnDataPos = drawnDataStartPos + mSelectedItemPosition,
             drawnOffsetPos = -mHalfDrawnItemCount;
             drawnDataPos < drawnDataStartPos + mSelectedItemPosition + mDrawnItemCount;
             drawnDataPos++, drawnOffsetPos++) {
            String data = handleCyclic(drawnDataPos);

            mPaint.setColor(new Color(mItemTextColor));
            mPaint.setStyle(Paint.Style.FILL_STYLE);
            int mDrawnItemCenterY = mDrawnCenterY + (drawnOffsetPos * mItemHeight) +
                    mScrollOffsetY % mItemHeight;

            int distanceToCenter = 0;
            if (isCurved) {
                distanceToCenter = addCurved(mDrawnItemCenterY);
            }
            if (hasAtmospheric) {
                addAtmospheric(mDrawnItemCenterY);
            }
            // 根据卷曲与否计算数据项绘制Y方向中心坐标
            // Calculate the center coordinates of the data item in the Y direction according to whether it is curled or not
            // Correct item's drawn centerY base on curved state
            int drawnCenterY = isCurved ? mDrawnCenterY - distanceToCenter : mDrawnItemCenterY;

            // 判断是否需要为当前数据项绘制不同颜色
            // Judges need to draw different color for current item or not
            addItemColor(canvas, data, drawnCenterY);

            if (isDebug) {
                handleDebug(canvas, drawnOffsetPos);
            }
        }
        // 是否需要绘制幕布
        // Need to draw curtain or not
        if (hasCurtain) {
            addCurtain(canvas);
        }
        // 是否需要绘制指示器
        // Need to draw indicator or not
        if (hasIndicator) {
            addIndicator(canvas);
        }
        if (isDebug) {
            handleDebug(canvas);
        }
    }

    private String handleCyclic(int drawnDataPos) {
        String data = "";
        if (isCyclic) {
            int actualPos = drawnDataPos % mData.size();
            actualPos = actualPos < 0 ? (actualPos + mData.size()) : actualPos;
            data = String.valueOf(mData.get(actualPos));
        } else {
            if (isPosInRang(drawnDataPos))
                data = String.valueOf(mData.get(drawnDataPos));
        }
        return data;
    }

    private void handleDebug(Canvas canvas) {
        mPaint.setColor(new Color(0x4433EE33));
        mPaint.setStyle(Paint.Style.FILL_STYLE);
        canvas.drawRect(0, 0, getPaddingLeft(), getHeight(), mPaint);
        canvas.drawRect(0, 0, getWidth(), getPaddingTop(), mPaint);
        canvas.drawRect(getWidth() - (float)getPaddingRight(), 0, getWidth(), getHeight(), mPaint);
        canvas.drawRect(0, getHeight() - (float)getPaddingBottom(), getWidth(), getHeight(), mPaint);
    }

    private void addIndicator(Canvas canvas) {
        mPaint.setColor(new Color(mIndicatorColor));
        mPaint.setStyle(Paint.Style.FILL_STYLE);
        canvas.drawRect(mRectIndicatorHead, mPaint);
        canvas.drawRect(mRectIndicatorFoot, mPaint);
    }

    private void addCurtain(Canvas canvas) {
        mPaint.setColor(new Color(mCurtainColor));
        mPaint.setStyle(Paint.Style.FILL_STYLE);
        canvas.drawRect(mRectCurrentItem, mPaint);
    }

    private void handleDebug(Canvas canvas, int drawnOffsetPos) {
        canvas.save();
        canvas.clipRect(mRectDrawn);
        mPaint.setColor(new Color(0xFFEE3333));
        int lineCenterY = mWheelCenterY + (drawnOffsetPos * mItemHeight);
        canvas.drawLine(mRectDrawn.left, lineCenterY, mRectDrawn.right, lineCenterY,
                mPaint);
        mPaint.setColor(new Color(0xFF3333EE));
        mPaint.setStyle(Paint.Style.STROKE_STYLE);
        int top = lineCenterY - mHalfItemHeight;
        canvas.drawRect(mRectDrawn.left, top, mRectDrawn.right, (float)top + mItemHeight, mPaint);
        canvas.restore();
    }

    private void addItemColor(Canvas canvas, String data, int drawnCenterY) {
        if (mSelectedItemTextColor != -1) {
            canvas.save();
            if (isCurved) canvas.concat(mMatrixRotate);
            RectFloat rectFloat = new RectFloat(mRectCurrentItem);
            canvas.clipRect(rectFloat, Canvas.ClipOp.DIFFERENCE);
            canvas.drawText(mPaint, data, mDrawnCenterX, drawnCenterY);
            canvas.restore();

            mPaint.setColor(new Color(mSelectedItemTextColor));
            canvas.save();
            if (isCurved) canvas.concat(mMatrixRotate);
            canvas.clipRect(mRectCurrentItem);
            canvas.drawText(mPaint, data, mDrawnCenterX, drawnCenterY);
            canvas.restore();
        } else {
            canvas.save();
            canvas.clipRect(mRectDrawn);
            if (isCurved) canvas.concat(mMatrixRotate);
            canvas.drawText(mPaint, data, mDrawnCenterX, drawnCenterY);
            canvas.restore();
        }
    }

    private void addAtmospheric(int mDrawnItemCenterY) {
        int alpha = (int) ((mDrawnCenterY - Math.abs(mDrawnCenterY - mDrawnItemCenterY)) *
                1.0F / mDrawnCenterY * 255);
        alpha = Math.max(alpha, 0);
        mPaint.setAlpha(alpha);
    }

    private int addCurved(int mDrawnItemCenterY) {
        // 计算数据项绘制中心距离滚轮中心的距离比率
        // Correct ratio of item's drawn center to wheel center
        float ratio = (mDrawnCenterY - Math.abs(mDrawnCenterY - mDrawnItemCenterY) -
                mRectDrawn.top) * 1.0F / (mDrawnCenterY - mRectDrawn.top);

        // 计算单位
        // Correct unit
        int unit = 0;
        if (mDrawnItemCenterY > mDrawnCenterY)
            unit = 1;
        else if (mDrawnItemCenterY < mDrawnCenterY)
            unit = -1;

        float degree = (-(1 - ratio) * 90 * unit);
        if (degree < -90) degree = -90;
        if (degree > 90) degree = 90;
        int distanceToCenter = computeSpace((int) degree);

        int transX = mWheelCenterX;
        switch (mItemAlign) {
            case ALIGN_LEFT:
                transX = mRectDrawn.left;
                break;
            case ALIGN_RIGHT:
                transX = mRectDrawn.right;
                break;
            default:
                break;
        }
        int transY = mWheelCenterY - distanceToCenter;

        ThreeDimView mThreeDimView = new ThreeDimView();
        mThreeDimView.rotateX(degree);
        mThreeDimView.getMatrix(mMatrixRotate);

        mMatrixRotate.preTranslate(-transX, -transY);
        mMatrixRotate.postTranslate(transX, transY);
        mMatrixDepth.preTranslate(-transX, -transY);
        mMatrixDepth.postTranslate(transX, transY);

        mMatrixRotate.postConcat(mMatrixDepth);
        return distanceToCenter;
    }

    private boolean isPosInRang(int position) {
        return position >= 0 && position < mData.size();
    }

    private int computeSpace(int degree) {
        return (int) (Math.sin(Math.toRadians(degree)) * mHalfWheelHeight);
    }

    @Override
    public boolean onTouchEvent(Component component, TouchEvent event) {
        final int activePointerIndex = event.getIndex();
        MmiPoint point = event.getPointerScreenPosition(activePointerIndex);

        switch (event.getAction()) {
            case TouchEvent.PRIMARY_POINT_DOWN:
                handleDownMovement(event, point);
                break;
            case TouchEvent.POINT_MOVE:
                if (Math.abs(mDownPointY - point.getY()) < mTouchSlop) {
                    isClick = true;
                    break;
                }
                isClick = false;
                mTracker.addEvent(event);
                if (null != mOnWheelChangeListener)
                    mOnWheelChangeListener.onWheelScrollStateChanged(SCROLL_STATE_DRAGGING);

                // 滚动内容
                // Scroll WheelPicker's content
                float move = point.getY() - mLastPointY;
                if (Math.abs(move) < 1) break;
                mScrollOffsetY += move;
                mLastPointY = (int) point.getY();
                new EventHandler(EventRunner.getMainEventRunner()).postTask(this::invalidate);
                break;
            case TouchEvent.PRIMARY_POINT_UP:
                if (isClick  && ! isForceFinishScroll) {break;}
                handleUpMovement(event);
                break;
            case TouchEvent.CANCEL:
                if (null != mTracker) {
                    mTracker.clear();
                    mTracker = null;
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void handleDownMovement(TouchEvent event, MmiPoint point) {
        isTouchTriggered = true;
        if (null == mTracker)
            mTracker = VelocityDetector.obtainInstance();
        else
            mTracker.clear();
        mTracker.addEvent(event);
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
            isForceFinishScroll = true;
        }
        mDownPointY = mLastPointY = (int) point.getY();
    }

    private void handleUpMovement(TouchEvent event) {
        mTracker.addEvent(event);
        mTracker.calculateCurrentVelocity(1000);

        // 根据速度判断是该滚动还是滑动
        // Judges the WheelPicker is scroll or fling base on current velocity
        isForceFinishScroll = false;
        int velocity = (int) mTracker.getVerticalVelocity();
        if (Math.abs(velocity) > mMinimumVelocity) {
            mScroller.doFling(0, mScrollOffsetY, 0, velocity, 0, 0, mMinFlingY, mMaxFlingY);
            mScroller.startScrollY ( mScroller.getCurrValue(AXIS_Y)  +  computeDistanceToEndPoint(
                    mScroller.getCurrValue(AXIS_Y)%mItemHeight ),0 );
        } else {
            mScroller.startScroll(0, mScrollOffsetY, 0,
                    computeDistanceToEndPoint(mScrollOffsetY % mItemHeight));
        }
        // 校正坐标
        // Correct coordinates
        if  (!isCyclic)  {
            if  (mScroller.getCurrValue(AXIS_Y) > mMaxFlingY) {
                mScroller.startScrollY(mMaxFlingY, 0);
            }  else if (mScroller.getCurrValue(ScrollHelper.AXIS_Y) < mMinFlingY) {
                mScroller.startScrollY(mMinFlingY, 0);
            }
        }
        mHandler.postTask(this);
        if (null != mTracker) {
            mTracker.clear();
            mTracker = null;
        }
    }


    private int computeDistanceToEndPoint(int remainder) {
        if (Math.abs(remainder) > mHalfItemHeight) {
            if (mScrollOffsetY < 0)
                return -mItemHeight - remainder;
            else
                return mItemHeight - remainder;
        }
        else
            return -remainder;
    }

    @Override
    public void run() {
        if (null == mData || mData.isEmpty()) return;
        if (mScroller.isFinished() && !isForceFinishScroll) {
            if (mItemHeight == 0) return;
            int position = (-mScrollOffsetY / mItemHeight + mSelectedItemPosition) % mData.size();
            position = position < 0 ? position + mData.size() : position;
            if (isDebug)
                LogUtil.info(TAG, position + ":" + mData.get(position) + ":" + mScrollOffsetY);
            mCurrentItemPosition = position;
            handleListener(position);
        }
        if (mScroller.updateScroll()) {
            handleUpdateScroll();
        }
    }

    private void handleListener(int position) {
        if (isTouchTriggered && null != mOnItemSelectedListener)
            mOnItemSelectedListener.onItemSelected(this, mData.get(position), position);
        if (isTouchTriggered && null != mOnWheelChangeListener) {
            mOnWheelChangeListener.onWheelSelected(position);
            mOnWheelChangeListener.onWheelScrollStateChanged(SCROLL_STATE_IDLE);
        }
    }

    private void handleUpdateScroll() {
        if (null != mOnWheelChangeListener)
            mOnWheelChangeListener.onWheelScrollStateChanged(SCROLL_STATE_SCROLLING);
        mScrollOffsetY = mScroller.getCurrValue(AXIS_Y);
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
        mHandler.postTask(this,16);
    }

    @Override
    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    @Override
    public int getVisibleItemCount() {
        return mVisibleItemCount;
    }

    @Override
    public void setVisibleItemCount(int count) {
        mVisibleItemCount = count;
        updateVisibleItemCount();
        postLayout();
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
    }

    @Override
    public boolean isCyclic() {
        return isCyclic;
    }

    @Override
    public void setCyclic(boolean isCyclic) {
        this.isCyclic = isCyclic;
        computeFlingLimitY();
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
    }

    @Override
    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        mOnItemSelectedListener = listener;
    }

    @Override
    public int getSelectedItemPosition() {
        return mSelectedItemPosition;
    }

    @Override
    public void setSelectedItemPosition(int position) {
        helperSetSelectedItemPosition(position,true);
    }

    public void helperSetSelectedItemPosition(int position, boolean check){
        isTouchTriggered = false;
        if (check && mScroller.isFinished()){
            int length = getData().size();
            int itemDiff = position - mCurrentItemPosition;
            if (itemDiff==0){
                return;
            }
            if (isCyclic && Math.abs(itemDiff) > (length/2)){
                itemDiff += (itemDiff>0) ? -length : length;
            }
            mScroller.startScroll(0,mScroller.getCurrValue(AXIS_Y),0,(-itemDiff)*mItemHeight);
            mHandler.postTask(this);
        }
        else {
            if (!mScroller.isFinished()){
                mScroller.abortAnimation();
            }
            position = Math.min(position, mData.size() - 1);
            position = Math.max(position, 0);
            mSelectedItemPosition = position;
            mCurrentItemPosition = position;
            mScrollOffsetY = 0;
            computeFlingLimitY();
            postLayout();
            new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
        }
    }

    @Override
    public int getCurrentItemPosition() {
        return mCurrentItemPosition;
    }

    @Override
    public List getData() {
        return mData;
    }

    @Override
    public void setData(List data) {
        if (null == data)
            throw new NullPointerException("WheelPicker's data can not be null!");
        mData = data;

        // 重置位置
        if (mSelectedItemPosition > data.size() - 1 || mCurrentItemPosition > data.size() - 1) {
            mSelectedItemPosition = mCurrentItemPosition = data.size() - 1;
        } else {
            mSelectedItemPosition = mCurrentItemPosition;
        }
        mScrollOffsetY = 0;
        computeTextSize();
        computeFlingLimitY();
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: postLayout);
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
    }

    public void setSameWidth(boolean hasSameWidth) {
        this.hasSameWidth = hasSameWidth;
        computeTextSize();
        postLayout();
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
    }

    @Override
    public boolean hasSameWidth() {
        return hasSameWidth;
    }

    @Override
    public void setOnWheelChangeListener(OnWheelChangeListener listener) {
        mOnWheelChangeListener = listener;
    }

    @Override
    public String getMaximumWidthText() {
        return mMaxWidthText;
    }

    @Override
    public void setMaximumWidthText(String text) {
        if (null == text)
            throw new NullPointerException("Maximum width text can not be null!");
        mMaxWidthText = text;
        computeTextSize();
        postLayout();
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
    }

    @Override
    public int getMaximumWidthTextPosition() {
        return mTextMaxWidthPosition;
    }

    @Override
    public void setMaximumWidthTextPosition(int position) {
        if (!isPosInRang(position))
            throw new ArrayIndexOutOfBoundsException("Maximum width text Position must in [0, " +
                    mData.size() + "), but current is " + position);
        mTextMaxWidthPosition = position;
        computeTextSize();
        postLayout();
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
    }

    @Override
    public int getSelectedItemTextColor() {
        return mSelectedItemTextColor;
    }

    @Override
    public void setSelectedItemTextColor(int color) {
        mSelectedItemTextColor = color;
        computeCurrentItemRect();
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
    }

    @Override
    public int getItemTextColor() {
        return mItemTextColor;
    }

    @Override
    public void setItemTextColor(int color) {
        mItemTextColor = color;
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
    }

    @Override
    public int getItemTextSize() {
        return mItemTextSize;
    }

    @Override
    public void setItemTextSize(int size) {
        mItemTextSize = size;
        mPaint.setTextSize(mItemTextSize);
        computeTextSize();
        postLayout();
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
    }

    @Override
    public int getItemSpace() {
        return mItemSpace;
    }

    @Override
    public void setItemSpace(int space) {
        mItemSpace = space;
        postLayout();
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
    }

    @Override
    public void setIndicator(boolean hasIndicator) {
        this.hasIndicator = hasIndicator;
        computeIndicatorRect();
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
    }

    @Override
    public boolean hasIndicator() {
        return hasIndicator;
    }

    @Override
    public int getIndicatorSize() {
        return mIndicatorSize;
    }

    @Override
    public void setIndicatorSize(int size) {
        mIndicatorSize = size;
        computeIndicatorRect();
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
    }

    @Override
    public int getIndicatorColor() {
        return mIndicatorColor;
    }

    @Override
    public void setIndicatorColor(int color) {
        mIndicatorColor = color;
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
    }

    @Override
    public void setCurtain(boolean hasCurtain) {
        this.hasCurtain = hasCurtain;
        computeCurrentItemRect();
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
    }

    @Override
    public boolean hasCurtain() {
        return hasCurtain;
    }

    @Override
    public int getCurtainColor() {
        return mCurtainColor;
    }

    @Override
    public void setCurtainColor(int color) {
        mCurtainColor = color;
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
    }

    @Override
    public void setAtmospheric(boolean hasAtmospheric) {
        this.hasAtmospheric = hasAtmospheric;
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
    }

    @Override
    public boolean hasAtmospheric() {
        return hasAtmospheric;
    }

    @Override
    public boolean isCurved() {
        return isCurved;
    }

    @Override
    public void setCurved(boolean isCurved) {
        this.isCurved = isCurved;
        postLayout();
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
    }

    @Override
    public int getItemAlign() {
        return mItemAlign;
    }

    @Override
    public void setItemAlign(int align) {
        mItemAlign = align;
        updateItemTextAlign();
        computeDrawnCenter();
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
    }

    @Override
    public Font getTypeface() {
        if (null != mPaint)
            return mPaint.getFont();
        return null;
    }

    @Override
    public void setTypeface(Font tf) {
        if (null != mPaint)
            mPaint.setFont(tf);
        computeTextSize();
        postLayout();
        new EventHandler(EventRunner.getMainEventRunner()).postTask(this :: invalidate);
    }



    /**
     * 滚轮选择器Item项被选中时监听接口
     *
     * @author AigeStudio 2016-06-17
     *         新项目结构
     * @version 1.1.0
     */
    public interface OnItemSelectedListener {
        /**
         * 当滚轮选择器数据项被选中时回调该方法
         * 滚动选择器滚动停止后会回调该方法并将当前选中的数据和数据在数据列表中对应的位置返回
         *
         * @param picker   滚轮选择器
         * @param data     当前选中的数据
         * @param position 当前选中的数据在数据列表中的位置
         */
        void onItemSelected(WheelPicker picker, Object data, int position);
    }

    /**
     * 滚轮选择器滚动时监听接口
     *
     * @author AigeStudio 2016-06-17
     *         新项目结构
     *         <p>
     *         New project structure
     * @since 2016-06-17
     */
    public interface OnWheelChangeListener {
        /**
         * 当滚轮选择器滚动时回调该方法
         * 滚轮选择器滚动时会将当前滚动位置与滚轮初始位置之间的偏移距离返回，该偏移距离有正负之分，正值表示
         * 滚轮正在往上滚动，负值则表示滚轮正在往下滚动
         * <p>
         * Invoke when WheelPicker scroll stopped
         * WheelPicker will return a distance offset which between current scroll position and
         * initial position, this offset is a positive or a negative, positive means WheelPicker is
         * scrolling from bottom to top, negative means WheelPicker is scrolling from top to bottom
         *
         * @param offset 当前滚轮滚动距离上一次滚轮滚动停止后偏移的距离
         *               <p>
         *               Distance offset which between current scroll position and initial position
         */
        void onWheelScrolled(int offset);

        /**
         * 当滚轮选择器停止后回调该方法
         * 滚轮选择器停止后会回调该方法并将当前选中的数据项在数据列表中的位置返回
         * <p>
         * Invoke when WheelPicker scroll stopped
         * This method will be called when WheelPicker stop and return current selected item data's
         * position in list
         *
         * @param position 当前选中的数据项在数据列表中的位置
         *                 <p>
         *                 Current selected item data's position in list
         */
        void onWheelSelected(int position);

        /**
         * 当滚轮选择器滚动状态改变时回调该方法
         * 滚动选择器的状态总是会在静止、拖动和滑动三者之间切换，当状态改变时回调该方法
         * <p>
         * Invoke when WheelPicker's scroll state changed
         * The state of WheelPicker always between idle, dragging, and scrolling, this method will
         * be called when they switch
         *
         * @param state 滚轮选择器滚动状态，其值仅可能为下列之一
         *              {@link WheelPicker#SCROLL_STATE_IDLE}
         *              表示滚动选择器处于静止状态
         *              {@link WheelPicker#SCROLL_STATE_DRAGGING}
         *              表示滚动选择器处于拖动状态
         *              {@link WheelPicker#SCROLL_STATE_SCROLLING}
         *              表示滚动选择器处于滑动状态
         *              <p>
         *              State of WheelPicker, only one of the following
         *              {@link WheelPicker#SCROLL_STATE_IDLE}
         *              Express WheelPicker in state of idle
         *              {@link WheelPicker#SCROLL_STATE_DRAGGING}
         *              Express WheelPicker in state of dragging
         *              {@link WheelPicker#SCROLL_STATE_SCROLLING}
         *              Express WheelPicker in state of scrolling
         */
        void onWheelScrollStateChanged(int state);
    }
}