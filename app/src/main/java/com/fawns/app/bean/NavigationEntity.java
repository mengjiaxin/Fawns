package com.fawns.app.bean;

/**
 * Project Fawns
 * Package com.fawns.app.bean
 * Author Mengjiaxin
 * Date 2016/4/12 15:36
 * Desc 请用一句话来描述作用
 */
public class NavigationEntity extends BaseEntity {
    
    private int iconResId;

    public NavigationEntity(String id, String name, int iconResId) {
        super(id, name);
        this.iconResId = iconResId;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }
}
