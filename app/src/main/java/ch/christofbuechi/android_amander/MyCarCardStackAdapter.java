package ch.christofbuechi.android_amander;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andtinder.model.CardModel;
import com.andtinder.view.CardStackAdapter;

import java.util.Collection;

/**
 * Created by christof on 03.10.15.
 */
public class MyCarCardStackAdapter extends CardStackAdapter {

    public MyCarCardStackAdapter(Context context) {
        super(context);
    }

    public MyCarCardStackAdapter(Context context, Collection<? extends CardModel> items) {
        super(context, items);
    }

    @Override
    protected View getCardView(int i, CardModel cardModel, View convertView, ViewGroup parent) {

        if (cardModel instanceof MyCarCardModel) {
            final MyCarCardModel model = (MyCarCardModel) cardModel;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.cardscroll, parent, false);
                assert convertView != null;
            }

            ((ImageView) convertView.findViewById(R.id.image)).setImageDrawable(model.getCardImageDrawable());
            ((TextView) convertView.findViewById(R.id.title)).setText(model.getTitle());


            ((TextView) convertView.findViewById(R.id.circle_yes)).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    model.setLike(true);
                }
            });

            ((TextView) convertView.findViewById(R.id.circle_no)).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    model.setLike(false);
                }
            });


            return convertView;
        } else {
            return null;
        }
    }
}
