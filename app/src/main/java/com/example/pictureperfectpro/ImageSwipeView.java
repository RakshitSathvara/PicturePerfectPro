package com.example.pictureperfectpro;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.pictureperfectpro.R.drawable;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.R.integer;
import android.R.layout;
import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.Image;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ImageSwipeView extends Activity implements OnPageChangeListener {
	
	ArrayList<Photo> lstPhoto;
	
	TextView photoTitle,likes,favs,shares,camera,aperture,shutter,iso,focalLength,dateTaken,lens,provider,description;
	
	ImageButton btnFav,btnLike,btnShare,btnAddToCollection,btnShowPhotoDetails;
	
	String sesToken;
	
	ViewPager viewPager;
	
	LinearLayout ll;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_image_swipe_view);	
		
		Typeface segoUI=Typeface.createFromAsset(getResources().getAssets(), "fonts/segoeui.ttf");
		
		Bundle b=getIntent().getExtras();
		lstPhoto=b.getParcelableArrayList("photoList");
		
		photoTitle=(TextView) findViewById(R.id.txt_photoTitle);
		likes=(TextView) findViewById(R.id.lbl_like);
		favs=(TextView) findViewById(R.id.lbl_fav);
		shares=(TextView) findViewById(R.id.lbl_share);
		camera=(TextView) findViewById(R.id.lbl_camera);
		aperture=(TextView) findViewById(R.id.lbl_aperture);
		shutter=(TextView) findViewById(R.id.lbl_shutter);
		iso=(TextView) findViewById(R.id.lbl_iso);
		focalLength=(TextView) findViewById(R.id.lbl_focalLength);
		dateTaken=(TextView) findViewById(R.id.lbl_dateTaken);
		lens=(TextView) findViewById(R.id.lbl_lens);
		provider=(TextView) findViewById(R.id.lbl_provider);
		description=(TextView) findViewById(R.id.lbl_description);
		
		ll=(LinearLayout) findViewById(R.id.linearLayout_photoDetails);
		
		btnFav=(ImageButton) findViewById(R.id.imageButton_fav);
		btnLike=(ImageButton) findViewById(R.id.imageButton_like);
		btnShare=(ImageButton) findViewById(R.id.btn_share);
		btnAddToCollection=(ImageButton) findViewById(R.id.btn_addToCollection);
		btnShowPhotoDetails=(ImageButton) findViewById(R.id.btn_showPhotoDetails);
		
		photoTitle.setText(lstPhoto.get(Integer.parseInt(getIntent().getStringExtra("index"))).name.toString());
		photoTitle.setTypeface(segoUI);
		likes.setText(lstPhoto.get(Integer.parseInt(getIntent().getStringExtra("index"))).like_count.toString());
		likes.setTypeface(segoUI);
		favs.setText(lstPhoto.get(Integer.parseInt(getIntent().getStringExtra("index"))).favorite_count.toString());
		favs.setTypeface(segoUI);
		shares.setText(lstPhoto.get(Integer.parseInt(getIntent().getStringExtra("index"))).share_count.toString());
		shares.setTypeface(segoUI);
		
		sesToken=getIntent().getStringExtra("tkn");
		
		viewPager=(ViewPager) findViewById(R.id.view_pager);
		ImageAdapter adapter=new ImageAdapter(this, lstPhoto);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(Integer.parseInt(getIntent().getStringExtra("index")));
		viewPager.setOnPageChangeListener(this);
		
		
		btnAddToCollection.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				Intent i=new Intent(v.getContext(),AddPhotoToCollection.class);
				i.putExtra("sestoken", sesToken);
				i.putExtra("pid", lstPhoto.get(viewPager.getCurrentItem()).photo_id);
				startActivity(i);				
			}
		});
		
		
		btnLike.setOnClickListener(new View.OnClickListener() {
			
			Context context;
			
			@Override
			public void onClick(View v)
			{
				context=v.getContext();
				
				try
				{
					//Toast.makeText(context, String.valueOf(viewPager.getCurrentItem()), Toast.LENGTH_LONG).show();
					//Toast.makeText(context, lstPhoto.get(viewPager.getCurrentItem()).photo_id.toString(), Toast.LENGTH_LONG).show();
					//Toast.makeText(context, viewPager.getCurrentItem(), Toast.LENGTH_LONG).show();
					String data=new PPP(context).execute("3",lstPhoto.get(viewPager.getCurrentItem()).photo_id,sesToken).get();
					Response response=new Response(data);
					//Toast.makeText(context, response.getStatus().toString(), Toast.LENGTH_LONG).show();
					if(TextUtils.equals(response.getStatus(), "1"))
					{						
						btnLike.setImageResource(R.drawable.liked);
					}
					else
					{
						btnLike.setImageResource(R.drawable.like);
					}
					lstPhoto.get(viewPager.getCurrentItem()).setLike_flag(response.getStatus());
					lstPhoto.get(viewPager.getCurrentItem()).setLike_count(response.getComment());
					likes.setText(response.getComment());
				}
				catch (Exception e)
				{
					Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		
		
		btnFav.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				
				Context context=v.getContext();
				
				try
				{
					//Toast.makeText(context, viewPager.getCurrentItem(), Toast.LENGTH_LONG).show();
					//Toast.makeText(context, lstPhoto.get(viewPager.getCurrentItem()).photo_id, Toast.LENGTH_LONG).show();
					String data=new PPP(context).execute("4",lstPhoto.get(viewPager.getCurrentItem()).photo_id,sesToken).get();
					Response response=new Response(data);
					
					if(TextUtils.equals(response.getStatus(), "1"))
					{
						btnFav.setImageResource(R.drawable.fav);
					}
					else
					{
						btnFav.setImageResource(R.drawable.unfav);
					}
					lstPhoto.get(viewPager.getCurrentItem()).setFav_flag(response.getStatus());
					lstPhoto.get(viewPager.getCurrentItem()).setFavorite_count(response.getComment());
					favs.setText(response.getComment());
				}
				catch (Exception e)
				{
					Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
				}
			
				
			}
		});
		
		
		btnShowPhotoDetails.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				if(ll.getVisibility()==View.INVISIBLE)
				{
					viewPager.setEnabled(false);
					ll.setVisibility(View.VISIBLE);
				}
				else
				{
					ll.setVisibility(View.INVISIBLE);
					viewPager.setEnabled(true);
				}
				
				
			}
		});
		
		
	}
	
	@Override
	public void onBackPressed()
	{
		
		Intent intent=new Intent();
		Bundle b=new Bundle();
		b.putParcelableArrayList("photoListBack", lstPhoto);
		intent.putExtras(b);
		setResult(Activity.RESULT_OK,intent);
		finish();
	}
		


	@Override
	public void onPageScrollStateChanged(int arg0) {
		
		
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
		//Toast.makeText(this, lstPhoto.get(arg0).like_flag.toString(), Toast.LENGTH_LONG).show();
		
		if(TextUtils.equals(lstPhoto.get(arg0).fav_flag,"1"))
		{
			btnFav.setImageResource(R.drawable.fav);
		}
		else
		{
			btnFav.setImageResource(R.drawable.unfav);
		}
		
		if(TextUtils.equals(lstPhoto.get(arg0).like_flag,"1"))
		{
			btnLike.setImageResource(R.drawable.liked);
		}
		else
		{
			btnLike.setImageResource(R.drawable.like);
		}
		
		photoTitle.setText(lstPhoto.get(arg0).name.toString());
		likes.setText(lstPhoto.get(arg0).like_count.toString());
		favs.setText(lstPhoto.get(arg0).favorite_count.toString());
		shares.setText(lstPhoto.get(arg0).share_count.toString());
		
		camera.setText(lstPhoto.get(arg0).camera_model);
		aperture.setText(lstPhoto.get(arg0).aperture);
		shutter.setText(lstPhoto.get(arg0).shutter);
		iso.setText(lstPhoto.get(arg0).iso);
		focalLength.setText(lstPhoto.get(arg0).focal_length);
		dateTaken.setText(lstPhoto.get(arg0).datetime);
		lens.setText(lstPhoto.get(arg0).lens);
		provider.setText(lstPhoto.get(arg0).provider);
		description.setText(lstPhoto.get(arg0).description);
		
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
}


class ImageAdapter extends PagerAdapter
{
	
	ImageView imageView;
	
	Context context;
	
	com.example.pictureperfectpro.ImageLoader imageLoader;
	
	ArrayList<Photo> lstPhoto;

	ImageAdapter(Context context,ArrayList<Photo> lstPhoto)
	{
		this.context=context;
		this.lstPhoto=lstPhoto;
		
		imageLoader=new com.example.pictureperfectpro.ImageLoader(context);
	}
	
	
	@Override
	public int getCount()
	{
		return lstPhoto.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		return arg0==((ImageView) arg1);
	}
	
	
	
	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		
		//return super.instantiateItem(container, position);
		
		
		
		imageView=new ImageView(context);
		
		//int padding = context.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
		//imageView.setPadding(padding, padding, padding, padding);
		
		imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		
		String src="";
		
		if(lstPhoto.get(position).src.contains("2.jpg"))
		{
			src= lstPhoto.get(position).src.replace("2.jpg", "4.jpg");
		}
		
		if(lstPhoto.get(position).src.contains("_q.jpg"))
		{
			src= lstPhoto.get(position).src.replace("_q.jpg", ".jpg");
		}
		
		
		imageLoader.DisplayImage(src, imageView);
		
		
		((ViewPager) container).addView(imageView,0);
		
		return imageView;
		
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		
		//super.destroyItem(container, position, object);
		
		((ViewPager) container).removeView((ImageView) object);
	}
	

	
	
}
