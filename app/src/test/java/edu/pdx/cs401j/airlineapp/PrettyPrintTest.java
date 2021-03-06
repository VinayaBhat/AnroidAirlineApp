package edu.pdx.cs401j.airlineapp;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.view.MenuItem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;

import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class PrettyPrintTest {

    /**
     * Pretty Print Flight with only Airline Name
     */
    @Test
    public void PrettyPrint_Airline_Success() {
        Intent intent = new Intent();
        ArrayList<String> flights=new ArrayList<>();
        flights.add("Alaska;123;PDX;1/1/00 1:30 AM;LAX;1/1/00 2:30 AM");
        intent.putStringArrayListExtra("airline",flights);
        PrettyPrintSearchFlight activity= Robolectric.buildActivity(PrettyPrintSearchFlight.class,intent)
        .create()
                .start()
                .resume()
                .visible()
                .get();
    }

    /**
     * Pretty Print Flight with Airline Name, Source, Destination
     */
    @Test
    public void PrettyPrint_AirlineSrcDest_Success() {
        Intent intent = new Intent();
        ArrayList<String> flights=new ArrayList<>();
        flights.add("Alaska;123;PDX;1/1/00 1:30 AM;LAX;1/1/00 2:30 AM");
        flights.add("Alaska;123;PDX;1/1/00 1:30 AM;LAX;1/1/00 2:30 AM");
        intent.putStringArrayListExtra("airline",flights);
        intent.putExtra("src","PDX");
        intent.putExtra("dest","LAX");
        PrettyPrintSearchFlight activity= Robolectric.buildActivity(PrettyPrintSearchFlight.class,intent)
                .create()
                .start()
                .resume()
                .visible()
                .get();


    }

    /**
     * Pretty Print Flight with improper file format
     */
    @Test
    public void PrettyPrint_InvalidFileFormat_Success() {
        Intent intent = new Intent();
        ArrayList<String> flights=new ArrayList<>();
        flights.add("Alaska123;PDX;1/1/00 1:30 AM;LAX;1/1/00 2:30 AM");
        intent.putStringArrayListExtra("airline",flights);
        intent.putExtra("src","PDX");
        intent.putExtra("dest","LAX");
        PrettyPrintSearchFlight activity= Robolectric.buildActivity(PrettyPrintSearchFlight.class,intent)
                .create()
                .start()
                .resume()
                .visible()
                .get();
        AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
        assertTrue(dialog.isShowing());
        ShadowAlertDialog sAlert = shadowOf(dialog);
        assertThat(sAlert.getMessage().toString(),equalTo("Flight number is not set properly"));


    }

    /**
     * Help Menu test
     */
    @Test
    public void helpTest(){
        Intent intent = new Intent();
        ArrayList<String> flights=new ArrayList<>();
        flights.add("Alaska;123;PDX;1/1/00 1:30 AM;LAX;1/1/00 2:30 AM");
        intent.putStringArrayListExtra("airline",flights);
        intent.putExtra("src","PDX");
        intent.putExtra("dest","LAX");
        PrettyPrintSearchFlight activity= Robolectric.buildActivity(PrettyPrintSearchFlight.class,intent)
                .create()
                .start()
                .resume()
                .visible()
                .get();
        MenuItem menuItem = new RoboMenuItem(R.menu.help_menu);
        activity.onOptionsItemSelected(menuItem);
        MenuItem menuItem2 = new RoboMenuItem(R.id.readme);
        activity.onOptionsItemSelected(menuItem2);
        Intent expectedIntent = new Intent(activity, README.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }




}
