package com.zii.higuide;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.zii.higuide.Overlay.HightLight;
import com.zii.higuide.Overlay.Tips;

/**
 * Create By Zii at 2018/5/3.
 */
class GuideView extends FrameLayout {

  private Overlay mOverlay;
  private Paint mPaint;
  private Path mBgPath;
  private Path mShapePath;
  private RemoveCallback mRemoveCallback;

  public GuideView(Context context, Overlay overlay) {
    super(context);
    mOverlay = overlay;
    init();
  }

  private void init() {
    final int bgColor = mOverlay.getColorBg();

    mBgPath = new Path();
    mShapePath = new Path();

    mPaint = new Paint();
    mPaint.setColor(bgColor);
    mPaint.setDither(true);
    mPaint.setAntiAlias(true);

    setWillNotDraw(false);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    mBgPath.reset();
    mShapePath.reset();

    mBgPath.addRect(0, 0, getWidth(), getHeight(), Path.Direction.CW);

    for (HightLight hightLight : mOverlay.getHightLightList()) {
      Path shapePath = calcHightLightShapePath(hightLight, mShapePath);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        mBgPath.op(shapePath, Path.Op.XOR);
      }
    }

    canvas.drawPath(mBgPath, mPaint);
  }

  private Path calcHightLightShapePath(HightLight hightLight, Path shapePath) {
    shapePath.reset();
    switch (hightLight.getShape()) {
      case HiGuide.SHAPE_CIRCLE:
        shapePath.addCircle(hightLight.getRectF().centerX(),
            hightLight.getRectF().centerY(),
            hightLight.getRadius(), Path.Direction.CW);
        break;
      case HiGuide.SHAPE_OVAL:
        shapePath.addOval(hightLight.getRectF(), Path.Direction.CW);
        break;
      case HiGuide.SHAPE_RECT:
        shapePath.addRect(hightLight.getRectF(), Path.Direction.CW);
        break;
      default:
        break;
    }
    return shapePath;
  }

  public void addTipsViews() {
    for (HightLight hightLight : mOverlay.getHightLightList()) {

      if (hightLight != null
          && hightLight.getTips() != null
          && hightLight.getTips().layoutRes != -1) {

        addTipsView(hightLight.getTips());
      }
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_UP) {
      //点击高亮
      if (mOverlay.getOnClickHightLightListener() != null) {
        float x = event.getX();
        float y = event.getY();
        for (RectF rectF : mOverlay.getHightLightAreas()) {
          if (rectF.contains(x, y)) {
            mOverlay.getOnClickHightLightListener().onClick(this);
            remove();
            return super.onTouchEvent(event); //点击高亮后不做其他的处理了，直接返回
          }
        }
      }
      //全局点击
      if (mOverlay.getOnClickGuideViewListener() != null) {
        mOverlay.getOnClickGuideViewListener().onClick(this);
      }
      if (mOverlay.isTouchDismiss()) {
        remove();
      }
    }

    return true;
  }

  private void remove() {
    ViewGroup parent = (ViewGroup) getParent();
    if (parent instanceof RelativeLayout || parent instanceof FrameLayout) {
      parent.removeView(this);
    } else {
      parent.removeView(this);
      View origin = parent.getChildAt(0);
      ViewGroup graParent = (ViewGroup) parent.getParent();
      graParent.removeView(parent);
      graParent.addView(origin, parent.getLayoutParams());
    }

    if (mRemoveCallback != null) {
      mRemoveCallback.callback();
    }
  }

  public void addTipsView(Tips tips) {
    View tipsView = LayoutInflater.from(getContext()).inflate(tips.layoutRes, this, false);

    LayoutParams lp = (LayoutParams) tipsView.getLayoutParams();

    lp.leftMargin = tips.leftMargin;
    lp.topMargin = tips.topMargin;
    lp.rightMargin = tips.rightMargin;
    lp.bottomMargin = tips.bottomMargin;

    if (lp.rightMargin != 0) {
      lp.gravity = Gravity.END;
    } else {
      lp.gravity = Gravity.START;
    }
    if (lp.bottomMargin != 0) {
      lp.gravity |= Gravity.BOTTOM;
    } else {
      lp.gravity |= Gravity.TOP;
    }

    tipsView.setLayoutParams(lp);

    addView(tipsView, lp);
  }

  public void setRemoveCallback(RemoveCallback removeCallback) {
    mRemoveCallback = removeCallback;
  }

  public interface RemoveCallback {

    void callback();
  }
}
