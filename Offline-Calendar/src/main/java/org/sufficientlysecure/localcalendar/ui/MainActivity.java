/**
 *  Copyright (C) 2013  Dominik Schürmann <dominik@dominikschuermann.de>
 *  Copyright (C) 2012  Harald Seltner <h.seltner@gmx.at>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.sufficientlysecure.localcalendar.ui;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;

import org.sufficientlysecure.localcalendar.CalendarController;
import org.sufficientlysecure.localcalendar.R;
import org.sufficientlysecure.localcalendar.util.InstallLocationHelper;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import static org.sufficientlysecure.localcalendar.CalendarController.addCalendar;

public class MainActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean firstRun = sp.getBoolean("first", true);
        if (firstRun){
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("first", false);
            editor.commit();

            // Add a default calendar
            addCalendar(this, "default", 0, getContentResolver());
        }

        // Exit the application
        System.exit(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_main_add_calendar:
                showAddCalendarActivity();
                return true;
            case R.id.menu_main_about:
                showAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAddCalendarActivity() {
        // show edit activity with empty text field and add button
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }

    private void showAbout() {
        SpannableString s = new SpannableString(getText(R.string.about));
        Linkify.addLinks(s, Linkify.ALL);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(s);
        AlertDialog alert = builder.create();
        alert.show();
        ((TextView) alert.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod
                .getInstance());
    }
}
