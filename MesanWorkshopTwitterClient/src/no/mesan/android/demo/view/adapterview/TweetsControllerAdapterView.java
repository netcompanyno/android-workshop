package no.mesan.android.demo.view.adapterview;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import no.mesan.android.demo.controller.R;
import no.mesan.android.demo.model.application.Application;
import no.mesan.android.demo.model.dto.TweetDTO;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TweetsControllerAdapterView extends RelativeLayout {

	private RelativeLayout listItemLayout;
	private TextView txtProfileName, txtTweetText, txtTweetDate;
	private ImageView imgProfileImage;

	private Context context;

	public TweetsControllerAdapterView(Context context) {
		super(context);
		this.context = context;
		initLayout();
	}

	public void initLayout() {
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		listItemLayout = (RelativeLayout) layoutInflater.inflate(R.layout.tweets_controller_list_item, this);
		//TextViewene fjernes, skal lages i oppgave fire
		txtProfileName = (TextView) listItemLayout.findViewById(R.id.txtProfileName);
		txtTweetText = (TextView) listItemLayout.findViewById(R.id.txtTweetText);
		txtTweetDate = (TextView) listItemLayout.findViewById(R.id.txtTweetDate);
		imgProfileImage = (ImageView) listItemLayout.findViewById(R.id.imgProfileImage);
	}

	public void renderItem(Boolean isNew, TweetDTO tweetDTO, int position) {
		//la stå
		if (position % 2 != 0) {
			listItemLayout.setBackgroundResource(R.drawable.tweets_gradient_list_element_darker);
		} else {
			listItemLayout.setBackgroundResource(R.drawable.tweets_gradient_list_element);
		}
		//fjernes. Teksten skal settes i oppgaven
		txtProfileName.setText(tweetDTO.getProfileName());
		txtTweetText.setText(tweetDTO.getContent());
		txtTweetDate.setText(Application.formatDateToTimeDiff(tweetDTO.getDate()));
		
		//la stå
		if (isNew) {
			new ImageFromWebTask().execute(tweetDTO.getProfileUrl());
		}
	}

	private class ImageFromWebTask extends AsyncTask<String, Void, Boolean> {
		private Drawable img;

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				URL url = new URL(params[0]);
				InputStream is = new BufferedInputStream(url.openStream());
				img = Drawable.createFromStream(is, "src");
				return true;

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (img != null) {
				imgProfileImage.setImageDrawable(img);
			}
			super.onPostExecute(result);
		}
	}

}