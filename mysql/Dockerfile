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

# 设置免密登录
ENV MYSQL_ALLOW_EMPTY_PASSWORD yes
# 将所需文件放到容器中
COPY setup.sh setup.sh
#COPY schema.sql schema.sql
COPY privileges.sql privileges.sql


# 设置容器启动时执行的命令
CMD ["sh", "/mysql/setup.sh"]
