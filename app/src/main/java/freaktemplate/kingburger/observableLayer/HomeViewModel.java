package freaktemplate.kingburger.observableLayer;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import freaktemplate.kingburger.R;
import freaktemplate.kingburger.getSet.MenuGetSet;
import freaktemplate.kingburger.getSet.SubMenuGetSet;
import freaktemplate.kingburger.utils.SPmanager;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<List<MenuGetSet>> menuGetSetMutableLiveData;
    private MutableLiveData<List<SubMenuGetSet>> subMenuGetSetMutableLiveData;


    public HomeViewModel(@NonNull Application application) {
        super(application);

        if (menuGetSetMutableLiveData == null) {
            menuGetSetMutableLiveData = new MutableLiveData<>();
            LoadMenuList();}

        if (subMenuGetSetMutableLiveData == null) {
            subMenuGetSetMutableLiveData = new MutableLiveData<>();
          //  LoadSubMenu(application.getSharedPreferences(application.getString(R.string.app_name), Context.MODE_PRIVATE).getString("subCategoryId", "77"));
        }

    }
    public MutableLiveData<List<SubMenuGetSet>> getSubMenuList() {
        return subMenuGetSetMutableLiveData;
    }

    public MutableLiveData<List<MenuGetSet>> getMenuList() {
        return menuGetSetMutableLiveData;
    }
    /*
     * =============================================================
     * ================ Getting Data From Server ===================
     * =============================================================
     */


    private void LoadMenuList() {
        //creating a string request to send request to the url
        String hp = getApplication().getString(R.string.link) + "api/menu_category";
        hp = hp.replace(" ", "%20");
        Log.e("url", hp);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, hp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        Log.e("Response", response);
                        try {
                            JSONObject jo_main = new JSONObject(response);
                            String del_charge = jo_main.getString("delivery_charges");
                            SPmanager.saveValue(getApplication(),"del_charge",del_charge);
                            JSONArray ja_menuData = jo_main.optJSONArray("menu_category");

                            List<MenuGetSet> tempArray = new ArrayList<>();
                            MenuGetSet temp;

                            for (int i = 0; i < ja_menuData.length(); i++) {
                                temp = new MenuGetSet();
                                JSONObject jo_menuData = ja_menuData.optJSONObject(i);
                                temp.setCategoryName(jo_menuData.optString("cat_name"));
                                temp.setCategoryImage(jo_menuData.optString("cat_icon"));
                                temp.setMenuId(jo_menuData.optString("id"));
                                tempArray.add(temp);
                            }
                            menuGetSetMutableLiveData.postValue(tempArray);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurs
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null) {
                            Log.e("Status code", String.valueOf(networkResponse.statusCode));
                            Toast.makeText(getApplication(), String.valueOf(networkResponse.statusCode), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public void LoadSubMenu(String subCategoryId) {
        //creating a string request to send request to the url
        String hp = getApplication().getString(R.string.link) + "api/subcategory?category="+subCategoryId;
        hp = hp.replace(" ", "%20");
        Log.e("url", hp);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, hp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        Log.e("Response", response);
                        try {
                            JSONObject jo_main = new JSONObject(response);
                            JSONArray ja_menuData = jo_main.optJSONArray("subcategory");
//                            String order_status  = jo_main.getString("order_status");
//                            SPmanager.saveValue(getApplication(),"order_status",order_status);
                            List<SubMenuGetSet> tempArray = new ArrayList<>();
                            SubMenuGetSet temp;
                            for (int i = 0; i < ja_menuData.length(); i++) {
                                temp = new SubMenuGetSet();
                                JSONObject jo_menuData = ja_menuData.optJSONObject(i);
                                temp.setCatId(jo_menuData.optString("category"));
                                temp.setItemDescription(jo_menuData.optString("description"));
                                temp.setItemId(jo_menuData.optString("id"));
                                temp.setItemImage(jo_menuData.optString("menu_image"));
                                temp.setItemName(jo_menuData.optString("menu_name"));
                                temp.setItemPrice(jo_menuData.optString("price"));
                                tempArray.add(temp);
                            }

                            subMenuGetSetMutableLiveData.postValue(tempArray);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurs
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null) {
                            Log.e("Status code", String.valueOf(networkResponse.statusCode));
                            Toast.makeText(getApplication(), String.valueOf(networkResponse.statusCode), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
