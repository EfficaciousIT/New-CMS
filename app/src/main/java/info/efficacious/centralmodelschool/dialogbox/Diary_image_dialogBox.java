package info.efficacious.centralmodelschool.dialogbox;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.common.ConnectionDetector;
import info.efficacious.centralmodelschool.webApi.RetrofitInstance;

public class Diary_image_dialogBox extends AppCompatActivity {
    ImageView imageView;
    ImageView callimage, messageimage, videcallimage;
    String Path;
    ConnectionDetector cd;
    int height=100,width=100;
    boolean flag=false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.zoom_image);
        cd = new ConnectionDetector(getApplicationContext());
        try {
            Intent intent = getIntent();
            if (null != intent) {
                Path = intent.getStringExtra("path");
            }
        } catch (Exception ex) {

        }

        imageView = (ImageView) findViewById(R.id.imageView6);
        callimage = (ImageView) findViewById(R.id.imageView12);
        messageimage = (ImageView) findViewById(R.id.imageView16);
        videcallimage = (ImageView) findViewById(R.id.imageView18);
        callimage.setVisibility(View.GONE);
        messageimage.setVisibility(View.GONE);
        videcallimage.setVisibility(View.GONE);
        try {
            /*String url = RetrofitInstance.Image_URL + Path;
            Glide.with(Diary_image_dialogBox.this)
                    .load(url)
                    .fitCenter()// image url
                    .error(R.mipmap.profile)
                    .into(imageView);*/
            setImageMethod(RetrofitInstance.Image_URL,Path,imageView);
        } catch (Exception ex) {

        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String url="";
                    if (flag)
                    {
                        url=RetrofitInstance.Image_error_URL;
                        callIntent(url);
                    }else {
                        url=RetrofitInstance.Image_URL;
                        callIntent(url);
                    }

                    } catch (Exception ex) {

                }

            }
        });

    }
    private void callIntent(String url){
        String mainurl = url + Path;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mainurl));
        startActivity(browserIntent);
    }
    private void setImageMethod(String baseUrl,String subUrl,ImageView view){
        String mainUrl=baseUrl+subUrl;
        Glide.with(Diary_image_dialogBox.this)
                .load(mainUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(width, height) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        view.setImageBitmap(resource);
                        //handle Bitmap, generate Palette etc.
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // Do something.
                        Log.d("TAG","onLoadFailed plan");
                        flag=true;
                        setImageMethod(RetrofitInstance.Image_error_URL,subUrl,view);
                    }
                });
    }
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}
