# Official-website-backend
backend  of official website

## interface for front-end
see  https://www.postman.com/lively-station-290552/workspace/publicworkspace/collection/13180077-af411142-dfd4-4e33-bfb5-ec26ff095072 

## login xyz
https://github.com/spruceid/siwe-go/blob/main/message.go


## Run
 mysql: pls docker run mysql 
 ipfs:  pls docker run ipfs node 
 https://medium.com/@borepstein/running-an-ipfs-node-as-a-docker-container-on-a-private-network-4c0186aeca61

## build
```
  ssh root@81.69.8.95
 ./gradlew build -x test
  scp ./dist/apps/dl.jar  root@81.69.8.95:/root/Dapp-Learning-Official-web/dist/apps

```

## IPFS data
/root/graph-node/docker/data/ipfs 

## graph ql 集成

## TODO
1. 头像 简历
2. 红包改造，奖励认领自动化。
3. team system. 支持member加入team。 
4.

## 参考链接
- Cors: https://cloud.tencent.com/developer/article/1924258
- upload pic: https://juejin.cn/post/6844903630416379918  
- JPA query: https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl
-  JPA :  https://www.baeldung.com/hibernate-criteria-queries
- session filter:  https://blog.csdn.net/dothetrick/article/details/110356640