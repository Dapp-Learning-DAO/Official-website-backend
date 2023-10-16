# Official-website-backend
backend  of official website

## interface for front-end
see the direction  ./interface
## login xyz
https://github.com/spruceid/siwe-go/blob/main/message.go

```agsl
type Message struct {
	domain  string
	address common.Address
	uri     url.URL
	version string

	statement *string
	nonce     string
	chainID   int

	issuedAt       string
	expirationTime *string
	notBefore      *string

	requestID *string
	resources []url.URL
}
```

## build
```
ssh root@81.69.8.95
 ./gradlew build -x test
  scp ./dist/apps/dl.jar  root@81.69.8.95:/root/Dapp-Learning-Official-web/dist/apps
```



## TODO
1. 头像 简历
2. 红包改造

## 参考链接
- Cors: https://cloud.tencent.com/developer/article/1924258
- upload pic: https://juejin.cn/post/6844903630416379918  