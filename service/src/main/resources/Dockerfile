FROM  amazoncorretto:21
# 作者
MAINTAINER He yu
## 工作目录
WORKDIR /usr/local/java
## 同步docker内部的时间
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
## 设置时区
ENV TZ=Asia/Shanghai
#EXPOSE 7900
## 复制jar包到/user/local/java下
COPY his.jar ./his.jar
# 执行创建app.jar
EXPOSE 7900
# 向外暴露的接口，最好与项目yml文件中的端口一致
ENTRYPOINT ["nohup","java","-Dspring.profiles.active=prod","-Dspring.config.location=/usr/local/java/application.yml,/usr/local/java/application-prod.yml","-jar","/usr/local/java/his.jar",">","his.log","&>","&"]
# 执行启动命令java -jar
