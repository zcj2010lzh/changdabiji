package com.example.zhangdabiji;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AutoCompleteAdapter<T> extends BaseAdapter implements Filterable {
        private ArrayList<String> dataSource ;
        private Context context;
        private ArrayList data = new ArrayList<>();
       AutoCompleteAdapter(ArrayList<String>  list, Context context){
          this. context=context;
           dataSource=list;
       }
        @Override
        public int getCount() {
        return  data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;

            if(convertView==null){
               textView=new TextView(context);

            }else{
                textView = (TextView) convertView;
            }

//          HashMap<String, String> pc = mList.get(position);

//          holder.tv_input_code.setText("code："+pc.get("input_code"));
//          holder.tv_item_name.setText("item_name："+pc.get("item_name"));

            LinearLayout.LayoutParams ip=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 90);
            ip.leftMargin=30;
            textView.setLayoutParams(ip);

       textView.setText(dataSource.get(position));
            return textView;

        }



        public  Filter  getFilter(){
            return new Filter() {
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    ArrayList<String> newData = new ArrayList<>();
                        for(String data : dataSource){
                            if(data.contains(constraint))
                            newData.add(data);
                    }
                        if (newData.size()<=0)
                        newData.add(dataSource.get(0));
                    results.values = newData;
                    results.count = newData.size();
                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    data = (ArrayList)results.values;
                    notifyDataSetChanged();
                }
            };
        }
    class ViewHolder{
        public TextView tv_input_code;
        public TextView tv_item_name;
    }//            LinearLayout.LayoutParams ip=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 90);
//            ip.leftMargin=30;
//            textView.setLayoutParams(ip);

}




