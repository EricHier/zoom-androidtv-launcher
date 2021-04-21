package tech.erichier.zoomlauncher;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.ListView;

public class CustomListView extends ListView {

    CustomOnKeyDownListener onKeyDownListener = null;

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnKeyDownListener(CustomOnKeyDownListener onKeyDownListener) {
        this.onKeyDownListener = onKeyDownListener;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (onKeyDownListener != null)
            onKeyDownListener.onKeyDown(event);

        return super.onKeyUp(keyCode, event);
    }
}
