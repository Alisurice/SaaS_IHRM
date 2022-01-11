FROM java:8
EXPOSE 9002
# 将本地的jar包放到容器的根目录中，linux中 '/'代表的就是根目录
ADD target/ihrm_company-1.0-SNAPSHOT.jar /ihrm_company.jar
# VOLUME /data/logs
ENTRYPOINT ["java", "-Xmx64m", "-Xms16m", "-Xmn8m", "-Xss8m", "-jar", "/ihrm_company.jar", "-Dspring.profiles.active=pro"]
