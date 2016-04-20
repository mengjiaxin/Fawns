package com.fawns.app.view;

import com.fawns.app.bean.NavigationEntity;
import com.obsessive.library.base.BaseLazyFragment;

import java.util.List;

/**
 * Project Fawns
 * Package com.fawns.app.view
 * Author Mengjiaxin
 * Date 2016/4/11 16:30
 * Desc 请用一句话来描述作用
 */
public interface HomeView {
    void initializeViews(List<BaseLazyFragment> fragments, List<NavigationEntity> navigationList);
}
