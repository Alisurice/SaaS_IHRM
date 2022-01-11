FROM java:8
EXPOSE 9005
# 将本地的jar包放到容器的根目录中，linux中 '/'代表的就是根目录
ADD target/ihrm_social_securitys-1.0-SNAPSHOT.jar /ihrm_social_securitys.jar
# VOLUME /data/logs
ENTRYPOINT ["java", "-Xmx128m", "-Xms16m", "-Xmn8m", "-Xss8m", "-jar", "/ihrm_social_securitys.jar", "-Dspring.profiles.active=prod"]
