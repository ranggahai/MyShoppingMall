package com.example.telkomsel.myshoppingmall;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.telkomsel.myshoppingmall.db.CartHelper;
import com.example.telkomsel.myshoppingmall.db.CartItem;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Multimatics on 22/07/2016.
 */
public class CartAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<CartItem> listItem;
    private CartHelper cartHelper;
    private String[] qtys = new String[]

    { "1","2","3","4","5","6","7","8"
    };

    public CartAdapter(Activity a) {
        this.activity = a;
        cartHelper = new CartHelper(a);
    }

    @Override
    public int getCount() {
        return getListItem().size();
    }

    @Override
    public Object getItem(int position) {
        return getListItem().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_row_cart, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CartItem item = (CartItem) getItem(position);

        holder.tvProductName.setText(item.getName());
        holder.tvProductPrice.setText(String.valueOf(item.getPrice()));
        String subtotal = Double.toString(item.getQty() * item.getPrice());
        holder.tvProductSubtotal.setText(subtotal);

        Glide.with(activity).load(item.getImage()).into(holder.imgProduk);

        holder.spnQty.setAdapter(new ArrayAdapter<String>(activity,
                android.R.layout.simple_dropdown_item_1line,
                android.R.id.text1,qtys));
        int selectedQtyPos = 0;
        for( int i=0; i<qtys.length; i++){
            if (Integer.parseInt(qtys[i])==item.getQty()){
                selectedQtyPos = i;
                break;
            }
        }
        holder.spnQty.setSelection(selectedQtyPos);

        holder.imgDelete.setOnClickListener(new CustomOnItemClickListener(position,
                new CustomOnItemClickListener.OnItemClickCallback() {
                    @Override
                    public void onItemClicked(View v, int position) {
                        cartHelper.delete(getListItem().get(position).getId());
                        getListItem().remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(activity,"One Item Successfully Deleted",Toast.LENGTH_SHORT).show();
                        RefreshCartEvent refreshCartEvent = new RefreshCartEvent();
                        refreshCartEvent.setEventMessage("Delete one item");
                        EventBus.getDefault().post(refreshCartEvent);
                    }
                }));

        holder.spnQty.setOnItemSelectedListener(new CustomOnItemSelectedListener(position,
                new CustomOnItemSelectedListener.OnItemSelectedCallback() {
                    @Override
                    public void onItemSelected(View v, TextView tv_subtotal, int cartRowPos, int spnArrayPos) {
                        int selectedQty = Integer.parseInt(qtys[spnArrayPos]);
                        int productQty = getListItem().get(cartRowPos).getQty();

                        if(selectedQty != productQty){
                            cartHelper.update(getListItem().get(cartRowPos).getId(),
                                    selectedQty);
                            tv_subtotal.setText(String.valueOf(selectedQty*getListItem().get(cartRowPos).getPrice()));
                            RefreshCartEvent refreshCartEvent = new RefreshCartEvent();
                            refreshCartEvent.setEventMessage("Update qty "+ selectedQty + " on productid : " + getListItem().get(cartRowPos).getId());

                            EventBus.getDefault().post(refreshCartEvent);
                        }
                    }
                },holder.tvProductSubtotal));

        return convertView;
    }

    public ArrayList<CartItem> getListItem() {
        return listItem;
    }

    public void setListItem(ArrayList<CartItem> listItem) {
        this.listItem = listItem;
    }


    static class ViewHolder {
        @BindView(R.id.spn_qty)
        Spinner spnQty;
        @BindView(R.id.img_delete)
        ImageView imgDelete;
        @BindView(R.id.img_produk)
        ImageView imgProduk;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_product_price)
        TextView tvProductPrice;
        @BindView(R.id.tv_product_subtotal)
        TextView tvProductSubtotal;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
