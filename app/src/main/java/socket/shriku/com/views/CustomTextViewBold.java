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
public class CustomTextViewBold extends TextView {

    public CustomTextViewBold(Context context) {
        super(context);
        if(!isInEditMode()) {
            FontUtil.setFont(this, context, true);
        }
    }

    public CustomTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()) {
            FontUtil.setFont(this, context, true);
        }
    }

    public CustomTextViewBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(!isInEditMode()) {
            FontUtil.setFont(this, context, true);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomTextViewBold(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if(!isInEditMode()) {
            FontUtil.setFont(this, context, true);
        }
    }
}
