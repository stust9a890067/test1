package com.example.myapplication20220512;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication20220512.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //新增整批資料
        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<Member> arrayList = initData();

                for(int i=0;i<arrayList.size();i++) {
                    Member m = arrayList.get(i);
                    db.collection("member2")
                            .document(m.id)
                            .set(m)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("Demo","input OK:" + m.id);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Demo",e.getMessage());
                                }
                            });

                }

            }
        });


        //新增單筆資料
        binding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Member m=new Member("m11","Seed","US", 82);
                db.collection("member2")
                        .document(m.id)
                        .set(m)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("Demo","input OK:" + m.id);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Demo",e.getMessage());
                            }
                        });
            }
        });

        //讀取整批資料
        binding.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.collection("member2")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()) {

                                    String string="";
                                    for (QueryDocumentSnapshot doc : task.getResult()){

                                        Member m = doc.toObject(Member.class);
                                        string +=m.id + "," + m.name +"," + m.address + "," + m.age +"\n";

                                    }

                                    binding.textView.setText(string);

                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Demo",e.getMessage());
                            }
                        });


            }
        });

        //讀取單筆資料
        binding.btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = binding.editText.getText().toString();

                db.collection("member2")
                        .document(id)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){

                                    DocumentSnapshot doc = task.getResult();
                                    Member m = doc.toObject(Member.class);

                                    String string =m.id + "," + m.name +"," + m.address + "," + m.age +"\n";

                                    binding.textView.setText(string);


                                }
                            }
                        });
            }
        });

        //Filter
        binding.btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.collection("member2")
                        .whereGreaterThan("age",30)
                        .whereLessThan("age",55)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()) {

                                    String s="";
                                    for (QueryDocumentSnapshot doc : task.getResult()){

                                        Member m = doc.toObject(Member.class);
                                        s +=m.id + "," + m.name +"," + m.address + "," + m.age +"\n";


                                    }
                                    binding.textView.setText(s);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Demo",e.getMessage());
                            }
                        });
            }
        });

        binding.btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this,A.class);
                startActivity(it);
            }
        });



    }

    ArrayList<Member> initData(){
        ArrayList<Member> list = new ArrayList<>();

        list.add(new Member("m1","Rand","台北",7));
        list.add(new Member("m2","Phill","台中",50));
        list.add(new Member("m3","Kono","高雄",43));
        list.add(new Member("m4","Cufu","台北",26));
        list.add(new Member("m5","Ori","宜蘭",35));
        list.add(new Member("m6","Bate","桃園",25));
        list.add(new Member("m7","Ais","台北",75));
        list.add(new Member("m8","Rolen","台北",61));
        list.add(new Member("m9","Sirra","台南",24));
        list.add(new Member("m10","Ina","台東",42));

        return list;
    }
}