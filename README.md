# Official-website-backend
backend  of official website

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

scp ./  root@81.69.8.95:/root/Dapp-Learning-Official-web/dist/apps
```

## 参考链接
- Cors: https://cloud.tencent.com/developer/article/1924258