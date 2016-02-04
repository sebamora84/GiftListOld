package com.mlstuff.apps.giftlist;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mlstuff.apps.giftlist.Entities.GiftItem;
import com.mlstuff.apps.giftlist.Entities.WhoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewGift extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_gift);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_gift, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
    public class DownloadWhoList extends AsyncTask<Object, WhoItem, String> {
        @Override
        protected String doInBackground(Object... params) {

            String url = "http://mlstuff.netau.net/GiftList/GetList.php";
            String jsonItems;
            try
            {
                jsonItems = new WebConnection().GetJsonItems(url,"listName=who");
            }
            catch (Exception e)
            {
                return "Error requesting gifts";
            }

            try
            {
                GetWhoItems(jsonItems);
            }
            catch (JSONException e)
            {
                return "Error getting gifts";
            }
            return "Ok";
        }

        @Override
        protected void onProgressUpdate(WhoItem... who){
           // AddGiftToCuurentList(gift[0]);
        }

        //Executed in the UI thread
        @Override
        protected void onPostExecute(String result) {
            if (result != "Ok") {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        }
        private void GetWhoItems(String jsonItems) throws JSONException {

            JSONArray jsonGiftArray = new JSONArray(jsonItems);

            for (int i = 0; i < jsonGiftArray.length(); i++) {

                JSONObject jsonGift = jsonGiftArray.getJSONObject(i);
                WhoItem item= new WhoItem();
                item.Id = jsonGift.getString("Id");
                item.Name = jsonGift.getString("Name");
                publishProgress(item);
            }
        }
    }
}
