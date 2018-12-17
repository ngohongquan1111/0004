package com.example.administrator.maytinhcamtay;

import android.app.Activity;
import android.app.Notification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.widget.Button;
import android.widget.EditText;

import java.util.Queue;
import java.util.Stack;

public class MainActivity extends Activity implements View.OnClickListener {
    EditText etGiaTri;
    String smanhinh = "", test = "";
    String[] str1 = new String[1000];
    EditText etShow, etKetqua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etGiaTri = (EditText) findViewById(R.id.edtxt);
        etShow = (EditText) findViewById(R.id.edtxt2);
       etKetqua = (EditText) findViewById(R.id.edtxt4);
        int[] idButton = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5,
                R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btndot, R.id.btndel, R.id.btndev,
                R.id.btnmul, R.id.btnadd, R.id.btnsub, R.id.btnccls, R.id.btndongngoac, R.id.btnmongoac,R.id.btnsqr,R.id.btndoidau};

        //Gan id cho View
        for (int id : idButton) {
            View v = (View) findViewById(id);
            v.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnccls:
                try{
                    String[] bieuthuc = etGiaTri.getText().toString().split("");
                    String kq = result(hauto(bieuthuc));
                    etShow.setText(kq);
                    String[] kqarray = kq.trim().split("");
                    etKetqua.setText(tamphan(convertToInt(kqarray)));
                }catch (Exception e){
                }


                break;
            case R.id.btndel:
                //Xu ly xoa ky tu cuoi cung theo logic
                try {
                    smanhinh = xulydel(etGiaTri.getText().toString());
                    etGiaTri.setText(smanhinh);
                } catch (Exception e) {
                }
                break;


            default:
                smanhinh += ((Button) v).getText().toString().trim();
                etGiaTri.setText(smanhinh);
        }
    }
    //Xu ly xoa ky tu cuoi cung
    public String xulydel(String str) {
        int lenght = str.length();
        String tem = str.substring(0, lenght - 1);
        return tem;
    }

    public int uuTien(char c) {
        if (c=='!'){
            return 5;
        }
        if(c=='√'){
            return 4;
        }
        if (c == '*' || c == '/') {
            return 3;
        }
        if (c == '+' || c == '-') {
            return 2;
        } else if (c == ')' || c == '(') {
            return 1;
        }
        else
            return 0;
    }

    public String[] hauto(String[] pt) {
        String s1 = " ", E[];
        Stack<String> S = new Stack<String>();
        for (int i = 1; i < pt.length; i++) {
            char c = pt[i].charAt(0);
            if (uuTien(c) == 0) {
                s1 += pt[i];
            } else if (uuTien(c) == 1) {
                if (c == '(') {
                    S.push(pt[i]);
                } else {
                    char c1;
                    do {
                        c1 = S.pop().charAt(0);
                        if (c1 != '(') {
                            s1 += c1;
                        }
                    } while (c1 != '(');
                }
            } else {
                while (!S.isEmpty() && uuTien(S.peek().charAt(0)) >= uuTien(c)) {
                    s1 += S.pop();
                }//while
                s1 += " ";
                S.push(pt[i]);
            }//else


        }//for
        while (!S.empty()) {
            s1 += S.peek();
            S.pop();

        }//while
        s1 = s1.trim();
        E = s1.split("");
        return E;
    }

    public String result(String[] bieuThuc) {
        Stack<String> S = new Stack<String>();
        S.push(" ");
        for (int i = 1; i < bieuThuc.length; i++) {
            char c = bieuThuc[i].charAt(0);
            if (uuTien(c) == 0 || c == ' ' || c == '.') {
                S.push(bieuThuc[i]);
            } else {
                double so1 = 0, so2 = 0, kq = 0;
                int a = 0;
                String temp;
                while ((temp = S.pop()).charAt(0) != ' ') {

                    if (temp.charAt(0) == '.') {

                        so1 = so1 * Math.pow(10, -a);
                        a = 0;
                        continue;
                    }
                    so1 = Math.pow(10, a) * Double.parseDouble(temp) + so1;
                    a++;
                }
                a = 0;

                while ((temp = S.pop()).charAt(0) != ' ') {

                    if (temp.charAt(0) == '.') {

                        so2 = so2 * Math.pow(10, -a);
                        a = 0;
                        continue;
                    }
                    so2 = Math.pow(10, a) * Double.parseDouble(temp) + so2;
                    a++;
                }
                switch (c) {
                    case '√':
                        kq=Math.sqrt(so2);
                        break;
                    case '+':
                        kq = so1 + so2;
                        break;
                    case '-':
                        kq = so2 - so1;
                        break;
                    case '*':
                        kq = so2 * so1;
                        break;
                    case '/':
                        kq = so2 / so1;
                        break;
                    default:
                        break;
                }
                //day ket qua tinh duoc vao stack
                S.push(" ");
                S.push(Double.toString(kq));
            }
        }
        return S.pop();
    }
    public Integer convertToInt (String[] string){
        String s1="";
        int vt = 0;
        for (int k =1 ; k < string.length; k++)
        {
            s1+=string[k];
            if(string[k].charAt(0)=='.'){
                vt = k-1;
            }
        }
        int lengthh = s1.length();
        s1 = s1.substring(0,vt);
        int i=Integer.parseInt(s1);
        return  i;
    }
    public String tamphan(int dayso){
        int tamphan[] = new int[100];
        String dayso1="";
        int index = 0;
        while(dayso > 0){
            tamphan[index++] = dayso%3;
            dayso = dayso/3;
        }

        for(int i = index-1;i >= 0;i--){
            System.out.print(tamphan[i]);
           dayso1+=Integer.toString(tamphan[i]);
        }
        return dayso1;
    }


}