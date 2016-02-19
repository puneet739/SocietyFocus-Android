package com.zircon.app.ui.assets;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.zircon.app.R;
import com.zircon.app.ui.common.fragment.AbsFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by jikoobaruah on 19/02/16.
 */
public class AssetBookingFragment extends AbsFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText dateEditText;
    EditText timeEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_asset_booking, null, false);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dateEditText = (EditText) view.findViewById(R.id.date);
        timeEditText = (EditText) view.findViewById(R.id.time);

        dateEditText.setClickable(true);
        timeEditText.setClickable(true);

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDPD();
            }
        });

        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTPD();
            }
        });

        dateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDPD();
                }
            }
        });

        timeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    showTPD();
            }
        });
    }

    DatePickerDialog dpd = null;
    private void showDPD() {
        if (dpd != null && dpd.isVisible()) {
            dpd.dismiss();
            dpd = null;
        }
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                AssetBookingFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(getResources().getColor(R.color.mdtp_accent_color));

        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");

    }

    TimePickerDialog tpd = null;
    private void showTPD() {
        if (tpd != null && tpd.isVisible()){
            tpd.dismiss();
            tpd = null;
        }
        Calendar now = Calendar.getInstance();
        tpd = TimePickerDialog.newInstance(AssetBookingFragment.this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false);
        tpd.setAccentColor(getResources().getColor(R.color.mdtp_accent_color));

        tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,monthOfYear);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        dateEditText.setText(new SimpleDateFormat("dd-MMM-yyyy").format(c.getTime()));
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        timeEditText.setText(new SimpleDateFormat("hh:mm a").format(c.getTime()));

    }
}
