package com.zii.higuide;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Create Zii at 2018/5/4.
 */
public class GuideUtils {

  public static int getScreenWidth(Context context) {
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    if (wm == null) {
      return context.getResources().getDisplayMetrics().widthPixels;
    }
    Point point = new Point();
    wm.getDefaultDisplay().getRealSize(point);
    return point.x;
  }

  public static int getScreenHeight(Context context) {
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    if (wm == null) {
      return context.getResources().getDisplayMetrics().heightPixels;
    }
    Point point = new Point();
    wm.getDefaultDisplay().getRealSize(point);
    return point.y;
  }

  public static int[] getScreenSize(Context context) {
    int[] sizes = new int[2];
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    if (wm == null) {
      sizes[0] = context.getResources().getDisplayMetrics().widthPixels;
      sizes[1] = context.getResources().getDisplayMetrics().heightPixels;
      return sizes;
    }
    Point point = new Point();
    wm.getDefaultDisplay().getRealSize(point);
    sizes[0] = point.x;
    sizes[1] = point.y;
    return sizes;
  }

  public static float dp2px(Context context, final float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return dpValue * scale + 0.5f;
  }

  public static int getMeasuredWidth(final View view) {
    return measureView(view)[0];
  }

  public static int getMeasuredHeight(final View view) {
    return measureView(view)[1];
  }

  public static int[] measureView(final View view) {
    ViewGroup.LayoutParams lp = view.getLayoutParams();
    if (lp == null) {
      lp = new ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.WRAP_CONTENT
      );
    }
    int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
    int lpHeight = lp.height;
    int heightSpec;
    if (lpHeight > 0) {
      heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
    } else {
      heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    }
    view.measure(widthSpec, heightSpec);
    return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
  }
}
