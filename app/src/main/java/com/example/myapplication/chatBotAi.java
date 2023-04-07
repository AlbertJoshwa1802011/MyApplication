package com.example.myapplication;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class chatBotAi extends AppCompatActivity {

    private RecyclerView chatsRV;
    private EditText userMsgEdt;
    private FloatingActionButton sendMsgFAB;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
    private ArrayList<ChatsModel> chatsModelArrayList;

    @Override
    public String toString() {
        return "chatBotAi{" +
                "chatsModelArrayList=" + chatsModelArrayList +
                '}';
    }

    private ChatRVAdapter chatRVAdapter;

    private Logger logger = Logger.getLogger(chatBotAi.class.getName());

    public chatBotAi() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot_ai);
        chatsRV = findViewById(R.id.idRVChats);
        userMsgEdt = findViewById(R.id.idEdtMessage);
        sendMsgFAB = findViewById(R.id.idFABSend);
        chatsModelArrayList = new ArrayList<>();
        chatRVAdapter = new ChatRVAdapter(chatsModelArrayList,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        chatsRV.setLayoutManager(manager);
        chatsRV.setAdapter(chatRVAdapter);

        sendMsgFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userMsgEdt.getText().toString().isEmpty()){
                    Toast.makeText(chatBotAi.this,"Please enter your message!",Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(chatBotAi.this,"Going to make request"+userMsgEdt.getText().toString(),Toast.LENGTH_SHORT).show();
                getResponse(userMsgEdt.getText().toString());
                userMsgEdt.setText("");
            }
        });

    }

    private void getResponse(String text){
        logger.info("text::"+text);
        chatsModelArrayList.add(new ChatsModel(text,USER_KEY));
        for(ChatsModel chatsModel :chatsModelArrayList) {
            logger.info("Chats Model Array list::" + chatsModel.toString());
        }
        chatRVAdapter.notifyDataSetChanged();
        String url = "http://api.brainshop.ai/get?bid=156451&key=RdjOJWhMeR2JDdOW&uid={uid}&msg="+text;
        String baseUrl = "http://api.brainshop.ai/";
        logger.info("url::"+url);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url) // Replace with the URL of your API
                .build();


        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {

                if (response.isSuccessful()) {
                    String responseBody = response.body().string();

                    // Parse the JSON response using Gson
                    Gson gson = new Gson();
                    MessageModel model = gson.fromJson(responseBody, MessageModel.class);
                    logger.info(" Success Response model"+model.toString());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Update UI with retrieved data
                            chatsModelArrayList.add(new ChatsModel(model.getCnt(),BOT_KEY));
                            chatRVAdapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    logger.info(" Failure Response");
                }
            }

            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
        });


        
      /*  Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetroFitAPI retroFitAPI = retrofit.create(RetroFitAPI.class);
        Call<MessageModel> call = retroFitAPI.getMessage(url);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                if(response.isSuccessful()){
                    MessageModel model = response.body();
                    logger.info(" Success Response model"+model);
                    chatsModelArrayList.add(new ChatsModel(model.getCnt(),BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                }
                else {
                    logger.info(" failure in success Response model"+response.body());

                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                logger.info(" Failure Response");
                chatsModelArrayList.add(new ChatsModel("please revert your question",BOT_KEY));
                chatRVAdapter.notifyDataSetChanged();
            }
        });*/
        logger.info("End Chats Model Array list::"+chatsModelArrayList);

    }
}