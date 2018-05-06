package com.zii.example;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.zii.higuide.GuideUtils;
import com.zii.higuide.HiGuide;
import com.zii.higuide.Overlay;
import com.zii.higuide.Overlay.Tips;
import com.zii.higuide.Overlay.Tips.Margin;

public class MainActivity extends AppCompatActivity {

  private Button mBtn1;
  private Button mBtn2;
  private Button mBtn3;
  private TextView mTv;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mBtn1 = findViewById(R.id.btn_1);
    mBtn2 = findViewById(R.id.btn_2);
    mBtn3 = findViewById(R.id.btn_3);
    mTv = findViewById(R.id.tv);
  }

  public void onShow1(View view) {
    new HiGuide(this)
        .addHightLight(mTv, new int[]{dp2px(8), dp2px(8)}, HiGuide.SHAPE_OVAL, null)
        .touchDismiss(false)
        .addHightLightClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            Toast.makeText(MainActivity.this, "Im Tv", Toast.LENGTH_SHORT).show();
          }
        })
        .nextOverLay(new Overlay().addHightLight(mBtn1, null, HiGuide.SHAPE_RECT, null))
        .nextOverLay(new Overlay()
            .addHightLight(mBtn2, new int[]{dp2px(8), dp2px(8)}, HiGuide.SHAPE_RECT,
                new Tips(R.layout.layout_tips, Tips.TO_LEFT_OF, Tips.ALIGN_BOTTOM, null))
            .addHightLightClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Im Btn 222", Toast.LENGTH_SHORT).show();
              }
            }).bgColor(Color.parseColor("#8000ffff")))
        .nextOverLay(new Overlay()
            .addHightLight(mBtn3, new int[]{dp2px(16), dp2px(16)}, HiGuide.SHAPE_OVAL,
                new Tips(R.layout.layout_tips, Tips.TO_LEFT_OF, Tips.ALIGN_TOP,
                    new Margin(0, dp2px(28), 0, dp2px(28)))))
        .show();

  }

  private int dp2px(float dp) {
    return (int) GuideUtils.dp2px(this, dp);
  }
}
