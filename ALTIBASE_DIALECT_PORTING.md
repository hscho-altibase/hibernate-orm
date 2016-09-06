# AltibaseDialect 포팅 방법
이 문서는 Hibernate에서 Altibase를 연동하기 위해 AltibaseDialect를 포팅하는 방법을 설명한다.
## Hibernate 전체빌드
아래와 같이 hibernate 전체빌드 명령어를 사용하면 AltibaseDialect.class가 포함된 hibernate core 라이브러리 파일이 생성되며 자세한 설명은 [이곳](README.md)을 참고한다.

    git clone git://github.com/ALTIBASE/hibernate-orm.git
    git checkout 빌드할 branch버전(예:5.1)
    cd hibernate-orm
    ./gradlew clean build

## AltibaseDialect 수동 포팅
hibernate를 전체빌드 할 필요없이 AltibaseDialect관련 클래스만 컴파일하여 기존 라이브러리에 추가할 수 있다.

### Hibernate 라이브러리 다운로드
Hibernate 공식 배포사이트에서 각 버전 별 hibernate core 라이브러리를 제공한다.

* [http://hibernate.org/orm/downloads/](http://hibernate.org/orm/downloads/) 에서 내려 받을 수 있는 버전은 아래와 같다. 이 링크에서 제공하는 라이브러리는 AltibaseDialect.class가 포함되어있지 않으므로 다시 패키징하여 포함시켜야 한다.
    * 5.2.1
    * 5.1.0
    * 5.0.9
    * 4.3.11
    * 4.2.21

## AltibaseDialect.java 컴파일
Hibernate버전에 따른 AltibaseDialect.java파일은아래 링크에서 내려 받을 수 있다. Hibernate 4.2 버전부터는AltibaseLimitHandler.java과 함께 컴파일 해야 한다.

| Hibernate Ver  |AltibaseDialect.java | AltibaseLimitHandler.java | Required JDK ver |
|---|---|---|---|
|3.6|https://github.com/ALTIBASE/hibernate-orm/blob/3.6/hibernate-core/src/main/java/org/hibernate/dialect/AltibaseDialect.java |   | 1.5 |
|4.2|https://github.com/ALTIBASE/hibernate-orm/blob/4.2/hibernate-core/src/main/java/org/hibernate/dialect/AltibaseDialect.java | https://github.com/ALTIBASE/hibernate-orm/blob/4.2/hibernate-core/src/main/java/org/hibernate/dialect/pagination/AltibaseLimitHandler.java  | 1.6  |
|4.3|https://github.com/ALTIBASE/hibernate-orm/blob/4.3/hibernate-core/src/main/java/org/hibernate/dialect/AltibaseDialect.java | https://github.com/ALTIBASE/hibernate-orm/blob/4.3/hibernate-core/src/main/java/org/hibernate/dialect/pagination/AltibaseLimitHandler.java   | 1.6 |
|5.0|https://github.com/ALTIBASE/hibernate-orm/blob/5.0/hibernate-core/src/main/java/org/hibernate/dialect/AltibaseDialect.java | https://github.com/ALTIBASE/hibernate-orm/blob/5.0/hibernate-core/src/main/java/org/hibernate/dialect/pagination/AltibaseLimitHandler.java   | 1.6 |
|5.1|https://github.com/ALTIBASE/hibernate-orm/blob/5.1/hibernate-core/src/main/java/org/hibernate/dialect/AltibaseDialect.java | https://github.com/ALTIBASE/hibernate-orm/blob/5.1/hibernate-core/src/main/java/org/hibernate/dialect/pagination/AltibaseLimitHandler.java   | 1.6 |
|5.2(master)|https://github.com/ALTIBASE/hibernate-orm/blob/master/hibernate-core/src/main/java/org/hibernate/dialect/AltibaseDialect.java | https://github.com/ALTIBASE/hibernate-orm/blob/master/hibernate-core/src/main/java/org/hibernate/dialect/pagination/AltibaseLimitHandler.java   | 1.8|

1. hibernate jar 압축해제

    내려 받은 Hibernate 라이브러리 파일 중에 hibernate-core-x.x.x.Final.jar 파일을 java파일이 위치한 디렉토리로 이동시키고 압축을 푼다.
    
        mv hibernate-core-x.x.x.Final.jar 파일이 위치한 디렉토리
        cd java파일이 위치한 디렉토리
        jar xvf hibernate-core-x.x.x.Final.jar`
    
2. AltibaseLimitHandler.java 및 AltibaseDialect.java 파일을 컴파일한다.

        javac -d . -cp . AltibaseLimitHandler.java
        javac -d . -cp . AltibaseDialect.java
    
3. 컴파일이 완료되면 현재 디렉토리 하위에 다음과 같은 두 개의 클래스 파일이 생성된다.

        ./org/hibernate/dialect/AltibaseDialect.class
        ./org/hibernate/dialect/pagination/AltibaseLimitHandler.class

### AltibaseDialect 클래스를 Hibernate jar파일에 포팅
jar명령어로 새로 컴파일한 AltibaseDialect클래스들을 jar로 묶는다

    jar -cvfm hibernate-core-x.x.x.Final.jar META-INF/MANIFEST.MF .
