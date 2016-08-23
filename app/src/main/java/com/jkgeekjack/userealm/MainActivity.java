package com.jkgeekjack.userealm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {
    private Realm realm;
    private RealmAsyncTask realmAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Realm realm=Realm.getInstance();
//        realm=Realm.getDefaultInstance();
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);

        realm=Realm.getDefaultInstance();
//
        realm.beginTransaction();
        RealmResults<Country>realmResults =realm.where(Country.class).findAll();
        realm.commitTransaction();
//            realm.executeTransactionAsync
//            realm.executeTransactionAsync(new Realm.Transaction() {
//                @Override
//                public void execute(Realm realm) {
//                    //TODO
//                }
//            });
//            realmAsyncTask=realm.executeTransactionAsync(new Realm.Transaction() {
//                @Override
//                public void execute(Realm realm) {
//                //TODO
//                }
//
//            });


            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    //TODO

                    Country country=realm.createObject(Country.class);
                    country.setPopulation(1000000);
                    country.setName("China");
//                    realm.copyToRealmOrUpdate()
//                    RealmResults<Country> results =
//                            realm.where(Country.class)
//                                    .findAllSorted("population", Sort.ASCENDING);
//                    for(int n=0;n<results.size();n++){
//                        results.get(n).setPopulation(n);
//                    }
                }
            });

        //结果对poppulation升序排序
        RealmResults<Country> results3 =
                realm.where(Country.class)
                        .findAllSorted("population", Sort.DESCENDING);

//        realm.copyToRealmOrUpdate()
        realm.beginTransaction();
        //删除查询到的所有项
        results3.deleteAllFromRealm();
        //删除查询到的location为2的项
        results3.deleteFromRealm(2);
        //删除查询到的第一项
        results3.deleteFirstFromRealm();
        //删除查询到的最后一项
        results3.deleteLastFromRealm();
        realm.commitTransaction();

        realm.beginTransaction();
        //将loacation为0的项的population改为13000000
        Country mCountry=results3.get(0);
        mCountry.setPopulation(13000000);
        realm.commitTransaction();

//        realm.beginTransaction();
        for (Country country:results3){

//            country.setPopulation(country.getPopulation()+5);

            Log.e("population",country.getPopulation()+"");
        }
//        realm.commitTransaction();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
