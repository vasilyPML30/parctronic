package com.ifkbhit.parktronic;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LOCKED;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        setRequestedOrientation(SCREEN_ORIENTATION_LOCKED);
        final int sysType = getIntent().getIntExtra("sysType", 0);
        findViewById(R.id.inc_216).setVisibility(sysType == 0 ? View.VISIBLE : View.GONE);
        findViewById(R.id.inc_218).setVisibility(sysType == 1 ? View.VISIBLE : View.GONE);
        findViewById(R.id.inc_277).setVisibility(sysType == 2 ? View.VISIBLE : View.GONE);
        int cur_tutorial = getIntent().getIntExtra("curTutorial", -1);
        if (cur_tutorial == 12) {
            ++cur_tutorial;
            findViewById(R.id.fab).setEnabled(false);
            findViewById(R.id.more_button).setEnabled(false);
        }
        else {
            findViewById(R.id.close).setVisibility(View.GONE);
        }
        ((InfoGraphics)findViewById(R.id.info_graphics)).cur_tutorial = cur_tutorial;
        int[] titles = {R.string.info_title_216, R.string.info_title_218, R.string.info_title_277};
        setTitle(getString(titles[sysType]));

        android.widget.Button fab = (android.widget.Button) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(((InfoGraphics)findViewById(R.id.info_graphics)).cur_tutorial);
                finish();
            }
        });
        View.OnClickListener link = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.aviline.ru");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        };
        (findViewById(R.id.site_image_1)).setOnClickListener(link);
        (findViewById(R.id.site_image_2)).setOnClickListener(link);

        android.widget.Button more = (android.widget.Button) findViewById(R.id.more_button);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((android.widget.Button)view).getText() != "Скрыть") {
                    if (((InfoGraphics)findViewById(R.id.info_graphics)).cur_tutorial == 14) {
                        ++((InfoGraphics)findViewById(R.id.info_graphics)).cur_tutorial;
                        findViewById(R.id.fab).setEnabled(true);
                        findViewById(R.id.more_button).setEnabled(false);
                    }
                    ((android.widget.Button)view).setText("Скрыть");
                    if (sysType == 0) {
                        ((TextView) findViewById(R.id.text_216)).setText(getString(R.string.full_216));
                    } else if (sysType == 1) {
                        ((TextView) findViewById(R.id.text_218)).setText(getString(R.string.full_218));
                    } else {
                        ((TextView) findViewById(R.id.text_277)).setText(getString(R.string.full_277));
                    }
                }
                else {
                    ((android.widget.Button)view).setText("Подробнее");
                    if (sysType == 0) {
                        ((TextView) findViewById(R.id.text_216)).setText(getString(R.string.short_216));
                    } else if (sysType == 1) {
                        ((TextView) findViewById(R.id.text_218)).setText(getString(R.string.short_218));
                    } else {
                        ((TextView) findViewById(R.id.text_277)).setText(getString(R.string.short_277));
                    }
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        ((InfoGraphics)findViewById(R.id.info_graphics)).init();
    }

    @Override
    public void onBackPressed() {
        if (((InfoGraphics)findViewById(R.id.info_graphics)).cur_tutorial < 0) {
            setResult(-1);
            super.onBackPressed();
        }
    }
    public void close(View view) {
        ((InfoGraphics)findViewById(R.id.info_graphics)).cur_tutorial = -1;
        findViewById(R.id.fab).setEnabled(true);
        findViewById(R.id.more_button).setEnabled(true);
        findViewById(R.id.inc_216).setAlpha(1.0f);
        findViewById(R.id.inc_218).setAlpha(1.0f);
    }
}
