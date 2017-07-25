package com.ifkbhit.parktronic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by vasyoid on 22.07.17.
 */

public class panel1 extends Panel {

    private Bitmap[] digits = new Bitmap[10];
    private Texture line;
    private Texture[][] texPanels = new Texture[4][4];
    private Texture[] texDig = new Texture[10];

    private Bitmap getScaledBitmap(int id, double w, double h) {
        Bitmap b = Bitmap.createScaledBitmap(getBitmap(id), (int) w, (int) h, false);
        return b;
    }

    panel1(int cnvW, int cnvH, Resources res, boolean isReverse) {
        this.res = res;
        reversable = true;
        reverse = isReverse;
        Bitmap panelBitmap = BitmapFactory.decodeResource(res, R.drawable.panel_0);

        double k = (1.0 - Config.CAR_Y_OFFSET_K) * 120.0 / Config.CAR_H;
        h = cnvH * k;
        w = panelBitmap.getWidth() * h / panelBitmap.getHeight();

        panel = new Texture(Bitmap.createScaledBitmap(panelBitmap, (int) w, (int) h, false));
        panel.setPos(new Point((cnvW - panel.img.getWidth()) / 2.0, cnvH * (Config.CAR_Y_OFFSET_K / 2) + ((1 - Config.CAR_Y_OFFSET_K) * cnvH) / 2.0 - panel.img.getWidth() / 16.0));
        panel.setScaled(1.3);

        digits[0] = getBitmap(R.drawable.dig_0);
        digits[1] = getBitmap(R.drawable.dig_1);
        digits[2] = getBitmap(R.drawable.dig_2);
        digits[3] = getBitmap(R.drawable.dig_3);
        digits[4] = getBitmap(R.drawable.dig_4);
        digits[5] = getBitmap(R.drawable.dig_5);
        digits[6] = getBitmap(R.drawable.dig_6);
        digits[7] = getBitmap(R.drawable.dig_7);
        digits[8] = getBitmap(R.drawable.dig_8);
        digits[9] = getBitmap(R.drawable.dig_9);

        for (int i = 0; i < 10; i++) {
            double kh = 0.9 * 98.0 / 174.0;
            double kw = 0.9 * 65.0 / 600.0;
            double w = panel.img.getWidth() * kw;
            double h = panel.img.getHeight() * kh;
            if (line == null) {
                line = new Texture(Bitmap.createScaledBitmap(getBitmap(R.drawable.line), (int) w, (int) h, true));
            }
            texDig[i] = new Texture(Bitmap.createScaledBitmap(digits[i], (int) w, (int) h, true));
            digits[i] = null;
        }

        k = panel.img.getHeight() / 174.0;

        Point position = new Point(84 * k, 68 * k).sum(panel.pos);
        texPanels[0][0] = new Texture(getScaledBitmap(R.drawable.left_small_0, 71 * k,(70 * k)), position, k);
        texPanels[0][1] = new Texture(getScaledBitmap(R.drawable.left_small_1, 71 * k,(70 * k)), position, k);
        texPanels[0][2] = new Texture(getScaledBitmap(R.drawable.left_small_2, 71 * k,(70 * k)), position, k);
        texPanels[0][3] = new Texture(getScaledBitmap(R.drawable.left_small_3, 71 * k, (70 * k)),position, k);

        position = new Point(157 * k, 50 * k).sum(panel.pos);
        texPanels[1][0] = new Texture(getScaledBitmap(R.drawable.left_big_0, 72 * k,(88 * k)),position , k);
        texPanels[1][1] = new Texture(getScaledBitmap(R.drawable.left_big_1, 72 * k,(88 * k)), position, k);
        texPanels[1][2] = new Texture(getScaledBitmap(R.drawable.left_big_2, 72 * k,(88 * k)), position, k);
        texPanels[1][3] = new Texture(getScaledBitmap(R.drawable.left_big_3, 72 * k, (88 * k)),position, k);

        position = new Point(370 * k, 50 * k).sum(panel.pos);
        texPanels[2][0] = new Texture(getScaledBitmap(R.drawable.right_big_0, 72 * k,(88 * k)),position , k);
        texPanels[2][1] = new Texture(getScaledBitmap(R.drawable.right_big_1, 72 * k, (88 * k)),position, k);
        texPanels[2][2] = new Texture(getScaledBitmap(R.drawable.right_big_2, 72 * k,(88 * k)), position, k);
        texPanels[2][3] = new Texture(getScaledBitmap(R.drawable.right_big_3, 72 * k,(88 * k)), position, k);

        position = new Point(445 * k, 68 * k).sum(panel.pos);
        texPanels[3][0] = new Texture(getScaledBitmap(R.drawable.right_small_0, 71 * k,(70 * k)), position, k);
        texPanels[3][1] = new Texture(getScaledBitmap(R.drawable.right_small_1, 71 * k,(70 * k)), position, k);
        texPanels[3][2] = new Texture(getScaledBitmap(R.drawable.right_small_2, 71 * k,(70 * k)), position, k);
        texPanels[3][3] = new Texture(getScaledBitmap(R.drawable.right_small_3, 71 * k,(70 * k)), position, k);

        panelBitmap = BitmapFactory.decodeResource(res, R.drawable.panel_1_empty);

        k = (1.0 - Config.CAR_Y_OFFSET_K) * 360.0 / Config.CAR_H;
        double lh = cnvH * k;
        double lw = panelBitmap.getWidth() * lh / panelBitmap.getHeight();
        double rh = lh, rw = lw;

        l_panel = new Texture(Bitmap.createScaledBitmap(panelBitmap, (int) lw, (int) lh, false));
        l_panel.setPos(new Point(-lw, cnvH * 0.45));
        r_panel = new Texture(l_panel.img);
        r_panel.setPos(new Point(cnvW, cnvH * 0.45));

    }

    private void drawNum(Canvas canvas) {
        Texture left, right;
        if (cur_l == -1 || cur_r == -1) {
            left = right = line;
        } else {
            left = texDig[cur_l];
            right = texDig[cur_r];
        }
        Point dotCenter = panel.getCenter();
        Point posLeft = new Point(dotCenter.x - left.img.getWidth() * 1.15, (dotCenter.y - (left.img.getHeight()) / 2));
        left.setPos(posLeft);
        left.draw(canvas);
        Point posRight = new Point(dotCenter.x + left.img.getWidth() * 0.15, (dotCenter.y - (left.img.getHeight()) / 2));
        right.setPos(posRight);
        right.draw(canvas);
        Paint dotPaint = new Paint();
        dotPaint.setARGB(255, 240, 80, 32);
        canvas.drawCircle((float)dotCenter.x, (float)dotCenter.y + (float)(texDig[0].h * 0.45), (float)(texDig[0].h * 0.06), dotPaint);
    }

    private void drawBars(Canvas canvas) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < 4; ++j) {
                texPanels[i][j].xPos = panel.xPos;
            }
            int lvl = state[i];
            if (lvl != 0) {
                texPanels[i][4 - lvl].draw(canvas);
            }
        }
    }

    void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(panelAngle, (float)panel.getCenter().x, (float)panel.getCenter().y);
        if (reverse) {
            canvas.save();
                canvas.rotate(180, (float) panel.getCenter().x, (float) panel.getCenter().y);
                panel.draw(canvas);
                canvas.scale(-1f, 1f, (float) panel.getCenter().x, (float) panel.getCenter().y);
                drawBars(canvas);
            canvas.restore();
            drawNum(canvas);
        }
        else {
            panel.draw(canvas);
            drawNum(canvas);
            drawBars(canvas);
        }
        canvas.restore();
        if (panel.xPos > 0.1) {
            l_panel.xPos = panel.xPos;
            l_panel.draw(canvas);
        }
        else if (panel.xPos < -0.1) {
            r_panel.xPos = panel.xPos;
            r_panel.draw(canvas);
        }
    }

    int getLevel(double val, boolean isUp) {
        if (isUp) {
            double MAX = 0.9;
            if (between(val, 0, MAX / 4.0)) {
                return 4;
            }
            if(between(val, MAX / 4,MAX / 2)) {
                return 3;
            }
            if(between(val, MAX / 2, MAX * 0.75)) {
                return 2;
            }
            if(between(val, MAX * 0.75, MAX)) {
                return 1;
            }
        }
        else {
            double MAX = 1.1;
            if (between(val, 0, MAX / 4.0)) {
                return 4;
            }
            if (between(val, MAX / 4, MAX / 2)) {
                return 3;
            }
            if (between(val, MAX / 2, MAX * 0.75)) {
                return 2;
            }
            if (between(val, MAX * 0.75, MAX)) {
                return 1;
            }
        }
        return 0;
    }

}