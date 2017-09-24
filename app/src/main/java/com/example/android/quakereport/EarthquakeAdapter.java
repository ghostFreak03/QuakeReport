package com.example.android.quakereport;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.graphics.drawable.GradientDrawable;
/**
 * Created by Ghost Freak on 18-02-2017.
 */

/**
 * this earthquake adapter is extended from arrayadapter class
 * earthquake adapter is setted up so that it creates list for the listview by using the list.xml layout and arraylist provided in earthquake activity
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    //overriding the default contructor
    private static final String LOCATION_SEPARATOR = " of ";
    public EarthquakeAdapter(Context context, List<Earthquake> earthquakes) {

        super(context,0, earthquakes);

    }
    //Overriding the getview method of Array Adapter class

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listitemView=convertView;

        //check if there is already an list view that can be reused
        //if listview is null inflate a new listitem in listview using this if statement

        if (listitemView==null){
            listitemView= LayoutInflater.from(getContext()).inflate(
             R.layout.list,parent,false);
        }

        //get the earthquake object from arraylist using position and save it in an Earthquake object

         Earthquake earthquake=getItem(position);
        //set the values in the list item by creating each textview an object
        //creating object for text view
        TextView mag=(TextView) listitemView.findViewById(R.id.magnitude);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.

        GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(earthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        //getting the magnitude of current earthquake object and setting it to textview object

        double magn=earthquake.getMagnitude();

        String output=formatMagnitude(magn);
        mag.setText(output);
        //repeat same for other textviews

        TextView location_offset=(TextView) listitemView.findViewById(R.id.location_offset);
        TextView primary_location=(TextView) listitemView.findViewById(R.id.primary_location);
        String originalLocation=earthquake.getPlace();
        String locationOffset;
        String primaryLocation;
        if (originalLocation.contains(LOCATION_SEPARATOR)){
            String[] parts=originalLocation.split(LOCATION_SEPARATOR);
            locationOffset=parts[0]+LOCATION_SEPARATOR;
            primaryLocation=parts[1];
        }
        else{
            locationOffset=getContext().getString(R.string.near_the);
            primaryLocation=originalLocation;
        }
        location_offset.setText(locationOffset);
        primary_location.setText(primaryLocation);

        Date dateObject=new Date(earthquake.getDate());
        String formateddate=formatDate(dateObject);
        TextView date=(TextView) listitemView.findViewById(R.id.date);

        date.setText(formateddate);

        TextView time=(TextView) listitemView.findViewById(R.id.time);
        String formtedtime=formatTime(dateObject);
        time.setText(formtedtime);

        //Now the list item is created...return the list item

        return listitemView;
    }
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
    private String formatMagnitude(double magnitude){
    DecimalFormat formatter = new DecimalFormat("0.0");
    String output = formatter.format(magnitude);
        return output;
}
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
    }
