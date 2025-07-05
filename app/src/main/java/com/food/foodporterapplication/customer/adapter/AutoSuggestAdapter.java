package com.food.foodporterapplication.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AutoSuggestAdapter extends ArrayAdapter<String> {
    private Context context;
    private int resource;
    private List<String> items;
    private List<String> tempItems;
    private List<String> suggestions;
    private HashMap<String, String> itemToIdMap;

    public AutoSuggestAdapter(Context context, int resource, List<String> items, List<String> ids) {
        super(context, resource, 0, items);

        this.context = context;
        this.resource = resource;
        this.items = items;
        this.tempItems = new ArrayList<>(items);
        this.suggestions = new ArrayList<>();
        this.itemToIdMap = new HashMap<>();

        for (int i = 0; i < items.size(); i++) {
            this.itemToIdMap.put(items.get(i), ids.get(i));
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);
        }

        String item = items.get(position);

        if (item != null && view instanceof TextView) {
            ((TextView) view).setText(item);
        }

        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return (String) resultValue;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (String name : tempItems) {
                    if (name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(name);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<String> filterList = (ArrayList<String>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (String item : filterList) {
                    add(item);
                    notifyDataSetChanged();
                }
            }
        }
    };

    /**
     * Get the ID associated with the selected item.
     *
     * @param selectedItem The selected item string.
     * @return The ID associated with the item.
     */
    public String getIdForItem(String selectedItem) {
        return itemToIdMap.get(selectedItem);
    }
}
