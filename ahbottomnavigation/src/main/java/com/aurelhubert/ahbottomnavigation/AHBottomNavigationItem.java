package com.aurelhubert.ahbottomnavigation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;

/**
 * AHBottomNavigationItem
 * The item is display in the AHBottomNavigation layout
 */
public class AHBottomNavigationItem {
	
	private String title = "";
	private Drawable drawable;
	private Drawable drawableDef;
	private int color = Color.GRAY;
	
	private
	@StringRes
	int titleRes = 0;
	private
	@DrawableRes
	int drawableRes = 0;
	@DrawableRes
	int drawableResDef = 0;

	/**
	 * @param title    Title
	 * @param resource Drawable resource
	 * @param drawableResDef    Drawable color
	 */
	@Deprecated
	public AHBottomNavigationItem(String title, @DrawableRes int resource, @ColorRes int drawableResDef) {
		this.title = title;
		this.drawableRes = resource;
		this.drawableResDef = drawableResDef;
	}

	/**
	 * Constructor
	 *
	 * @param titleRes    String resource
	 * @param drawableRes Drawable resource
	 * @param drawableResDef    Drawable resource
	 */
	public AHBottomNavigationItem(@StringRes int titleRes, @DrawableRes int drawableRes, @DrawableRes int drawableResDef) {
		this.titleRes = titleRes;
		this.drawableRes = drawableRes;
		this.drawableResDef = drawableResDef;
	}


	/**
	 * Constructor
	 *
	 * @param title    String
	 * @param drawable Drawable
	 * @param drawableDef    Drawable
	 */
	public AHBottomNavigationItem(String title, Drawable drawable, Drawable drawableDef) {
		this.title = title;
		this.drawable = drawable;
		this.drawableDef = drawableDef;
	}
	
	public String getTitle(Context context) {
		if (titleRes != 0) {
			return context.getString(titleRes);
		}
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
		this.titleRes = 0;
	}
	
	public void setTitle(@StringRes int titleRes) {
		this.titleRes = titleRes;
		this.title = "";
	}


	public Drawable getDrawable(Context context) {
		if (drawableRes != 0) {
			try {
				return AppCompatResources.getDrawable(context, drawableRes);
			} catch (Resources.NotFoundException e) {
				return ContextCompat.getDrawable(context, drawableRes);
			}
		}
		return drawable;
	}

	public Drawable getDrawableDef(Context context) {
		if (drawableRes != 0) {
			try {
				return AppCompatResources.getDrawable(context, drawableResDef);
			} catch (Resources.NotFoundException e) {
				return ContextCompat.getDrawable(context, drawableResDef);
			}
		}
		return drawableDef;
	}

	public void setDrawable(@DrawableRes int drawableRes) {
		this.drawableRes = drawableRes;
		this.drawable = null;
	}
	
	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
		this.drawableRes = 0;
	}

	public void setDrawableDef(@DrawableRes int drawableRes) {
		this.drawableResDef = drawableRes;
		this.drawableDef = null;
	}

	public void setDrawableDef(Drawable drawable) {
		this.drawableDef = drawable;
		this.drawableResDef = 0;
	}
}
