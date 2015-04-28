package socket.shriku.com.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FontUtil {
    
    private static Typeface typeface;
    
    public static void setFont(TextView textView, Context context, boolean isBold) {
        
        if(isBold) {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway_SemiBold.ttf");
        } else {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway_Regular.ttf");
        }
        
        textView.setTypeface(typeface);
    }
    
    public static void setFont(EditText editText, Context context, boolean isBold) {
        
        if(isBold) {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway_SemiBold.ttf");
        } else {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway_Regular.ttf");
        }
        
        editText.setTypeface(typeface);
    }
    
    public static void setFont(Button button, Context context, boolean isBold) {
        
        if(isBold) {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway_SemiBold.ttf");
        } else {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway_Regular.ttf");
        }
        
        button.setTypeface(typeface);
    }
}
