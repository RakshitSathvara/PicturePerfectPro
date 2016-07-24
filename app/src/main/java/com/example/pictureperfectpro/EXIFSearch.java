package com.example.pictureperfectpro;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.R.layout;
import android.R.string;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EXIFSearch extends Fragment {
	
	
	TextView txt_apertureValue;
	//Spinner aperture,shutter,iso,camera,dayNight,lens,focalLength,color;
	
	ListView lstEXIF;
	
	String[] apertureArr;
	String[] shutterArr;
	String[] isoArr;
	String[] cameraArr;
	String[] dayNightArr={"ANY","day","night"};
	String[] lensArr;
	String[] focalLengthArr;
	String[] colorArr={"ANY","red","green","blue"};
	
	Integer[] icons={R.drawable.camera,R.drawable.a_low,R.drawable.s_speed,R.drawable.iso,R.drawable.lens,R.drawable.f_length,R.drawable.day_night,R.drawable.pallet};
	
	User user;
	
	EXIFListAdapter adapter;
	
	Button btnSearchEXIF;
	
	EXIFData exifData;
	
	public EXIFSearch(String data)
	{
		try {
			user=new User(data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v=inflater.inflate(R.layout.exif_search_layout, container,false);
		
		//get values for EXIF from webservice
		
		lstEXIF=(ListView) v.findViewById(R.id.lst_exifListView);
		
		btnSearchEXIF=(Button) v.findViewById(R.id.btn_exifSearch);

		
		try {
			String result=new PPP(getActivity()).execute("5",user.getSesToken()).get();
			exifData=new EXIFData(result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e){
			
		}
		
		
		
		cameraArr=exifData.getCamera();
		apertureArr=exifData.getAperture();
		shutterArr=exifData.getShutter();
		isoArr=exifData.getIso();
		lensArr=exifData.getLens();
		focalLengthArr=exifData.getFocalLength();
		
		DataHolder data = new DataHolder(getActivity(),cameraArr,0);
        DataHolder data1 = new DataHolder(getActivity(),apertureArr,1);
        DataHolder data2 = new DataHolder(getActivity(),shutterArr,2);
        DataHolder data3 = new DataHolder(getActivity(),isoArr,3);
        DataHolder data4 = new DataHolder(getActivity(),lensArr,4);
        DataHolder data5 = new DataHolder(getActivity(),focalLengthArr,5);
        DataHolder data6 = new DataHolder(getActivity(),dayNightArr,6);
        DataHolder data7 = new DataHolder(getActivity(),colorArr,7);


		//EXIF exif=new EXIF(cameraArr,apertureArr,shutterArr,isoArr,lensArr,focalLengthArr,dayNightArr,colorArr);
		
		adapter=new EXIFListAdapter(getActivity(), icons,new DataHolder[]{data,data1,data2,data3,data4,data5,data6,data7});
		
		lstEXIF.setAdapter(adapter);
		
		btnSearchEXIF.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i=new Intent(getActivity(),QuickSearch.class);
				i.putExtra("sestoken",user.getSesToken());
				i.putExtra("keyword", "");
				i.putExtra("camera", adapter.camera);
				i.putExtra("aperture", adapter.aperture);
				i.putExtra("shutter", adapter.shutter);
				i.putExtra("iso", adapter.iso);
				i.putExtra("lens", adapter.lens);
				i.putExtra("focal_length", adapter.focalLength);
				i.putExtra("day_night", adapter.dayNight);
				i.putExtra("color", adapter.color);
		        startActivity(i);
				/*Toast.makeText(getActivity(), adapter.camera, Toast.LENGTH_SHORT).show();
				Toast.makeText(getActivity(), adapter.aperture, Toast.LENGTH_SHORT).show();
				Toast.makeText(getActivity(), adapter.shutter, Toast.LENGTH_SHORT).show();
				Toast.makeText(getActivity(), adapter.iso, Toast.LENGTH_SHORT).show();
				Toast.makeText(getActivity(), adapter.lens, Toast.LENGTH_SHORT).show();
				Toast.makeText(getActivity(), adapter.focalLength, Toast.LENGTH_SHORT).show();
				Toast.makeText(getActivity(), adapter.dayNight, Toast.LENGTH_SHORT).show();
				Toast.makeText(getActivity(), adapter.color, Toast.LENGTH_SHORT).show();*/
				
			}
		});
		
		return v;
		
	}

}

class EXIF
{
	ArrayList<String[]> exifItemList;
	String[] apertureArr;
	String[] shutterArr;
	String[] isoArr;
	String[] cameraArr;
	String[] dayNightArr;
	String[] lensArr;
	String[] focalLengthArr;
	String[] colorArr;
	
	
	EXIF(String[] cameraArr,String[] apertureArr,String[] shutterArr,String[] isoArr,String[] lensArr, String[] focalLengthArr,String[] dayNightArr,String[] colorArr)
	{
		this.apertureArr=apertureArr;
		this.shutterArr=shutterArr;
		this.isoArr=isoArr;
		this.cameraArr=cameraArr;
		this.dayNightArr=dayNightArr;
		this.lensArr=lensArr;
		this.focalLengthArr=focalLengthArr;
		this.colorArr=colorArr;
		exifItemList=new ArrayList<String[]>();
		
		exifItemList.add(cameraArr);
		exifItemList.add(apertureArr);
		exifItemList.add(shutterArr);
		exifItemList.add(isoArr);
		exifItemList.add(lensArr);
		exifItemList.add(focalLengthArr);
		exifItemList.add(dayNightArr);		
		exifItemList.add(colorArr);
	}
}


/*class EXIFListAdapter extends ArrayAdapter<Integer>
{
	Context context;
	EXIF exif;
	
	Integer[] icons;
	
	EXIFListAdapter(Context context,EXIF exif,Integer[] icons)
	{
		super(context, R.layout.exif_list_item,R.id.img_exifIcon,icons);
		this.context=context;
		this.exif=exif;
		this.icons=icons;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflator=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row=inflator.inflate(R.layout.exif_list_item, parent,false);
		
		ImageView icon=(ImageView) row.findViewById(R.id.img_exifIcon);
		Spinner spinner=(Spinner) row.findViewById(R.id.cmb_exifValues);
		
		
		icon.setImageResource(icons[position]);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,exif.exifItemList.get(position));
		spinner.setAdapter(adapter);

		
		return row;
		
	}


}*/


class EXIFListAdapter extends ArrayAdapter<DataHolder>
{
	Context context;
	//EXIF exif;
	
	Integer[] icons;
	
	int index;
	
	String camera,aperture,shutter,iso,lens,focalLength,dayNight,color;
	
	DataHolder[] objects;
	
	EXIFListAdapter(Context context,Integer[] icons,DataHolder[] objects)
	{
		super(context, R.layout.exif_list_item,objects);
		this.context=context;
		//this.exif=exif;
		this.icons=icons;
		this.objects=objects;
		/*btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				((EXIFSearch) context)
				
				Intent i=new Intent(context.,QuickSearch.class);
				i.putExtra("sestoken",user.sesToken);
				i.putExtra("keyword", "");
				i.putExtra("camera", camera);
				i.putExtra("aperture", aperture);
				i.putExtra("shutter", shutter);
				i.putExtra("iso", iso);
				i.putExtra("lens", lens);
				i.putExtra("focal_length", focalLength);
				i.putExtra("day_night", dayNight);
				i.putExtra("color", color);
		        startActivity(i);
				
			}
		});*/
	}
	
	
	static class ViewHolder {
        protected DataHolder data;
        protected Spinner spin;
    }
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		index=position;
		
		View row=null;
		
		if(convertView==null)
		{
		
			LayoutInflater inflator=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row=inflator.inflate(R.layout.exif_list_item, parent,false);
			
			final ViewHolder viewHolder=new ViewHolder();
			viewHolder.data = objects[position];
			
			ImageView icon=(ImageView) row.findViewById(R.id.img_exifIcon);
			viewHolder.spin=(Spinner) row.findViewById(R.id.cmb_exifValues);
			
			viewHolder.spin.setAdapter(viewHolder.data.getAdapter());
			
			viewHolder.spin.setOnItemSelectedListener(new OnItemSelectedListener() {
	
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					viewHolder.data.setSelected(arg2);
					
					Integer x=viewHolder.data.getIndex();
					
	                //Toast.makeText(context, x.toString(), Toast.LENGTH_LONG).show();
	                
	                if(x==0)
	                {
	                	camera=viewHolder.data.getText();
	                }
	                if(x==1)
	                {
	                	aperture=viewHolder.data.getText();
	                }
	                if(x==2)
	                {
	                	shutter=viewHolder.data.getText();
	                }
	                if(x==3)
	                {
	                	iso=viewHolder.data.getText();
	                }
	                if(x==4)
	                {
	                	lens=viewHolder.data.getText();
	                }
	                if(x==5)
	                {
	                	focalLength=viewHolder.data.getText();
	                }
	                if(x==6)
	                {
	                	dayNight=viewHolder.data.getText();
	                }
	                if(x==7)
	                {
	                	color=viewHolder.data.getText();
	                }
					
				}
	
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			
			icon.setImageResource(icons[position]);
			//ArrayAdapter<String> adapter=new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,exif.exifItemList.get(position));
			//spinner.setAdapter(adapter);
	
			row.setTag(viewHolder);
			
			
		
		}
		else
		{
            row = convertView;
        }

		ViewHolder holder = (ViewHolder) row.getTag();
        
        holder.spin.setSelection(getItem(position).getSelected());

        return row;

}
	
}


class DataHolder {
	
	

    private int selected;
    private ArrayAdapter<String> adapter;
    private int index;

    public DataHolder(Context parent, String[] values,int index) {
    	this.index=index;
        adapter = new ArrayAdapter<String>(parent, android.R.layout.simple_spinner_dropdown_item,values);
        
    }

    public ArrayAdapter<String> getAdapter() {
        return adapter;
    }

    public String getText() {
        return (String) adapter.getItem(selected);
        
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
    
    public int getIndex()
    {
    	return index;
    }

}


