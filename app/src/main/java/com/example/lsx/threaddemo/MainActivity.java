package com.example.lsx.threaddemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageView mImageView;
    private Button mLoadImageButton;
    private Button mToastButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.activity_main_image_view);
        mLoadImageButton = (Button) findViewById(R.id.activity_main_load_image_button);
        mToastButton = (Button) findViewById(R.id.activity_main_toast_button);

        mToastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Toast message", Toast.LENGTH_SHORT).show();
            }
        });
        mLoadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //方式一
                // loadImage();
                //方式二
                new LoadImageTask().execute();
            }
        });
    }

    /*private void loadImage() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //将ic_launcher转换为Bitmap类型
                final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mImageView.post(new Runnable() {
                    //post,把runable取出来，推到主线程的message queue中，等待Looper发现并执行其中的run()
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(bitmap);
                    }
                });

            }
        }).start();
    }*/
    //使用AsyncTask类的方法实现子线程切换到主线程
    class LoadImageTask extends AsyncTask<Void,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mImageView.setImageBitmap(bitmap);
        }
    }
}
