package com.example.myapplication.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.myapplication.Adapter.Detail_screen;
import com.example.myapplication.Adapter.MQTTHelper;
import com.example.myapplication.Adapter.pin_screen;
import com.example.myapplication.Domain.domain;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    ArrayList<Integer> lineData = new ArrayList<>();
    ArrayList<Integer> lineData2 = new ArrayList<>();
    ArrayList<Integer> lineData3 = new ArrayList<>();
    MQTTHelper mqttHelper;
    ArrayList<domain> domainArrayList1 = new ArrayList<>();
    Detail_screen adapter1 = new Detail_screen(domainArrayList1);
//    ArrayList<domain> domainArrayList2 = new ArrayList<>();
//    pin_screen adapter2 = new pin_screen(domainArrayList2);
    Integer temp;

    SwitchCompat toggle1, toggle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setData();
        startMQTT();
        recyclerViewDetail();
        toggle1 = findViewById(R.id.switch1);
        toggle2 = findViewById(R.id.switch2);
        toggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sentData("PneumaMD/feeds/toggle-1", "1");
                }
                else sentData("PneumaMD/feeds/toggle-1", "0");
            }
        });
        toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sentData("PneumaMD/feeds/toggle-2", "1");
                }
                else sentData("PneumaMD/feeds/toggle-2", "0");
            }
        });
//        recyclerViewPin();
    }

    private void recyclerViewDetail() {
        binding.sensorView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        domainArrayList1.add(new domain("temperature", 0, "0", lineData));
        domainArrayList1.add(new domain("humidity", 0, "0", lineData2));
        domainArrayList1.add(new domain("light", 0, "0", lineData3));
        binding.sensorView.setAdapter(adapter1);
    }
//    private void recyclerViewPin() {
//        binding.pinedView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        domainArrayList2.add(new domain("temperature", 0, "1", lineData));
//        domainArrayList2.add(new domain("humidity", 0, "1", lineData2));
//        domainArrayList2.add(new domain("light", 0, "1", lineData3));
//        binding.pinedView.setAdapter(adapter2);
//    }

    private void setData() {
        lineData.add(0);
        lineData.add(1);
        lineData.add(2);
        lineData.add(3);
        lineData.add(4);

        lineData2.add(5);
        lineData2.add(6);
        lineData2.add(7);
        lineData2.add(8);
        lineData2.add(9);

        lineData3.add(10);
        lineData3.add(11);
        lineData3.add(12);
        lineData3.add(13);
        lineData3.add(14);
    }

    public void sentData(String topic, String value){
        MqttMessage msg = new MqttMessage();
        msg.setId(1234);
        msg.setQos(0);
        msg.setRetained(false);

        byte[] b = value.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);

        try {
            mqttHelper.mqttAndroidClient.publish(topic, msg);
        }catch (MqttException e){
        }
    }

    public void startMQTT(){
        mqttHelper = new MQTTHelper(this);
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if (topic.contains("temperature")){
                    temp = domainArrayList1.get(0).getCurrent();
                    domainArrayList1.get(0).setCurrent(Integer.parseInt(message.toString()));
                    lineData.add(Integer.parseInt(message.toString()));
                    lineData.remove(0);
                    domainArrayList1.get(0).setLineData(lineData);
                    temp = domainArrayList1.get(0).getCurrent() - temp;
                    domainArrayList1.get(0).setChangeAmount(temp.toString());
                    adapter1.notifyItemChanged(0);
                    Log.d("TEST", "Receive: " + topic + ' ' + message);
                }
                else if (topic.contains("humidity")){
                    temp = domainArrayList1.get(1).getCurrent();
                    domainArrayList1.get(1).setCurrent(Integer.parseInt(message.toString()));
                    lineData2.add(Integer.parseInt(message.toString()));
                    lineData2.remove(0);
                    domainArrayList1.get(0).setLineData(lineData2);
                    temp = domainArrayList1.get(1).getCurrent() - temp;
                    domainArrayList1.get(1).setChangeAmount(temp.toString());
                    adapter1.notifyItemChanged(1);
                    Log.d("TEST", "Receive: " + topic + ' ' + message);
                }
                else if (topic.contains("light")){
                    temp = domainArrayList1.get(2).getCurrent();
                    domainArrayList1.get(2).setCurrent(Integer.parseInt(message.toString()));
                    lineData3.add(Integer.parseInt(message.toString()));
                    lineData3.remove(0);
                    domainArrayList1.get(0).setLineData(lineData3);
                    temp = domainArrayList1.get(2).getCurrent() - temp;
                    domainArrayList1.get(2).setChangeAmount(temp.toString());
                    adapter1.notifyItemChanged(2);
                    Log.d("TEST", "Receive: " + topic + ' ' + message);
                }
                else if (topic.contains("toggle-1")){
                    toggle1.setChecked(message.toString().equals("1"));
                    Log.d("TEST", "Receive: " + topic + ' ' + message);
                }
                else if (topic.contains("toggle-2")){
                    toggle2.setChecked(message.toString().equals("1"));
                    Log.d("TEST", "Receive: " + topic + ' ' + message);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
}