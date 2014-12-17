package com.tlnguyen.mentormatetest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tlnguyen.mentormatetest.R;
import com.tlnguyen.mentormatetest.models.Idea;

import java.util.List;

/**
 * Created by TL on 12/15/2014.
 */
public class IdeaAdapter extends BaseAdapter {

    private List<Idea> mIdeas;
    private Context mContext;

    public IdeaAdapter(Context context, List<Idea> ideas) {
        this.mContext = context;
        this.mIdeas = ideas;
    }

    @Override
    public int getCount() {
        return mIdeas.size();
    }

    @Override
    public Object getItem(int position) {
        return mIdeas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        IdeaHolder holder;

        if (row == null) {
            row = LayoutInflater.from(mContext).inflate(R.layout.idea_row, parent, false);

            holder = new IdeaHolder();
            holder.name = (TextView) row.findViewById(R.id.tvIdeaName);
            holder.description = (TextView) row.findViewById(R.id.tvIdeaDesc);
            holder.owner = (TextView) row.findViewById(R.id.tvIdeaOwner);

            row.setTag(holder);
        }
        else {
            holder = (IdeaHolder) row.getTag();
        }

        Idea idea = this.mIdeas.get(position);

        if (idea != null) {
            holder.name.setText(idea.getName());
            holder.description.setText(idea.getDescription());
            holder.owner.setText(idea.getOwner().getUsername());
        }

        return row;
    }

    static class IdeaHolder {
        private TextView name;
        private TextView description;
        private TextView owner;
    }
}
