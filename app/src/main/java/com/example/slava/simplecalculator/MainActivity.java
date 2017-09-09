package com.example.slava.simplecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    @BindView(R.id.text_result)
    TextView mTextResult;
    @BindView(R.id.sign_status)
    TextView mSing;
    @BindView(R.id.entered_data)
    TextView mEnteredData;

    private String first_number;
    private String second_number;
    private boolean isPlusPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        isPlusPressed = false;
        first_number = "";
        second_number = "";
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("first_number", first_number);
        outState.putString("second_number", second_number);
        outState.putString("mTextResult", mTextResult.getText().toString());
        outState.putString("mSing", mSing.getText().toString());
        outState.putString("mEnteredData", mEnteredData.getText().toString());
        outState.putBoolean("isPlusPressed", isPlusPressed);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        first_number = savedInstanceState.getString("first_number");
        second_number = savedInstanceState.getString("second_number");
        mTextResult.setText(savedInstanceState.getString("mTextResult"));
        mSing.setText(savedInstanceState.getString("mSing"));
        mEnteredData.setText(savedInstanceState.getString("mEnteredData"));
        isPlusPressed = savedInstanceState.getBoolean("isPlusPressed");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button7:
                plus();
                return;

            case R.id.button8:
                equally();
                return;

            case R.id.button9:
                clear();
                return;
        }

        if (mSing.getText().equals("=")) {
            first_number = "";
            second_number = "";
            mSing.setText("");
        }

        number(view);
    }

    private void number(View view) {
        if (!isPlusPressed) {
            if (first_number.length() < 6) {
                first_number += numberPressed(view);
                mTextResult.setText(first_number);
            }
        } else {
            if (second_number.length() < 6) {
                second_number += numberPressed(view);
                mTextResult.setText(second_number);
            }
        }
    }

    private void plus() {
        mTextResult.setText("0");
        if (isPlusPressed) {
            first_number = countUp();
            second_number = "";
            mEnteredData.setText(first_number);
            mTextResult.setText("0");
            return;
        }
        isPlusPressed = true;
        mSing.setText("+");
        mEnteredData.setText(first_number);
    }

    private void equally() {
        if (first_number.length() == 0 || second_number.length() == 0) {
            return;
        }
        if (!isSumValid()) {
            Toast.makeText(this, "Number is too much!", Toast.LENGTH_SHORT).show();
        }
        mSing.setText("=");
        mEnteredData.setText(String.format("%s + %s =", first_number, second_number));

        isPlusPressed = false;
        first_number = countUp();
        second_number = "";
        mTextResult.setText(first_number);
    }

    private void clear() {
        isPlusPressed = false;
        first_number = "";
        second_number = "";
        mEnteredData.setText("");
        mTextResult.setText("0");
        mSing.setText("");
    }

    private String numberPressed(View view) {
        switch (view.getId()) {

            case R.id.button:
                return "1";

            case R.id.button2:
                return "2";

            case R.id.button3:
                return "3";

            case R.id.button4:
                return "4";

            case R.id.button5:
                return "5";

            case R.id.button6:
                return "6";
        }
        return "";
    }

    private String countUp() {
        int res = Integer.valueOf(first_number) + Integer.valueOf(second_number);
        return String.valueOf(res);
    }

    public boolean isSumValid() {
        int res = Integer.MAX_VALUE - Integer.valueOf(first_number);
        return res >= Integer.valueOf(second_number);
    }
}
