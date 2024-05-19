package vn.iotstar.AppMobileEcommerce.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import vn.iotstar.AppMobileEcommerce.R;
import vn.iotstar.AppMobileEcommerce.model.ImagesModel;

import java.util.List;

public class ProductDetailPagerAdapter extends PagerAdapter {

    private Context context;
    private List<ImagesModel> imagesModel;

    public ProductDetailPagerAdapter(Context context, List<ImagesModel> imagesModel) {
        this.context = context;
        this.imagesModel = imagesModel;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        return null;
    }

    @Override
    public int getCount() {
        if(imagesModel!= null){
            return imagesModel.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
