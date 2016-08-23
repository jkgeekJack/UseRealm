# UseRealm
Realm简单使用介绍小Demo
Realm作为一种新兴的数据库以其变态的速度吸引了我们，摆图看看它有多快
![这里写图片描述](http://cc.cocimg.com/api/uploads/20160512/1463023867618595.jpg)
力压主流Sqlite，所以学习Realm就很有必要了

##**1.在项目的build.gradle=>buildscript=>dependencies添加**

```
classpath "io.realm:realm-gradle-plugin:1.0.1"
```
添加后效果如下

```

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.0.0'
        classpath "io.realm:realm-gradle-plugin:1.0.1"
    }
}
```
##**2.在moudle的build.gradule里头部添加**

```
apply plugin: 'realm-android'
```
##**3.开始使用Realm**

```
RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);
realm=Realm.getDefaultInstance();
```
最好把realm设为Realm类型的全局变量因为最后要把它关闭掉
我这里只介绍最简单的方法，至于配置表名，表的版本这里不详细介绍

##**4.声明开始事务**

```
		realm.beginTransaction();
        //TODO
        realm.commitTransaction();
```
开始前要beginTransaction，结束后要commitTransaction
不过还有另外两种方式
###第一种同步执行

```
realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    //TODO         
                }
            });
```
###第二种异步执行

```
				realmAsyncTask=realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                //TODO
                }

            });
```
为什么要返回一个 RealmAsyncTask的值呢，因为如果你在跳转Activity之后想中止这个过程则要把realmAsyncTask 关掉，就像网络请求一样。

##**5.新建实体**

```
public class Country extends RealmObject {
    private String name;
    private int population;

    public Country() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
```
实体必须是继承自RealmObject 

##**6.增加**

```
		realm.beginTransaction();
        Country country=realm.createObject(Country.class);
        country.setPopulation(1000000);
        country.setName("China");
        realm.commitTransaction();
```
##**7.查询**

```
RealmResults<Country>realmResults =realm.where(Country.class).findAll();
```
支持以下几种条件查询

 - between()、greaterThan()、lessThan()、greaterThanOrEqualTo() 和
   lessThanOrEqualTo()
 - equalTo() 和 notEqualTo()
 - contains()、beginsWith() 和 endsWith()
 - isNull() 和 isNotNull()
 - isEmpty() 和 isNotEmpty()
 - or()和and()
 - not()
 
 若想结果排序，可将findall换成findAllSorted
 

```
		//结果对poppulation升序排序
        RealmResults<Country> results3 =
                realm.where(Country.class)
                        .findAllSorted("population", Sort.ASCENDING);
```
如果是降序则要把**Sort.ASCENDING**换成Sort.DESCENDING**
##**8.删除**
无论是删除和修改都是基于查询语句的

```
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
```
##**9.修改**

```
		realm.beginTransaction();
        //将loacation为0的项的population改为13000000
        Country mCountry=results3.get(0);
        mCountry.setPopulation(13000000);
        realm.commitTransaction();
```

##**总结**
Realm虽然速度快，但他的扩展性不及其实sqlite的库，导致有些人不喜欢用，这世界上并没有完美的东西，至于你们怎么选择就看你们自己的取舍吧
