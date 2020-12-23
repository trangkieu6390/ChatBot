package com.example.covid_19.ChatBot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19.DashBoard.DashBoardActivity;
import com.example.covid_19.R;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.common.collect.Lists;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class ChatActivity extends AppCompatActivity implements BotReply{
    List<MessageHelpClass> messageHelpClassList = new ArrayList<>();
    RecyclerView recyclerView;
    MessageAdapter adapter;

    EditText edtMessage;
    ImageView imgSend;
    //dialogFlow
    private SessionsClient sessionsClient;
    private SessionName sessionName;
    private String uuid = UUID.randomUUID().toString();
    private String TAG = "mainactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chat);

        edtMessage = findViewById(R.id.edtMessage);
        imgSend = findViewById(R.id.imgSend);

        recyclerView = findViewById(R.id.RecyclerView_View);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.smoothScrollToPosition(messageHelpClassList.size());
        adapter = new MessageAdapter(messageHelpClassList, ChatActivity.this);
        recyclerView.setAdapter(adapter);

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = edtMessage.getText().toString();

                if (!message.isEmpty()) {
                    messageHelpClassList.add(new MessageHelpClass(message, false));
                    edtMessage.setText("");
                    sendMessageToBot(message);
                    Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                    Objects.requireNonNull(recyclerView.getLayoutManager())
                            .scrollToPosition(messageHelpClassList.size() - 1);
                } else {
                    Toast.makeText(ChatActivity.this, "Please enter text!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setUpBot();
    }


    private void sendMessageToBot(String message) {
        QueryInput input = QueryInput.newBuilder()
                .setText(TextInput.newBuilder().setText(message).setLanguageCode("en-EN")).build();
        new SendMessage((BotReply) this, sessionName, sessionsClient, input).execute();
    }

    private void setUpBot() {
        try {
            InputStream stream = this.getResources().openRawResource(R.raw.chatbot);
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream)
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
            String projectId = ((ServiceAccountCredentials) credentials).getProjectId();

            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(
                    FixedCredentialsProvider.create(credentials)).build();
            sessionsClient = SessionsClient.create(sessionsSettings);
            sessionName = SessionName.of(projectId, uuid);

            Log.d(TAG, "projectId : " + projectId);
        } catch (Exception e) {
            Log.d(TAG, "setUpBot: " + e.getMessage());
        }
    }


    @Override
    public void callback(DetectIntentResponse returnResponse) {
        if (returnResponse != null) {
            String botReply = returnResponse.getQueryResult().getFulfillmentText();
            if (!botReply.isEmpty()) {
                messageHelpClassList.add(new MessageHelpClass(botReply, true));
                adapter.notifyDataSetChanged();
                Objects.requireNonNull(recyclerView.getLayoutManager()).scrollToPosition(messageHelpClassList.size() - 1);
            } else {
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "failed to connect!", Toast.LENGTH_SHORT).show();
        }
    }
    public void callDashboard(View view){
        Intent intent = new Intent(getApplicationContext(), DashBoardActivity.class);
        startActivity(intent);
    }
}