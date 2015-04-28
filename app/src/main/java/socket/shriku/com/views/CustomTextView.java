package socket.shriku.com.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import socket.shriku.com.util.FontUtil;

/**
 * Created by sudharsanan on 2/21/15.
 */
public class CustomTextView extends TextView {

    public CustomTextView(Context context) {
        super(context);
        if(!isInEditMode()) {
            FontUtil.setFont(this, context, false);
        }
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()) {
            FontUtil.setFont(this, context, false);
        }
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(!isInEditMode()) {
            FontUtil.setFont(this, context, false);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if(!isInEditMode()) {
            FontUtil.setFont(this, context, false);
        }
    }
}
