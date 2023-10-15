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
```
