package com.zii.higuide;

import android.graphics.Color;
import android.graphics.RectF;
import android.support.annotation.LayoutRes;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by Zii at 2018/5/4.
 */
public class Overlay {

  private List<HightLight> mHightLightList;
  private int mColorBg;
  private boolean mIsTouchDismiss;
  private View.OnClickListener mOnClickGuideViewListener;
  private View.OnClickListener mOnClickHightLightListener;
  private List<RectF> mHightLightAreas;

  public Overlay() {
    mHightLightList = new ArrayList<>();
    mColorBg = Color.parseColor("#80000000");
    mIsTouchDismiss = true;
    mHightLightAreas = new ArrayList<>();
  }

  public Overlay touchDismiss(boolean isDismiss) {
    mIsTouchDismiss = isDismiss;
    return this;
  }

  public Overlay bgColor(int color) {
    mColorBg = color;
    return this;
  }

  public Overlay addHightLight(View view, int[] expand, @HiGuide.Shape int shape, Tips tips) {
    HightLight hightLight = new HightLight(view, expand, shape, tips);
    mHightLightList.add(hightLight);
    addHightLightArea(hightLight);
    return this;
  }

  private void addHightLightArea(HightLight info) {
    RectF rectF = info.getRectF();
    if (info.getShape() != HiGuide.SHAPE_CIRCLE) {
      mHightLightAreas.add(rectF);
    } else {
      mHightLightAreas.add(new RectF(rectF.left, rectF.centerY() - info.getRadius(),
          rectF.right, rectF.centerY() + info.getRadius()));
    }
  }

  public Overlay addHightLightClickListener(View.OnClickListener listener) {
    mOnClickHightLightListener = listener;
    return this;
  }

  public boolean isTouchDismiss() {
    return mIsTouchDismiss;
  }

  public Overlay addGuideViewClickListener(View.OnClickListener listener) {
    mOnClickGuideViewListener = listener;
    return this;
  }

  public List<RectF> getHightLightAreas() {
    return mHightLightAreas;
  }

  public List<HightLight> getHightLightList() {
    return mHightLightList;
  }

  public int getColorBg() {
    return mColorBg;
  }

  public View.OnClickListener getOnClickGuideViewListener() {
    return mOnClickGuideViewListener;
  }

  public View.OnClickListener getOnClickHightLightListener() {
    return mOnClickHightLightListener;
  }

  public static class HightLight {

    private View mView;
    private RectF mRectF;
    @HiGuide.Shape
    private int mShape;
    private float mRadius;
    private Tips mTips;

    HightLight(View view, int[] expand, @HiGuide.Shape int shape, Tips tips) {
      mView = view;
      mShape = shape;

      mRectF = new RectF();
      int[] location = new int[2];
      final int[] size = GuideUtils.measureView(mView);
      view.getLocationOnScreen(location);
      if (expand == null) {
        expand = new int[2];
      }
      mRectF.left = location[0] - expand[0];
      mRectF.top = location[1] - expand[1];
      mRectF.right = location[0] + size[0] + expand[0];
      mRectF.bottom = location[1] + size[1] + expand[1];

      mRadius = Math.max((mRectF.right - mRectF.left) / 2, (mRectF.bottom - mRectF.top) / 2);

      mTips = tips;
    }

    public Tips getTips() {
      return mTips;
    }

    public float getRadius() {
      return mRadius;
    }

    public View getView() {
      return mView;
    }

    public RectF getRectF() {
      return mRectF;
    }

    @HiGuide.Shape
    public int getShape() {
      return mShape;
    }

  }

  public static class Tips {

    public int leftMargin;
    public int topMargin;
    public int bottomMargin;
    public int rightMargin;
    public int layoutRes;

    public Tips(@LayoutRes int layoutRes, int leftMargin, int topMargin, int rightMargin,
        int bottomMargin) {
      this.layoutRes = layoutRes;
      this.leftMargin = leftMargin;
      this.topMargin = topMargin;
      this.bottomMargin = bottomMargin;
      this.rightMargin = rightMargin;
    }
  }

}
