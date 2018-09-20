package com.benmu.framework.extend.hook.ui.components;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.benmu.framework.extend.hook.HookConstants;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXInput;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.ui.view.WXEditText;
import com.taobao.weex.utils.WXResourceUtils;

import java.lang.reflect.Field;

/**
 * Created by Carry on 2018/4/17.
 */

public class HookInput extends WXInput {
    private String TAG = getClass().getName();

    public HookInput(WXSDKInstance instance, WXDomObject dom, WXVContainer parent, String
            instanceId, boolean isLazy) {
        super(instance, dom, parent, instanceId, isLazy);
        Log.e(TAG, TAG + "init");
    }

    public HookInput(WXSDKInstance instance, WXDomObject dom, WXVContainer parent, boolean isLazy) {
        super(instance, dom, parent, isLazy);
        Log.e(TAG, TAG + "init");
    }


    //benmu.org
    @WXComponentProp(name = HookConstants.NAME.TINTCOLOR)
    public void setTintColor(String tintColor) {

        if (TextUtils.isEmpty(tintColor)) return;
        try {
            EditText editText = getHostView();
            Field cursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            cursorDrawableRes.setAccessible(true);
            int drawableId = cursorDrawableRes.getInt(editText);
            Field mEditor = TextView.class.getDeclaredField("mEditor");
            mEditor.setAccessible(true);
            Object editor = mEditor.get(editText);
            Class<?> clazz = editor.getClass();
            Field mCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
            mCursorDrawable.setAccessible(true);
            Drawable[] drawables = new Drawable[1];
            drawables[0] = editText.getContext().getResources().getDrawable(drawableId);
            drawables[0].setColorFilter(WXResourceUtils.getColor(tintColor), PorterDuff.Mode
                    .SRC_IN);
            mCursorDrawable.set(editor, drawables);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //benmu.org

    @JSMethod
    public void clear() {
        WXEditText host = getHostView();
        if (host != null && host.getText().length() > 0) {
            host.getText().clear();
        }
    }

    @JSMethod
    public void show(boolean isShowPassWord){
        WXEditText editText = getHostView();
        if (editText != null){
            if (isShowPassWord){
                editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}
