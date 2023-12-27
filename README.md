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
  ssh root@ip
 ./gradlew build -x test
  scp ./dist/apps/dl.jar  root@ip:/root/Official-website-backend/dist/apps

```

## IPFS data
/root/graph-node/docker/data/ipfs

## COS file 
https://dlh-1257682033.cos.ap-hongkong.myqcloud.com/{uuid}

## graph ql 集成
"https://api.studio.thegraph.com/proxy/55957/dapp-learning-redpacket/version/latest"

```agsl
{
  redpackets  {
                id   
    happyRedPacketId
    				name
                    refunded
                 allClaimed  
                     claimers {
                      claimer
                    } 
                  }
}
```

## TODO
1. 头像 简历
2. 红包改造，奖励认领自动化。
3. team system. 支持member加入team。 
4.

## 参考链接
- Cors: https://cloud.tencent.com/developer/article/1924258
- session: https://www.cnblogs.com/RudeCrab/p/14251154.html
- upload pic: https://juejin.cn/post/6844903630416379918  
- JPA query: https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl
-  JPA :  https://www.baeldung.com/hibernate-criteria-queries
- session filter:  https://blog.csdn.net/dothetrick/article/details/110356640
- cos： https://cloud.tencent.com/developer/article/1559746
- cos download: https://blog.csdn.net/qq_43960768/article/details/126731733 