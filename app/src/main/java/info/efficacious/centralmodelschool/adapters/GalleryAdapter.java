package info.efficacious.centralmodelschool.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import info.efficacious.centralmodelschool.Interface.DataService;
import info.efficacious.centralmodelschool.R;
import info.efficacious.centralmodelschool.activity.MainActivity;
import info.efficacious.centralmodelschool.dialogbox.image_zoom_dialog;
import info.efficacious.centralmodelschool.entity.SchoolDetail;
import info.efficacious.centralmodelschool.fragment.Gallery_fragment;
import info.efficacious.centralmodelschool.webApi.RetrofitInstance;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private ArrayList<SchoolDetail> data;
    int height=100,width=100;
    Context mcontext;
    String school_id;
    private ProgressDialog progress;
    SharedPreferences settings;
    private static final String PREFRENCES_NAME = "myprefrences";
    public GalleryAdapter(ArrayList<SchoolDetail> dataList,Context context) {
        data = dataList;
        Log.d("CMSSSSSSSS",""+data.size());
        mcontext=context;
    }

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_adapter,null);
        ViewHolder viewHolder=new ViewHolder(itemLayoutView);

        return viewHolder;
    }



    public void onBindViewHolder(final ViewHolder holder, int position) {
        try
        {
            settings = mcontext.getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
            school_id=settings.getString("TAG_SCHOOL_ID", "");
            String url= RetrofitInstance.Image_URL+data.get(position).getName();
            Log.d("TAG","onBindViewHolderURL"+url);


            Glide.with(mcontext)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .dontAnimate()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                            //Log.d(TAG,e.getLocalizedMessage(),e);
                            holder.rl_image.setVisibility(View.GONE);
                            holder.eventDescriptiontv.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(holder.image);

//                Glide.with(mcontext)
//                        .load(url)
//                        .fitCenter()// image url
//                        .error(R.mipmap.profile)
//                        .into(holder.image);


            setImageMethod(RetrofitInstance.Image_URL,data.get(position).getName(),holder.image);
            holder.eventDescriptiontv.setText(data.get(position).getEventDescription());

           holder.image.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent=new Intent(mcontext,image_zoom_dialog.class);
                   intent.putExtra("path",data.get(position).getName());
                   mcontext.startActivity(intent);
               }
           });
        }catch (Exception ex)
        {

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventDescriptiontv;
        ImageView image ;
        RelativeLayout rl_image;
        public ViewHolder(View itemView) {
            super(itemView);
            eventDescriptiontv=(TextView)itemView.findViewById(R.id.eventDescriptiontv);
            image = (ImageView) itemView.findViewById(R.id.image);
            rl_image = itemView.findViewById(R.id.relative);
        }
    }
    private void setImageMethod(String baseUrl,String subUrl,ImageView view){
        String mainUrl=baseUrl+subUrl;
        Glide.with(mcontext)
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
                        setImageMethod(RetrofitInstance.Image_error_URL,subUrl,view);
                    }
                });
    }
    public void  DeleteAsync(String imageId, String imageName){
        try {
            progress = new ProgressDialog(mcontext);
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.setMessage("loading...");
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<ResponseBody> call = service.delete(imageId,imageName,school_id);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(ResponseBody body) {
                    try {

                    } catch (Exception ex) {
                        progress.dismiss();
                        Toast.makeText(mcontext, " onNext Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    progress.dismiss();
                    Toast.makeText(mcontext, " onError Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {
                    progress.dismiss();
                    try {
                        Toast.makeText(mcontext, "Image Deleted Successfully", Toast.LENGTH_LONG).show();
//                        Gallery_Folder gallery_folder = new Gallery_Folder();
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main,new  Gallery_fragment()).commitAllowingStateLoss();
                    } catch (Exception ex) {
                        ex.getMessage();
                    }
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
            progress.dismiss();
        }
    }
}