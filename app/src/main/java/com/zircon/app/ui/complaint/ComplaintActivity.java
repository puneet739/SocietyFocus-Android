package com.zircon.app.ui.complaint;

import android.widget.EditText;
import android.widget.TextView;

import com.zircon.app.R;
import com.zircon.app.ui.common.AbsBaseDialogFormActivity;

/**
 * Created by jikoobaruah on 09/02/16.
 */
public class ComplaintActivity  extends AbsBaseDialogFormActivity {
    private EditText mcomplaintView;

    @Override
    protected int getContentViewResID() {
        return R.layout.activity_complaint_new;
    }

    @Override
    protected void initViews() {
        setTitle("Lodge a complaint");
        mcomplaintView = (EditText) findViewById(R.id.complaint_input);
    }


}
