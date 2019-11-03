package com.example.zhangdabiji;

        import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

    public class AutoCompleteAdapter<T> extends BaseAdapter implements Filterable {


        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }

        @Override
        public Filter getFilter() {
            return null;
        }
      /*  private class MyFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (mOriginalValues == null) {
                    synchronized (mLock) {
                        mOriginalValues = new ArrayList<T>(mObjects);
                    }
                }
                int count = mOriginalValues.size();
                ArrayList<T> values = new ArrayList<T>();

                for (int i = 0; i < count; i++) {
                    T value = mOriginalValues.get(i);
                    String valueText = value.toString();
                    if (null != valueText && null != constraint
                            && valueText.contains(constraint)) {
                        values.add(value);
                    }
                }
                results.values = values;
                results.count = values.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                mObjects = (List<T>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        }*/
    }




