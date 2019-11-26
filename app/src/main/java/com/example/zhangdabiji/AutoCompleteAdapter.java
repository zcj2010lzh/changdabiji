package com.example.zhangdabiji;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class AutoCompleteAdapter<T> extends BaseAdapter implements Filterable {
        private ArrayList<String> dataSource ;
        private Context context;
        private ArrayList data = new ArrayList<>();
       AutoCompleteAdapter(ArrayList<String>  list, Context context){
           context=context;
           dataSource=list;
       }
        @Override
        public int getCount() {
            return dataSource.size();
        }

        @Override
        public Object getItem(int position) {
            return dataSource.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=null;
         ViewHolder holder;
            if(convertView==null){
                view = View.inflate(context, R.layout.item_info_dict, null);

               holder=new ViewHolder();
//              holder.tv_input_code = (TextView) view.findViewById(R.id.tv_input_code);
                holder.tv_item_name = (TextView) view.findViewById(R.id.tv_item_name);

                view.setTag(holder);
            }else{
                view = convertView;
                holder =(ViewHolder)  view.getTag();
            }

//          HashMap<String, String> pc = mList.get(position);
            String pc=dataSource.get(position);

//          holder.tv_input_code.setText("code："+pc.get("input_code"));
//          holder.tv_item_name.setText("item_name："+pc.get("item_name"));


            holder.tv_item_name.setText(pc);

            return view;

        }



        public  Filter  getFilter(){
            return new Filter() {
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    ArrayList<String> newData = new ArrayList<>();
                        for(String data : dataSource){
                            if(data.contains(constraint.toString())){
                            newData.add(data);
                        }
                    }
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
    }

}




