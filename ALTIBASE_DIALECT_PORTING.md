# AltibaseDialect 수동 포팅 방법
이 문서에서는 수동으로 AltibaseDialect.java를 컴파일하여 기존의 hibernate jar파일에 합치는 방법을 설명한다.
hibernate core 라이브러리를 통째로 컴파일하여 빌드하는 방법은 아래와 같으며 상세한 설명은 [이곳](README.md)을 참고하기 바란다.

    git clone git://github.com/ALTIBASE/hibernate-orm.git
    git checkout 빌드할 branch버전(예:5.1)
    cd hibernate-orm
    ./gradlew clean build

## Hibernate 라이브러리 다운로드
Hibernate 공식 배포사이트에서 각 버전별 core 라이브러리를 다운로드한다.

* [http://hibernate.org/orm/downloads/](http://hibernate.org/orm/downloads/) 에서 다운로드가 가능하며 현재 다운로드 가능한 버전은 아래와 같다.
    * 5.2.1
    * 5.1.0
    * 5.0.9
    * 4.3.11
    * 4.2.21


* 위에 링크되어 있는 라이브러리에는 AltibaseDialect.class 가 포함되어 있지 않기때문에 수동으로 AltibaseDialect.java를 컴파일해서 넣어줘야 한다.

## AltibaseDialect.java 컴파일
Hibernate 버전별 AltibaseDialect 소스는 아래 URL에서 다운로드 할 수 있다. 또한 Hibernate 4.2 버전부터는 AltibaseDialect.java와 더불어 AltibaseLimitHandler.java가 추가되었기 때문에 함께 컴파일해야 한다.

| Hibernate Ver  |AltibaseDialect.java | AltibaseLimitHandler.java | Required JDK ver |
|---|---|---|---|
|3.6|https://github.com/ALTIBASE/hibernate-orm/blob/3.6/hibernate-core/src/main/java/org/hibernate/dialect/AltibaseDialect.java |   | 1.5 |
|4.2|https://github.com/ALTIBASE/hibernate-orm/blob/4.2/hibernate-core/src/main/java/org/hibernate/dialect/AltibaseDialect.java | https://github.com/ALTIBASE/hibernate-orm/blob/4.2/hibernate-core/src/main/java/org/hibernate/dialect/pagination/AltibaseLimitHandler.java  | 1.6  |
|4.3|https://github.com/ALTIBASE/hibernate-orm/blob/4.3/hibernate-core/src/main/java/org/hibernate/dialect/AltibaseDialect.java | https://github.com/ALTIBASE/hibernate-orm/blob/4.3/hibernate-core/src/main/java/org/hibernate/dialect/pagination/AltibaseLimitHandler.java   | 1.6 |
|5.0|https://github.com/ALTIBASE/hibernate-orm/blob/5.0/hibernate-core/src/main/java/org/hibernate/dialect/AltibaseDialect.java | https://github.com/ALTIBASE/hibernate-orm/blob/5.0/hibernate-core/src/main/java/org/hibernate/dialect/pagination/AltibaseLimitHandler.java   | 1.6 |
|5.1|https://github.com/ALTIBASE/hibernate-orm/blob/5.1/hibernate-core/src/main/java/org/hibernate/dialect/AltibaseDialect.java | https://github.com/ALTIBASE/hibernate-orm/blob/5.1/hibernate-core/src/main/java/org/hibernate/dialect/pagination/AltibaseLimitHandler.java   | 1.6 |
|5.2(master)|https://github.com/ALTIBASE/hibernate-orm/blob/master/hibernate-core/src/main/java/org/hibernate/dialect/AltibaseDialect.java | https://github.com/ALTIBASE/hibernate-orm/blob/master/hibernate-core/src/main/java/org/hibernate/dialect/pagination/AltibaseLimitHandler.java   | 1.8|

1. hibernate jar 압축해제
    다운로드한 Hibernate 라이브러리 파일 중 hibernate-core-x.x.x.Final.jar 파일을 다운로드한 소스위치로 이동시키고 압축해제한다.
    `mv hibernate-core-x.x.x.Final.jar 다운로드한 소스 디렉토리`
    `cd 다운로드한 소스 디렉토리`
    `jar xvf hibernate-core-x.x.x.Final.jar`
2. AltibaseLimitHandler.java 및 AltibaseDialect.java 컴파일
    `javac -d . -cp . AltibaseLimitHandler.java`
    `javac -d . -cp . AltibaseDialect.java`
3. Compile이 정상적으로 되면 현재디렉토리의 하위에 다음 두개의 클래스파일이 추가된다.
    `./org/hibernate/dialect/AltibaseDialect.class`
    `./org/hibernate/dialect/pagination/AltibaseLimitHandler.class`

## AltibaseDialect 클래스를 Hibernate jar파일에 포팅
jar 명령을 통해 새로 컴파일한 AltibaseDialect 관련 클래스들을 jar로 묶는다.
    `rm AltibaseLimitHandler.java AltibaseDialect.java`
    `jar -cvfm hibernate-core-x.x.x.Final.jar META-INF/MANIFEST.MF .`
