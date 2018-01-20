package ca.mmess.gratefulhappinessdaily;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView extends TextView {

    private final String TAG = "CustomTextView";
    private static int idCounter = 0;
    public String uniqID;

    public CustomTextView(Context context) {
        super(context);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
/*        //assign unique id and increment counter
        idCounter++;
        uniqID = "ctv" + idCounter;
        */
    }

}
