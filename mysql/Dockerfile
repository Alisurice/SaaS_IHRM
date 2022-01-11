FROM mysql:8.0.20

# 参考：https://www.yuque.com/lazypg/docker/e4f4788850fd1c9f0e7fb1e588a5309e
# Dockerfile 搭建mysql并初始化db

# 定义会被容器自动执行的目录
ENV AUTO_RUN_DIR /docker-entrypoint-initdb.d

# 定义初始化sql文件
ENV FILE_0 act.sql
ENV FILE_1 ihrm.sql

# 把要执行的sql文件放到/docker-entrypoint-initdb.d/目录下，容器会自动执行这个sql
COPY ./$FILE_0 $AUTO_RUN_DIR/
COPY ./$FILE_1 $AUTO_RUN_DIR/

#给执行文件增加可执行权限
RUN chmod a+x $AUTO_RUN_DIR/$FILE_0
RUN chmod a+x $AUTO_RUN_DIR/$FILE_1
