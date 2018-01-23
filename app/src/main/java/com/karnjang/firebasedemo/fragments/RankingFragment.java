package com.karnjang.firebasedemo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.karnjang.firebasedemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankingFragment extends Fragment {

    String[] RANKNAME = {"USERNAME01", "USERNAME02", "USERNAME03", "USERNAME04", "USERNAME05", "USERNAME06"};
    String[] RANKLEVEL = {"99", "92", "85", "75", "62", "30"};

    public RankingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rankview = inflater.inflate(R.layout.fragment_ranking, container, false);
        ListView listViewRank = (ListView) rankview.findViewById(R.id.listViewRank);
        CustomRankAdapter customRankAdapter = new CustomRankAdapter();
        listViewRank.setAdapter(customRankAdapter);

        return rankview;
    }

    public class CustomRankAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return RANKNAME.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View ranklistview, ViewGroup viewGroup) {
            ranklistview = getLayoutInflater().inflate(R.layout.custom_rank_listview, null);
            TextView textRankName = (TextView) ranklistview.findViewById(R.id.rankTextName);
            TextView textRankLevel = (TextView) ranklistview.findViewById(R.id.rankTextLevel);

            textRankName.setText(RANKNAME[i]);
            textRankLevel.setText(RANKLEVEL[i]);
            return ranklistview;
        }
    }

}
