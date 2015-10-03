package ch.christofbuechi.android_amander;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.andtinder.model.CardModel;
import com.andtinder.view.CardStackAdapter;

import java.util.Collection;
import java.util.List;

/**
 * Created by christof on 03.10.15.
 */
public class MyCarCardStackAdapter extends CardStackAdapter {

    private static final long DURATIONTIME = 700;

    public MyCarCardStackAdapter(Context context) {
        super(context);
    }

    public MyCarCardStackAdapter(Context context, Collection<? extends CardModel> items) {
        super(context, items);
    }

    @Override
    protected View getCardView(int i, CardModel cardModel, View convertView, final ViewGroup parent) {

        if (cardModel instanceof MyCarCardModel) {
            final MyCarCardModel model = (MyCarCardModel) cardModel;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.cardscroll, parent, false);
                assert convertView != null;
            }


            List<Bitmap> list = model.getVehicle().imageBitmaps;
            if (list.size() > 0) {
                ((ImageView) convertView.findViewById(R.id.image)).setImageBitmap(list.get(0));
            } else {
                ((ImageView) convertView.findViewById(R.id.image)).setImageDrawable(model.getCardImageDrawable());
            }


            ((TextView) convertView.findViewById(R.id.title)).setText(model.getTitle());
            ((TextView) convertView.findViewById(R.id.description)).setText(model.getDescription());


            final View finalConvertView = convertView;
            convertView.findViewById(R.id.circle_yes).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    finalConvertView.animate()
                            .setDuration(DURATIONTIME)
                            .alpha(.75f)
                            .setInterpolator(new LinearInterpolator())
                            .x(finalConvertView.getWidth())
                            .y(finalConvertView.getHeight())
                            .rotation(Math.copySign(45, 0))
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    model.getOnCardDimissedListener().onLike();
                                }
                            });


                    model.getVehicle().match = 1.0;
                }
            });

            convertView.findViewById(R.id.circle_no).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    finalConvertView.animate()
                            .setDuration(DURATIONTIME)
                            .alpha(.75f)
                            .setInterpolator(new LinearInterpolator())
                            .x(-finalConvertView.getWidth())
                            .y(finalConvertView.getHeight())
                            .rotation(Math.copySign(45, 0))
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    model.getOnCardDimissedListener().onDislike();
                                }
                            });

                    model.getVehicle().match = 0.0;
                }
            });


            return convertView;
        } else {
            return null;
        }
    }
}
