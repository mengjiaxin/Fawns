package com.obsessive.library.loading;

import android.content.Context;
import android.view.View;

/**
 * Project Fawns
 * Package com.obsessive.library.loading
 * Author Mengjiaxin
 * Date 2016/4/11 16:42
 * Desc 请用一句话来描述作用
 */
public interface IVaryViewHelper {

    View getCurrentLayout();

    void restoreView();

    void showLayout(View view);

    View inflate(int layoutId);

    Context getContext();

    View getView();
}
