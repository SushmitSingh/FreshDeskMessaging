package com.sushmit.freshdeskmessaging;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.freshchat.consumer.sdk.ConversationOptions;
import com.freshchat.consumer.sdk.FaqOptions;
import com.freshchat.consumer.sdk.Freshchat;
import com.freshchat.consumer.sdk.FreshchatConfig;
import com.freshchat.consumer.sdk.FreshchatUser;
import com.freshchat.consumer.sdk.exception.MethodNotAllowedException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.floatingActionButton);

        FreshchatConfig config = new FreshchatConfig("6cb513cf-a80e-4a36-9b42-f16a6793bc86", "25dcb135-b1cc-43c8-9928-3a03d61f398a");
        config.setDomain("msdk.in.freshchat.com");
        config.setCameraCaptureEnabled(true);
        config.setGallerySelectionEnabled(true);
        config.setResponseExpectationEnabled(true);
        Freshchat.getInstance(getApplicationContext()).init(config);

        // Get the user object for the current installation
        FreshchatUser freshchatUser = Freshchat.getInstance(getApplicationContext()).getUser();
        freshchatUser.setFirstName("John");
        freshchatUser.setLastName("Doe");
        freshchatUser.setEmail("john.doe.1982@mail.com");
        freshchatUser.setPhone("+91", "9790987495");

        // Call setUser so that the user information is synced with Freshchat's servers
        try {
            Freshchat.getInstance(getApplicationContext()).setUser(freshchatUser);
        } catch (MethodNotAllowedException e) {
            e.printStackTrace();
        }


        /* Set any custom metadata to give agents more context, and for segmentation for marketing or pro-active messaging */
        Map<String, String> userMeta = new HashMap<String, String>();
        userMeta.put("userLoginType", "Facebook");
        userMeta.put("city", "SpringField");
        userMeta.put("age", "22");
        userMeta.put("userType", "premium");
        userMeta.put("numTransactions", "5");
        userMeta.put("usedWishlistFeature", "yes");

//Call setUserProperties to sync the user properties with Freshchat's servers
        try {
            Freshchat.getInstance(getApplicationContext()).setUserProperties(userMeta);
        } catch (MethodNotAllowedException e) {
            e.printStackTrace();
        }

        //Faq Options
        List<String> tag = new ArrayList<>();
        tag.add("hiii");
        FaqOptions faqOptions = new FaqOptions().filterByTags(tag,"hiii")
                .showFaqCategoriesAsGrid(true)
                .showContactUsOnAppBar(true)
                .showContactUsOnFaqScreens(true)
                .showContactUsOnFaqNotHelpful(true);

        //Conversation Options
        List<String> tags = new ArrayList<>();
        tags.add("order_queries");
        ConversationOptions options = new ConversationOptions()
                .filterByTags(tags, "Order Queries");

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Freshchat.showConversations(MainActivity.this, options);
                //Freshchat.showFAQs(MainActivity.this,faqOptions);
            }
        });
    }


}