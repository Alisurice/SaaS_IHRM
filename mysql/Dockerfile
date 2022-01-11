FROM mysql:8.0.20

# 参考：https://blog.csdn.net/weixin_38187317/article/details/81477424
# mysql的工作位置
ENV WORK_PATH /usr/local/

# 初始化数据库的SQL
ENV FILE_0 act.sql
ENV FILE_1 ihrm.sql
# 执行SQL
ENV INSTALL_DATA_SHELL docker-entrypoint.sh

COPY ./$FILE_0 $WORK_PATH/
COPY ./$FILE_1 $WORK_PATH/
#把要执行的shell文件放到/docker-entrypoint-initdb.d/目录下，容器会自动执行这个shell
COPY ./$INSTALL_DATA_SHELL $AUTO_RUN_DIR/

#给执行文件增加可执行权限
RUN chmod a+x $AUTO_RUN_DIR/$INSTALL_DATA_SHELL

# 设置容器启动时执行的命令
#CMD ["sh", "/docker-entrypoint-initdb.d/import.sh"]