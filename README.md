# Coupon project

The project simulates a system for creating and selling coupons.
There are three interfaces in the system:
Administration Interface - responsible for creating, updating and removing customers and companies in the system.
Company interface - responsible for creating, updating and removing coupons.
Customer interface - could buys the coupons that the companies produced.

# Required software

1. [Eclipse IDE](https://www.eclipse.org/downloads/download.php?file=/oomph/epp/2019-09/R/eclipse-inst-win64.exe)
2. [MySQL](https://dev.mysql.com/downloads/workbench/)
3. [JDK 1.8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
4. [Maven 3](https://maven.apache.org/)

# How to run

1. [Download ](https://github.com/ChayMizrahi/Full-Stack-coupon-project/archive/master.zip) this project.
2. Extract the files.
3. Copy the path of the unzipped fileds and open 'eclipse'.
4. In Eclipse: File --> import --> select: Existing maven projects --> next --> paste the path of the files in 'ROOT Directiry' --> Finish.
5. Open the project --> src/main/java --> com.chay.couponprojectspring --> right click on 'CouponProjectSpringApplication' --> run as --> java application.
6. When spring boot finish run open your browser and go to http://localhost:8080/.
