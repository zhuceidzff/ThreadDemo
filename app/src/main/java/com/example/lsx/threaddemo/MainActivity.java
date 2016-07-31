package com.example.lsx.threaddemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageView mImageView;
    private Button mLoadImageButton;
    private Button mToastButton;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.activity_main_image_view);
        mLoadImageButton = (Button) findViewById(R.id.activity_main_load_image_button);
        mToastButton = (Button) findViewById(R.id.activity_main_toast_button);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_main_progress_bar);

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
    class LoadImageTask extends AsyncTask<Void,Integer,Bitmap>{
        @Override
        protected void onPreExecute() {
            mImageView.setImageBitmap(null);
            mProgressBar.setProgress(0);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            //在子线程执行
            Log.d(TAG, "doInBackground: "+Thread.currentThread().getName());
            for (int i = 1; i < 11; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "进度: "+i);
                publishProgress(i * 10);
            }
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mProgressBar.setVisibility(View.INVISIBLE);
            mImageView.setImageBitmap(bitmap);
            //在主线程执行
            Log.d(TAG, "onPostExecute: "+Thread.currentThread().getName());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressBar.setProgress(values[0]);
        }
    }
}
