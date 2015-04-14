package com.bitcamp.benjamin.services_lab;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bitcamp.benjamin.services_lab.singletons.CouponFeed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CouponActivity extends ActionBarActivity {

    private ListView mCouponList= (ListView)findViewById(R.id.list_view_coupons);
    private EditText mFilter;
    private CouponAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        CouponFeed couponFeed = CouponFeed.getInstance();
        couponFeed.getFeed( getString(R.string.service_posts) );
        mAdapter = new CouponAdapter(couponFeed.getFeed());

        mCouponList.setAdapter(mAdapter);


       /* ArrayAdapter<Post> listAdapter = new ArrayAdapter<Post>(
                this,
                android.R.layout.simple_list_item_1,
                postFeed.getFeed()
        );
        mCouponList.setAdapter(listAdapter);
*/
        mFilter = (EditText)findViewById(R.id.edit_text_filter);
        mFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ( (ArrayAdapter<Post>)mCouponList.getAdapter()).getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_posts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private  class CouponAdapter extends ArrayAdapter<Coupon>{
        public CouponAdapter(ArrayList<Coupon> coupons){
            super(CouponActivity.this, R.layout.row,  coupons );
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            Coupon current = getItem(position);
            if (convertView == null) {
                convertView = CouponActivity.this.getLayoutInflater()
                        .inflate(R.layout.row, null);
            }


            TextView couponName = (TextView) convertView.findViewById(R.id.textview_name);
           couponName.setText(current.getName());


            TextView couponDescription = (TextView) convertView.findViewById(R.id.textview_description);

            couponDescription.setText(current.getDescription());

            ImageView couponImage = (ImageView) convertView.findViewById(R.id.imageview_image);


            Picasso.with(getContext()).load(current.getPicture()).into(couponImage);


            return convertView;

        }
    }
}
